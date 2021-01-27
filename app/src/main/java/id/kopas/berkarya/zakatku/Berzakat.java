package id.kopas.berkarya.zakatku;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Berzakat extends AppCompatActivity {

    public static Adapter adapter;
    public static TabLayout tabLayout;
    public static ViewPager viewPager;

    TabLayout.Tab tab;
    public static int showPage = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_berzakat);


        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewPager);

        if (viewPager != null) {
            adapter = new Adapter(getSupportFragmentManager());
            //adapter.addFragment(getNameDay(0));

            viewPager.setAdapter(adapter);

            //adapter.notifyDataSetChanged();
        }
        tabLayout.setupWithViewPager(viewPager);
    }

    public static class Adapter extends FragmentPagerAdapter {
        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ZakatProfesiFragment().newInstance(0,"Zakat Profesi");
                case 1:
                    return new ZakatFitrahFragment().newInstance(0,"Zakat Fitrah");
                case 2:
                    return new ZakatFidyahFragment().newInstance(0,"Bayar Fidyah");
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Zakat Profesi";
                case 1:
                    return "Zakat Fitrah";
                case 2:
                    return "Bayar Fidyah";
                default:
                    return null;
            }
        }

        @Override
        public int getItemPosition(Object object) {
            // POSITION_NONE makes it possible to reload the PagerAdapter
            return POSITION_NONE;
        }
    }



}
