package com.example.duan_1.DataBase;

import androidx.room.RoomDatabase;

import com.example.duan_1.DAO.User_DAO;
import com.example.duan_1.Model.User;

@androidx.room.Database(entities = {User.class}, version = 1)
public abstract class User_DataBase extends RoomDatabase {
    public abstract User_DAO UserDAO();
}
