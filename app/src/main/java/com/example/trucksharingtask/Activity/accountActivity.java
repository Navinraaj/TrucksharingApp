/*
Author: Navin Raaj
*/

package com.example.trucksharingtask.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.trucksharingtask.Database.DataBase;
import com.example.trucksharingtask.R;
import com.example.trucksharingtask.Views.Maps;

import java.io.ByteArrayInputStream;
import java.util.BitSet;

public class accountActivity extends AppCompatActivity {
    int UID; // User ID
    Intent intentReceive;

    TextView name, username, phone;
    ImageView image;
    BitSet byteStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        DataBase db = new DataBase(this); // Create a database instance
        intentReceive = getIntent();
        UID = intentReceive.getIntExtra("UID", 0); // Retrieve the user ID from the intent

        DataBase.MyResult result = db.GUS(UID); // Retrieve user information from the database based on the ID

        Log.v("test", String.valueOf(result.name)); // Log the user's name for debugging purposes

        name = findViewById(R.id.Name); // Find the TextView for the name
        username = findViewById(R.id.username); // Find the TextView for the username
        phone = findViewById(R.id.phone); // Find the TextView for the phone number

        name.setText(String.valueOf(result.name)); // Set the user's name in the name TextView
        phone.setText(String.valueOf(result.phone)); // Set the user's phone number in the phone TextView
        username.setText(String.valueOf(result.username)); // Set the user's username in the username TextView

        // Display the user's image if available
        if (result.imageUrl != null && !result.imageUrl.isEmpty()) {
            Glide.with(this)
                    .load(result.imageUrl)
                    .into(image);
        }
    }



    @Override
    public void onBackPressed() {
        finish(); // Finish the activity when the back button is pressed
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu); // Inflate the menu layout and add it to the menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_my_location:
                // Start the Maps activity
                Intent intent = new Intent(accountActivity.this, Maps.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.nav_home:
                // Start the HomeActivity
                Intent intentHome = new Intent(accountActivity.this, HomeActivity.class);
                startActivity(intentHome);
                finish();
                return true;
            case R.id.nav_account:
                // Start the accountActivity with the current user's ID
                Intent intentAccount = new Intent(accountActivity.this, accountActivity.class);
                intentAccount.putExtra("UID", UID);
                startActivity(intentAccount);
                finish();
                return true;
            case R.id.nav_my_orders:
                // Start the HomeActivity
                Intent intentOrders = new Intent(accountActivity.this, HomeActivity.class);
                startActivity(intentOrders);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
