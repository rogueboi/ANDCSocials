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

public class SocietyInformationAdapter extends RecyclerView.Adapter<SocietyInformationAdapter.ViewHolder> {

    List<SocietyInformation> societyInformations;

    public SocietyInformationAdapter (List<SocietyInformation> societyInformations) {
        this.societyInformations=societyInformations;
    }

    @NonNull
    @NotNull
    @Override
    public SocietyInformationAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder( LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SocietyInformationAdapter.ViewHolder holder, int position) {
        holder.setValues(societyInformations.get(position));
    }

    @Override
    public int getItemCount() {
        return societyInformations.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView societyImage;
        TextView societyName;
        TextView description, studentCoordinatorName, societyContactInformation;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            societyImage=itemView.findViewById(R.id.societyImage);
            societyName=itemView.findViewById(R.id.societyName);
            description=itemView.findViewById(R.id.description);
            studentCoordinatorName=itemView.findViewById(R.id.studentCordinatorName);
            societyContactInformation=itemView.findViewById(R.id.societyContactInformation);
        }

        public void setValues(SocietyInformation societyInformation) {
            societyImage.setImageResource(societyInformation.societyImageID);
            societyName.setText(societyInformation.societyName);
            description.setText(societyInformation.description);
            studentCoordinatorName.setText(societyInformation.studentCoordinatorName);
            societyContactInformation.setText(societyInformation.societyContactInformation);
        }
    }
}
