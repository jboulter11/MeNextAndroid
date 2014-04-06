package com.jimboulter.menextandroid;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    /**
     * placeholder ArrayList of groups and tracks
     */
    private ArrayList<String> groups
            = new ArrayList<String>(Arrays.asList("Group 1", "Group 2", "Group 3"));
    private ArrayList<String> tracks
            = new ArrayList<String>(Arrays.asList("http://www.youtube.com/watch?v=NK2FqPNIT_U",
            "http://www.youtube.com/watch?v=bbEoRnaOIbs",
            "http://www.youtube.com/watch?v=NK2FqPNIT_U"));

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

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, new FragmentQueueHolder(tracks))
                .commit();
    }

    public void onSectionAttached(int number) {
        mTitle = getString(R.string.title_section) + " " + Integer.toString(number);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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
}
