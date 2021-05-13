package com.reasoftware.elmexicano;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

class adaptador2 extends RecyclerView.Adapter<adaptador2.ViewHolder>{

    ArrayList<menuClass> lista_de_platillos;
    ArrayList<Integer> lista_de_cantidad;
    private final ClickListener listener;

    public adaptador2(ArrayList<menuClass> lista, ClickListener listener) {
        this.lista_de_platillos = lista;
        this.listener = listener;
        lista_de_cantidad = new ArrayList<>();
        for(int x=0; x<lista_de_platillos.size();x++){
            lista_de_cantidad.add(0);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView nombre_platillo,
                cantidad_platillo,precio_platillo;
        Button mas, menos;
        private WeakReference<ClickListener> listenerRef;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listenerRef = new WeakReference<>(listener);
            nombre_platillo = (TextView) itemView.findViewById(R.id.platilloNombre);
            cantidad_platillo = (TextView) itemView.findViewById(R.id.cantidad);
            precio_platillo = (TextView) itemView.findViewById(R.id.platilloPrecio);
            mas = itemView.findViewById(R.id.masBTN);
            menos = itemView.findViewById(R.id.menosBTN);
            mas.setOnClickListener(this);
            menos.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int cantidad = Integer.parseInt(cantidad_platillo.getText().toString());

            if (v.getId() == mas.getId()) {
                cantidad = cantidad + 1;
            } else if(v.getId() == menos.getId()){
                if(cantidad!=0){
                    cantidad = cantidad - 1;
                }else{
                    cantidad = 0;
                }
            }

            for(int x=0;x<lista_de_platillos.size();x++){
                if(getAdapterPosition()==x){
                    lista_de_cantidad.set(x,cantidad);
                }
            }

            cantidad_platillo.setText(String.valueOf(cantidad));
           // listenerRef.get().onPositionClicked(getAdapterPosition());
            listenerRef.get().onClicked(lista_de_cantidad,lista_de_platillos);
        }
    }

    @NonNull
    @Override
    public adaptador2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item2_list,null,false);
        return new adaptador2.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adaptador2.ViewHolder holder, int position) {
        holder.nombre_platillo.setText(lista_de_platillos.get(position).getNombre_platillo());
        holder.cantidad_platillo.setText("0");
        holder.precio_platillo.setText("$"+ lista_de_platillos.get(position).getPrecio_platillo());

    }
    public interface ClickListener {

        void onPositionClicked(int position);

        void onLongClicked(int position);

        void onClicked(ArrayList<Integer> cantidad, ArrayList<menuClass> lista);
    }

    @Override
    public int getItemCount() {
        return lista_de_platillos.size();
    }
}
