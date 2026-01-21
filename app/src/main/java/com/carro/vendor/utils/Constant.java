package com.carro.vendor.utils;

public class Constant {

    public static final String CARRO_RENTAL = "carrorentalvendor";

    public static final String ddMMyyyy = "dd-MM-yyyy";
    public static final String yyyyMMdd = "yyyy-MM-dd";
    public static final String HHMMSSA = "hh:mm a";
    public static final String HHMMSS = "hh:mm:ss";
    public static final String yyyyMMdd_HHmmss = "yyyy-MM-dd HH:mm:ss";

    public static String FILENAME = "file";

    public static final String SUCCESS_RESPONSE_CODE = "200";
    public static final String SUCCESS_RESPONSE = "success";

    public static String WEBVIEW_TITLE = "";
    public static String WEBVIEW_URL = "";
    public static String QUICKEKYC_API_KEY = "d2ebfb20-199d-4ed1-8c82-9cba3f1614a0";


    public interface BundleExtras {
        String PHONE_NUMBER = "phone_number";
        String WAY_TYPE = "wayType";
        String WALLET_AMOUNT = "wallet_amount";
        String CAR_DETAIL = "car_details";
        String CAR_TYPE = "car_type";

    }

    public interface PreferenceConstant {

        String IS_LOGIN = "isLogin";
        String USER_DATA = "user_data";
        String USER_ID = "user_id";
        String FIREBASE_TOKEN = "firebase_token";

    }

    public interface ApiKey {

        String USER_MOBILE = "user_mobile";
        String USER_ALT_MOBILE = "user_alt_mobile";
        String USER_ID = "user_id";
        String CAR_ID = "car_id";
        String OTP = "otp";

        String AADHAR_NO = "adhar_no";
        String USER_NAME = "user_name";
        String USER_EMAIL = "user_email";
        String USER_GENDER = "user_gender";
        String USER_ADDRESS = "user_address";
        String USER_ADHAR_NO = "user_adhar_no";
        String USER_LIC_NO = "user_lic_no";
        String USER_PAN_NO = "user_pan_no";
        String USER_VEHICLE_TYPE = "user_vehicle_type";
        String USER_REG_NO = "user_reg_no";
        String USER_PIC = "user_pic";
        String USER_BRANCH = "user_branch";
        String USER_STATE = "user_state";
        String USER_CITY = "user_city";
        String USER_PINCODE = "user_pincode";
        String PAN_IMG = "pan_img";
        String ADHAR_FRONT = "adhar_front";
        String ADHAR_BACK = "adhar_back";
        String LIC_IMG = "lic_img";
        String CAR_BRAND = "car_brand";
        String CAR_NAME = "car_name";
        String CAR_TITLE= "car_title";
        String CAR_NUMBER = "car_number";
        String CAR_FUEL = "car_fuel";
        String CAR_SEATS = "car_seat";
        String CAR_IMG = "car_img";
        String CAR_MODEL = "car_model";
        String CAR_TYPE = "car_type";
        String CAR_STATUS = "car_status";
        String CAR_TC = "car_tc";
        String CAR_EXCLUSION = "car_exclusion";
        String CAR_INCLUSION = "car_inclusion";
        String CAR_BRANCH = "car_branch";
        String CAR_DRIVER_TYPE = "car_drivetype";
        String CAR_PRICE = "car_price";
        String CAR_AC = "car_AC";
        String CAR_LUGGAGE = "car_luggage";
        String CAR_SERV_TYPE = "car_servtype";
        String BRAND = "brand";
        String DRIVER_NAME = "driver_name";
        String DRIVER_MOBILE = "driver_mobile";
        String LIC_EXP_DATE = "lic_expdate";
        String DRIVER_ID = "driver_id";
        String EXP_DATE = "exp_date";
        String REG_CERTI = "reg_certi";
        String ISS_CERTI = "iss_certi";
        String PERMIT = "permit";
        String OWNERCAR_IMG = "ownercar_img";
        String DRIVER_IMAGE = "driver_img";
        String LIC_IMAGE = "lic_img";
        String BKING_ID = "bking_id";
        String AMOUNT = "amount";
        String BANK_HOLDER_NAME = "bank_holder_name";
        String BANK_NAME = "bank_name";
        String BANK_ACC_NO = "bank_acc_no";
        String BANK_IFSC_CODE = "bank_ifsc_code";
        String COUNTRY_CODE = "country_code";
        String STATE_CODE = "state_code";
        String BANK_UPI = "bank_upi";
        String BANK_PP = "bank_pp";
        String BANK_GP = "bank_gp";
        String USER_BUSINESS_NAME = "user_business_name";
        String USER_BUSINESS_ADDRESS = "user_business_address";
        String USER_BUSINESS_EMAIL = "user_business_email";
        String USER_BUSINESS_PHONE = "user_business_phone";
        String USER_BUSINESS_LIC_NO = "user_business_lic_no";
        String USER_BUSINESS_LIC_FRONT = "b_lic_img_front";
        String USER_BUSINESS_LIC_BACK = "b_lic_img_back";
        String FCM_TOKEN = "fcmtoken";

    }

