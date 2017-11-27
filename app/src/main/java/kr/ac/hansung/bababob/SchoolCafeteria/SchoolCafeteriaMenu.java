package kr.ac.hansung.bababob.SchoolCafeteria;

import java.util.ArrayList;

/**
 * Created by Jina on 2017-11-25.
 */

public class SchoolCafeteriaMenu {

    private String menu;
    private int price;

    public SchoolCafeteriaMenu(String menu,int price){
        this.menu = menu;
        this.price = price;
    }

    public String getMenu() {
        return menu;
    }

    public String getPrice(){
        return Integer.toString(price);
    }
    public static ArrayList<SchoolCafeteriaMenu> getMenus(String cafeteria){
        ArrayList<SchoolCafeteriaMenu> menus = new ArrayList<SchoolCafeteriaMenu>();
        menus.add(new SchoolCafeteriaMenu("menu1",1000));
        menus.add(new SchoolCafeteriaMenu("menu2",200));
        menus.add(new SchoolCafeteriaMenu("menu3",4000));

        return menus;
    }
}
