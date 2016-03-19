package me.kantrael.calcit.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import me.kantrael.calcit.R;
import me.kantrael.calcit.fragment.CalculatorFragment;
import me.kantrael.calcit.fragment.RPNFragment;
import me.kantrael.calcit.fragment.WolframAlphaFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private static final int SECTION_CALCULATOR = 1;
    private static final int SECTION_RPN = 2;
    private static final int SECTION_WOLFRAM_ALPHA = 3;

    private int[] sections = {SECTION_CALCULATOR, SECTION_RPN, SECTION_WOLFRAM_ALPHA};
    private Context context;

    public SectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (sections[position]) {
            case SECTION_CALCULATOR:
                return new CalculatorFragment();
            case SECTION_RPN:
                return new RPNFragment();
            case SECTION_WOLFRAM_ALPHA:
                return new WolframAlphaFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return sections.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (context != null) {
            Resources resources = context.getResources();

            switch (sections[position]) {
                case SECTION_CALCULATOR:
                    return resources.getString(R.string.section_title_calculator);
                case SECTION_RPN:
                    return resources.getString(R.string.section_title_rpn);
                case SECTION_WOLFRAM_ALPHA:
                    return resources.getString(R.string.section_title_wolfram_alpha);
            }
        }
        return null;
    }
}