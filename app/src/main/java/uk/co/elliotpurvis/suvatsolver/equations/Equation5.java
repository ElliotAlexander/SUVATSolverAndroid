package uk.co.elliotpurvis.suvatsolver.equations;

import java.util.HashMap;

import uk.co.elliotpurvis.suvatsolver.MainActivity;

/**
 * Created by Elliot on 04/09/2015.
 */
public class Equation5 implements MainActivity.Equation {

    private final String U,V,A,S,T;

    public Equation5(){
        U = MainActivity.INITIAL_VELOCITY;
        V = MainActivity.VELOCITY;
        S = MainActivity.DISTANCE;
        T = MainActivity.TIME;
        A = MainActivity.ACCELERATION;

    }

    /**
     *  S = vt - 1/2at^2
     **/
    public String[] getRequiredChars() {
        return new String[]{V, T, S, A};
    }

    public Double calculate(HashMap<String, Double> passedValues, String nullValue) {

        if(nullValue == V) {
            return (passedValues.get(S) + (0.5 * passedValues.get(A) * (passedValues.get(T) * passedValues.get(T)))) / passedValues.get(T) ;
        } else if(nullValue == T){
            /**
             *  Rearranged to : (V +- sqrt(V^2 - 2AS)) / a
             *
             * We can't  accept a negative time
             */
            double tempValue;

            tempValue = (passedValues.get(V) + Math.sqrt((passedValues.get(V) * passedValues.get(V)) - (2 * passedValues.get(A) * passedValues.get(S))))/passedValues.get(A);

            if(tempValue < 0){
                tempValue = (passedValues.get(V) - Math.sqrt((passedValues.get(V) * passedValues.get(V)) - (2 * passedValues.get(A) * passedValues.get(S))))/passedValues.get(A);
            }
            return tempValue;
        } else if(nullValue == S) {
            return (passedValues.get(V) * passedValues.get(T)) - (0.5 * passedValues.get(A) * (passedValues.get(T) * passedValues.get(T)));
        } else if(nullValue == A) {
            return ((passedValues.get(T) * passedValues.get(T)) - passedValues.get(S) ) / (0.5 * (passedValues.get(T) * passedValues.get(V) ));
        }
        return null;
    }
}
