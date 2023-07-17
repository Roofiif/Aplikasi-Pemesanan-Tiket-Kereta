package com.example.trainpedia;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://trainpedia-a76fd-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Tiket");
    private Context context;

    private ArrayList<Tiket> list;

    public MyAdapter(Context context, ArrayList<Tiket> list) {
        this.context = context;
        this.list = list;
    }

    public void updateData(ArrayList<Tiket> list){
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.argo.setText(list.get(position).getArgo());
        holder.jadwal.setText(list.get(position).getJadwal());
        holder.jurusan.setText(list.get(position).getJurusan());
        holder.kelas.setText(list.get(position).getKelas());
        holder.harga.setText("Rp "+Integer.valueOf(list.get(position).getHarga()));
        holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Checkout.class);
                intent.putExtra("argo", list.get(holder.getAdapterPosition()).getArgo());
                intent.putExtra("jadwal", list.get(holder.getAdapterPosition()).getJadwal());
                intent.putExtra("jurusan", list.get(holder.getAdapterPosition()).getJurusan());
                intent.putExtra("kelas", list.get(holder.getAdapterPosition()).getKelas());
                intent.putExtra("harga", Integer.valueOf(list.get(holder.getAdapterPosition()).getHarga()));
                intent.putExtra("Key",list.get(holder.getAdapterPosition()).getKey());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() { return list.size();}

    public void searchDataList(ArrayList<Tiket> searchList){

        list = searchList;
        notifyDataSetChanged();
    }


}
class MyViewHolder extends RecyclerView.ViewHolder{
    TextView argo, kelas, jurusan, jadwal, harga, addToCartButton;
    CardView recCard;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        recCard = itemView.findViewById(R.id.recCard);
        argo = itemView.findViewById(R.id.tvArgo);
        kelas = itemView.findViewById(R.id.tvKelas);
        jurusan = itemView.findViewById(R.id.tvJurusan);
        jadwal = itemView.findViewById(R.id.tvJadwal);
        harga = itemView.findViewById(R.id.tvHarga);
        addToCartButton = itemView.findViewById(R.id.btnBeli);

    }
}