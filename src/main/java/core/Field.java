package core;

import java.util.Random;

public class Field {

    private final int ROWS;
    private final int COLUMNS;
    private int realLength;
    private Tile[][] fieldArray;
    private GameState state = GameState.PLAYING;

    public Field(int rowCount, int columnCount){
        this.ROWS = rowCount;
        this.COLUMNS = columnCount;
        realLength = rowCount;
        this.fieldArray = new Tile[ROWS][COLUMNS];
        generateTiles();
    }

    /**
     * Generates random color tile on each position in array
     */
    public void generateTiles(){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLUMNS; j++){
                this.fieldArray[i][j] = new Tile(getRandomColor());
            }
        }
    }

    public void printField(){
        for(int i = 0; i < COLUMNS; i++){
            System.out.print(i + "    ");
        }
        System.out.println();

        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLUMNS; j++) {
                System.out.print(fieldArray[i][j].toString() + "    ");
            }
            System.out.print(i);
            System.out.println();
        }
    }

    /**
     * Deletes marked tiles from fieldArray
     */
    public void deleteTiles(){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLUMNS; j++){
                if(fieldArray[i][j].isMarked()) fieldArray[i][j].setTileColor(Color.NONE);
            }
        }
    }

    /**
     * Marks tiles to delete. First tile is tile which user specifies
     * @param row y coordinate of first tile
     * @param column x coordinate of first tile
     */
    public void markTiles(int row, int column){
        if(row >= ROWS || column >= COLUMNS) {System.out.println("Wrong coordinates"); return;}
        fieldArray[row][column].mark();
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
        while(checkForEmptyColumn()) {
            for (int j = 1; j < COLUMNS - 1; j++) {
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
        }
    }

    private boolean checkForEmptyColumn(){
        for(int j = 1; j < realLength; j++){
            if(fieldArray[ROWS-1][j].getTileColor() == Color.NONE) return true;
        }
        return false;
    }

    private void deleteColumn(int column){
        this.realLength--;
        for(int i = 0; i < ROWS; i++){
            for(int j = column + 1; j < COLUMNS - 1; j++) {
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

    public Tile[][] getFieldArray() {
        return fieldArray;
    }

    public void setFieldArray(Tile[][] fieldArray) {
        this.fieldArray = fieldArray;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }
}
