package com.example.nrapesh.ecommerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProductList extends AppCompatActivity {

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
    private String url_all_products = "http://ec2-52-77-246-8.ap-southeast-1.compute.amazonaws.com/get_products.php";
    ListView listview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_list);

        // Set a toolbar to replace the action bar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.titlebar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new TabFragmentPagerAdapter(getSupportFragmentManager()));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        /* new LoadAllProducts().execute("");

//        ArrayList image_details = getListData();
        final ListView lv1 = (ListView) findViewById(R.id.product_list);
//        lv1.setAdapter(new ProductListAdapter(this, image_details));
        lv1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = lv1.getItemAtPosition(position);
                Product product = (Product) o;
                Toast.makeText(ProductList.this, "Selected :" + " " + product, Toast.LENGTH_LONG).show();
            }
        });*/
    }

    @Override
    protected void onRestart() {
        super.onRestart();  // Always call the superclass method first

        // Activity being restarted from stopped state
    }

    public void browse_category(View v) {
        Intent intent;
        Bundle b = new Bundle();
        switch(v.getId()) {
            case R.id.browse_shoes: // R.id.textView2
                intent = new Intent(this, BrowseShoesActivity.class);
                b.putInt("category", PageFragment.SHOES); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                break;
            case R.id.browse_clothing: // R.id.textView3
                intent = new Intent(this, BrowseClothingActivity.class);
                b.putInt("category", PageFragment.CLOTHING); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                break;
            case R.id.browse_watches:
                intent = new Intent(this, BrowseWatchesActivity.class);
                b.putInt("category", PageFragment.WATCHES); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                break;
            case R.id.browse_brands:
                intent = new Intent(this, BrandSelectionActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                break;
            default:
                intent = new Intent(this, BrowseBagsActivity.class);
                b.putInt("category", PageFragment.HANDBAGS); //Your id
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.putExtras(b); //Put your id to your next Intent
        }
        startActivity(intent);
    }

    /*public void browse_brands(View v) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.brands);
        TextView mk = (TextView) findViewById(R.id.mk);
        TextView bags = (TextView) findViewById(R.id.browse_bags);
        TextView shoes = (TextView) findViewById(R.id.browse_shoes);
        TextView clothing = (TextView) findViewById(R.id.browse_clothing);
        TextView watches = (TextView) findViewById(R.id.browse_watches);
        if (mk.isShown())
        {
            Animation brands_slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.brands_slide_up);
            Animation cat_slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.cat_slide_up);
            mk.startAnimation(brands_slide_up);
            bags.startAnimation(cat_slide_up);
            shoes.startAnimation(cat_slide_up);
            clothing.startAnimation(cat_slide_up);
            watches.startAnimation(cat_slide_up);

            mk.setVisibility(View.GONE);
            layout.setVisibility(View.GONE);
            bags.setVisibility(View.VISIBLE);
            shoes.setVisibility(View.VISIBLE);
            clothing.setVisibility(View.VISIBLE);
            watches.setVisibility(View.VISIBLE);

        }
        else
        {
            Animation brands_slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.brands_slide_down);
            Animation cat_slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.cat_slide_down);
            mk.startAnimation(brands_slide_down);
            bags.startAnimation(cat_slide_down);
            shoes.startAnimation(cat_slide_down);
            clothing.startAnimation(cat_slide_down);
            watches.startAnimation(cat_slide_down);

            layout.setVisibility(View.VISIBLE);
            mk.setVisibility(View.VISIBLE);
            bags.setVisibility(View.GONE);
            shoes.setVisibility(View.GONE);
            clothing.setVisibility(View.GONE);
            watches.setVisibility(View.GONE);

        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    ArrayList<Product> results = new ArrayList<Product>();

    private ArrayList getListData() {

        return results;
    }

    // Progress Dialog
    private ProgressDialog pDialog;

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
            pDialog = new ProgressDialog(ProductList.this);
            pDialog.setMessage("Loading products. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {


            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONParser jParser = new JSONParser();
            JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
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
                            imageBitmap = BitmapFactory.decodeStream((InputStream) new URL(imageUrl).getContent());

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
            /*pDialog.dismiss();
            listview = (ListView) findViewById(R.id.product_list);
            listview.setAdapter(new ProductListAdapter(ProductList.this, results));
            // Create an OnScrollListener
            listview.setOnScrollListener(new OnScrollListener() {

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

            });*/
        }

        private class LoadMoreDataTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Create a progressdialog
                pDialog = new ProgressDialog(ProductList.this);
                // Set progressdialog title
                pDialog.setTitle("Load More Products");
                // Set progressdialog message
                pDialog.setMessage("Loading more products. Please wait...");
                pDialog.setIndeterminate(false);
                // Show progressdialog
                pDialog.show();
            }

            @Override
            protected Void doInBackground(Void... args) {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                // getting JSON string from URL
                JSONParser jParser = new JSONParser();
                NameValuePair n = new BasicNameValuePair("start", Integer.toString(itemsLoaded));
                params.add(n);
                JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
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
                                imageBitmap = BitmapFactory.decodeStream((InputStream) new URL(imageUrl).getContent());

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
                listview.setAdapter(new ProductListAdapter(ProductList.this, results));
                // Show the latest retrived results on the top
                listview.setSelectionFromTop(position, 0);
                // Close the progressdialog
                pDialog.dismiss();
            }
        }
    }
}
