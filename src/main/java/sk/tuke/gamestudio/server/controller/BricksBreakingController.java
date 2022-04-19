package sk.tuke.gamestudio.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.game.core.Color;
import sk.tuke.gamestudio.game.core.Field;
import sk.tuke.gamestudio.game.core.GameState;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/bricks")
public class BricksBreakingController{

    private Field field;

    @RequestMapping("/init")
    public String init(){
        return "init";
    }

    @RequestMapping("/create")
    public String initializeField(@RequestParam int y, @RequestParam int x){
        field = new Field(y, x);
        return "bricks";
    }

    @RequestMapping
    public String bricks(@RequestParam(required = false) Integer row, @RequestParam(required = false) Integer column){
        if(row != null && column != null && field.getState() != GameState.FAILED) {
            field.markTiles(row, column);
            field.deleteTiles();
            field.updateField();
            field.isFailed();
            if(field.isSolved()){
                field.generateTiles();
                //services
            }
            if(field.getState() == GameState.FAILED){
                return "failed";
            }
        }
        return "bricks";
    }

    @RequestMapping("/new")
    public String newGame(){
        field = new Field(12, 12);
        return "bricks";
    }

    public String getHtmlField() {
        StringBuilder sb = new StringBuilder();
        sb.append("<table>\n");
        for(int i = 0; i < field.getROWS(); i++){
            sb.append("<tr>\n");
            for(int j = 0; j < field.getCOLUMNS(); j++){
                sb.append("<td>\n");
                Color color = field.getFieldArray()[i][j].getTileColor();
                switch (color){
                    case RED:
                        sb.append("<a href='/bricks?row=" + i + "&column=" + j + "'>\n");
                        sb.append("<img src='/images/redTile.png'>\n");
                        break;
                    case YELLOW:
                        sb.append("<a href='/bricks?row=" + i + "&column=" + j + "'>\n");
                        sb.append("<img src='/images/yellowTile.png'>\n");
                        break;
                    case BLUE:
                        sb.append("<a href='/bricks?row=" + i + "&column=" + j + "'>\n");
                        sb.append("<img src='/images/blueTile.png'>\n");
                        break;
                    case NONE:
                        sb.append("<img src='/images/noneTile.png'>\n");
                }
                sb.append("</a>\n");
                sb.append("</td>\n");
            }
            sb.append("</tr>\n");
        }
        sb.append("</table>\n");
        return sb.toString();
    }

    public String getScore(){
        return String.valueOf(field.getScore());
    }

    public String getWands(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < field.getSingleDeleteCount(); i++){
            sb.append("<img src='/images/bulb.png'>\n");
        }
        return sb.toString();
    }
}
