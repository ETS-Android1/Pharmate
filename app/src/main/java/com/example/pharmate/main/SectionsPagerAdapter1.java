package com.example.pharmate.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.pharmate.Frag1;
import com.example.pharmate.Frag2;
import com.example.pharmate.R;
import com.example.pharmate.Regperson1;
import com.example.pharmate.Regperson2;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter1 extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    public SectionsPagerAdapter1(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
     Fragment fragment=null;
     switch (position){
         case 0:
             fragment=new Regperson1();
             break;
         case 1:
             fragment=new Regperson2();
             break;

     }return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "İnformation 1";
            case 1:
                return "İnformation 2";

        }return null;

    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}