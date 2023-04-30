package com.example.kiki.fragment.Home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.kiki.fragment.Home.Type.ActionTypeFragment;
import com.example.kiki.fragment.Home.Type.AdventureTypeFragment;
import com.example.kiki.fragment.Home.Type.ComedyTypeFragment;

public class ViewPager2Adapter extends FragmentStateAdapter {


    public ViewPager2Adapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new ActionTypeFragment();
            case 1:
                return new AdventureTypeFragment();
            case 2:
                return new ComedyTypeFragment();
            default:
                return new ActionTypeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
