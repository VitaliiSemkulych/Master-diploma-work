package com.example.demo.extractor;

import com.example.demo.beans.Marck;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
// extract list of mack from database
@Component
public class MarckExtractor implements ResultSetExtractor<List<Marck>> {

    @Override
    public List<Marck> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Marck> bookList = new ArrayList<>();
        while (resultSet.next()) {
             bookList.add(new Marck(resultSet.getInt("mark")));
        }
        return  bookList;
    }
}
