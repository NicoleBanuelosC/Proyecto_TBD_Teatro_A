package com.example.proyectofinal_tbd_teatro.obra;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyectofinal_tbd_teatro.R;
import java.util.List;

public class ObraAdapter extends RecyclerView.Adapter<ObraAdapter.ViewHolder>{

    private List<Obra> obras;

    public ObraAdapter(List<Obra> obras) {
        this.obras = obras;
    }//obraAdapter

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_obra, parent, false);
        return new ViewHolder(view);
    }//onCreate

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Obra obra = obras.get(position);
        holder.tvNombre.setText(obra.nombre);
        holder.tvAutor.setText("Autor: " + obra.autor);
        holder.tvGenero.setText("Género: " + obra.genero);
        holder.tvDuracion.setText("Duración: " + obra.duracionMinutos + " min");
    }//onBind

    @Override
    public int getItemCount() {
        return obras.size();
    }//getItem

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvAutor, tvGenero, tvDuracion;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvAutor = itemView.findViewById(R.id.tvAutor);
            tvGenero = itemView.findViewById(R.id.tvGenero);
            tvDuracion = itemView.findViewById(R.id.tvDuracion);
        }//view holdes

    }//static class

}//public class
