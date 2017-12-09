package kr.ac.hansung.bababob;

/**
 * Created by Jina on 2017-12-09.
 */

public class Comment {

    private String email;
    private String text;
    private String date;

    public Comment(){}

    public Comment(String email, String text, String date) {
        this.email = email;
        this.text = text;
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
