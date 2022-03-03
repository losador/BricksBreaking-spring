package core;

import lombok.Data;

import java.io.*;
import java.util.Random;

@Data
public class Field {

    private final int ROWS;
    private final int COLUMNS;
    private long score;
    private Tile[][] fieldArray;
    private GameState state = GameState.PLAYING;
    private int singleDeleteCount;
    private int tilesToDelete;

    public Field(int rowCount, int columnCount){
        this.ROWS = rowCount;
        this.COLUMNS = columnCount;
        this.score = 0;
        this.fieldArray = new Tile[ROWS][COLUMNS];
        this.singleDeleteCount = 5;
        this.tilesToDelete = 0;
        generateTiles();
    }

    public Field(int rowCount, int columnCount, String path){
        this.ROWS = rowCount;
        this.COLUMNS = columnCount;
        this.score = 0;
        this.fieldArray = new Tile[ROWS][COLUMNS];
        this.singleDeleteCount = 5;
        this.tilesToDelete = 0;
        loadFieldFromFile(path);
    }

    /**
     * Generates random color tile on each position in array
     */
    public void generateTiles(){
        state = GameState.PLAYING;
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLUMNS; j++){
                this.fieldArray[i][j] = new Tile(getRandomColor());
            }
        }
    }

    public void loadFieldFromFile(String path){
        File file = new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            for(int i = 0; i < ROWS; i++){
                for(int j = 0; j < COLUMNS; j++){
                    char tmp = (char) br.read();
                    if(tmp == 'r') fieldArray[i][j] = new Tile(Color.RED);
                    else if(tmp == 'y') fieldArray[i][j] = new Tile(Color.YELLOW);
                    else if(tmp == 'b') fieldArray[i][j] = new Tile(Color.BLUE);
                    else if(tmp == ' ') fieldArray[i][j] = new Tile(Color.NONE);
                    else throw new IllegalArgumentException("Wrong color in file or wrong size of file");
                }
                br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateField();
        state = GameState.PLAYING;
    }

    /**
     * Deletes marked tiles from fieldArray
     */
    public void deleteTiles(){
        if(this.tilesToDelete == 1) {
            if(this.singleDeleteCount > 0) this.singleDeleteCount--;
            else{
                System.out.println("You are not able to delete one tile");
                return;
            }
        }
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLUMNS; j++){
                if(fieldArray[i][j].isMarked()) {
                    fieldArray[i][j].unmark();
                    fieldArray[i][j].setTileColor(Color.NONE);
                    this.score += 11;
                }
            }
        }
        this.tilesToDelete = 0;
    }

    /**
     * Marks tiles to delete. First tile is tile which user specifies
     * @param row y coordinate of first tile
     * @param column x coordinate of first tile
     */
    public void markTiles(int row, int column){
        if(row >= ROWS || column >= COLUMNS) {System.out.println("Wrong coordinates"); return;}
        fieldArray[row][column].mark();
        this.tilesToDelete++;
        if(row != 0 && fieldArray[row-1][column].getTileColor() == fieldArray[row][column].getTileColor() && !fieldArray[row-1][column].isMarked()) markTiles(row - 1, column);
        if(row != ROWS - 1 && fieldArray[row+1][column].getTileColor() == fieldArray[row][column].getTileColor() && !fieldArray[row+1][column].isMarked()) markTiles(row + 1, column);
        if(column != 0 && fieldArray[row][column-1].getTileColor() == fieldArray[row][column].getTileColor() && !fieldArray[row][column-1].isMarked()) markTiles(row, column - 1);
        if(column != COLUMNS - 1 && fieldArray[row][column+1].getTileColor() == fieldArray[row][column].getTileColor() && !fieldArray[row][column+1].isMarked()) markTiles(row, column + 1);
    }

    /**
     * Checks if level is solved
     * @return true or false
     */
    public boolean isSolved(){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLUMNS; j++) {
                if(fieldArray[i][j].getTileColor() == Color.BLUE || fieldArray[i][j].getTileColor() == Color.RED || fieldArray[i][j].getTileColor() == Color.YELLOW) return false;
            }
        }
        return true;
    }

    public void isFailed(){
        int count = 0;
        for(int i = 0; i < COLUMNS; i++){
            if(fieldArray[ROWS-1][i].getTileColor() != Color.NONE) count++;
        }
        if(count == 1 && singleDeleteCount == 0) this.state = GameState.FAILED;
    }

    public void updateField(){
        // Check rows
        for(int i = ROWS - 1; i > 0; i--){
            for(int j = 0; j < COLUMNS; j++){
                if(fieldArray[i][j].getTileColor() == Color.NONE){
                    checkUpperTile(i - 1, j);
                }
            }
        }
        // Check columns
        boolean clear;
        int columnsToDelete = checkForEmptyColumns() + 3;
        while(columnsToDelete != 0) {
            for (int j = 1; j < COLUMNS; j++) {
                clear = true;
                for (int i = 0; i < ROWS; i++) {
                    if (fieldArray[i][j].getTileColor() != Color.NONE) {
                        clear = false;
                        break;
                    }
                }
                if (clear) {
                    deleteColumn(j);
                }
            }
            columnsToDelete--;
        }
    }

    private int checkForEmptyColumns(){
        int count = 0;
        for(int j = 1; j < COLUMNS; j++){
            if(fieldArray[ROWS-1][j].getTileColor() == Color.NONE) count++;
        }
        return count;
    }

    private void deleteColumn(int column){
        for(int j = column + 1; j < COLUMNS; j++) {
            for(int i = ROWS-1; i >= 0; i--){
                if (fieldArray[i][j - 1].getTileColor() != Color.NONE || fieldArray[i][j].getTileColor() == Color.NONE) break;
                fieldArray[i][j - 1].setTileColor(fieldArray[i][j].getTileColor());
                fieldArray[i][j].setTileColor(Color.NONE);

            }
        }
    }

    private void checkUpperTile(int row, int column){
        if(fieldArray[row][column].getTileColor() == Color.NONE) return;
        for(int i = row; i < ROWS -1; i++){
            if(fieldArray[i+1][column].getTileColor() == Color.NONE){
                fieldArray[i+1][column].setTileColor(fieldArray[i][column].getTileColor());
                fieldArray[i][column].setTileColor(Color.NONE);
            }
        }
    }

    /**
     * Generates random color from Color enum
     * @return generated color
     */
    private Color getRandomColor(){
        Random rand = new Random();
        switch(rand.nextInt(3)){
            case 0: return Color.RED;
            case 1: return Color.YELLOW;
            case 2: return Color.BLUE;
            default: break;
        }
        return null;
    }
}
