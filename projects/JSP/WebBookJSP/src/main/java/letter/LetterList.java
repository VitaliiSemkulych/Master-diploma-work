/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package letter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
// create list with english alphabet letter
public class LetterList {

    private static final List<String> letterList = new ArrayList<String>();

    static {
        for (int i = 65; i <= 90; i++) {
            letterList.add(String.valueOf((char) i));

        }
    }

    public static List<String> getLetterList() {
        return letterList;
    }

}
