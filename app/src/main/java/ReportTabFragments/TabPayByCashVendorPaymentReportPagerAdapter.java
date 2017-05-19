package ReportTabFragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class TabPayByCashVendorPaymentReportPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;


    public TabPayByCashVendorPaymentReportPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new FragmentPayByCash1MonthReport();

            case 1:
                return new FragmentPayByCash3MonthReport();

            case 2:
                return new FragmentPayByCash6MonthReport();

            case 3:
                return new FragmentPayByCashReport();

        }

        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
