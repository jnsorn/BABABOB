package kr.ac.hansung.bababob;

public class RestaurantItem {
    private String restaurantName;
    private String restaurantImg;

    public RestaurantItem(String name, String Img) {
        restaurantName = name;
        restaurantImg = Img;
    }

    public String getName() {
        return restaurantName;
    }

    public String getImg(){
       return restaurantImg;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setRestaurantImg(String restaurantImg) {
        this.restaurantImg = restaurantImg;
    }
}
