package com.example.proyectofinal_tbd_teatro.obra;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyectofinal_tbd_teatro.R;
import java.util.List;

public class ObraAdapter extends RecyclerView.Adapter<ObraAdapter.ViewHolder> {
    private List<Obra> obras;
    private ListaObrasActivity activity;
    public ObraAdapter(List<Obra> obras, ListaObrasActivity activity) {
        this.obras = obras;
        this.activity = activity;
    }//publicObraAdaorte

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_obra, parent, false);
        return new ViewHolder(view);
    }//voewHolder

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Obra obra = obras.get(position);
        holder.tvNombre.setText(obra.nombre);
        holder.tvAutor.setText("Autor: " + obra.autor);
        holder.tvGenero.setText("Género: " + obra.genero);
        holder.tvDuracion.setText("Duración: " + obra.duracionMinutos + " min");

        // editar al hacer clic
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(activity, FormObraActivity.class);
            intent.putExtra("OBRA", obra);
            activity.startActivity(intent);
        });

        // eliminar al mantener presionado
        holder.itemView.setOnLongClickListener(v -> {
            new android.app.AlertDialog.Builder(activity)
                    .setTitle("Eliminar")
                    .setMessage("¿Seguro que quieres eliminar esta obra?")
                    .setPositiveButton("Sí", (d, w) -> {
                        activity.executor.execute(() -> {
                            activity.db.obraDao().delete(obra);
                            activity.runOnUiThread(() -> activity.cargarObras());
                        });                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });
    }//onBindViewHolder

    @Override
    public int getItemCount() {
        return obras.size();
    }//getItemCount

    public void updateList(List<Obra> nuevaLista) {
        this.obras = nuevaLista;
        notifyDataSetChanged();
    }//updateList

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvAutor, tvGenero, tvDuracion;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvAutor = itemView.findViewById(R.id.tvAutor);
            tvGenero = itemView.findViewById(R.id.tvGenero);
            tvDuracion = itemView.findViewById(R.id.tvDuracion);
        }//viewHolder
    }//static ckass
}//public class