package com.carro.vendor.model;

import com.google.gson.annotations.SerializedName;

public class RcDataModel{

	@SerializedName("status_code")
	private int statusCode;

	@SerializedName("data")
	private Data data;

	@SerializedName("message")
	private String message;

	@SerializedName("request_id")
	private int requestId;

	@SerializedName("status")
	private String status;

	public int getStatusCode(){
		return statusCode;
	}

	public Data getData(){
		return data;
	}

	public String getMessage(){
		return message;
	}

	public int getRequestId(){
		return requestId;
	}

	public String getStatus(){
		return status;
	}

    public static class Data{

        @SerializedName("vehicle_category")
        private String vehicleCategory;

        @SerializedName("permit_number")
        private String permitNumber;

        @SerializedName("vehicle_category_description")
        private String vehicleCategoryDescription;

        @SerializedName("rc_status")
        private String rcStatus;

        @SerializedName("permanent_address")
        private String permanentAddress;

        @SerializedName("seat_capacity")
        private String seatCapacity;

        @SerializedName("tax_upto")
        private String taxUpto;

        @SerializedName("rto_code")
        private Object rtoCode;

        @SerializedName("maker_model")
        private String makerModel;

        @SerializedName("body_type")
        private String bodyType;

        @SerializedName("non_use_from")
        private Object nonUseFrom;

        @SerializedName("variant")
        private Object variant;

        @SerializedName("fuel_type")
        private String fuelType;

        @SerializedName("sleeper_capacity")
        private String sleeperCapacity;

        @SerializedName("vehicle_engine_number")
        private String vehicleEngineNumber;

        @SerializedName("owner_name")
        private String ownerName;

        @SerializedName("owner_number")
        private String ownerNumber;

        @SerializedName("manufacturing_date_formatted")
        private String manufacturingDateFormatted;

        @SerializedName("vehicle_gross_weight")
        private String vehicleGrossWeight;

        @SerializedName("financed")
        private boolean financed;

        @SerializedName("blacklist_status")
        private Object blacklistStatus;

        @SerializedName("registration_date")
        private String registrationDate;

        @SerializedName("wheelbase")
        private String wheelbase;

        @SerializedName("father_name")
        private String fatherName;

        @SerializedName("permit_valid_upto")
        private Object permitValidUpto;

        @SerializedName("cubic_capacity")
        private String cubicCapacity;

        @SerializedName("noc_details")
        private Object nocDetails;

        @SerializedName("national_permit_upto")
        private Object nationalPermitUpto;

        @SerializedName("financer")
        private String financer;

        @SerializedName("tax_paid_upto")
        private String taxPaidUpto;

        @SerializedName("rc_number")
        private String rcNumber;

        @SerializedName("fit_up_to")
        private String fitUpTo;

        @SerializedName("present_address")
        private String presentAddress;

        @SerializedName("standing_capacity")
        private String standingCapacity;

        @SerializedName("color")
        private String color;

        @SerializedName("vehicle_chasi_number")
        private String vehicleChasiNumber;

        @SerializedName("maker_description")
        private String makerDescription;

        @SerializedName("masked_name")
        private boolean maskedName;

        @SerializedName("manufacturing_date")
        private String manufacturingDate;

        @SerializedName("insurance_company")
        private String insuranceCompany;

        @SerializedName("unladen_weight")
        private String unladenWeight;

        @SerializedName("national_permit_number")
        private Object nationalPermitNumber;

        @SerializedName("pucc_number")
        private String puccNumber;

        @SerializedName("permit_valid_from")
        private Object permitValidFrom;

        @SerializedName("national_permit_issued_by")
        private Object nationalPermitIssuedBy;

        @SerializedName("insurance_upto")
        private String insuranceUpto;

        @SerializedName("insurance_policy_number")
        private String insurancePolicyNumber;

        @SerializedName("less_info")
        private boolean lessInfo;

        @SerializedName("registered_at")
        private String registeredAt;

        @SerializedName("permit_issue_date")
        private Object permitIssueDate;

        @SerializedName("challan_details")
        private Object challanDetails;

        @SerializedName("latest_by")
        private String latestBy;

        @SerializedName("permit_type")
        private Object permitType;

        @SerializedName("non_use_status")
        private Object nonUseStatus;

        @SerializedName("no_cylinders")
        private String noCylinders;

