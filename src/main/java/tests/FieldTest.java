package tests;

import core.Color;
import core.Field;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FieldTest {
    private Random generator = new Random();
    private Field field;
    private int rowCount;
    private int columnCount;

    public FieldTest() {
        rowCount = generator.nextInt(5) + 5;
        columnCount = rowCount;
        field = new Field(rowCount, columnCount);
    }

    @Test
    public void checkForThreeColors(){
        List<Color> colors = new ArrayList<>();
        for(int i = 0; i < rowCount; i++){
            for(int j = 0; j < columnCount; j++){
                if(!colors.contains(field.getFieldArray()[i][j].getTileColor())) colors.add(field.getFieldArray()[i][j].getTileColor());
            }
        }
        int size = colors.size();
        assertEquals(3, size, "Different count of colors in the field");
    }

    @Test
    public void checkForRightMark(){
        field = new Field(6, 6, "src/main/java/field.txt");
        field.markTiles(1, 0);
        boolean isRight = true;
        if(!field.getFieldArray()[1][0].isMarked()) isRight = false;
        if(!field.getFieldArray()[1][1].isMarked()) isRight = false;
        if(!field.getFieldArray()[2][0].isMarked()) isRight = false;
        if(!field.getFieldArray()[2][1].isMarked()) isRight = false;
        assertTrue(isRight, "Tiles are marked incorrectly");
    }
}
