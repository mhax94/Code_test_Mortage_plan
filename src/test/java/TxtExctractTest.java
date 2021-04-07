
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class TxtExctractTest {
    TextExtractor codeCheck = new TextExtractor();


    @Test
    void nameTest()
    {
        String[] testData = {"hans\"","FiskHatt","1344","2","6"};
        boolean[] testBoolean = {true,true,false,false,false};
        String testValue = codeCheck.nameController(testData,testBoolean);

        Assertions.assertEquals("hans FiskHatt",testValue);
    }



    @Test
    void dataFilterTest()
    {
        String[] newDataTest = {"Max", "munter","2.13","32.22","1"};


        String[][] testArray =
                {{"Hans Tätbyxa","33","2","26"},
                 {"Petra Gladhjärta","1000","22","11"}};

        String[][] compareArray ={{"Hans Tätbyxa","33","2","26"},
                                  {"Petra Gladhjärta","1000","22","11"},
                                  {"Max munter","2.13","32.22","1"}};


        testArray=codeCheck.dataFilter(newDataTest,testArray);

        Assertions.assertArrayEquals(compareArray[0],testArray[0]);
        Assertions.assertArrayEquals(compareArray[1],testArray[1]);
        Assertions.assertArrayEquals(compareArray[2],testArray[2]);
    }


    @Test
    void readTxtTest()
    {
        String[][] testArray = codeCheck.readTxt("prospects.txt");

        String[][] compareArray = {{"Juha","1000","5","2"},
                {"Karvinen","4356","1.27","6"},
                {"Claes Månsson","1300.55","8.67","2"},
                        {"Clarencé Andersson","2000","6","4"}};
        System.out.println(Arrays.toString(testArray[2]));
        System.out.println(testArray[2][0]=="Claes Månsson");

        Assertions.assertEquals(compareArray[2][0],"Claes Månsson");
        Assertions.assertArrayEquals(compareArray[0],testArray[0]);
        Assertions.assertArrayEquals(compareArray[1],testArray[1]);
        Assertions.assertArrayEquals(compareArray[2],testArray[2]);
        Assertions.assertArrayEquals(compareArray[3],testArray[3]);
    }

}
