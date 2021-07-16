/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilities.ConnectionDBUtility;

/**
 *
 * @author Admin
 */
// servise where defined query for retriving data from database for functionality related with single book
public class SingleBookPageService {
    
    public boolean checkIfInReadingMode(String id,User user) {

        Connection conn = ConnectionDBUtility.getInstance();
        try (Statement st = conn.createStatement();
                ResultSet res = st.executeQuery("select * from reading_book where book_id=" + id + " and user_id='" + user.getUserEmail() + "'");) {
            if (res.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SingleBookPageService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }



    public boolean checkIfInInterestingMode(String id,User user) {
        Connection conn = ConnectionDBUtility.getInstance();
        try (Statement st = conn.createStatement();
                ResultSet res = st.executeQuery("select * from interesting_book where book_id=" + id + " and user_id='" + user.getUserEmail() + "'");) {
            if (res.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SingleBookPageService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean checkIfInReadedMode(String id,User user) {
        Connection conn = ConnectionDBUtility.getInstance();
        try (Statement st = conn.createStatement();
                ResultSet res = st.executeQuery("select * from readed_book where book_id=" + id + " and user_id='" + user.getUserEmail() + "'");) {
            if (res.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SingleBookPageService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
    
        public boolean isEvaluatedByUser(String id,User user) {
        boolean isEvaluated = false;
        Connection conn = ConnectionDBUtility.getInstance();
        try (Statement st = conn.createStatement();
                ResultSet res = st.executeQuery("select * from evaluation where book_id=" + id + " and user_id='" + user.getUserEmail() + "'");) {
            if (res.next()) {
                isEvaluated = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SingleBookPageService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return isEvaluated;
    }
        
        
        public String getMarkExergue(String id) {
        Connection conn = ConnectionDBUtility.getInstance();
        try (Statement st = conn.createStatement();
                ResultSet res = st.executeQuery("select sum(mark) as summ, count(evaluation_id) as numb from evaluation where book_id=" + String.valueOf(id));) {
            if (res.next()) {
                long sum = res.getLong("summ");
                int count = res.getInt("numb");
                float result = Math.round(100 * (sum / (double) count)) / (float) 100;
                if (result != 0.0f) {
                    return String.valueOf(result);
                } else {
                    return "Not evaluated yet";
                }
            } else {
                return "Not evaluated yet";
            }
        } catch (SQLException ex) {
            Logger.getLogger(SingleBookPageService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "error";
    }
        
    
}
