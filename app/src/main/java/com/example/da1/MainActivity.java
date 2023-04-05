package com.example.da1;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;

import com.example.da1.DAO.SingersDAO;
import com.example.da1.DAO.SongsDAO;
import com.example.da1.DAO.StylesDAO;
import com.example.da1.Fragments.AllFragment;
import com.example.da1.Fragments.HomeFragment;
import com.example.da1.Fragments.TopFragment;
import com.example.da1.Models.Singer;
import com.example.da1.Models.Song;
import com.example.da1.Models.Style;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    SongsDAO songsDAO;
    SingersDAO singersDAO;
    StylesDAO stylesDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        //addData
        songsDAO = new SongsDAO(MainActivity.this);
        if (songsDAO.getAll().size()==0){
            addData();
        }

        // gắn toolbar
        setSupportActionBar(toolbar);

        //gán hiển thị mặc định ban đầu
        replaceFragment(new HomeFragment());


        //sự kiện khi nhấn nav
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                int id = item.getItemId();
                switch (id) {
                    case R.id.home:
                        replaceFragment(new HomeFragment());
                        break;
                    case R.id.top:
                        replaceFragment(new TopFragment());
                        break;
                    case R.id.about:
                        replaceFragment(new AllFragment());
                        break;
                }
                return false;
            }
        });

    }



    public void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction().replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }

    public void addData(){
        singersDAO = new SingersDAO(MainActivity.this);
        String[] nameSinger = new String[]{"JusTaTee","Quang Lê","Trúc Nhân","Sơn Tùng"
                ,"Hoài Lâm","Phan Mạnh Quỳnh",};
        int[] imageSinger = new int[]{R.drawable.am_iu,R.drawable.gocuatraitim,
                R.drawable.truc_nhan,R.drawable.son_tung,R.drawable.hoai_lam
                ,R.drawable.phan_manh_quynh};
        for (int i = 0; i<nameSinger.length;i++){
            singersDAO.insert(new Singer(nameSinger[i],imageSinger[i]));
        }

        songsDAO = new SongsDAO(MainActivity.this);
        //Song(String name, int image, String link, String styleId, String singerId, int count, boolean status)
        int[] imageSong = new int[]{R.drawable.am_iu,R.drawable.gocuatraitim,
                R.drawable.lon_roi_con_khoc_nhe,R.drawable.thoi_bung_cam_hung,R.drawable.buon_lam_chi_em_oi,
                R.drawable.co_chang_trai_viet_len_cay,R.drawable.em_cua_ngay_hom_qua,R.drawable.nang_am_xa_dan,
                R.drawable.hoa_no_khong_mau,R.drawable.yeu_nhau_nua_ngay};
        int[] linkSong = new int[]{R.raw.am_iu,R.raw.gocuatraitim,R.raw.lon_roi_con_khoc_nhe,R.raw.thoibungcamhung,
                R.raw.buon_lam_chi_em_oi,R.raw.co_chang_trai_viet_len_cay,R.raw.em_cua_ngay_hom_qua
                ,R.raw.nang_am_xa_dan, R.raw.hoa_no_khong_mau,R.raw.yeu_nhau_nua_ngay};
        String[] nameSong = new String[]{"Ấm iu","Gõ cửa trái tim","Lớn rồi còn khóc nhè", "Thổi bừng cảm hứng",
                "Buồn làm chi em ơi","Có chàng trai viết lên cây","Em của ngày hôm qua"
                , "Nắng ấm xa dần","Hoa nở không màu","Yêu nhau nửa ngày"};
        int[] styleIds = new int[]{3,1,3,3,2,3,3,3,2,3};
        int[] singerIds = new int[]{1,2,3,3,5,6,4,4,5,6};
        for (int i = 0;i<imageSong.length;i++){
            Song newSong = new Song(nameSong[i],imageSong[i],linkSong[i],
                    String.valueOf(styleIds[i]),String.valueOf(singerIds[i]),0,false);
            songsDAO.insert(newSong);
        }

        stylesDAO = new StylesDAO(MainActivity.this);
        String[] nameStyle = new String[]{"Bolero","Ballads","Pop"};
        int[] imageStyle = new int[]{R.drawable.bolero,R.drawable.ballads,R.drawable.pop};
        for (int i = 0; i<nameStyle.length;i++){
            stylesDAO.insert(new Style(nameStyle[i],imageStyle[i]));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Intent intent = new Intent(MainActivity.this,MyService.class);
//        stopService(intent);
    }
}