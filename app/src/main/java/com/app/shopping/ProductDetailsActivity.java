package com.app.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.shopping.Api.ApiService;
import com.app.shopping.Model.DetailOrder;
import com.app.shopping.Model.Orders;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.app.shopping.Model.Products;
import com.app.shopping.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends AppCompatActivity {
    private Button addToCartButton;
    private ImageView productImage;
    private ElegantNumberButton numberButton;
    private TextView productPrice,productDescription,productName;
    private String productID="", state = "Normal";
    private Orders order;
    private DetailOrder detailOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        productID = getIntent().getStringExtra("pid");
        addToCartButton =(Button) findViewById(R.id.pd_add_to_cart_button);
        numberButton = (ElegantNumberButton) findViewById(R.id.number_btn);
        productImage = (ImageView) findViewById(R.id.product_image_details);
        productName = (TextView) findViewById(R.id.product_name_details);
        productDescription = (TextView) findViewById(R.id.product_description_details);
        productPrice = (TextView) findViewById(R.id.product_price_details);
        getProductDetails(productID);
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (state.equals("Confirmed") ){
                    Toast.makeText(ProductDetailsActivity.this,"You can add Purchase more product, once your order is shipped or confirmed",Toast.LENGTH_LONG).show();
                }
                else
                {
                    addingToCartList();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        CheckOrderState();
    }

    private void addingToCartList() {
        ApiService.apiService.getOrdersByUID(Prevalent.currentOnlineUser.getPhone()).enqueue(new Callback<Orders>() {

            @Override
            public void onResponse(Call<Orders> call, Response<Orders> response) {
                order = response.body();
                if (order == null) {
                    ApiService.apiService.addOrder(Prevalent.currentOnlineUser.getPhone(),"Not confirmed").enqueue(new Callback<Orders>() {

                        @Override
                        public void onResponse(Call<Orders> call, Response<Orders> response) {
                            order = response.body();
                            Toast.makeText(ProductDetailsActivity.this,"add Order success",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Orders> call, Throwable t) {
                            Toast.makeText(ProductDetailsActivity.this,"Call api fail",Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Orders> call, Throwable t) {
                Toast.makeText(ProductDetailsActivity.this,"Call api fail",Toast.LENGTH_SHORT).show();
            }
        });

        ApiService.apiService.getDetailOrdersByIdorderAndPid(order.getIdOrder(),productID).enqueue(new Callback<DetailOrder>() {

            @Override
            public void onResponse(Call<DetailOrder> call, Response<DetailOrder> response) {
                detailOrder = response.body();
                Toast.makeText(ProductDetailsActivity.this,"Call api success",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<DetailOrder> call, Throwable t) {
                Toast.makeText(ProductDetailsActivity.this,"Call api fail",Toast.LENGTH_SHORT).show();
            }
        });
        final HashMap<String, Object>cartMap = new HashMap<>();
        cartMap.put("pid",productID);
        cartMap.put("quantity",numberButton.getNumber());
        cartMap.put("idOrder",order.getIdOrder());
        if (detailOrder  == null) {
            ApiService.apiService.addDetailOrder(cartMap).enqueue(new Callback<DetailOrder>() {

                @Override
                public void onResponse(Call<DetailOrder> call, Response<DetailOrder> response) {
                    detailOrder = response.body();
                    Toast.makeText(ProductDetailsActivity.this,"Added to cart List",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<DetailOrder> call, Throwable t) {
                    Toast.makeText(ProductDetailsActivity.this,"Call api fail",Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            cartMap.put("idDetailOrder",detailOrder.getIdDetailorder());
            ApiService.apiService.updateDetailOrder(cartMap).enqueue(new Callback<DetailOrder>() {

                @Override
                public void onResponse(Call<DetailOrder> call, Response<DetailOrder> response) {
                    detailOrder = response.body();
                    Toast.makeText(ProductDetailsActivity.this,"Updated to cart List",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<DetailOrder> call, Throwable t) {
                    Toast.makeText(ProductDetailsActivity.this,"Call api fail",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void getProductDetails(String productID) {
        ApiService.apiService.getProductByPid(productID).enqueue(new Callback<Products>() {

            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                Toast.makeText(ProductDetailsActivity.this, "Call api getproduct success ", Toast.LENGTH_SHORT).show();
                Products products = response.body();
                productName.setText(products.getPname());
                productPrice.setText(products.getPrice());
                productDescription.setText(products.getDescription());
                Picasso.get().load(products.getImage()).into(productImage);
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                Toast.makeText(ProductDetailsActivity.this, "Call api getproduct fail", Toast.LENGTH_SHORT).show();

            }
        });
    }
        private void CheckOrderState ()
        {
            ApiService.apiService.getOrdersByUID(Prevalent.currentOnlineUser.getPhone()).enqueue(new Callback<Orders>() {

                @Override
                public void onResponse(Call<Orders> call, Response<Orders> response) {
                    Orders Order = response.body();
                    if (Order == null) {
                        state = "Not confirmed";
                    } else {
                        state = Order.getState();
                    }
                }

                @Override
                public void onFailure(Call<Orders> call, Throwable t) {
                    Toast.makeText(ProductDetailsActivity.this, "Call API fail", Toast.LENGTH_SHORT).show();
                }
            });
        }

}
