package com.example.realwearuuuhhhhhh;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.realwearuuuhhhhhh.R;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.HyphenationAuto;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PDF extends AppCompatActivity {
    private Button savePDF;
    private static final int STORAGE_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf);
        savePDF = findViewById(R.id.savePDF);
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
                        modifyAndSavePdf();
                    }
                } else {
                    //savePdf();
                    modifyAndSavePdf();
                }
            }
        });
    }

   /* private void savePdf() {
        Document mDoc = new Document();
        //file name
        String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        //file path
        //String filePath = Environment.getExternalStorageDirectory() + "/" + fileName + ".pdf";
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + fileName + ".pdf";
        try {
            PdfWriter.getInstance(mDoc, new FileOutputStream(filePath));
            mDoc.open();
            mDoc.add(new Paragraph("WOW"));
            mDoc.close();

            Toast.makeText(this, fileName + ".pdf\nis saved to\n" + filePath, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            System.out.println(e.getMessage());
        }
    }*/
    private void modifyAndSavePdf() {
        // file name
        String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
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
            //AcroFields formFields = pdfStamper.getAcroFields();
//formFields.setField()
            /*// Get a map of all the form fields and their values
            Map<String, AcroFields.Item> fields = formFields.getFields();
            for (Map.Entry<String, AcroFields.Item> entry : fields.entrySet()) {
                String fieldName = entry.getKey();
                AcroFields.Item fieldItem = entry.getValue();
                int fieldType = formFields.getFieldType(fieldName);
                String fieldValue = formFields.getField(fieldName);

                // Fill in the form field based on its type
                if (fieldType == AcroFields.FIELD_TYPE_TEXT) {
                    formFields.setField(fieldName, "John");
                } else if (fieldType == AcroFields.FIELD_TYPE_CHECKBOX) {
                    formFields.setField(fieldName, "Yes");
                } else if (fieldType == AcroFields.FIELD_TYPE_COMBO) {
                    formFields.setField(fieldName, "Option 1");
                }
            }*/

            AcroFields formFields2 = pdfReader.getAcroFields();

// Get a map of all the form fields and their values
            Map<String, AcroFields.Item> fields = formFields2.getFields();
            for (Map.Entry<String, AcroFields.Item> entry : fields.entrySet()) {
                String fieldName = entry.getKey();
                String fieldValue = formFields2.getField(fieldName);

                System.out.println("Field name: " + fieldName);
                System.out.println("Field value: " + fieldValue);
            }


            // close the PdfStamper and PdfReader objects
            pdfStamper.close();
            pdfReader.close();

            Toast.makeText(this, fileName + ".pdf\nis saved to\n" + filePath, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            System.out.println(e.getMessage());
        }

    }

    //handle permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case STORAGE_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                    modifyAndSavePdf();
                } else {
                    //denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}
