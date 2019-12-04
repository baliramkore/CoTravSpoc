package com.cotrav.cotravspoc.models;

import android.app.Application;
import android.content.Context;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;

public class GlobalData extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(getApplicationContext());
    }

//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(this);
//    }

    public static void initImageLoader(Context context) {

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();

        ImageLoader.getInstance().init(config);
    }

    public static GlobalData mGlobalData;

    private String screen_from = "";
    private boolean selectedACTV = false;
    private String tempPlace = "";

    // users location details
    private Double Latitude = 0.0;
    private Double Longitude = 0.0;
    private String Address = "";
    private String CurrentCity = "";
    private String PostalCode = "";
    private String Country = "";

    //users current location lat, long & city
    private Double UserCurrentLatitude = 0.0;
    private Double UserCurrentLongitude = 0.0;
    private String UserCurrentAddress = "";
    private String UserCurrentCity = "";

    // users login status flag
    private boolean islogin = false;
    private String is_mobile_validated = "0";
    private String is_email_validated = "0";
    private String access_token;

    // user profile info
    private String contact_no;
    private String email_id;
    private String ful_name;
    private String user_city;

    // search screen flags
    private boolean radio_taxi_rode_now = true;
    private boolean tour_taxi_local = true;

    // search parameters
    private String pickup_date = "";
    private String pickup_time = "";

    private String pickup_city = "";
    private String drop_city = "";
    private ArrayList<TextView> trip_cities_layout = new ArrayList<>();
    private String radio_group_trip_selected = "";
    private String duration_hour = "";
    private String duration_days = "";
    private boolean is_duration_hour_selected = true;

    private String drop_location = "";
    private String tourStartCity = "";

    //used for Promo Code
    private String promoCodeSelected = "";
    private String revisedEstimatedAmount = "";
    private String revisedPrepaidAmount = "";

    // search filter values
    private String is_filter_applied = "0";
    private String no_of_seat = "";
    private ArrayList<String> taxi_type_ids = null;
    private ArrayList<String> operator_type_ids = null;
    private String taxi_type_id_final = "";
    private String operator_type_id_final = "";

    // Self Drive Module Variables
    private String selfDrivePickupDate = "";
    private String selfDrivePickupTime = "";
    private String selfDriveReturnDate = "";
    private String selfDriveReturnTime = "";
    private boolean isSearchCar = true;

