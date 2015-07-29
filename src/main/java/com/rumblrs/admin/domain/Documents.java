/**
 * 
 */
package com.rumblrs.admin.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rumblrs.admin.domain.util.CustomDateTimeDeserializer;
import com.rumblrs.admin.domain.util.CustomDateTimeSerializer;

/**
 * Stores info about the documentation of the bike
 * @author Varun Achar
 */
public class Documents {
	
	@Field("loan_amount")
	private BigDecimal loanAmount = new BigDecimal(0);
	
	@Field("rc_book")
	private boolean rcBook;
	
	@Field("puc")
	private DateTime puc;
	
	@Field("insurance")
	@JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
	private DateTime insurance;
	
	@Field("warranty")
	@JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
	private DateTime warranty;

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public boolean isRcBook() {
		return rcBook;
	}

	public void setRcBook(boolean rcBook) {
		this.rcBook = rcBook;
	}

	public DateTime getPuc() {
		return puc;
	}

	public void setPuc(DateTime puc) {
		this.puc = puc;
	}

	public DateTime getInsurance() {
		return insurance;
	}

	public void setInsurance(DateTime insurance) {
		this.insurance = insurance;
	}

	public DateTime getWarranty() {
		return warranty;
	}

	public void setWarranty(DateTime warranty) {
		this.warranty = warranty;
	}
}
