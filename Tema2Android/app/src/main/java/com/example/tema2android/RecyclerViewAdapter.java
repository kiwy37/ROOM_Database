package com.example.tema2android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Animal> dataList;
    private AnimalDatabase animalDatabase;

    public RecyclerViewAdapter(List<Animal> dataList, AnimalDatabase animalDatabase) {
        this.dataList = dataList;
        this.animalDatabase = animalDatabase;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String data = dataList.get(position).getName();
        holder.animalText.setText(data);
        data = dataList.get(position).getContinent();
        holder.continentText.setText(data);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                Animal animalToDelete = dataList.get(adapterPosition);
                deleteAnimal(animalToDelete);
                dataList.remove(adapterPosition);
                notifyItemRemoved(adapterPosition);
            }
        });
    }

    private void deleteAnimal(Animal animal) {
        new Thread(() -> {
            animalDatabase.animalDao().deleteAnimal(animal);
        }).start();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView animalText;
        TextView continentText;
        ImageButton imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            animalText = itemView.findViewById(R.id.animal);
            continentText = itemView.findViewById(R.id.continent);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }
}

