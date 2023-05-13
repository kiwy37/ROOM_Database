package com.example.tema2android;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "animals")
public class Animal {
    @PrimaryKey
    @NonNull
    private String name;

    @ColumnInfo(name = "continent")
    private String continent;

    public Animal(String name, String continent) {
        this.name = name;
        this.continent = continent;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }
}
