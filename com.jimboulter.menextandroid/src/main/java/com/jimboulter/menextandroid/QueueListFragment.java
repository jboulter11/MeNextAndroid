package com.jimboulter.menextandroid;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Jim Boulter on 4/19/14.
 *
 * This is a fragment to use a listview to display the tracks in the queue using my fragment.
 */
public class QueueListFragment extends Fragment{

    protected Context c;
    ArrayList<String> tracks;
    ArrayList<String> titles;
    ArrayList<Uri> pics;

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

        if(titles == null || pics == null)
        {

            for(String track : tracks)
            {
                //TODO: Youtube API junk to get track stuff (This probably needs fixing)
                DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
                String url = "https://www.googleapis.com/youtube/v3/videos?id="
                        + track + "&key=AIzaSyCfOVXmyDks2RoqmT-L54Sox1PoN-GrHsQ" +
                        "&fields=items(id,snippet(title,thumbnails(default)))" +
                        "&part=snippet";

                HttpGet httpGet = new HttpGet(url);

                InputStream inputStream = null;
                String result;
                try
                {
                    HttpResponse response = httpClient.execute(httpGet);
                    HttpEntity entity = response.getEntity();

                    inputStream = entity.getContent();
                    BufferedReader reader
                            = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line;
                    while((line = reader.readLine() + "\n") != null)
                    {
                        sb.append(line);
                    }
                    result = sb.toString();

                    JSONObject jObject = new JSONObject(result);
                    titles.add(jObject.getString("title"));
                    pics.add(Uri.parse(jObject.getJSONObject("default").getString("url")));
                }
                catch(Exception e)
                {
                    //something went wrong with this track
                }
                finally
                {
                    try
                    {
                        if(inputStream != null)
                        {
                            inputStream.close();
                        }
                    }
                    catch(Exception squish){/*catch something*/}
                }
            }
        }

        ArrayAdapter<String> titleAdapter = new ArrayAdapter<String>(c,
                R.layout.fragment_queue_item, R.id.videoTitleTextView, titles);
        ArrayAdapter<Uri> imgAdapter = new ArrayAdapter<Uri>(c,
                R.layout.fragment_queue_item, R.id.videoPreviewImageView, pics);

        View v = inflater.inflate(R.layout.fragment_list_view, container, false);
        assert v != null;
        ListView lv = (ListView) v.findViewById(R.id.list_view);
        if(titleAdapter != null){lv.setAdapter(titleAdapter);}
        if(imgAdapter != null){lv.setAdapter(imgAdapter);}
        return v;
    }

    public static QueueListFragment newInstance(ArrayList<String> t)
    {
        QueueListFragment qlf = new QueueListFragment();
        qlf.tracks = t;
        return qlf;
    }


}
