package org.odddev.gameofthrones.houses;

import org.odddev.gameofthrones.R;
import org.odddev.gameofthrones.core.utils.FragmentUtils;
import org.odddev.gameofthrones.databinding.HousesActivityBinding;
import org.odddev.gameofthrones.splash.data.HouseItem;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.IntentCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public class HousesActivity extends AppCompatActivity {

    @IntDef({R.id.nav_stark, R.id.nav_lannister, R.id.nav_targaryen})
    @Retention(RetentionPolicy.SOURCE)
    public @interface NAV_DRAWER_ITEM {
    }

    private static final int[] houseTitles =
            {R.string.nav_stark, R.string.nav_lannister, R.string.nav_targaryen};

    private HousesActivityBinding mBinding;

    public static void start(Context context) {
        start(context, Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
    }

    public static void start(Context context, int flags) {
        Intent intent = new Intent(context, HousesActivity.class);
        intent.setFlags(intent.getFlags() | flags);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.houses_activity);

        setupViewPager(mBinding.viewpager);
        mBinding.tabs.setupWithViewPager(mBinding.viewpager);
        setHouseId(R.id.nav_stark);

        mBinding.navigationView.setNavigationItemSelectedListener(item -> {
                @NAV_DRAWER_ITEM int itemId = item.getItemId();

                mBinding.navigationView.setCheckedItem(itemId);
                return setHouseId(itemId);
            });
    }

    public boolean setHouseId(@NAV_DRAWER_ITEM int houseId) {
        switch (houseId) {
            case R.id.nav_stark: {
                mBinding.viewpager.setCurrentItem(0, false);
                return true;
            }
            case R.id.nav_lannister: {
                mBinding.viewpager.setCurrentItem(1, false);
                return true;
            }
            case R.id.nav_targaryen: {
                mBinding.viewpager.setCurrentItem(2, false);
                return true;
            }
        }
        return false;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter  =
                new ViewPagerAdapter(FragmentUtils.getFragmentManager(this));
        adapter.addFragment(new HouseFragment(HouseItem.STARK), getString(houseTitles[0]));
        adapter.addFragment(new HouseFragment(HouseItem.LANNISTER), getString(houseTitles[1]));
        adapter.addFragment(new HouseFragment(HouseItem.TARGARYEN), getString(houseTitles[2]));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}