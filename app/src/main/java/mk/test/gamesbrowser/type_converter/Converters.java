package mk.test.gamesbrowser.type_converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import mk.test.gamesbrowser.model.Cover;
import mk.test.gamesbrowser.model.Game;
import mk.test.gamesbrowser.model.GameImage;
import mk.test.gamesbrowser.model.GamePhrase;
import mk.test.gamesbrowser.model.GameVideo;
import mk.test.gamesbrowser.model.InvolvedCompany;
import mk.test.gamesbrowser.model.Platform;
import mk.test.gamesbrowser.model.TimeToBeat;

public class Converters {

    //COVER CONVERTER
    @TypeConverter
    public static Cover coverConverter(String value){
        Type listType = new TypeToken<Cover>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromCover(Cover cover){
        Gson gson = new Gson();
        return gson.toJson(cover);
    }

    //INVOLVED COMPANIES CONVERTER
    @TypeConverter
    public static ArrayList<InvolvedCompany> companiesConverter(String value){
        Type listType = new TypeToken<ArrayList<InvolvedCompany>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromCompanies(ArrayList<InvolvedCompany> companiesConverter){
        Gson gson = new Gson();
        return gson.toJson(companiesConverter);
    }

    //GAME PHRASE CONVERTER
    @TypeConverter
    public static ArrayList<GamePhrase> phrasesConverter(String value){
        Type listType = new TypeToken<ArrayList<GamePhrase>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromPhrase(ArrayList<GamePhrase> phrasesConverter){
        Gson gson = new Gson();
        return gson.toJson(phrasesConverter);
    }

    //PLATFORMS CONVERTER
    @TypeConverter
    public static ArrayList<Platform> platformsConverter(String value){
        Type listType = new TypeToken<ArrayList<Platform>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromPlatform(ArrayList<Platform> platformsConverter){
        Gson gson = new Gson();
        return gson.toJson(platformsConverter);
    }

    //IMAGE CONVERTER
    @TypeConverter
    public static ArrayList<GameImage> imagesConverter(String value){
        Type listType = new TypeToken<ArrayList<GameImage>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromImage(ArrayList<GameImage> imagesConverter){
        Gson gson = new Gson();
        return gson.toJson(imagesConverter);
    }

    //VIDEO CONVERTER
    @TypeConverter
    public static ArrayList<GameVideo> videosConverter(String value){
        Type listType = new TypeToken<ArrayList<GameVideo>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromVideo(ArrayList<GameVideo> videosConverter){
        Gson gson = new Gson();
        return gson.toJson(videosConverter);
    }

    //GAMES CONVERTER
    @TypeConverter
    public static ArrayList<Game> gamesConverter(String value){
        Type listType = new TypeToken<ArrayList<Game>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromGame(ArrayList<Game> gamesConverter){
        Gson gson = new Gson();
        return gson.toJson(gamesConverter);
    }

    //TIME TO BEAT CONVERTER
    @TypeConverter
    public static TimeToBeat timeToBeatConverter(String value){
        Type listType = new TypeToken<TimeToBeat>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromTimeToBeat(TimeToBeat timeToBeatConverter){
        Gson gson = new Gson();
        return gson.toJson(timeToBeatConverter);
    }

}
