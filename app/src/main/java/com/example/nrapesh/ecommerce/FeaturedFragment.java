package com.example.nrapesh.ecommerce;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

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
// In this case, the fragment displays simple text based on the page
public class FeaturedFragment extends Fragment {

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
    private int itemsLoaded=0;
    private String url_featured_products = "http://ec2-52-77-246-8.ap-southeast-1.compute.amazonaws.com/get_featured_products.php";
    private String url_sale_products = "http://ec2-52-77-246-8.ap-southeast-1.compute.amazonaws.com/get_sale_products.php";
    private String query_url;
    ListView listview;

    public static final String ARG_TAB = "ARG_TAB";
    private View view;
    private ProgressBar progressBar;
    private int mTab;

    public static FeaturedFragment newInstance(int tab) {
        Bundle args = new Bundle();
        args.putInt(ARG_TAB, tab);
        FeaturedFragment fragment = new FeaturedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTab = getArguments().getInt(ARG_TAB);
        query_url = url_featured_products;
        if (mTab == 2) query_url = url_sale_products;
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.products_list, container, false);
        final ListView lv1 = (ListView) view.findViewById(R.id.product_list);
        progressBar = (ProgressBar) getActivity().findViewById(R.id.progressbar);
        new LoadAllProducts().execute("");

//        ArrayList image_details = getListData();
//        lv1.setAdapter(new ProductListAdapter(this, image_details));
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = lv1.getItemAtPosition(position);
                Product product = (Product) o;
                Toast.makeText(FeaturedFragment.this.getContext(), "Selected :" + " " + product, Toast.LENGTH_LONG).show();
            }
        });
        //TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        //tvTitle.setText("Fragment Featured");
        return view;
    }

    ArrayList<Product> results = new ArrayList<Product>();



    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadAllProducts extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {


            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONParser jParser = new JSONParser();
            JSONObject json = jParser.makeHttpRequest(query_url, "GET", params);
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
                        Integer id = 0;//Integer.parseInt(idString);
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

                        Product p = new Product(id, name, "", retailer, price, discountPrice, "",
                                "", description, url, imageUrl, imageBitmap, false);

                        results.add(p);
                        Log.d("Adding Product - ", p.getName());
                    }
                } else {
                    // no products found
                }
            } catch (JSONException e)
            {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            progressBar.setVisibility(View.GONE);
            listview = (ListView) view.findViewById(R.id.product_list);
            listview.setAdapter(new ProductListAdapter(FeaturedFragment.this.getContext(), results));
            // Create an OnScrollListener
            listview.setOnScrollListener(new AbsListView.OnScrollListener() {

                @Override
                public void onScrollStateChanged(AbsListView view,
                                                 int scrollState) { // TODO Auto-generated method stub
                    int threshold = 1;
                    int count = listview.getCount();

                    if (scrollState == SCROLL_STATE_IDLE) {
                        if (listview.getLastVisiblePosition() >= count
                                - threshold) {
                            // Execute LoadMoreDataTask AsyncTask
                            new LoadMoreDataTask().execute();
                        }
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem,
                                     int visibleItemCount, int totalItemCount) {
                    // TODO Auto-generated method stub

                }

            });
        }
        private class LoadMoreDataTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... args) {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                // getting JSON string from URL
                JSONParser jParser = new JSONParser();
                NameValuePair n = new BasicNameValuePair("start", Integer.toString(itemsLoaded));
                params.add(n);
                JSONObject json = jParser.makeHttpRequest(query_url, "GET", params);
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
                            Integer id = 0;//Integer.parseInt(idString);
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

                            Product p = new Product(id, name, "", retailer, price, discountPrice, "",
                                    "", description, url, imageUrl, imageBitmap, false);

                            results.add(p);
                            Log.d("Adding Product - ", p.getName());
                        }
                    } else {
                        // no products found
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                // Locate listview last item
                int position = listview.getLastVisiblePosition();
                // Binds the Adapter to the ListView
                listview.setAdapter(new ProductListAdapter(FeaturedFragment.this.getContext(), results));
                // Show the latest retrived results on the top
                listview.setSelectionFromTop(position, 0);
                // Close the progressdialog
                progressBar.setVisibility(View.GONE);

            }
        }
    }
}