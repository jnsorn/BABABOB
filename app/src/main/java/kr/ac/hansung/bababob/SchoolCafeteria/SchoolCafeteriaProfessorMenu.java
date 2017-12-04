package kr.ac.hansung.bababob.SchoolCafeteria;

import java.util.ArrayList;

/**
 * Created by Jina on 2017-12-04.
 */

public class SchoolCafeteriaProfessorMenu {
    private SchoolCafeteriaMenu lunch;
    private SchoolCafeteriaMenu dinner;

    public SchoolCafeteriaProfessorMenu(){

    }

    public SchoolCafeteriaMenu getLunch() {
        return lunch;
    }

    public void setLunch(SchoolCafeteriaMenu lunch) {
        this.lunch = lunch;
    }

    public SchoolCafeteriaMenu getDinner() {
        return dinner;
    }

    public void setDinner(SchoolCafeteriaMenu dinner) {
        this.dinner = dinner;
    }
}
