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
import com.example.da1.Models.Style;
import com.example.da1.R;

import java.util.List;


public class StylesAdapter extends RecyclerView.Adapter<StylesAdapter.TypeViewHolder> {
    private Context mContext;
    private List<Style> listStyle;

    public StylesAdapter() {
    }

    public StylesAdapter(Context mContext, List<Style> listStyle) {
        this.mContext = mContext;
        this.listStyle = listStyle;
    }

    public StylesAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Style> listStyle) {
        this.listStyle = listStyle;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_item, parent, false);
        return new TypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TypeViewHolder holder, int position) {

        Style style = listStyle.get(position);
        if (style == null){
            return;
        }

        holder.ivStyle.setImageResource(style.getImage());
        holder.tvStyleName.setText(style.getName());

        holder.ivStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idStyle = String.valueOf(style.getId());
                Intent intent = new Intent(mContext, FilterListActivity.class);
                intent.putExtra("id_style",idStyle);
                intent.putExtra("name_style",style.getName());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listStyle != null){
            return listStyle.size();
        }
        return 0;
    }

    public class TypeViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivStyle;
        private TextView tvStyleName;


        public TypeViewHolder(@NonNull View itemView) {
            super(itemView);

            ivStyle = itemView.findViewById(R.id.ivStyle);
            tvStyleName = itemView.findViewById(R.id.txtNameType);
        }
    }

}
