package com.reasoftware.elmexicano;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.BoolRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class adaptador extends RecyclerView.Adapter<adaptador.ViewHolder> {

    ArrayList<menuClass> lista_de_platillos;
    FragmentActivity fragmentActivity;

    public adaptador(ArrayList<menuClass> lista, FragmentActivity activity) {
        this.lista_de_platillos = lista;
        this.fragmentActivity=activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nombre_platillo.setText(lista_de_platillos.get(position).getNombre_platillo());
        holder.descripcion_platillo.setText(lista_de_platillos.get(position).getDescripcion_platillo());
        holder.precio_platillo.setText("$" + lista_de_platillos.get(position).getPrecio_platillo());

        Drawable d = fragmentActivity.getDrawable(R.drawable.logo);

        switch (lista_de_platillos.get(position).getNombre_platillo()){
            case "Enchiladas Verdes":
                d = fragmentActivity.getDrawable(R.drawable.enchiladas);
                break;
            case "Tacos Dorados":
                d = fragmentActivity.getDrawable(R.drawable.tacos);
                break;
            case "Torta Pierna Horneada":
                d = fragmentActivity.getDrawable(R.drawable.pierna1);
                break;
            case "Torta Cubana":
                d = fragmentActivity.getDrawable(R.drawable.cubana1);
                break;
            case "Torta Hawaiana":
                d = fragmentActivity.getDrawable(R.drawable.hawaiana);
                break;
            case "Torta Salchicha":
                d = fragmentActivity.getDrawable(R.drawable.salchicha);
                break;
            default:
                d = fragmentActivity.getDrawable(R.drawable.pierna1);
                break;
        }
            Glide
                .with(fragmentActivity)
                .load(lista_de_platillos.get(position).getImg_platillo())
                .placeholder(R.drawable.logob)
                .error(d)
                .circleCrop()
                .into(holder.img_platillo);
    }

    @Override
    public int getItemCount() {
        return lista_de_platillos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nombre_platillo,
                descripcion_platillo,precio_platillo;

        ImageView img_platillo;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre_platillo = (TextView) itemView.findViewById(R.id.nombre);
            descripcion_platillo = (TextView) itemView.findViewById(R.id.descrip);
            img_platillo = (ImageView) itemView.findViewById(R.id.imagen);
            precio_platillo = (TextView) itemView.findViewById(R.id.precio);
        }
    }
}
