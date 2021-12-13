package com.app.shopping.Api;


import com.app.shopping.Model.AdminOrders;
import com.app.shopping.Model.Cart;
import com.app.shopping.Model.DetailOrder;
import com.app.shopping.Model.Orders;
import com.app.shopping.Model.Products;
import com.app.shopping.Model.Shipments;
import com.app.shopping.Model.Users;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    //    https://sell-clothes-online.herokuapp.com/v1/products/getbyid?idProduct=1
//    https://sell-clothes-online.herokuapp.com/v1/products
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://sell-clothes-online.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

//    @GET("v1/products/getbyid")
//        //  @HTTP(method = "GET", path = "accounts/login", hasBody = true)
//    Call<Products2> isUserOrAdmin(@Query("idProduct") int id);

    @GET("v1/orders/getadminorder")
    Call<List<AdminOrders>> getAllAdminOrders();//trả về list bản ghi trong bảng Orders có state = "confirmed" có kiểu trả về là AdminOrders
    @GET("v1/carts/getcartbyuid")
    Call<List<Cart>> getListCartByUID(@Query("uid") String userID);//trả về list bản ghi trong bảng detaiorder có uID=userID
    @DELETE("v1/orders/deleteorderbyuid")
    Call<Void> removeOrderByUID(@Query("uid") String uID); //xóa bản ghi trong bảng Orders có uID= "uID"
    @Multipart
    @POST("v1/products/add")
    Call<Products> addProduct(@Part("name") RequestBody pname,
                          @Part("description")RequestBody description,
                          @Part("price")RequestBody price,
                          @Part("idcategory")RequestBody idcategory,
                          @Part MultipartBody.Part image); // thêm vào bảng product
    @GET("v1/accounts/login")
    Call<Users> checkLogin(@Query("phone") String phone,
                           @Query("password") String password,
                           @Query("level") int level); // trong bảng User trả về User có phone=phone   level=level
    @POST("v1/accounts")
    Call<Users> addUser(@Body Users userdata); // add User phone name password
    @GET("v1/products")
    Call<List<Products>> getAllProduct(); // trả về list<Product>
    @GET("v1/orders/getorderbyuid")
    Call<List<Orders>> getOrdersByUID(@Query("uid") String uid);// trả về Orders có uID=phone
    @POST("v1/orders/add")
    Call<Orders> addOrder(@Body Orders order);// add Order uID=phone, state=state
    @GET("v1/detailorders/getbypidandoid")
    Call<List<DetailOrder>> getDetailOrdersByIdorderAndPid(@Query("idOrder") String idOrder,
                                                     @Query("productID") String productID);//get detailorder by idorder and pid
    @POST("v1/detailorders/add")
    Call<DetailOrder> addDetailOrder(@Body DetailOrder detailOrder); // add detailOrder
    @PUT("v1/detailorders/update")
    Call<DetailOrder> updateDetailOrder(@Body DetailOrder detailOrder); // update detailOrder
    @GET("v1/products/getbyid")
    Call<Products> getProductByPid(@Query("idProduct") int pid); // Bang product
    @DELETE("v1/detailorders/delete")
    Call<Void> removeDetailOrderById(@Query("idProduct") String idDetailorder); // Bang DetailOrder
    @POST("v1/shipmentdetails/add")
    Call<Void> addShipment(@Body Shipments ordersShipment); // Bang shipment
    @PUT("v1/orders/update")
    Call<Orders> updateOrders(@Body Orders ordersRef); // Bang Order
    @GET("v1/products/getbyname")
    Call<List<Products>> getProductByName(@Query("nameProduct") String searchInput); // Bang Product
}
