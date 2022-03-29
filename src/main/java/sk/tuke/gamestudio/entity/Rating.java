package sk.tuke.gamestudio.entity;


import lombok.Data;

import java.util.Date;

@Data
public class Rating {
    private String game;
    private String player;
    private int rating;
    private Date ratedOn;

    public Rating(String game, String player, int rating, Date ratedOn) {
        this.game = game;
        this.player = player;
        this.rating = rating;
        this.ratedOn = ratedOn;
    }

    @Override
    public String toString() {
        return  "Game: " + game +
                ", Player: " + player +
                ", Rating: " + rating +
                ", Rated on: " + ratedOn +
                '}';
    }
}
