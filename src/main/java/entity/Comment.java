package entity;


import lombok.Data;

import java.util.Date;

@Data
public class Comment {

    private String game;
    private String player;
    private String comment;
    private Date commentedOn;

    public Comment(String game, String player, String comment, Date commentedOn) {
        this.game = game;
        this.player = player;
        this.comment = comment;
        this.commentedOn = commentedOn;
    }

    @Override
    public String toString() {
        return  "Game: " + game +
                ", Player: " + player +
                ", Comment: " + comment +
                ", Commented on: " + commentedOn;
    }
}
