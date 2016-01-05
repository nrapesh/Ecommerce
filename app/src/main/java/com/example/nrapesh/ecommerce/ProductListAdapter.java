package com.example.nrapesh.ecommerce;

import android.content.Context;
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
            holder.descriptionView = (TextView) convertView.findViewById(R.id.description);
            holder.priceView = (TextView) convertView.findViewById(R.id.price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.descriptionView.setText(listData.get(position).getDescription());
        holder.priceView.setText(Float.toString(listData.get(position).getPrice()));
        holder.imageView.setImageResource(R.drawable.michael_kors);
        holder.imageView.setScaleType(ScaleType.FIT_XY);
        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView descriptionView;
        TextView priceView;
    }
}