package com.app.shopping;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.shopping.Api.ApiService;
import com.app.shopping.Model.Products;
import com.app.shopping.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchProductsActivity extends AppCompatActivity {
    private Button searchBtn;
    private EditText inputText;
    private RecyclerView searchList;
    private String searchInput;
    private List<Products> productsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_products);
        inputText = findViewById(R.id.search_product_name);
        searchBtn = findViewById(R.id.search_btn);
        searchList = findViewById(R.id.search_list);
        searchList.setLayoutManager(new LinearLayoutManager(SearchProductsActivity.this));
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchInput = inputText.getText().toString();
                onStart();


            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        ApiService.apiService.getProductByName(searchInput).enqueue(new Callback<List<Products>>() {

            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                productsRef = response.body();
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {

            }
        });
        ProductsAdapter adapter = new ProductsAdapter(productsRef,this);
        searchList.setAdapter(adapter);
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");
//        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
//                .setQuery(reference.orderByChild("pname").startAt(searchInput), Products.class)
//                .build();
//        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
//                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
//                    @Override
//                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model) {
//                        holder.txtProductName.setText(model.getPname());
//                        holder.txtProductDescription.setText(model.getDescription());
//                        holder.txtProductPrice.setText("Price = " + model.getPrice() + "Rs.");
//                        Picasso.get().load(model.getImage()).into(holder.imageView);
//                        holder.itemView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                Intent intent =new Intent(SearchProductsActivity.this,ProductDetailsActivity.class);
//                                intent.putExtra("pid",model.getPid());
//                                startActivity(intent);
//                            }
//                        });
//
//                    }
//
//                    @NonNull
//                    @Override
//                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
//                        ProductViewHolder holder = new ProductViewHolder(view);
//                        return holder;
//                    }
//                };
//        searchList.setAdapter(adapter);
//        adapter.startListening();
    }
    public class ProductsAdapter extends RecyclerView.Adapter<ProductViewHolder> {
        private List<Products> mListProducts;
        private Context mContext;
        public ProductsAdapter(List<Products> mListProducts, Context mContext) {
            this.mListProducts = mListProducts;
            this.mContext = mContext;
        }

        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
            ProductViewHolder holder = new ProductViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
            Products model = mListProducts.get(position);
            holder.txtProductName.setText(model.getPname());
            holder.txtProductDescription.setText(model.getDescription());
            holder.txtProductPrice.setText("Price = " + model.getPrice() + "VND");
            Picasso.get().load(model.getImage()).into(holder.imageView);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =new Intent(SearchProductsActivity.this,ProductDetailsActivity.class);
                    intent.putExtra("pid",model.getPid());
                    startActivity(intent);
                }

            });
        }

        @Override
        public int getItemCount() {
            return mListProducts.size();
        }
    }
}
