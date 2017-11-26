package kr.ac.hansung.bababob.SchoolCafeteria;

import java.util.ArrayList;

/**
 * Created by Jina on 2017-11-13.
 */

public class StudentMenu {
    private String menu;
    private ArrayList<StudentMenu> menus;

    public StudentMenu(String menu){
        this.menu = menu;
    }

    public String getMenu() {
        return menu;
    }

    public static ArrayList<StudentMenu> createContactsList(int numMenus) {
        ArrayList<StudentMenu> menus = new ArrayList<StudentMenu>();

        for (int i = 1; i <= numMenus; i++) {
            menus.add(new StudentMenu("Menu"+i));
        }

        return menus;
    }
}
