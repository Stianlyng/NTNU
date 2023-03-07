import org.junit.jupiter.api.Test;
import resources.CalculatorResource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorResourceTest{

    @Test
    public void testCalculate(){
        CalculatorResource calculatorResource = new CalculatorResource();

        String expression = "100+300+100+100";
        assertEquals(600, calculatorResource.calculate(expression));

        expression = "300 - 99 - 1 - 1";
        assertEquals(199, calculatorResource.calculate(expression));

        expression = "5 * 5 * 2 * 2";
        assertEquals(100, calculatorResource.calculate(expression));

        expression = "25 / 5 / 5 / 1";
        assertEquals(1, calculatorResource.calculate(expression));
    }

    @Test
    public void testSum(){
        CalculatorResource calculatorResource = new CalculatorResource();

        String expression = "10 + 10 + 10";
        assertEquals(30, calculatorResource.sum(expression));

        expression = "300+99";
        assertEquals(399, calculatorResource.sum(expression));
    }

    @Test
    public void testSubtraction(){
        CalculatorResource calculatorResource = new CalculatorResource();

        String expression = "999-100-100";
        assertEquals(799, calculatorResource.subtraction(expression));

        expression = "20-2";
        assertEquals(18, calculatorResource.subtraction(expression));
    }

    @Test
    public void testMultiplication() {
        CalculatorResource calculatorResource = new CalculatorResource();

        String expression = "2*2*2*2";
        assertEquals(16, calculatorResource.multiplication(expression));

        expression = "5*5";
        assertEquals(25, calculatorResource.multiplication(expression));
    }

    @Test
    public void testDivision() {
        CalculatorResource calculatorResource = new CalculatorResource();

        String expression = "100/10/2";
        assertEquals(5, calculatorResource.division(expression));

        expression = "100/10";
        assertEquals(10, calculatorResource.division(expression));
    }
}
