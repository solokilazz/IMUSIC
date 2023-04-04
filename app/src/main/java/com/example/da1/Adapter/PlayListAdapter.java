package com.example.da1.Adapter;


import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.da1.DAO.SingersDAO;
import com.example.da1.ItemViewModel;
import com.example.da1.Models.Song;
import com.example.da1.R;

import java.util.ArrayList;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.viewHolder> {

    Context context;
    ArrayList<Song> listSong;
    ItemViewModel viewModel;

    public PlayListAdapter(Context context, ArrayList<Song> listSong, ItemViewModel viewModel) {
        this.context = context;
        this.listSong = listSong;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_play_list,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Song song = listSong.get(position);
        holder.tvPlayListIndex.setText(position+1+"");
        holder.tvPlayListNameSinger.setText((new SingersDAO(context)).get(song.getSingerId()).getName());
        holder.tvPlayListNameSong.setText(song.getName());

        //gui du lieu cho livedata
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.selectItem(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSong.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView tvPlayListNameSong, tvPlayListIndex, tvPlayListNameSinger;
        RelativeLayout layoutItem;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvPlayListIndex = itemView.findViewById(R.id.tvPlayListIndex);
            tvPlayListNameSong = itemView.findViewById(R.id.tvPlayListNameSong);
            tvPlayListNameSinger = itemView.findViewById(R.id.tvPlayListNameSinger);
            layoutItem = itemView.findViewById(R.id.relativeLayout);

        }
    }
}
