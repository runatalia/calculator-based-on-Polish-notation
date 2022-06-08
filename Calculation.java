package SimpleCalculator;

import java.util.EmptyStackException;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculation {

    private final String stringFromInPut;         //expression
    private Stack<Double> stack = new Stack<>(); //stack for solution 
    private Stack<Character> operators = new Stack<>(); //stack for operators in reverseToPolishNotation()

    public Calculation(String stringFromInPut) {
        this.stringFromInPut = stringFromInPut;
    }

    public String getStringFromInPut() {
        return stringFromInPut;
    }

    public double reverseToPolishNotation() {       //change the appearance of the string to reverse polish entry
        String correctedString = checkUnary(stringFromInPut);               //check for unarity

        StringBuilder reversePolishNotation = new StringBuilder();   //final reverse polish notation
        int priority;
        for (int i = 0; i < correctedString.length(); i++) {
            priority = getPriority(correctedString.charAt(i));  //get the priority of the current character
            if (priority == 0) {        //if number then add to reversePolishNotation
                reversePolishNotation.append(correctedString.charAt(i));

            } else {          //if not a number, then add to the stack or string, depending on priority
                if (priority == 1) //if operator priority = 1, put it on the stack
                {
                    operators.push(correctedString.charAt(i));
                }
                if (priority > 1) {            //if operator priority > 0
                    while (!operators.isEmpty()) {
                        reversePolishNotation.append(" ");   //space to distinguish elements
                        if (getPriority(operators.peek()) >= priority) //if the priority of the top element of the stack is greater than or equal to the priority of the current element, 
                        //then remove it from the stack and add it to the reversePolishNotation
                        {
                            reversePolishNotation.append(operators.pop());
                        } else {

                            break;                      //otherwise we end the stack walk
                        }
                    }
                    operators.push(correctedString.charAt(i));   // put the operator on the stack
                }
                if (priority == -1) {  // if operator ) then until we meet an opening bracket
                    reversePolishNotation.append(" ");
                    while (getPriority(operators.peek()) != 1) {
                        reversePolishNotation.append(" ");
                        reversePolishNotation.append(operators.pop());   //add to the reversePolishNotation and delete from operators

                    }
                    operators.pop();            //delete '('
                }
                reversePolishNotation.append(" ");
            }
        }
        while (!operators.isEmpty()) {
            reversePolishNotation.append(" ");
            reversePolishNotation.append(operators.pop()); // pop the remaining operators from the stack into the reversePolishNotation
        }

        return solution(reversePolishNotation.toString().trim());

    }

    private int getPriority(char token) {  //comparing operators to their expression
        if (token == '^' || token == '%') {
            return 4;
        } else if (token == '*' || token == '/') {
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

    private double solution(String task) {                     //the solution of the task
        final var expression = task.split("\\s+");           //array of elements
        double result = 0.0;
        try {
            for (int i = 0; i < expression.length; i++) {
                if (isNumber(expression[i])) {             //if number then push in stack   
                    stack.push(Double.parseDouble(expression[i]));
                } else {
                    double op1 = stack.pop();           //take last digits on the stack
                    double op2 = stack.pop();
                    evaluate(op1, op2, expression[i]);   //expression solution
                }
            }
            if (!stack.isEmpty()) {
                result = stack.pop();

            } else {

                System.out.println("\n ERROR");
            }
        } catch (IllegalArgumentException re) {
            System.out.println("\n Operation Error!");
        } catch (ArithmeticException ae) {
            System.out.println("\n Error! Division by zero");
        }
        catch (EmptyStackException ae) {
            System.out.println("\n Invalid value entered. Check it");
        }
        return result;
    }

    private void evaluate(double op1, double op2, String operator) {   //get last digits on the stack and operator
        switch (operator) {
            case "%":
                stack.push(op2 % op1);
                break;
            case "^":
                stack.push(Math.pow(op2, op1));
                break;
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

    private boolean isNumber(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }

    }

    private String checkUnary(String string) {  //check for unarity

        if (string.trim().charAt(0) == '-') {   // if first element is '-',then replace with "0-"
            string = "0-" + string.substring(1);
        }
        if (string.trim().charAt(string.length() - 1) == '=') {   // delete =
            string = string.substring(0, string.length() - 1);
        }

        Matcher matcherMinus = Pattern.compile("(-\\(-)(\\d+)(\\))").matcher(string); //if " - ( - digits)" replace with " + digits"
        String newString = matcherMinus.replaceAll("+$2");
        Matcher matcherMinus2 = Pattern.compile("(\\(-)(\\d+)").matcher(newString); //if " ( - digits" replace with "(0 - digits"
        newString = matcherMinus2.replaceAll("(0-$2");

        return newString;

    }

}
