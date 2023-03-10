package com.example.newsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private static String TAG="ArticleAdapter";
    private ArrayList<NewsArticle> mArrayList;
    private Context mContext;

    public ArticleAdapter(Context context,ArrayList<NewsArticle> list){
        this.mContext=context;
        this.mArrayList=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.article_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsArticle currentArticle=mArrayList.get(position);

        holder.title.setText(currentArticle.getTitle());
        holder.description.setText(currentArticle.getDescription());

        holder.contributordate.setText(currentArticle.getTitle()+
                " | "+currentArticle.getPublishedAt().substring(0,10));

        Glide.with(mContext).load(currentArticle.getUrlToImage()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("url_key",currentArticle.getUrl());

                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView title,description,contributordate;
        private ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title_id);
            description=itemView.findViewById(R.id.description_id);
            image = itemView.findViewById(R.id.image_id);
            contributordate=itemView.findViewById(R.id.contributordate_id);
        }
    }

}

