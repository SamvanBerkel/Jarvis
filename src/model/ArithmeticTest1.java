package model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by sam on 15/01/2017.
 */
public class ArithmeticTest1 {

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
}
