package uk.co.elliotpurvis.suvatsolver.equations;


import java.util.HashMap;

import uk.co.elliotpurvis.suvatsolver.MainActivity;

/**
 * Created by Elliot on 19/08/2015.
 */
public class Equation1 implements MainActivity.Equation {
    private final String U,V,A,S,T;

    public Equation1(){
        U = MainActivity.INITIAL_VELOCITY;
        V = MainActivity.VELOCITY;
        S = MainActivity.DISTANCE;
        T = MainActivity.TIME;
        A = MainActivity.ACCELERATION;

    }

    /**
     *  V = U + AT
     **/
    public String[] getRequiredChars() {
        return new String[]{ V, U ,A, T};
    }

    public Double calculate(HashMap<String, Double> values, String nullValue)
    {
        if(nullValue == V) {
            return (values.get(U) + (values.get(A)*values.get(T)));
        } else if(nullValue == U){
            return (values.get(V) - (values.get(A) * values.get(T)));
        } else if(nullValue == A) {
            return (values.get(V) - values.get(U) ) / values.get(T);
        } else if(nullValue == T) {
            return (values.get(V) - values.get(U) ) / values.get(A);
        }
        return null;
    }


}
