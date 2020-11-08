package com.example.fitness;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String n = "name";
    public static final String w = "Weight";
    public static final String h = "Height";
    private static final String FLAG ="FLAG" ;
    public static final String g = "Gender";
    public static final String bmi = "BMI";


    String RES;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    TextView textInfo , bmiResult;
    EditText Ed_Name, Ed_Weight, Ed_Height ;
    Button Save, BMI_cal,Timer;

    Spinner spinner;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timer = findViewById(R.id.goTimer);
        spinner = findViewById(R.id.spGender);
        populateSpinner();

        Ed_Name = findViewById(R.id.edName);
        Ed_Height = findViewById(R.id.edHeight);
        Ed_Weight = findViewById(R.id.edWeight);
        Save = findViewById(R.id.save);
        textInfo= findViewById(R.id.addInfo);
        bmiResult= findViewById(R.id.bmi);
        BMI_cal = findViewById(R.id.calBMI);
        setupSharedPrefs();
        checkPrefs();





        Timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimerActivity();
            }
        });

    }

    public void calculatedBMI(View view) {
        float w = Float.parseFloat(Ed_Weight.getText().toString());
        float h = Float.parseFloat(Ed_Height.getText().toString())/100;



        float BMI = w/((h*h));

        if(BMI<18.5){
            RES="under Weight";
        }
        else if (BMI < 25) {
            RES="Normal";
        }
        else if ( BMI < 30) {
            RES="Overweight";
        }
        else if (BMI > 30) {
            RES="Obese";
        }

        bmiResult.setText(BMI +"\n"+ RES);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private  void checkPrefs(){

        boolean check = prefs.getBoolean(FLAG , false);

        if(check){
            String name = prefs.getString(n,"");
            String Weight = prefs.getString(w,"");
            String Height = prefs.getString(h,"");
            String  Gender = prefs.getString(g,"");
            String bm1 = prefs.getString(bmi,"");


            Ed_Name.setText(name);
            Ed_Height.setText(Height);
            Ed_Weight.setText(Weight);
            bmiResult.setText(bm1);
            spinner.getTextAlignment();




        }
    }
    public void btnSaveData(View view){
        String name = Ed_Name.getText().toString();
        String weight = Ed_Weight.getText().toString();
        String height = Ed_Height.getText().toString();
        String gender = spinner.getSelectedItem().toString();
        String bm = bmiResult.getText().toString();





        if(Save.isClickable()==true){
            editor.putString(n,name);
            editor.putString(w,weight);
            editor.putString(h,height);
            editor.putString(g,gender);
            editor.putString(bmi,bm);
            editor.putBoolean(FLAG,true);
            editor.commit();


        }
        textInfo.setText("Name :" +name +"\n"+
                "Weight :" +weight +"\n" +
                "Height :" +height +"\n" +
                "Gender :" +gender +"\n" +
                "BMI :" +bm + "\n"
        );
    }





    private void setupSharedPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }

    private void populateSpinner() {


                ArrayList<String> gender = new ArrayList<>();

                gender.add("Male");
                gender.add("FeMale");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,gender);
        spinner.setAdapter(adapter);
            }






    public void openTimerActivity(){
        Intent intent = new Intent(this ,TimerActivity.class);
        startActivity(intent);
    }

}