package com.app.shopping;

import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.shopping.Api.ApiService;
import com.app.shopping.Model.Orders;
import com.app.shopping.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmFinalOrderActivity extends AppCompatActivity {
    private EditText nameEditText,phoneEditText,addressEditText,cityEditText;
    private Button confirmOrderBtn;
    private String totalAmount = "";
    private Orders ordersRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        totalAmount = getIntent().getStringExtra("Total Price");
        Toast.makeText(this, "Total Price = "+totalAmount+" VND",Toast.LENGTH_SHORT).show();
        confirmOrderBtn = (Button) findViewById(R.id.confirm_final_order_btn);
        nameEditText =(EditText) findViewById(R.id.shippment_name);
        phoneEditText =(EditText) findViewById(R.id.shippment_phone_number);
        addressEditText =(EditText) findViewById(R.id.shippment_address);
        cityEditText =(EditText) findViewById(R.id.shippment_city);
        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Check();
            }
        });
    }

    private void Check() {
        if(TextUtils.isEmpty(nameEditText.getText().toString())){
            Toast.makeText(this,"Please Provide Your Full Name",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phoneEditText.getText().toString())){
            Toast.makeText(this,"Please Provide Your Phone Number",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(addressEditText.getText().toString())){
            Toast.makeText(this,"Please Provide Your Valid Address.",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(cityEditText.getText().toString())){
            Toast.makeText(this,"Please Provide Your City Name",Toast.LENGTH_SHORT).show();
        }
        else {

            ConfirmOrder();
        }
    }

    private void ConfirmOrder() {
        final String saveCurrentTime,saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd. yyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentDate.format(calForDate.getTime());
        ApiService.apiService.getOrdersByUID(Prevalent.currentOnlineUser.getPhone()).enqueue(new Callback<Orders>() {

            @Override
            public void onResponse(Call<Orders> call, Response<Orders> response) {
                ordersRef = response.body();
            }

            @Override
            public void onFailure(Call<Orders> call, Throwable t) {

            }
        });
//        final DatabaseReference ordersRef= FirebaseDatabase.getInstance().getReference()
//                .child("Orders")
//                .child(Prevalent.currentOnlineUser.getPhone());
        HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("totalAmount",totalAmount);
        ordersMap.put("state", "Confirmed");
        ApiService.apiService.updateOrders(ordersMap).enqueue(new Callback<Orders>() {

            @Override
            public void onResponse(Call<Orders> call, Response<Orders> response) {

            }

            @Override
            public void onFailure(Call<Orders> call, Throwable t) {

            }
        });
        HashMap<String, Object> ordersShipment = new HashMap<>();
        ordersShipment.put("name",nameEditText.getText().toString());
        ordersShipment.put("phone",phoneEditText.getText().toString());
        ordersShipment.put("address",addressEditText.getText().toString());
        ordersShipment.put("city",cityEditText.getText().toString());
        ordersShipment.put("date",saveCurrentDate);
        ordersShipment.put("time",saveCurrentTime);
        ordersShipment.put("idOrder",ordersRef.getIdOrder());
        ApiService.apiService.addShipment(ordersShipment).enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(ConfirmFinalOrderActivity.this,"Your final Order has been placed successfully.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ConfirmFinalOrderActivity.this,HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
//        ordersRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()){
//                    FirebaseDatabase.getInstance().getReference()
//                            .child("Cart List")
//                            .child("User view")
//                            .child(Prevalent.currentOnlineUser.getPhone())
//                            .removeValue()
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()){
//                                        Toast.makeText(ConfirmFinalOrderActivity.this,"Your final Order has been placed successfully.",Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(ConfirmFinalOrderActivity.this,HomeActivity.class);
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                        startActivity(intent);
//                                        finish();
//                                    }
//                                }
//                            });
//                }
//            }
//        });


    }
}
