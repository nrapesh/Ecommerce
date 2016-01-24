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
        View view = inflater.inflate(R.layout.basic_product_info, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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

//        holder.imageView.setImageBitmap(listData.get(position).getImageBitmap());
        holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        Picasso.with(context)
//                .load(listData.get(position).getImageUrl())
////                .transform(new Transformation() {
////                    @Override
////                    public Bitmap transform(Bitmap source) {
////                        Bitmap temp = source.copy(source.getConfig(), true /* mutable */);
////                        source.recycle();
////                        return ImageUtil.widthAdjust(ImageUtil.cropTopBackgroud(temp));
////                    }
////
////                    @Override
////                    public String key() {
////                        return "imageResizeTransformation";
////                    }
////                })
//                .fit()
//                .noFade()
//                .into(holder.imageView);
//       Picasso.with(context)
//                .load(listData.get(position).getImageUrl())
////                .transform(new Transformation() {
////                    @Override
////                    public Bitmap transform(Bitmap source) {
////                        Bitmap temp = source.copy(source.getConfig(), true /* mutable */);
////                        source.recycle();
////                        return ImageUtil.widthAdjust(ImageUtil.cropTopBackgroud(temp));
////                    }
////
////                    @Override
////                    public String key() {
////                        return "imageResizeTransformation";
////                    }
////                })
//                .fit()
//                .noFade()
//                .into(holder.imageView);
//
        Glide.with(context)
                .load(listData.get(position).getImageUrl())
                .placeholder(R.drawable.image_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(holder.imageView);
  }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}