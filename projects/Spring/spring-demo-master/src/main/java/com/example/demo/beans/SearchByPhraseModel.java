package com.example.demo.beans;

import com.example.demo.enums.SearchType;

//model for searching by frace functionality
public class SearchByPhraseModel {
    private String searchPhrace;
    private SearchType searchType;


    public SearchByPhraseModel() {
    }
    public String getSearchPhrace() {
        return searchPhrace;
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public void setSearchPhrace(String searchPhrace) {
        this.searchPhrace = searchPhrace;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }


}
