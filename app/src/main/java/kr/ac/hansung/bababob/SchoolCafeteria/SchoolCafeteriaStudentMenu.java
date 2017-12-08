package kr.ac.hansung.bababob.SchoolCafeteria;

import java.util.ArrayList;

/**
 * Created by Jina on 2017-12-08.
 */

public class SchoolCafeteriaStudentMenu extends SchoolCafeteriaMenu {

    public static final int ROLL_NOODLES = 0;
    public static final int BAB = 1;
    public static final int FRY_RICE = 2;
    public static final String[] SCHOOLCAFETERIASTUDENT = {"면류&찌개&김밥", "덮밥류&비빔밥", "볶음밥&오므라이스&돈까스"};

    public static ArrayList<SchoolCafeteriaMenu> getMenus(int num){
        ArrayList<SchoolCafeteriaMenu> menus = new ArrayList<SchoolCafeteriaMenu>();
        switch(num){
            case ROLL_NOODLES:
                return SchoolCafeteriaStudentInfoActivity.cafeteriaStudentMenusNoodles;
            case BAB:
                return SchoolCafeteriaStudentInfoActivity.cafeteriaStudentMenusBab;
            case FRY_RICE:
                return SchoolCafeteriaStudentInfoActivity.cafeteriaStudentMenusFry;
        }
        return menus;
    }
}