        @SerializedName("non_use_to")
        private Object nonUseTo;

        @SerializedName("norms_type")
        private String normsType;

        @SerializedName("pucc_upto")
        private String puccUpto;

        @SerializedName("mobile_number")
        private Object mobileNumber;

        public String getVehicleCategory(){
            return vehicleCategory;
        }

        public String getPermitNumber(){
            return permitNumber;
        }

        public String getVehicleCategoryDescription(){
            return vehicleCategoryDescription;
        }

        public String getRcStatus(){
            return rcStatus;
        }

        public String getPermanentAddress(){
            return permanentAddress;
        }

        public String getSeatCapacity(){
            return seatCapacity;
        }

        public String getTaxUpto(){
            return taxUpto;
        }

        public Object getRtoCode(){
            return rtoCode;
        }

        public String getMakerModel(){
            return makerModel;
        }

        public String getBodyType(){
            return bodyType;
        }

        public Object getNonUseFrom(){
            return nonUseFrom;
        }

        public Object getVariant(){
            return variant;
        }

        public String getFuelType(){
            return fuelType;
        }

        public String getSleeperCapacity(){
            return sleeperCapacity;
        }

        public String getVehicleEngineNumber(){
            return vehicleEngineNumber;
        }

        public String getOwnerName(){
            return ownerName;
        }

        public String getOwnerNumber(){
            return ownerNumber;
        }

        public String getManufacturingDateFormatted(){
            return manufacturingDateFormatted;
        }

        public String getVehicleGrossWeight(){
            return vehicleGrossWeight;
        }

        public boolean isFinanced(){
            return financed;
        }

        public Object getBlacklistStatus(){
            return blacklistStatus;
        }

        public String getRegistrationDate(){
            return registrationDate;
        }

        public String getWheelbase(){
            return wheelbase;
        }

        public String getFatherName(){
            return fatherName;
        }

        public Object getPermitValidUpto(){
            return permitValidUpto;
        }

        public String getCubicCapacity(){
            return cubicCapacity;
        }

        public Object getNocDetails(){
            return nocDetails;
        }

        public Object getNationalPermitUpto(){
            return nationalPermitUpto;
        }

        public String getFinancer(){
            return financer;
        }

        public String getTaxPaidUpto(){
            return taxPaidUpto;
        }

        public String getRcNumber(){
            return rcNumber;
        }

        public String getFitUpTo(){
            return fitUpTo;
        }

        public String getPresentAddress(){
            return presentAddress;
        }

        public String getStandingCapacity(){
            return standingCapacity;
        }

        public String getColor(){
            return color;
        }

        public String getVehicleChasiNumber(){
            return vehicleChasiNumber;
        }

        public String getMakerDescription(){
            return makerDescription;
        }

        public boolean isMaskedName(){
            return maskedName;
        }

        public String getManufacturingDate(){
            return manufacturingDate;
        }

        public String getInsuranceCompany(){
            return insuranceCompany;
        }

        public String getUnladenWeight(){
            return unladenWeight;
        }

        public Object getNationalPermitNumber(){
            return nationalPermitNumber;
        }

        public String getPuccNumber(){
            return puccNumber;
        }

        public Object getPermitValidFrom(){
            return permitValidFrom;
        }

        public Object getNationalPermitIssuedBy(){
            return nationalPermitIssuedBy;
        }

        public String getInsuranceUpto(){
            return insuranceUpto;
        }

        public String getInsurancePolicyNumber(){
            return insurancePolicyNumber;
        }

        public boolean isLessInfo(){
            return lessInfo;
        }

        public String getRegisteredAt(){
            return registeredAt;
        }

        public Object getPermitIssueDate(){
            return permitIssueDate;
        }

        public Object getChallanDetails(){
            return challanDetails;
        }

        public String getLatestBy(){
            return latestBy;
        }

        public Object getPermitType(){
            return permitType;
        }

        public Object getNonUseStatus(){
            return nonUseStatus;
        }

        public String getNoCylinders(){
            return noCylinders;
        }

        public Object getNonUseTo(){
            return nonUseTo;
        }

        public String getNormsType(){
            return normsType;
        }

        public String getPuccUpto(){
            return puccUpto;
        }

        public Object getMobileNumber(){
            return mobileNumber;
        }
    }
}