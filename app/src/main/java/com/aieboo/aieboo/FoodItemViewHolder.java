package com.aieboo.aieboo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * View holder for single food item or list of horizontal scrollable food items.
 */
class FoodItemViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    public TextView mTextView;
    public RecyclerView mHorizontalListView;
    public ImageView mImageView;

    public FoodItemViewHolder(View v) {
        super(v);
        mTextView = (TextView) v.findViewById(R.id.food_name);
        mHorizontalListView = (RecyclerView) v.findViewById(R.id.food_sub_list);
        mImageView = (ImageView) v.findViewById(R.id.food_icon);
    }

    enum ViewTypes {
        MULTIPLE(0),
        SINGLE(1);

        private final int pos;

        ViewTypes(int pos) {
            this.pos = pos;
        }

        public int getPos() {
            return pos;
        }
    }
}
