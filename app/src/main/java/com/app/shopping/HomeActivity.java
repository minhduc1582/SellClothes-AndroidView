package com.app.shopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.app.shopping.Api.ApiService;
import com.app.shopping.Model.Cart;
import com.app.shopping.Model.Products;
import com.app.shopping.Prevalent.Prevalent;
import com.app.shopping.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
//    private DatabaseReference ProductsRef;
    private List<Products> ProductsRef;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView textView;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        ApiService.apiService.getAllProduct().enqueue(new Callback<List<Products>>() {

            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                Toast.makeText(HomeActivity.this, "Call API success", Toast.LENGTH_SHORT).show();
                ProductsRef = response.body();
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Call API fail", Toast.LENGTH_SHORT).show();

            }
        });
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView = headerView.findViewById(R.id.user_profile_image);
        userNameTextView.setText(Prevalent.currentOnlineUser.getName());
        Picasso.get().load(Prevalent.currentOnlineUser.getImage()).placeholder(R.drawable.profile).into(profileImageView);
        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();

//        ProductsRef = new ArrayList<Products>();
//        ProductsRef.add(new Products("jeans","a","10","https://res.cloudinary.com/dyvlzl3cw/image/upload/v1637232171/projects/pbl4/products/yaqndlavempc0fu4hwrj.jpg","","","",""));

        ProductsAdapter adapter = new ProductsAdapter(ProductsRef,this);
        recyclerView.setAdapter(adapter);



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
                    Intent intent =new Intent(HomeActivity.this,ProductDetailsActivity.class);
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
    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cart) {
            Intent intent = new Intent(HomeActivity.this,CartActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_search) {
            Intent intent = new Intent(HomeActivity.this,SearchProductsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_categories) {

        } else if (id == R.id.nav_settings) {
            Intent intent=new Intent(HomeActivity.this,SettinsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {
            Paper.book().destroy();
            Intent intent=new Intent(HomeActivity.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK );
            startActivity(intent);
            finish();

        }


        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}