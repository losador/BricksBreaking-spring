import consoleUI.ConsoleUI;
import core.Field;
import core.GameState;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to BricksBreaking game");
        System.out.println("Enter size of field (y,x)");
        int y = sc.nextInt(), x = sc.nextInt();
        sc = new Scanner(System.in);
        System.out.println("If you want to generate field randomly type 'g', if you want to load field from file type 'l'");
        String mode = sc.nextLine();

        StringBuilder path = new StringBuilder("src/main/java/");
        Field field;
        if(mode.equals("l")){
            System.out.println("Put your file in src/main/java and enter name of file");
            path.append(sc.nextLine());
            field = new Field(y, x, path.toString());
        } else{
            field = new Field(y, x);
        }
        ConsoleUI ui = new ConsoleUI(field);

        while(true){
            ui.play();
            if(field.isSolved()){
                System.out.println("Do you want to continue playing?(y/n)");
                String tmp = sc.nextLine();
                if(Objects.equals(tmp, "y")) field.generateTiles();
                else break;
            }
        }

    }
}
