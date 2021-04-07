import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SQLTest {

    private MySqlConnection dataBase = new MySqlConnection();




    @Test
    void splitGivenName()
    {
        String[] displayArray1={null,null,null,null,null,null};
        String[] dataArray1 ={"Pontus","12","5","33"};

        String[] displayArray2={null,null,null,null,null,null};
        String[] dataArray2 ={"Pontus Erik Hansson","12","5","33"};

        displayArray1=dataBase.nameSplitter(dataArray1,displayArray1);
        displayArray2=dataBase.nameSplitter(dataArray2,displayArray2);

        Assertions.assertEquals("Pontus",displayArray1[0]);
        Assertions.assertEquals(null,displayArray1[1]);

        Assertions.assertEquals("Pontus",displayArray2[0]);
        Assertions.assertEquals("Erik",displayArray2[1]);
    }

    @Test
    void sqlAdjustmentTest()
    {
        String[] testCustomerSend1 = {"Juha","1000","5","2"};
        float testMonthlyPayment1 = 43.87f;
        String[] testCustomerReceiver1;
        String[] testCustomerResults1 = {"Juha",null,"1000","5","2","43.87"};

        String[] testCustomerSend2 = {"Juha Muha","1000","5","2"};
        float testMonthlyPayment2 = 43.87f;
        String[] testCustomerReceiver2;
        String[] testCustomerResults2 = {"Juha","Muha","1000","5","2","43.87"};

        testCustomerReceiver1 = dataBase.adjustArrayForSQL(testCustomerSend1,testMonthlyPayment1);

        Assertions.assertEquals(testCustomerResults1[0],testCustomerReceiver1[0]);
        Assertions.assertEquals(testCustomerResults1[1],testCustomerReceiver1[1]);
        Assertions.assertEquals(testCustomerResults1[2],testCustomerReceiver1[2]);
        Assertions.assertEquals(testCustomerResults1[3],testCustomerReceiver1[3]);
        Assertions.assertEquals(testCustomerResults1[4],testCustomerReceiver1[4]);
        Assertions.assertEquals(testCustomerResults1[5],testCustomerReceiver1[5]);

        testCustomerReceiver2 = dataBase.adjustArrayForSQL(testCustomerSend2,testMonthlyPayment2);

        Assertions.assertEquals(testCustomerResults2[0],testCustomerReceiver2[0]);
        Assertions.assertEquals(testCustomerResults2[1],testCustomerReceiver2[1]);
        Assertions.assertEquals(testCustomerResults2[2],testCustomerReceiver2[2]);
        Assertions.assertEquals(testCustomerResults2[3],testCustomerReceiver2[3]);
        Assertions.assertEquals(testCustomerResults2[4],testCustomerReceiver2[4]);
        Assertions.assertEquals(testCustomerResults2[5],testCustomerReceiver2[5]);

    }

    @Test
    void addAndRemoveCustomerTest()
    {
        dataBase.connectToMySQL();
        String[] testCustomerinput = {"Juha",null,"1000","5","2","43.87"};

        dataBase.addSQLData(testCustomerinput);

        int sqlAddedID= dataBase.newCustomerID()-1;

        dataBase.removeSQLData(sqlAddedID);
    }

    @Test
    void removeRandomIDAndThenAllID()
    {
        dataBase.connectToMySQL();

        dataBase.removeSQLData(2);

        dataBase.removeAllSQLData();
    }

    @Test
    void displaySQLTest()
    {
        dataBase.connectToMySQL();
        dataBase.displaySQLData();
    }


}