//	private String return_date = "";
//	private String return_time = "";

    // payment gateway variables
    private String statusCode = "";

    //speed dial filter api check flag
    private boolean isFirst = true;

    //Radio Cancellation Success Flag
    private boolean isCancelSuccess = false;

    //myWallet
    private String walletbalance;
    private String taxiVaxipoints;

    public String getWalletbalance() {
        return walletbalance;
    }

    public void setWalletbalance(String walletbalance) {
        this.walletbalance = walletbalance;
    }

    public String getTaxiVaxipoints() {
        return taxiVaxipoints;
    }

    public void setTaxiVaxipoints(String taxiVaxipoints) {
        this.taxiVaxipoints = taxiVaxipoints;
    }

    public boolean isCancelSuccess() {
        return isCancelSuccess;
    }

    public void setIsCancelSuccess(boolean isCancelSuccess) {
        this.isCancelSuccess = isCancelSuccess;
    }

    public boolean isSearchCar() {
        return isSearchCar;
    }

    public void setIsSearchCar(boolean isSearchCar) {
        this.isSearchCar = isSearchCar;
    }

    public String getSelfDrivePickupDate() {
        return selfDrivePickupDate;
    }

    public void setSelfDrivePickupDate(String selfDrivePickupDate) {
        this.selfDrivePickupDate = selfDrivePickupDate;
    }

    public String getSelfDrivePickupTime() {
        return selfDrivePickupTime;
    }

    public void setSelfDrivePickupTime(String selfDrivePickupTime) {
        this.selfDrivePickupTime = selfDrivePickupTime;
    }

    public String getSelfDriveReturnDate() {
        return selfDriveReturnDate;
    }

    public void setSelfDriveReturnDate(String selfDriveReturnDate) {
        this.selfDriveReturnDate = selfDriveReturnDate;
    }

    public String getSelfDriveReturnTime() {
        return selfDriveReturnTime;
    }

    public void setSelfDriveReturnTime(String selfDriveReturnTime) {
        this.selfDriveReturnTime = selfDriveReturnTime;
    }

    public String getRevisedEstimatedAmount() {
        return revisedEstimatedAmount;
    }

    public void setRevisedEstimatedAmount(String revisedEstimatedAmount) {
        this.revisedEstimatedAmount = revisedEstimatedAmount;
    }

    public String getRevisedPrepaidAmount() {
        return revisedPrepaidAmount;
    }

    public void setRevisedPrepaidAmount(String revisedPrepaidAmount) {
        this.revisedPrepaidAmount = revisedPrepaidAmount;
    }

    public String getPromoCodeSelected() {
        return promoCodeSelected;
    }

    public void setPromoCodeSelected(String promoCodeSelected) {
        this.promoCodeSelected = promoCodeSelected;
    }

    public Double getUserCurrentLatitude() {
        return UserCurrentLatitude;
    }

    public void setUserCurrentLatitude(Double userCurrentLatitude) {
        UserCurrentLatitude = userCurrentLatitude;
    }

    public Double getUserCurrentLongitude() {
        return UserCurrentLongitude;
    }

    public void setUserCurrentLongitude(Double userCurrentLongitude) {
        UserCurrentLongitude = userCurrentLongitude;
    }

    public String getUserCurrentAddress() {
        return UserCurrentAddress;
    }

    public void setUserCurrentAddress(String userCurrentAddress) {
        UserCurrentAddress = userCurrentAddress;
    }

    public String getUserCurrentCity() {
        return UserCurrentCity;
    }

    public void setUserCurrentCity(String userCurrentCity) {
        UserCurrentCity = userCurrentCity;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setIsFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }

