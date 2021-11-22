package com.app.shopping.Api;


import com.app.shopping.Model.AdminOrders;
import com.app.shopping.Model.Cart;
import com.app.shopping.Model.Products;
import com.app.shopping.Model.Products2;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.app.shopping.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Query;

public interface ApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    //    https://sell-clothes-online.herokuapp.com/v1/products/getbyid?idProduct=1
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://sell-clothes-online.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("v1/products/getbyid")
        //  @HTTP(method = "GET", path = "accounts/login", hasBody = true)
    Call<Products2> isUserOrAdmin(@Query("idProduct") int id);

    @GET("v1/adminorders")
    Call<List<AdminOrders>> getAllOrders();//trả về list bản ghi trong bảng Orders có state = "confirmed" có kiểu trả về là AdminOrders
    @GET("v1/cart")
    Call<List<Cart>> getListCartByUID(String userID);//trả về list bản ghi trong bảng detaiorder có uID=userID
    @DELETE("v1/adminoders")
    Call<Void> removeOrderByUID(String uID); //xóa bản ghi trong bảng Orders có uID= "uID"
}
