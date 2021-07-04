package com.andc.andcsocials;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;

public class FragmentAdapterSocietyDashboard extends FragmentStateAdapter {

    public FragmentAdapterSocietyDashboard(@NonNull @NotNull FragmentManager fragmentManager,
                                           @NonNull @NotNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 1:
                return new society_coordinators_dashboard();
            case 2:
                return new info_links_society_Dashboard();

        }
        return new SocietyPhotos_dashboard();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
