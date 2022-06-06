package SimpleCalculator;
// не забытғ унарныј оператор
// покрытғ junit test
//сделать проверку чтобы колво цмфр  соответствовало колличеству операторов и скобок

import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class Calculator {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String expression = sc.nextLine();   // принимаем выражение с консоли
        reverseToPolishNotation(expression);
        sc.close();

    }

    private static void reverseToPolishNotation(String string) {
        Stack<Character> operators = new Stack<>();
        StringBuilder symbols = new StringBuilder();
        int priority;
        for (int i = 0; i < string.length(); i++) {
            priority = getPriority(string.charAt(i));  // получаем приоритет текущего символа
            if (priority == 0) {        //если число то добавляем в стрингбилдер
                symbols.append(string.charAt(i));

            } //если оператора, то добавляем в стек или строку в зависмости от приоритета
            else {
                if (priority == 1) //если приоритет оператора  =1,то это ( и кладем в стек
                {
                    operators.push(string.charAt(i));
                }
                if (priority > 1) {            //если >1,то это + - / *
                    while (!operators.isEmpty()) {   //пока стэк не будет пустым, 
                        symbols.append(" ");   // пробел,чтобы элементы можно было различить
                        if (getPriority(operators.peek()) >= priority) // если приоритет верхнего элемента стека больше или равно,чем, приоритет у текущего элемента,то убираем из стека и добавл в стрингбилдер
                        {
                            symbols.append(operators.pop());
                        } else {
                            break;  //иначе заканчиваем обход стека
                        }
                    }
                    operators.push(string.charAt(i));   // кладем оператор  в стек
                }
                if (priority == -1) {  // если оператор ) то пока не встретим открывающуюся скобку 
                    symbols.append(" ");
                    while (getPriority(operators.peek()) != 1) {
                        symbols.append(operators.pop());

                    }
                    operators.pop();
                }
                symbols.append(" ");
            }

        }

        while (!operators.isEmpty()) {   //пока стэк не будет пустым, 
            symbols.append(" ");   // пробел,чтобы элементы можно было различить
            symbols.append(operators.pop());
        } //вытаксиваем из стека оставшиеся оператора в стрингбилдер 
        //   System.out.println(Arrays.toString(symbols.toString().trim().split("\\s+")));
        System.out.println(symbols.toString().trim());
        solution(symbols.toString().trim());

    }

    private static int getPriority(char token) {
        if (token == '*' || token == '/') {
            return 3;
        } else if (token == '+' || token == '-') {
            return 2;
        } else if (token == '(') {
            return 1;
        } else if (token == ')') {
            return -1;
        } else {
            return 0;
        }

    }
    static Stack<Double> stack = new Stack<>();

    private static void solution(String task) {
        final var expression = task.split("\\s+");

        try {
            for (int i = 0; i < expression.length; i++) {
                if (isNumber(expression[i])) {
                    stack.push(Double.parseDouble(expression[i]));
                } else {
                    double op1 = stack.pop();
                    double op2 = stack.pop();
                    evaluate(op1, op2, expression[i]);
                }
            }
            if (!stack.isEmpty()) {
                System.out.println(stack.pop());

            } else {
                System.out.println("ERROR");
            }
        } catch (IllegalArgumentException re) {
            System.out.println("Operation Error!");
        } catch (ArithmeticException ae) {
            System.out.println("Error! Division by zero");
        }
    }

    private static void evaluate(double op1, double op2, String operator) {
        double result;
        switch (operator) {
            case "+":
                stack.push(op1 + op2);
                break;

            case "-":
                stack.push(op2 - op1);
                break;

            case "*":
                stack.push(op1 * op2);
                break;

            case "/":
                if (op2 == 0.0) {
                    throw new ArithmeticException();
                }
                stack.push(op2 / op1);
                break;

            default:
                throw new IllegalArgumentException();
        }

    }

    private static boolean isNumber(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }

    }
}
