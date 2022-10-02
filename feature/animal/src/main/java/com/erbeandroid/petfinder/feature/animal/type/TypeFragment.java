package com.erbeandroid.petfinder.feature.animal.type;

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
import com.erbeandroid.petfinder.feature.animal.databinding.FragmentTypeBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TypeFragment extends Fragment {

    private FragmentTypeBinding binding;
    private TypeViewModel typeViewModel;
    private TypeAdapter typeAdapter;

    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {
        binding = FragmentTypeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        typeViewModel = new ViewModelProvider(this).get(TypeViewModel.class);

        typeAdapter = new TypeAdapter((viewAdapter, item) -> {
            if (item.getName() != null) {
                Navigation.findNavController(view).navigate(
                    TypeFragmentDirections.actionTypeFragmentToBreedFragment(item.getName())
                );
            }
        });
        binding.recyclerView.setAdapter(typeAdapter);

        binding.refreshLayout.setOnRefreshListener(() -> {
            typeViewModel.getTypes();
            binding.refreshLayout.setRefreshing(false);
        });

        observeData();
    }

    private void observeData() {
        typeViewModel.typeState.observe(getViewLifecycleOwner(), typeState -> {
            binding.progressBar.setVisibility(Extension.isVisible(typeState.isLoading()));
            if (typeState.isSuccess()) {
                Log.d("TAG", typeState.getData().toString());
                typeAdapter.submitList(typeState.getData());
            }
            if (typeState.isError()) {
                Log.d("TAG", typeState.getThrowable().getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}