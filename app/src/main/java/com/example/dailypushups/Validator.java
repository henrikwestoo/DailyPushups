package com.example.dailypushups;

public class Validator {

    //kontrollerar som strängen kan göras om till en int
    public static boolean isInteger(String phrase){

        boolean isInteger = true;

        try{
            Integer.parseInt(phrase);
        }
        catch(NullPointerException | NumberFormatException |  IndexOutOfBoundsException ex){

            isInteger = false;
        }
        return isInteger;
    }

}
