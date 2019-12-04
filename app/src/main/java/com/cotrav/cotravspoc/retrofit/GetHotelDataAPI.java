package com.cotrav.cotravspoc.retrofit;

import com.cotrav.cotravspoc.models.hoteltypesmodel.HotelTypeApiResponse;
import com.cotrav.cotravspoc.models.roomtypesmodel.RoomTypeApiResponse;
import com.cotrav.cotravspoc.models.traintypemodel.TrainTypeApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface GetHotelDataAPI
{

    @POST(APIurls.ROOMTYPE)
    Call<RoomTypeApiResponse> getRoomType(@Header("Authorization") String Authorization,
                                          @Header("usertype") String Usertype);


    @POST(APIurls.HOTELTYPE)
    Call<HotelTypeApiResponse> getHotelType(@Header("Authorization") String Authorization,
                                            @Header("usertype") String Usertype);
}
