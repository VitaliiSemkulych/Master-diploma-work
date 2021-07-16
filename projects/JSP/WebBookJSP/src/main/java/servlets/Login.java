/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import bean.User;
import dao.GenreDAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import letter.LetterList;
import utilities.ConnectionDBUtility;

/**
 *
 * @author Admin
 */
// servlet  process login functionality
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    private final GenreDAO genreDAO = new GenreDAO();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String password = request.getParameter("userPassword");
        String userEmail = request.getParameter("userEmail");
        Connection conn = ConnectionDBUtility.getInstance();
        try (Statement st = conn.createStatement();
                ResultSet res = st.executeQuery("select * from users where userid='" + userEmail + "'");) {
            if (res.next()) {
                try {

                    request.login(userEmail, password);
                    request.getSession().setAttribute("genreList", genreDAO.getAll());
                    request.getSession().setAttribute("letterList", LetterList.getLetterList());
                    request.getSession().setAttribute("user", updateUserData(userEmail));
                    response.sendRedirect(request.getContextPath() + "/SearchServlet?genre_id=17");
                } catch (ServletException ex) {
                    request.getSession().setAttribute("errorMessage", "Password is not correct!");
                    response.sendRedirect(request.getContextPath() + "/LoginPage/LoginPage.jsp");
                }

            } else {
                request.getSession().setAttribute("errorMessage", "Account with current email is not exist.");
                response.sendRedirect(request.getContextPath() + "/LoginPage/LoginPage.jsp");
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private User updateUserData(String userEmail) {
        User user = null;
        Connection conn = ConnectionDBUtility.getInstance();
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select * from users where userid='" + userEmail + "'");) {
            while (rs.next()) {
                user = new User(rs.getString("userName"), rs.getString("tephoneNumber"), userEmail, rs.getString("password"), rs.getBytes("image"));

            }

        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
