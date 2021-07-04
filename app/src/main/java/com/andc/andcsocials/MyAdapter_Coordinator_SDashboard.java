package com.andc.andcsocials;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyAdapter_Coordinator_SDashboard extends RecyclerView.Adapter<MyHolder_Coordinators_SDashboaard> {

    Context c;
    ArrayList<Model_Coordinators_Sdashboard> models;


    public MyAdapter_Coordinator_SDashboard(Context c, ArrayList<Model_Coordinators_Sdashboard> models) {
        this.c = c;
        this.models = models;
    }

    @NonNull
    @NotNull
    @Override
    public MyHolder_Coordinators_SDashboaard onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_coordinators_sdashboard,null);

        return new MyHolder_Coordinators_SDashboaard(view);



    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder_Coordinators_SDashboaard holder, int position) {

        holder.mTitle.setText(models.get(position).getTitle());
        holder.mDes.setText(models.get(position).getDescription());
        holder.mImageView.setImageResource(models.get(position).getImg());


    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
