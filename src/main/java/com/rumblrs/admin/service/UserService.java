package com.rumblrs.admin.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.rumblrs.admin.domain.Authority;
import com.rumblrs.admin.domain.User;
import com.rumblrs.admin.repository.AuthorityRepository;
import com.rumblrs.admin.repository.UserRepository;
import com.rumblrs.admin.security.AuthoritiesConstants;
import com.rumblrs.admin.security.SecurityUtils;
import com.rumblrs.admin.service.util.RandomUtil;
import com.rumblrs.admin.web.rest.dto.UserDTO;

/**
 * Service class for managing users.
 */
@Service
public class UserService {

	private final Logger log = LoggerFactory.getLogger(UserService.class);

	@Inject
	private PasswordEncoder passwordEncoder;

	@Inject
	private UserRepository userRepository;

	@Inject
	private AuthorityRepository authorityRepository;

	/**
	 * Verify if the any user exists with the mobile, email or login supplied.
	 * 
	 * @param login
	 * @param mobile
	 * @param email
	 * @return
	 */
	public Optional<User> verifyUserExists(String login, String mobile,
			String email) {
		Optional<User> user = Optional.empty();
		
		if (!StringUtils.isEmpty(mobile)) {
			user = userRepository.findOneByMobileNo(mobile);
			if (user.isPresent()) {
				return user;
			}
		}
		
		if (!StringUtils.isEmpty(email)) {
			user = userRepository.findOneByEmail(email);
			if (user.isPresent()) {
				return user;
			}
		}
		
		if (!StringUtils.isEmpty(login)) {
			user = userRepository.findOneByLogin(login);
			if (user.isPresent()) {
				return user;
			}
		}
		return user;

	}

	// FIXME: Activation search should include user name to avoid cases where
	// same activation key has been generated for 2 users
	public Optional<User> activateRegistration(String key) {
		log.debug("Activating user for activation key {}", key);
		userRepository.findOneByActivationKey(key).map(user -> {
			// activate given user for the registration key.
				user.setActivated(true);
				user.setActivationKey(null);
				userRepository.save(user);
				log.debug("Activated user: {}", user);
				return user;
			});
		return Optional.empty();
	}

	public Optional<User> completePasswordReset(String newPassword, String key) {
		log.debug("Reset user password for reset key {}", key);

		return userRepository
				.findOneByResetKey(key)
				.filter(user -> {
					DateTime oneDayAgo = DateTime.now().minusHours(24);
					return user.getResetDate().isAfter(
							oneDayAgo.toInstant().getMillis());
				}).map(user -> {
					user.setPassword(passwordEncoder.encode(newPassword));
					user.setResetKey(null);
					user.setResetDate(null);
					userRepository.save(user);
					return user;
				});
	}

	public Optional<User> requestPasswordReset(String mail) {
		return userRepository.findOneByEmail(mail)
				.filter(user -> user.getActivated() == true).map(user -> {
					user.setResetKey(RandomUtil.generateResetKey());
					user.setResetDate(DateTime.now());
					userRepository.save(user);
					return user;
				});
	}

	public User createUserInformation(UserDTO userDTO) {

		Optional<User> userOptional = verifyUserExists(userDTO.getLogin(), userDTO.getMobileNo(), userDTO.getEmail());
		if (userOptional.isPresent()) {
			throw new DuplicateUserException("User already exists");
		}
		User newUser = new User();
		Authority authority = authorityRepository.findOne("ROLE_USER");
		Set<Authority> authorities = new HashSet<>();
		String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
		newUser.setLogin(userDTO.getLogin());
		// new user gets initially a generated password
		newUser.setPassword(encryptedPassword);
		newUser.setFirstName(userDTO.getFirstName());
		newUser.setLastName(userDTO.getLastName());
		newUser.setEmail(userDTO.getEmail());
		newUser.setLangKey(userDTO.getLangKey());
		newUser.setAddress(userDTO.getAddress());
		newUser.setMobileNo(userDTO.getMobileNo());
		// new user is not active
		newUser.setActivated(false);
		// new user gets registration key
		newUser.setActivationKey(RandomUtil.generateActivationKey());
		authorities.add(authority);
		newUser.setAuthorities(authorities);
		userRepository.save(newUser);
		log.debug("Created Information for User: {}", newUser);
		return newUser;

	}

