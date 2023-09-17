package com.example.realwearuuuhhhhhh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.pdfbox.pdmodel.interactive.annotation.layout.PlainText;

import java.util.ArrayList;
import java.util.List;

public class EmployeeList extends AppCompatActivity {

    private Button startButton;
    private Button backBut;
    private EditText name, number, company, initials;
    private String nameText, numberText, companyText, initialsText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emoloyeeselection);
        getSupportActionBar().hide();
        startButton = findViewById(R.id.startPDF);
        backBut = findViewById(R.id.backToTools);
        name = findViewById(R.id.NameEmployee);
        number = findViewById(R.id.InputEmNumber);
        initials = findViewById(R.id.inputInitials);
        company = findViewById(R.id.inputCompany);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        nameText = sharedPreferences.getString("Name", "");
        if (!nameText.isEmpty()) {
            name.setText(nameText);
        }
        numberText = sharedPreferences.getString("Number", "");
        if (!numberText.isEmpty()) {
            number.setText(numberText);
        }
        companyText = sharedPreferences.getString("Company", "");
        if (!companyText.isEmpty()) {
            company.setText(companyText);
        }
        initialsText = sharedPreferences.getString("Initials", "");
        if (!initialsText.isEmpty()) {
            initials.setText(initialsText);
        }


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputText = name.getText().toString();
                String inputNumber = number.getText().toString();
                String inputCompany = company.getText().toString();
                String inputInitials = initials.getText().toString();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(EmployeeList.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Name", inputText);
                editor.apply();
                editor.putString("Number",inputNumber);
                editor.apply();
                editor.putString("Company",inputCompany);
                editor.apply();
                editor.putString("Initials",inputInitials);
                editor.apply();
                if(inputText.isEmpty())
                {
                    Toast.makeText(EmployeeList.this, "Fill Name field", Toast.LENGTH_SHORT).show();

                } else if(inputCompany.isEmpty())
                {
                    Toast.makeText(EmployeeList.this, "Fill Company field", Toast.LENGTH_SHORT).show();

                }else if(inputNumber.isEmpty())
                {
                    Toast.makeText(EmployeeList.this, "Fill Employee number field", Toast.LENGTH_SHORT).show();

                }else if(inputInitials.isEmpty())
                {
                    Toast.makeText(EmployeeList.this, "Fill Initials field", Toast.LENGTH_SHORT).show();

                }else
                {
                    editor.putString("Remarks", "");
                    editor.apply();
                    Intent intent = new Intent(EmployeeList.this,CheckList.class);
                    startActivity(intent);
                }
            }
        });

        backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputText = name.getText().toString();
                String inputNumber = number.getText().toString();
                String inputCompany = company.getText().toString();
                String inputInitials = initials.getText().toString();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(EmployeeList.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Name", inputText);
                editor.apply();
                editor.putString("Number",inputNumber);
                editor.apply();
                editor.putString("Company",inputCompany);
                editor.apply();
                editor.putString("Initials",inputInitials);
                editor.apply();
                Intent intent = new Intent(EmployeeList.this,SelectTool.class);
                startActivity(intent);
            }
        });
    }

}

