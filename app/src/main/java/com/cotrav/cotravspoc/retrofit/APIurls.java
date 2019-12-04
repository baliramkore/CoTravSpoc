package com.cotrav.cotravspoc.retrofit;

public class APIurls
{
    //static final String LIVE_URL="http://192.168.0.10:8000/api/";
    static final String LIVE_URL="http://cotrav.co/api/";
    public static final String BASE_URL = LIVE_URL;
    static final String LOGIN="login";
    public static final String TAXITYPE="taxi_types";
    public static final String TRAIN_TYPE="train_types";
    public static final String TRAIN_STATIONS="railway_stations";

    public static final String CITIES="cities";
    public static final String ASSCITIES="get_assessment_city";
    public static final String ASSCODES="get_assessment_code";
    public static final String AddTaxiBooking="add_taxi_booking";
    public static final String AddBusBooking="add_bus_booking";
    public static final String AddTrainBooking="add_train_booking";
    public static final String AddHotelBooking="add_hotel_booking";
    public static final String AddFlightBooking="add_flight_booking";
    public static final String getratesbycity="corporate_package";
    public static final String getallemployee=LIVE_URL+"employees";
    public static final String billingEntity=LIVE_URL+"billing_entities";
    static final String ROOMTYPE="room_types";
    static final String HOTELTYPE="hotel_types";
    static final String COMPANYTYPE="company_types";
    static final String TAXIBOOKINGS="spoc_taxi_bookings";
    static final String BUSBOOKINGS="spoc_bus_bookings";
    static final String TRAINBOOKINGS="spoc_train_bookings";
    static final String HOTELBOOKINGS="spoc_hotel_bookings";
    static final String FLIGHTBOOKINGS="spoc_flight_bookings";
    static final String TAXIBOOKINGSDETAILS="view_taxi_booking";
    static final String BUSBOOKINGSDETAILS="view_bus_booking";
    static final String TRAINBOOKINGSDETAILS="view_train_booking";
    static final String HOTELBOOKINGSDETAILS="view_hotel_booking";
    static final String FLIGHTBOOKINGSDETAILS="view_flight_booking";
    static final String VIEWSPOC="view_spoc";
    static final String DASHBOARD="spoc_dashboard";
    static final String REJECTBOOKING_TAXI="spoc_reject_taxi_booking";
    static final String REJECTBOOKING_TRAIN="spoc_reject_train_booking";
    static final String REJECTBOOKING_BUS="spoc_reject_bus_booking";
    static final String REJECTBOOKING_FLIGHT="spoc_reject_flight_booking";
    static final String REJECTBOOKING_HOTEL="spoc_reject_hotel_booking";

    static final String LOGOUT="logout";
}
