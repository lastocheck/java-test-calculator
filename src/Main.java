import java.util.Scanner;

public class Main {
    enum RomanNumber {
        I(1), II(2), III(3), IV(4), V(5), VI(6), VII(7), VIII(8), IX(9), X(10);

        final private int value;

        RomanNumber(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = "";
        while (true) {
            System.out.println("Введите арифметическую операцию, или exit для выхода из программы.");
            line = scanner.nextLine();
            if (line.equals("exit")) {
                System.out.println("Завершение работы");
                break;
            }
            else {
                try {
                    System.out.println(calc(line));


                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Завершение работы");
                    break;
                }

            }
        }
    }


    private static int parseOperandArabic(String input) throws NumberFormatException, Exception {
        //trying to parse an int
        int result = Integer.parseInt(input);
        if (result < 1 || result > 10) {
            throw new Exception("Калькулятор работает с числами от 1 до 10");
        }
        return result;
    }

    private static Integer calculate(char operator, int operand1, int operand2) throws Exception {
        switch (operator) {
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '*':
                return operand1 * operand2;
            case '/':
                try {
                    return operand1 / operand2;
                } catch (ArithmeticException e) {
                    throw new Exception("Деление на ноль");
                }
            default:
                throw new Exception("Данный оператор не поддерживается");

        }
    }

    private static String convertToRoman(int number) throws Exception {
        if (number < 1 || number > 100)
            throw new Exception("Неподдерживаемое римское число");

        String result = "";

        int currentDigit = number % 10;
        if (currentDigit > 0)
            result += RomanNumber.values()[currentDigit-1];

        number /= 10;

        // tens
        if (number > 0) {
            String singles = result;
            result = "";
            currentDigit = number % 10;
            if (currentDigit > 0) {
                if (currentDigit < 4) {
                    for (int i = 0; i < currentDigit; i++) {
                        result += "X";
                    }
                } else if (currentDigit == 4) {
                    result += "XL";

                } else if (currentDigit < 9) {
                    result += "L";
                    for (int i = 0; i < currentDigit - 5; i++) {
                        result += "X";
                    }

                } else if (currentDigit == 9) {
                    result += "XC";
                }
            } else {
                // 100
                result += "C";
            }
            result += singles;
        }
//        String resultReversed = "";
//        for (int i = result.length()-1; i >= 0; i--) {
//            resultReversed += result.charAt(i);
//        }

        return result;
    }


    public static String calc(String input) throws Exception {

        String[] splitLine = input.split("[+\\-*/]");

        // get operator
        char operator = '0';
        for (char c : "+-*/".toCharArray()) {
            if (input.contains(""+c)) {
                operator = c;
                break;
            }
        }

        // if no operator found, throw exception
        if (operator == '0')
            throw new Exception("Неверный формат ввода");

        // if it doesnt find two operands
        if (splitLine.length != 2)
            throw new Exception("Неверный формат ввода");

        //try to parse an int
        try {
            int operand1 = parseOperandArabic(splitLine[0].trim());
            int operand2 = parseOperandArabic(splitLine[1].trim());
            return calculate(operator, operand1, operand2).toString();
        } catch (NumberFormatException e) {
            //if failed to parse int, check if its a roman numeral
            try {
                RomanNumber operand1 = RomanNumber.valueOf(splitLine[0].trim().toUpperCase());
                RomanNumber operand2 = RomanNumber.valueOf(splitLine[1].trim().toUpperCase());

                int result = calculate(operator, operand1.getValue(), operand2.getValue());
                return convertToRoman(result);

            } catch (IllegalArgumentException ee) {
                throw new Exception("Неверный формат числа");
            }

        }
    }
}