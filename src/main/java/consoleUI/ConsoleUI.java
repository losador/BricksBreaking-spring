package consoleUI;

import core.Field;
import core.GameState;
import core.LevelLoader;
import entity.Comment;
import entity.Rating;
import entity.Score;
import lombok.Data;
import service.*;


import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

@Data
public class ConsoleUI implements UserInterface {

    private static final String GAME_NAME = "BricksBreaking";
    private ScoreService scoreService = new ScoreServiceJDBC();
    private CommentService commentService = new CommentServiceJDBC();
    private RatingService ratingService = new RatingServiceJDBC();
    private Field field;
    private int rowCount;
    private int columnCount;
    private String name;

    public void initializeField(){
        Scanner sc = new Scanner(System.in);
        printStartText();
        String mode = getGeneratingMode();
        StringBuilder path = new StringBuilder("src/main/java/");
        if(mode.equals("l")){
            System.out.print("Put your file in src/main/java and enter name of file: ");
            path.append(sc.nextLine());
            field = new LevelLoader(rowCount, columnCount).loadFieldFromFile(path.toString());
        } else{
            field = new Field(rowCount, columnCount);
            field.generateTiles();
        }
        play();
    }

    private void printStartText(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to BricksBreaking game");
        printRules();

        System.out.print("Enter your name: ");
        name = sc.nextLine();

        System.out.print("Enter size of field (y x): ");
        rowCount = sc.nextInt();
        columnCount = sc.nextInt();
        while(rowCount < 0 && columnCount < 0){
            System.out.print("Wrong size, try again: ");
            rowCount = sc.nextInt();
            columnCount = sc.nextInt();
        }
    }

    private void printRules(){
        System.out.println("\nDestroy all the bricks by choosing them in groups of the same color. The more you get at once, the higher the score." +
                "\nThe only way to remove a single brick is to zap it with a magic wand.\n");
    }

    private String getGeneratingMode(){
        Scanner sc = new Scanner(System.in);
        System.out.print("If you want to generate field randomly type 'g', if you want to load field from file type 'l': ");
        String tmp = sc.nextLine();
        while(!Objects.equals(tmp, "g") && !Objects.equals(tmp, "l")){
            System.out.print("Wrong input, try again: ");
            tmp = sc.nextLine();
        }
        return tmp;
    }

    public void printField(){
        System.out.println("\nYour score: " + this.field.getScore());

        printMagicWands();
        printCoordinates();

        for(int i = 0; i < this.field.getROWS(); i++){
            for(int j = 0; j < this.field.getCOLUMNS(); j++) {
                if(j == field.getCOLUMNS() - 1) {
                    System.out.print(this.field.getFieldArray()[i][j].toString() + "  ");
                    break;
                }
                System.out.print(this.field.getFieldArray()[i][j].toString() + "   ");
            }
            System.out.println(i);
        }
    }

    private void printCoordinates() {
        for(int i = 0; i < this.field.getCOLUMNS(); i++){
            if(i < 9) System.out.print(i + "   ");
            if(i >= 9) System.out.print(i + "  ");
        }
        System.out.println();
    }

    private void printMagicWands(){
        for(int i = 0; i < field.getSingleDeleteCount(); i++){
            System.out.print("\uD83E\uDE84 ");
        }
        System.out.println();
    }

    public void play(){
        while(field.getState() != GameState.PLAYING) printCommands();
        do{
            printField();
            handleInput();
            if(field.isSolved()){
                field.setScore(field.getScore() + 500);
                printField();
                scoreService.addScore(new Score(GAME_NAME, name, field.getScore(), new Date()));
                stop();
            }
            field.isFailed();
        } while(field.getState() == GameState.PLAYING);
        if(field.getState() == GameState.FAILED) failed();
        rate();
        comment();
    }

    private void failed(){
        printField();
        Scanner sc = new Scanner(System.in);
        System.out.println("You lose! Do you want to try again(y/n)?");
        String tmp = sc.nextLine();
        if(tmp.equals("y")) play();
    }

