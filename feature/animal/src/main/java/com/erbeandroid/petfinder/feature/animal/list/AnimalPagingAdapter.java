package com.erbeandroid.petfinder.feature.animal.list;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.erbeandroid.petfinder.core.data.model.Animal;
import com.erbeandroid.petfinder.feature.animal.databinding.ItemAnimalBinding;

import java.util.Objects;

public class AnimalPagingAdapter extends PagingDataAdapter<Animal, AnimalPagingAdapter.AnimalViewHolder> {

    private final OnAnimalClickListener listener;

    public AnimalPagingAdapter(OnAnimalClickListener listener) {
        super(animalCallback);
        this.listener = listener;
    }

    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAnimalBinding binding = ItemAnimalBinding.inflate(
            LayoutInflater.from(parent.getContext()),
            parent,
            false
        );
        return new AnimalViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        Animal item = getItem(position);
        if (item != null) {
            holder.bind(item, listener);
        }
    }

    static class AnimalViewHolder extends RecyclerView.ViewHolder {
        private final ItemAnimalBinding binding;

        public AnimalViewHolder(ItemAnimalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Animal item, OnAnimalClickListener listener) {
            binding.animal.setText(item.getName());
            binding.animal.setOnClickListener(view -> {
                listener.onAnimalClick(view, item);
            });
        }
    }

    private static final DiffUtil.ItemCallback<Animal> animalCallback = new DiffUtil.ItemCallback<Animal>() {
        @Override
        public boolean areItemsTheSame(@NonNull Animal oldItem, @NonNull Animal newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Animal oldItem, @NonNull Animal newItem) {
            return Objects.equals(oldItem, newItem);
        }
    };

    public interface OnAnimalClickListener {
        void onAnimalClick(View view, Animal item);
    }
}