import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class MySqlConnection {
    Connection conn=null;
    Statement stmt = null;
    ResultSet rs = null;


    public void connectToMySQL()
    {
        //Initiates connection to the MySQL server

        //variables to access a txtfile with
        //in the txt file is name and password, which are split with a comma
        File nameAndPasswordTxt;
        Scanner nameAndPassword;
        String nameAndPass;
        String[] logInArray = new String[0];


        try {
            // The newInstance() call is a work around for some
            // broken Java implementations
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            System.err.println("Got an exception!");
            System.err.println(ex.getMessage());
        }

        //Find name and password in txt file for loggin
        try
        {
            nameAndPasswordTxt = new File("signIn.txt");
            nameAndPassword = new Scanner(nameAndPasswordTxt);
            nameAndPass =nameAndPassword.nextLine();
            logInArray = nameAndPass.split(",");
        }catch(FileNotFoundException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        //Connect to MySQL server
        try {

            conn =
                    DriverManager.getConnection("jdbc:mysql://localhost/mortagebase?" +
                            "user="+logInArray[0]+"&password="+logInArray[1]);


        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

    //You are now connected
    }

    public void displaySQLData()
    {
        //This part is optional
        //it is to print all the data from MySQL in Java
        //it is used in Test to quickly see the data
        String customerId;
        String firstName;
        String surName;
        String totalLoan;
        String interst;
        String years;
        String monthlyPayment;


        //Prints the data in the customerData table
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM customerData");

            // or alternatively, if you don't know ahead of time that
            // the query will be a SELECT...
            String formDisplay = "%-16s%-16s%-16s%-16s%-16s%-16s%-16s\n";
            System.out.printf(formDisplay,"idCustomer","firstName","surName","totalLoan","interest","years","monthlyPayment");
            while(rs.next())
            {
                customerId=rs.getString(1);
                firstName=rs.getString(2);
                surName=rs.getString(3);
                totalLoan=rs.getString(4);
                interst=rs.getString(5);
                years=rs.getString(6);
                monthlyPayment=rs.getString(7);
                System.out.printf(formDisplay,customerId,
                        firstName,
                        surName,
                        totalLoan,
                        interst,
                        years,
                        monthlyPayment);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public int newCustomerID()
    {
        //When creating a new customer in the table, the customer must recieve a new ID
        //this is being done by searching for the largest ID and adding +1
        //If the table is empty, then newCustomerID will return 1

        String sqlMaxID;
        int returnID=1;

        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT MAX(idCustomer) FROM customerData");


            if(rs.next())
            {
                sqlMaxID=rs.getString(1);
                //Since null takes up a row in the table, we must prevent to do
                //null+1
                if(sqlMaxID!=null)
                {
                    returnID = Integer.parseInt(sqlMaxID) + 1;
                }
            }

        } catch (SQLException ex)
        {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }


        return returnID;
    }

    public void addSQLData(String[] newCustomer)
    {
        //Hitta nytt id för kund
        int idCustomer=newCustomerID();
        String firstName=newCustomer[0];
        String surName=newCustomer[1];
        float totalLoan=Float.parseFloat(newCustomer[2]);
        float interst=Float.parseFloat(newCustomer[3]);
        int years=Integer.parseInt(newCustomer[4]);
        float monthlyPayment=Float.parseFloat(newCustomer[5]);

        //Add new the customer to the customerData Table
        try
        {
            String query = "INSERT INTO customerData(idCustomer,firstName,surName,totalLoan,Interest,Years,MonthlyPayment)"+
                    "VALUES(?,?,?,?,?,?,?)";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,idCustomer);
            preparedStmt.setString(2,firstName);
            preparedStmt.setString(3,surName);
            preparedStmt.setFloat(4,totalLoan);
            preparedStmt.setFloat(5,interst);
            preparedStmt.setInt(6,years);
            preparedStmt.setFloat(7,monthlyPayment);

            preparedStmt.execute();

            //Following code is disabled since it is not part of the assignment
            //This links to displaySQLData to see all customer data

            //System.out.println("id: "+idCustomer+" added. New table:");
            //displaySQLData();



        }catch(Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }

    }

    public void removeSQLData(int id)
    {
        //Removes customer based on ID
        try
        {
            String query = "DELETE FROM customerData WHERE idCustomer=?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,id);

            preparedStmt.executeUpdate();

            System.out.println("id: "+id+" removed. New table:");
            displaySQLData();

        }catch(Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }

    public void removeAllSQLData()
    {
        //Removes all data in table
        //if we try and remove a number thar does not exist,
        // the code just jumps to the next number
        int largestID = newCustomerID()-1;

        for(int i=largestID;i>0;i--)
        {
            removeSQLData(i);
        }


    }

    static String[] nameSplitter(String[] oldArray,String[] newArray)
    {
        /*
        The recieved name is in one column "Hans Fisk"
        SQL wants first and surname, so the string must be split accordingly
        however. if a user writes several names,
        then the code will take the first name as firstname
        and the second name as surname, while ignoring the remaining names
        */

        String[] name = oldArray[0].split(" ");
        for(int i =0;( i<name.length && i<2);i++)
        {
            newArray[i]=name[i];
        }
        return newArray;
    }

    public String[] adjustArrayForSQL(String[] givenArray, float monthPayment)
    {
        /*
        Given array is one smaller than the SQL array
        this is due to the given array has one column for full name, and does not include the monthly payment
        while SQL wants first and surname separately, this adjustment is mad in nameSplitter
         */

        String[] sqlAdjustedArray = {null,null,null,null,null,null};

        sqlAdjustedArray=nameSplitter(givenArray,sqlAdjustedArray);
        sqlAdjustedArray[2]=givenArray[1];
        sqlAdjustedArray[3]=givenArray[2];
        sqlAdjustedArray[4]=givenArray[3];
        sqlAdjustedArray[5]=String.valueOf(monthPayment);


        return sqlAdjustedArray;
    }
}
