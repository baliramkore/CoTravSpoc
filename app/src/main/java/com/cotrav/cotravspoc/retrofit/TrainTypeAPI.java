package com.cotrav.cotravspoc.retrofit;

import com.cotrav.cotravspoc.models.trainstationmodel.TrainStationApiResponse;
import com.cotrav.cotravspoc.models.traintypemodel.TrainTypeApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface TrainTypeAPI
{
    @FormUrlEncoded
    @POST(APIurls.TRAIN_TYPE)
    Call<TrainTypeApiResponse> getTrainTypes(@Header("Authorization") String Authorization,
                                             @Header("usertype") String usertype,
                                             @Field("corporate_id")String corporate_id);

    @POST(APIurls.TRAIN_STATIONS)
    Call<TrainStationApiResponse> getTrainStations(@Header("Authorization") String Authorization,
                                                   @Header("usertype") String usertype);


}
