package com.aieboo.aieboo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Adapter for food lists. This can have horizontal scrollable food item list.
 */
public class FoodSubListAdapter extends RecyclerView.Adapter<FoodItemViewHolder> {
    private final FoodItemData[] mDataSubset;
    private Context mContext;
    private final Activity activity;

    public FoodSubListAdapter(FoodItemData[] mDataSubset, Context mContext, Activity activity) {
        this.mDataSubset = mDataSubset;
        this.mContext = mContext;
        this.activity = activity;
    }

    @Override
    public FoodItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_item_view, parent, false);
        return new FoodItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoodItemViewHolder holder, int position) {
        final FoodItemData foodItemData = mDataSubset[position];
        holder.mTextView.setText(foodItemData.name);
        holder.mImageView.setImageResource(foodItemData.resourceId);
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Interesting data to pass across are the thumbnail size/location, the
                // resourceId of the source bitmap, the picture description, and the
                // orientation (to avoid returning back to an obsolete configuration if
                // the device rotates again in the meantime)
                int[] screenLocation = new int[2];
                view.getLocationOnScreen(screenLocation);
                Intent subActivity = new Intent(mContext, FoodItemDetailsActivity.class);
                int orientation = mContext.getResources().getConfiguration().orientation;
                subActivity.
                        putExtra(FoodItemDetailsActivity.PACKAGE + ".orientation", orientation).
                        putExtra(FoodItemDetailsActivity.PACKAGE + ".resourceId", foodItemData.resourceId).
                        putExtra(FoodItemDetailsActivity.PACKAGE + ".left", screenLocation[0]).
                        putExtra(FoodItemDetailsActivity.PACKAGE + ".top", screenLocation[1]).
                        putExtra(FoodItemDetailsActivity.PACKAGE + ".width", view.getWidth()).
                        putExtra(FoodItemDetailsActivity.PACKAGE + ".height", view.getHeight()).
                        putExtra(FoodItemDetailsActivity.PACKAGE + ".description", foodItemData.name);
                mContext.startActivity(subActivity);

                // Override transitions: we don't want the normal window animation in addition
                // to our custom one
                activity.overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSubset.length;
    }

}
