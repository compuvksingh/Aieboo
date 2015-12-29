package com.aieboo.aieboo;

/**
 * Data for food item.
 */
public class FoodItemData {
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
