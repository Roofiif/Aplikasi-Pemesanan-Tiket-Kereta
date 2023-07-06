package com.example.trainpedia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    Context context;

    ArrayList<Tiket> list;

    public MyAdapter(Context context, ArrayList<Tiket> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        Tiket tiket = list.get(position);
        holder.argo.setText(tiket.getArgo());
        holder.kelas.setText(tiket.getKelas());
        holder.jurusan.setText(tiket.getJurusan());
        holder.jadwal.setText(tiket.getJadwal());
        holder.harga.setText(tiket.getHarga());

    }

    @Override
    public int getItemCount() { return list.size();}

    public void searchDataList(ArrayList<Tiket> searchList){

        list = searchList;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView argo, kelas, jurusan, jadwal, harga;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            argo = itemView.findViewById(R.id.tvArgo);
            kelas = itemView.findViewById(R.id.tvKelas);
            jurusan = itemView.findViewById(R.id.tvJurusan);
            jadwal = itemView.findViewById(R.id.tvJadwal);
            harga = itemView.findViewById(R.id.tvHarga);

        }
    }
}
