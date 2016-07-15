package com.example.rahul.forms;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
;import java.util.ArrayList;
import java.util.List;


public class MyPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {
    public List<Fragment> mfragments = new ArrayList<Fragment>();
    private ViewPager pager;
    private TabLayout tabLayout;
    public MyPagerAdapter(FragmentManager fm,ViewPager pager,TabLayout tabLayout) {
        super(fm);
        this.pager = pager;
        pager.setOnPageChangeListener(this);
        this.tabLayout = tabLayout;
        mfragments.add(PersonalInfoFragment.newInstance());
    }
    @Override
    public Fragment getItem(int position) {
            return mfragments.get(position);
    }

    @Override
    public int getCount() {
        return mfragments.size();
    }
    public void addTab(Fragment frag) {
        mfragments.add(frag);
        notifyDataSetChanged();
        tabLayout.setupWithViewPager(pager);
        for(int i = 0 ; i < mfragments.size()  ; i++) {
            switch(i) {
                case 0:tabLayout.getTabAt(i).setIcon(R.drawable.people);
                    break;
                case 1:tabLayout.getTabAt(i).setIcon(R.drawable.book);
                    break;
                case 2:tabLayout.getTabAt(i).setIcon(R.drawable.home);
                    break;
                case 3:tabLayout.getTabAt(i).setIcon(R.drawable.graduation);
                    break;
                case 4:tabLayout.getTabAt(i).setIcon(R.drawable.photo);
                    break;
            }
        }
    }

    public int getSize() {
        return mfragments.size();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch(position) {
            case 0:tabLayout.setSelectedTabIndicatorColor(tabLayout.getResources().getColor(R.color.personal));
                break;
            case 1:tabLayout.setSelectedTabIndicatorColor(tabLayout.getResources().getColor(R.color.additional));
                break;
            case 2:tabLayout.setSelectedTabIndicatorColor(tabLayout.getResources().getColor(R.color.education));
                break;
            case 3:tabLayout.setSelectedTabIndicatorColor(tabLayout.getResources().getColor(R.color.jee));
                break;
            case 4:tabLayout.setSelectedTabIndicatorColor(tabLayout.getResources().getColor(R.color.photo));
                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
