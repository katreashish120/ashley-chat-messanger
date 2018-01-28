package com.vr.ashley.utils;


import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.StatusLine;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.impl.client.cache.CacheConfig;

/**
 * Created by katre on 21-May-17.
 */

public class RestServiceHelper extends AsyncTask<String, Void, String> {

    private static final String url = "http://lowcost-env.yjtnympx46.ap-southeast-1.elasticbeanstalk.com/webapi/myresource/";
    private static final int HTTP_STATUS_OK = 200;
    private static final String errorMessage = "Could not connect to ashley, Please try again later";
    private static byte[] buff = new byte[1024];
    public static final String LOG = "RestServiceHelper";

    public String doInBackground(String... req) {

        Log.d(LOG, "*** In RestServiceHelper");

        StringBuffer response = new StringBuffer();

        try {

            HttpClient httpClient = new DefaultHttpClient();
            HttpGet getRequest = new HttpGet(url + req[0]);

            HttpResponse httpResponse = httpClient.execute(getRequest);
            StatusLine statusLine = httpResponse.getStatusLine();

            if (statusLine.getStatusCode() != HTTP_STATUS_OK) {

                response.append(errorMessage);

            } else {

                HttpEntity httpEntity = httpResponse.getEntity();
                InputStream inputStream = httpEntity.getContent();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                int readCount = 0;

                while ((readCount = inputStream.read(buff)) != -1) {

                    outputStream.write(buff, 0, readCount);
                }

                response.append(outputStream.toByteArray());
            }

            Log.d(LOG, "*** Done");

        } catch (Exception e) {

            response.append(errorMessage);

            e.printStackTrace();

            Log.d(LOG, "*** In Error");
        }

        return response.toString();
    }
}
