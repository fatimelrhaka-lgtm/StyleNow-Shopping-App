package com.example.stylenow;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        ImageView qrCode = findViewById(R.id.qrImage);
        TextView payText = findViewById(R.id.payText);
        TextView addressText = findViewById(R.id.addressText);

        qrCode.setImageResource(R.drawable.qr_code); // Put qr_code.png in res/drawable/
        payText.setText("Scan this QR code to complete your payment");

        // Show address if passed
        String address = getIntent().getStringExtra("deliveryAddress");
        if (address != null && !address.isEmpty()) {
            addressText.setText("Deliver to: " + address);
        } else {
            addressText.setText("No address provided");
        }
    }
}


