package com.app.shopping;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.shopping.Api.ApiService;
import com.app.shopping.Model.DetailOrder;
import com.app.shopping.Model.Orders;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.app.shopping.Model.Products;
import com.app.shopping.Prevalent.Prevalent;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends AppCompatActivity {
    private Button addToCartButton;
    private ImageView productImage;
    private ElegantNumberButton numberButton;
    private TextView productPrice,productDescription,productName;
    private String productID="", state = "Normal";
    private List<Orders> order;
    private List<DetailOrder> detailOrder;
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
//        Prevalent.currentOnlineUser.getPhone()
        ApiService.apiService.getOrdersByUID(Prevalent.currentOnlineUser.getPhone()).enqueue(new Callback<List<Orders>>() {

            @Override
            public void onResponse(Call<List<Orders>> call, Response<List<Orders>> response) {
                order = response.body();
                Log.e("abcd getOrdersByUID",order.toString());
                Toast.makeText(ProductDetailsActivity.this,"call api success getOrdersByUID",Toast.LENGTH_SHORT).show();
                if (order.size() == 0) {
                    Orders orderRef = new Orders();
                    orderRef.setUid(Prevalent.currentOnlineUser.getPhone());
                    orderRef.setState("Not confirmed");
                    ApiService.apiService.addOrder(orderRef).enqueue(new Callback<Orders>() {

                        @Override
                        public void onResponse(Call<Orders> call, Response<Orders> response) {
                            order.add( response.body());
                            Log.e("abcd addOrder",order.toString());
                            Toast.makeText(ProductDetailsActivity.this,"add Order success",Toast.LENGTH_SHORT).show();
                            getDetailOrdersByIdorderAndPid();
                        }

                        @Override
                        public void onFailure(Call<Orders> call, Throwable t) {
                            Toast.makeText(ProductDetailsActivity.this,"Call api fail addOrder",Toast.LENGTH_SHORT).show();

                        }
                    });
                } else {
                    getDetailOrdersByIdorderAndPid();
                }
            }

            @Override
            public void onFailure(Call<List<Orders>> call, Throwable t) {
                Toast.makeText(ProductDetailsActivity.this,"Call api fail getOrdersByUID",Toast.LENGTH_SHORT).show();
            }
        });



    }
    private void getDetailOrdersByIdorderAndPid(){
        ApiService.apiService.getDetailOrdersByIdorderAndPid(order.get(0).getIdOrder(),productID).enqueue(new Callback<List<DetailOrder>>() {

            @Override
            public void onResponse(Call<List<DetailOrder>> call, Response<List<DetailOrder>> response) {
                detailOrder = response.body();
                Log.e("abcd getDetailOrdersByIdorderAndPid",detailOrder.toString());
                addDetailOrder();
                Toast.makeText(ProductDetailsActivity.this,"Call api success getDetailOrdersByIdorderAndPid",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<DetailOrder>> call, Throwable t) {
                Log.e("abc",t.getMessage());
                Toast.makeText(ProductDetailsActivity.this,"Call api fail getDetailOrdersByIdorderAndPid",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void addDetailOrder(){
        final DetailOrder detailOrderRef = new DetailOrder();
        detailOrderRef.setPid(productID);
        detailOrderRef.setQuantity(numberButton.getNumber());
        detailOrderRef.setIdOrder(order.get(0).getIdOrder());
        if (detailOrder.size()  == 0) {
            ApiService.apiService.addDetailOrder(detailOrderRef).enqueue(new Callback<DetailOrder>() {

                @Override
                public void onResponse(Call<DetailOrder> call, Response<DetailOrder> response) {
                    detailOrder.add(response.body());
                    Log.e("abcd addDetailOrder",detailOrderRef.toString());
                    Log.e("abcd addDetailOrder",detailOrder.toString());
                    Toast.makeText(ProductDetailsActivity.this,"Added to cart List addDetailOrder",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<DetailOrder> call, Throwable t) {
                    Toast.makeText(ProductDetailsActivity.this,"Call api fail addDetailOrder",Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            detailOrderRef.setIdDetailorder(detailOrder.get(0).getIdDetailorder());
            ApiService.apiService.updateDetailOrder(detailOrderRef).enqueue(new Callback<DetailOrder>() {

                @Override
                public void onResponse(Call<DetailOrder> call, Response<DetailOrder> response) {
                    detailOrder.add(response.body());
                    Log.e("abcd updateDetailOrder",detailOrder.toString());
                    Toast.makeText(ProductDetailsActivity.this,"Updated to cart List updateDetailOrder",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<DetailOrder> call, Throwable t) {
                    Toast.makeText(ProductDetailsActivity.this,"Call api fail updateDetailOrder",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void getProductDetails(String productID) {
        ApiService.apiService.getProductByPid(Integer.parseInt(productID)).enqueue(new Callback<Products>() {

            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                Toast.makeText(ProductDetailsActivity.this, "Call api success getProductByPid", Toast.LENGTH_SHORT).show();
                Products products = response.body();
                Log.e("abcd getProductByPid",products.toString());
                productName.setText(products.getPname());
                productPrice.setText(products.getPrice());
                productDescription.setText(products.getDescription());
                Picasso.get().load(products.getImage()).into(productImage);
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                Toast.makeText(ProductDetailsActivity.this, "Call api fail getProductByPid"  , Toast.LENGTH_SHORT).show();

            }
        });
    }
        private void CheckOrderState ()
        {
//            Prevalent.currentOnlineUser.getPhone()
            ApiService.apiService.getOrdersByUID(Prevalent.currentOnlineUser.getPhone()).enqueue(new Callback<List<Orders>>() {

                @Override
                public void onResponse(Call<List<Orders>> call, Response<List<Orders>> response) {
                    List<Orders> Order = response.body();
                    Log.e("abcd getOrdersByUID",Order.toString());
                    Toast.makeText(ProductDetailsActivity.this, "Call API success getOrdersByUID 2", Toast.LENGTH_SHORT).show();
                    if (Order.size() == 0) {
                        state = "Not confirmed";
                    } else {
                        state = Order.get(0).getState();
                    }
                }

                @Override
                public void onFailure(Call<List<Orders>> call, Throwable t) {
                    Log.e("abc2",Prevalent.currentOnlineUser.getPhone());
                    Log.e("abc",t.getMessage());
                    Toast.makeText(ProductDetailsActivity.this, "Call API fail getOrdersByUID 2", Toast.LENGTH_SHORT).show();
                }
            });
        }

}
