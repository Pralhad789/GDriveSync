package com.example.projectgdsync;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {
    Button btnMaster, btnTransactions, btnReport, btnSale, btnStock, btnItems, btnCustomer;
    LinearLayout viewMaster, viewTransactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnMaster = findViewById(R.id.btn_master);
        btnTransactions = findViewById(R.id.btn_transactions);
        btnSale = findViewById(R.id.btn_sale);
        btnStock = findViewById(R.id.btn_stock);
        btnItems = findViewById(R.id.btn_items);
        btnCustomer = findViewById(R.id.btn_customer);
        btnReport = findViewById(R.id.btn_report);

        viewMaster = findViewById(R.id.view_master);
        viewTransactions = findViewById(R.id.view_transactions);

        btnMaster.setOnClickListener(v -> {
            viewTransactions.setVisibility(View.GONE);
            viewMaster.setVisibility(View.VISIBLE);
        });
        btnTransactions.setOnClickListener(v -> {
            viewMaster.setVisibility(View.GONE);
            viewTransactions.setVisibility(View.VISIBLE);
        });

        btnItems.setOnClickListener(v -> {
            startActivity(new Intent(Menu.this, ItemsPage.class));
            finish();
        });

        btnCustomer.setOnClickListener(v -> {
            startActivity(new Intent(Menu.this, CustomersPage.class));
            finish();
        });
        btnSale.setOnClickListener(v -> {
            startActivity(new Intent(Menu.this, SalesPage.class));
            finish();
        });
        btnStock.setOnClickListener(v -> {
            startActivity(new Intent(Menu.this, StocksPage.class));
            finish();
        });
        btnReport.setOnClickListener(v -> {
            startActivity(new Intent(Menu.this, ReportsPage.class));
            finish();
        });
    }
}