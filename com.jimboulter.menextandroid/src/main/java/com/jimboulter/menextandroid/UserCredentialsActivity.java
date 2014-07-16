package com.jimboulter.menextandroid;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jim Boulter on 2/15/14.
 */
public class UserCredentialsActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        final EditText usernameText = (EditText) findViewById(R.id.usernameText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final HttpClient client = new DefaultHttpClient();
        final HttpPost post = new HttpPost(getString(R.string.server));

        if(!sharedPref.getString(getString(R.string.username_setting),getString(R.string.default_setting)).equals(getString(R.string.default_setting)))
        {
            usernameText.setText((CharSequence)sharedPref.getString(getString(R.string.username_setting),getString(R.string.default_setting)), TextView.BufferType.EDITABLE);
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

        //TODO: add alert view to help stupid people when they punch in special character

        setContentView(R.layout.user_credentials_activity);//display new view

        Button loginButton = (Button)findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putString(getString(R.string.username_setting),usernameText.getText().toString());
                editor.commit();
                List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                pairs.add(new BasicNameValuePair(getString(R.string.action), ));
                pairs.add(new BasicNameValuePair(getString(R.string.username_setting), "value2"));
                pairs.add(new BasicNameValuePair(getString(R.string.password_setting), "value2"));
                try
                {
                    post.setEntity(new UrlEncodedFormEntity(pairs));
                }
                catch (UnsupportedEncodingException e)
                {
                //TODO Handle problems..
                }
                try
                {
                    HttpResponse response = client.execute(post);
                }
                catch (IOException e)
                {
                //TODO Handle problems..
                }


                finish();
            }
        });
    }


    public void registerMeClick(View view)
    {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.addCategory(Intent.CATEGORY_BROWSABLE);
        i.setData(Uri.parse(getString(R.string.server)));
        startActivity(i);
    }
}
