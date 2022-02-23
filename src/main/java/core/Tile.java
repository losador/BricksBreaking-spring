package core;

public class Tile {

    private Color tileColor;
    private boolean isMarked;
    public final String ANSI_RESET = "\u001B[0m";
    public final String ANSI_RED = "\u001B[31m";
    public final String ANSI_YELLOW = "\u001B[33m";
    public final String ANSI_BLUE = "\u001B[34m";

    public Tile(Color color){
        this.tileColor = color;
        this.isMarked = false;
    }

    @Override
    public String toString() {
        String str = "";
//        switch(this.tileColor){
//            case RED: return "r";
//            case YELLOW: return "y";
//            case BLUE: return "b";
//            case NONE: return " ";
//            default: break;
//        }
        if(tileColor == Color.RED) str = ANSI_RED + "□" + ANSI_RESET;
        if(tileColor == Color.YELLOW) str = ANSI_YELLOW + "□" + ANSI_RESET;
        if(tileColor == Color.BLUE) str = ANSI_BLUE + "□" + ANSI_RESET;
        if(tileColor == Color.NONE) str = " ";
        if(this.isMarked) {
            str = str.toUpperCase();
        }
        return str;
    }

    public Color getTileColor() {
        return tileColor;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void mark() {
        isMarked = true;
    }

    public void unmark(){
        isMarked = false;
    }

    public void setTileColor(Color tileColor) {
        this.tileColor = tileColor;
    }


}
