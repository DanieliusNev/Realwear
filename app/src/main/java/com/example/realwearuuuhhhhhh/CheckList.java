package com.example.realwearuuuhhhhhh;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.Font;


public class CheckList extends AppCompatActivity {
    private SelectTool selectTool;
    private TextView tool;
    private CheckBox checkBox1;
    static boolean isChecked;
    private Button nextStep;
    private Button exit;
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION= 1;
    public CheckList()
    {
        selectTool = new SelectTool();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_list_main);
        getSupportActionBar().hide();

        tool = findViewById(R.id.toolText);
        System.out.println(selectTool.getChosenTool());
        tool.setText(selectTool.getChosenTool());
        checkBox1 = findViewById(R.id.checkBox);
        exit = findViewById(R.id.backCheck);
        nextStep = findViewById(R.id.nextStep);
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isChecked = checkBox1.isChecked();
                if (isChecked) {
                    Intent intent = new Intent(CheckList.this, CheckList2.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(CheckList.this, "Please finish the step", Toast.LENGTH_SHORT).show();
                }

            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(CheckList.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Remarks", "");
                editor.apply();
                Intent intent = new Intent(CheckList.this, SelectTool.class);
                startActivity(intent);
            }
        });

       /* if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
        } else {
        }
*//*ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        // permission was granted, do your pdf-related stuff here
                        Document document = new Document();

                        document.open();
                        document.add(new Paragraph("Hello, PDF!"));
                        document.close();
                        File pdfFile = new File(Environment.getExternalStorageDirectory(), "example.pdf");
                        PdfWriter.getInstance(document, new FileOutputStream(pdfFile));

                        System.out.println("PDF created");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    // permission denied, show an appropriate message to the user
                    Toast.makeText(this, "You need to grant the storage permission to create the PDF file", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;

        }
    }*/
       /* createandDisplayPdf("wow");
    }
    public void createandDisplayPdf(String text) {
        Document doc = new Document();

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Dir";
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(dir, "newFile.pdf");
            FileOutputStream fOut = new FileOutputStream(file);
            PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();

            Paragraph p1 = new Paragraph(text);
            Font paraFont = new Font();
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            p1.setFont(paraFont);

            //add paragraph to document
            doc.add(p1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //close the document
            doc.close();
        }
    }
    // Method for opening a pdf file
    private void viewPdf(String file, String directory) {

        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + directory + "/" + file);
        Uri path = Uri.fromFile(pdfFile);

        // Setting the intent for pdf reader
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(CheckList.this, "Can't read pdf file", Toast.LENGTH_SHORT).show();
        }*/
    }
}
