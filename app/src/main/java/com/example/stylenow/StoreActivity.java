package com.example.stylenow;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
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
import android.os.Handler;
import android.os.Looper;

public class StoreActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StoreAdapter adapter;
    private List<Store> storeList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        recyclerView = findViewById(R.id.recyclerViewStores);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        storeList = new ArrayList<>();
        adapter = new StoreAdapter(this, storeList);
        recyclerView.setAdapter(adapter);

        fetchStores();
    }

    private void fetchStores() {
        new Thread(() -> {
            try {
                URL url = new URL("http://10.0.2.2:8080/style_data/store.json");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream())
                    );
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    JSONArray jsonArray = new JSONArray(response.toString());
                    storeList.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        int id = obj.getInt("id");
                        String name = obj.getString("name");
                        String logo = obj.getString("logo");

                        storeList.add(new Store(id, name, logo));
                    }

                    // Update UI on main thread
                    new Handler(Looper.getMainLooper()).post(() -> adapter.notifyDataSetChanged());

                } else {
                    showToast("Failed to fetch stores: " + responseCode);
                }
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                showToast("Error: " + e.getMessage());
            }
        }).start();
    }

    private void showToast(String msg) {
        new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(StoreActivity.this, msg, Toast.LENGTH_LONG).show());
    }
}
