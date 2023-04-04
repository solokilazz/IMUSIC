package com.example.da1.Fragments;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1.Activities.PlayActivity;
import com.example.da1.Adapter.PlayListAdapter;
import com.example.da1.R;
import com.google.android.material.imageview.ShapeableImageView;

public class DiskFragment extends Fragment {

    ShapeableImageView ivDisk;
    public static ObjectAnimator objectAnimator;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.disk_fragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivDisk = view.findViewById(R.id.ivDisk);

        objectAnimator = ObjectAnimator.ofFloat(ivDisk,"rotation",0f,360f);
        objectAnimator.setDuration(10000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
    }

    public void playMusic(final int hinhanh) {
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ivDisk.setImageResource(hinhanh);
            }
        },300);
    }

    public void pause(){
        objectAnimator.pause();

    }
    public void run(){
        objectAnimator.resume();
    }
}
