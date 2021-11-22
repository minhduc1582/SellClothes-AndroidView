package com.app.shopping;


import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.shopping.Api.ApiService;
import com.app.shopping.Model.AdminOrders;
import com.app.shopping.Model.Cart;
import com.app.shopping.Prevalent.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminUserProductsActivity extends AppCompatActivity {
    private RecyclerView productsList;
    RecyclerView.LayoutManager layoutManager;
    //private DatabaseReference cartListRef;
    private List<Cart> cartListRef;
    private String userID = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_products);
        userID = getIntent().getStringExtra("uid");
        productsList = findViewById(R.id.products_list);
        productsList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        productsList.setLayoutManager(layoutManager);
        //cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List").child("Admin view").child(userID).child("Products");
//        ApiService.apiService.getListCartByUID(userID).enqueue(new Callback<List<Cart>>() {
//            @Override
//            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
//                Toast.makeText(AdminUserProductsActivity.this, "Call API success", Toast.LENGTH_SHORT).show();
//                cartListRef = response.body();
//            }
//
//            @Override
//            public void onFailure(Call<List<Cart>> call, Throwable t) {
//                Toast.makeText(AdminUserProductsActivity.this, "Call API failure", Toast.LENGTH_SHORT).show();
//
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        cartListRef = new ArrayList<Cart>();
        cartListRef.add(new Cart("1","1","1","1"));
        cartListRef.add(new Cart("2","2","2","2"));
        CartAdapter adapter = new CartAdapter(cartListRef,this);
        productsList.setAdapter(adapter);
//        FirebaseRecyclerOptions<Cart> options=
//                new FirebaseRecyclerOptions.Builder<Cart>()
//                        .setQuery(cartListRef,Cart.class)
//                        .build();
//        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {
//
//                holder.txtProductQuantity.setText("Quantity = "+model.getQuantity());
//                holder.txtProductPrice.setText("Price = "+model.getPrice()+" Rs.");
//                holder.txtProductName.setText(model.getPname());
//            }
//
//            @NonNull
//            @Override
//            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
//                CartViewHolder holder = new CartViewHolder(view);
//                return holder;
//            }
//        };
//        productsList.setAdapter(adapter);
//        adapter.startListening();
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
            holder.txtProductQuantity.setText("Quantity = "+model.getQuantity());
            holder.txtProductPrice.setText("Price = "+model.getPrice()+" Rs.");
            holder.txtProductName.setText(model.getPname());
        }

        @Override
        public int getItemCount() {
            return mListCart.size();
        }
    }

}
