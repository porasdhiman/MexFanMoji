package worksdelight.openbusiness;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import worksdelight.openbusiness.adapter.CustomExpandableListAdapter;
import worksdelight.openbusiness.datasource.ExpandableListDataSource;
import worksdelight.openbusiness.fragment.navigation.FragmentNavigationManager;
import worksdelight.openbusiness.fragment.navigation.NavigationManager;


public class MainActivity extends FragmentActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private String[] items;

    private ExpandableListView mExpandableListView;
    private ExpandableListAdapter mExpandableListAdapter;
    private List<String> mExpandableListTitle;
    private NavigationManager mNavigationManager;

    private Map<String, List<String>> mExpandableListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        mExpandableListView = (ExpandableListView) findViewById(R.id.navList);
        mNavigationManager = FragmentNavigationManager.obtain(MainActivity.this);

        initItems();

        LayoutInflater inflater = getLayoutInflater();
        View listHeaderView = inflater.inflate(R.layout.nav_header, null, false);
        mExpandableListView.addHeaderView(listHeaderView);

        mExpandableListData = ExpandableListDataSource.getData(this);
        mExpandableListTitle = new ArrayList(mExpandableListData.keySet());

        addDrawerItems();
        setupDrawer();

        if (savedInstanceState == null) {
            selectFirstItemAsDefault();
        }

/*    getSupportActionBar   getSupportActionBar getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);*/
    }

    private void selectFirstItemAsDefault() {
        if (mNavigationManager != null) {
            String firstActionSelling = getResources().getStringArray(R.array.actionSelling)[0];
            mNavigationManager.showFragmentSelleing(firstActionSelling);
            //getSupportActionBar().setTitle(firstActionMovie);
        }
    }

    private void initItems() {
        items = getResources().getStringArray(R.array.type);
    }

    private void addDrawerItems() {
        mExpandableListAdapter = new CustomExpandableListAdapter(this, mExpandableListTitle, mExpandableListData);
        mExpandableListView.setAdapter(mExpandableListAdapter);
        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //getSupportActionBar().setTitle(mExpandableListTitle.get(groupPosition).toString());
            }
        });

        mExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                //getSupportActionBar().setTitle(R.string.film_genres);
            }
        });

        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                String selectedItem = ((List) (mExpandableListData.get(mExpandableListTitle.get(groupPosition))))
                    .get(childPosition).toString();
                //getSupportActionBar().setTitle(selectedItem);

                if (items[0].equals(mExpandableListTitle.get(groupPosition))) {
                    mNavigationManager.showFragmentSelleing(selectedItem);
                } else if (items[1].equals(mExpandableListTitle.get(groupPosition))) {
                    mNavigationManager.showFragmentRelationShip(selectedItem);
                } else if (items[2].equals(mExpandableListTitle.get(groupPosition))) {
                    mNavigationManager.showFragmentSetting(selectedItem);
                } else if (items[3].equals(mExpandableListTitle.get(groupPosition))) {
                    mNavigationManager.showFragmentReport(selectedItem);
                } else if (items[4].equals(mExpandableListTitle.get(groupPosition))) {
                    mNavigationManager.showFragmentHelp(selectedItem);
                }else if (items[5].equals(mExpandableListTitle.get(groupPosition))) {
                    mNavigationManager.showFragmentLegal(selectedItem);
                }
                else {
                    throw new IllegalArgumentException("Not supported fragment type");
                }

                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getSupportActionBar().setTitle(R.string.film_genres);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
