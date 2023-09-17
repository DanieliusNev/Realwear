package com.example.realwearuuuhhhhhh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class CheckList4 extends AppCompatActivity {
    private SelectTool selectTool;
    private TextView tool;
    private CheckBox checkBox1;
    static boolean isChecked;
    private Button nextStep;
    private Button exit;
    public CheckList4()
    {
        selectTool = new SelectTool();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checklist4);
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
                if(isChecked)
                {
                    Intent intent = new Intent(CheckList4.this,CheckList5.class);
                    startActivity(intent);
                }else
                {
                    Toast.makeText(CheckList4.this, "Please finish the step", Toast.LENGTH_SHORT).show();
                }

            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CheckList4.this,CheckList3.class);
                startActivity(intent);
            }
        });
    }
}
