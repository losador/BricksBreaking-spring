package core;

import entity.Comment;
import entity.Rating;
import entity.Score;
import org.junit.jupiter.api.Test;
import service.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    @Test
    public void getAverageRatingTest(){
        ratingService.reset();

        Rating[] ratings = { new Rating("BricksBreaking", "lama", 4, new Date()), new Rating("BricksBreaking", "lama", 2, new Date())};
        for(Rating r : ratings){
            ratingService.setRating(r);
        }

        int avg = ratingService.getAverageRating("BricksBreaking");
        assertEquals(3, avg);
    }

    @Test
    public void getRatingTest(){
        ratingService.reset();

        ratingService.setRating(new Rating("BricksBreaking", "lama", 4, new Date()));

        int avg = ratingService.getRating("BricksBreaking", "lama");
        assertEquals(4, avg);
    }

    @Test
    public void addCommentTest(){
        Comment comment = new Comment("BricksBreaking", "lama", "nice game!", new Date());
        commentService.addComment(comment);
        List<Comment> comments = commentService.getComments("BricksBreaking");

        boolean isRight = false;
        for(Comment c : comments){
            if (Objects.equals(c.getComment(), "nice game!") && Objects.equals(c.getPlayer(), "lama")) {
                isRight = true;
                break;
            }
        }

        assertTrue(isRight);
    }

    @Test
    public void getCommentsTest(){
        commentService.reset();

        Comment[] comments = {new Comment("BricksBreaking", "lama", "nice", new Date()), new Comment("BricksBreaking", "lox", "shit", new Date())};
        for(Comment c : comments) {
            commentService.addComment(c);
        }

        List<Comment> coms = commentService.getComments("BricksBreaking");
        assertEquals("lama", coms.get(0).getPlayer());
        assertEquals("nice", coms.get(0).getComment());
        assertEquals("lox", coms.get(1).getPlayer());
        assertEquals("shit", coms.get(1).getComment());
    }

}
