package mk.test.gamesbrowser.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "game_table")
public class Game implements Parcelable {

    @PrimaryKey
    @ColumnInfo(name = "game_id")
    private int id;
    @ColumnInfo
    private Cover cover;
    @ColumnInfo
    private int first_release_date;
    @ColumnInfo(name = "game_name")
    private String name;
    @ColumnInfo
    private ArrayList<InvolvedCompany> involved_companies;
    @ColumnInfo
    private ArrayList<GamePhrase> game_modes;
    @ColumnInfo
    private ArrayList<GamePhrase> genres;
    @ColumnInfo
    private ArrayList<GamePhrase> player_perspectives;
    @ColumnInfo
    private ArrayList<GamePhrase> themes;
    @ColumnInfo
    private ArrayList<Platform> platforms;
    @ColumnInfo
    private double popularity;
    @ColumnInfo
    private double rating;
    @ColumnInfo
    private double rating_count;
    @ColumnInfo
    private ArrayList<GameImage> screenshots, artworks;
    @ColumnInfo
    private ArrayList<Game> similar_games;
    @ColumnInfo
    private ArrayList<Game> expansions;
    @ColumnInfo
    private String storyline;
    @ColumnInfo
    private String summary;
    @ColumnInfo
    private ArrayList<GameVideo> videos;
    @ColumnInfo
    private TimeToBeat time_to_beat;
    @ColumnInfo
    private boolean isWanted;
    @ColumnInfo
    private boolean isPlaying;
    @ColumnInfo
    private boolean isPlayed;
    @ColumnInfo
    private boolean isVisited;

    public Game () {}

    protected Game(Parcel in) {
        id = in.readInt();
        cover = in.readParcelable(Cover.class.getClassLoader());
        first_release_date = in.readInt();
        name = in.readString();
        involved_companies = in.createTypedArrayList(InvolvedCompany.CREATOR);
        game_modes = in.createTypedArrayList(GamePhrase.CREATOR);
        genres = in.createTypedArrayList(GamePhrase.CREATOR);
        player_perspectives = in.createTypedArrayList(GamePhrase.CREATOR);
        themes = in.createTypedArrayList(GamePhrase.CREATOR);
        platforms = in.createTypedArrayList(Platform.CREATOR);
        popularity = in.readDouble();
        rating = in.readDouble();
        rating_count = in.readDouble();
        screenshots = in.createTypedArrayList(GameImage.CREATOR);
        artworks = in.createTypedArrayList(GameImage.CREATOR);
        similar_games = in.createTypedArrayList(Game.CREATOR);
        expansions = in.createTypedArrayList(Game.CREATOR);
        storyline = in.readString();
        summary = in.readString();
        videos = in.createTypedArrayList(GameVideo.CREATOR);
        time_to_beat = in.readParcelable(TimeToBeat.class.getClassLoader());
        isWanted = in.readByte() != 0;
        isPlaying = in.readByte() != 0;
        isPlayed = in.readByte() != 0;
        isVisited = in.readByte() != 0;
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

    public ArrayList<GameImage> getArtworks() {
        return artworks;
    }

    public void setArtworks(ArrayList<GameImage> artworks) {
        this.artworks = artworks;
    }

    public ArrayList<Game> getSimilar_games() {
        return similar_games;
    }

    public void setSimilar_games(ArrayList<Game> similar_games) {
        this.similar_games = similar_games;
    }

    public ArrayList<Game> getExpansions() {
        return expansions;
    }

    public void setExpansions(ArrayList<Game> expansions) {
        this.expansions = expansions;
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

    public boolean isWanted() {
        return isWanted;
    }

    public void setWanted(boolean wanted) {
        isWanted = wanted;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public boolean isPlayed() {
        return isPlayed;
    }

    public void setPlayed(boolean played) {
        isPlayed = played;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeParcelable(cover, i);
        parcel.writeInt(first_release_date);
        parcel.writeString(name);
        parcel.writeTypedList(involved_companies);
        parcel.writeTypedList(game_modes);
        parcel.writeTypedList(genres);
        parcel.writeTypedList(player_perspectives);
        parcel.writeTypedList(themes);
        parcel.writeTypedList(platforms);
        parcel.writeDouble(popularity);
        parcel.writeDouble(rating);
        parcel.writeDouble(rating_count);
        parcel.writeTypedList(screenshots);
        parcel.writeTypedList(artworks);
        parcel.writeTypedList(similar_games);
        parcel.writeTypedList(expansions);
        parcel.writeString(storyline);
        parcel.writeString(summary);
        parcel.writeTypedList(videos);
        parcel.writeParcelable(time_to_beat, i);
        parcel.writeByte((byte) (isWanted ? 1 : 0));
        parcel.writeByte((byte) (isPlaying ? 1 : 0));
        parcel.writeByte((byte) (isPlayed ? 1 : 0));
        parcel.writeByte((byte) (isVisited ? 1 : 0));
    }
}
