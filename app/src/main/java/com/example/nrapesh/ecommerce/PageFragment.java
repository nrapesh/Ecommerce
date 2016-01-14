package com.example.nrapesh.ecommerce;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by VineetR on 12-01-2016.
 */
// In this case, the fragment displays simple text based on the page
public class PageFragment extends Fragment {
    private static final String[] mCategories = new String [] {"HANDBAGS", "SHOES", "DRESSES", "WATCHES", "BY BRAND"} ;
    public static final int HANDBAGS = 1;
    public static final int SHOES = 2;
    public static final int CLOTHING = 3;
    public static final int WATCHES = 4;

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);


        //TextView tvTitle = (TextView) view.findViewById(R.id.browse_tab);
        //tvTitle.setText("Fragment #" + mPage);
        return view;
    }
}