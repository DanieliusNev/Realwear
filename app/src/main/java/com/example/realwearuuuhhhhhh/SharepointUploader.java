package com.example.realwearuuuhhhhhh;

import java.io.File;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.AuthenticationResult;
import com.microsoft.aad.adal4j.ClientCredential;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SharepointUploader {
    private String accessToken;
    private Retrofit retrofit;

    private static final String CLIENT_ID = "586ecb43-a1d7-46b2-93cb-051b1c56c7df";
    private static final String CLIENT_SECRET = "nyH8Q~UQiZW7kCZtfrg8lLwISFadDkZbjVzSubkH";


    public SharepointUploader() throws MalformedURLException, ExecutionException, InterruptedException {
        // Obtain an access token for SharePoint using AAD authentication
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        accessToken = doAuthenticate("https://login.microsoftonline.com/0fc53fb7-4268-40fe-9e0a-a89f0b8da86d","586ecb43-a1d7-46b2-93cb-051b1c56c7df");
        System.out.println(accessToken);
        // Create a Retrofit instance with the SharePoint API endpoint and authentication headers
        retrofit = new Retrofit.Builder()
                .baseUrl("https://2y7v5x.sharepoint.com/")
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new Interceptor() {
                            @Override
                            public okhttp3.Response intercept(Chain chain) throws IOException {
                                Request request = chain.request().newBuilder()
                                        .header("Authorization", "Bearer " + accessToken)
                                        .build();
                                return chain.proceed(request);
                            }
                        })
                        .build())
                .build();
        /*retrofit = new Retrofit.Builder()
                .baseUrl("https://2y7v5x.sharepoint.com/")
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();*/
    }

    public boolean uploadFile(File file, String fileName, String folderPath) {
        try {
            // Define the request body with the file data and metadata
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", fileName, requestBody);
            RequestBody fileNameBody = RequestBody.create(MediaType.parse("text/plain"), fileName);
            RequestBody folderPathBody = RequestBody.create(MediaType.parse("text/plain"), folderPath);

            // Use the Retrofit instance to make a POST request to the SharePoint API with the request body
            SharePointApiService service = retrofit.create(SharePointApiService.class);
            Call<ResponseBody> call = service.uploadFile(fileNameBody, folderPathBody, filePart);
            Response<ResponseBody> response = call.execute();
            return response.isSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public String doAuthenticate(String authorization, String resource) {
        AuthenticationContext context = null;
        AuthenticationResult result = null;
        String token = "";
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        try {
            context = new AuthenticationContext(authorization, false, executorService);
            final ClientCredential credential = new ClientCredential(CLIENT_ID, CLIENT_SECRET);

            final Future<AuthenticationResult> future = context.acquireToken(resource, credential, null);
            result = future.get(30, TimeUnit.SECONDS);
            token = result.getAccessToken();
        } catch (MalformedURLException | TimeoutException | InterruptedException | ExecutionException ex) {
            throw new IllegalStateException("Failed to do authentication.", ex);
        } finally {
            executorService.shutdown();
        }
        return token;
    }
    public List<String> listFiles(String folderPath) throws IOException {
        List<String> fileNames = new ArrayList<>();
        // Use the Retrofit instance to make a GET request to the SharePoint API to list files in the folder
        SharePointApiService service = retrofit.create(SharePointApiService.class);
        Call<String> call = service.listFiles(folderPath);
        String response = call.execute().body();
        if (response != null && !response.isEmpty()) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("value");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject fileJsonObject = jsonArray.getJSONObject(i);
                    String fileName = fileJsonObject.getString("Name");
                    fileNames.add(fileName);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return fileNames;
    }

}

