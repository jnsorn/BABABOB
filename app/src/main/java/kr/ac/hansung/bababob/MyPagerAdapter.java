package kr.ac.hansung.bababob;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import kr.ac.hansung.bababob.Restaurant.RestaurantFragment;
import kr.ac.hansung.bababob.SchoolCafeteria.SchoolCafeteriaFragment;

public class MyPagerAdapter extends FragmentPagerAdapter {

    private static int PAGE_NUM = 3;

    public MyPagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TimelineFragment.getInstance();
            case 1:
                return SchoolCafeteriaFragment.getInstance();
            case 2:
                return RestaurantFragment.getInstance();
            default:
                return null;
        }
    }

    public String getPageTitle(int position){
        switch (position) {
            case 0:
                return "TimeLine";
            case 1:
                return "School Cafeteria";
            case 2:
                return "Restaurant";
            default:
                return null;
        }
    }

    public int getIcon(int position){
        switch (position) {
            case 0:
                return R.drawable.menu;
            case 1:
                return R.drawable.shop;
            case 2:
                return R.drawable.restaurant;
            default:
                return 0;
        }
    }
    @Override
    public int getCount() {
        return PAGE_NUM;
    }
}
