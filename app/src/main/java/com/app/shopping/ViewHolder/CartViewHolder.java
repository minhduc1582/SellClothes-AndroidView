package com.app.shopping.ViewHolder;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

//import com.app.shopping.Interface.ItemClickListner;
import com.app.shopping.R;
//implements View.OnClickListener
public class CartViewHolder extends RecyclerView.ViewHolder {
    public TextView txtProductName,txtProductPrice,txtProductQuantity;
//    private ItemClickListner itemClickListner;

    public CartViewHolder(View itemView) {
        super(itemView);
        txtProductName = itemView.findViewById(R.id.cart_product_name);
        txtProductPrice = itemView.findViewById(R.id.cart_product_price);
        txtProductQuantity = itemView.findViewById(R.id.cart_product_quantity);
    }
//
//    @Override
//    public void onClick(View view) {
//        itemClickListner.onClick(view,getAdapterPosition(),false);
//    }
//
//    public void setItemClickListner(ItemClickListner itemClickListner) {
//        this.itemClickListner = itemClickListner;
//    }
}


