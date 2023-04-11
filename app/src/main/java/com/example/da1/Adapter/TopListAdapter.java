package com.example.da1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1.Activities.PlayActivity;
import com.example.da1.DAO.SingersDAO;
import com.example.da1.Models.Singer;
import com.example.da1.Models.Song;
import com.example.da1.MyService;
import com.example.da1.R;

import java.util.ArrayList;
import java.util.List;

public class TopListAdapter extends  RecyclerView.Adapter<TopListAdapter.SingerViewHolder> {
    private Context context;
    private ArrayList<Song> listTop;

    public TopListAdapter(Context context, ArrayList<Song> listTop) {
        this.context = context;
        this.listTop = listTop;
    }

    public TopListAdapter(Context iContext) {
        this.context = iContext;
    }

    public void setData(List<Singer> listSinger){
        this.listTop = listTop;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SingerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_list,parent, false);
        return new SingerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingerViewHolder holder, int position) {
        Song song = listTop.get(position);
        if (song == null){
            return;
        }
        holder.ivSong.setImageResource(song.getImage());
        holder.tvTopListIndex.setText(position+1+"");
        holder.tvTopListNameSinger.setText((new SingersDAO(context)).get(song.getSingerId()).getName());
        holder.tvTopListNameSong.setText(song.getName());
        holder.tvTopListListens.setText("Lượt nghe: "+song.getCount());
        //truyen du lieu(danh sach va bai hat duoc chon)
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ListSong",listTop);
                bundle.putSerializable("Song",song);
                bundle.putSerializable("isplaying",true);
                intent.putExtra("bundle",bundle);
                Intent intentService = new Intent(context, MyService.class);
                context.stopService(intentService);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listTop != null){
            return listTop.size();
        }
        return 0;
    }

    public class SingerViewHolder extends RecyclerView.ViewHolder{

        ImageView ivSong;
        TextView tvTopListNameSong, tvTopListIndex, tvTopListNameSinger, tvTopListListens;
        RelativeLayout layoutItem;

        public SingerViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSong = itemView.findViewById(R.id.ivImageSong);
            tvTopListIndex = itemView.findViewById(R.id.tvTopListIndex);
            tvTopListNameSong = itemView.findViewById(R.id.tvTopListNameSong);
            tvTopListNameSinger = itemView.findViewById(R.id.tvTopListNameSinger);
            tvTopListListens = itemView.findViewById(R.id.tvTopListListens);
            layoutItem = itemView.findViewById(R.id.relativeLayoutToplist);
        }
    }
}
