package consoleUI;

import core.Field;
import core.GameState;
import entity.Rating;
import entity.Score;
import lombok.Data;
import service.RatingServiceJDBC;
import service.ScoreServiceJDBC;

import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

@Data
public class ConsoleUI {

    private Field field;
    private int rowCount;
    private int columnCount;
    String name;

    public void printField(){
        System.out.println("Your score: " + this.field.getScore());

        printStars();
        printCoordinates();

        for(int i = 0; i < this.field.getROWS(); i++){
            for(int j = 0; j < this.field.getCOLUMNS(); j++) {
                System.out.print(this.field.getFieldArray()[i][j].toString() + "    ");
            }
            System.out.print(i);
            System.out.println();
        }
    }

    private void printCoordinates() {
        for(int i = 0; i < this.field.getCOLUMNS(); i++){
            if(i < 9) System.out.print(i + "    ");
            if(i >= 9) System.out.print(i + "   ");
        }
        System.out.println();
    }

    private void printStars(){
        for(int i = 0; i < field.getSingleDeleteCount(); i++){
            System.out.print("* ");
        }
        System.out.println();
    }

    public void play(){
        do{
            printField();
            handleInput();
            if(field.isSolved()){
                field.setScore(field.getScore() + 500);
                field.generateTiles();
                new ScoreServiceJDBC().addScore(new Score("BricksBreaking", name, field.getScore(), new Date()));
                stop();
            }
            field.isFailed();
        } while(field.getState() == GameState.PLAYING);
        rate();
    }

    private void rate(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to rate the game(y,n)?");
        String tmp = sc.nextLine();
        if(Objects.equals(tmp, "y")){
            System.out.println("Enter your rating(1-5)");
            int rating  = sc.nextInt();
            while(rating < 1 || rating > 5) {
                System.out.println("Invalid rating, try again");
                rating = sc.nextInt();
            }
            new RatingServiceJDBC().setRating(new Rating("BricksBreaking", name, rating, new Date()));
        }
    }

    public void handleInput(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter tile coordinates of tile you want to delete(y,x): ");
        int row = sc.nextInt(), column = sc.nextInt();
        field.markTiles(row, column);
        field.deleteTiles();
        field.updateField();
    }

    private void stop(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to stop playing(y,n)?");
        String tmp = sc.nextLine();
        if(tmp.equals("y")) field.setState(GameState.STOPPED);
    }

    private void startText(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to BricksBreaking game");
        System.out.println("Enter your name: ");
        name = sc.nextLine();
        System.out.println("Enter size of field (y,x)");
        rowCount = sc.nextInt();
        columnCount = sc.nextInt();
    }

    public void initializeField(){
        Scanner sc = new Scanner(System.in);
        startText();
        String mode = getGeneratingMode();
        StringBuilder path = new StringBuilder("src/main/java/");
        if(mode.equals("l")){
            System.out.println("Put your file in src/main/java and enter name of file");
            path.append(sc.nextLine());
            field = new Field(rowCount, columnCount, path.toString());
        } else{
            field = new Field(rowCount, columnCount);
        }
    }

    private String getGeneratingMode(){
        Scanner sc = new Scanner(System.in);
        System.out.println("If you want to generate field randomly type 'g', if you want to load field from file type 'l'");
        return sc.nextLine();
    }

}
