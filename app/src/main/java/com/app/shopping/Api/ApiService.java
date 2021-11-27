package com.app.shopping.Api;


import android.app.DownloadManager;

import com.app.shopping.Model.AdminOrders;
import com.app.shopping.Model.Cart;
import com.app.shopping.Model.DetailOrder;
import com.app.shopping.Model.Orders;
import com.app.shopping.Model.Products;
import com.app.shopping.Model.Products2;
import com.app.shopping.Model.Users;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.app.shopping.Model.User;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
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

    @GET("v1/adminorders")
    Call<List<AdminOrders>> getAllAdminOrders();//trả về list bản ghi trong bảng Orders có state = "confirmed" có kiểu trả về là AdminOrders
    @GET("v1/cart")
    Call<List<Cart>> getListCartByUID(String userID);//trả về list bản ghi trong bảng detaiorder có uID=userID
    @DELETE("v1/adminoders")
    Call<Void> removeOrderByUID(String uID); //xóa bản ghi trong bảng Orders có uID= "uID"
    @Multipart
    @POST("v1/products/add")
 //   @POST()
    Call<Products> addProduct(@Part("name") RequestBody pname,
                          @Part("description")RequestBody description,
                          @Part("price")RequestBody price,
                          @Part("idcategory")RequestBody idcategory,
                          @Part MultipartBody.Part image); // thêm vào bảng product
    @GET
    Call<Users> checkLogin(String phone, int level); // trong bảng User trả về User có phone=phone   level=level
    @POST
    Call<Users> addUser(HashMap<String, Object> userdataMap); // add User phone name password
    @GET
    Call<List<Products>> getAllProduct(); // trả về list<Product>
    @GET
    Call<Orders> getOrdersByUID(String phone);// trả về Orders có uID=phone
    @POST
    Call<Orders> addOrder(String phone, String state);// add Order uID=phone, state=state
    @GET
    Call<DetailOrder> getDetailOrdersByIdorderAndPid(String idOrder, String productID);//get detailorder by idorder and pid
    @POST
    Call<DetailOrder> addDetailOrder(HashMap<String, Object> cartMap); // add detailOrder
    @POST
    Call<DetailOrder> updateDetailOrder(HashMap<String, Object> cartMap); // update detailOrder
    @GET
    Call<Products> getProductByPid(String productID); // Bang product
    @DELETE
    Call<Void> removeDetailOrderById(String idDetailorder); // Bang DetailOrder
    @POST
    Call<Void> addShipment(HashMap<String, Object> ordersShipment); // Bang shipment
    @POST
    Call<Orders> updateOrders(HashMap<String, Object> ordersMap); // Bang Order
    @GET
    Call<List<Products>> getProductByName(String searchInput); // Bang Product
}
