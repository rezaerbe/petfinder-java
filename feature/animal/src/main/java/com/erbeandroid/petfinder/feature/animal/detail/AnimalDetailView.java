package com.erbeandroid.petfinder.feature.animal.detail;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.erbeandroid.petfinder.core.data.model.AnimalDetail;
import com.erbeandroid.petfinder.feature.animal.databinding.ViewAnimalDetailBinding;

public class AnimalDetailView extends ConstraintLayout {

    private final ViewAnimalDetailBinding binding =
        ViewAnimalDetailBinding.inflate(LayoutInflater.from(getContext()), this);

    public AnimalDetailView(@NonNull Context context) {
        super(context);
    }

    public AnimalDetailView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimalDetailView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAnimalDetailView(AnimalDetail animalDetail) {
        binding.animalName.setText(animalDetail.getName());
        binding.animalDescription.setText(
            animalDetail.getDescription() != null ? animalDetail.getDescription() : "No description"
        );
    }
}