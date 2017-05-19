package ReportTabFragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class TabPurchasingReportPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;


    public TabPurchasingReportPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                return  new FragmentDailyPurchasingReport();

            case 1:
                return  new FragmentPurchasing1MonthReport();

            case 2:
                return new FragmentPurchasing3MonthReport();

            case 3:
                return new FragmentPurchasingReport();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
