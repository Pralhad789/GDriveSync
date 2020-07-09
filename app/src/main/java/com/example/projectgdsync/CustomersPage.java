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

public class CustomersPage extends AppCompatActivity {
    private DatabaseReference mDatabase;

    EditText custcode,custname, custaddress, custbalance, custsaleamt, custrecievedamt;
    Button custsubmit;
    FirebaseAuth mAuth;
    CustomerMaster custmaster;
    DatabaseReference reff;
    long maxid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers_page);

        mAuth = FirebaseAuth.getInstance();

        custcode = findViewById(R.id.edit_custcode);
        custname = findViewById(R.id.edit_custname);
        custaddress = findViewById(R.id.edit_custaddress);
        custbalance = findViewById(R.id.edit_custbalance);
        custsaleamt = findViewById(R.id.edit_custsaleamt);
        custrecievedamt = findViewById(R.id.edit_custrecievedamt);
        custsubmit = findViewById(R.id.btn_addcustdata);
        custmaster = new CustomerMaster();

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        reff = FirebaseDatabase.getInstance().getReference().child("CustomerDetails");

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

        custsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                custmaster.setCustCode(custcode.getText().toString().trim());
                custmaster.setCustName(custname.getText().toString().trim());
                custmaster.setCustaddress(custaddress.getText().toString().trim());
                custmaster.setCustBalance(custbalance.getText().toString().trim());
                custmaster.setCustSaleamt(custsaleamt.getText().toString().trim());
                custmaster.setCustRecievedamt(custrecievedamt.getText().toString().trim());

                reff.child("CustomerDetails").child(uid).child(String.valueOf(maxid+1)).setValue(custmaster);

                Toast.makeText(CustomersPage.this, "Data Inserted Successfully",Toast.LENGTH_LONG).show();
            }
        });





//        custsubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                FirebaseDatabase.getInstance().getReference(uid).addListenerForSingleValueEvent(new ValueEventListener() {
//
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
////                long points = dataSnapshot.child("Points").getValue(Long.class);
//                        addcustomerdetails();
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        throw databaseError.toException();
//                    }
//                });
//
//            }
//        });
    }

    private void addcustomerdetails()
    {

    }

}