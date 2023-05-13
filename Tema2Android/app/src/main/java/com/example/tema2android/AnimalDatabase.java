package com.example.tema2android;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Animal.class}, version = 1)
public abstract class AnimalDatabase extends RoomDatabase {
    public abstract AnimalDao animalDao();
}

