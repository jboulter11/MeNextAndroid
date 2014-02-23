package com.jimboulter.menextandroid;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Jim Boulter on 2/15/14.
 */
public class UserCredentialsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        if(!sharedPref.getString(getString(R.string.username_setting),
                getString(R.string.default_setting)).equals(getString(R.string.default_setting)))
        {
            EditText usernameText = (EditText) findViewById(R.id.usernameText);
            usernameText.setText((CharSequence)
                    sharedPref.getString(getString(R.string.username_setting),
                            getString(R.string.default_setting)), TextView.BufferType.EDITABLE);
        }
        else
        {
            Toast.makeText(this, getString(R.string.ask_for_cred), Toast.LENGTH_LONG).show();
        }

        ActionBar actionBar = getActionBar();
        if (actionBar != null)
        {
            actionBar.hide();
        }

        setContentView(R.layout.user_credentials_activity);//display new view

        Button loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.username_setting),
                        getString(R.id.usernameText));
                editor.putString(getString(R.string.password_setting),
                        getString(R.id.passwordText));
                editor.commit();
                finish();
            }
        });
    }


}
