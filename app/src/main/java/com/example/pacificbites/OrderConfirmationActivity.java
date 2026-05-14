package com.example.pacificbites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class OrderConfirmationActivity extends AppCompatActivity {

    private TextView displayName, displayPhone, displayEmail, displayFood;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        // Initialize views
        displayName = findViewById(R.id.displayName);
        displayPhone = findViewById(R.id.displayPhone);
        displayEmail = findViewById(R.id.displayEmail);
        displayFood = findViewById(R.id.displayFood);
        backButton = findViewById(R.id.backButton);

        // Get data from intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String phone = intent.getStringExtra("phone");
        String email = intent.getStringExtra("email");
        String food = intent.getStringExtra("food");

        // Display data
        displayName.setText(name != null ? name : "N/A");
        displayPhone.setText(phone != null ? phone : "N/A");
        displayEmail.setText(email != null ? email : "N/A");
        displayFood.setText(food != null ? food : "N/A");

        // Back button - returns to MainActivity with empty form
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go back to MainActivity
                Intent intent = new Intent(OrderConfirmationActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}