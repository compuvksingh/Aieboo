package com.aieboo.aieboo;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;

public class FoodList extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private FoodItemData[] mDataset = new FoodItemData[]{
            new FoodItemData("Punjabi Samosa", R.drawable.punjabi_samosa, "http://gdurl.com/xoVT"),
            new FoodItemData("Afghani Biryani", R.drawable.afghani_biryani),
            new FoodItemData("Kashmiri Biryani", R.drawable.kashmiri_biryani),
            new FoodItemData("Mughlai Biryani", R.drawable.mughlai_biryani),
            new FoodItemData("Hyderabad Biryani", R.drawable.hyderabad_biryani),
            new FoodItemData("Lucknowi Biryani", R.drawable.lucknowi_biryani)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FoodListAdapter(mDataset, this, this);
        mRecyclerView.setAdapter(mAdapter);
    }
}
