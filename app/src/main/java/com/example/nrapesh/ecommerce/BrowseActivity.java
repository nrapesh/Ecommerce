package com.example.nrapesh.ecommerce;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by VineetR on 12-01-2016.
 */

public class BrowseActivity extends AppCompatActivity {

    ArrayList<Product> results = new ArrayList<Product>();
    // Progress Dialog
    // JSON Node names
    private static String TAG_SUCCESS = "success";
    private static String TAG_PRODUCTS = "product";
    private static String TAG_ID = "id";
    private static String TAG_NAME = "name";
    private static String TAG_DESCRIPTION = "description";
    private static String TAG_PRICE = "price";
    private static String TAG_DISCOUNTPRICE = "discountPrice";
    private static String TAG_RETAILER = "retailer";
    private static String TAG_IMAGEURL = "image_url";
    private static String TAG_URL = "url";

    private int itemsLoaded = 0;
    private int category = 0;
    private String url_products = "http://ec2-52-77-246-8.ap-southeast-1.compute.amazonaws.com/get_products_by_category.php";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mRecyclerViewAdapter;
    private boolean loading = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        // Set a toolbar to replace the action bar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

                Bundle b = getIntent().getExtras();
        category = b.getInt("category");
//        TextView v = (TextView) toolbar.findViewById(R.id.toolbar_title);
//        v.setGravity(Gravity.LEFT | Gravity.CENTER);
//        v.setText(PageFragment.M_CATEGORIES[category - 1]);

        new LoadProducts().execute("");

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewAdapter = new ProductListAdapter(this, results);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                if (!loading && linearLayoutManager.getItemCount() <=
                        (linearLayoutManager.findLastVisibleItemPosition() + 5)) {
                    loading = true;

                    // End has been reached
                    // Do something
                    new LoadMoreDataTask().execute();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, ProductList.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);

                // another approach
                //this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //@Override
    //public void onBackPressed()
    //{
    //    moveTaskToBack(true);
    //    return;
    //}

