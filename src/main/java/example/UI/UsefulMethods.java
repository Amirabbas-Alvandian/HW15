package example.UI;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UsefulMethods {
    private final Scanner scanner = new Scanner(System.in);


    public Long scanLong (){
        String temp;
        Long tempLong = null;
        while (tempLong == null){
            try {
                temp= scanner.nextLine();
                tempLong = Long.parseLong(temp);
            }catch (InputMismatchException | NumberFormatException n){
                System.out.println(n.getMessage());
                System.out.println("Enter number only");
            }
        }
        return tempLong;
    }

    public Integer scanInt (){
        String temp;
        Integer integer = null ;
        while (integer == null){
            try {
                temp = scanner.nextLine();
                integer = Integer.parseInt(temp);
            }catch (InputMismatchException | NumberFormatException n){
                System.out.println(n.getMessage());
            }
        }
        return integer;
    }

    public Double scanDouble (){
        String temp;
        Double score = null ;
        while (score == null){
            try {
                temp = scanner.nextLine();
                score = Double.parseDouble(temp);
            }catch (InputMismatchException | NumberFormatException n){
                System.out.println(n.getMessage());
            }
        }
        return score;
    }

}
