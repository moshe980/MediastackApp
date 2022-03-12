package com.example.mediastackapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mediastackapp.Activities.ArticleResultsActivity;
import com.example.mediastackapp.Adapters.CategoriesListAdapter;
import com.example.mediastackapp.R;

import java.util.ArrayList;
import java.util.Arrays;

public class CategoriesFragment extends Fragment {
    public ArrayList<String> categoriesList;
    private RecyclerView mRecyclerView;
    public static CategoriesListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_categories, container, false);

        mRecyclerView = view.findViewById(R.id.categories_list_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        categoriesList = new ArrayList<String>();

        String[] categories = getResources().getStringArray(R.array.categories);
        categoriesList.addAll(Arrays.asList(categories));

        mAdapter = new CategoriesListAdapter(categoriesList);
        mAdapter.setOnItemClickListener(new CategoriesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getContext(), ArticleResultsActivity.class);
                intent.putExtra("category", categoriesList.get(position));
                startActivity(intent);
            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        return view;

    }
}