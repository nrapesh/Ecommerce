package com.example.nrapesh.ecommerce;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

/**
 * Created by VineetR on 12-01-2016.
 */
public class BrowseWatchesActivity extends BrowseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Watches");

    }

}
