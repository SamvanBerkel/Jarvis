package model;

/**
 * Created by sambe on 13/01/2017.
 */
public class Arithmetic {
    private static final String[] oneDigitNumbers = {"one", "two", "three", "four", "five", "six",
            "seven", "eight", "nine"};

    public static void main(String[] args){

    }

    public static double calculation(long firstNumber, String operator, long secondNumber){
        double result = 0;
        switch(operator){
            case("plus"): result = firstNumber + secondNumber;
                break;
            case("minus"): result = firstNumber - secondNumber;
                break;
            case("times"): result = firstNumber * secondNumber;
                break;
            case("divided by"): result = firstNumber / secondNumber;
                break;
            case("to the power of"): result = Math.pow(firstNumber, secondNumber);
        }
        return result;
    }

    public static long getFirstNumber(String calculationSentence){
        int indexOfFirstNumber = 0;
        if(calculationSentence.contains("what is")){
            indexOfFirstNumber = 15;
        } else {
            indexOfFirstNumber = 7;
        }

        int indexOfOperator = calculationSentence.indexOf(getOperator(calculationSentence));

        String firstNumberString = calculationSentence.substring(indexOfFirstNumber, indexOfOperator - 1);

       return convertStringToNumber(firstNumberString);
    }

    public static long getSecondNumber(String calculationSentence) {
        int lengtOfOperator = 0;

        String operator = getOperator(calculationSentence);

        switch (operator){
            case "plus": lengtOfOperator = 4;
                break;
            case "minus": lengtOfOperator = 5;
                break;
            case "times": lengtOfOperator = 5;
                break;
            case "divided by": lengtOfOperator = 10;
                break;
            case "to the power of": lengtOfOperator = 15;
                break;
        }

        int indexofOperator = calculationSentence.indexOf(operator);
        int indexOfSecondNumber = indexofOperator + lengtOfOperator;

        String secondNumberString = calculationSentence.substring(indexOfSecondNumber, calculationSentence.length());

        return convertStringToNumber(secondNumberString);
    }

    public static String getOperator(String calculationSentence){
        if(calculationSentence.contains("plus")){
            return "plus";
        } else if(calculationSentence.contains("minus")) {
            return "minus";
        } else if(calculationSentence.contains("times")) {
            return "times";
        } else if(calculationSentence.contains("divided by")) {
            return "divided by";
        } else if(calculationSentence.contains("to the power of")) {
            return "to the power of";
        }
        return "";
    }

    public static long convertStringToNumber(String word){
        long billionDigits = 0;
        long millionDigits = 0;
        long thousandDigits = 0;
        long finalDigits = 0;

        int indexOfBillion = 0;
        int indexOfMillion = 0;
        int indexOfMillionPlus8 = 0;
        int indexOfThousand = 0;
        int highestIndex = 0;

        String[] wordArray = word.split(" ");
        if (word.equals("zero")){
            return 0;
        }

        if (word.contains("billion")){
            indexOfBillion = word.indexOf("billion");
            String numberBeforeBillionString = word.substring(0, indexOfBillion - 1);
            int numberBeforeBillion = stringToThreeDigitNumber(numberBeforeBillionString);
            billionDigits = numberBeforeBillion * 1000000000;
        }

        if (word.contains("million")){
            indexOfMillion = word.indexOf("million");
            String numberBeforeMillionString = word.substring(0, indexOfMillion - 1);
            int numberBeforeMillion = stringToThreeDigitNumber(numberBeforeMillionString);
            millionDigits = numberBeforeMillion * 1000000;
            indexOfMillionPlus8 = indexOfMillion + 8;
        }

        if (word.contains("thousand")){
            indexOfThousand = word.indexOf("thousand");

            String numberBeforeThousandString = word.substring(indexOfMillionPlus8, indexOfThousand - 1);
            int numberBeforeThousand = stringToThreeDigitNumber(numberBeforeThousandString);
            thousandDigits = numberBeforeThousand * 1000;
        }

        int[] indexArray = {indexOfBillion, indexOfMillion, indexOfThousand};

        for (int i = 0; i < indexArray.length; i++) {
            for (int j = i + 1; j < indexArray.length; j++) {
                int tmp = 0;
                if (indexArray[i] > indexArray[j]) {
                    tmp = indexArray[i];
                    indexArray[i] = indexArray[j];
                    indexArray[j] = tmp;
                }
            }
        }



        highestIndex = indexArray[2];

        if(highestIndex > 0 ){
           highestIndex = highestIndex + 9;
        }

        String finalDigitsString = word.substring(highestIndex, word.length());

        finalDigits = stringToThreeDigitNumber(finalDigitsString);

        return billionDigits + millionDigits + thousandDigits + finalDigits;
    }

