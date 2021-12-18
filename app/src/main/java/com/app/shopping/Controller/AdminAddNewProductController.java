package com.app.shopping.Controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.shopping.Api.ApiService;
import com.app.shopping.Lib.RealPathUtil;
import com.app.shopping.Model.Products;
import com.app.shopping.R;


import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminAddNewProductController extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 10;
    private String idCategory, Description, Price, Pname, saveCurrentDate, saveCurrentTime;
    private Button AddNewProductButton;
    private ImageView InputProductImage;
    private EditText InputProductName, InputProductDescription, InputProductPrice;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String productRandomKey, downloadImageUrl;
private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);


        idCategory = getIntent().getExtras().get("idCategory").toString();


        AddNewProductButton = (Button) findViewById(R.id.add_new_product);
        InputProductImage = (ImageView) findViewById(R.id.select_product_image);
        InputProductName = (EditText) findViewById(R.id.product_name);
        InputProductDescription = (EditText) findViewById(R.id.product_description);
        InputProductPrice = (EditText) findViewById(R.id.product_price);
        loadingBar = new ProgressDialog(this);


        InputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onClickRequestPermission() {
//            }
                OpenGallery();
            }
        });


        AddNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ValidateProductData();
            }
        });
    }


    private void OpenGallery(){
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            ImageUri = data.getData();
            InputProductImage.setImageURI(ImageUri);
        }
    }
    private void ValidateProductData() {
        Description = InputProductDescription.getText().toString();
        Price = InputProductPrice.getText().toString();
        Pname = InputProductName.getText().toString();
        if (ImageUri == null)
        {
            Toast.makeText(this, "Product image is mandatory...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Description))
        {
            Toast.makeText(this, "Please write product description...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Price))
        {
            Toast.makeText(this, "Please write product Price...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Pname))
        {
            Toast.makeText(this, "Please write product name...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            SaveProductInfoToDatabase();
        }

    }
private void SaveProductInfoToDatabase()
    {
        loadingBar.setTitle("Add New Product");
        loadingBar.setMessage("Dear Admin, please wait while we are adding the new product.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        RequestBody requestBodyPname = RequestBody.create(MediaType.parse("multipart/form-data"), Pname);
        RequestBody requestBodyDescription = RequestBody.create(MediaType.parse("multipart/form-data"), Description);
        RequestBody requestBodyPrice = RequestBody.create(MediaType.parse("multipart/form-data"), Price);
        RequestBody requestBodyIdCategory = RequestBody.create(MediaType.parse("multipart/form-data"), idCategory);
        String strRealPath = RealPathUtil.getRealPath(this, ImageUri);
        File file = new File(strRealPath);

        RequestBody requestBodyImage = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        Log.e("strfile",strRealPath);
        MultipartBody.Part multipartBodyImage = MultipartBody.Part.createFormData("image",file.getName(),requestBodyImage);
        ApiService.apiService.addProduct(requestBodyPname,requestBodyDescription,requestBodyPrice,requestBodyIdCategory,multipartBodyImage).enqueue(new Callback<Products>() {

            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                Intent intent = new Intent(AdminAddNewProductController.this, AdminCategoryController.class);
                startActivity(intent);
                Products rproduct = response.body();
                if (rproduct!=null)
                Log.e("Products",rproduct.toString());
                loadingBar.dismiss();
                Toast.makeText(AdminAddNewProductController.this, "Product is added successfully..", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                loadingBar.dismiss();
                Toast.makeText(AdminAddNewProductController.this, "Error: ", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
