package com.aieboo.aieboo;

/**
 * Data for food item.
 */
public class FoodItemData {
    final static FoodItemData[] PRIMARY_FOOD_LIST = new FoodItemData[]{
            new FoodItemData("Punjabi Samosa", R.drawable.punjabi_samosa, "http://gdurl.com/xoVT"),
            new FoodItemData("Afghani Biryani", R.drawable.afghani_biryani),
            new FoodItemData("Kashmiri Biryani", R.drawable.kashmiri_biryani),
            new FoodItemData("Mughlai Biryani", R.drawable.mughlai_biryani),
            new FoodItemData("Hyderabad Biryani", R.drawable.hyderabad_biryani),
            new FoodItemData("Lucknowi Biryani", R.drawable.lucknowi_biryani)
    };

    final static FoodItemData[] SECONDARY_FOOD_LIST = new FoodItemData[]{
            new FoodItemData("Chilli Paneer", R.drawable.chilli_paneer),
            new FoodItemData("Kadai Paneer", R.drawable.kadai_paneer),
            new FoodItemData("Palak Paneer", R.drawable.palak_paneer),
            new FoodItemData("Paneer bhurji", R.drawable.paneer_bhurji),
            new FoodItemData("Paneer butter masala", R.drawable.paneer_butter_masala),
            new FoodItemData("Paneer cutlet", R.drawable.paneer_cutlet),
            new FoodItemData("Sahi paneer", R.drawable.shahi_paneer),
            new FoodItemData("Tandoori paneer tikka", R.drawable.tandoori_paneer_tikka)
    };


    public String name;
    public int resourceId;
    public String url;

    public FoodItemData(String name, int resourceId) {
        this(name, resourceId, null);
    }

    public FoodItemData(String name, int resourceId, String url) {
        this.name = name;
        this.resourceId = resourceId;
        this.url = url;
    }
}
