package com.example.mediastackapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediastackapp.R;

import java.util.ArrayList;

public class CategoriesListAdapter extends RecyclerView.Adapter<CategoriesListAdapter.CategoriesListViewHolder>{
    private ArrayList<String> categoriesList;
    private OnItemClickListener mlistener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mlistener = listener;
    }

    public static class CategoriesListViewHolder extends RecyclerView.ViewHolder {
        public TextView category;

        public CategoriesListViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            category = itemView.findViewById(R.id.categoryTV);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }

    public CategoriesListAdapter(ArrayList<String> categoriesList) {
        this.categoriesList = categoriesList;
    }

    @NonNull
    @Override
    public CategoriesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        CategoriesListViewHolder categoriesListViewHolder = new CategoriesListViewHolder(v, mlistener);

        return categoriesListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesListViewHolder holder, int position) {
        String currentCategoryItem = categoriesList.get(position);

        holder.category.setText(currentCategoryItem);

    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }
}

