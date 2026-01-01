package com.example.stylenow;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView storeRecyclerView;
    private StoreAdapter storeAdapter;
    private List<Store> storeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Make sure activity_main.xml exists

        storeRecyclerView = findViewById(R.id.storeRecyclerView);
        storeRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchStores();
    }

    private void fetchStores() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/style_data/store.json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                showToast("Failed to load stores");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Store>>() {}.getType();
                    storeList = gson.fromJson(json, type);

                    new Handler(Looper.getMainLooper()).post(() -> {
                        storeAdapter = new StoreAdapter(MainActivity.this, storeList);
                        storeRecyclerView.setAdapter(storeAdapter);
                    });
                } else {
                    showToast("Server returned error");
                }
            }
        });
    }

    private void showToast(String msg) {
        new Handler(Looper.getMainLooper()).post(() ->
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show());
    }
}
