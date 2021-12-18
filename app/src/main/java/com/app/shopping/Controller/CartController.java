package com.app.shopping.Controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
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
import com.app.shopping.Model.Cart;
import com.app.shopping.Model.Orders;
import com.app.shopping.R;
import com.app.shopping.ViewHolder.CartViewHolder;
import com.app.shopping.Prevalent.Prevalent;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartController extends AppCompatActivity{
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button NextProcessBtn;
    private TextView txtTotalAmount, txtMsg1;
    private int overTotalPrice=0;
    private List<Cart> cartListRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        NextProcessBtn = (Button)findViewById(R.id.next_btn);
        txtTotalAmount = (TextView)findViewById(R.id.total_price);
        txtMsg1 = (TextView)findViewById(R.id.msg1);
        NextProcessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                txtTotalAmount.setText("Total Price = "+String.valueOf(overTotalPrice)+" VND");
                Intent intent = new Intent(CartController.this, ConfirmFinalOrderController.class);
                intent.putExtra("Total Price", String.valueOf(overTotalPrice));
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        CheckOrderState();
        ApiService.apiService.getListCartByUID(Prevalent.currentOnlineUser.getPhone()).enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                Toast.makeText(CartController.this, "Call API success", Toast.LENGTH_SHORT).show();
                cartListRef = response.body();
                CartAdapter adapter = new CartAdapter(cartListRef, CartController.this);
                recyclerView.setAdapter(adapter);
                Log.e("abcdef",String.valueOf(overTotalPrice));
         //       txtTotalAmount.setText("Total Price = "+String.valueOf(overTotalPrice)+" VND");
            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {
                Toast.makeText(CartController.this, "Call API failure", Toast.LENGTH_SHORT).show();

            }
        });


    }
    public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
        private List<Cart> mListCart;
        private Context mContext;
        public CartAdapter(List<Cart> mListCart, Context mContext) {
            this.mListCart = mListCart;
            this.mContext = mContext;
        }

        @NonNull
        @Override
        public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }

        @Override
        public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
            Cart model = mListCart.get(position);
            holder.txtProductQuantity.setText("Quantity = " + model.getQuantity());
            holder.txtProductPrice.setText("Price = " + model.getPrice() + " VND.");
            holder.txtProductName.setText(model.getPname());
            int oneTyprProductTPrice = ((Integer.valueOf(model.getPrice()))) * Integer.valueOf(model.getQuantity());
            overTotalPrice = overTotalPrice + oneTyprProductTPrice;
            txtTotalAmount.setText("Total Price = "+String.valueOf(overTotalPrice)+" VND");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CharSequence options[] = new CharSequence[]
                            {
                                    "Edit",
                                    "Remove"
                            };
                    AlertDialog.Builder builder = new AlertDialog.Builder(CartController.this);
                    builder.setTitle("Cart Options: ");
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (i == 0) {
                                Intent intent = new Intent(CartController.this, ProductDetailsController.class);
                                intent.putExtra("pid", model.getPid());
                                startActivity(intent);
                            }
                            if (i == 1) {
                                ApiService.apiService.removeDetailOrderById(model.getIdDetailorder()).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        Toast.makeText(CartController.this, "Item removed Successfully.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(CartController.this, HomeController.class);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText(CartController.this, "Item removed Fail.", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        }
                    });
                    builder.show();

                }
            });
        }
        @Override
        public int getItemCount() {

            if (mListCart !=null) return mListCart.size();
            return 0;
        }
    }
    private void CheckOrderState()
    {
//        Prevalent.currentOnlineUser.getPhone()
        ApiService.apiService.getOrdersByUID(Prevalent.currentOnlineUser.getPhone()).enqueue(new Callback<List<Orders>>() {

            @Override
            public void onResponse(Call<List<Orders>> call, Response<List<Orders>> response) {
                List<Orders> Order = response.body();
                if (Order.size() == 0) return;
                Log.e("abcdf",Order.toString());
                Log.e("abcdf",Order.get(0).getState());
//                Order != null &&
                if (Order.get(0).getState().equals("Confirmed")) {
                    Log.e("abcdf","check state confiremd");
                    txtTotalAmount.setText("Shipping State = Not Shipped");
                    recyclerView.setVisibility(View.GONE);
                    txtMsg1.setVisibility(View.VISIBLE);
                    NextProcessBtn.setVisibility(View.GONE);
                    Toast.makeText(CartController.this,"You can purchase more products, Once you received your first order",Toast.LENGTH_SHORT).show();
//                    } else {
                  //  state = Order.getState();
                }
            }

            @Override
            public void onFailure(Call<List<Orders>> call, Throwable t) {
                Toast.makeText(CartController.this, "Call API fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
