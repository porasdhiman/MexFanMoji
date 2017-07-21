package worksdelight.openbusiness.fragment.navigation;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import worksdelight.openbusiness.BuildConfig;
import worksdelight.openbusiness.MainActivity;
import worksdelight.openbusiness.R;
import worksdelight.openbusiness.fragment.FragmentHelp;
import worksdelight.openbusiness.fragment.FragmentLegal;
import worksdelight.openbusiness.fragment.FragmentRelationship;
import worksdelight.openbusiness.fragment.FragmentReport;
import worksdelight.openbusiness.fragment.FragmentSelling;
import worksdelight.openbusiness.fragment.FragmentSetting;


/**
 * @author msahakyan
 */

public class FragmentNavigationManager implements NavigationManager {

    private static FragmentNavigationManager sInstance;

    private FragmentManager mFragmentManager;
    private MainActivity mActivity;

    public static FragmentNavigationManager obtain(MainActivity activity) {
        if (sInstance == null) {
            sInstance = new FragmentNavigationManager();
        }
        sInstance.configure(activity);
        return sInstance;
    }

    private void configure(MainActivity activity) {
        mActivity = activity;
        mFragmentManager = mActivity.getSupportFragmentManager();
    }



    private void showFragment(Fragment fragment, boolean allowStateLoss) {
        FragmentManager fm = mFragmentManager;

        @SuppressLint("CommitTransaction")
        FragmentTransaction ft = fm.beginTransaction()
            .replace(R.id.container, fragment);

        ft.addToBackStack(null);

        if (allowStateLoss || !BuildConfig.DEBUG) {
            ft.commitAllowingStateLoss();
        } else {
            ft.commit();
        }

        fm.executePendingTransactions();
    }

    @Override
    public void showFragmentSelleing(String title) {
        showFragment(FragmentSelling.newInstance(title), false);

    }

    @Override
    public void showFragmentRelationShip(String title) {
        showFragment(FragmentRelationship.newInstance(title), false);
    }

    @Override
    public void showFragmentSetting(String title) {
        showFragment(FragmentSetting.newInstance(title), false);
    }

    @Override
    public void showFragmentReport(String title) {
        showFragment(FragmentReport.newInstance(title), false);
    }
    @Override
    public void showFragmentHelp(String title) {
        showFragment(FragmentHelp.newInstance(title), false);
    }
    @Override
    public void showFragmentLegal(String title) {
        showFragment(FragmentLegal.newInstance(title), false);
    }



}
