/*
@author Max Hellberg
 */


public class Main {

    public static void addCustomerSQL(String[] customerData, float monthlyPayment)
    {
        //Connect to MySQL and add all new customers
        MySqlConnection dataBase = new MySqlConnection();
        dataBase.connectToMySQL();

        //Adjusts array for SQL use
        String[] customerDataSQL = dataBase.adjustArrayForSQL(customerData,monthlyPayment);

        dataBase.addSQLData(customerDataSQL);
    }


    public static void main(String[] args)
    {
        //Connects to TextExtractor and MortageCalculator
        TextExtractor textFileData = new TextExtractor();
        MortageCalculator mortage = new MortageCalculator();


        float monthlyPayment;
        String loan;
        String interest;
        String years;

        //makes the txt-file into an array with {complete name, loan,interest,years}
        String[][] newCustomers = textFileData.readTxt("prospects.txt");

        //the loop goes into display mortage which calculates
        //and displays the monthly payment according to the assignment
        //the loop then revieces the monthly payment and adds the customer to a database
        //the data is saved {first name, surname, total loan, interest, years, monthly payment}
        //then the code ends
        for(int i =0; i<newCustomers.length;i++)
        {
            mortage.displayMortage(newCustomers[i]);

            loan=newCustomers[i][1];
            interest = newCustomers[i][2];
            years =newCustomers[i][3];

            //monthlyPayment=mortage.monthlyPayment(loan,interest,years);
            //addCustomerSQL(newCustomers[i],monthlyPayment);
        }
    }
}
