package com.erbeandroid.petfinder.feature.animal.breed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.erbeandroid.petfinder.core.data.model.Breed;
import com.erbeandroid.petfinder.feature.animal.databinding.ItemBreedBinding;

import java.util.Objects;

public class BreedAdapter extends ListAdapter<Breed, BreedAdapter.BreedViewHolder> {

    private final OnBreedClickListener listener;

    public BreedAdapter(OnBreedClickListener listener) {
        super(breedCallback);
        this.listener = listener;
    }

    @NonNull
    @Override
    public BreedAdapter.BreedViewHolder onCreateViewHolder(
        @NonNull ViewGroup parent,
        int viewType
    ) {
        ItemBreedBinding binding = ItemBreedBinding.inflate(
            LayoutInflater.from(parent.getContext()),
            parent,
            false
        );
        return new BreedViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BreedAdapter.BreedViewHolder holder, int position) {
        Breed item = getItem(position);
        holder.bind(item, listener);
    }

    static class BreedViewHolder extends RecyclerView.ViewHolder {
        private final ItemBreedBinding binding;

        public BreedViewHolder(ItemBreedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Breed item, OnBreedClickListener listener) {
            binding.breed.setText(item.getName());
            binding.breed.setOnClickListener(view ->
                listener.onBreedClick(view, item)
            );
        }
    }

    private static final DiffUtil.ItemCallback<Breed> breedCallback = new DiffUtil.ItemCallback<Breed>() {
        @Override
        public boolean areItemsTheSame(@NonNull Breed oldItem, @NonNull Breed newItem) {
            return oldItem.getName().equals(newItem.getName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Breed oldItem, @NonNull Breed newItem) {
            return Objects.equals(oldItem, newItem);
        }
    };

    public interface OnBreedClickListener {
        void onBreedClick(View view, Breed item);
    }
}