'use strict';

angular.module('rumblrsadminApp')
    .controller('BikeEditController', function($scope, $stateParams, $state, Upload, Bike, Model, User) {
        $scope.bike = {
            yearOfManufacture: 2010,
            owners: 1
        };
        $scope.bikeDetail = {
            report: {}
        };

        $scope.owner = {};
        $scope.models = [];
        $scope.uploads = [];
        $scope.closeImageTemplate = 'closeImageTemplate.html';
        $scope.radios = [{
            text: 'Good Condition',
            value: "1"
        }, {
            text: 'Average Condition',
            value: "2"
        }, {
            text: 'Needs Repair',
            value: "3"
        }, {
            text: 'Needs Replacement',
            value: "4"
        }, {
            text: 'N/A',
            value: "5"
        }];

        //Date picker related scopes
        $scope.datePickers = {
            insurance: {
                opened: false
            },
            warranty: {
                opened: false
            },
            puc: {
                opened: false
            }
        };

        // Image Upload
        $scope.$watch('uploads', function(uploads) {
            $scope.formUpload = false;
            if (uploads != null) {
                for (var i = 0; i < uploads.length; i++) {
                    $scope.errorMsg = null;
                    (function(file) {
                        uploadFile(file);
                    })(uploads[i]);
                }
            }
        });


        $scope.removeImage = function(pic) {
            console.log(pic);
        }

        $scope.uploadFile = function(file) {
            console.log("uploading");
        };

        $scope.load = function(id) {
            Bike.get({
                id: id
            }, function(result) {
                $scope.bike = result.bike;
                $scope.bikeDetail = result.bikeDetail;
                $scope.owner = result.owner;
            });
        };

        // Instantiate the bikeDetail with default values
        var initModel = function(graph, defaultValue) {
            var split = graph.split('.'),
                obj = $scope.bikeDetail.report;

            for (var i = 0; i < split.length; i++) {
                if (!obj[split[i]]) {
                    obj[split[i]] = {};
                }
                obj = obj[split[i]];
            };
            obj.value = defaultValue;
            return obj;
        };

        $scope.save = function() {
            $scope.bike.brand = $scope.bike.model.brandName;
            $scope.bike.name = $scope.bike.model.name;
            $scope.bike.engineCapacity = $scope.bike.model.engineCapacity;
            $scope.bike.brandId = $scope.bike.model.brandId;
            if ($scope.bike.id != null) {
                Bike.update({
                        bike: $scope.bike,
                        bikeDetail: $scope.bikeDetail,
                        owner: $scope.owner
                    },
                    function() {
                        $state.go('bike');
                    });
            } else {
                Bike.save({
                        bike: $scope.bike,
                        bikeDetail: $scope.bikeDetail,
                        owner: $scope.owner
                    },
                    function() {
                        // navigate back to listing page
                        $state.go('bike');
                    });
            }
        };

        $scope.getModels = function(value) {
            Model.search({
                name: value
            }, function(result) {
                $scope.models = result;
            });
            return $scope.models;
        };

        $scope.formatValue = function(value) {
            if (value) {
                return value.brandName + ' - ' + value.name + ' ' + value.engineCapacity + 'cc';
            }
        };

        $scope.openDatePicker = function($event, datePickerDate) {
            $event.preventDefault();
            $event.stopPropagation();

            for (var datePicker in $scope.datePickers) {
                $scope.datePickers[datePicker].opened = false;
            }
            $scope.datePickers[datePickerDate].opened = true;
        };

        // The report. Used to generated the HTML
        $scope.report = {
            suspension: {
                text: 'Suspension',
                attr: {
                    front: {
                        text: 'Front'
                    },
                    rear: {
                        text: 'Rear'
                    },
                    forkAssembly: {
                        text: 'Fork Assembly'
                    }
                }
            },
            wheel: {
                text: 'Wheel Section',
                attr: {
                    front: {
                        text: 'Front Tyre',
                        defaultValue: '2'
                    },
                    rear: {
                        text: 'Rear Tyre',
                        defaultValue: '2'
                    },
                    frontWheel: {
                        text: 'Front Wheel',
                        defaultValue: '2'
                    },
                    rearWheel: {
                        text: 'Rear Wheel',
                        defaultValue: '2'
                    },
                    frontMudgaurd: {
                        text: 'Front Mudgaurd'
                    },
                    rearMudgaurd: {
                        text: 'Rear Mudgaurd'
                    }
                }
            },
            tank: {
                text: 'Tank Section',
                attr: {
                    fuelTank: {
                        text: 'Fuel Tank'
                    },
                    rightSideCover: {
                        text: 'Right Side Tank Cover'
                    },
                    leftSideCover: {
                        text: 'Left Side Tank Cover'
                    }
                }
            },
            lower: {
                text: 'Lower Section',
                attr: {
                    gearShifter: {
                        text: 'Gear Shifter'
                    },
                    kick: {
                        text: 'Kick'
                    },
                    sideStand: {
                        text: 'Side Stand'
                    },
                    silencerCover: {
                        text: 'Silence Cover'
                    },
                    silencer: {
                        text: 'Silencer'
                    },
                    brakePedal: {
                        text: 'Brake Pedal'
                    },
                    mainStand: {
                        text: 'Main Stand'
                    },
                    engineBelly: {
                        text: 'Engine Belly'
                    },
                    chainCover: {
                        text: 'Chain Cover'
                    }
                }
            },
            underSeat: {
                text: 'Under Seat Section',
                attr: {
                    seat: {
                        text: 'Seat'
                    },
                    leftSidePanel: {
                        text: 'Left Side Panel'
                    },
                    rightSidePanel: {
                        text: 'Right Side Panel'
                    },
                    swingArm: {
                        text: 'Swing Arm'
                    },
                    battery: {
                        text: 'Battery'
                    },
                    reflector: {
                        text: 'Reflector'
                    }
                }
            },
            handle: {
                text: 'Handle Section',
                attr: {
                    handleBar: {
                        text: 'Handle Bar'
                    },
                    clutchLever: {
                        text: 'Clutch Lever'
                    },
                    leftMirror: {
                        text: 'Left Mirror'
                    },
                    selfStart: {
                        text: 'Self Start'
                    },
                    horn: {
                        text: 'Horn'
                    },
                    grabHandle: {
                        text: 'Grab Handle'
                    },
                    brakeLever: {
                        text: 'Brake Lever'
                    },
                    rightMirror: {
                        text: 'Right Mirror'
                    },
                    ignitionKey: {
                        text: 'Ignition Key'
                    },
                    switches: {
                        text: 'Switches'
                    }
                }
            },
            headlight: {
                text: 'Headlight Section',
                attr: {
                    headlightFairing: {
                        text: 'Headlight Fairing'
                    },
                    windScreen: {
                        text: 'Wind Screen'
                    }
                }
            },
            lights: {
                text: 'Lights Section',
                attr: {
                    headlight: {
                        text: 'Headlight'
                    },
                    frontLeftIndicator: {
                        text: 'Front Left Indicator'
                    },
                    rearLeftIndicator: {
                        text: 'Rear Left Indicator'
                    },
                    tailLights: {
                        text: 'Tail Lights'
                    },
                    frontRightIndicator: {
                        text: 'Front Right Indicator'
                    },
                    rearRightIndicator: {
                        text: 'Rear Right Indicator'
                    }
                }
            },
            meter: {
                text: 'Meter Section',
                attr: {
                    fuelGauge: {
                        text: 'Fuel Gauge'
                    },
                    speedometer: {
                        text: 'Speedometer'
                    },
                    tachometer: {
                        text: 'Tachometer'
                    },
                    odometer: {
                        text: 'Odometer'
                    },
                    meterLights: {
                        text: 'Meter Lights'
                    }
                }
            },
            performance: {
                text: 'Performance',
                attr: {
                    driverComfort: {
                        text: 'Driver Comfort',
                        values: [{
                            text: 'Comfortable',
                            value: '1'

                        }, {
                            text: 'Hard',
                            value: '3'

                        }, {
                            text: 'Jerky',
                            value: '4'

                        }]
                    },
                    clutchOperation: {
                        text: 'Clutch Operation'
                    },
                    bodyRattlingNoise: {
                        text: 'Body Rattling Noise'
                    },
                    transmission: {
                        text: 'Transmission'
                    },
                    acceleratorOperation: {
                        text: 'Accelerator Operation'
                    },
                    enginePerformance: {
                        text: 'Engine Performance'
                    },
                    engineOilLevel: {
                        text: 'Engine Oil Level',
                        values: [{
                            text: 'Appropriate',
                            value: '1'
                        }, {
                            text: 'More than Miniumum',
                            value: '2'
                        }, {
                            text: 'Less than Miniumum',
                            value: '3'
                        }, {
                            text: 'N/A',
                            value: '5'
                        }]
                    },
                    engineOilCheck: {
                        text: 'Engine Oil Check',
                        values: [{
                            text: 'No Leakage',
                            value: '1'
                        }, {
                            text: 'Minor Leakage',
                            value: '3'
                        }, {
                            text: 'Major Oil Leakage',
                            value: '4'
                        }, {
                            text: 'N/A',
                            value: '5'
                        }]
                    },
                    silencer: {
                        text: 'Silencer'
                    },
                    chainDriven: {
                        text: 'Chain Driven'
                    },
                    chassisAlignment: {
                        text: 'Chassis Alignment'
                    },
                    frontBrakingSystem: {
                        text: 'Front Braking System'
                    },
                    rearBrakingSystem: {
                        text: 'Rear Braking System'
                    }
                }
            }
        };

        // Load the bike details using the id
        if ($stateParams.id) {
            $scope.load($stateParams.id);
        } else {
            // Instantiate the model so that values are preselected
            for (var section in $scope.report) {
                for (var part in $scope.report[section].attr) {
                    initModel(section + "." + part, $scope.report[section].attr[part].defaultValue || '1');
                }
            }
        }
    });