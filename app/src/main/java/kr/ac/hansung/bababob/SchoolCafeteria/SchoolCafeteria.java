package kr.ac.hansung.bababob.SchoolCafeteria;

import java.util.ArrayList;

/**
 * Created by Jina on 2017-11-26.
 */

public class SchoolCafeteria {
    private String cafeteria;

    public SchoolCafeteria(String cafeteria){
        this.cafeteria = cafeteria;
    }

    public String getCafeteria(){
        return cafeteria;
    }

    public static ArrayList<SchoolCafeteria> getCafeterias(){
        ArrayList<SchoolCafeteria> cafeterias = new ArrayList<SchoolCafeteria>();
        cafeterias.add(new SchoolCafeteria("교직원 식당"));
        cafeterias.add(new SchoolCafeteria("학생 식당"));
        cafeterias.add(new SchoolCafeteria("밀가옥"));

        return cafeterias;
    }
}
