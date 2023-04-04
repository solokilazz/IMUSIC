package com.example.da1.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1.Activities.PlayActivity;
import com.example.da1.Adapter.PlayListAdapter;
import com.example.da1.Adapter.SongsAdapter;
import com.example.da1.ItemViewModel;
import com.example.da1.R;

public class PlayListFragment extends Fragment {

    RecyclerView recyclerViewPlayList;
    PlayListAdapter playListAdapter;
    ItemViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.play_list_fragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewPlayList = view.findViewById(R.id.recycleViewPlayList);
        //livedata
        viewModel = new ViewModelProvider(getActivity()).get(ItemViewModel.class);
        fillList();

    }

    public void fillList(){
        if (PlayActivity.listSong.size()>0){
            playListAdapter = new PlayListAdapter(getContext(), PlayActivity.listSong,viewModel);
            recyclerViewPlayList.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerViewPlayList.setAdapter(playListAdapter);
        }
    }
}
