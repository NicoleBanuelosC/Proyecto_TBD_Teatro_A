package com.example.proyectofinal_tbd_teatro.produccion;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyectofinal_tbd_teatro.R;
import java.util.List;

public class ProduccionAdapter extends RecyclerView.Adapter<ProduccionAdapter.ViewHolder> {
    private List<Produccion> producciones;
    private ListaProduccionesActivity activity;

    public ProduccionAdapter(List<Produccion> producciones, ListaProduccionesActivity activity) {
        this.producciones = producciones;
        this.activity = activity;
    }//PorduccionAdapter

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_produccion, parent, false);
        return new ViewHolder(view);
    }//viewHodler

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Produccion produccion = producciones.get(position);
        holder.tvLugar.setText("Lugar: " + produccion.lugar);
        holder.tvFecha.setText("Fecha: " + produccion.fecha);
        holder.tvPresupuesto.setText("Presupuesto: $" + produccion.presupuesto);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(activity, FormProduccionActivity.class);
            intent.putExtra("PRODUCCION", produccion);
            activity.startActivity(intent);
        });

        holder.itemView.setOnLongClickListener(v -> {
            new android.app.AlertDialog.Builder(activity)
                    .setTitle("Eliminar")
                    .setMessage("¿Seguro que quieres eliminar esta producción?")
                    .setPositiveButton("Sí", (d, w) -> {
                        activity.executor.execute(() -> {
                            activity.db.produccionDao().delete(produccion);
                            activity.runOnUiThread(() -> activity.cargarProducciones());
                        });
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });
    }//onBildHolder

    @Override
    public int getItemCount() {
        return producciones.size();
    }//getItemCount

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLugar, tvFecha, tvPresupuesto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLugar = itemView.findViewById(R.id.tvLugar);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvPresupuesto = itemView.findViewById(R.id.tvPresupuesto);
        }//viewHokder

    }//ViewHolder

}//public lcass