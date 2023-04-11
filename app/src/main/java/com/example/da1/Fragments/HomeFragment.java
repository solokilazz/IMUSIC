package com.example.da1.Fragments;


import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.da1.Adapter.SingerAdapter;
import com.example.da1.Adapter.SlideAdapter;
import com.example.da1.Adapter.SongsAdapter;
import com.example.da1.Adapter.StylesAdapter;
import com.example.da1.DAO.CommercialsDAO;
import com.example.da1.DAO.SingersDAO;
import com.example.da1.DAO.SongsDAO;
import com.example.da1.DAO.StylesDAO;
import com.example.da1.MainActivity;
import com.example.da1.Models.Commercial;
import com.example.da1.Models.Singer;
import com.example.da1.Models.Song;
import com.example.da1.Models.Style;
import com.example.da1.R;
import com.example.da1.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.Random;

import me.relex.circleindicator.CircleIndicator3;

public class HomeFragment extends Fragment {

    private RecyclerView recycleType, recyclerItem, recyclerSinger;
    private ViewPager2 vpSlide;
    private CircleIndicator3 circleIndicator3;

    private StylesAdapter stylesAdapter;
    private SongsAdapter songsAdapter;
    private SingerAdapter singerAdapter;
    private SlideAdapter slideAdapter;

    SongsDAO songsDAO;
    SingersDAO singersDAO;
    StylesDAO stylesDAO;
    CommercialsDAO commercialsDAO;

    ArrayList<Song> listSongRandoms = new ArrayList<>();
    ArrayList<Singer> listSingers = new ArrayList<>();
    ArrayList<Style> listStyles = new ArrayList<>();
    ArrayList<Commercial> listComercial = new ArrayList<>();


    //transfer while run time
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (vpSlide.getCurrentItem() == listComercial.size()-1){
                vpSlide.setCurrentItem(0);
            }
            else {
                vpSlide.setCurrentItem(vpSlide.getCurrentItem()+1);
            }
        }
    };



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

        //slide comercial
        vpSlide = view.findViewById(R.id.vpSlide);
        circleIndicator3 = view.findViewById(R.id.circleIndi);

        songsDAO = new SongsDAO(getContext());
        singersDAO = new SingersDAO(getContext());
        stylesDAO = new StylesDAO(getContext());
        commercialsDAO = new CommercialsDAO(getContext());

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

        //transfer slide comercial
        transferSile();

    }

    public ArrayList<Song> getMixListSongs(ArrayList<Song> listSong){
        ArrayList<Song> list = new ArrayList<>();
        Random random = new Random();
        while (listSong.size()>0){
            int index = random.nextInt(listSong.size());
            list.add(listSong.get(index));
            listSong.remove(index);
        }
        return list;
    }

    public void fillList(){
        listSongRandoms = getMixListSongs(songsDAO.getAll());
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

        //comercial
        listComercial = commercialsDAO.getAll();
        slideAdapter = new SlideAdapter(getContext(),listComercial);
        vpSlide.setAdapter(slideAdapter);
        circleIndicator3.setViewPager(vpSlide);
    }

    public void transferSile(){
        vpSlide.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                //set time 3s chuyen
                handler.postDelayed(runnable, 3000);
            }
        });
        // hieu ung zoomout slide
        vpSlide.setPageTransformer(new ZoomOutPageTransformer());
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }
}