//    public String getReturn_date() {
//		return return_date;
//	}
//
//	public void setReturn_date(String return_date) {
//		this.return_date = return_date;
//	}
//
//	public String getReturn_time() {
//		return return_time;
//	}
//
//	public void setReturn_time(String return_time) {
//		this.return_time = return_time;
//	}

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getTourStartCity() {
        return tourStartCity;
    }

    public void setTourStartCity(String tourStartCity) {
        this.tourStartCity = tourStartCity;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getDrop_location() {
        return drop_location;
    }

    public void setDrop_location(String drop_location) {
        this.drop_location = drop_location;
    }

    public String getDuration_hour() {
        return duration_hour;
    }

    public void setDuration_hour(String duration_hour) {
        this.duration_hour = duration_hour;
    }

    public String getDuration_days() {
        return duration_days;
    }

    public void setDuration_days(String duration_days) {
        this.duration_days = duration_days;
    }

    public boolean isIs_duration_hour_selected() {
        return is_duration_hour_selected;
    }

    public void setIs_duration_hour_selected(boolean is_duration_hour_selected) {
        this.is_duration_hour_selected = is_duration_hour_selected;
    }

    public String getPickup_city() {
        return pickup_city;
    }

    public void setPickup_city(String pickup_city) {
        this.pickup_city = pickup_city;
    }

    public String getDrop_city() {
        return drop_city;
    }

    public void setDrop_city(String drop_city) {
        this.drop_city = drop_city;
    }

    public String getRadio_group_trip_selected() {
        return radio_group_trip_selected;
    }

    public void setRadio_group_trip_selected(String radio_group_trip_selected) {
        this.radio_group_trip_selected = radio_group_trip_selected;
    }

    public String getIs_mobile_validated() {
        return is_mobile_validated;
    }

    public void setIs_mobile_validated(String is_mobile_validated) {
        this.is_mobile_validated = is_mobile_validated;
    }

    public String getIs_email_validated() {
        return is_email_validated;
    }

    public void setIs_email_validated(String is_email_validated) {
        this.is_email_validated = is_email_validated;
    }

    public String getFul_name() {
        return ful_name;
    }

    public void setFul_name(String ful_name) {
        this.ful_name = ful_name;
    }

    public String getUser_city() {
        return user_city;
    }

    public void setUser_city(String user_city) {
        this.user_city = user_city;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public ArrayList<TextView> getTrip_cities_layout() {
        return trip_cities_layout;
    }

    public void setTrip_cities_layout(ArrayList<TextView> trip_cities_layout) {
        this.trip_cities_layout = trip_cities_layout;
    }

    public String getOperator_type_id_final() {
        return operator_type_id_final;
    }

    public void setOperator_type_id_final(String operator_type_id_final) {
        this.operator_type_id_final = operator_type_id_final;
    }

    public String getTaxi_type_id_final() {
        return taxi_type_id_final;
    }

    public void setTaxi_type_id_final(String taxi_type_id_final) {
        this.taxi_type_id_final = taxi_type_id_final;
    }

    public String getIs_filter_applied() {
        return is_filter_applied;
    }

    public void setIs_filter_applied(String is_filter_applied) {
        this.is_filter_applied = is_filter_applied;
    }

    public static GlobalData getmGlobalData() {
        return mGlobalData;
    }

    public static void setmGlobalData(GlobalData mGlobalData) {
        GlobalData.mGlobalData = mGlobalData;
    }

    public String getNo_of_seat() {
        return no_of_seat;
    }

    public void setNo_of_seat(String no_of_seat) {
        this.no_of_seat = no_of_seat;
    }

    public ArrayList<String> getTaxi_type_ids() {
        return taxi_type_ids;
    }

    public void setTaxi_type_ids(ArrayList<String> taxi_type_ids) {
        this.taxi_type_ids = taxi_type_ids;
    }

    public ArrayList<String> getOperator_type_ids() {
        return operator_type_ids;
    }

    public void setOperator_type_ids(ArrayList<String> operator_type_ids) {
        this.operator_type_ids = operator_type_ids;
    }

    public boolean isTour_taxi_local() {
        return tour_taxi_local;
    }

    public void setTour_taxi_local(boolean tour_taxi_local) {
        this.tour_taxi_local = tour_taxi_local;
    }

    public boolean isRadio_taxi_rode_now() {
        return radio_taxi_rode_now;
    }

    public void setRadio_taxi_rode_now(boolean radio_taxi_rode_now) {
        this.radio_taxi_rode_now = radio_taxi_rode_now;
    }

    public String getPickup_date() {
        return pickup_date;
    }

    public void setPickup_date(String pickup_date) {
        this.pickup_date = pickup_date;
    }

    public String getPickup_time() {
        return pickup_time;
    }

    public void setPickup_time(String pickup_time) {
        this.pickup_time = pickup_time;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public boolean isIslogin() {
        return islogin;
    }

    public void setIslogin(boolean islogin) {
        this.islogin = islogin;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCurrentCity() {
        return CurrentCity;
    }

    public void setCurrentCity(String currentCity) {
        CurrentCity = currentCity;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getTempPlace() {
        return tempPlace;
    }

    public void setTempPlace(String tempPlace) {
        this.tempPlace = tempPlace;
    }

    public boolean isSelectedACTV() {
        return selectedACTV;
    }

    public void setSelectedACTV(boolean selectedACTV) {
        this.selectedACTV = selectedACTV;
    }

    public String getScreen_from() {
        return screen_from;
    }

    public void setScreen_from(String screen_from) {
        this.screen_from = screen_from;
    }

    public static GlobalData getInstance() {

        if (mGlobalData == null) {
            mGlobalData = new GlobalData();
        }
        return mGlobalData;
    }

}
