package com.example.duan_1.DAO;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.duan_1.Model.User;

import java.util.List;

@Dao
public interface User_DAO {
    @Query("SELECT * FROM User")
    List<User> getUser() ;

//    @Query("SELECT Password FROM User")
//    List<User> getPassword();

    @Insert
    long[] inserUser(User... users);

}
