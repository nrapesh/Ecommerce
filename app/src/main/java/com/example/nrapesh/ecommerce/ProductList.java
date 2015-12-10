package com.example.nrapesh.ecommerce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;

public class ProductList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        ArrayList image_details = getListData();
        final ListView lv1 = (ListView) findViewById(R.id.product_list);
        lv1.setAdapter(new ProductListAdapter(this, image_details));
        lv1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = lv1.getItemAtPosition(position);
                Product product = (Product) o;
                Toast.makeText(ProductList.this, "Selected :" + " " + product, Toast.LENGTH_LONG).show();
            }
        });
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

    private ArrayList getListData() {
        ArrayList<Product> results = new ArrayList<Product>();
        Product product1 = new Product("Description1", 1000);
        Product product2 = new Product("Description2", 2000);
        Product product3 = new Product("Description3", 3000);
        Product product4 = new Product("Description4", 4000);
        Product product5 = new Product("Description5", 5000);
        Product product6 = new Product("Description6", 6000);
        Product product7 = new Product("Description7", 7000);
        Product product8 = new Product("Description8", 8000);
        Product product9 = new Product("Description9", 9000);
        Product product10 = new Product("Description10", 10000);
        Product product11 = new Product("Description11", 11000);
        Product product12 = new Product("Description12", 12000);
        Product product13 = new Product("Description13", 13000);
        Product product14 = new Product("Description14", 14000);
        Product product15 = new Product("Description15", 15000);
        Product product16 = new Product("Description16", 16000);
        Product product17 = new Product("Description17", 17000);
        Product product18 = new Product("Description18", 18000);
        Product product19 = new Product("Description19", 19000);
        Product product20 = new Product("Description20", 20000);


        results.add(product1);
        results.add(product2);
        results.add(product3);
        results.add(product4);
        results.add(product5);
        results.add(product6);
        results.add(product7);
        results.add(product8);
        results.add(product9);
        results.add(product10);
        results.add(product11);
        results.add(product12);
        results.add(product13);
        results.add(product14);
        results.add(product15);
        results.add(product16);
        results.add(product17);
        results.add(product18);
        results.add(product19);
        results.add(product20);

        return results;
    }
}
