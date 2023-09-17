package com.example.realwearuuuhhhhhh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;


public class CheckList7 extends AppCompatActivity {
    private SelectTool selectTool;
    private TextView tool;
    private CheckBox checkBox1;
    static boolean isChecked;
    private Button nextStep;
    private Button exit;
    private EditText remarks;
    static ArrayList<String> listOfCheck2;
    public CheckList7()
    {
        selectTool = new SelectTool();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checklist7);
        getSupportActionBar().hide();

        tool = findViewById(R.id.toolText);
        System.out.println(selectTool.getChosenTool());
        tool.setText(selectTool.getChosenTool());
        checkBox1 = findViewById(R.id.checkBox);
        exit = findViewById(R.id.backCheck);
        nextStep = findViewById(R.id.nextStep);
        remarks = findViewById(R.id.remarks);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String remarkText = sharedPreferences.getString("Remarks", "");
        if (!remarkText.isEmpty()) {
            remarks.setText(remarkText);
        }
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isChecked = checkBox1.isChecked();
                if(isChecked)
                {
                    SharedPreferences sharedPreferences = getSharedPreferences("CheckList", MODE_PRIVATE);
                    Map<String, ?> allEntries = sharedPreferences.getAll();
                    if (allEntries.size() == 0) {
                        listOfCheck2 = new ArrayList<>();
                    }
                    else {
                        String json = sharedPreferences.getString("list", null);

                        // Convert the JSON string back to an ArrayList
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<String>>() {
                        }.getType();
                        listOfCheck2 = gson.fromJson(json, type);
                    }
                    listOfCheck2.add(selectTool.getChosenTool());
                    Date currentDate = new Date();
                    listOfCheck2.add(currentDate.toString());
                    Gson gson = new Gson();
                    String json = gson.toJson(listOfCheck2);

// Save the list to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("list", json);
                    editor.apply();

                    String inputText = remarks.getText().toString();
                    SharedPreferences sharedPreferences2 = PreferenceManager.getDefaultSharedPreferences(CheckList7.this);
                    SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                    editor2.putString("Remarks", inputText);
                    editor2.apply();
                    Intent intent = new Intent(CheckList7.this,PDFDownload.class);
                    startActivity(intent);
                }else
                {

                    Toast.makeText(CheckList7.this, "Please finish the step", Toast.LENGTH_SHORT).show();
                }

            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputText = remarks.getText().toString();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(CheckList7.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Remarks", inputText);
                editor.apply();
                Intent intent = new Intent(CheckList7.this,CheckList6.class);
                startActivity(intent);
            }
        });
    }
}