	/**
	 * Creates a customer's login.
	 * 
	 * @param login
	 * @param password
	 *            If the password is empty, then 4 digit number is set as
	 *            password. This is also used as verification or OTP code.
	 * @param name
	 * @param mobileNo
	 * @param email
	 * @return
	 */
	public User createCustomer(UserDTO userDTO) {

		Optional<User> userOptional = verifyUserExists(null, userDTO.getMobileNo(), userDTO.getEmail());
		if (userOptional.isPresent()) {
			throw new DuplicateUserException("User already exists");
		}
		User newUser = new User();
		Authority authority = authorityRepository
				.findOne(AuthoritiesConstants.CUSTOMER);
		Set<Authority> authorities = new HashSet<>();
		String activationKey = RandomUtil.generateOTP();
		String password = userDTO.getPassword();
		if (StringUtils.isEmpty(password)) {
			password = activationKey;
		}
		String encryptedPassword = passwordEncoder.encode(password);
		newUser.setLogin(userDTO.getMobileNo());
		// new user gets initially a generated password
		newUser.setPassword(encryptedPassword);
		newUser.setFirstName(userDTO.getFirstName());
		newUser.setLastName(userDTO.getLastName());
		newUser.setEmail(userDTO.getEmail());
		newUser.setMobileNo(userDTO.getMobileNo());
		newUser.setLangKey("en");
		newUser.setAddress(userDTO.getAddress());
		newUser.setLandmark(userDTO.getLandmark());
		newUser.setMobileNo(userDTO.getMobileNo());
		// new user is not active
		newUser.setActivated(true);
		newUser.setResetDate(DateTime.now());
		newUser.setResetKey(userDTO.getMobileNo());
		
		// new user gets registration key
		newUser.setActivationKey(activationKey);
		authorities.add(authority);
		newUser.setAuthorities(authorities);
		userRepository.save(newUser);
		log.debug("Created Information for User: {}", newUser);
		return newUser;
	}

	public void updateUserInformation(String firstName, String lastName,
			String email, String mobileNo, String langKey) {
		userRepository.findOneByLogin(SecurityUtils.getCurrentLogin())
				.ifPresent(u -> {
					u.setFirstName(firstName);
					u.setLastName(lastName);
					u.setEmail(email);
					u.setMobileNo(mobileNo);
					u.setLangKey(langKey);
					userRepository.save(u);
					log.debug("Changed Information for User: {}", u);
				});
	}

	public void changePassword(String password) {
		userRepository.findOneByLogin(SecurityUtils.getCurrentLogin())
				.ifPresent(
						u -> {
							String encryptedPassword = passwordEncoder
									.encode(password);
							u.setPassword(encryptedPassword);
							userRepository.save(u);
							log.debug("Changed password for User: {}", u);
						});
	}

	public User getUserWithAuthorities() {
		User currentUser = userRepository.findOneByLogin(
				SecurityUtils.getCurrentLogin()).get();
		currentUser.getAuthorities().size(); // eagerly load the association
		return currentUser;
	}

	/**
	 * Not activated users should be automatically deleted after 3 days.
	 * <p/>
	 * <p>
	 * This is scheduled to get fired everyday, at 01:00 (am).
	 * </p>
	 */
	@Scheduled(cron = "0 0 1 * * ?")
	public void removeNotActivatedUsers() {
		DateTime now = new DateTime();
		List<User> users = userRepository
				.findAllByActivatedIsFalseAndCreatedDateBefore(now.minusDays(3));
		for (User user : users) {
			log.debug("Deleting not activated user {}", user.getLogin());
			userRepository.delete(user);
		}
	}
}
