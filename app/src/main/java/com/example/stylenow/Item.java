package com.example.stylenow;

import java.util.List;

public class Item {
    public int id;
    public int storeId;
    public String name;
    public double price;
    public String image;
    public List<String> sizes;

    public Item(int id, int storeId, String name, double price, String image, List<String> sizes) {
        this.id = id;
        this.storeId = storeId;
        this.name = name;
        this.price = price;
        this.image = image;
        this.sizes = sizes;
    }
}
