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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.SingleBookPageService;
import utilities.ConnectionDBUtility;

/**
 *
 * @author Admin
 */
// servlet  process functionality which updare book mack
@WebServlet(name = "updateMarckServlet", urlPatterns = {"/updateMarckServlet"})
public class updateMarckServlet extends HttpServlet {
private final SingleBookPageService bookPageService= new SingleBookPageService();
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
        try (PreparedStatement st = conn.prepareStatement(
                "INSERT INTO evaluation (user_id,book_id,mark) "
                + "VALUES(?,?,?)");) {
            st.setString(1, ((User) request.getSession().getAttribute("user")).getUserEmail());
            st.setLong(2, ((Book) request.getSession().getAttribute("singleBook")).getId());
            st.setInt(3, Integer.valueOf(request.getParameter("mark")));
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(updateMarckServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.getSession().setAttribute("isEvaluated","true");
        request.getSession().setAttribute("markValue", bookPageService.getMarkExergue(String.valueOf(((Book) request.getSession().getAttribute("singleBook")).getId())));
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
