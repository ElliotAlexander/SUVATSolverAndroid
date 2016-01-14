package uk.co.elliotpurvis.suvatsolver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;

import uk.co.elliotpurvis.suvatsolver.equations.Equation1;
import uk.co.elliotpurvis.suvatsolver.equations.Equation2;
import uk.co.elliotpurvis.suvatsolver.equations.Equation3;
import uk.co.elliotpurvis.suvatsolver.equations.Equation4;
import uk.co.elliotpurvis.suvatsolver.equations.Equation5;
import uk.co.elliotpurvis.suvatsolver.exceptions.InsufficientValuesException;


public class MainActivity extends Activity {


    EditText velocity_input, initial_input, distance_input, time_input, acceleration_input;


    private ArrayList<Equation> registeredEquations;
    private HashMap<String, Double> values;
    private Button calculateButton;

    public static  String ACCELERATION, DISTANCE, INITIAL_VELOCITY, TIME, VELOCITY;



    @Override
    protected void onCreate(Bundle savedInstanceState) {



        ACCELERATION = getResources().getString(R.string.ACCELERATION);
        DISTANCE = getResources().getString(R.string.DISTANCE);
        TIME = getResources().getString(R.string.TIME);
        INITIAL_VELOCITY = getResources().getString(R.string.INITAL_VELOCITY);
        VELOCITY = getResources().getString(R.string.VELOCITY);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        velocity_input = (EditText)findViewById(R.id.velocity_input);
        initial_input = (EditText)findViewById(R.id.intial_velocity_input);
        distance_input = (EditText) findViewById(R.id.distance_input);
        time_input = (EditText) findViewById(R.id.time_input);
        acceleration_input = (EditText) findViewById(R.id.acceleration_input);


        calculateButton = (Button)findViewById(R.id.calculate_button);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, String> tempValues = new HashMap<String, String>();
                tempValues.put(VELOCITY, velocity_input.getText().toString());
                tempValues.put(DISTANCE, distance_input.getText().toString());
                tempValues.put(INITIAL_VELOCITY, initial_input.getText().toString());
                tempValues.put(TIME, time_input.getText().toString());
                tempValues.put(ACCELERATION, acceleration_input.getText().toString());

                HashMap<String, Double> finalValues = new HashMap<String, Double>();

                for (String key : tempValues.keySet()) {
                    try {
                        Double x = Double.parseDouble(tempValues.get(key));
                        finalValues.put(key, x);
                    } catch (NumberFormatException numberFormatException) {
                        continue;
                    }
                    setValue(key, finalValues.get(key));
                }
                try {
                    calculate();
                } catch(InsufficientValuesException e){
                    Context context = getApplicationContext();
                    CharSequence insufficientValuesText = "Insufficient values!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, insufficientValuesText, duration);
                    toast.show();
                }
            }
        });

        setup();
    }

    private void setup(){
        values = new HashMap<String, Double>() {{
            put(VELOCITY, null);
            put(ACCELERATION, null);
            put(DISTANCE, null);
            put(INITIAL_VELOCITY, null);
            put(TIME, null);
        }};


        registeredEquations = new ArrayList<Equation>() {{
            add(new Equation1());
            add(new Equation2());
            add(new Equation3());
            add(new Equation4());
            add(new Equation5());
        }};
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }


    // We use an interface to keep the equations together and easily iterate through multiple classes, as well as for readabillity.
    public interface Equation {
        public String[] getRequiredChars();

        public Double calculate(HashMap<String, Double> passedValues, String nullValue);
    }

    public void setValue(String s, Double x){
        values.put(s, x);
    }

    public Double getValue(String s){
        return values.get(s);
    }


    public void calculate() throws InsufficientValuesException {

        // Stores the keys to reference the null values in HashMap<Double> values;
        ArrayList<String> nullvalues = new ArrayList<String>();


        for (String tempKey : values.keySet()) {
            if (values.get(tempKey) == null) {
                nullvalues.add(tempKey);
            }
        }

        if (nullvalues.size() > 2) {
            // newErrorWindow("Too many values!", "You've entered too many values to calculate. Please enter a maximum of two unknown values.");
            throw new InsufficientValuesException();
        }


        nullValueLoop:
        for (String nullValueKey : nullvalues) {

            EquationLoop:
            for (Equation e : registeredEquations) {
                // Flag to keep ` of if we have any unknowns yet
                boolean foundUnknown = false;

                // Iterate through the values required
                // If the loop does not exit, the equation only requires one of our null values and the program continues.
                boolean containsUnknown = false;
                for (String s : e.getRequiredChars()) {

                    if (s == nullValueKey) {
                        containsUnknown = true;
                    }

                    // If we have a null value and havent yet had one, all is good
                    if (nullvalues.contains(s) && foundUnknown == false && values.get(s) == null) {
                        foundUnknown = true;

                        // We have more than one null value, abort
                    } else if (foundUnknown == true && nullvalues.contains(s) && values.get(s) == null) {
                        continue EquationLoop;
                    }
                }

                if (containsUnknown == false) {
                    continue EquationLoop;
                }


                Double returnValue = e.calculate(values, nullValueKey);
                if(returnValue.toString().length() > 5){
                    String tempShorteningString = returnValue.toString();
                    tempShorteningString = tempShorteningString.substring(0,5);
                    returnValue = Double.parseDouble(tempShorteningString);
                }



                setValue(nullValueKey, returnValue);
                break EquationLoop;
            }
            continue nullValueLoop;
        }
        updateTextBoxes();
    }

    private void updateTextBoxes(){
        velocity_input.setText(Double.toString(getValue(VELOCITY)));
        initial_input.setText(Double.toString(getValue(INITIAL_VELOCITY)));
        acceleration_input.setText(Double.toString(getValue(ACCELERATION)));
        time_input.setText(Double.toString(getValue(TIME)));
        distance_input.setText(Double.toString(getValue(DISTANCE)));
    }



}


// TODO - Replace "U" flags with Static final fields to identify types.