package com.andc.andcsocials;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {

    int[] images;

    public ViewPagerAdapter (int[] images) {
        this.images=images;
    }

    @NonNull
    @NotNull
    @Override
    public ViewPagerAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_viewpage_example,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewPagerAdapter.ViewHolder holder, int position) {
        holder.img_view.setBackgroundResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_view;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            img_view=itemView.findViewById(R.id.image_view);
        }
    }
}
