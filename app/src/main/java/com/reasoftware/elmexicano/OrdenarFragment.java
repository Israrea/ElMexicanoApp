package com.reasoftware.elmexicano;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class OrdenarFragment extends Fragment {

    private View view;
    private DatabaseReference mDatabase;
    private EditText numero_de_mesa_et;
    private Button boton_continuar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ordenar, container, false);
        boton_continuar = view.findViewById(R.id.continuar_button);
        boton_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtener_verificar_datos();
            }
        });
        numero_de_mesa_et = view.findViewById(R.id.numero_de_mesa_et);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view=view;

    }

    private void showData(@NonNull DataSnapshot dataSnapshot){
        final String et_numero = numero_de_mesa_et.getText().toString();
        boolean found = false;

        if(dataSnapshot.exists()){

            for(DataSnapshot ds: dataSnapshot.getChildren()){
                String numero_de_mesa = ds.getValue().toString();
                System.out.println(numero_de_mesa);
                if(et_numero.equals(numero_de_mesa)){
                    found = true;
                    break;
                }
            }
            if(found){
                //iniciar siguiente actividad
                System.out.println("INICIAR ACTIVIDAD");
                Intent i = new Intent(getActivity(),OrdenActivity.class);
                i.putExtra("ID_MESA", et_numero);
                startActivity(i);
            }else{
                final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setMessage("El numero de mesa es incorrecto").setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
            }
        }
    }

    private void obtener_verificar_datos(){

        mDatabase.child("mesas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setMessage("Error con la base de datos, por favor intentelo mas tarde").setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
            }
        });
    }

}