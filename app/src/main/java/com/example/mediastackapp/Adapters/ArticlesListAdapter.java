package com.example.mediastackapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediastackapp.Data.Article;
import com.example.mediastackapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArticlesListAdapter extends RecyclerView.Adapter<ArticlesListAdapter.ArticlesListViewHolder>{
    private ArrayList<Article> articlesList;
    private OnItemClickListener mlistener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mlistener = listener;
    }

    public static class ArticlesListViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView date;
        public ImageView imgUrl;

        public ArticlesListViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTV);
            date = itemView.findViewById(R.id.dateTV);
            imgUrl = itemView.findViewById(R.id.imageId);

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

    public ArticlesListAdapter(ArrayList<Article> articlesList) {
        this.articlesList = articlesList;
    }

    @NonNull
    @Override
    public ArticlesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item, parent, false);
        ArticlesListViewHolder articlesListViewHolder = new ArticlesListViewHolder(v, mlistener);

        return articlesListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticlesListViewHolder holder, int position) {
        Article currentArticle = articlesList.get(position);

        holder.title.setText(currentArticle.getTitle());
        holder.date.setText(currentArticle.getPublished_at());
        Picasso.get()
                .load(currentArticle.getImage())
                .placeholder(R.drawable.ic_launcher_foreground)
                .fit()
                .centerCrop()
                .into(holder.imgUrl);
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }
}

