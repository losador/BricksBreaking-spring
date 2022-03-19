package sk.tuke.gamestudio.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import java.util.Date;

@Entity
@NamedQuery( name = "Comment.getComments",
        query = "SELECT c FROM Comment c WHERE c.game=:game")
@NamedQuery( name = "Comment.resetComments",
        query = "DELETE FROM Comment ")
@Data
public class Comment {
    @Id
    @GeneratedValue
    private int ident;
    private String game;
    private String player;
    private String comment;
    private Date commentedOn;

    public Comment() {}

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
