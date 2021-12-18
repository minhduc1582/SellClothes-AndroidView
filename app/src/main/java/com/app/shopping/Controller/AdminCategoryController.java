package com.app.shopping.Controller;


import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.app.shopping.R;

public class AdminCategoryController extends AppCompatActivity {
    private ImageView tShirts, pants, jeans, sweathers;
    private ImageView glasses, hatsCaps, walletsBagsPurses, shoes;
    private ImageView headPhonesHandFree, Laptops, watches, mobilePhones;
    private Button LogoutBtn, CheckOrdersBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        LogoutBtn = (Button) findViewById(R.id.admin_logout_btn);
        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminCategoryController.this, MainController.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                finish();
            }
        });
        CheckOrdersBtn = (Button) findViewById(R.id.check_orders_btn);


        CheckOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminCategoryController.this, AdminNewOrdersController.class);
                startActivity(intent);
            }
        });



        tShirts = (ImageView) findViewById(R.id.t_shirts);
        pants = (ImageView) findViewById(R.id.pants);
        jeans = (ImageView) findViewById(R.id.jeans);


        tShirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryController.this, AdminAddNewProductController.class);
                intent.putExtra("idCategory", "1");
                startActivity(intent);
            }
        });
        pants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryController.this, AdminAddNewProductController.class);
                intent.putExtra("idCategory", "2");
                startActivity(intent);
            }
        });


        jeans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryController.this, AdminAddNewProductController.class);
                intent.putExtra("idCategory", "3");
                startActivity(intent);
            }
        });

    }
}
