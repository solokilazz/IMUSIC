package com.example.da1.Adapter;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1.Activities.PlayActivity;
import com.example.da1.DAO.SingersDAO;
import com.example.da1.DAO.SongsDAO;
import com.example.da1.Models.Song;
import com.example.da1.MyService;
import com.example.da1.R;

import java.util.ArrayList;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Song> list;

    public SongsAdapter(Context context, ArrayList<Song> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_song,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.ivImageSong.setImageResource(list.get(position).getImage());
        //lay ten ca si
        String singerName = (new SingersDAO(context)).get(list.get(position).getSingerId()).getName();
        holder.tvSingerName.setText(singerName);
        holder.tvSongName.setText(list.get(position).getName());

        //truyen du lieu(danh sach va bai hat duoc chon)
        Song song = list.get(position);
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ListSong",list);
                bundle.putSerializable("Song",song);
                bundle.putSerializable("isplaying",true);
                intent.putExtra("bundle",bundle);
                Intent intentService = new Intent(context,MyService.class);
                context.stopService(intentService);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvSongName, tvSingerName;
        ImageView ivImageSong;
        LinearLayout layoutItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSingerName = itemView.findViewById(R.id.tvSingerName);
            tvSongName = itemView.findViewById(R.id.tvSongName);
            ivImageSong = itemView.findViewById(R.id.ivImageSong);
            layoutItem = itemView.findViewById(R.id.layoutItem);
        }
    }

}
