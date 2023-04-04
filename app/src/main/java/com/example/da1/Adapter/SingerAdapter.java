package com.example.da1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.da1.Activities.FilterListActivity;
import com.example.da1.Models.Singer;
import com.example.da1.R;

import java.util.List;

public class SingerAdapter extends  RecyclerView.Adapter<SingerAdapter.SingerViewHolder> {
    private Context context;
    private List<Singer> listSinger;

    public SingerAdapter(Context iContext, List<Singer> listSinger) {
        this.context = iContext;
        this.listSinger = listSinger;
    }

    public SingerAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Singer> listSinger){
        this.listSinger = listSinger;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SingerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_singer,parent, false);
        return new SingerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingerViewHolder holder, int position) {
        Singer singer = listSinger.get(position);
        if (singer == null){
            return;
        }

        holder.ivSinger.setImageResource(singer.getImage());
        holder.tvSingerName.setText(singer.getName());
        holder.ivSinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idSinger = String.valueOf(singer.getId());
                Intent intent = new Intent(context, FilterListActivity.class);
                intent.putExtra("id_singer",idSinger);
                intent.putExtra("name_singer",singer.getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listSinger != null){
            return listSinger.size();
        }
        return 0;
    }

    public class SingerViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivSinger;
        private TextView tvSingerName;

        public SingerViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSinger = itemView.findViewById(R.id.ivSinger);
            tvSingerName = itemView.findViewById(R.id.txtSingerName);
        }
    }
}
