package ReportTabFragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;



public class TabInventoryReportPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;


    public TabInventoryReportPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                return new FragmentDailyInventoryReport();

            case 1:
                return  new FragmentInventory1MonthReport();

            case 2:
                return new FragmentInventory3MonthReport();

            case 3:
                return new FragmentInventory6MonthReport();

            case 4:
                return new FragmentInventoryReport();

            case 5:
                return new FragmentStockReport();


        }
        return null;
    }

    @Override
    public int getCount() {
        return 6;
    }
}
