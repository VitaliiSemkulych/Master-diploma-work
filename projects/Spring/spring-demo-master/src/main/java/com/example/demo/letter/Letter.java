package com.example.demo.letter;

import java.util.ArrayList;
import java.util.List;
// create list with english alphabet letter
public class Letter {
    private static final List<Character> letterList;

    static {
        letterList=new ArrayList<>();
        for(int i =65;i<=90;i++){
            letterList.add((char)i);
        }
    }

    public static List<Character> getLetterList() {
        return letterList;
    }
}
