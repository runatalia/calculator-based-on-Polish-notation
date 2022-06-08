package SimpleCalculator;

//// покрытғ junit test
////сделать проверку чтобы колво цмфр  соответствовало колличеству операторов и скобок
////если не ввести операторы,то не выдает ошибку
////добавить остаток от деления
//input и output


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Calculation calculation ;
        // String expression = sc.nextLine();   // get expression from file
        try {
            calculation = new Calculation(new InputExpression().input());
            System.out.println(" = "+Math.round(calculation.reverseToPolishNotation()));
        } 
        catch(IOException ioe){
            System.out.println("check your file, it might be empty");
        } finally {
            sc.close();
        }

    }

}
