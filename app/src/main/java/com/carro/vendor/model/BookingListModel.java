package com.carro.vendor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingListModel {

    @SerializedName("m_bking_id")
    @Expose
    private String mBkingId;
    @SerializedName("m_booking_id")
    @Expose
    private String mBookingId;
    @SerializedName("m_bking_vendor")
    @Expose
    private String mBkingVendor;
    @SerializedName("m_bking_user")
    @Expose
    private String mBkingUser;
    @SerializedName("m_bking_username")
    @Expose
    private String mBkingUsername;
    @SerializedName("m_bking_useremail")
    @Expose
    private String mBkingUseremail;
    @SerializedName("m_bking_usermobile")
    @Expose
    private String mBkingUsermobile;
    @SerializedName("m_bking_road_type")
    @Expose
    private String mBkingRoadType;
    @SerializedName("m_bking_hour")
    @Expose
    private String mBkingHour;
    @SerializedName("m_bking_service_type")
    @Expose
    private String mBkingServiceType;
    @SerializedName("m_bking_from")
    @Expose
    private String mBkingFrom;
    @SerializedName("m_bking_to")
    @Expose
    private String mBkingTo;
    @SerializedName("m_bking_pickup")
    @Expose
    private String mBkingPickup;
    @SerializedName("m_bking_pickup_at")
    @Expose
    private String mBkingPickupAt;
    @SerializedName("m_bking_return")
    @Expose
    private String mBkingReturn;
    @SerializedName("m_bking_return_at")
    @Expose
    private String mBkingReturnAt;
    @SerializedName("m_bking_car_type")
    @Expose
    private String mBkingCarType;
    @SerializedName("m_bking_car")
    @Expose
    private String mBkingCar;
    @SerializedName("m_bking_driver")
    @Expose
    private String mBkingDriver;
    @SerializedName("m_bking_km")
    @Expose
    private String mBkingKm;
    @SerializedName("m_bking_price")
    @Expose
    private String mBkingPrice;
    @SerializedName("m_bking_total")
    @Expose
    private String mBkingTotal;
    @SerializedName("m_bking_paymode")
    @Expose
    private String mBkingPaymode;
    @SerializedName("m_bking_pay_status")
    @Expose
    private String mBkingPayStatus;
    @SerializedName("m_bking_status")
    @Expose
    private String mBkingStatus;
    @SerializedName("m_bking_addedon")
    @Expose
    private String mBkingAddedon;
    @SerializedName("m_bking_updatedon")
    @Expose
    private String mBkingUpdatedon;
    @SerializedName("m_car_id")
    @Expose
    private String mCarId;
    @SerializedName("m_car_name")
    @Expose
    private String mCarName;
    @SerializedName("m_car_number")
    @Expose
    private String mCarNumber;
    @SerializedName("m_car_type")
    @Expose
    private String mCarType;
    @SerializedName("m_car_brand")
    @Expose
    private String mCarBrand;
    @SerializedName("m_car_model")
    @Expose
    private String mCarModel;
    @SerializedName("m_car_vendor")
    @Expose
    private String mCarVendor;
    @SerializedName("m_car_fuel")
    @Expose
    private String mCarFuel;
    @SerializedName("m_car_img")
    @Expose
    private String mCarImg;
    @SerializedName("m_car_seat")
    @Expose
    private String mCarSeat;
    @SerializedName("m_car_reg_certi")
    @Expose
    private String mCarRegCerti;
    @SerializedName("m_car_iss_certi")
    @Expose
    private String mCarIssCerti;
    @SerializedName("m_car_permit")
    @Expose
    private String mCarPermit;
    @SerializedName("m_car_ownercar_img")
    @Expose
    private String mCarOwnercarImg;
    @SerializedName("m_car_status")
    @Expose
    private String mCarStatus;
    @SerializedName("m_car_addedon")
    @Expose
    private String mCarAddedon;
    @SerializedName("m_car_updatedon")
    @Expose
    private String mCarUpdatedon;
    @SerializedName("m_ctype_id")
    @Expose
    private String mCtypeId;
    @SerializedName("m_ctype_title")
    @Expose
    private String mCtypeTitle;
    @SerializedName("m_ctype_img")
    @Expose
    private String mCtypeImg;
    @SerializedName("m_ctype_seat")
    @Expose
    private String mCtypeSeat;
    @SerializedName("m_ctype_luggage")
    @Expose
    private String mCtypeLuggage;
    @SerializedName("m_ctype_AC")
    @Expose
    private String mCtypeAC;
    @SerializedName("m_ctype_price")
    @Expose
    private String mCtypePrice;
    @SerializedName("m_ctype_drivetype")
    @Expose
    private String mCtypeDrivetype;
    @SerializedName("m_ctype_servtype")
    @Expose
    private String mCtypeServtype;
    @SerializedName("m_ctype_inclusion")
    @Expose
    private String mCtypeInclusion;
    @SerializedName("m_ctype_exclusion")
    @Expose
    private String mCtypeExclusion;
    @SerializedName("m_ctype_tc")
    @Expose
    private String mCtypeTc;
    @SerializedName("m_ctype_status")
    @Expose
    private String mCtypeStatus;
    @SerializedName("m_ctype_addedon")
    @Expose
    private String mCtypeAddedon;
    @SerializedName("m_bking_type")
    @Expose
    private String mBkingType;

    @SerializedName("m_bking_type_cat")
    @Expose
    private String mBkingTypeCat;

    @SerializedName("m_bus_title")
    @Expose
    private String mBusTitle;
    @SerializedName("m_ctype_number")
    @Expose
    private String mCtypeNumber;

    public String getmBusTitle() {
        return mBusTitle;
    }

    public void setmBusTitle(String mBusTitle) {
        this.mBusTitle = mBusTitle;
    }

    public String getmBkingType() {
        return mBkingType;
    }

    public void setmBkingType(String mBkingType) {
        this.mBkingType = mBkingType;
    }

    public String getmBkingId() {
        return mBkingId;
    }

    public void setmBkingId(String mBkingId) {
        this.mBkingId = mBkingId;
    }

    public String getmBookingId() {
        return mBookingId;
    }

    public void setmBookingId(String mBookingId) {
        this.mBookingId = mBookingId;
    }

    public String getmBkingVendor() {
        return mBkingVendor;
    }

    public void setmBkingVendor(String mBkingVendor) {
        this.mBkingVendor = mBkingVendor;
    }

    public String getmBkingUser() {
        return mBkingUser;
    }

    public void setmBkingUser(String mBkingUser) {
        this.mBkingUser = mBkingUser;
    }

    public String getmBkingUsername() {
        return mBkingUsername;
    }

    public void setmBkingUsername(String mBkingUsername) {
        this.mBkingUsername = mBkingUsername;
    }

    public String getmBkingUseremail() {
        return mBkingUseremail;
    }

    public void setmBkingUseremail(String mBkingUseremail) {
        this.mBkingUseremail = mBkingUseremail;
    }

    public String getmBkingUsermobile() {
        return mBkingUsermobile;
    }

    public void setmBkingUsermobile(String mBkingUsermobile) {
        this.mBkingUsermobile = mBkingUsermobile;
    }

    public String getmBkingRoadType() {
        return mBkingRoadType;
    }

    public void setmBkingRoadType(String mBkingRoadType) {
        this.mBkingRoadType = mBkingRoadType;
    }

    public String getmBkingHour() {
        return mBkingHour;
    }

    public void setmBkingHour(String mBkingHour) {
        this.mBkingHour = mBkingHour;
    }

    public String getmBkingServiceType() {
        return mBkingServiceType;
    }

    public void setmBkingServiceType(String mBkingServiceType) {
        this.mBkingServiceType = mBkingServiceType;
    }

    public String getmBkingFrom() {
        return mBkingFrom;
    }

    public void setmBkingFrom(String mBkingFrom) {
        this.mBkingFrom = mBkingFrom;
    }

    public String getmBkingTo() {
        return mBkingTo;
    }

    public void setmBkingTo(String mBkingTo) {
        this.mBkingTo = mBkingTo;
    }


    public String getmBkingPickup() {
        return mBkingPickup;
    }

    public void setmBkingPickup(String mBkingPickup) {
        this.mBkingPickup = mBkingPickup;
    }

    public String getmBkingPickupAt() {
        return mBkingPickupAt;
    }

    public void setmBkingPickupAt(String mBkingPickupAt) {
        this.mBkingPickupAt = mBkingPickupAt;
    }

    public String getmBkingReturn() {
        return mBkingReturn;
    }

    public void setmBkingReturn(String mBkingReturn) {
        this.mBkingReturn = mBkingReturn;
    }

    public String getmBkingReturnAt() {
        return mBkingReturnAt;
    }

    public void setmBkingReturnAt(String mBkingReturnAt) {
        this.mBkingReturnAt = mBkingReturnAt;
    }

    public String getmBkingCarType() {
        return mBkingCarType;
    }

    public void setmBkingCarType(String mBkingCarType) {
        this.mBkingCarType = mBkingCarType;
    }

    public String getmBkingCar() {
        return mBkingCar;
    }

    public void setmBkingCar(String mBkingCar) {
        this.mBkingCar = mBkingCar;
    }

    public String getmBkingDriver() {
        return mBkingDriver;
    }

    public void setmBkingDriver(String mBkingDriver) {
        this.mBkingDriver = mBkingDriver;
    }

    public String getmBkingKm() {
        return mBkingKm;
    }

    public void setmBkingKm(String mBkingKm) {
        this.mBkingKm = mBkingKm;
    }

    public String getmBkingPrice() {
        return mBkingPrice;
    }

    public void setmBkingPrice(String mBkingPrice) {
        this.mBkingPrice = mBkingPrice;
    }

    public String getmBkingTotal() {
        return mBkingTotal;
    }

    public void setmBkingTotal(String mBkingTotal) {
        this.mBkingTotal = mBkingTotal;
    }

    public String getmBkingPaymode() {
        return mBkingPaymode;
    }

    public void setmBkingPaymode(String mBkingPaymode) {
        this.mBkingPaymode = mBkingPaymode;
    }

    public String getmBkingPayStatus() {
        return mBkingPayStatus;
    }

    public void setmBkingPayStatus(String mBkingPayStatus) {
        this.mBkingPayStatus = mBkingPayStatus;
    }

    public String getmBkingStatus() {
        return mBkingStatus;
    }

    public void setmBkingStatus(String mBkingStatus) {
        this.mBkingStatus = mBkingStatus;
    }

    public String getmBkingAddedon() {
        return mBkingAddedon;
    }

    public void setmBkingAddedon(String mBkingAddedon) {
        this.mBkingAddedon = mBkingAddedon;
    }

    public String getmBkingUpdatedon() {
        return mBkingUpdatedon;
    }

    public void setmBkingUpdatedon(String mBkingUpdatedon) {
        this.mBkingUpdatedon = mBkingUpdatedon;
    }

    public String getmCarId() {
        return mCarId;
    }

    public void setmCarId(String mCarId) {
        this.mCarId = mCarId;
    }

    public String getmCarName() {
        return mCarName;
    }

    public void setmCarName(String mCarName) {
        this.mCarName = mCarName;
    }

    public String getmCarNumber() {
        return mCarNumber;
    }

    public void setmCarNumber(String mCarNumber) {
        this.mCarNumber = mCarNumber;
    }

    public String getmCarType() {
        return mCarType;
    }

    public void setmCarType(String mCarType) {
        this.mCarType = mCarType;
    }

    public String getmCarBrand() {
        return mCarBrand;
    }

    public void setmCarBrand(String mCarBrand) {
        this.mCarBrand = mCarBrand;
    }

    public String getmCarModel() {
        return mCarModel;
    }

    public void setmCarModel(String mCarModel) {
        this.mCarModel = mCarModel;
    }

    public String getmCarVendor() {
        return mCarVendor;
    }

    public void setmCarVendor(String mCarVendor) {
        this.mCarVendor = mCarVendor;
    }

    public String getmCarFuel() {
        return mCarFuel;
    }

    public void setmCarFuel(String mCarFuel) {
        this.mCarFuel = mCarFuel;
    }

    public String getmCarImg() {
        return mCarImg;
    }

    public void setmCarImg(String mCarImg) {
        this.mCarImg = mCarImg;
    }

    public String getmCarSeat() {
        return mCarSeat;
    }

    public void setmCarSeat(String mCarSeat) {
        this.mCarSeat = mCarSeat;
    }

    public String getmCarRegCerti() {
        return mCarRegCerti;
    }

    public void setmCarRegCerti(String mCarRegCerti) {
        this.mCarRegCerti = mCarRegCerti;
    }

    public String getmCarIssCerti() {
        return mCarIssCerti;
    }

    public void setmCarIssCerti(String mCarIssCerti) {
        this.mCarIssCerti = mCarIssCerti;
    }

    public String getmCarPermit() {
        return mCarPermit;
    }

    public void setmCarPermit(String mCarPermit) {
        this.mCarPermit = mCarPermit;
    }

    public String getmCarOwnercarImg() {
        return mCarOwnercarImg;
    }

    public void setmCarOwnercarImg(String mCarOwnercarImg) {
        this.mCarOwnercarImg = mCarOwnercarImg;
    }

    public String getmCarStatus() {
        return mCarStatus;
    }

    public void setmCarStatus(String mCarStatus) {
        this.mCarStatus = mCarStatus;
    }

    public String getmCarAddedon() {
        return mCarAddedon;
    }

    public void setmCarAddedon(String mCarAddedon) {
        this.mCarAddedon = mCarAddedon;
    }

    public String getmCarUpdatedon() {
        return mCarUpdatedon;
    }

    public void setmCarUpdatedon(String mCarUpdatedon) {
        this.mCarUpdatedon = mCarUpdatedon;
    }

    public String getmCtypeId() {
        return mCtypeId;
    }

    public void setmCtypeId(String mCtypeId) {
        this.mCtypeId = mCtypeId;
    }

    public String getmCtypeTitle() {
        return mCtypeTitle;
    }

    public void setmCtypeTitle(String mCtypeTitle) {
        this.mCtypeTitle = mCtypeTitle;
    }

    public String getmCtypeImg() {
        return mCtypeImg;
    }

    public void setmCtypeImg(String mCtypeImg) {
        this.mCtypeImg = mCtypeImg;
    }

    public String getmCtypeSeat() {
        return mCtypeSeat;
    }

    public void setmCtypeSeat(String mCtypeSeat) {
        this.mCtypeSeat = mCtypeSeat;
    }

    public String getmCtypeLuggage() {
        return mCtypeLuggage;
    }

    public void setmCtypeLuggage(String mCtypeLuggage) {
        this.mCtypeLuggage = mCtypeLuggage;
    }

    public String getmCtypeAC() {
        return mCtypeAC;
    }

    public void setmCtypeAC(String mCtypeAC) {
        this.mCtypeAC = mCtypeAC;
    }

    public String getmCtypePrice() {
        return mCtypePrice;
    }

    public void setmCtypePrice(String mCtypePrice) {
        this.mCtypePrice = mCtypePrice;
    }

    public String getmCtypeDrivetype() {
        return mCtypeDrivetype;
    }

    public void setmCtypeDrivetype(String mCtypeDrivetype) {
        this.mCtypeDrivetype = mCtypeDrivetype;
    }

    public String getmCtypeServtype() {
        return mCtypeServtype;
    }

    public void setmCtypeServtype(String mCtypeServtype) {
        this.mCtypeServtype = mCtypeServtype;
    }

    public String getmCtypeInclusion() {
        return mCtypeInclusion;
    }

    public void setmCtypeInclusion(String mCtypeInclusion) {
        this.mCtypeInclusion = mCtypeInclusion;
    }

    public String getmCtypeExclusion() {
        return mCtypeExclusion;
    }

    public void setmCtypeExclusion(String mCtypeExclusion) {
        this.mCtypeExclusion = mCtypeExclusion;
    }

    public String getmCtypeTc() {
        return mCtypeTc;
    }

    public void setmCtypeTc(String mCtypeTc) {
        this.mCtypeTc = mCtypeTc;
    }

    public String getmCtypeStatus() {
        return mCtypeStatus;
    }

    public void setmCtypeStatus(String mCtypeStatus) {
        this.mCtypeStatus = mCtypeStatus;
    }

    public String getmCtypeAddedon() {
        return mCtypeAddedon;
    }

    public void setmCtypeAddedon(String mCtypeAddedon) {
        this.mCtypeAddedon = mCtypeAddedon;
    }

    public String getmCtypeNumber() {
        return mCtypeNumber;
    }

    public void setmCtypeNumber(String mCtypeNumber) {
        this.mCtypeNumber = mCtypeNumber;
    }

    public String getmBkingTypeCat() {
        return mBkingTypeCat;
    }

    public void setmBkingTypeCat(String mBkingTypeCat) {
        this.mBkingTypeCat = mBkingTypeCat;
    }
}



