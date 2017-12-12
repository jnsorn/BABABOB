package kr.ac.hansung.bababob;

/**
 * Created by Jina on 2017-12-09.
 */

public class Review {

    private String id;
    private String email;
    private String restaurant;
    private String menu;
    private String time;
    private String image;
    private String text;
    private float amountScore;
    private float spicyScore;
    private float totalScore;

    public Review() {
        this.image = null;

    }

    public Review(String id, String email, String restaurant, String time, String image, String text, float amountScore, float spicyScore, float totalScore) {
        this.id = id;
        this.email = email;
        this.restaurant = restaurant;
        this.time = time;
        this.image = image;
        this.text = text;
        this.amountScore = amountScore;
        this.spicyScore = spicyScore;
        this.totalScore = totalScore;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() { return image; }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getAmountScore() {
        return amountScore;
    }

    public void setAmountScore(float amountScore) {
        this.amountScore = amountScore;
    }

    public float getSpicyScore() {
        return spicyScore;
    }

    public void setSpicyScore(float spicyScore) {
        this.spicyScore = spicyScore;
    }

    public float getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(float totalScore) {
        this.totalScore = totalScore;
    }
}
