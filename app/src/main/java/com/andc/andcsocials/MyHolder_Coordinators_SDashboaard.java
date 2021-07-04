package com.andc.andcsocials;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class MyHolder_Coordinators_SDashboaard extends RecyclerView.ViewHolder {

    ImageView mImageView;
    TextView mTitle, mDes;

    public MyHolder_Coordinators_SDashboaard(@NonNull @NotNull View itemView) {
        super(itemView);

        this.mImageView = itemView.findViewById(R.id.imageIvCoordinators);
        this.mTitle = itemView.findViewById(R.id.titleTvCoordinators);
        this.mDes = itemView.findViewById(R.id.descrptionTvCoordinators);
    }
}
