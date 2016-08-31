package com.agriscienceapp.webservice;

import com.agriscienceapp.model.AgriScienceTVModel;
import com.agriscienceapp.model.BajarBhavZoneListModel;
import com.agriscienceapp.model.CommodityDetail;
import com.agriscienceapp.model.DistrictDetail;
import com.agriscienceapp.model.KhedutSafalGathaModel;
import com.agriscienceapp.model.KrushiSalahAdvisoryModel;
import com.agriscienceapp.model.KrushiSalahModel;
import com.agriscienceapp.model.PakPasandCropModel;
import com.agriscienceapp.model.PakPasandModel;
import com.agriscienceapp.model.PakPasandYardModel;
import com.agriscienceapp.model.SamacharModel;
import com.agriscienceapp.model.TalukaDetail;
import com.agriscienceapp.model.UtaraModel;

import retrofit.Callback;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by IGS on 1/3/2016.
 */
public interface APICallMethods {


//       @GET("/DistrictList/")
//       public void getdistrictList(Callback<DistrictModel> response);

// @GET("/DistrictList/{username}")
//   Call<User> getUser(@Path("username") String username);

    @POST("/NewsList/")
    public void getSamacharList(Callback<SamacharModel> response);

   /* @FormUrlEncoded
    @POST("/NewslistDetail/")
    public void getSamacharDetails(@Field("NewsId") int newsID, Callback<SamacharDescriptionModel> response);*/

    @POST("/ZoneList/")
    public void getBajarBhavZoneList(Callback<BajarBhavZoneListModel> response);

    @POST("/PakPasandCrop/")
    public void getPakPasandCrop(Callback<PakPasandCropModel> response);

    @POST("/PakPasandYard/")
    public void getPakPasandCropYard(@Query("CropId") int CropId, Callback<PakPasandYardModel> response);

    @POST("/Storieslist/")
    public void getKhedutSafalGathaList(Callback<KhedutSafalGathaModel> response);

    /*@GET("/StoriesDetail/")
    public void getKhedutSafalGathaDetail(@Field("StoryId") int storyID, Callback<KhedutSafalGathaModel> response);
*/

    @POST("/CommodityTitle/")
    public void getKrushiSalahList(Callback<KrushiSalahModel> response);

    @POST("/Advisory/")
    public void getKrushiSalahAdvisoryList(Callback<KrushiSalahAdvisoryModel> response);

    @POST("/Gallerylist/")
    public void getAgriScienceTVList(Callback<AgriScienceTVModel> response);

    @POST("/uttara/")
    public void getUtara(Callback<UtaraModel> response);

    @POST("/PakPasand/")
    public void getPakPasand(@Query("CropId") String CropId, @Query("YardId") int YardId, Callback<PakPasandModel> response);

    @POST("/DistrictList/")
    public void getDistrictList(Callback<DistrictDetail> response);

    @POST("/TalukaList/")
    public void getTalukaList(@Query("DistID") String DistId, Callback<TalukaDetail> response);

    @POST("/CommodityList/")
    public void getCommodityList(Callback<CommodityDetail> response);
}
// @GET("/Newslist/{NewslistDetail}")
// public void getSamacharDescription(@Path("NewslistDetail")String NewslistDetail, Callback<SamacharDescriptionModel> cb);
// Call<SamacharDescriptionModel> getUser(@Path("username") String username);


