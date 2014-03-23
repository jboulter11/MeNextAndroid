package com.jimboulter.menextandroid;

/**
 * Created by Janet on 3/23/14.
 */

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class QueueFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static QueueFragment newInstance(ArrayList<String> tracks) {
        QueueFragment fragment = new QueueFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_SECTION_NUMBER, tracks);
        fragment.setArguments(args);
        return fragment;
    }

    public QueueFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        assert rootView != null;
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        assert getArguments() != null;
        textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        assert getArguments() != null;
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}