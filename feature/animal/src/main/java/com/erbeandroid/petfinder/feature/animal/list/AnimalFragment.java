package com.erbeandroid.petfinder.feature.animal.list;

import static autodispose2.AutoDispose.autoDisposable;

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
import androidx.paging.LoadState;

import com.erbeandroid.petfinder.feature.animal.common.Extension;
import com.erbeandroid.petfinder.feature.animal.databinding.FragmentAnimalBinding;

import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AnimalFragment extends Fragment {

    private FragmentAnimalBinding binding;
    private AnimalViewModel animalViewModel;
    private AnimalPagingAdapter animalAdapter;

    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {
        binding = FragmentAnimalBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        animalViewModel = new ViewModelProvider(this).get(AnimalViewModel.class);

        animalAdapter = new AnimalPagingAdapter((viewAdapter, item) -> {
            if (item.getId() != null) {
                Navigation.findNavController(viewAdapter).navigate(
                    AnimalFragmentDirections.actionAnimalFragmentToDetailFragment(item.getId())
                );
            }
        });
        binding.recyclerView.setAdapter(animalAdapter);

        observeData();
    }

    private void observeData() {
        animalAdapter.addLoadStateListener(loadStates -> {
            binding.progressBar.setVisibility(
                Extension.isVisible(loadStates.getRefresh() instanceof LoadState.Loading)
            );
            if (!(loadStates.getRefresh() instanceof LoadState.Loading)) {
                Log.d("TAG", "Retry");
            }
            if (loadStates.getRefresh() instanceof LoadState.Error) {
                Log.d("TAG", "Error");
            }
            return null;
        });

        animalViewModel.flowable
            .to(autoDisposable(AndroidLifecycleScopeProvider.from(this)))
            .subscribe(pagingData -> animalAdapter.submitData(getLifecycle(), pagingData));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}