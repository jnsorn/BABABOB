package kr.ac.hansung.bababob.Restaurant;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class RestaurantPagerAdapter extends FragmentPagerAdapter {
    private String restaurantName;
    private static int PAGE_NUM = 2;

    public RestaurantPagerAdapter(FragmentManager fm, String restaurantName) {
        super(fm);
        this.restaurantName = restaurantName;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                RestaurantMenuFragment restaurantMenuFragment = RestaurantMenuFragment.getInstance();
                restaurantMenuFragment.setRestaurantName(restaurantName);
                return restaurantMenuFragment;

            case 1:
                RestaurantReviewFragment restaurantReviewFragment = RestaurantReviewFragment.getInstance();
                restaurantReviewFragment.setRestaurantName(restaurantName);
                return restaurantReviewFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_NUM;
    }
}
