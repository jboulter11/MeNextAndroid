package com.jimboulter.menextandroid;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by Jim Boulter on 4/6/14.
 */
public class FragmentQueueHolder extends Fragment{
    protected static ArrayList<QueueFragment> queueList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager manager = getFragmentManager();
        assert manager != null;
        FragmentTransaction transaction = manager.beginTransaction();

        for(QueueFragment frag : queueList)
        {
            transaction.add(R.id.queueView, frag);
        }
        transaction.commit();
    }

    public FragmentQueueHolder(ArrayList<String> tracks)
    {
        assert queueList != null;
        for(String track : tracks)
        {
            queueList.add(new QueueFragment(track));
        }
    }
}
