package model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by sam on 15/01/2017.
 */
public class ArithmeticTest {

    @Test
    public void string_to_one_digit_number(){
        assertEquals(Arithmetic.stringToOneDigitNumber("four"), 4);
    }

    @Test
    public void string_to_two_digit_number(){
        assertEquals(Arithmetic.stringToTwoDigitNumber("twenty four"), 24);
    }

    @Test
    public void string_to_three_digit_number(){
        assertEquals(Arithmetic.stringToThreeDigitNumber("seven hundred eighty six"), 786);
    }

    @Test
    public void convert_string_to_number1(){
        assertEquals(Arithmetic.convertStringToNumber("one"), 1);
    }

    @Test
    public void convert_string_to_number2(){
        assertEquals(Arithmetic.convertStringToNumber("five thousand sixty two"), 5062);
    }

    @Test
    public void convert_string_to_number3(){
        assertEquals(Arithmetic.convertStringToNumber("one hundred eighty five thousand three hundred four"), 185304);
    }

    @Test
    public void convert_string_to_number4(){
        assertEquals(Arithmetic.convertStringToNumber("one hundred twenty five million six hundred seventy six thousand five hundred thirty four"), 125676534);
    }

    @Test
    public void get_first_number(){
        assertEquals(Arithmetic.getFirstNumber("Jarvis what is one thousand seventy eight times three hundred twenty four"), 1078);
    }

    @Test
    public void get_second_number(){
        assertEquals(Arithmetic.getFirstNumber("Jarvis what is one thousand seventy eight times three hundred twenty four"), 1078);
    }

    @Test
    public void get_operator(){
        assertEquals(Arithmetic.getFirstNumber("Jarvis what is one thousand seventy eight times three hundred twenty four"), 1078);
    }

    @Test
    public void calculation_plus(){
        assertEquals(Arithmetic.calculation(123, "plus", 50430), 50553.0, 0);
    }

    @Test
    public void calculation_minus(){
        assertEquals(Arithmetic.calculation(345023, "minus", 23423), 321600, 0);
    }
    @Test
    public void calculation_times(){
        assertEquals(Arithmetic.calculation(342, "times", 2346), 802332.0, 0);
    }

    @Test
    public void calculation_divided_by(){
        assertEquals(Arithmetic.calculation(49234, "divided by", 722), 68.1911357341, 0.001);
    }

    @Test
    public void calculation_to_the_power_of(){
        assertEquals(Arithmetic.calculation(42, "to the power of", 4), 3111696.0, 0);
    }

}
