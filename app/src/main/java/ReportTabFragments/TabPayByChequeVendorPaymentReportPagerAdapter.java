package ReportTabFragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class TabPayByChequeVendorPaymentReportPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;


    public TabPayByChequeVendorPaymentReportPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new FragmentPayByCheque1MonthReport();

            case 1:
                return new FragmentPayByCheque3MonthReport();

            case 2:
                return new FragmentPayByCheque6MonthReport();

            case 3:
                return new FragmentPayByChequeReport();

        }

        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
