package com.example.da1.DAO;

import com.example.da1.Models.Commercial;
import com.example.da1.Models.Song;

import java.util.ArrayList;

public interface ICommercials {
    Commercial get(String id);
    ArrayList<Commercial> getAll();
    boolean insert(Commercial commercial);
    boolean update(Commercial commercial);
    boolean delete(String id);
}
