/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.BookService;
import service.RecensionService;
import service.SingleBookPageService;

/**
 *
 * @author Admin
 */
// servlet  process functionality which load book page
@WebServlet(name = "SigleBookServlet", urlPatterns = {"/SigleBookServlet"})
public class SigleBookServlet extends HttpServlet {

    private final SingleBookPageService bookPageService = new SingleBookPageService();
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
        request.getSession().setAttribute("singleBook", new BookService().selectBookByID(request.getParameter("idB")));
        if (!bookPageService.isEvaluatedByUser(request.getParameter("idB"), (bean.User) request.getSession().getAttribute("user"))) {
            request.getSession().setAttribute("isEvaluated", "false");
        } else {
            request.getSession().setAttribute("isEvaluated", "true");
        }
        if (!bookPageService.checkIfInReadedMode(request.getParameter("idB"), (bean.User) request.getSession().getAttribute("user"))) {
            request.getSession().setAttribute("readedMode", "false");
        } else {
            request.getSession().setAttribute("readedMode", "true");
        }
        if (!bookPageService.checkIfInReadingMode(request.getParameter("idB"), (bean.User) request.getSession().getAttribute("user"))) {
            request.getSession().setAttribute("readingMode", "false");
        } else {
            request.getSession().setAttribute("readingMode", "true");
        }
        if (!bookPageService.checkIfInInterestingMode(request.getParameter("idB"), (bean.User) request.getSession().getAttribute("user"))) {
            request.getSession().setAttribute("interestingMode", "false");
        } else {
            request.getSession().setAttribute("interestingMode", "true");
        }
        request.getSession().setAttribute("userPage", null);
        request.getSession().setAttribute("letter", null);
        request.getSession().setAttribute("genre_id", null);
        request.getSession().setAttribute("ReadingBooks", null);
        request.getSession().setAttribute("InterestingBooks", null);
        request.getSession().setAttribute("ReadBooks", null);
        request.getSession().setAttribute("searchValue", null);
        request.getSession().setAttribute("searchType", null);
        request.getSession().setAttribute("setting", null);
        request.getSession().setAttribute("modifyRecensionId", "-1");
        request.getSession().setAttribute("recensionList", recensionService.getRecensionListByUser(Long.valueOf(request.getParameter("idB"))));
        request.getSession().setAttribute("markValue", bookPageService.getMarkExergue(request.getParameter("idB")));
        response.sendRedirect(request.getContextPath() + "/MainPagies/singleBookPage.jsp");
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
