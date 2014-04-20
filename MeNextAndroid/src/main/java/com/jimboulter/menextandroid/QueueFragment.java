package com.jimboulter.menextandroid;

/**
 * Created by Jim Boulter on 3/23/14.
 * The object that will be
 */

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class QueueFragment extends Fragment {
    String track;
    String title;
    Uri img;
    String desc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: Youtube API junk to get track stuff (This probably needs fixing)
        DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
        String url = "https://www.googleapis.com/youtube/v3/videos?id="
                + track + "&key=AIzaSyDNCyav_GilHTF4ZMiMEo5XQypeGDy3W98" +
                "&fields=items(id,snippet(title,description,thumbnails(default)))" +
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
            title = jObject.getString("title");
            desc = jObject.getString("description");
            img = Uri.parse(jObject.getJSONObject("default").getString("url"));
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

    public static QueueFragment getInstanceOf(String trackId) {
        QueueFragment q = new QueueFragment();
        q.track = trackId;
        return q;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_queue_item, container, false);
        assert v != null;
        RelativeLayout queueItem = (RelativeLayout) v.findViewById(R.id.queueItemLayout);
        TextView titleView = (TextView) queueItem.findViewById(R.id.videoTitleTextView);
        titleView.setText(title);
        ImageView imgView = (ImageView) queueItem.findViewById(R.id.videoPreviewImageView);
        imgView.setImageURI(null);
        imgView.setImageURI(img);
        return v;
    }
}