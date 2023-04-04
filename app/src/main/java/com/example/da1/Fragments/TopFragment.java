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
import com.example.da1.Adapter.TopListAdapter;
import com.example.da1.DAO.SongsDAO;
import com.example.da1.ItemViewModel;
import com.example.da1.R;

public class TopFragment extends Fragment {

    RecyclerView recyclerViewTopList;
    TopListAdapter topListAdapter;
    SongsDAO songsDAO;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public TopFragment() {
        // Required empty public constructor
    }


    // method này là thể hiện của lớp, dùng để gọi trực tiếp mà ko cần tạo mới đối tượng.
    public static TopFragment newInstance(String param1, String param2){
        TopFragment fragment = new TopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.top_fragment,container,false);
        return view;
    }

    // xử lý sự kiện trong method bên dưới.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewTopList = view.findViewById(R.id.rvTopList);
        songsDAO = new SongsDAO(getContext());
        fillList();
    }

    public void fillList(){
        if (songsDAO.getAll().size()>0){
            topListAdapter = new TopListAdapter(getContext(), songsDAO.getAll());
            recyclerViewTopList.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerViewTopList.setAdapter(topListAdapter);
        }
    }

}