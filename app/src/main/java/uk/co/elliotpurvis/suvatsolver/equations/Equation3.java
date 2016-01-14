package uk.co.elliotpurvis.suvatsolver.equations;

import java.util.HashMap;

import uk.co.elliotpurvis.suvatsolver.MainActivity;

/**
 * Created by Elliot on 04/09/2015.
 */
public class Equation3 implements MainActivity.Equation {


    private final String U,V,A,S,T;

    public Equation3(){
        U = MainActivity.INITIAL_VELOCITY;
        V = MainActivity.VELOCITY;
        S = MainActivity.DISTANCE;
        T = MainActivity.TIME;
        A = MainActivity.ACCELERATION;

    }

    /**
     *  s = ((u+v)/2 )* t
     **/
    public String[] getRequiredChars() {
        return new String[]{V, U, S, T};
    }

    public Double calculate(HashMap<String, Double> passedValues, String nullValue) {
        if(nullValue == V) {
            return ((2 * passedValues.get(S)) / passedValues.get(T)) - passedValues.get(U);
        } else if(nullValue == U){
            return ((2 * passedValues.get(S)) / passedValues.get(T)) - passedValues.get(V);
        } else if(nullValue == S) {
            return ((passedValues.get(U) + passedValues.get(V)) / 2) * passedValues.get(T);
        } else if(nullValue == T) {
            return (passedValues.get(S) / (0.5 * (passedValues.get(U) + passedValues.get(V))));
        }
        return null;
    }
}
