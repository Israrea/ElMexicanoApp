package com.reasoftware.elmexicano;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrdenActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    private ArrayList<menuClass> lista;
    private TextView subtotalPrecio;
    private int subtotal;
    private ArrayList<Integer> Lcantidad;
    private String mesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orden);
        mesa = getIntent().getStringExtra("ID_MESA");
        subtotalPrecio = findViewById(R.id.subtotal);
        recyclerView =  findViewById(R.id.RVplatillosLista);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Button ordenarBTN = findViewById(R.id.ordenarBTN);
        ordenarBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(OrdenActivity.this, "EL TOTAL ES: " + subtotal, Toast.LENGTH_LONG).show();
                //mandar datos a FIREBASE
                mandarOrden();
            }
        });
        Button back = findViewById(R.id.backBtnOrdena);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void mandarOrden() {
        int Nmesa = 0;

        switch (mesa){
            case "0101":
                Nmesa = 1;
                break;
            case "0102":
                Nmesa = 2;
                break;
            case "0103":
                Nmesa = 3;
                break;
            case "0104":
                Nmesa = 4;
                break;
            case "0105":
                Nmesa = 5;
                break;
            default:break;
        }

        if(subtotal!=0){
            for(int x=0;x<lista.size();x++){
                if(Lcantidad.get(x) !=0){
                    String platillo = lista.get(x).nombre_platillo.toString();
                    int cantidad = Lcantidad.get(x);
                    mDatabase.child("ordenes").child("mesa"+Nmesa).child(platillo).setValue(cantidad);
                }
            }
            Intent i = new Intent(OrdenActivity.this,OrdenRealizadaActivity.class);
            finish();
            startActivity(i);
        }else{
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("No se ha agregado a la orden, primero seleccione la cantidad del producto que desea.")
                    .setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            dialog.show();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    protected void onStart() {
        super.onStart();
        leerClase();
    }

    public void leerClase(){
        lista = new ArrayList<>();
        mDatabase.child("platillos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    lista.clear();
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        String nombre = ds.child("nombre").getValue().toString();
                        int precio = Integer.parseInt(ds.child("precio").getValue().toString());
                        int id = Integer.parseInt(ds.child("id").getValue().toString());
                        llenarLista(nombre,"","",id,precio);
                    }
                    adaptador2 adaptador_de_recycler = new adaptador2(lista, new adaptador2.ClickListener() {
                        @Override public void onPositionClicked(int position) {
                            // callback performed on click
                            Toast.makeText(OrdenActivity.this, "ITEM PRESSED ", Toast.LENGTH_SHORT).show();
                        }

                        @Override public void onLongClicked(int position) {
                            // callback performed on click
                        }

                        @Override
                        public void onClicked(ArrayList<Integer> cantidad, ArrayList<menuClass> lista) {
                            subtotal = 0;
                            Lcantidad = cantidad;
                           for(int x=0;x<lista.size();x++){
                               if(cantidad.get(x) !=0){
                                   int precio = lista.get(x).precio_platillo;
                                   subtotal = subtotal + (cantidad.get(x)*precio);
                               }
                           }

                            subtotalPrecio.setText("$ " + subtotal);
                        }
                    });
                    recyclerView.setAdapter(adaptador_de_recycler);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void llenarLista(String nombre, String desc, String img, int id,int precio){
        lista.add(new menuClass(nombre,desc,img,id,precio));
    }
}