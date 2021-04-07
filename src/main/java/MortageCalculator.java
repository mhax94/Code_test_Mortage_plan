/*
this code calculates the monthly payment of the customer and displays the info of the customer according to the assignment

@author Max Hellberg
*/

import java.text.DecimalFormat;

public class MortageCalculator {
    public static float powerOf(float base, int raisedBy) {
        //Since the library Math was not allowed to be used,
        //was powerOf made in its stead

        //if the raised value is = 0, then returnedValues is 1
        float retunedValue = 1;

        if (raisedBy != 0) {
            for (int i = 1; i <= raisedBy; i++) {
                retunedValue *= base;
            }

            if (raisedBy < 0) {
                // For negative exponent, must invert
                retunedValue = (float) (1.0 / retunedValue);
            }
        }

        return retunedValue;
    }



    public static float monthlyPayment(String totalLoan, String interest, String years) {
        //Creating equation from assignment

        float e;
        //Adjusts rent to percentage and divided over months
        float b = Float.parseFloat(interest) / (100 * 12);
        float u = Float.parseFloat(totalLoan);
        //turns years into months
        int p = Integer.parseInt(years) * 12;

        String eAdjust;

         e = u * (b * powerOf((1 + b), p)) / (powerOf((1 + b), p) - 1);

        //make it only keep two decimals
        eAdjust = new DecimalFormat("0.00").format(e);
        e = Float.parseFloat(eAdjust);

        return e;

    }

    public static void displayMortage(String[] Customer) {
        //Calculates monthly payment and displays the text
        //according to the assignment
        float monthlyPayment = monthlyPayment(Customer[1], Customer[2], Customer[3]);

        System.out.println(Customer[0] + " wants to borrow "
                + Customer[1] + "\u20ac for a period of "
                + Customer[3] + " years and pay " +
                monthlyPayment + "\u20ac each month");
    }
}
