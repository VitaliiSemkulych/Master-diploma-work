package com.example.demo.dao;

import com.example.demo.beans.Login;
import com.example.demo.beans.UserRegistrForm;
import com.example.demo.mapper.LoginUserMapper;
import com.example.demo.mapper.UserFormMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.IOException;

// data access object, which bring data for registration functionality
@Repository
@Transactional
public class UserRegistrFormDAO {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public UserRegistrFormDAO (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UserRegistrForm findUserAccount(String userName) {
        // Select .. from App_User u Where u.User_Name = ?
        String sql = "select * from users where userid = ? ";

        Object[] params = new Object[] { userName };
        UserFormMapper mapper = new UserFormMapper();
        try {
            UserRegistrForm userInfo = jdbcTemplate.queryForObject(sql, params, mapper);
            return userInfo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void addUserAccount(UserRegistrForm userRegistrForm){
        String sql1 = "INSERT INTO users (userid, password, tephoneNumber, userName,image) "
                + "VALUES (?,?,?,?,?)";
        String sql2 = "INSERT INTO users_groups (GROUPID,USERID) VALUES (?,?)";

        try {
            jdbcTemplate.update(sql1,userRegistrForm.getUserEmail(),userRegistrForm.getPassword(),
                    userRegistrForm.getTelephoneNumber(),userRegistrForm.getUserName(),userRegistrForm.getImage().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        jdbcTemplate.update(sql2,"user",userRegistrForm.getUserEmail());

    }



}
