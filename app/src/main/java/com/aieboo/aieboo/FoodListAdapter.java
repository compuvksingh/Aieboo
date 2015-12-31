package com.aieboo.aieboo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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

    private final Context mContext;
    private final Activity activity;

    public FoodListAdapter(Context context, Activity activity) {
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
        final Resources res = mContext.getResources();
        if (viewType == FoodItemViewHolder.ViewTypes.MULTIPLE.getPos()) {

            int leftOverMargin = getEmptyViewWidth();

            holder.mHorizontalListView.setAdapter(
                    new FoodSubListAdapter(mContext, activity, leftOverMargin));
            return;
        }
        final FoodItemData foodItemData = FoodItemData.PRIMARY_FOOD_LIST[position - 1];
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
                int orientation = res.getConfiguration().orientation;
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

    private int getEmptyViewWidth() {
        final Resources res = mContext.getResources();
        int screenWidthPx = res.getDisplayMetrics().widthPixels -
                res.getDimensionPixelSize(R.dimen.activity_horizontal_margin);

        int cardWidth = res.getDimensionPixelSize(R.dimen.food_card_width) +
                res.getDimensionPixelSize(R.dimen.food_card_margin) +
                res.getDimensionPixelSize(R.dimen.food_card_padding);
        int leftOver = screenWidthPx % cardWidth;
        if (leftOver < cardWidth/4) {
            return leftOver + cardWidth / 4;
        }

        if (leftOver > 3 * cardWidth / 4) {
            return (cardWidth - leftOver + cardWidth / 4);
        }

        return 0;
    }

    @Override
    public int getItemCount() {
        return FoodItemData.PRIMARY_FOOD_LIST.length + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0
                ? FoodItemViewHolder.ViewTypes.MULTIPLE.getPos()
                : FoodItemViewHolder.ViewTypes.SINGLE.getPos();
    }
}