    private void comment(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to leave a comment(y/n)?");
        String tmp = sc.nextLine();
        if(tmp.equals("y")){
            System.out.print("Write your comment here(max. 64 letters): ");
            String comment = sc.nextLine();
            commentService.addComment(new Comment(GAME_NAME, name, comment, new Date()));
        }
    }

    private void rate(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to rate the game(y/n)?");
        String tmp = sc.nextLine();
        if(tmp.equals("y")){
            System.out.print("Enter your rating(1-5): ");
            int rating  = sc.nextInt();
            while(rating < 1 || rating > 5) {
                System.out.println("Invalid rating, try again");
                rating = sc.nextInt();
            }
            ratingService.setRating(new Rating(GAME_NAME, name, rating, new Date()));
        }
    }

    public void handleInput(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter coordinates of tile you want to delete(y x): ");
        int row = sc.nextInt(), column = sc.nextInt();
        if((row >= rowCount || row < 0) || (column >= columnCount || column < 0)) {
            System.out.println("Wrong coordinates");
            return;
        }
        field.markTiles(row, column);
        field.deleteTiles();
        field.updateField();
    }

    private void stop(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to continue playing(y/n)?");
        String tmp = sc.nextLine();
        if(tmp.equals("n")) {
            field.setState(GameState.STOPPED);
        } else{
            field.generateTiles();
        }
    }

    private void printCommands(){
        System.out.println("\nEnter 's' to get list of top scores");
        System.out.println("Enter 'a' to add comment");
        System.out.println("Enter 'g' to get list of comments");
        System.out.println("Enter 'r' to rate the game");
        System.out.println("Enter 'ga' to get average rating");
        System.out.println("Enter 'gr' to get rate by user name");
        System.out.println("Enter 'rs' to reset the score table");
        System.out.println("Enter 'rr' to reset rating table");
        System.out.println("Enter 'rc' to reset comment table");
        System.out.println("Enter 'p' to play the game");
        parseCommands();
    }

    private void parseCommands(){
        Scanner sc = new Scanner(System.in);
        System.out.print("\nYour input: ");
        String command = sc.nextLine();
        switch(command){
            case "s":
                printTopScores();
                break;
            case "a":
                System.out.print("Enter your comment(max. 64 letters): ");
                String tmp = sc.nextLine();
                commentService.addComment(new Comment("BricksBreaking", name, tmp, new Date()));
                break;
            case "g":
                printComments();
                break;
            case "r":
                System.out.print("Enter your rate(1-5): ");
                int rateTmp = sc.nextInt();
                ratingService.setRating(new Rating("BricksBreaking", name, rateTmp, new Date()));
                break;
            case "ga":
                int avg = ratingService.getAverageRating("BricksBreaking");
                System.out.println("Average rating from game BricksBreaking is: " + avg);
                break;
            case "gr":
                System.out.print("Enter name of user: ");
                String username = sc.nextLine();
                int rate = ratingService.getRating("BricksBreaking", username);
                System.out.println(username + " rated " + rate);
                break;
            case "rs":
                scoreService.reset();
                break;
            case "rr":
                ratingService.reset();
                break;
            case "rc":
                commentService.reset();
                break;
            case "p":
                field.setState(GameState.PLAYING);
                break;
            default:
                break;
        }
    }

    private void printTopScores() {
        var scores = scoreService.getTopScores(GAME_NAME);
        System.out.println("---------------------------------------------------------------");
        for (int i = 0; i < scores.size(); i++) {
            var score = scores.get(i);
            System.out.printf("%d. %s: %d\n", i + 1, score.getPlayer(), score.getPoints());
        }
        System.out.println("---------------------------------------------------------------");
    }

    private void printComments(){
        var comments = commentService.getComments(GAME_NAME);
        System.out.println("---------------------------------------------------------------");
        comments.forEach(e -> System.out.printf("%s: %s\n", e.getPlayer(), e.getComment()));
        System.out.println("---------------------------------------------------------------");
    }

}
