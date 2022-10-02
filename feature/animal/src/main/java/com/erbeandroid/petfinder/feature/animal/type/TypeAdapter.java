package com.erbeandroid.petfinder.feature.animal.type;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.erbeandroid.petfinder.core.data.model.Type;
import com.erbeandroid.petfinder.feature.animal.databinding.ItemTypeBinding;

import java.util.Objects;

public class TypeAdapter extends ListAdapter<Type, TypeAdapter.TypeViewHolder> {

    private final OnTypeClickListener listener;

    public TypeAdapter(OnTypeClickListener listener) {
        super(typeCallback);
        this.listener = listener;
    }

    @NonNull
    @Override
    public TypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTypeBinding binding = ItemTypeBinding.inflate(
            LayoutInflater.from(parent.getContext()),
            parent,
            false
        );
        return new TypeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TypeViewHolder holder, int position) {
        Type item = getItem(position);
        holder.bind(item, listener);
    }

    static class TypeViewHolder extends RecyclerView.ViewHolder {
        private final ItemTypeBinding binding;

        public TypeViewHolder(ItemTypeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Type item, OnTypeClickListener listener) {
            binding.type.setText(item.getName());
            binding.type.setOnClickListener(view ->
                listener.onTypeClick(view, item)
            );
        }
    }

    private static final DiffUtil.ItemCallback<Type> typeCallback = new DiffUtil.ItemCallback<Type>() {
        @Override
        public boolean areItemsTheSame(@NonNull Type oldItem, @NonNull Type newItem) {
            return oldItem.getName().equals(newItem.getName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Type oldItem, @NonNull Type newItem) {
            return Objects.equals(oldItem, newItem);
        }
    };

    public interface OnTypeClickListener {
        void onTypeClick(View view, Type item);
    }
}