package ReportTabFragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class TabSalesReportPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;


    public TabSalesReportPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new FragmentDailySalesReport();

            case 1:
                return new FragmentWeeklySalesReport();

            case 2:
                return new FragmentMonthlySalesReport();

            case 3:
                return new FragmentQuarterlySalesReport();

            case 4:
                return new FragmentYearlySalesReport();

            case 5:
                return new FragmentSalesReport();

            case  6:
                return  new FragmentDailyOverallSalesReport();
            case  7:
                return  new FragmentLostSalesReport();





        }

        return null;
    }

    @Override
    public int getCount() {
        return 8;
    }
}
