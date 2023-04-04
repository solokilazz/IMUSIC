package com.example.da1.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.da1.Fragments.DiskFragment;
import com.example.da1.Fragments.PlayListFragment;

public class ViewPagerPlayMusicAdapter extends FragmentStateAdapter {

    Fragment diskFragment, playListFragment;
    int quantity = 2;

    public ViewPagerPlayMusicAdapter(@NonNull FragmentActivity fragmentActivity,
                                     PlayListFragment playListFragment, DiskFragment diskFragment) {
        super(fragmentActivity);
        this.playListFragment = playListFragment;
        this.diskFragment = diskFragment;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return playListFragment;
            case 1:
                return diskFragment;
            default:
                return diskFragment;
        }
    }

    @Override
    public int getItemCount() {
        return quantity;
    }
}
