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
    private final int leftOverMargin;

    public FoodSubListAdapter(FoodItemData[] mDataSubset, Context mContext,
                              Activity activity, int leftOverMargin) {
        this.mDataSubset = mDataSubset;
        this.mContext = mContext;
        this.activity = activity;
        this.leftOverMargin = leftOverMargin;
    }

    @Override
    public FoodItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType != FoodItemViewHolder.ViewTypes.EMPTY.getPos()) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.food_item_view, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.food_item_empty_view, parent, false);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width = leftOverMargin;
            view.setLayoutParams(layoutParams);
        }

        return new FoodItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoodItemViewHolder holder, int position) {
        int viewType = getItemViewType(position);

        if (viewType == FoodItemViewHolder.ViewTypes.MULTIPLE.getPos()) {

            return;
        }
        final FoodItemData foodItemData = mDataSubset[position - 1];
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
        return mDataSubset.length + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0
                ? FoodItemViewHolder.ViewTypes.EMPTY.getPos()
                : FoodItemViewHolder.ViewTypes.SINGLE.getPos();
    }
}
