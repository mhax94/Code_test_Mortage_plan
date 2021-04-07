import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class TextExtractor {

    public static  String nameController(String[] activeRow,boolean[] namePlacement)
    {
        /*
        activeRow contains the entire array of text from prospects.txt
        after each row has been split based on commas

        namePlacement has checked which parts of the row that contains names

        the for-loop goes through each column
            -checks if the column has a name in it
            -the removes all " inside the string
             (we could add more,but it was not necessary for the asignment)
            -merge names if names have been split of by commas (only Clarencé Andersson is affected in this case)
        the return the name
        */

        String customerName;
        String customerCompleteName=null;

        for(int i=0; i<activeRow.length;i++)
        {
            if(namePlacement[i])
            {
                customerName=activeRow[i].replaceAll("\"","");

                if(customerCompleteName==null)
                {
                    customerCompleteName=customerName;
                } else
                {
                    customerCompleteName+=(" "+customerName);
                }


            }
        }

        return customerCompleteName;
    }

    public static String[][] dataFilter(String[] activeRow,String[][] customerData)
    {
        /*
         goes through one row/customer at the time
         places customer in the customerData String that contains all the customers from the txt-file
         activeRow=current customer
         customerData=all customers in one string
        */


        float numberFilter1;
        int allMortageData=0;

        String[] returningRow= new String[4];
        int rowPlacement = 3;

        boolean[] nameColumn = new boolean[activeRow.length];

        /*
        numberFilter1 is a blocker. if numberfilter isn't a number, then allMortageData wont +=1
        if numberFilter1 isn't a number, then the code goes to catch, which puts nameColumn[i] to true
        nameColumn is used to manage names in nameController and needs to now which columns contains names
        allMortageData must be 3, otherwise the customer has given incorrect data
        */

        for(int i=0;i<activeRow.length;i++)
        {
            try{
                numberFilter1= Float.parseFloat(activeRow[i]);
                allMortageData+=1;
            } catch (NumberFormatException e)
            {
                nameColumn[i]=true;
            }

        }

        //Är längden med data större eller lika med 4?
        // Måste ha namn(helst för och efter-namn),Totalt lån, ränta, antal år att betala


        /*
        allMortageData must be 3, otherwise the customer has given incorrect data
        And the activeRos must be atleast the length of four (name + 3 numbers)
         */
        if(allMortageData==3 && activeRow.length >= 4)
        {
            //Merge all name columns in the array into one and add it to the returningRow
            //this is the array that will be added to the customerData
            returningRow[0]=nameController(activeRow,nameColumn);

            //this adds the numbers into the returningRow
            for(int i=activeRow.length-1;i!=(activeRow.length-4);i--)
            {
                returningRow[rowPlacement]=activeRow[i];
                rowPlacement-=1;
            }


            //makes customerData one size larger, to add the new customer
            customerData = Arrays.copyOf(customerData, customerData.length+1);
            for(int i=0;i<customerData.length;i++)
            {
                customerData[customerData.length-1]=returningRow;
            }


        }



        /*
        Returns customerData
        if the activeRow does not meet the requirements to add data to customerData
        then customerData will return unchanged
         */
        return customerData;
    }

    public static String[][] readTxt(String fileName)
    {
        /*
        readTxt takes the text from prospects.txt and makes an array[][] that contains all the customers
        in such a way for Main to later print the data according to the assignment
         */


        String[][] customerData = new String[0][0];



        try{
            //take the data from prospects.txt
            String file=fileName;
            System.out.println(file);
            File unfilteredData = new File("prospects.txt");
            Scanner myReader = new Scanner(unfilteredData,"UTF-8");

            //Bryt upp varje rad
            while (myReader.hasNextLine())
            {
                //goes through one row from the txt-file at a time, adjusts it and puts the data in customerData
                String activeRow = myReader.nextLine();
                String[] dataSeperated = activeRow.split(",");
                customerData = dataFilter(dataSeperated, customerData);
            }

        } catch(FileNotFoundException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        System.out.println("");
        return customerData;
    }

}
