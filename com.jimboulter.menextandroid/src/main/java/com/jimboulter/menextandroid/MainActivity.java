package com.jimboulter.menextandroid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends Activity {

    /**
     * placeholder ArrayList of groups and tracks
     */
    private ArrayList<String> groups
            = new ArrayList<String>(Arrays.asList("Group 1", "Group 2", "Group 3",
            "Group 4", "Group 5", "Group 6",
            "Group 7", "Group 8", "Group 9"));
    private ArrayList<String> tracks
            = new ArrayList<String>(Arrays.asList("NK2FqPNIT_U",
            "bbEoRnaOIbs",
            "NK2FqPNIT_U"));

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //If it's the first time the App has been opened, we need to get user credentials
        //TODO: change this over to a login script with the MySQL login database
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        if(!sharedPref.getBoolean(getString(R.string.first_time_setting), false))
        {
            //this is the users first time in the app
            Intent getCredIntent = new Intent(MainActivity.this, UserCredentialsActivity.class);
            startActivity(getCredIntent);

            //write that the user has entered the app before and we obtained their credentials
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(getString(R.string.first_time_setting), true);
            editor.commit();
        }

        //Get the list of groups for that user(These will be displayed in the nav drawer
        //for easy switching
        //TODO: fetch the user's groups

        //allows sharing from youtube app
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String url = extras.getString(Intent.EXTRA_TEXT); //get url from youtube app
            Intent addUrlIntent = new Intent(MainActivity.this, AddUrlActivity.class); //my intent
            addUrlIntent.putExtra(getString(R.string.url), url); //add url into my intent
            addUrlIntent.putStringArrayListExtra(getString(R.string.groups), groups); //add groups
            startActivity(addUrlIntent); //execute my intent, passing url and group list
        }

        setContentView(R.layout.activity_main);



        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(1, true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_settings)
        {
            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class); //my intent
            startActivity(settingsIntent); //go to settings activity
        }

        // If they selected one of our items,
        // or they selected something super knows about, return true.
        return (id == R.id.action_settings) || super.onOptionsItemSelected(item);
    }

    public void addToQueueClick(MenuItem item) {
        Intent addUrlIntent = new Intent(MainActivity.this, AddUrlActivity.class); //my intent
        addUrlIntent.putStringArrayListExtra(getString(R.string.groups), groups); //add groups
        startActivity(addUrlIntent); //execute my intent, passing url and group list
    }

    

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //TODO: This needs an if-statement to get the page
            if(position>1){return PlaceholderFragment.newInstance(position + 1);}
            else if(position==1){return QueueListFragment.newInstance(tracks);}
            else{return GroupListFragment.newInstance(groups);}
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

}
