/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;


import bean.Book;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.RecensionService;
import utilities.ConnectionDBUtility;

/**
 *
 * @author Admin
 */
// servlet  process functionality which delete recension
@WebServlet(name = "DeleteRecencion", urlPatterns = {"/DeleteRecencion"})
public class DeleteRecencion extends HttpServlet {
private final RecensionService recensionService = new RecensionService();
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
        try (Statement st = conn.createStatement();) {
            String deleteSql = "DELETE FROM recension WHERE recension_id=" + request.getParameter("recensionID");
            st.executeUpdate(deleteSql);

        } catch (SQLException ex) {
            Logger.getLogger(DeleteRecencion.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.getSession().setAttribute("recensionList",recensionService.getRecensionListByUser(((Book) request.getSession().getAttribute("singleBook")).getId()));
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
