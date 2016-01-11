package com.example.nrapesh.ecommerce;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

import java.util.ArrayList;

/**
 * Adapter to manager list of products.
 */
public class ProductListAdapter extends BaseAdapter {
    private ArrayList<Product> listData;
    private LayoutInflater layoutInflater;

    public ProductListAdapter(Context aContext, ArrayList<Product> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.basic_product_info, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.image);
            holder.nameView = (TextView) convertView.findViewById(R.id.name);
            holder.priceView = (TextView) convertView.findViewById(R.id.price);
            holder.discountPriceView = (TextView) convertView.findViewById(R.id.discountPrice);
            holder.retailerView = (TextView) convertView.findViewById(R.id.retailer);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nameView.setText(listData.get(position).getName());
        String priceText = "";
        String discountPriceText = "";
        Float price = listData.get(position).getPrice();
        Float discountPrice = listData.get(position).getDiscountPrice();
        if (discountPrice == null || discountPrice == 0 || discountPrice.equals(price))
        {
            priceText = "$" + Float.toString(price);
        }
        else
        {
            priceText = "Orig. $" + Float.toString(price);
            discountPriceText = "Now $" + Float.toString(discountPrice);
        }
        holder.priceView.setText(priceText);
        holder.discountPriceView.setText(discountPriceText);
        String retailerString = listData.get(position).getRetailer().toUpperCase() + ", USA";
        holder.retailerView.setText(retailerString);
        //holder.imageView.setImageResource(R.drawable.michael_kors);
        //holder.imageView.setImageURI(Uri.parse(listData.get(position).getImageUrl()));
        holder.imageView.setImageBitmap(listData.get(position).getImageBitmap());
        holder.imageView.setScaleType(ScaleType.FIT_XY);
        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView nameView;
        TextView priceView;
        TextView discountPriceView;
        TextView retailerView;
    }
}