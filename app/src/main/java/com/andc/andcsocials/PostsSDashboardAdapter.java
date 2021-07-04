package com.andc.andcsocials;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PostsSDashboardAdapter extends RecyclerView.Adapter<PostsSDashboardAdapter.PostViewHolder>{

    public PostsSDashboardAdapter(List<PostItemSDashboard> postItemSDashboardList) {
        this.postItemSDashboardList = postItemSDashboardList;
    }

    @NonNull
    @NotNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new PostViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.post_society_dashboard_container,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PostsSDashboardAdapter.PostViewHolder holder, int position) {
        holder.setPostImageView(postItemSDashboardList.get(position));
    }

    @Override
    public int getItemCount() {
        return postItemSDashboardList.size();
    }

    private List<PostItemSDashboard> postItemSDashboardList;

    class PostViewHolder extends RecyclerView.ViewHolder{

        RoundedImageView postImageView;

        PostViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            postImageView = itemView.findViewById(R.id.imagePostSdashboard);
        }

        void setPostImageView(PostItemSDashboard postItemSDashboard) {
            postImageView.setImageResource(postItemSDashboard.getImage());
        }
    }
}
