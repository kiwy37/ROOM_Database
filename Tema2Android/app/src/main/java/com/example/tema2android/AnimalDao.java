package com.example.tema2android;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface AnimalDao {
    @Insert
    void insertAnimal(Animal animal);

    @Update
    void updateAnimal(Animal animal);

    @Query("SELECT * FROM animals")
    List<Animal> getAllAnimals();

    @Delete
    void deleteAnimal(Animal animal);
}
