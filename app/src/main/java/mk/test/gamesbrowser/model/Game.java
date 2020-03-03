package mk.test.gamesbrowser.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

import java.util.ArrayList;

public class Game extends SugarRecord implements Parcelable {

    //private int id;
    private Cover cover;
    private int first_release_date;
    private String name;
    private ArrayList<InvolvedCompany> involved_companies;
    private ArrayList<GamePhrase> game_modes;
    private ArrayList<GamePhrase> genres;
    private ArrayList<GamePhrase> player_perspectives;
    private ArrayList<GamePhrase> themes;
    private ArrayList<GamePhrase> game_engines;
    private ArrayList<Platform> platforms;
    private double popularity;
    private double rating;
    private double rating_count;
    private ArrayList<GameImage> screenshots;
    private ArrayList<Game> similar_games;
    private String storyline;
    private String summary;
    private ArrayList<GameVideo> videos;
    private TimeToBeat time_to_beat;

    public Game () {}

    protected Game(Parcel in) {
        //id = in.readInt();
        cover = in.readParcelable(Cover.class.getClassLoader());
        first_release_date = in.readInt();
        name = in.readString();
        involved_companies = in.createTypedArrayList(InvolvedCompany.CREATOR);
        game_modes = in.createTypedArrayList(GamePhrase.CREATOR);
        genres = in.createTypedArrayList(GamePhrase.CREATOR);
        player_perspectives = in.createTypedArrayList(GamePhrase.CREATOR);
        themes = in.createTypedArrayList(GamePhrase.CREATOR);
        game_engines = in.createTypedArrayList(GamePhrase.CREATOR);
        platforms = in.createTypedArrayList(Platform.CREATOR);
        popularity = in.readDouble();
        rating = in.readDouble();
        rating_count = in.readDouble();
        screenshots = in.createTypedArrayList(GameImage.CREATOR);
        similar_games = in.createTypedArrayList(Game.CREATOR);
        storyline = in.readString();
        summary = in.readString();
        videos = in.createTypedArrayList(GameVideo.CREATOR);
        time_to_beat = in.readParcelable(TimeToBeat.class.getClassLoader());
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    /*public int getGameId() {
        return id;
    }

    public void setGameId(int id) {
        this.id = id;
    }*/

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

    public ArrayList<InvolvedCompany> getInvolved_companies() {
        return involved_companies;
    }

    public void setInvolved_companies(ArrayList<InvolvedCompany> involved_companies) {
        this.involved_companies = involved_companies;
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

    public ArrayList<GamePhrase> getGame_engines() {
        return game_engines;
    }

    public void setGame_engines(ArrayList<GamePhrase> game_engines) {
        this.game_engines = game_engines;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public ArrayList<GameVideo> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<GameVideo> videos) {
        this.videos = videos;
    }

    public TimeToBeat getTime_to_beat() {
        return time_to_beat;
    }

    public void setTime_to_beat(TimeToBeat time_to_beat) {
        this.time_to_beat = time_to_beat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        //parcel.writeInt(id);
        parcel.writeParcelable(cover, i);
        parcel.writeInt(first_release_date);
        parcel.writeString(name);
        parcel.writeTypedList(involved_companies);
        parcel.writeTypedList(game_modes);
        parcel.writeTypedList(genres);
        parcel.writeTypedList(player_perspectives);
        parcel.writeTypedList(themes);
        parcel.writeTypedList(game_engines);
        parcel.writeTypedList(platforms);
        parcel.writeDouble(popularity);
        parcel.writeDouble(rating);
        parcel.writeDouble(rating_count);
        parcel.writeTypedList(screenshots);
        parcel.writeTypedList(similar_games);
        parcel.writeString(storyline);
        parcel.writeString(summary);
        parcel.writeTypedList(videos);
        parcel.writeParcelable(time_to_beat, i);
    }
}
