package com.app.shopping.Controller;


import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.shopping.Api.ApiService;
import com.app.shopping.Model.Cart;
import com.app.shopping.R;
import com.app.shopping.ViewHolder.CartViewHolder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminUserProductsController extends AppCompatActivity {
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
        Log.e("abcde","cr3eate");
        productsList = findViewById(R.id.products_list);
        productsList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        productsList.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        ApiService.apiService.getListCartByUID(userID).enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                Toast.makeText(AdminUserProductsController.this, "Call API success", Toast.LENGTH_SHORT).show();
                cartListRef = response.body();

                CartAdapter adapter = new CartAdapter(cartListRef,AdminUserProductsController.this);
                productsList.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {
                Toast.makeText(AdminUserProductsController.this, "Call API failure", Toast.LENGTH_SHORT).show();

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
            holder.txtProductQuantity.setText("Quantity = "+model.getQuantity());
            holder.txtProductPrice.setText("Price = "+model.getPrice()+" VND.");
            holder.txtProductName.setText(model.getPname());
        }

        @Override
        public int getItemCount() {

            if (mListCart !=null) return mListCart.size();
            return 0;
        }
    }

}
