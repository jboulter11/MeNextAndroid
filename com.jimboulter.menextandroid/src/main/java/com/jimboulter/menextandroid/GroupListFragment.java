package com.jimboulter.menextandroid;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Jim Boulter on 4/19/14.
 *
 * This is for the left side, to be a simple list of groups.
 * Touching one will change panel 2.
 */
public class GroupListFragment extends Fragment{
    protected Context c;
    ArrayList<String> groups;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        c = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(c,
                android.R.layout.simple_selectable_list_item, groups);

        View v = inflater.inflate(R.layout.fragment_list_view, container, false);
        assert v != null;
        ListView lv = (ListView) v.findViewById(R.id.list_view);
        lv.setAdapter(arrayAdapter);
        return v;
    }

    public static GroupListFragment newInstance(ArrayList<String> g)
    {
        GroupListFragment glf = new GroupListFragment();
        glf.groups = g;
        return glf;
    }

}
