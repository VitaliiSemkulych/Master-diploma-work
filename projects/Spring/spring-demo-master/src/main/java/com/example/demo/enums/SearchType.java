package com.example.demo.enums;
//enum where counted search by phrase category
public enum SearchType {
    BOOK_NAME("BookName"),
    AUTHOR("Author");
    private String value;
    SearchType(String value){
        this.value=value;
    }
    public String getValue(){
        return value;
    }
}
