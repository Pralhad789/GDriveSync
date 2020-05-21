package com.example.projectgdsync;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

//ok


public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private static final int REQUEST_CODE_SIGN_IN = 1;
    private static final int REQUEST_CODE_OPEN_DOCUMENT = 2;
    DatabaseHelper myDB;
    private DriveServiceHelper mDriveServiceHelper;
    private String mOpenFileId;
    private EditText editid, editprice, editname, editdate, editValue, editqty, editproduct;
    private Button btnupdate, btnadddata, btndisplay, btn_delete, btnexport, btnopen, btnpdf, btnLogout;
    private TextView textViewDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        myDB = new DatabaseHelper(this);
        editid = findViewById(R.id.edit_id);
        editname = findViewById(R.id.edit_name);
        editprice = findViewById(R.id.edit_price);
        editqty = findViewById(R.id.edit_quantity);
        editproduct = findViewById(R.id.edit_product);
        editdate = findViewById(R.id.edit_date);
        editdate = findViewById(R.id.edit_date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String currentDateandTime = sdf.format(new Date());
        //editdate.setText(currentDateandTime);


        btnLogout = findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(v->{
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        });
        btnopen = findViewById(R.id.open_btn);
        btnopen.setOnClickListener(view -> openFilePicker());

        btnexport = findViewById(R.id.save_btn);
        btnexport.setOnClickListener(view -> {
            try {
                exportData();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        requestSignIn();

        btnupdate = findViewById(R.id.btn_update);
        btnupdate.setOnClickListener(v -> {

            boolean isupdate = myDB.updateData(Integer.parseInt(editid.getText().toString()), editname.getText().toString(), editproduct.getText().toString(), editqty.getText().toString(), editprice.getText().toString(), editdate.getText().toString());

            if (isupdate == true) {
                Toast.makeText(HomeActivity.this, "Data updated", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(HomeActivity.this, "Data not updated", Toast.LENGTH_LONG).show();
            }
        });


        btnadddata = findViewById(R.id.btn_add_data);

        btnadddata.setOnClickListener(v -> {

            boolean isInserted = myDB.insertData(Integer.parseInt(editid.getText().toString()),
                    editname.getText().toString(),
                    editproduct.getText().toString(),
                    editqty.getText().toString(),
                    editprice.getText().toString(),
                    editdate.getText().toString());    //, Integer.parseInt(editprice.getText().toString()), editdate.getText().toString(), Integer.parseInt(editValue.getText().toString()));

            if (isInserted == true) {
                Toast.makeText(HomeActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(HomeActivity.this, "Data not inserted", Toast.LENGTH_LONG).show();
            }

        });

        btndisplay = findViewById(R.id.btn_display);

        btndisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ViewListContents.class);
                startActivity(intent);
            }
        });


        btn_delete = findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              Integer deletedRows = myDB.deleteData(editid.getText().toString());
                                              if (deletedRows > 0)
                                                  Toast.makeText(HomeActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                                              else
                                                  Toast.makeText(HomeActivity.this, "Data not Deleted", Toast.LENGTH_LONG).show();
                                          }
                                      }
        );

        btnpdf = findViewById(R.id.btn_pdf);
        btnpdf.setOnClickListener(view -> {

            CreatePDF();

        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        switch (requestCode) {
            case REQUEST_CODE_SIGN_IN:
                if (resultCode == Activity.RESULT_OK && resultData != null) {
                    handleSignInResult(resultData);
                }
                break;

            case REQUEST_CODE_OPEN_DOCUMENT:
                if (resultCode == Activity.RESULT_OK && resultData != null) {
                    Uri uri = resultData.getData();
                    if (uri != null) {
                        openFileFromFilePicker(uri);
                    }
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, resultData);
    }


    public void exportData() throws InterruptedException {
        Cursor cursor = myDB.getListContents();
        java.io.File sd = Environment.getExternalStorageDirectory();
        String csvFile = "mydata2.xlsx";

        java.io.File directory = new java.io.File(sd.getAbsolutePath());
        //create directory if not exist
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }
        try {
            //file path
            java.io.File file = new java.io.File(directory, csvFile);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(file, wbSettings);

            WritableSheet sheet = workbook.createSheet("userList", 0);
            // column and row
            sheet.addCell(new Label(0, 0, "ID"));
            sheet.addCell(new Label(1, 0, "Name"));

            if (cursor.moveToFirst()) {
                do {
                    String ID = cursor.getString(cursor.getColumnIndex("ID"));
                    String name1 = cursor.getString(cursor.getColumnIndex("Name"));
                    int i = cursor.getPosition() + 1;
                    sheet.addCell(new Label(0, i, ID));
                    sheet.addCell(new Label(1, i, name1));
                } while (cursor.moveToNext());
            }

            cursor.close();
            workbook.write();
            workbook.close();
            Toast.makeText(getApplication(), "Data Exported in a Excel Sheet", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        createFile();
        Thread.sleep(2000);
        saveFile();
    }


    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

    }

    private void requestSignIn() {
        Log.d(TAG, "Requesting sign-in");

        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .requestScopes(new Scope(DriveScopes.DRIVE_FILE))
                        .build();
        GoogleSignInClient client = GoogleSignIn.getClient(this, signInOptions);

        // The result of the sign-in Intent is handled in onActivityResult.
        startActivityForResult(client.getSignInIntent(), REQUEST_CODE_SIGN_IN);
    }

    private void handleSignInResult(Intent result) {
        GoogleSignIn.getSignedInAccountFromIntent(result)
                .addOnSuccessListener(googleAccount -> {
                    Log.d(TAG, "Signed in as " + googleAccount.getEmail());

                    // Use the authenticated account to sign in to the Drive service.
                    GoogleAccountCredential credential =
                            GoogleAccountCredential.usingOAuth2(
                                    this, Collections.singleton(DriveScopes.DRIVE_FILE));
                    credential.setSelectedAccount(googleAccount.getAccount());
                    Drive googleDriveService =
                            new Drive.Builder(
                                    AndroidHttp.newCompatibleTransport(),
                                    new GsonFactory(),
                                    credential)
                                    .setApplicationName("Drive API Migration")
                                    .build();


                    mDriveServiceHelper = new DriveServiceHelper(googleDriveService);
                })
                .addOnFailureListener(exception -> Log.e(TAG, "Unable to sign in.", exception));
    }


    private void openFilePicker() {
        if (mDriveServiceHelper != null) {
            Log.d(TAG, "Opening file picker.");

            Intent pickerIntent = mDriveServiceHelper.createFilePickerIntent();

            // The result of the SAF Intent is handled in onActivityResult.
            startActivityForResult(pickerIntent, REQUEST_CODE_OPEN_DOCUMENT);
        }
    }


    private void openFileFromFilePicker(Uri uri) {
        if (mDriveServiceHelper != null) {
            Log.d(TAG, "Opening " + uri.getPath());

            mDriveServiceHelper.openFileUsingStorageAccessFramework(getContentResolver(), uri)
                    .addOnSuccessListener(nameAndContent -> {
                        String[] content = nameAndContent.second.split(";");
//
                        myDB = new DatabaseHelper(HomeActivity.this);
                        for (int i = 1; i < content.length; i++) {
                            String[] data = content[i].split(",");
                            //Log.i(TAG, "DATA FROM GOOGLE DRIVE: " + content.clone());
                            myDB.insertData(Integer.parseInt(data[0]), data[1], data[2], data[3], data[4], data[5]);              //, Integer.parseInt(data[2]), data[3], Integer.parseInt(data[4]));
                            //myDB.insertDat;
                        }
                        Toast.makeText(HomeActivity.this, "Imported from Google Drive", Toast.LENGTH_LONG).show();
                    })
                    .addOnFailureListener(exception ->
                            Log.e(TAG, "Unable to open file from picker.", exception));
        }
    }

    /**
     * Creates a new file via the Drive REST API.
     */
    private void createFile() {
        if (mDriveServiceHelper != null) {
            Log.d(TAG, "Creating a file.");

            mDriveServiceHelper.createFile()
                    .addOnSuccessListener(fileId ->
                            readFile(fileId)
                    )
                    .addOnFailureListener(exception ->
                            Log.e(TAG, "Couldn't create file.", exception));
        }
        assert mDriveServiceHelper != null;
        mOpenFileId = mDriveServiceHelper.id;
        Toast.makeText(HomeActivity.this, "File created, Start typing...", Toast.LENGTH_LONG).show();
    }

    /**
     * Retrieves the title and content of a file identified by {@code fileId} and populates the UI.
     */
    private void readFile(String fileId) {
        if (mDriveServiceHelper != null) {
            Log.d(TAG, "Reading file " + fileId);

            mDriveServiceHelper.readFile(fileId)
                    .addOnSuccessListener(nameAndContent -> {

                        setReadWriteMode(fileId);
                        saveFile();
                    })
                    .addOnFailureListener(exception ->
                            Log.e(TAG, "Couldn't read file.", exception));
        }
    }

    /**
     * Saves the currently opened file created via {@link #createFile()} if one exists.
     */
    private void saveFile() {
        if (mDriveServiceHelper != null && mOpenFileId != null) {
            Log.d(TAG, "Saving " + mOpenFileId);

            String fileName = "myData1";
            Cursor cursor = myDB.getListContents();
            String fileContent = ";";
            //StringBuilder fc = new StringBuilder();

            while (cursor.moveToNext()) {
                fileContent += cursor.getString(0) + "," + cursor.getString(1) + "," + cursor.getString(2) + "," + cursor.getString(3) + "," + cursor.getString(4) + "," + cursor.getString(5) + ";\n";  //+","+cursor.getString(2)+","+cursor.getString(3)+","+cursor.getString(4)+"\n";
                //fc.append(cursor.getString(0)).append(cursor.getString(1));
            }
            //String fileContent = fc.toString();
            cursor.close();


            mDriveServiceHelper.saveFile(mOpenFileId, fileName, fileContent)
                    .addOnSuccessListener(v -> Toast.makeText(HomeActivity.this, "File Saved to Drive!", Toast.LENGTH_LONG).show())
                    .addOnFailureListener(exception ->
                            Log.e(TAG, "Unable to save file via REST.", exception));
        }

    }


    private void setReadOnlyMode() {
        mOpenFileId = null;
    }


    private void setReadWriteMode(String fileId) {
        mOpenFileId = fileId;
    }

    public void CreatePDF() {
        String query = "Select * from firsttable";
        Cursor cursor = myDB.db.rawQuery(query, null);
        try {
            cursor.moveToFirst();
            //textViewDisplay.setText(cursor.getString(0));
            Toast.makeText(HomeActivity.this, "PDF created", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            //textViewDisplay.setText("");
            return;
        }

        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        String filePath = Environment.getExternalStorageDirectory().getPath() + "/Download/" + editid.getText().toString() + ".pdf";
        File file = new File(filePath);
        page.getCanvas().drawText("Report: ",0,20,new Paint());
        if (cursor.moveToFirst()) {
            int i=1;
            do {
                page.getCanvas().drawText("ID: ",0,25*i+25,new Paint());
                page.getCanvas().drawText(cursor.getString(0), 55, 25*i+25, new Paint());

                page.getCanvas().drawText("Name: ",0,25*i+45,new Paint());
                page.getCanvas().drawText(cursor.getString(1), 55, 25*i+45, new Paint());

                page.getCanvas().drawText("Product: ",0,25*i+65,new Paint());
                page.getCanvas().drawText(cursor.getString(2), 55, 25*i+65, new Paint());

                page.getCanvas().drawText("Quantity: ",0,25*i+85,new Paint());
                page.getCanvas().drawText(cursor.getString(3), 55, 25*i+85, new Paint());

                page.getCanvas().drawText("Price: ",0,25*i+105,new Paint());
                page.getCanvas().drawText(cursor.getString(4), 55, 25*i+105, new Paint());

                page.getCanvas().drawText("Date: ",0,25*i+125,new Paint());
                page.getCanvas().drawText(cursor.getString(5), 55, 25*i+125, new Paint());

                page.getCanvas().drawText("---------------------------------------------------",0,25*i+145,new Paint());

                i++;

            } while (cursor.moveToNext());
        }

        pdfDocument.finishPage(page);
        try {
            pdfDocument.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pdfDocument.close();
    }
}