    public static int stringToThreeDigitNumber(String word) {
        String[] wordArray = word.split(" ");
        int indexOfHundred = word.indexOf("hundred");
        if(word.contains("hundred")){
            String stringBeforeHundred = wordArray[0];
            int numberBeforeHundred = stringToOneDigitNumber(stringBeforeHundred) * 100;
            String stringAfterHundred = word.substring(indexOfHundred + 8, word.length());
            int numberAfterHundred = stringToTwoDigitNumber(stringAfterHundred);
            return numberBeforeHundred + numberAfterHundred;
        }
        return stringToTwoDigitNumber(word);
    }

    public static int stringToTwoDigitNumber(String word) {
        String[] wordArray = word.split(" ");
        String firstDigitString = wordArray[0];
        String secondDigitString = "";

        if(wordArray.length > 1){
            secondDigitString = wordArray[1];
        }

        int firstDigit = 0;
        int secondDigit = stringToOneDigitNumber(secondDigitString);

        if(word.contains("eleven") || word.contains("twelve") || word.contains("teen")){
            switch(word){
                case "ten": return 10;
                case "eleven": return 11;
                case "twelve": return  12;
                case "thirteen": return 13;
                case "fourteen": return 14;
                case "fifteen": return 15;
                case "sixteen": return 16;
                case "seven teen": return 17;
                case "eight teen": return 18;
                case "nine teen": return 19;
            }
        } else {
            switch(firstDigitString){
                case "twenty": firstDigit = 20;
                    break;
                case "thirty": firstDigit = 30;
                    break;
                case "forty": firstDigit = 40;
                    break;
                case "fifty": firstDigit = 50;
                    break;
                case "sixty": firstDigit = 60;
                    break;
                case "seventy": firstDigit = 70;
                    break;
                case "eighty": firstDigit = 80;
                    break;
                case "ninety": firstDigit = 90;
                    break;
                case "one": firstDigit =0;
                    secondDigit = 1;
                    break;
                case "two": firstDigit = 0;
                    secondDigit = 2;
                    break;
                case "three": firstDigit = 0;
                    secondDigit = 3;
                    break;
                case "four": firstDigit = 0;
                    secondDigit = 4;
                    break;
                case "five": firstDigit = 0;
                    secondDigit = 5;
                    break;
                case "six": firstDigit = 0;
                    secondDigit = 6;
                    break;
                case "seven": firstDigit = 0;
                    secondDigit = 7;
                    break;
                case "eight": firstDigit = 0;
                    secondDigit = 8;
                    break;
                case "nine": firstDigit = 0;
                    secondDigit = 9;
                    break;
                case "ten": firstDigit = 0;
                    secondDigit = 10;
                    break;
            }
        }
        int number = firstDigit + secondDigit;
        return number;
    }

    public static int stringToOneDigitNumber(String word) {
        int number = 0;
        switch(word){
            case "one": number = 1;
                break;
            case "two": number = 2;
                break;
            case "three": number = 3;
                break;
            case "four": number = 4;
                break;
            case "five": number = 5;
                break;
            case "six": number = 6;
                break;
            case "seven": number = 7;
                break;
            case "eight": number = 8;
                break;
            case "nine": number = 9;
        }
        return number;
    }
}
