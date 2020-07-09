package com.example.projectgdsync;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ItemsPage extends AppCompatActivity {

    EditText itemcode, itemname, itemcategory, itemstockqty, itemsaleqty, itemrecvqty;
    Button itemsubmit;

    FirebaseAuth mAuth;
    ItemMaster itemmaster;
    DatabaseReference reff;
    long maxid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_page);

        itemcode = findViewById(R.id.edit_itemcode);
        itemname = findViewById(R.id.edit_itemname);
        itemcategory = findViewById(R.id.edit_itemcategory);
        itemstockqty = findViewById(R.id.edit_itemstockqty);
        itemsaleqty = findViewById(R.id.edit_itemsaleqty);
        itemrecvqty = findViewById(R.id.edit_itemrecievedqty);
        itemmaster = new ItemMaster();

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        reff = FirebaseDatabase.getInstance().getReference().child("ItemDetails");

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    maxid = (dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        itemsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                itemmaster.setItemcode(itemcode.getText().toString().trim());
                itemmaster.setItemname(itemname.getText().toString().trim());
                itemmaster.setItemcategory(itemcategory.getText().toString().trim());
                itemmaster.setItemstockqty(itemstockqty.getText().toString().trim());
                itemmaster.setItemsaleqty(itemsaleqty.getText().toString().trim());
                itemmaster.setItemrecvqty(itemrecvqty.getText().toString().trim());

               ;

                reff.child("ItemDetails").child(uid).child(String.valueOf(maxid+1)).setValue(itemmaster);

                Toast.makeText(ItemsPage.this, "Data Inserted Successfully",Toast.LENGTH_LONG).show();
            }
        });


    }
}