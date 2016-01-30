package com.example.nrapesh.ecommerce;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import org.apache.commons.lang3.text.WordUtils;

/**
 * Adapter to manage list of products.
 */

class ViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView nameView;
    TextView priceView;
    TextView discountPriceView;
    TextView retailerView;

    protected ViewHolder(View itemLayoutView) {
        super(itemLayoutView);
        this.imageView = (ImageView) itemLayoutView.findViewById(R.id.image);
        this.nameView = (TextView) itemLayoutView.findViewById(R.id.name);
        this.priceView = (TextView) itemLayoutView.findViewById(R.id.price);
        this.discountPriceView = (TextView) itemLayoutView.findViewById(R.id.discountPrice);
        this.retailerView = (TextView) itemLayoutView.findViewById(R.id.retailer);
    }

}

public class ProductListAdapter extends RecyclerView.Adapter<ViewHolder> {

    private ArrayList<Product> listData;
    private Context context;

    public ProductListAdapter(Context context, ArrayList<Product> listData) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.basic_product_info, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String brand = listData.get(position).getBrand();
        String name = listData.get(position).getName();

        String titleString = brand.toUpperCase() + "    " + WordUtils.capitalize(name);
        holder.nameView.setText(titleString);
        String priceText = "";
        String discountPriceText = "";
        Float price = listData.get(position).getPrice();
        Float discountPrice = listData.get(position).getDiscountPrice();
        String rupee = this.context.getString(R.string.Rs);
        if (discountPrice == null || discountPrice == 0 || discountPrice.equals(price))
        {
            priceText = rupee + String.format("%-10.0f", price);
        }
        else
        {
            priceText = "Orig. " + rupee + String.format("%-10.0f", price);
            discountPriceText = "Now " + rupee + String.format("%-10.0f", discountPrice);
        }
        holder.priceView.setText(priceText);
        holder.discountPriceView.setText(discountPriceText);
        String retailerString = listData.get(position).getRetailer().toUpperCase() + ", USA";
        holder.retailerView.setText(retailerString);

        Glide.with(context)
                .load(listData.get(position).getImageUrl())
                .placeholder(R.drawable.image_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .thumbnail(0.1f)
                .dontTransform()
                .into(holder.imageView);
  }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}