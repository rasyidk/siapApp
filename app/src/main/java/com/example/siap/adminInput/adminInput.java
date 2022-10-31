package com.example.siap.adminInput;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siap.R;
import com.example.siap.adminActivity.adminActivity;
import com.example.siap.setModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class adminInput extends AppCompatActivity {


    EditText et_noreg,et_pangkatnm,et_satuan,et_pasal,et_noputusan,et_noeksekusi,et_tglarsip,et_norak,et_noarsip;
    String  noreg,pangkatnm,satuan,pasal,noputusan,noeksekusi,tglarsip, nameFile, noarsip,norak;
    Calendar myCalendar,myCalendar2;
    Button btn_arsip;
    TextView tv_nameFile,tv_lihat;
    ImageView imgAttach;

    String finalDisplayName;
    Uri xuri;

    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_input);

        myCalendar = Calendar.getInstance();
        myCalendar2 = Calendar.getInstance();

        btn_arsip = findViewById(R.id.input_btnArsip);

        tv_nameFile = findViewById(R.id.input_tvNameFile);
        tv_lihat =  findViewById(R.id.input_tv_lihat);

        imgAttach =  findViewById(R.id.input_imgAttach);

//        Edittext
        et_noreg = findViewById(R.id.input_edt_noreg);
        et_pangkatnm = findViewById(R.id.input_edt_pangkatnm);
        et_satuan =  findViewById(R.id.input_edt_satuan);
        et_pasal = findViewById(R.id.input_edt_pasal);
        et_noputusan =  findViewById(R.id.input_edt_noputusan);
        et_noeksekusi =  findViewById(R.id.input_edt_noeksekusi);
        et_tglarsip =  findViewById(R.id.input_edt_tglarsip);
        et_norak =  findViewById(R.id.input_edt_norak);
        et_noarsip =  findViewById(R.id.input_edt_noarsip);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("arsipDataBaru2");

        imgAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPDF();
            }
        });

        tv_lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =  new Intent(adminInput.this, adminActivity.class);
                startActivity(i);
            }
        });

        arsipkanData();
        setDate();

    }

    private void selectPDF() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"pdf file select"),12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 12 && resultCode==RESULT_OK && data!= null && data.getData()!= null){

            Uri uri = data.getData();
            String uriString = uri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            String displayName = null;

            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
            }

            Log.d("dn", displayName);
            finalDisplayName = displayName;
            tv_nameFile.setText(displayName);
            xuri = data.getData();

        }
    }

    private void arsipkanData() {
        btn_arsip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                noreg = et_noreg.getText().toString();
                pangkatnm = et_pangkatnm.getText().toString();
                satuan = et_satuan.getText().toString();
                pasal =  et_pasal.getText().toString();
                noputusan = et_noputusan.getText().toString();
                noeksekusi = et_noeksekusi.getText().toString();
                tglarsip = et_tglarsip.getText().toString();
                noarsip =  et_noarsip.getText().toString();
                norak = et_norak.getText().toString();

//                Toast.makeText(getApplicationContext(),"AHAH",Toast.LENGTH_LONG).show();
                nameFile = tv_nameFile.getText().toString();

                if (noreg.equals("") || pangkatnm.equals("") || satuan.equals("") || pasal.equals("") || noputusan.equals("") || noeksekusi.equals("") || tglarsip.equals("") || norak.equals("") || noarsip.equals("")){
                    Toast.makeText(getApplicationContext(),"Isi Semua Data Terlebih Dahulu",Toast.LENGTH_LONG).show();
                }else {
                    if (nameFile.equals("Unggah data yang sesuai") || xuri == null){
                        Toast.makeText(getApplicationContext(),"Tambahkan File Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    }else {
                        String newNameFile = noreg +"_"+pangkatnm+"_"+satuan+"_"+pasal+"_"+noputusan+"_"+noeksekusi+"_"+tglarsip+"_"+ norak +"_"+ noarsip +"_"+nameFile;
                        UploadPDFFirebase(xuri, newNameFile);
                    }
                }
            }
        });
    }




    private void UploadPDFFirebase(Uri data, String finalDisplayName) {
        final ProgressDialog progressDialog =  new ProgressDialog(this);
        progressDialog.setTitle("File Is loading");
        progressDialog.show();

        StorageReference reference= storageReference.child(finalDisplayName);

        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri uri = uriTask.getResult();

                        setModel setModel = new setModel(finalDisplayName, uri.toString(),noreg,pangkatnm,satuan,pasal,noputusan,noeksekusi,tglarsip,norak,noarsip);
                        databaseReference.child(databaseReference.push().getKey()).setValue(setModel);
                        Toast.makeText(adminInput.this, "Unggah File Berhasil", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0* taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                progressDialog.setMessage("Mengunggah File " + (int) progress + "%");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Unggahan Gagal",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }

    private void setDate() {

        DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelTglArsip();
            }

        };



        et_tglarsip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(adminInput.this, date2, myCalendar2
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }



    private void updateLabelTglArsip() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        et_tglarsip.setText(sdf.format(myCalendar.getTime()));
    }


}