    /**
     * Background Async Task to Load all product by making HTTP Request
     */
    class LoadProducts extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * getting All products from url
         */
        protected String doInBackground(String... args) {


            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            NameValuePair n_category = new BasicNameValuePair("category", String.valueOf(category));
            params.add(n_category);
            JSONParser jParser = new JSONParser();
            JSONObject json = jParser.makeHttpRequest(url_products, "GET", params);
            // products JSONArray
            JSONArray products = null;

            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    products = json.getJSONArray(TAG_PRODUCTS);

                    // looping through All Products
                    itemsLoaded += products.length();
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        // Storing each json item in variable
                        String idString = c.getString(TAG_ID);
                        String name = c.getString(TAG_NAME);
                        String description = c.getString(TAG_DESCRIPTION);
                        String priceString = c.getString(TAG_PRICE);
                        String discountPriceString = c.getString(TAG_DISCOUNTPRICE);
                        String retailer = c.getString(TAG_RETAILER);
                        String imageUrl = c.getString(TAG_IMAGEURL);
                        String url = c.getString(TAG_URL);
<<<<<<< HEAD
                        //Integer id = Integer.parseInt(idString);
                        float price = 0, discountPrice = 0;
                        if (!priceString.isEmpty()) {
=======

                        float price=0, discountPrice=0;
                        if (!priceString.isEmpty())
                        {
>>>>>>> origin/master
                            price = Float.parseFloat(priceString);
                        }
                        if (!discountPriceString.isEmpty()) {
                            discountPrice = Float.parseFloat(discountPriceString);
                        }
                        Bitmap imageBitmap = null;
//                        try {
//                            Bitmap sourceImageBitmap = BitmapFactory.decodeStream((InputStream) new URL(imageUrl).getContent());
//                            imageBitmap = ImageUtil.widthAdjust(ImageUtil.cropTopBackgroud(sourceImageBitmap));
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }

                        Product p = new Product(idString, name, "", retailer, price, discountPrice, "",
                                "", description, url, imageUrl, imageBitmap, false);

                        results.add(p);
                        Log.d("Adding Product - ", p.getName());
                    }
                } else {
                    // no products found
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {
            loading = false;
            mRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    class LoadMoreDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

<<<<<<< HEAD
        }

        @Override
        protected Void doInBackground(Void... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONParser jParser = new JSONParser();
            NameValuePair n_start = new BasicNameValuePair("start", Integer.toString(itemsLoaded));
            params.add(n_start);
            NameValuePair n_category = new BasicNameValuePair("category", String.valueOf(category));
            params.add(n_category);
            JSONObject json = jParser.makeHttpRequest(url_products, "GET", params);
            // products JSONArray
            JSONArray products = null;

            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    products = json.getJSONArray(TAG_PRODUCTS);

                    // looping through All Products
                    itemsLoaded += products.length();
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        // Storing each json item in variable
                        String idString = c.getString(TAG_ID);
                        String name = c.getString(TAG_NAME);
                        String description = c.getString(TAG_DESCRIPTION);
                        String priceString = c.getString(TAG_PRICE);
                        String discountPriceString = c.getString(TAG_DISCOUNTPRICE);
                        String retailer = c.getString(TAG_RETAILER);
                        String imageUrl = c.getString(TAG_IMAGEURL);
                        String url = c.getString(TAG_URL);
                        // Integer id = Integer.parseInt(idString);
                        float price = 0, discountPrice = 0;
                        if (!priceString.isEmpty()) {
                            price = Float.parseFloat(priceString);
                        }
                        if (!discountPriceString.isEmpty()) {
                            discountPrice = Float.parseFloat(discountPriceString);
                        }
                        Bitmap imageBitmap = null;
                        try {
                            Bitmap sourceImageBitmap = BitmapFactory.decodeStream((InputStream) new URL(imageUrl).getContent());
                            imageBitmap = ImageUtil.widthAdjust(ImageUtil.cropTopBackgroud(sourceImageBitmap));

                        } catch (Exception e) {
                            e.printStackTrace();
=======
            @Override
            protected Void doInBackground(Void... args) {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                // getting JSON string from URL
                JSONParser jParser = new JSONParser();
                NameValuePair n_start = new BasicNameValuePair("start", Integer.toString(itemsLoaded));
                params.add(n_start);
                NameValuePair n_category = new BasicNameValuePair("category", String.valueOf(category));
                params.add(n_category);
                JSONObject json = jParser.makeHttpRequest(url_products, "GET", params);
                // products JSONArray
                JSONArray products = null;

                // Check your log cat for JSON reponse
                Log.d("All Products: ", json.toString());

                try {
                    // Checking for SUCCESS TAG
                    int success = json.getInt(TAG_SUCCESS);

                    if (success == 1) {
                        // products found
                        // Getting Array of Products
                        products = json.getJSONArray(TAG_PRODUCTS);

                        // looping through All Products
                        itemsLoaded += products.length();
                        for (int i = 0; i < products.length(); i++) {
                            JSONObject c = products.getJSONObject(i);

                            // Storing each json item in variable
                            String idString = c.getString(TAG_ID);
                            String name = c.getString(TAG_NAME);
                            String description = c.getString(TAG_DESCRIPTION);
                            String priceString = c.getString(TAG_PRICE);
                            String discountPriceString = c.getString(TAG_DISCOUNTPRICE);
                            String retailer = c.getString(TAG_RETAILER);
                            String imageUrl = c.getString(TAG_IMAGEURL);
                            String url = c.getString(TAG_URL);

                            float price=0, discountPrice=0;
                            if (!priceString.isEmpty())
                            {
                                price = Float.parseFloat(priceString);
                            }
                            if (!discountPriceString.isEmpty())
                            {
                                discountPrice = Float.parseFloat(discountPriceString);
                            }
                            Bitmap imageBitmap = null;
                            try {
                                Bitmap sourceImageBitmap = BitmapFactory.decodeStream((InputStream) new URL(imageUrl).getContent());
                                imageBitmap = ImageUtil.widthAdjust(ImageUtil.cropTopBackgroud(sourceImageBitmap));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            Product p = new Product(idString, name, "", retailer, price, discountPrice, "",
                                    "", description, url, imageUrl, imageBitmap, false);

                            results.add(p);
                            Log.d("Adding Product - ", p.getName());
>>>>>>> origin/master
                        }

                        Product p = new Product(idString, name, "", retailer, price, discountPrice, "",
                                "", description, url, imageUrl, imageBitmap, false);

                        results.add(p);
                        Log.d("Adding Product - ", p.getName());
                    }
                } else {
                    // no products found
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            loading = false;
            // Locate listview last item
            mRecyclerViewAdapter.notifyDataSetChanged();

        }
    }
}
