package com.example.realwearuuuhhhhhh;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileUpload extends AppCompatActivity {

    private Button sendButton;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload);
        getSupportActionBar().hide();
        context = getApplicationContext();
        sendButton = findViewById(R.id.sendFile);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code for uploading file
                File file = new File(context.getFilesDir(), "empty.txt");
                try (FileWriter writer = new FileWriter(file)) {
                    writer.append("This is a new file.");
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String fileName = file.getName();
                String folderPath = "/sites/try/Shared Documents";
                UploadTask uploadTask = new UploadTask(file, fileName, folderPath);
                uploadTask.execute();
//from here is reading

                /*ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.submit(new Runnable() {
                    @Override
                    public void run() {
                        SharepointUploader sharePointHelper = null;
                        try {
                            sharePointHelper = new SharepointUploader();
                            List<String> fileNames = sharePointHelper.listFiles("https://2y7v5x.sharepoint.com/sites/try/Shared Documents/trial");
                            for (String fileName1 : fileNames) {
                                System.out.println(fileName1);
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                executor.shutdown();*/


            }
        });




    }
}

