package com.parindo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class NewUserForm extends AppCompatActivity {

    ////////////////////////////////////////////////////////Variables Globales////////////////////////////////////////////////////////////
    private TextView txtNombreCompleto,txtCedula,txtNumeroTelefono;
    private Button btnAgregar,btnCancelar;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;
    public  DBController dbController;
    ////////////////////////////////////////////////////////Variables Globales////////////////////////////////////////////////////////////



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_form);
        getSupportActionBar().hide();

        txtNombreCompleto = (TextView) findViewById(R.id.txtNombreCompleto);
        txtCedula = (TextView) findViewById(R.id.txtCedula);
        txtNumeroTelefono = (TextView) findViewById(R.id.txtNumeroTelefono);

        btnAgregar = (Button) findViewById(R.id.btnAgregar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        dbController= (DBController) new DBController(this,"",null,1);
    }




    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onResume() {
        super.onResume();

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AgregarUsuario();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenMainActivity();
                CloseThis();
            }
        });

    }

    public void OpenMainActivity(){
        startActivity(new Intent(this,MainActivity.class));
    }


    public void CloseThis(){
        this.finish();
    }




    public void AgregarUsuario(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Guardar");
        builder.setMessage("Desea guardar los cambios?");
        builder.setCancelable(false);


        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference referencia = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(txtCedula.getText().toString());
                referencia.child("NombreCompleto").setValue(txtNombreCompleto.getText().toString());
                referencia.child("Cedula").setValue(txtCedula.getText().toString());
                referencia.child("CodigoQR").setValue(txtCedula.getText().toString());
                referencia.child("NumeroTelefono").setValue(txtNumeroTelefono.getText().toString());

                dbController.InsertUserData(txtNombreCompleto.getText().toString(),txtCedula.getText().toString());

                Toast.makeText(getApplicationContext(), "Informacion Guardada Exitosamente", Toast.LENGTH_SHORT).show();
            }
        });


        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }



    /*SelectUser
    private void GetUser(){

        FirebaseDatabase.getInstance().getReference("Empleados").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList = new ArrayList<String>();
                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
                //lsList.setAdapter(adapter);

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    try{
                        String NombreCompleto=data.child("NombreCompleto").getValue().toString();
                        for(int i=NombreCompleto.length(); i<50;i++){
                            NombreCompleto+=".";
                        }
                        arrayList.add(NombreCompleto + data.child("Cedula").getValue().toString());
                    }catch (Exception e){}
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }*/


}
