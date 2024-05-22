/**
 * 1. Калькулятор умеет выполнять операции сложения, вычитания, умножения и деления с двумя числами: a + b, a - b, a * b, a / b.
 * Данные передаются в одну строку (смотри пример)! Решения, в которых каждое число и арифметическая операция передаются с новой строки считаются неверными.
 * <p>
 * 2. Калькулятор умеет работать как с арабскими (1,2,3,4,5...), так и с римскими (I, II, III, IV, V...) числами.
 * <p>
 * 3. Калькулятор должен принимать на вход числа от 1 до 10 включительно, не более. На выходе числа не ограничиваются по величине и могут быть любыми.
 * <p>
 * 4. Калькулятор умеет работать только с целыми числами.
 * <p>
 * 5. Калькулятор умеет работать только с арабскими или римскими цифрами одновременно, при вводе пользователем строки вроде 3 + II калькулятор должен
 * выбросить исключение и прекратить свою работу.
 * <p>
 * 6. При вводе римских чисел, ответ должен быть выведен римскими цифрами, соответственно, при вводе арабских - ответ ожидается арабскими.
 * <p>
 * 7. При вводе пользователем неподходящих чисел приложение выбрасывает исключение и завершает свою работу.
 * <p>
 * 8. При вводе пользователем строки, не соответствующей одной из вышеописанных арифметических операций, приложение выбрасывает исключение и завершает свою работу.
 * <p>
 * 9. Результатом операции деления является целое число, остаток отбрасывается.
 * <p>
 * 10. Результатом работы калькулятора с арабскими числами могут быть отрицательные числа и ноль. Результатом работы калькулятора с римскими
 * числами могут быть только положительные числа, если результат работы меньше единицы, выбрасывается исключение
 */

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws ExceptionCalc {
        Scanner sc = new Scanner(System.in);
        while (!sc.nextLine().equals("exit")) {
            System.out.println("Enter an expression:");
            System.out.println(calc(sc.nextLine()));
        }
        sc.close();

    }

    public static String calc(String input) throws ExceptionCalc {

        if (input.isEmpty()) {
            throw new ExceptionCalc("String empty");
        }

        if (input.equals("exit")) {
            return "exit";
        }

        String regexOperand = "[+\\-*/]";
        String regexArabOperand = "^[0-9 ]+([+\\-*/])([0-9 ]+)$";
        String regexRomeOperand = "^[IVX ]+([+\\-*/])([IVX ]+)$";

//работа с арабскими числами
        Pattern patternArab = Pattern.compile(regexArabOperand);
        Matcher matcherArab = patternArab.matcher(input);
        input = input.trim();
        if (matcherArab.matches()) {
            String[] numbersArab = input.split(regexOperand);
            int arabNumberOne = Integer.parseInt(numbersArab[0].trim());
            int arabNumberTwo = Integer.parseInt(numbersArab[1].trim());
            if (arabNumberOne < 11 && arabNumberTwo < 11) {
                int result;
                if (input.contains("+")) {
                    result = arabNumberOne + arabNumberTwo;
                } else if (input.contains("-")) {
                    result = arabNumberOne - arabNumberTwo;
                } else if (input.contains("*")) {
                    result = arabNumberOne * arabNumberTwo;
                } else if (input.contains("/") && arabNumberTwo != 0) {
                    result = arabNumberOne / arabNumberTwo;
                } else {
                    throw new ExceptionCalc("Incorrect expression");
                }
                return String.valueOf(result);
            }
        }

//работа с римскими числами

        String[] unitsRomeForCheck = {"I", "II", "III", "V", "IV", "VI", "VII", "VIII", "X", "IX"}; //порядок цифр изменен для корректной проверки
        int[] unitsArabForCheck = {1, 2, 3, 5, 4, 6, 7, 8, 10, 9}; //порядок цифр изменен для корректной проверки

        String[] unitsRome = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        String[] tensRome = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] hundredsRome = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};

        Pattern patternRome = Pattern.compile(regexRomeOperand);
        Matcher matcherRome = patternRome.matcher(input);
        input = input.trim();
        if (matcherRome.matches()) {
            String[] numbersRome = input.split(regexOperand);
            int romeNumberOne = 0;
            int romeNumberTwo = 0;
            for (int i = 9; i >= 0; i--) {
                if (numbersRome[0].trim().equals(unitsRomeForCheck[i])) {
                    romeNumberOne = unitsArabForCheck[i];
                }
                if (numbersRome[1].trim().equals(unitsRomeForCheck[i])) {
                    romeNumberTwo = unitsArabForCheck[i];
                }
            }
            if (romeNumberOne == 0 || romeNumberTwo == 0) {
                throw new ExceptionCalc("Invalid Roman numerals");   //нуля в римских цифрах нет
            }
            int result = 0;
            if (input.contains("+")) {
                result = romeNumberOne + romeNumberTwo;
            } else if (input.contains("-")) {
                result = romeNumberOne - romeNumberTwo;
            } else if (input.contains("*")) {
                result = romeNumberOne * romeNumberTwo;
            } else if (input.contains("/")) {
                result = romeNumberOne / romeNumberTwo;
            }

            if (result > 0) {
                int numHundreds = result / 100;
                int numTens = (result - numHundreds * 100) / 10;
                int numUnits = (result - numHundreds * 100 - numTens * 10);
                String numUnitsRome = unitsRome[numUnits];
                String numTensRome = tensRome[numTens];
                String numHundredsRome = hundredsRome[numHundreds];
                return numHundredsRome + numTensRome + numUnitsRome;
            } else {
                throw new ExceptionCalc("The answer does not exist in the Roman numeral system");
            }
        }
        throw new ExceptionCalc("Wrong input");
    }
}

class ExceptionCalc extends Exception {
    public ExceptionCalc(String message) {
        super(message);
    }
}