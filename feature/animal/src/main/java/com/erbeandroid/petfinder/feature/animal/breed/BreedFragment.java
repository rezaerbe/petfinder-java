package com.erbeandroid.petfinder.feature.animal.breed;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.erbeandroid.petfinder.feature.animal.common.Extension;
import com.erbeandroid.petfinder.feature.animal.databinding.FragmentBreedBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BreedFragment extends Fragment {

    private FragmentBreedBinding binding;
    private BreedViewModel breedViewModel;
    private BreedAdapter breedAdapter;

    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {
        binding = FragmentBreedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        breedViewModel = new ViewModelProvider(this).get(BreedViewModel.class);

        breedAdapter = new BreedAdapter((viewAdapter, item) -> {
            String type = getArguments() != null ? getArguments().getString("type") : null;
            if (item.getName() != null) {
                Navigation.findNavController(viewAdapter).navigate(
                    BreedFragmentDirections.actionBreedFragmentToAnimalFragment(type, item.getName())
                );
            }
        });
        binding.recyclerView.setAdapter(breedAdapter);

        binding.refreshLayout.setOnRefreshListener(() -> {
            breedViewModel.getBreeds();
            binding.refreshLayout.setRefreshing(false);
        });

        observeData();
    }

    private void observeData() {
        breedViewModel.breedState.observe(getViewLifecycleOwner(), breedState -> {
            binding.progressBar.setVisibility(Extension.isVisible(breedState.isLoading()));
            if (breedState.isSuccess()) {
                Log.d("TAG", breedState.getData().toString());
                breedAdapter.submitList(breedState.getData());
            }
            if (breedState.isError()) {
                Log.d("TAG", breedState.getThrowable().getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}