package kr.ac.hansung.bababob.SchoolCafeteria;

import java.util.ArrayList;

/**
 * Created by Jina on 2017-11-25.
 */

public class SchoolCafeteriaMenu {

    private String menu;
    private int price;

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public SchoolCafeteriaMenu(){

    }

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
    public static ArrayList<SchoolCafeteriaMenu> getMenus(int num){
        ArrayList<SchoolCafeteriaMenu> menus = new ArrayList<SchoolCafeteriaMenu>();
        switch (num) {
            case SchoolCafeteria.PROFESSOR_CAFETERIA: {
                menus.add(new SchoolCafeteriaMenu("중식", 0));
                menus.add(new SchoolCafeteriaMenu("중식 메뉴!!", 4500));
                menus.add(new SchoolCafeteriaMenu("석식", 0));
                menus.add(new SchoolCafeteriaMenu("석식 메뉴!!", 4500));
                break;
            }
            case SchoolCafeteria.STUDENT_CAFETERIA: {
                return SchoolCafeteriaFragment.schoolCafeteriaStudentMenus;
            }
            case SchoolCafeteria.MILGAOK: {
                break;
            }
        }
        return menus;
    }
}
