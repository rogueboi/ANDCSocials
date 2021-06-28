package com.andc.andcsocials;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    int[] images;

    public MainAdapter (int[] images) {
        this.images=images;
    }

    @NonNull
    @NotNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_viewpage_example,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MainAdapter.ViewHolder holder, int position) {
        holder.image_view.setBackgroundResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image_view;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            image_view=itemView.findViewById(R.id.image_view);
        }
    }
}
