import core.Field;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Field field = new Field(6, 6);
        Scanner sc = new Scanner(System.in);
        boolean isSolved;
        while(true){
            field.printField();
            System.out.println("Enter tile coordinates you want to delete: ");
            int row = sc.nextInt(), column = sc.nextInt();
            field.markTiles(row, column);
            field.deleteTiles();
            field.updateField();
            if(field.isSolved()){
                field.generateTiles();
            }
        }

    }
}
