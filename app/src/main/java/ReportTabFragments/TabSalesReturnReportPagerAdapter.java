package ReportTabFragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class TabSalesReturnReportPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;


    public TabSalesReturnReportPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new FragmentWithInvoiceReport();

            case 1:
                return new FragmentWithoutInvoiceReport();

        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
