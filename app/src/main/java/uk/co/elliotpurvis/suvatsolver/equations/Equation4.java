package uk.co.elliotpurvis.suvatsolver.equations;

import java.util.HashMap;

import uk.co.elliotpurvis.suvatsolver.MainActivity;

/**
 * Created by Elliot on 04/09/2015.
 */
public class Equation4 implements MainActivity.Equation {

    private final String U,V,A,S,T;

    public Equation4(){
        U = MainActivity.INITIAL_VELOCITY;
        V = MainActivity.VELOCITY;
        S = MainActivity.DISTANCE;
        T = MainActivity.TIME;
        A = MainActivity.ACCELERATION;

    }

    /**
     *  V^2 = U^2 + 2as
     **/

    public String[] getRequiredChars() {
        return new String[]{V, U, S, A};
    }

    public Double calculate(HashMap<String, Double> passedValues, String nullValue) {
        if(nullValue == V) {
            return Math.sqrt((passedValues.get(U)*passedValues.get(U)) + (2 * passedValues.get(A) * passedValues.get(S)) );
        } else if(nullValue == U){
            return Math.sqrt((passedValues.get(V) * passedValues.get(V)) + (2 * passedValues.get(A) * passedValues.get(S)));
        } else if(nullValue == S) {
            return ((passedValues.get(V) * passedValues.get(V)) - (passedValues.get(U) * passedValues.get(U))) / (2 * passedValues.get(A));
        } else if(nullValue == A) {
            return ((passedValues.get(V) * passedValues.get(V)) - (passedValues.get(U) * passedValues.get(U))) / (2 * passedValues.get(S));
        }
        return null;
    }
}
