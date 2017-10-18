package com.company.downloadandplay.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by niraj.markandey on 18/10/17.
 */

public class DownloadService extends IntentService {
    public static final int UPDATE_PROGRESS = 8344;
    String TAG = getClass().getName();
    public DownloadService() {
        super("DownloadService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        String urlToDownload = intent.getStringExtra("url");
//        ResultReceiver receiver = (ResultReceiver) intent.getParcelableExtra("receiver");
        try {
            URL url = new URL(urlToDownload);
            URLConnection connection = url.openConnection();
            connection.connect();
            // this will be useful so that you can show a typical 0-100% progress bar
            int fileLength = connection.getContentLength();

            // download the file
            InputStream input = new BufferedInputStream(connection.getInputStream());
            String filepath = getBaseContext().getFilesDir()+"/audio.aac"; // Note : give appropriate extentions (mp3,aac,etc)
            // Note: I am storing file Internally if you want to store file externally DownloadManager is better option

            Log.d(TAG, "onHandleIntent: Filepath = "+filepath);
            OutputStream output = new FileOutputStream(filepath);

            byte data[] = new byte[1024];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                Bundle resultData = new Bundle();
                resultData.putInt("progress" ,(int) (total * 100 / fileLength));
//                receiver.send(UPDATE_PROGRESS, resultData);
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bundle resultData = new Bundle();
        resultData.putInt("progress" ,100);
        Log.d(TAG, "onHandleIntent: DONE download");
//        receiver.send(UPDATE_PROGRESS, resultData);
    }
}
