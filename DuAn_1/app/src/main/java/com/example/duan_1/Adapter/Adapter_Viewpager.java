package com.example.duan_1.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.duan_1.Fragment.Fragment_Beef;
import com.example.duan_1.Fragment.Fragment_Chicken;
import com.example.duan_1.Fragment.Fragment_Fish;
import com.example.duan_1.Fragment.Fragment_Pork;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Viewpager extends FragmentPagerAdapter {
    private List<Fragment> fragmentList_Thit ;

    public Adapter_Viewpager(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        fragmentList_Thit = new ArrayList<>();
        fragmentList_Thit.add(new Fragment_Pork());
        fragmentList_Thit.add(new Fragment_Beef());
        fragmentList_Thit.add(new Fragment_Chicken());
        fragmentList_Thit.add(new Fragment_Fish()) ;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList_Thit.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList_Thit.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Lợn" ;
            case 1:
                return "Bò" ;
            case 2:
                return "Gà" ;
            case 3:
                return "Cá" ;
            default:
                return "";
        }
    }
}
