package com.reasoftware.elmexicano;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class MenuFragment extends Fragment {

    private View view;
    private DatabaseReference mDatabase;
    private RecyclerView recyclerView;
    private ArrayList<menuClass> lista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
       // llenarLista();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        leerClase();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view=view;
      //  mDatabase = FirebaseDatabase.getInstance().getReference("menuClass");

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
                        String desc = ds.child("descripcion").getValue().toString();
                        int precio = Integer.parseInt(ds.child("precio").getValue().toString());
                        String img = ds.child("img").getValue().toString();
                        int id = Integer.parseInt(ds.child("id").getValue().toString());
                        llenarLista(nombre,desc,img,id,precio);
                    }
                    adaptador adaptador_de_recycler = new adaptador(lista,getActivity());
                    recyclerView.setAdapter(adaptador_de_recycler);
                }
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

    /*public void llenarLista(){
        lista.add(new menuClass("x","y","img",1));
        lista.add(new menuClass("x","y","img",1));
        lista.add(new menuClass("x","y","img",1));
        lista.add(new menuClass("x","y","img",1));
        lista.add(new menuClass("x","y","img",1));
        lista.add(new menuClass("x","y","img",1));
        lista.add(new menuClass("x","y","img",1));
        lista.add(new menuClass("x","y","img",1));
        lista.add(new menuClass("x","y","img",1));
        lista.add(new menuClass("x","y","img",1));
        lista.add(new menuClass("x","y","img",1));
        lista.add(new menuClass("x","y","img",1));

    }*/
    public void llenarLista(String nombre, String desc, String img, int id,int precio){
        lista.add(new menuClass(nombre,desc,img,id,precio));
    }
}