package com.example.projectgdsync;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SalesPage extends AppCompatActivity {

    Button seestockbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_page);

        seestockbtn = findViewById(R.id.see_stock);

        seestockbtn.setOnClickListener(v -> {
            startActivity(new Intent(SalesPage.this, StocksPage.class));
            finish();
        });
    }
}