package com.carro.vendor.api;

import com.carro.vendor.api.response.BookingListResponse;
import com.carro.vendor.api.response.BranchResponse;
import com.carro.vendor.api.response.CarBrandResponse;
import com.carro.vendor.api.response.CarListResponse;
import com.carro.vendor.api.response.CarNameResponse;
import com.carro.vendor.api.response.CarTypeResponse;
import com.carro.vendor.api.response.CityResponse;
import com.carro.vendor.api.response.CreateOrderResponse;
import com.carro.vendor.api.response.DriverListResponse;
import com.carro.vendor.api.response.DriverOtpResponse;
import com.carro.vendor.api.response.LoginResponse;
import com.carro.vendor.api.response.MarkAllReadResponse;
import com.carro.vendor.api.response.NotificationResponse;
import com.carro.vendor.api.response.RecommendedResponse;
import com.carro.vendor.api.response.SliderResponse;
import com.carro.vendor.api.response.StateResponse;
import com.carro.vendor.api.response.UserDetailResponse;
import com.carro.vendor.api.response.WalletCountResponse;
import com.carro.vendor.api.response.WalletTransResponse;
import com.carro.vendor.api.response.commonResponse.BaseResponse;
import com.carro.vendor.model.AadharOtpSendModel;
import com.carro.vendor.model.AadharVerificationModel;
import com.carro.vendor.model.AdvertiseModel;
import com.carro.vendor.model.CheckBlockModel;
import com.carro.vendor.model.DLVerificationModel;
import com.carro.vendor.model.HomeData;
import com.carro.vendor.model.RcDataModel;
import com.carro.vendor.utils.Constant;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {


    @FormUrlEncoded
    @POST(Constant.EndPoint.SIGNUP_USER)
    Call<LoginResponse> user_login(
            @Field(Constant.ApiKey.USER_MOBILE) String user_mobile
    );

    @FormUrlEncoded
    @POST(Constant.EndPoint.SEND_OTP)
    Call<BaseResponse> send_OTP(
            @Field(Constant.ApiKey.USER_MOBILE) String user_mobile
    );

    @FormUrlEncoded
    @POST(Constant.EndPoint.VERIFY_OTP)
    Call<BaseResponse> verify_OTP(
            @Field(Constant.ApiKey.USER_ID) String user_id,
            @Field(Constant.ApiKey.OTP) String otp
    );

    @FormUrlEncoded
    @POST(Constant.EndPoint.USER_DETAILS)
    Call<UserDetailResponse> user_details(
            @Field(Constant.ApiKey.USER_ID) String user_id
    );



    @FormUrlEncoded
    @POST(Constant.EndPoint.UPDATE_FCM)
    Call<BaseResponse> updateFCM(
            @Field(Constant.ApiKey.USER_ID) String user_id,
            @Field(Constant.ApiKey.FCM_TOKEN) String fcmtoken
    );

    @FormUrlEncoded
    @POST(Constant.EndPoint.CAR)
    Call<CarListResponse> car(
            @Field(Constant.ApiKey.USER_ID) String user_id
    );

    @FormUrlEncoded
    @POST(Constant.EndPoint.DRIVER)
    Call<DriverListResponse> driver(
            @Field(Constant.ApiKey.USER_ID) String user_id
    );




    @POST(Constant.EndPoint.BRAND)
    Call<CarBrandResponse> carBrand();

    @POST(Constant.EndPoint.CAR_TYPE)
    Call<CarTypeResponse> carType();

    @FormUrlEncoded
    @POST(Constant.EndPoint.MODEL)
    Call<CarNameResponse> carModel(
            @Field(Constant.ApiKey.BRAND) String brand
    );
/*
    @Multipart
    @POST(Constant.EndPoint.UPDATE_PROFILE)
    Call<LoginResponse> update_profile(
            @Part(Constant.ApiKey.USER_ID) RequestBody user_id,
            @Part(Constant.ApiKey.USER_NAME) RequestBody user_name,
            @Part(Constant.ApiKey.USER_EMAIL) RequestBody user_email,
            @Part(Constant.ApiKey.USER_ADDRESS) RequestBody user_address,
            @Part(Constant.ApiKey.USER_ADHAR_NO) RequestBody user_adhar_no,
            @Part(Constant.ApiKey.USER_PAN_NO) RequestBody user_pan_no,
            @Part(Constant.ApiKey.USER_PINCODE) RequestBody user_pincode,
            @Part(Constant.ApiKey.USER_ALT_MOBILE) RequestBody user_alt_mobile,
            @Part(Constant.ApiKey.USER_BRANCH) RequestBody user_branch,
            @Part(Constant.ApiKey.USER_STATE) RequestBody user_state,
            @Part(Constant.ApiKey.USER_CITY) RequestBody user_city,
            @Part MultipartBody.Part user_pic,
            @Part MultipartBody.Part adhar_front,
            @Part MultipartBody.Part adhar_back,
            @Part MultipartBody.Part pan_img
    );*/
    @Multipart
    @POST(Constant.EndPoint.UPDATE_PROFILE)
    Call<LoginResponse> update_profile(
            @Part(Constant.ApiKey.USER_ID) RequestBody user_id,
            @Part(Constant.ApiKey.USER_NAME) RequestBody user_name,
            @Part(Constant.ApiKey.USER_EMAIL) RequestBody user_email,
            @Part(Constant.ApiKey.USER_PAN_NO) RequestBody user_pan_no,
            @Part(Constant.ApiKey.USER_ALT_MOBILE) RequestBody user_alt_mobile,
            @Part(Constant.ApiKey.USER_BRANCH) RequestBody user_branch,
            @Part MultipartBody.Part user_pic
    );


    @Multipart
    @POST(Constant.EndPoint.UPDATE_BASIC_PROFILE)
    Call<LoginResponse> update_basic_details(
            @Part(Constant.ApiKey.USER_ID) RequestBody user_id,
            @Part(Constant.ApiKey.USER_NAME) RequestBody user_name,
            @Part(Constant.ApiKey.USER_EMAIL) RequestBody user_email,
            @Part(Constant.ApiKey.USER_ALT_MOBILE) RequestBody user_alt_mobile,
//            @Part(Constant.ApiKey.USER_STATE) RequestBody user_state,
//            @Part(Constant.ApiKey.USER_CITY) RequestBody user_city,
//            @Part(Constant.ApiKey.USER_ADDRESS) RequestBody user_address,
//            @Part(Constant.ApiKey.USER_PINCODE) RequestBody user_pincode,
            @Part MultipartBody.Part user_pic
    );

  /*  @Multipart
    @POST(Constant.EndPoint.INSERT_CAR)
    Call<BaseResponse> insertCar(
            @Part(Constant.ApiKey.USER_ID) RequestBody user_id,
            @Part(Constant.ApiKey.CAR_ID) RequestBody car_id,
            @Part(Constant.ApiKey.CAR_TYPE) RequestBody car_type,
            @Part(Constant.ApiKey.CAR_DRIVER_TYPE) RequestBody car_drive_type,
            @Part(Constant.ApiKey.CAR_TITLE) RequestBody car_name,
            @Part(Constant.ApiKey.CAR_NUMBER) RequestBody car_number,
            @Part(Constant.ApiKey.CAR_FUEL) RequestBody car_fuel,
            @Part(Constant.ApiKey.CAR_AC) RequestBody car_ac,
            @Part(Constant.ApiKey.CAR_SEATS) RequestBody car_seat,
            @Part(Constant.ApiKey.CAR_LUGGAGE) RequestBody car_luggage,
            @Part MultipartBody.Part car_img
    );*/


  /*  @Multipart
    @POST(Constant.EndPoint.INSERT_DRIVER)
    Call<BaseResponse> insert_driver(
            @Part(Constant.ApiKey.DRIVER_NAME) RequestBody driver_name,
            @Part(Constant.ApiKey.DRIVER_MOBILE) RequestBody driver_mobile,
            @Part(Constant.ApiKey.LIC_EXP_DATE) RequestBody lic_expdate,
            @Part(Constant.ApiKey.USER_ID) RequestBody user_id,
            @Part MultipartBody.Part lic_img
    );*/

    @Multipart
    @POST(Constant.EndPoint.UPDATE_DRIVER)
    Call<BaseResponse> upd_driver(
            @Part(Constant.ApiKey.DRIVER_ID) RequestBody driver_id,
            @Part(Constant.ApiKey.DRIVER_NAME) RequestBody driver_name,
            @Part(Constant.ApiKey.DRIVER_MOBILE) RequestBody driver_mobile,
            @Part(Constant.ApiKey.LIC_EXP_DATE) RequestBody lic_expdate,
            @Part(Constant.ApiKey.USER_ID) RequestBody user_id,
            @Part MultipartBody.Part lic_img
    );
    @FormUrlEncoded
    @POST(Constant.EndPoint.SEND_DRIVER_OTP)
    Call<DriverOtpResponse> send_driver_otp(
            @Field(Constant.ApiKey.DRIVER_MOBILE) String driver_mobile
    );
    @FormUrlEncoded
    @POST(Constant.EndPoint.VERIFY_DRIVER_OTP)
    Call<BaseResponse> verify_driver_otp(
            @Field(Constant.ApiKey.DRIVER_ID) String driver_id,
            @Field(Constant.ApiKey.OTP) String otp
    );

    @FormUrlEncoded
    @POST(Constant.EndPoint.NOTIFICATION)
    Call<NotificationResponse> notification(
            @Field(Constant.ApiKey.USER_ID) String user_id
    );

    @FormUrlEncoded
    @POST(Constant.EndPoint.DELETE_NOTIFICATION)
    Call<BaseResponse> deleteNotification(
            @Field(Constant.ApiKey.USER_ID) String user_id
    );

    @FormUrlEncoded
    @POST(Constant.EndPoint.MARK_AS_READ_NOTIFICATION) // Add this in your Constant.EndPoint
    Call<MarkAllReadResponse> markAllNotificationsRead(
            @Field(Constant.ApiKey.USER_ID) String user_id
    );

    @FormUrlEncoded
    @POST(Constant.EndPoint.UPDATE_NOTIFY_BOOKING)
    Call<BaseResponse> update_notify_booking(
            @Field(Constant.ApiKey.USER_ID) String user_id,
            @Field(Constant.ApiKey.BKING_ID) String bking_id
    );

    @Multipart
    @POST(Constant.EndPoint.UPDATE_DRIVER)
    Call<BaseResponse> update_driver(
            @Part(Constant.ApiKey.DRIVER_ID) RequestBody driver_id,

            @Part MultipartBody.Part driver_img,
            @Part(Constant.ApiKey.EXP_DATE) RequestBody exp_date
    );

    @Multipart
    @POST(Constant.EndPoint.UPD_REG_CERTI)
    Call<BaseResponse> upd_reg_certi(
            @Part(Constant.ApiKey.CAR_ID) RequestBody car_id,
            @Part MultipartBody.Part reg_certi
    );
    @Multipart
    @POST(Constant.EndPoint.UPD_ISS_CERTI)
    Call<BaseResponse> upd_iss_certi(
            @Part(Constant.ApiKey.CAR_ID) RequestBody car_id,
            @Part MultipartBody.Part iss_certi
    );
    @Multipart
    @POST(Constant.EndPoint.UPD_CAR_PERMIT)
    Call<BaseResponse> upd_car_permit(
            @Part(Constant.ApiKey.CAR_ID) RequestBody car_id,
            @Part MultipartBody.Part permit
    );
    @Multipart
    @POST(Constant.EndPoint.UPD_OWNER_CAR)
    Call<BaseResponse> upd_ownercar(
            @Part(Constant.ApiKey.CAR_ID) RequestBody car_id,
            @Part MultipartBody.Part ownercar_img
    );

    @FormUrlEncoded
    @POST(Constant.EndPoint.WALLET)
    Call<WalletTransResponse> wallet(
            @Field(Constant.ApiKey.USER_ID) String user_id
    );
    @FormUrlEncoded
    @POST(Constant.EndPoint.COUNT_WALLET)
    Call<WalletCountResponse> countWallet(
            @Field(Constant.ApiKey.USER_ID) String user_id
    );
    @FormUrlEncoded
    @POST(Constant.EndPoint.NEW_BOOKING)
    Call<BookingListResponse>new_booking(
            @Field(Constant.ApiKey.USER_ID) String user_id
    );

    @FormUrlEncoded
    @POST(Constant.EndPoint.ACTIVE_BOOKING)
    Call<BookingListResponse>active_booking(
            @Field(Constant.ApiKey.USER_ID) String user_id
    );

    @FormUrlEncoded
    @POST(Constant.EndPoint.UPDATE_CAR_DRIVER)
    Call<BaseResponse>update_car_driver(
            @Field(Constant.ApiKey.CAR_ID) String car_id,
            @Field(Constant.ApiKey.DRIVER_ID) String driver_id,
            @Field(Constant.ApiKey.BKING_ID) String bking_id
    );
    @FormUrlEncoded
    @POST(Constant.EndPoint.CANCEL_BOOKING)
    Call<BaseResponse>cancel_booking(
            @Field(Constant.ApiKey.BKING_ID) String bking_id
    );

    @FormUrlEncoded
    @POST(Constant.EndPoint.COMPLETE_BOOKING)
    Call<BaseResponse>complete_booking(
            @Field(Constant.ApiKey.BKING_ID) String bking_id
    );


    @FormUrlEncoded
    @POST(Constant.EndPoint.INSERT_WALLET)
    Call<BaseResponse>insert_wallet(
            @Field(Constant.ApiKey.USER_ID) String user_id,
            @Field(Constant.ApiKey.AMOUNT) String amount
    );
    @FormUrlEncoded
    @POST(Constant.EndPoint.UPDATE_BANK_DETAILS)
    Call<BaseResponse>update_bank_details(
            @Field(Constant.ApiKey.USER_ID) String user_id,
            @Field(Constant.ApiKey.BANK_HOLDER_NAME) String bank_holder_name,
            @Field(Constant.ApiKey.BANK_NAME) String bank_name,
            @Field(Constant.ApiKey.BANK_ACC_NO) String bank_acc_no,
            @Field(Constant.ApiKey.BANK_IFSC_CODE) String bank_ifsc_code,
            @Field(Constant.ApiKey.BANK_UPI) String bank_upi,
            @Field(Constant.ApiKey.BANK_PP) String bank_pp,
            @Field(Constant.ApiKey.BANK_GP) String bank_gp
    );



    @Multipart
    @POST(Constant.EndPoint.UPDATE_DOC_DETAILS)
    Call<BaseResponse> update_doc_details(
            @Part(Constant.ApiKey.USER_ID) RequestBody user_id,
            @Part(Constant.ApiKey.USER_ADHAR_NO) RequestBody user_adhar_no,
            @Part(Constant.ApiKey.USER_PAN_NO) RequestBody user_pan_no,
            @Part MultipartBody.Part adhar_front,
            @Part MultipartBody.Part adhar_back,
            @Part MultipartBody.Part pan_img
    );

    @Multipart
    @POST(Constant.EndPoint.UPDATE_BUSINESS_DETAILS)
    Call<BaseResponse> update_business_details(
            @Part(Constant.ApiKey.USER_ID) RequestBody user_id,
            @Part(Constant.ApiKey.USER_BUSINESS_NAME) RequestBody user_business_name,
            @Part(Constant.ApiKey.USER_BUSINESS_ADDRESS) RequestBody user_business_address,
            @Part(Constant.ApiKey.USER_BUSINESS_EMAIL) RequestBody user_business_email,
            @Part(Constant.ApiKey.USER_BUSINESS_PHONE) RequestBody user_business_phone,
            @Part(Constant.ApiKey.USER_BUSINESS_LIC_NO) RequestBody user_business_lic_no,
            @Part MultipartBody.Part b_lic_img_front,
            @Part MultipartBody.Part b_lic_img_back
    );

    @FormUrlEncoded
    @POST(Constant.EndPoint.RECENT_PAID_WALLET)
    Call<RecommendedResponse>recent_paid_wallet(
            @Field(Constant.ApiKey.USER_ID) String user_id
    );

    @FormUrlEncoded
    @POST(Constant.EndPoint.GET_COUNTRY_STATE)
    Call<StateResponse> get_country_state(
            @Field(Constant.ApiKey.COUNTRY_CODE) String country_code
    );
    @FormUrlEncoded
    @POST(Constant.EndPoint.GET_STATE_CITIES)
    Call<CityResponse> get_state_cities(
            @Field(Constant.ApiKey.COUNTRY_CODE) String country_code,
            @Field(Constant.ApiKey.STATE_CODE) String state_code
    );

    @POST(Constant.EndPoint.BRANCH)
    Call<BranchResponse> get_branch();

    @POST(Constant.EndPoint.SLIDER)
    Call<SliderResponse> get_slider();


    @Multipart
    @POST(Constant.EndPoint.UPDATE_CAR_DOCUMENTS)
    Call<BaseResponse> uploadCarDocuments(
            @Part(Constant.ApiKey.CAR_ID) RequestBody car_id,
            @Part MultipartBody.Part reg_certi,
            @Part MultipartBody.Part iss_certi,
            @Part MultipartBody.Part permit,
            @Part MultipartBody.Part ownercar_img
    );

    @POST(Constant.EndPoint.ADVERTISE)
    Call<AdvertiseModel> get_advertise();


    @FormUrlEncoded
    @POST(Constant.EndPoint.CHECK_BLOCK_WITH_ID)
    Call<CheckBlockModel> check_block_with_id(
            @Field(Constant.ApiKey.USER_ID) String user_id
    );


    @POST(Constant.EndPoint.HOME_PAGE)
    Call<HomeData> home_page();


    @POST(Constant.EndPoint.AADHAAR_GENERATE_OTP)
    Call<AadharOtpSendModel> aadhaar_generate_otp(
            @Body RequestBody body
    );

    @POST(Constant.EndPoint.RC_FULL)
    Call<RcDataModel> rc_full(
            @Body RequestBody body
    );

    @POST(Constant.EndPoint.AADHAAR_SUBMIT_OTP)
    Call<AadharVerificationModel> aadhaar_submit_otp(
            @Body RequestBody body
    );

    @FormUrlEncoded
    @POST(Constant.EndPoint.UPDATE_ADHAR)
    Call<BaseResponse> update_adhar(
            @Field(Constant.ApiKey.USER_ID) String user_id,
            @Field(Constant.ApiKey.AADHAR_NO) String aadhar_no,
            @Field("adhar_name") String adhar_name,
            @Field("adhar_dob") String adhar_dob,
            @Field("adhar_add") String adhar_add,
            @Field("adhar_gender") String adhar_gender,
            @Field("adhar_zipcode") String adhar_zipcode
    );

    @POST(Constant.EndPoint.DRIVING_LICENSE)
    Call<DLVerificationModel> driving_license(
            @Body RequestBody body
    );


    @FormUrlEncoded
    @POST(Constant.EndPoint.INSERT_DRIVER)
    Call<BaseResponse> insert_driver(
            @Field(Constant.ApiKey.USER_ID) String user_id,
            @Field(Constant.ApiKey.DRIVER_NAME) String driver_name,
            @Field(Constant.ApiKey.DRIVER_MOBILE) String driver_mobile,
            @Field("dl_no") String dl_no,
            @Field("dl_name") String dl_name,
            @Field("dl_temp_add") String dl_temp_add,
            @Field("dl_temp_zip") String dl_temp_zip,
            @Field("dl_per_add") String dl_per_add,
            @Field("dl_per_zip") String dl_per_zip,
            @Field("dl_state") String dl_state,
            @Field("dl_olacode") String dl_olacode,
            @Field("dl_olaname") String dl_olaname,
            @Field("dl_gender") String dl_gender,
            @Field("dl_fh_name") String dl_fh_name,
            @Field("dl_dob") String dl_dob,
            @Field("dl_blood") String dl_blood,
            @Field("dl_veh_class") List<String> dl_veh_class,
            @Field("dl_cur_status") String dl_cur_status,
            @Field("dl_isdate") String dl_isdate,
            @Field("dl_exdate") String dl_exdate
    );


    @FormUrlEncoded
    @POST(Constant.EndPoint.INSERT_CAR)
    Call<BaseResponse> insertCar(
            @Field(Constant.ApiKey.USER_ID) String user_id,
            @Field(Constant.ApiKey.CAR_TYPE) String car_type,
            @Field(Constant.ApiKey.CAR_TITLE) String car_name,
            @Field(Constant.ApiKey.CAR_NUMBER) String car_number,
            @Field(Constant.ApiKey.CAR_FUEL) String car_fuel

    );

    @FormUrlEncoded
    @POST(Constant.EndPoint.CREATE_ORDER_TEST) // <-- IMPORTANT: USE YOUR ACTUAL SERVER URL
    Call<CreateOrderResponse> createRazorpayOrderTest(
            @Field("payable_amount") String amount
    );
}
