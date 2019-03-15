package com.example.computerdiy;

public class Option {

    private String value;
    private  String html;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    @Override
    public String toString() {
        return html;
    }

    public Option(String value, String html) {
        this.value = value;
        this.html = html;
    }


}
