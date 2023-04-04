package com.example.da1.DAO;


import com.example.da1.Models.Song;
import com.example.da1.Models.Style;

import java.util.ArrayList;

public interface IStyles {
    Style get(String id);
    ArrayList<Style> getAll();
    boolean insert(Style style);
    boolean update(Style style);
    boolean delete(String id);
}