    public interface EndPoint {

        String SIGNUP_USER = "signup_user";
        String SEND_OTP = "send_otp";
        String VERIFY_OTP = "verify_otp";
        String UPDATE_PROFILE = "update_profile";
        String UPDATE_BASIC_PROFILE = "update_basic_details";
        String USER_DETAILS = "user_details";
        String CAR = "car";
        String DRIVER = "driver";
        String CAR_TYPE = "cartype";
        String BRAND = "brand";
        String MODEL = "model";
        String INSERT_CAR = "insert_car";
        String INSERT_DRIVER = "insert_driver";
        String SEND_DRIVER_OTP = "send_driver_otp";
        String VERIFY_DRIVER_OTP = "verify_driver_otp";
        String NOTIFICATION = "notification";
        String UPDATE_NOTIFY_BOOKING = "update_notify_booking";
        String UPDATE_DRIVER = "update_driver";
        String UPD_REG_CERTI = "upd_reg_certi";
        String UPD_ISS_CERTI = "upd_iss_certi";
        String UPD_CAR_PERMIT = "upd_car_permit";
        String UPD_OWNER_CAR = "upd_ownercar";
        String WALLET = "wallet";
        String COUNT_WALLET = "countWallet";
        String NEW_BOOKING = "new_booking";
        String ACTIVE_BOOKING = "active_booking";
        String UPDATE_CAR_DRIVER = "update_car_driver";
        String INSERT_WALLET = "insert_wallet";
        String UPDATE_BANK_DETAILS = "update_bank_details";
        String UPDATE_DOC_DETAILS = "update_doc_details";
        String UPDATE_BUSINESS_DETAILS = "update_business_details";
        String RECENT_PAID_WALLET = "recent_paid_wallet";
        String CANCEL_BOOKING = "cancel_booking";
        String COMPLETE_BOOKING = "complete_booking";
        String BRANCH = "branch";
        String GET_STATE_CITIES = "get_state_cities";
        String GET_COUNTRY_STATE = "get_country_state";
        String SLIDER = "slider";
        String HOME_PAGE = "home_page";
        String UPDATE_CAR_DOCUMENTS = "update_car_documents";

        String UPDATE_FCM = "update_fcm";

        String DELETE_NOTIFICATION = "delete_notification";

        String ADVERTISE = "advertise";

        String CHECK_BLOCK_WITH_ID = "check_block_with_id";


        String AADHAAR_GENERATE_OTP = "aadhaar-v2/generate-otp";
        String  RC_FULL = "rc/rc-full";
        String AADHAAR_SUBMIT_OTP = "aadhaar-v2/submit-otp";

        String UPDATE_ADHAR = "update_adhar";

        String DRIVING_LICENSE  = "driving-license/driving-license";
    }
}
