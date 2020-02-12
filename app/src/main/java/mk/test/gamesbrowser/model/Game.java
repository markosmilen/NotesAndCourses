package mk.test.gamesbrowser.model;

import java.util.ArrayList;

public class Game {

    private int id;
    private Cover cover;
    private int first_release_date;
    private String name;
    private ArrayList<GamePhrase> game_modes;
    private ArrayList<GamePhrase> genres;
    private ArrayList<GamePhrase> player_perspectives;
    private ArrayList<GamePhrase> themes;
    private ArrayList<Platform> platforms;
    private double popularity;
    private double rating;
    private double rating_count;
    private ArrayList<GameImage> screenshots;
    private ArrayList<Game> similar_games;
    private String storyline;
    private ArrayList<GameVideo> videos;
    private TimeToBeat time_to_beat;

    public TimeToBeat getTime_to_beat() {
        return time_to_beat;
    }

    public void setTime_to_beat(TimeToBeat time_to_beat) {
        this.time_to_beat = time_to_beat;
    }

    public Game() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    public int getFirst_release_date() {
        return first_release_date;
    }

    public void setFirst_release_date(int first_release_date) {
        this.first_release_date = first_release_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<GamePhrase> getGame_modes() {
        return game_modes;
    }

    public void setGame_modes(ArrayList<GamePhrase> game_modes) {
        this.game_modes = game_modes;
    }

    public ArrayList<GamePhrase> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<GamePhrase> genres) {
        this.genres = genres;
    }

    public ArrayList<GamePhrase> getPlayer_perspectives() {
        return player_perspectives;
    }

    public void setPlayer_perspectives(ArrayList<GamePhrase> player_perspectives) {
        this.player_perspectives = player_perspectives;
    }

    public ArrayList<GamePhrase> getThemes() {
        return themes;
    }

    public void setThemes(ArrayList<GamePhrase> themes) {
        this.themes = themes;
    }

    public ArrayList<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(ArrayList<Platform> platforms) {
        this.platforms = platforms;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getRating_count() {
        return rating_count;
    }

    public void setRating_count(double rating_count) {
        this.rating_count = rating_count;
    }

    public ArrayList<GameImage> getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(ArrayList<GameImage> screenshots) {
        this.screenshots = screenshots;
    }

    public ArrayList<Game> getSimilar_games() {
        return similar_games;
    }

    public void setSimilar_games(ArrayList<Game> similar_games) {
        this.similar_games = similar_games;
    }

    public String getStoryline() {
        return storyline;
    }

    public void setStoryline(String storyline) {
        this.storyline = storyline;
    }

    public ArrayList<GameVideo> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<GameVideo> videos) {
        this.videos = videos;
    }
}
