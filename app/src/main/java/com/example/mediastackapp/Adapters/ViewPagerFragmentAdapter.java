package com.example.mediastackapp.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mediastackapp.Fragments.CategoriesFragment;
import com.example.mediastackapp.Fragments.FavoritesFragment;

public class ViewPagerFragmentAdapter extends FragmentStateAdapter {

    public ViewPagerFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position)
        {
            case 0:
                return new CategoriesFragment();
            case 1:
                return new FavoritesFragment();
        }
        return new CategoriesFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
