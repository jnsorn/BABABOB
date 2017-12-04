package kr.ac.hansung.bababob.Restaurant;

import java.util.ArrayList;

import kr.ac.hansung.bababob.SchoolCafeteria.SchoolCafeteriaMenu;

public class RestaurantMenuItem {
    private  String menu;
    private int price;

    public RestaurantMenuItem(String menu, int price){
        this.menu=menu;
        this.price=price;
    }

    public void setMenu(String menu1) {
        menu = menu1;
    }

    public void setPrice(int price1) {
        price = price1;
    }

    public String getMenu() {
        return menu;
    }

    public int getPrice() {
        return price;
    }

/*    public static ArrayList<SchoolCafeteriaMenu> getMenus(int num) {
        ArrayList<SchoolCafeteriaMenu> menus = new ArrayList<SchoolCafeteriaMenu>();

        menus.add();

        return menus;
    }*/
}
