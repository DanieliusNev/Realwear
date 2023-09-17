package com.example.realwearuuuhhhhhh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class CheckList3 extends AppCompatActivity {
    private SelectTool selectTool;
    private TextView tool;
    private CheckBox checkBox1;
    static boolean isChecked;
    private Button nextStep;
    private Button exit;
    public CheckList3()
    {
        selectTool = new SelectTool();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checklist3);
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
                    Intent intent = new Intent(CheckList3.this,CheckList4.class);
                    startActivity(intent);
                }else
                {
                    Toast.makeText(CheckList3.this, "Please finish the step", Toast.LENGTH_SHORT).show();
                }

            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CheckList3.this,CheckList2.class);
                startActivity(intent);
            }
        });
    }
}
