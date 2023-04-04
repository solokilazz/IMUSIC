package com.example.da1.DAO;

import com.example.da1.Models.Commercial;

public interface ICommercials {
    Commercial get(String id);
    boolean insert(Commercial commercial);
    boolean update(Commercial commercial);
    boolean delete(String id);
}
