package core;

public class Tile {

    private Color tileColor;
    private boolean isMarked;

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
        if(tileColor == Color.RED) str = "r";
        if(tileColor == Color.YELLOW) str = "y";
        if(tileColor == Color.BLUE) str = "b";
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
