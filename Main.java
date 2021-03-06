package SimpleCalculator;

//// покрытғ junit test
////сделать проверку чтобы колво цмфр  соответствовало колличеству операторов и скобок
////если не ввести операторы,то не выдает ошибку
//вывод ошибок в  output


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Calculation calculation ;
        OutputSolution outputsolution =null;
        InputExpression inputExpression;
        List<String> listString;
        
        try {
            inputExpression = new InputExpression();
            listString = new ArrayList<>(Arrays.asList(inputExpression.input().split("[\\r\\n;=]")));
            for(int i=0;i<listString.size();i++){
                if(!listString.get(i).isEmpty()){
             calculation = new Calculation(listString.get(i));
             
             outputsolution =new OutputSolution(listString.get(i) + " = "+calculation.getResult());
            }}
            
        } 
        catch(IOException ioe){
            System.out.println("check your file, it might be empty");
        } finally {
            sc.close();
        }

    }
    

}
