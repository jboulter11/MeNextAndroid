package com.jimboulter.menextandroid;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

/**
 * Created by Jim Boulter on 2/22/14.
 */
public class AddUrlActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getActionBar();
        if (actionBar != null)
        {
            actionBar.hide();
        }

        setContentView(R.layout.add_url_activity);

        EditText urlText = (EditText) findViewById(R.id.urlText);
        Spinner groupSpinner = (Spinner) findViewById(R.id.groupSpinner);

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            //set URL if it was shared
            urlText.setText(extras.getString(getString(R.string.url)));
            //set spinner to have list of groups
            ArrayAdapter<String> groupsAdapter =
                    new ArrayAdapter<String>(AddUrlActivity.this,
                            android.R.layout.simple_spinner_item,
                            extras.getStringArrayList(getString(R.string.groups)));
            groupSpinner.setAdapter(groupsAdapter);
        }
        else
        {
            //finish and report error to user
            finish();
            Toast.makeText(AddUrlActivity.this,
                    getString(R.string.no_groups_error), Toast.LENGTH_LONG).show();
        }

        Button okButton = (Button) findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: implement share to group
                finish();
            }
        });
    }
}
