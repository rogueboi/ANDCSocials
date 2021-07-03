package com.andc.andcsocials;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

   private List<SocietyInformation> societyInformations;

    public MainAdapter (List<SocietyInformation> societyInformations) {
        this.societyInformations=societyInformations;
    }

    @NonNull
    @NotNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder( LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_item,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MainAdapter.ViewHolder holder, int position) {
        holder.setValues(societyInformations.get(position));
    }

    @Override
    public int getItemCount() {
        return societyInformations.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView societyImage;
        TextView societyName;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            societyImage=itemView.findViewById(R.id.societyImage);
            societyName=itemView.findViewById(R.id.societyName);
        }

        public void setValues(SocietyInformation societyInformation) {
            societyImage.setImageResource(societyInformation.societyImageID);
            societyName.setText(societyInformation.societyName);
        }
    }
}
