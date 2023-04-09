package com.example.da1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1.Models.Commercial;
import com.example.da1.R;

import java.util.ArrayList;
import java.util.List;

public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.SlideViewHolder> {
    private Context sContext;
    private ArrayList<Commercial> sListSlide;

    public SlideAdapter(Context sContext, ArrayList<Commercial> sListSlide) {
        this.sContext = sContext;
        this.sListSlide = sListSlide;
    }

    public SlideAdapter(Context sContext) {
        this.sContext = sContext;
    }

    public SlideAdapter(ArrayList<Commercial> sListSlide) {
        this.sListSlide = sListSlide;
        notifyDataSetChanged();
    }




    @NonNull
    @Override
    public SlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slide, parent, false);
        return new SlideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SlideViewHolder holder, int position) {
        Commercial commercial = sListSlide.get(position);
        if (commercial == null){
            return;
        }
        holder.imgSlide.setImageResource(commercial.getImage());
    }

    @Override
    public int getItemCount() {
        if (sListSlide != null){
            return sListSlide.size();
        }
        return 0;
    }

    public class SlideViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgSlide;
        public SlideViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSlide = itemView.findViewById(R.id.img_photo);
        }
    }


}
