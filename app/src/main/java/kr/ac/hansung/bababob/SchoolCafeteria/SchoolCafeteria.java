package kr.ac.hansung.bababob.SchoolCafeteria;

import java.util.ArrayList;

/**
 * Created by Jina on 2017-11-26.
 */

public class SchoolCafeteria {

    public static final int PROFESSOR_CAFETERIA = 0;
    public static final int STUDENT_CAFETERIA = 1;
    public static final int MILGAOK = 2;
    public static final String[] SCHOOLCAFETERIA = {"교직원 식당", "학생 식당", "밀가옥"};
    private String cafeteria;
    private String time;

    public SchoolCafeteria(String cafeteria,String time){
        this.cafeteria = cafeteria;
        this.time = time;
    }

    public String getCafeteria(){
        return cafeteria;
    }

    public String getTime(){
        return time;
    }

    public static ArrayList<SchoolCafeteria> getCafeterias(){
        ArrayList<SchoolCafeteria> cafeterias = new ArrayList<SchoolCafeteria>();
        cafeterias.add(new SchoolCafeteria("교직원 식당","오전 11:30 - 오후 1:30\n오후 5:30 - 오후 7:00"));
        cafeterias.add(new SchoolCafeteria("학생 식당","오전 10:30 - 오후 7:00"));
        cafeterias.add(new SchoolCafeteria("밀가옥",""));
        return cafeterias;
    }
}
