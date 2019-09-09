package com.parindo;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView lblNombre;
    private ImageView qrBox;
    private DBController dbController;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        qrBox = (ImageView) findViewById(R.id.qrBox);
        dbController = (DBController) new DBController(this,"",null,1);
        lblNombre = (TextView) findViewById(R.id.lblNombre);


    }

    @Override
    protected void onStart() {
        super.onStart();

        if(dbController.SelectUserData()==null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Datos Faltantes");
            builder.setMessage("Debe Completar el formulario para continuar");
            builder.setCancelable(false);


            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(getApplicationContext(),NewUserForm.class));
                    finish();
                }
            });


            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            builder.show();
        }else{
            lblNombre.setText("Bienvenido: "+dbController.SelectUserData()[0]);

            try {
                BitMatrix bitMatrix = new MultiFormatWriter().encode(dbController.SelectUserData()[1], BarcodeFormat.QR_CODE,1000,1000);
                BarcodeEncoder barcodeEncoder=  new BarcodeEncoder();
                Bitmap bitmap =barcodeEncoder.createBitmap(bitMatrix);
                qrBox.setImageBitmap(bitmap);

            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    private void LoadName(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Usuarios").child(dbController.SelectCedula()).child("NombreCompleto");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lblNombre.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    */


}
