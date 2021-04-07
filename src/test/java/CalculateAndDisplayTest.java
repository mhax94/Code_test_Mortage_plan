import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



public class CalculateAndDisplayTest {
    MortageCalculator codeCheck = new MortageCalculator();

    @Test
    void powerOfTest()
    {

        float testValue = codeCheck.powerOf(4,2);

        Assertions.assertEquals(16,testValue);
    }

    @Test
    void calculationTest()
    {
        float paymentTest = codeCheck.monthlyPayment("1000","5","2");

        Assertions.assertEquals(43.87,paymentTest,0.0001);
    }

    @Test
    void displayMortageTest()
    {
        String[] testCustomer = {"Claes Månsson","1300.55","8.67","2"};

        codeCheck.displayMortage(testCustomer);
    }
}
