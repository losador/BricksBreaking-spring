package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.tuke.gamestudio.game.consoleUI.ConsoleUI;
import sk.tuke.gamestudio.game.core.Field;
import sk.tuke.gamestudio.service.*;

@SpringBootApplication
@Configuration
public class SpringClient {

    public static void main(String[] args) {
        SpringApplication.run(SpringClient.class, args);
    }

    @Bean
    public CommandLineRunner runner(ConsoleUI ui) {
        return args -> ui.initializeField();
    }

    @Bean
    public ConsoleUI consoleUI() {
        return new ConsoleUI();
    }

    @Bean
    public Field field() {
        return new Field(6, 6);
    }

    @Bean
    public ScoreService scoreService() {
        return new ScoreServiceJPA();
    }

    @Bean
    public CommentService commentService() {
        return new CommentServiceJPA();
    }

    @Bean
    public RatingService ratingService() {
        return new RatingServiceJPA();
    }
}
