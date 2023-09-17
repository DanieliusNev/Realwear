package com.example.realwearuuuhhhhhh;

import android.os.AsyncTask;

import java.io.File;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

public class UploadTask extends AsyncTask<Void, Void, Boolean> {

    private final File file;
    private final String fileName;
    private final String folderPath;
    private SharepointUploader sharepointUploader;

    public UploadTask(File file, String fileName, String folderPath) {
        this.file = file;
        this.fileName = fileName;
        this.folderPath = folderPath;
        try {
            this.sharepointUploader = new SharepointUploader();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        boolean result = sharepointUploader.uploadFile(file, fileName, folderPath);
        return result;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            System.out.println("File uploaded successfully.");
        } else {
            System.out.println("File upload failed.");
        }
    }
}
