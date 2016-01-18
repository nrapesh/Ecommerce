package com.example.nrapesh.ecommerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
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
 * Created by VineetR on 14-01-2016.
 */
public class BrandSelectionActivity extends AppCompatActivity {

    private ArrayList<String> results = new ArrayList<>();
    // Progress Dialog
    private ProgressDialog pDialog;
    // JSON Node names
    private static String TAG_SUCCESS = "success";
    private static String TAG_BRANDS = "brands";
    private static String TAG_BRAND = "brand";
    private static String TAG_COUNT = "count";

    private String url_brands = "http://ec2-52-77-246-8.ap-southeast-1.compute.amazonaws.com/get_top_brands.php";
    ListView listview;
    public static final String[] M_BRANDS = new String [] {"michael kors", "vince", "dkny", "calvin klein", "tory burch", "loeffler randall", "michael kors",
            "michael kors", "michael kors", "michael kors", "michael kors", "michael kors", "michael kors"} ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brand_selection_page);
        // Set a toolbar to replace the action bar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.titlebar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView v = (TextView) toolbar.findViewById(R.id.toolbar_title);
        v.setGravity(Gravity.LEFT | Gravity.CENTER);
        v.setText("SELECT BRAND");

        new LoadBrands().execute("");

//        ArrayList image_details = getListData();
        final ListView list_view = (ListView) findViewById(R.id.brand_list);

//        lv1.setAdapter(new ProductListAdapter(this, image_details));
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                String brand = ((TextView) v).getText().toString().toLowerCase();
                Bundle b = new Bundle();
                Intent intent = new Intent(BrandSelectionActivity.this, BrandProductsActivity.class);
                b.putString("brand", brand); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                //Toast.makeText(BrandSelectionActivity.this, "Selected :" + " " + ((TextView) v).getText(), Toast.LENGTH_LONG).show();
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
     * */
    class LoadBrands extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*pDialog = new ProgressDialog(BrandSelectionActivity.this);
            pDialog.setMessage("Getting Brands");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();*/
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {


            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONParser jParser = new JSONParser();
            JSONObject json = jParser.makeHttpRequest(url_brands, "GET", params);
            // products JSONArray
            JSONArray brands = null;

            // Check your log cat for JSON reponse
            Log.d("All Brands: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    brands = json.getJSONArray(TAG_BRANDS);

                    // looping through All Products
                    for (int i = 0; i < brands.length(); i++) {
                        JSONObject c = brands.getJSONObject(i);

                        // Storing each json item in variable
                        String brand = c.getString(TAG_BRAND);
                        String count_string = c.getString(TAG_COUNT);
                        Integer count = Integer.parseInt(count_string);
                        results.add(brand);
                        Log.d("Adding Brand - ", brand);
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
            //pDialog.dismiss();
            //listview = (ListView) findViewById(R.id.product_list);
            //listview.setAdapter(new ProductListAdapter(BrandSelectionActivity.this, results));
            // Create an OnScrollListener
            final ListView list_view = (ListView) findViewById(R.id.brand_list);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(BrandSelectionActivity.this,
                    R.layout.brand_box, (String[]) results.toArray(new String[results.size()]));
            list_view.setAdapter(adapter);
        }
    }
}
