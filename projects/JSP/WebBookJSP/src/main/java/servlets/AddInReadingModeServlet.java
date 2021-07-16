/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import bean.Book;
import bean.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utilities.ConnectionDBUtility;

/**
 *
 * @author Admin
 */
// servlet  process functionality which marck book as reading
@WebServlet(name = "AddInReadingModeServlet", urlPatterns = {"/AddInReadingModeServlet"})
public class AddInReadingModeServlet extends HttpServlet {

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
  Connection conn = ConnectionDBUtility.getInstance();
        if (request.getSession().getAttribute("readingMode").toString().equals("false")) {
            try (PreparedStatement st = conn.prepareStatement("INSERT INTO reading_book (book_id,user_id) VALUES(?,?)");) {
                st.setLong(1, ((Book)request.getSession().getAttribute("singleBook")).getId());
                st.setString(2, ((User)request.getSession().getAttribute("user")).getUserEmail());
                st.execute();
            } catch (SQLException ex) {
                Logger.getLogger(AddInReadingModeServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.getSession().setAttribute("readingMode", "true");
        } else {
            try (Statement st = conn.createStatement();) {
                st.execute("DELETE FROM reading_book WHERE book_id=" + ((Book)request.getSession().getAttribute("singleBook")).getId() + " and user_id='" + ((User)request.getSession().getAttribute("user")).getUserEmail() + "'");
            } catch (SQLException ex) {
                Logger.getLogger(AddInReadingModeServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.getSession().setAttribute("readingMode", "false");

        }
        response.sendRedirect(request.getContextPath() + "/MainPagies/singleBookPage.jsp");
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
        processRequest(request, response);
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
