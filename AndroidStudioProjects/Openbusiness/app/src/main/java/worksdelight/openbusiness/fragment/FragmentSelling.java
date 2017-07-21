package worksdelight.openbusiness.fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import worksdelight.openbusiness.R;
import worksdelight.openbusiness.SampleFragmentPagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSelling#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSelling extends Fragment implements ViewPager.OnPageChangeListener {
    private int previousPage=0;
    PagerSlidingTabStrip tabsStrip;


    LinearLayout mTabsLinearLayout;
    private static final String KEY_MOVIE_TITLE = "key_title";

    public FragmentSelling() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment FragmentSelling.
     */
    public static FragmentSelling newInstance(String movieTitle) {
        FragmentSelling fragmentAction = new FragmentSelling();
        Bundle args = new Bundle();
        args.putString(KEY_MOVIE_TITLE, movieTitle);
        fragmentAction.setArguments(args);

        return fragmentAction;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_main2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager viewPager = (ViewPager)view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getChildFragmentManager()));

        tabsStrip = (PagerSlidingTabStrip)view.findViewById(R.id.tabs);

        //tabsStrip.setTextColorResource(R.drawable.selectore);

        tabsStrip.setViewPager(viewPager);
        tabsStrip.setOnPageChangeListener(this);
        ((LinearLayout)tabsStrip.getChildAt(0)).getChildAt(0).setSelected(true);
        setUpTabStrip(0);

        String movieTitle = getArguments().getString(KEY_MOVIE_TITLE);

    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ((LinearLayout)tabsStrip.getChildAt(0)).getChildAt(previousPage).setSelected(false);
        //set the selected page to state_selected = true
        ((LinearLayout)tabsStrip.getChildAt(0)).getChildAt(position).setSelected(true);
        //remember the current page
        previousPage=position;
        setUpTabStrip(position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    public void setUpTabStrip(int pos) {

        //your other customizations related to tab strip...blahblah
        // Set first tab selected
        mTabsLinearLayout = ((LinearLayout) tabsStrip.getChildAt(0));
        for (int i = 0; i < mTabsLinearLayout.getChildCount(); i++) {
            TextView tv = (TextView) mTabsLinearLayout.getChildAt(i);

            if (i == pos) {
                tv.setTextColor(Color.WHITE);
            } else {
                tv.setTextColor(getResources().getColor(R.color.list_item_title));
            }
        }
    }
}
