package com.example.chaitanya_university_2;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {
    private int tabsnumber;

    public PageAdapter(@NonNull FragmentManager fm, int behavior,int tabs) {
        super(fm, behavior);
        this.tabsnumber=tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0: return new student_exams_tab();
            case 1: return new student_results_tab();
            default: return null;
        }

    }

    @Override
    public int getCount() {
        return tabsnumber;
    }
}
