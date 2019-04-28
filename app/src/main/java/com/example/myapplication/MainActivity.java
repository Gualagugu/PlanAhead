package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button submit;
    private Spinner select;
    private Spinner index;
    private Button choose;
    double averageGPA;
    double percentageA;
    private List<ClassInfo> ClassInfoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readClassInfo();
        addItemsOnSpinner();
        addListenerOnButton2();
        addItemsOnSpinner2();
        addListenerOnButton();
    }
    private void readClassInfo() {
        InputStream is = getResources().openRawResource(R.raw.sp2018);
        BufferedReader read = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        String line;
        try {
            while ((line = read.readLine()) != null) {
                String[] tokens = line.split(",");
                ClassInfo theInfo = new ClassInfo();
                theInfo.setSubject(tokens[0]);
                theInfo.setIndex(Integer.parseInt(tokens[1]));
                theInfo.setAverageGPA(Double.parseDouble(tokens[2]));
                theInfo.setInstructor(tokens[3]);
                theInfo.setPeople(Integer.parseInt(tokens[5]));
                theInfo.setPercentageA(Double.parseDouble(tokens[6]));
                ClassInfoList.add(theInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addItemsOnSpinner() {
        select = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < ClassInfoList.size(); i++) {
            if (ClassInfoList.get(i).getSubject() == null) {
                continue;
            }
            if (i >= 1) {
                if (ClassInfoList.get(i).getSubject().equals(ClassInfoList.get(i - 1).getSubject())) {
                    continue;
                }
            }
            list.add(ClassInfoList.get(i).getSubject());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select.setAdapter(dataAdapter);
    }
    public void addListenerOnButton2() {
        choose = (Button) findViewById(R.id.choose);

        choose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addItemsOnSpinner2();
            }
        });
    }
    public void addItemsOnSpinner2() {
        index = (Spinner) findViewById(R.id.spinner2);

        List<Integer> list2 = new ArrayList<Integer>();
        for(int i = 0; i < ClassInfoList.size(); i++) {
            if (String.valueOf(select.getSelectedItem()).equals(ClassInfoList.get(i).getSubject())) {
                if (i >= 1) {
                    if(ClassInfoList.get(i).getIndex() == ClassInfoList.get(i - 1).getIndex()) {
                        continue;
                    }
                }
                list2.add(ClassInfoList.get(i).getIndex());
            }
        }
        ArrayAdapter<Integer> dataAdapter2 = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_item, list2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        index.setAdapter(dataAdapter2);
    }
    public void addListenerOnButton() {
        select = (Spinner) findViewById(R.id.spinner);
        submit = (Button) findViewById(R.id.submit);
        index = (Spinner) findViewById(R.id.spinner2);

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                List<Double> averageGPAList = new ArrayList<>();
                List<Double> percentageAList = new ArrayList<>();
                List<Integer> peopleList = new ArrayList<>();
                for(int i = 0; i < ClassInfoList.size(); i++) {
                    if (String.valueOf(select.getSelectedItem()).equals(ClassInfoList.get(i).getSubject())
                        && index.getSelectedItem().equals(ClassInfoList.get(i).getIndex())) {
                        averageGPAList.add(ClassInfoList.get(i).getAverageGPA());
                        percentageAList.add(ClassInfoList.get(i).getPercentageA());
                        peopleList.add(ClassInfoList.get(i).getPeople());
                    }
                }
                int totalPeople = 0;
                for (int i = 0; i < peopleList.size(); i++) {
                    totalPeople = totalPeople + peopleList.get(i);
                }
                double totalGPA = 0;
                for (int i = 0; i < averageGPAList.size(); i++) {
                    totalGPA = totalGPA + peopleList.get(i) * averageGPAList.get(i);
                }
                double totalA= 0;
                for (int i = 0; i < percentageAList.size();i++) {
                    totalA = totalA + peopleList.get(i) * percentageAList.get(i);
                }
                averageGPA = totalGPA/totalPeople;
                percentageA = totalA/totalPeople;
                Toast.makeText(MainActivity.this,
                        "The average GPA for this course is: " + averageGPA + "  "
                                + "The percentage of student geting A in the course is " + percentageA,
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
