package com.example.da1.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1.Adapter.SingerAdapter;
import com.example.da1.Adapter.SongsAdapter;
import com.example.da1.Adapter.StylesAdapter;
import com.example.da1.DAO.SingersDAO;
import com.example.da1.DAO.SongsDAO;
import com.example.da1.DAO.StylesDAO;
import com.example.da1.MainActivity;
import com.example.da1.Models.Singer;
import com.example.da1.Models.Song;
import com.example.da1.Models.Style;
import com.example.da1.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView recycleType, recyclerItem, recyclerSinger;

    private StylesAdapter stylesAdapter;
    private SongsAdapter songsAdapter;
    private SingerAdapter singerAdapter;

    SongsDAO songsDAO;
    SingersDAO singersDAO;
    StylesDAO stylesDAO;

    ArrayList<Song> listSongRandoms = new ArrayList<>();
    ArrayList<Singer> listSingers = new ArrayList<>();
    ArrayList<Style> listStyles = new ArrayList<>();


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }


    // method này là thể hiện của lớp, dùng để gọi trực tiếp mà ko cần tạo mới đối tượng.
    public static HomeFragment newInstance(String param1, String param2){
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.home_fragment,container,false);
        MainActivity.replaceToolbarColor(getResources().getDrawable(R.drawable.toolbar_bg));
        return view;
    }

    // xử lý sự kiện trong method bên dưới.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycleType = view.findViewById(R.id.recycleType);
        recyclerItem = view.findViewById(R.id.recycleChart);
        recyclerSinger = view.findViewById(R.id.recyclerSinger);

        songsDAO = new SongsDAO(getContext());
        singersDAO = new SingersDAO(getContext());
        stylesDAO = new StylesDAO(getContext());

        LinearLayoutManager layoutManagerSongs = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        LinearLayoutManager layoutManagerSingers = new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false);
        LinearLayoutManager layoutManagerStyles = new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false);
        recyclerItem.setLayoutManager(layoutManagerSongs);
        recyclerSinger.setLayoutManager(layoutManagerSingers);
        recycleType.setLayoutManager(layoutManagerStyles);

        fillList();
    }

    public void fillList(){
        listSongRandoms = songsDAO.getAll();
        songsAdapter = new SongsAdapter(getContext(),listSongRandoms);
        recyclerItem.setAdapter(songsAdapter);
        recyclerItem.setHasFixedSize(true);

        listSingers = singersDAO.getAll();
        singerAdapter = new SingerAdapter(getContext(),listSingers);
        recyclerSinger.setAdapter(singerAdapter);
        recyclerSinger.setHasFixedSize(true);

        listStyles = stylesDAO.getAll();
        stylesAdapter = new StylesAdapter(getContext(),listStyles);
        recycleType.setAdapter(stylesAdapter);
        recycleType.setHasFixedSize(true);
    }

}