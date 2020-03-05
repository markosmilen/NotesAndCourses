package mk.test.gamesbrowser.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Review implements Parcelable {

    private String conclusion, content;
    private double created_at;
    private int game;
    private int likes, views;
    private String title, introduction;
    private String positive_points, negative_points;

    public Review() {}

    protected Review(Parcel in) {
        conclusion = in.readString();
        content = in.readString();
        created_at = in.readDouble();
        game = in.readInt();
        likes = in.readInt();
        views = in.readInt();
        title = in.readString();
        introduction = in.readString();
        positive_points = in.readString();
        negative_points = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getCreated_at() {
        return created_at;
    }

    public void setCreated_at(double created_at) {
        this.created_at = created_at;
    }

    public int getGame() {
        return game;
    }

    public void setGame(int game) {
        this.game = game;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getPositive_points() {
        return positive_points;
    }

    public void setPositive_points(String positive_points) {
        this.positive_points = positive_points;
    }

    public String getNegative_points() {
        return negative_points;
    }

    public void setNegative_points(String negative_points) {
        this.negative_points = negative_points;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(conclusion);
        parcel.writeString(content);
        parcel.writeDouble(created_at);
        parcel.writeInt(game);
        parcel.writeInt(likes);
        parcel.writeInt(views);
        parcel.writeString(title);
        parcel.writeString(introduction);
        parcel.writeString(positive_points);
        parcel.writeString(negative_points);
    }
}
