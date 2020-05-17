package com.example.projectgdsync;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Created by Mitch on 2016-05-13.
 */
public class ViewListContents extends AppCompatActivity {

    DatabaseHelper myDB;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewlistcontents_layout);

        ListView listView = (ListView) findViewById(R.id.listView);
        myDB = new DatabaseHelper(this);

        //populate an ArrayList<String> from the database and then view it
        ArrayList<User> theList = new ArrayList<>();

        Cursor data = myDB.getListContents();
        if(data.getCount() == 0){
            Toast.makeText(this, "There are no contents in this list!",Toast.LENGTH_LONG).show();
        }
        else
            {
            int i=0;
            while(data.moveToNext()){
                user = new User(data.getString(0),data.getString(1));
                theList.add(i,user);
//                System.out.println(data.getString(0)+" "+data.getString(1)+" ");
//                System.out.println(theList.get(i).toString());
                i++;
//                theList.add(data.getString(1));

            }

                ThreeColumn_ListAdapter adapter =  new ThreeColumn_ListAdapter(this,R.layout.list_adapter_view, theList);
//                ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,theList);
                listView.setAdapter(adapter);
        }


    }
}