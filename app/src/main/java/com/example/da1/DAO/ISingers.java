package com.example.da1.DAO;

import com.example.da1.Models.Singer;
import com.example.da1.Models.Song;

import java.util.ArrayList;
import java.util.List;

public interface ISingers {
    Singer get(String id);
    ArrayList<Singer> getAll();
    boolean insert(Singer singer);
    boolean update(Singer singer);
    boolean delete(String id);
}
