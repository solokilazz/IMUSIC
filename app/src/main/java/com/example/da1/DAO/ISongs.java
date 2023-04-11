package com.example.da1.DAO;

import com.example.da1.Models.Song;

import java.util.ArrayList;
import java.util.List;

public interface ISongs {
    ArrayList<Song> getBySingerId(String id);
    ArrayList<Song> getByStyleId(String id);
    ArrayList<Song> getAll();
    ArrayList<Song> getTop();
    Song get(String id);
    boolean insert(Song song);
    boolean update(Song song);
    boolean delete(String id);
}
