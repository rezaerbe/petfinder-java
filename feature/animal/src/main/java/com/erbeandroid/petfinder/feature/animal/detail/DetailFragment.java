package com.erbeandroid.petfinder.feature.animal.detail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.erbeandroid.petfinder.feature.animal.common.Extension;
import com.erbeandroid.petfinder.feature.animal.databinding.FragmentDetailBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DetailFragment extends Fragment {

    private FragmentDetailBinding binding;
    private DetailViewModel detailViewModel;

    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        detailViewModel = new ViewModelProvider(this).get(DetailViewModel.class);

        observeData();
    }

    private void observeData() {
        detailViewModel.animalDetailState.observe(getViewLifecycleOwner(), animalDetailState -> {
            binding.progressBar.setVisibility(Extension.isVisible(animalDetailState.isLoading()));
            if (animalDetailState.isSuccess()) {
                Log.d("TAG", animalDetailState.getData().toString());
                binding.animalView.setAnimalDetailView(animalDetailState.getData());
            }
            if (animalDetailState.isError()) {
                Log.d("TAG", "Error");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}