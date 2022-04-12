package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

import java.sql.*;


public class RatingServiceJDBC implements RatingService {
    public static final String URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String USER = "postgres";
    public static final String PASSWORD = "postgres";
    public static final String SELECT = "SELECT rate FROM rating WHERE game = ? AND player = ?";
    public static final String AVG = "SELECT AVG(rate) FROM rating WHERE game = 'BricksBreaking';";
    public static final String DELETE = "DELETE FROM rating";
    public static final String INSERT = "INSERT INTO rating (game, player, rate, ratedOn) VALUES (?, ?, ?, ?)";

    @Override
    public void setRating(Rating rating) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(INSERT)
        ) {
            statement.setString(1, rating.getGame());
            statement.setString(2, rating.getPlayer());
            statement.setInt(3, rating.getRate());
            statement.setTimestamp(4, new Timestamp(rating.getRatedOn().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RatingException("Problem inserting rating", e);
        }
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
        ) {
            try(ResultSet rs = statement.executeQuery(AVG)) {
                rs.next();
                return rs.getInt("avg");
            }
        } catch (SQLException e) {
            throw new RatingException("Problem selecting average rating", e);
        }
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT)
        ) {
            statement.setString(1, game);
            statement.setString(2, player);
            try(ResultSet rs = statement.executeQuery()) {
                rs.next();
                return rs.getInt("rate");
            }
        } catch (SQLException e) {
            throw new RatingException("Problem selecting rating", e);
        }
    }

    @Override
    public void reset() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            throw new RatingException("Problem deleting rating", e);
        }
    }
}
