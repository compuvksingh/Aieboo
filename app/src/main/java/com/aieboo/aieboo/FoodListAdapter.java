package com.aieboo.aieboo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Adapter for food lists. This can have individual food items as well as horizontal scrollable
 * food item list and the first entry.
 */
public class FoodListAdapter extends RecyclerView.Adapter<FoodItemViewHolder> {

    final private static FoodItemData[] mDataSubset = new FoodItemData[]{
            new FoodItemData("Chilli Paneer", R.drawable.chilli_paneer),
            new FoodItemData("Kadai Paneer", R.drawable.kadai_paneer),
            new FoodItemData("Palak Paneer", R.drawable.palak_paneer),
            new FoodItemData("Paneer bhurji", R.drawable.paneer_bhurji),
            new FoodItemData("Paneer butter masala", R.drawable.paneer_butter_masala),
            new FoodItemData("Paneer cutlet", R.drawable.paneer_cutlet),
            new FoodItemData("Sahi paneer", R.drawable.shahi_paneer),
            new FoodItemData("Tandoori paneer tikka", R.drawable.tandoori_paneer_tikka)
    };

    private FoodItemData[] mDataset;
    private final Context mContext;
    private final Activity activity;

    public FoodListAdapter(FoodItemData[] mDataset, Context context, Activity activity) {
        this.mDataset = mDataset;
        this.mContext = context;
        this.activity = activity;
    }

    @Override
    public FoodItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == FoodItemViewHolder.ViewTypes.MULTIPLE.getPos()) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.food_sublist_view, parent, false);
            FoodItemViewHolder holder = new FoodItemViewHolder(view);
            holder.mHorizontalListView.setHasFixedSize(false);
            holder.mHorizontalListView.setHorizontalScrollBarEnabled(true);

            // use a linear layout manager
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
            mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            holder.mHorizontalListView.setLayoutManager(mLayoutManager);
            return holder;
        }

        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_item_view, parent, false);
        return new FoodItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoodItemViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == FoodItemViewHolder.ViewTypes.MULTIPLE.getPos()) {
            holder.mHorizontalListView.setAdapter(new FoodSubListAdapter(mDataSubset, mContext, activity));
            return;
        }
        final FoodItemData foodItemData = mDataset[position - 1];
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
        return mDataset.length + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0
                ? FoodItemViewHolder.ViewTypes.MULTIPLE.getPos()
                : FoodItemViewHolder.ViewTypes.SINGLE.getPos();
    }
}
