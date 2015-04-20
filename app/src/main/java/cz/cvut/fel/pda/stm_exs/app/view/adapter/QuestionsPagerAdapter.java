package cz.cvut.fel.pda.stm_exs.app.view.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * @author jan.lantora
 *         <p/>
 *         A {@link QuestionsPagerAdapter} that returns a fragment corresponding to
 *         one of the sections/tabs/pages.
 */
public class QuestionsPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments;

    public QuestionsPagerAdapter(FragmentManager fragmentManager, List<Fragment> myFrags) {
        super(fragmentManager);
        mFragments = myFrags;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Integer.toString(position);
    }
}


