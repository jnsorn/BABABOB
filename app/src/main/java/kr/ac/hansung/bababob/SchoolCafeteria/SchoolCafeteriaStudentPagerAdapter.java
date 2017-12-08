package kr.ac.hansung.bababob.SchoolCafeteria;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Jina on 2017-12-08.
 */

public class SchoolCafeteriaStudentPagerAdapter extends FragmentPagerAdapter {
    private String schoolCafeteria;
    private static int PAGE_NUM = 2;

    public SchoolCafeteriaStudentPagerAdapter(FragmentManager fm, String schoolCafeteria) {
        super(fm);
        this.schoolCafeteria = schoolCafeteria;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                SchoolCafeteriaStudentMenuFragment cafeteriaMenuFragment = SchoolCafeteriaStudentMenuFragment.getInstance();
                //cafeteriaMenuFragment.setCafeteriaName(schoolCafeteria);
                return cafeteriaMenuFragment;
            case 1:
                SchoolCafeteriaStudentReviewFragment cafeteriaReviewFragment = SchoolCafeteriaStudentReviewFragment.getInstance();
                //cafeteriaReviewFragment.setCafeteriaName(schoolCafeteria);
                return cafeteriaReviewFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_NUM;
    }
}
