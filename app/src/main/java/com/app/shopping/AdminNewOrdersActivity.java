package com.app.shopping;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.shopping.Api.ApiService;
import com.app.shopping.Model.AdminOrders;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminNewOrdersActivity extends AppCompatActivity {
    private RecyclerView ordersList;
    private List<AdminOrders> ordersRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_orders);
        ApiService.apiService.getAllAdminOrders().enqueue(new Callback<List<AdminOrders>>() {
            @Override
            public void onResponse(Call<List<AdminOrders>> call, Response<List<AdminOrders>> response) {
                Toast.makeText(AdminNewOrdersActivity.this, "Call API success", Toast.LENGTH_SHORT).show();

                ordersRef = response.body();

            }

            @Override
            public void onFailure(Call<List<AdminOrders>> call, Throwable t) {
                Toast.makeText(AdminNewOrdersActivity.this, "Call API failure", Toast.LENGTH_SHORT).show();

            }


        });

        ordersList = findViewById(R.id.orders_list);
        ordersList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

//        ordersRef = new ArrayList<AdminOrders>();
//        ordersRef.add(new AdminOrders("123","1","2","3","4","Not Shipped","Nov 03. 2021","Nov 03. 2021","429"));
        AdminOrdersAdapter adapter = new AdminOrdersAdapter(ordersRef,this);
        ordersList.setAdapter(adapter);
    }

    public class  AdminOrdersAdapter extends RecyclerView.Adapter<AdminOrdersViewHolder> {
        //Dữ liệu hiện thị là danh sách sinh viên
        private List<AdminOrders> mListAdminOrders;
        // Lưu Context để dễ dàng truy cập
        private Context mContext;

        public AdminOrdersAdapter(List<AdminOrders> mListAdminOrders, Context mContext) {
            this.mListAdminOrders = mListAdminOrders;
            this.mContext = mContext;
        }
        @NonNull
        @Override
        public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout,parent,false);
            return new AdminOrdersViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AdminOrdersViewHolder holder, int position) {

            AdminOrders model = mListAdminOrders.get(position);
            holder.userName.setText("Name: "+model.getName());
            holder.userPhoneNumber.setText("Phone: "+model.getPhone());
            holder.userTotalPrice.setText("Total Ammount = "+model.getTotalAmount()+ " VND");
            holder.userDateTime.setText("Order at: "+model.getDate()+" "+ model.getTime());
            holder.userShippingAddress.setText("Shipping Address: "+model.getAddress()+", "+model.getCity());
            holder.showOrdersBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //String uID = getRef(position).getKey();
                    String uID = model.getuID();
                    Intent intent = new Intent(AdminNewOrdersActivity.this,AdminUserProductsActivity.class);
                    intent.putExtra("uid",uID);
                    startActivity(intent);
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    CharSequence options[] =new CharSequence[]{
                            "Yes",
                            "No"

                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(AdminNewOrdersActivity.this);
                    builder.setTitle("Have you shipped this order products?");
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (i==0){
                                //String uID = getRef(position).getKey();
                                String uID = model.getuID();
                                RemoverOrder(uID);

                            }
                            else {
                                finish();
                            }

                        }
                    });
                    builder.show();
                }
            });

        }

        @Override
        public int getItemCount() {
            return mListAdminOrders.size();
        }
    }

    public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder{

        public TextView userName, userPhoneNumber,userTotalPrice,userDateTime,userShippingAddress;
        public Button showOrdersBtn;
        public AdminOrdersViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.order_user_name);
            userPhoneNumber = itemView.findViewById(R.id.order_phone_number);
            userTotalPrice = itemView.findViewById(R.id.order_total_price);
            userDateTime = itemView.findViewById(R.id.order_date_time);
            userShippingAddress = itemView.findViewById(R.id.order_address_city);
            showOrdersBtn = itemView.findViewById(R.id.show_all_product_btn);
        }
    }
    private void RemoverOrder(String uID) {
       // ordersRef.child(uID).removeValue();
        ApiService.apiService.removeOrderByUID(uID).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(AdminNewOrdersActivity.this,"Call API success",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AdminNewOrdersActivity.this,"Call API failure",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
