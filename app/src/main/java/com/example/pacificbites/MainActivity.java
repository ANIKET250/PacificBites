package com.example.pacificbites;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText nameInput, phoneInput, emailInput;
    private Spinner foodSpinner;
    private Button placeOrderButton;
    private String selectedFood = "";
    private boolean isFirstSelection = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        nameInput = findViewById(R.id.nameInput);
        phoneInput = findViewById(R.id.phoneInput);
        emailInput = findViewById(R.id.emailInput);
        foodSpinner = findViewById(R.id.foodSpinner);
        placeOrderButton = findViewById(R.id.placeOrderButton);

        // Setup spinner
        setupSpinner();

        // Place order button click
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndPlaceOrder();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Clear form every time MainActivity is shown (after coming back from confirmation)
        clearForm();
    }

    private void clearForm() {
        // Clear all input fields
        nameInput.setText("");
        phoneInput.setText("");
        emailInput.setText("");

        // Reset spinner to first position (Please select)
        foodSpinner.setSelection(0);

        // Reset selected food
        selectedFood = "";
        isFirstSelection = true;

        // Clear any error messages
        clearErrors();
    }

    private void setupSpinner() {
        // Create food items list with emojis and prices
        final List<String> foodItems = new ArrayList<>();
        foodItems.add("📋 Please select a food item");
        foodItems.add("🍗 Chicken Palau - $12.99");
        foodItems.add("🍖 Lamb Palau - $15.99");
        foodItems.add("🍕 Pizza - $10.99");
        foodItems.add("🍜 Chow Mein - $9.99");

        // Create adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, foodItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        foodSpinner.setAdapter(adapter);

        // Handle item selection
        foodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirstSelection) {
                    isFirstSelection = false;
                    selectedFood = "";
                } else if (position > 0) {
                    String selected = foodItems.get(position);
                    // Extract food name without emoji and price
                    if (selected.contains("🍗")) {
                        selectedFood = "Chicken Palau";
                    } else if (selected.contains("🍖")) {
                        selectedFood = "Lamb Palau";
                    } else if (selected.contains("🍕")) {
                        selectedFood = "Pizza";
                    } else if (selected.contains("🍜")) {
                        selectedFood = "Chow Mein";
                    }
                    Toast.makeText(MainActivity.this, "Selected: " + selectedFood, Toast.LENGTH_SHORT).show();
                } else {
                    selectedFood = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedFood = "";
            }
        });
    }

    private void validateAndPlaceOrder() {
        String name = nameInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();

        // Clear previous errors
        clearErrors();

        boolean isValid = true;

        // Validate name
        if (TextUtils.isEmpty(name)) {
            nameInput.setError("Customer name is required");
            nameInput.requestFocus();
            isValid = false;
        }

        // Validate phone
        if (TextUtils.isEmpty(phone)) {
            phoneInput.setError("Phone number is required");
            if (isValid) phoneInput.requestFocus();
            isValid = false;
        } else if (!isValidPhone(phone)) {
            phoneInput.setError("Enter 10-15 digit phone number");
            if (isValid) phoneInput.requestFocus();
            isValid = false;
        }

        // Validate email
        if (TextUtils.isEmpty(email)) {
            emailInput.setError("Email address is required");
            Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT).show();
            if (isValid) emailInput.requestFocus();
            isValid = false;
        } else if (!isValidEmail(email)) {
            emailInput.setError("Enter valid email address (e.g., name@example.com)");
            Toast.makeText(this, "Invalid email format! Example: name@domain.com", Toast.LENGTH_LONG).show();
            if (isValid) emailInput.requestFocus();
            isValid = false;
        }

        // Validate food selection
        if (TextUtils.isEmpty(selectedFood)) {
            Toast.makeText(this, "Please select a food item", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        // If all valid, proceed to confirmation
        if (isValid) {
            proceedToConfirmation(name, phone, email);
        }
    }

    private void clearErrors() {
        nameInput.setError(null);
        phoneInput.setError(null);
        emailInput.setError(null);
    }

    private boolean isValidPhone(String phone) {
        return phone.matches("^[0-9]{10,15}$");
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.compile(emailPattern).matcher(email).matches();
    }

    private void proceedToConfirmation(String name, String phone, String email) {
        Intent intent = new Intent(MainActivity.this, OrderConfirmationActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("phone", phone);
        intent.putExtra("email", email);
        intent.putExtra("food", selectedFood);
        startActivity(intent);
    }
}