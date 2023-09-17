package com.example.realwearuuuhhhhhh;

import static org.apache.http.HttpHeaders.USER_AGENT;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.hardware.biometrics.BiometricPrompt;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.HyphenationAuto;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.HttpMultipartMode;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.fluent.Response;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.FileEntity;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.http.HttpHeaders;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Base64;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.*;

public class PDFDownload extends AppCompatActivity {
    private Button savePDF, cancel;
    private TextView titlePDF;
    private static final int STORAGE_CODE = 1000;
    private String fileName= "example";
    private LocalDate currentDate;
    private EmployeeList employeeList;
    private SelectTool selectTool;


    public PDFDownload()
    {
        selectTool = new SelectTool();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.downloadingpdf);
        getSupportActionBar().hide();
        savePDF = findViewById(R.id.downloadButton);
        cancel = findViewById(R.id.Cancel);
        titlePDF = findViewById(R.id.titlePDF);
        employeeList = new EmployeeList();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDate = LocalDate.now();
        }
        // file name
        fileName = new SimpleDateFormat("yyyy-MMdd-HHmmss", Locale.getDefault()).format(System.currentTimeMillis())+"-ZCH_Commision-tower_HKZ";
        titlePDF.setText(fileName);
        savePDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        //permission not granted
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, STORAGE_CODE);
                    } else {
                        //savePdf();
                       //modifyAndSavePdf();
                        //getAUthToken("https://login.microsoftonline.com/12f921d8-f30d-4596-a652-7045b338485a/oauth2/v2.0/token","9bb04fad-1749-4a80-a24b-81a06fe7da38","f618Q~aNIMUEJf3UkOniTInsuB3EoR6B4tap.ai9");
                        getAUthToken("https://login.microsoftonline.com/0fc53fb7-4268-40fe-9e0a-a89f0b8da86d/oauth2/token","586ecb43-a1d7-46b2-93cb-051b1c56c7df","nyH8Q~UQiZW7kCZtfrg8lLwISFadDkZbjVzSubkH");
                        Intent intent = new Intent(PDFDownload.this,Main.class);
                        startActivity(intent);
                    }
                } else {
                    //savePdf();
                    //modifyAndSavePdf();

                    //getAUthToken("https://login.microsoftonline.com/12f921d8-f30d-4596-a652-7045b338485a/oauth2/v2.0/token","9bb04fad-1749-4a80-a24b-81a06fe7da38","f618Q~aNIMUEJf3UkOniTInsuB3EoR6B4tap.ai9");
                    getAUthToken("https://login.microsoftonline.com/0fc53fb7-4268-40fe-9e0a-a89f0b8da86d/oauth2/token","586ecb43-a1d7-46b2-93cb-051b1c56c7df","nyH8Q~UQiZW7kCZtfrg8lLwISFadDkZbjVzSubkH");
                    Intent intent = new Intent(PDFDownload.this,Main.class);
                    startActivity(intent);
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PDFDownload.this,Main.class);
                startActivity(intent);
            }
        });
    }

    private void modifyAndSavePdf() {
        // file path
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + fileName + ".pdf";
        try {
            // create output stream to save the modified PDF
            OutputStream outputStream = new FileOutputStream(filePath);

            // get input stream for the PDF file in assets
            InputStream inputStream = getAssets().open("D2834645-ZCH_Commission-tower_HKZ.pdf");

            // create a PdfReader object from the input stream
            PdfReader pdfReader = new PdfReader(inputStream);

            // create a PdfStamper object to modify the PDF
            PdfStamper pdfStamper = new PdfStamper(pdfReader, outputStream);

            AcroFields formFields = pdfStamper.getAcroFields();
            formFields.setField("TECH_DATE1.0.0", String.valueOf(currentDate));
            formFields.setField("TECH_DATE1.0.1", String.valueOf(currentDate));
            formFields.setField("TECH_DATE1.0.2", String.valueOf(currentDate));
            formFields.setField("TECH_DATE1.0.3", String.valueOf(currentDate));
            formFields.setField("TECH_DATE1.1.0", String.valueOf(currentDate));
            formFields.setField("TECH_DATE1.1.1", String.valueOf(currentDate));
            formFields.setField("TECH_DATE1.1.3", String.valueOf(currentDate));
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String nameText = sharedPreferences.getString("Name", "");
            formFields.setField("TECH_Technicians_Name_1", nameText);
            String numberText = sharedPreferences.getString("Number", "");
            formFields.setField("TECH_Employee_No_1", numberText);
            String companyText = sharedPreferences.getString("Company", "");
            formFields.setField("TECH_Company_name_1", companyText);
            String initialsText = sharedPreferences.getString("Initials", "");
            formFields.setField("TECH_Initials_1", initialsText);
            String remarkText = sharedPreferences.getString("Remarks", "");
            formFields.setField("Remarks_1", remarkText);

            formFields.setField("TECH_CB1", "Yes");
            formFields.setField("TECH_CB2", "Yes");

            formFields.setField("TECH_Initial1.0.0", initialsText);
            formFields.setField("TECH_Initial1.0.1", initialsText);
            formFields.setField("TECH_Initial1.0.2", initialsText);
            formFields.setField("TECH_Initial1.0.3", initialsText);
            formFields.setField("TECH_Initial1.1.0", initialsText);
            formFields.setField("TECH_Initial1.1.1", initialsText);
            formFields.setField("TECH_Initial1.1.3", initialsText);

            String componentText = sharedPreferences.getString("Component", "");
            formFields.setField("ComponentNo", componentText);
            formFields.setField("TurbineNr", selectTool.getChosenTool());


            // close the PdfStamper and PdfReader objects
            pdfStamper.close();
            pdfReader.close();

            Toast.makeText(this, "PDF saved to downloads", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            System.out.println(e.getMessage());
        }

    }


    private void getAUthToken(final String url, final String clientId, final String clientSecret) {
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

            private String authToken;

            @Override
            protected String doInBackground(Void... voids) {
                //String authToken = null;
                try {
                    URL urlObj = new URL(url);
                    HttpsURLConnection httpsURLConnection = (HttpsURLConnection) urlObj.openConnection();
                    httpsURLConnection.setRequestMethod("POST");
                    httpsURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                    // Construct the POST data to obtain the access token
                    String postData = "grant_type=client_credentials" +
                            "&client_id=" + clientId +
                            "&client_secret=" + clientSecret +
                            "&scope=https://graph.microsoft.com/.default";

                    byte[] postDataBytes = postData.getBytes("UTF-8");
                    httpsURLConnection.setDoOutput(true);
                    httpsURLConnection.getOutputStream().write(postDataBytes);

                    InputStream inputStream = httpsURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = bufferedReader.readLine()) != null) {
                        response.append(inputLine);
                    }
                    bufferedReader.close();
                    inputStream.close();

                    // Extract the access token from the JSON response
                    JSONObject jsonObject = new JSONObject(response.toString());
                    authToken = jsonObject.getString("access_token");

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return authToken;
            }

            @Override
            protected void onPostExecute(String authToken) {
                // The authToken is available here, so you can use it to make API calls
                // For example, you can store it in a member variable to use later
                // Or you can pass it to another function that makes API calls
                // In your case, you can save it to the authToken variable that you declared in your class
                this.authToken = authToken;
                System.out.println(authToken);
                String siteUrl = "https://siemensgamesa.sharepoint.com/teams/IP007A1/Shared%20Documents";
                String libraryName = "Daniel testing RW checklist";
                String fileName = "example.pdf";
                byte[] fileData = modifyPdfAndGetByteArray();
                //uploadFile(authToken, siteUrl, libraryName, fileName, fileData);
                uploadFileToSharePoint2(fileData, fileName, siteUrl, libraryName, authToken);
                Intent intent = new Intent(PDFDownload.this,Main.class);
                startActivity(intent);
            }
        };

        task.execute();
    }


    private void uploadFile(String authToken, String siteUrl, String libraryName, String fileName, byte[] fileData) {
        try {
            URL url = new URL(siteUrl + "/_api/web/lists/getByTitle('" + libraryName + "')/RootFolder/Files/Add(url='" + fileName + "', overwrite=true)");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/octet-stream");
            conn.setRequestProperty("Authorization", "Bearer " + authToken);

            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(fileData);
            outputStream.flush();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
                // File uploaded successfully
                System.out.println("File uploaded successfully");
            } else {
                // Error occurred while uploading the file
                System.out.println("Error occurred while uploading the file: " + conn.getResponseMessage());
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void uploadFileToSharePoint(byte[] fileData, String fileName, String siteUrl, String libraryName, String accessToken) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    // Construct the endpoint URL
                    //String endpointUrl = siteUrl + "/_api/web/lists/getbytitle('" + libraryName + "')/rootfolder/files/add(url='" + fileName + "',overwrite=true)";

                    String endpointUrl = siteUrl + "/_api/web/GetFolderByServerRelativeUrl('/"+ libraryName + "')/Files/add(url='" + fileName + "',overwrite=true)";
                    //https://{site_url}/_api/web/GetFolderByServerRelativeUrl('/Folder Name')/Files/add(url='a.txt',overwrite=true)
                    //String endpointUrl = siteUrl + "/_api/web/lists/getbytitle('Shared Documents')/RootFolder/Files/Add(url='" + fileName + "',overwrite=true)";
                    //String endpointUrl = siteUrl + "/_api/web/lists/getbytitle('Shared Documents')/RootFolder/Folders('Documents')/Files/Add(url='" + fileName + "',overwrite=true)";
                    //String endpointUrl = siteUrl + "/_api/web/GetFolderByServerRelativeUrl('/teams/IP007A1/Shared%20Documents/General/Daniel%20testing%20RW%20checklist')/Files/Add(url='" + fileName + "', overwrite=true)";
                    //String siteUrl = "https://siemensgamesa.sharepoint.com";
                    //String listUrl = "/teams/IP007A1/Shared%20Documents/General/Daniel%20testing%20RW%20checklist";

                    //String endpointUrl = siteUrl + "/_api/web/GetFolderByServerRelativeUrl('/teams/IP007A1/Shared Documents/General/Daniel testing RW checklist')/Files/Add(url='" + fileName + "', overwrite=true)";
                    //String endpointUrl = "https://siemensgamesa.sharepoint.com/teams/IP007A1/Shared%20Documents/General/Daniel%20testing%20RW%20checklist/example.pdf";
                    //String endpointUrl = "https://siemensgamesa.sharepoint.com/_api/web/GetFolderByServerRelativeUrl('/teams/IP007A1/Shared%20Documents/General/Daniel%20testing%20RW%20checklist')/Files/Add(url='example.pdf',overwrite=true)";

                    // Create the HTTP connection
                    URL url = new URL(endpointUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Authorization", "Bearer " + accessToken);
                    conn.setRequestProperty("Content-Type", "application/octet-stream");

                    InputStream inputStream = conn.getInputStream();
                    // Read all bytes from the input stream
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                    int nRead;
                    byte[] data = new byte[16384];
                    while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                        buffer.write(data, 0, nRead);
                    }
                    buffer.flush();
                    byte[] fileData = buffer.toByteArray();
                    // Write the file data to the connection output stream
                    OutputStream outputStream = conn.getOutputStream();
                    outputStream.write(fileData);
                    outputStream.flush();
                    outputStream.close();

                    // Check the response code
                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_CREATED) {
                        // File uploaded successfully
                        System.out.println("File uploaded successfully to SharePoint library " + libraryName);
                    } else {
                        // Error occurred
                        System.out.println("Error uploading file to SharePoint library " + libraryName + ": " + conn.getResponseMessage());
                    }
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    private void uploadFileToSharePoint2(byte[] fileData, String fileName, String siteUrl, String libraryName, String accessToken) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    // Construct the endpoint URL
                    //String endpointUrl = siteUrl + "/_api/web/lists/getbytitle('" + libraryName + "')/rootfolder/files/add(url='" + fileName + "',overwrite=true)";
                    String endpointUrl = "https://siemensgamesa.sharepoint.com/_api/web/GetFolderByServerRelativeUrl('/teams/IP007A1/Shared%20Documents/General/Daniel%20testing%20RW%20checklist')/Files/add(url='" + fileName + "',overwrite=true)";
                    String endpointUrl2 = "https://2y7v5x.sharepoint.com/sites/try/_api/web/getfolderbyserverrelativeurl('/sites/try/Shared%20Documents/trial')/files/add(url='filename.extension',overwrite=true)";
                    //String endpointUrl = siteUrl + "/_api/web/GetFolderByServerRelativeUrl('/"+ libraryName + "')/Files/add(url='" + fileName + "',overwrite=true)";
                    // Create the HTTP connection
                    URL url = new URL(endpointUrl2);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Authorization", "Bearer " + accessToken);
                    conn.setRequestProperty("Content-Type", "application/octet-stream");

                    // Write the file data to the connection output stream
                    OutputStream outputStream = conn.getOutputStream();
                    outputStream.write(fileData);
                    outputStream.flush();
                    outputStream.close();

                    // Check the response code
                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_CREATED) {
                        // File uploaded successfully
                        System.out.println("File uploaded successfully to SharePoint library " + libraryName);
                    } else {
                        // Error occurred
                        System.out.println("Error uploading file to SharePoint library " + libraryName + ": " + conn.getResponseMessage());
                    }
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    private byte[] modifyPdfAndGetByteArray() {
        byte[] fileData = null;
        try {
            // get input stream for the PDF file in assets
            InputStream inputStream = getAssets().open("D2834645-ZCH_Commission-tower_HKZ.pdf");

            // create a PdfReader object from the input stream
            PdfReader pdfReader = new PdfReader(inputStream);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfStamper pdfStamper = new PdfStamper(pdfReader, outputStream);

            AcroFields formFields = pdfStamper.getAcroFields();
            formFields.setField("TECH_DATE1.0.0", String.valueOf(currentDate));
            formFields.setField("TECH_DATE1.0.1", String.valueOf(currentDate));
            formFields.setField("TECH_DATE1.0.2", String.valueOf(currentDate));
            formFields.setField("TECH_DATE1.0.3", String.valueOf(currentDate));
            formFields.setField("TECH_DATE1.1.0", String.valueOf(currentDate));
            formFields.setField("TECH_DATE1.1.1", String.valueOf(currentDate));
            formFields.setField("TECH_DATE1.1.3", String.valueOf(currentDate));
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String nameText = sharedPreferences.getString("Name", "");
            formFields.setField("TECH_Technicians_Name_1", nameText);
            String numberText = sharedPreferences.getString("Number", "");
            formFields.setField("TECH_Employee_No_1", numberText);
            String companyText = sharedPreferences.getString("Company", "");
            formFields.setField("TECH_Company_name_1", companyText);
            String initialsText = sharedPreferences.getString("Initials", "");
            formFields.setField("TECH_Initials_1", initialsText);
            String remarkText = sharedPreferences.getString("Remarks", "");
            formFields.setField("Remarks_1", remarkText);

            formFields.setField("TECH_CB1", "Yes");
            formFields.setField("TECH_CB2", "Yes");

            formFields.setField("TECH_Initial1.0.0", initialsText);
            formFields.setField("TECH_Initial1.0.1", initialsText);
            formFields.setField("TECH_Initial1.0.2", initialsText);
            formFields.setField("TECH_Initial1.0.3", initialsText);
            formFields.setField("TECH_Initial1.1.0", initialsText);
            formFields.setField("TECH_Initial1.1.1", initialsText);
            formFields.setField("TECH_Initial1.1.3", initialsText);

            String componentText = sharedPreferences.getString("Component", "");
            formFields.setField("ComponentNo", componentText);
            formFields.setField("TurbineNr", selectTool.getChosenTool());

            // close the PdfStamper and PdfReader objects
            pdfStamper.close();
            pdfReader.close();

            fileData = outputStream.toByteArray();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            System.out.println(e.getMessage());
        }

        return fileData;
    }


    //handle permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case STORAGE_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                    /*modifyAndSavePdf();
                    Intent intent = new Intent(PDFDownload.this,Main.class);
                    startActivity(intent);*/
                } else {
                    //denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }



}
