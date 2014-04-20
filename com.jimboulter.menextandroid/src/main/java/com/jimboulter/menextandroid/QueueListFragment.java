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
 * This is a fragment to use a listview to display the tracks in the queue using my fragment.
 */
public class QueueListFragment extends Fragment{

    protected Context c;
    ArrayList<String> tracks;

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
                R.layout.fragment_queue_item, R.id.videoTitleTextView, tracks);

        View v = inflater.inflate(R.layout.fragment_list_view, container, false);
        assert v != null;
        ListView lv = (ListView) v.findViewById(R.id.list_view);
        lv.setAdapter(arrayAdapter);
        return v;
    }

    public static QueueListFragment newInstance(ArrayList<String> t)
    {
        QueueListFragment qlf = new QueueListFragment();
        qlf.tracks = t;
        return qlf;
    }


}
