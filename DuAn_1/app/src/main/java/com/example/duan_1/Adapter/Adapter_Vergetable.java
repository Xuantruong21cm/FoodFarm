package com.example.duan_1.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.duan_1.Fragment.Fragment_Cu;
import com.example.duan_1.Fragment.Fragment_Hoa;
import com.example.duan_1.Fragment.Fragment_Mam;
import com.example.duan_1.Fragment.Fragment_Rau;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Vergetable extends FragmentPagerAdapter {
    private List<Fragment> fragmentList_Vergetable ;

    public Adapter_Vergetable(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        fragmentList_Vergetable = new ArrayList<>();
        fragmentList_Vergetable.add(new Fragment_Rau());
        fragmentList_Vergetable.add(new Fragment_Cu());
        fragmentList_Vergetable.add(new Fragment_Hoa());
        fragmentList_Vergetable.add(new Fragment_Mam()) ;
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList_Vergetable.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList_Vergetable.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Rau" ;
            case 1:
                return "Củ" ;
            case 2:
                return "Hoa" ;
            case 3:
                return "Mầm" ;
            default:
                return "";
        }
    }
}
