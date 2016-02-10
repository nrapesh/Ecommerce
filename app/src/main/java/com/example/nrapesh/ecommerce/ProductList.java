package com.example.nrapesh.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;

import java.util.ArrayList;

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
    private int itemsLoaded = 0;
    private String url_all_products = "http://ec2-52-77-246-8.ap-southeast-1.compute.amazonaws.com/get_products.php";


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;
    private ProfilePictureView mUserProfilePicture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_product_list);

        // Set a toolbar to replace the action bar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);        // Drawer object Assigned to the view
        mNavigationView = (NavigationView) findViewById(R.id.navigation);
        mUserProfilePicture = (ProfilePictureView) findViewById(R.id.userProfilePicture);
        mNavigationView.setNavigationItemSelectedListener(new NavigationItemOnClickListener());
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, 0); // this disables the animation
            }


        }; // Drawer Toggle Object Made
        mDrawerLayout.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        TabFragmentPagerAdapter pagerAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(FeaturedFragment.newInstance(1), "Featured");
        pagerAdapter.addFragment(FeaturedFragment.newInstance(2), "Sale");
        pagerAdapter.addFragment(PageFragment.newInstance(3), "Browse");
        viewPager.setAdapter(pagerAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onRestart() {
        super.onRestart();  // Always call the superclass method first

        // Activity being restarted from stopped state
    }

    public void browse_category(View v) {
        Intent intent;
        Bundle b = new Bundle();
        switch (v.getId()) {
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

    @Override
    public void onBackPressed() {
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    ArrayList<Product> results = new ArrayList<Product>();

    private ArrayList getListData() {

        return results;
    }


    private class NavigationItemOnClickListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.logout:
                    LoginManager.getInstance().logOut();
                    Intent intent = new Intent(ProductList.this, LauncherActivity.class);
                    startActivity(intent);
                    return true;


            }
            return false;
        }
    }
}
