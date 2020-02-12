package mk.test.gamesbrowser.model;

public class Cover {
    private int id, game;
    private boolean alpha_channel, animated;
    private int width, height;
    private String image_id;
    private String url;

    public Cover () {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGame() {
        return game;
    }

    public void setGame(int game) {
        this.game = game;
    }

    public boolean isAlpha_channel() {
        return alpha_channel;
    }

    public void setAlpha_channel(boolean alpha_channel) {
        this.alpha_channel = alpha_channel;
    }

    public boolean isAnimated() {
        return animated;
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
