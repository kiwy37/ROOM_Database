package com.example.tema2android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private EditText t1, t2;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private List<Animal> lista;
    private AnimalDatabase animalDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the AnimalDatabase
        animalDatabase = Room.databaseBuilder(getApplicationContext(),
                        AnimalDatabase.class, "animal-database")
                .build();

        mButton = findViewById(R.id.addButton);
        t1 = findViewById(R.id.text1);
        t2 = findViewById(R.id.text2);
        recyclerView = findViewById(R.id.recycler_view);

        List<String> continente = Arrays.asList("EUROPA", "AUSTRALIA", "AMERICA DE SUD", "AMERICA DE NORD", "ASIA", "ANTARCTICA", "AFRICA");

        lista = new ArrayList<>();
        adapter = new RecyclerViewAdapter(lista, animalDatabase);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        displayDatabase(); // Load data from Room

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (t1.getText().toString().trim().isEmpty() || t2.getText().toString().trim().isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Unul dintre fielduri e gol.");
                    builder.setTitle("Alert !");
                    builder.setCancelable(false);
                    builder.setNegativeButton("Back", (DialogInterface.OnClickListener) (dialog, which) -> {
                        dialog.cancel();
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else if (!continente.contains(t2.getText().toString().toUpperCase())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Continent inexistent.");
                    builder.setTitle("Alert !");
                    builder.setCancelable(false);
                    builder.setNegativeButton("Back", (DialogInterface.OnClickListener) (dialog, which) -> {
                        dialog.cancel();
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    String text1 = t1.getText().toString();
                    String text2 = t2.getText().toString();
                    Animal a = new Animal(text1,text2);
                    if(existAnimal(a))
                    {
                        // Search for the existing animal with the same name
                        for (Animal existingAnimal : lista) {
                            if (existingAnimal.getName().equalsIgnoreCase(a.getName())) {
                                // Update the continent of the existing animal
                                existingAnimal.setContinent(a.getContinent());
                                updateAnimal(existingAnimal);
                                break;
                            }
                        }
                        adapter.notifyDataSetChanged();
                        t1.setText("");
                        t2.setText("");
                    }
                    else
                    {
                        lista.add(a);
                        insertAnimal(a);
                    }

                    adapter.notifyDataSetChanged();
                    t1.setText("");
                    t2.setText("");
                }
            }
        });
    }


    private boolean existAnimal(Animal a)
    {
        boolean exists = false;
        for (Animal obj : lista) {
            if (obj.getName().toUpperCase().equals(a.getName().toUpperCase())) {
                exists = true;
                break;
            }
        }

        if (exists) {
            return true;
        } else {
            return false;
        }
    }

    private void insertAnimal(Animal animal) {
        new Thread(() -> {
            animalDatabase.animalDao().insertAnimal(animal);
            runOnUiThread(() -> {
                displayDatabase(); // Refresh the data after insertion
            });
        }).start();
    }

    private void updateAnimal(Animal animal) {
        new Thread(() -> {
            animalDatabase.animalDao().updateAnimal(animal);
        }).start();
    }
    private void displayDatabase() {
        new Thread(() -> {
            lista.clear();
            lista.addAll(animalDatabase.animalDao().getAllAnimals());
            runOnUiThread(() -> {
                adapter.notifyDataSetChanged();
            });
        }).start();
    }

}

