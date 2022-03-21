package tests;

import entity.Rating;
import entity.Score;
import org.junit.jupiter.api.Test;
import service.*;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JDBCtest {

    private ScoreService scoreService;
    private CommentService commentService;
    private RatingService ratingService;

    public JDBCtest(){
        scoreService = new ScoreServiceJDBC();
        commentService = new CommentServiceJDBC();
        ratingService = new RatingServiceJDBC();
    }

    @Test
    public void addScoreTest(){
        Score score = new Score("BricksBreaking", "lama", 99999, new Date());
        scoreService.addScore(score);
        List<Score> scores = scoreService.getTopScores("BricksBreaking");

        assertTrue(scores.size() > 0);
        assertEquals("lama", scores.get(0).getPlayer());
        assertEquals(99999, scores.get(0).getPoints());
        assertEquals("BricksBreaking", scores.get(0).getGame());
    }

    @Test
    public void addRatingTest(){
        Rating rating = new Rating("BricksBreaking", "lama", 4, new Date());
        ratingService.setRating(rating);
        int rate = ratingService.getRating("BricksBreaking", "lama");
        assertEquals(4, rate);
    }

}
