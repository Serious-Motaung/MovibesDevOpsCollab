package com.example.movibes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private RecyclerReviewListener listener;
    private Context context;
    private List<Reviews> list;

    public ReviewAdapter(Context context, List<Reviews> list, RecyclerReviewListener listener)
    {
        this.listener = listener;
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.reviews_list,parent,false);
        return new ReviewAdapter.ViewHolder(v,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ViewHolder holder, int position) {
        Reviews reviews = list.get(position);

        holder.tvReviewAccountOwner.setText(reviews.getReviewProfileId());
        holder.tvReviewComment.setText(reviews.getReviewComment());

        //Glide.with(context).load(event.getImageUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.ivFlyer);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvReviewAccountOwner,tvReviewComment;
        public ImageView ivReviewProfilePic;

        public ViewHolder(@NonNull View itemView, final RecyclerReviewListener listener) {
            super(itemView);

            tvReviewAccountOwner = itemView.findViewById(R.id.tvReviewAccountOwner);
            tvReviewComment = itemView.findViewById(R.id.tvReviewComment);
            ivReviewProfilePic = itemView.findViewById(R.id.ivReviewProfilePic);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null)
                    {
                        listener.onRowClicked(getAdapterPosition());
                    }
                }
            });
        }
    }
}
