package com.example.Coba.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Coba.EditFriends;
import com.example.Coba.MainActivity;
import com.example.Coba.R;
import com.example.Coba.database.DBControll;
import com.example.Coba.database.Friends;

import java.util.ArrayList;

public class TemanAdapter extends RecyclerView.Adapter<TemanAdapter.TemanViewHolder> {

    private ArrayList<Friends> listData;

    public TemanAdapter(ArrayList<Friends> listData) {
        this.listData = listData;
    }
    @NonNull
    @Override
    public TemanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_data_teman, parent, false);
        return new TemanViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull TemanViewHolder holder, int position) {
        String nama, telpon;
        nama = listData.get(position).getNama();telpon = listData.get(position).getTelpon();
        holder.tvNama.setTextColor(Color.BLUE);holder.tvNama.setTextSize(18);holder.tvNama.setText(nama);holder.tvTelpon.setText(telpon);
    }
    @Override
    public int getItemCount() {
        return (listData != null) ? listData.size() : 0;
    }
    public class TemanViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView; private TextView tvNama, tvTelpon; private Context context;
        Bundle bundle = new Bundle();
        DBControll dbControll;
        public TemanViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.ctFriend);
            tvNama = itemView.findViewById(R.id.leName);
            tvTelpon = itemView.findViewById(R.id.leContact);
            context = itemView.getContext();
            dbControll = new DBControll(context);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nama = listData.get(getAdapterPosition()).getNama();
                    String telpon = listData.get(getAdapterPosition()).getTelpon();
                    bundle.putString("nama", nama.trim());
                    bundle.putString("telpon", telpon.trim());
                }
            });

            cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    PopupMenu pm = new PopupMenu(context, view);
                    pm.inflate(R.menu.popup_menu);
                    pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.mnEdit:
                                    String id = listData.get(getAdapterPosition()).getId(); String nama = listData.get(getAdapterPosition()).getNama(); String telpon = listData.get(getAdapterPosition()).getTelpon();
                                    bundle.putString("id", id.trim()); bundle.putString("nama", nama.trim()); bundle.putString("telpon", telpon.trim());
                                    Intent intent = new Intent(context, EditFriends.class);
                                    intent.putExtras(bundle);
                                    context.startActivity(intent);
                                    break;
                                case R.id.mnDelete:
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setTitle("Hapus Data"); builder.setMessage("Apakah Anda yakin?"); builder.setCancelable(true); builder.setPositiveButton("Ya",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                String id = listData.get(getAdapterPosition()).getId();
                                                dbControll.deleteData(id); Intent intent = new Intent(context, MainActivity.class); intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                context.startActivity(intent);
                                            }
                                        }
                                    );
                                    builder.setNegativeButton("Batal",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.cancel();
                                            }
                                        }
                                    );
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                    break;
                            }
                            return false;
                        }
                    });
                    pm.show();
                    return false;
                }
            });
        }
    }
}