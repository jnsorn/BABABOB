package kr.ac.hansung.bababob;

public class RestaurantContact {
    private String restaurantName;
    private String restaurantImg;

    public RestaurantContact(String Img, String name) {
        restaurantImg = Img;
        restaurantName = name;
    }

    public String getName() {
        return restaurantName;
    }

    public String getImg(){
       return restaurantImg;
    }


}
