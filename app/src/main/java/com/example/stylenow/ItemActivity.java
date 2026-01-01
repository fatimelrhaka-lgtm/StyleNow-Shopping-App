package com.example.stylenow;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ItemActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Item> itemList;
    private ItemAdapter adapter;
    private int storeId;
    private String storeName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        storeId = getIntent().getIntExtra("storeId", 0);
        storeName = getIntent().getStringExtra("storeName");

        setTitle(storeName);

        recyclerView = findViewById(R.id.recyclerViewItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemList = new ArrayList<>();
        adapter = new ItemAdapter(this, itemList);
        recyclerView.setAdapter(adapter);

        // ✅ Fix: Add FAB Cart button click to go to CartActivity
        FloatingActionButton fabCart = findViewById(R.id.fabCart);
        fabCart.setOnClickListener(v -> {
            Intent intent = new Intent(ItemActivity.this, CartActivity.class);
            startActivity(intent);
        });

        fetchItemsFromServer();
    }

    private void fetchItemsFromServer() {
        new Thread(() -> {
            try {
                URL url = new URL("http://10.0.2.2:8080/style_data/style_items.json");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    JSONArray jsonArray = new JSONArray(result.toString());
                    itemList.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        if (obj.getInt("storeId") == storeId) {
                            int id = obj.getInt("id");
                            String name = obj.getString("name");
                            double price = obj.getDouble("price");
                            String image = obj.getString("image");

                            JSONArray sizesJsonArray = obj.getJSONArray("sizes");
                            List<String> sizes = new ArrayList<>();
                            for (int j = 0; j < sizesJsonArray.length(); j++) {
                                sizes.add(sizesJsonArray.getString(j));
                            }

                            itemList.add(new Item(id, storeId, name, price, image, sizes));
                        }
                    }

                    new Handler(Looper.getMainLooper()).post(() -> adapter.notifyDataSetChanged());

                } else {
                    showToast("Failed to fetch items.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                showToast("Error: " + e.getMessage());
            }
        }).start();
    }

    private void showToast(String message) {
        new Handler(Looper.getMainLooper()).post(() ->
                Toast.makeText(ItemActivity.this, message, Toast.LENGTH_LONG).show());
    }
}
