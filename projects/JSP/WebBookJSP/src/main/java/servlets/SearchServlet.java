/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import bean.Book;
import bean.User;
import emums.SearchType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.BookService;

/**
 *
 * @author Admin
 */
// servlet  process search book functionality by different types
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    private volatile BookService bookService = new BookService();
    private volatile int selectedPageNumber;
    private volatile int numberBookOnPage = 3;
    private volatile long searchedBooksNumber;
    private volatile List<Integer> pageNumbers = new ArrayList<Integer>();
    private volatile List<Integer> showedPageNumbers = new ArrayList<Integer>();
    private volatile List<Book> currentSearchList_Page = new ArrayList<Book>();
    private volatile List<Book> currentSearchList_Full;

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
       
        if (request.getParameter("nextPage") != null) {
            selectedPageNumber=nextPage((int) request.getSession().getAttribute("page"));
            selectPage(selectedPageNumber);
        } else if (request.getParameter("previousPage") != null) {
            selectedPageNumber=previousPage((int)request.getSession().getAttribute("page"));
            selectPage(selectedPageNumber);
        } else if (request.getParameter("page") != null) {
            selectPage(Integer.valueOf(request.getParameter("page")));
        } else if (request.getParameter("genre_id") != null) {
            this.bookListByGenre(Integer.valueOf(request.getParameter("genre_id")), 1);
            this.updateSession(request);
            request.getSession().setAttribute("genre_id", request.getParameter("genre_id"));
        } else if (request.getParameter("letter") != null) {
            this.bookListByLetter(request.getParameter("letter"), 1);
            this.updateSession(request);
            request.getSession().setAttribute("letter", request.getParameter("letter"));
        } else if (request.getParameter("ReadingBooks") != null) {
            this.prepareUserReadingBookList((User) request.getSession().getAttribute("user"), 1);
            this.updateSession(request);
            request.getSession().setAttribute("ReadingBooks", request.getParameter("ReadingBooks"));
           
        } else if (request.getParameter("InterestingBooks") != null) {
            this.prepareUserInterestingBookList((User) request.getSession().getAttribute("user"), 1);
            this.updateSession(request);
            request.getSession().setAttribute("InterestingBooks", request.getParameter("InterestingBooks"));
        } else if (request.getParameter("ReadBooks") != null) {
            this.prepareUserReadedBookList((User) request.getSession().getAttribute("user"), 1);
            this.updateSession(request);
            request.getSession().setAttribute("ReadBooks", request.getParameter("ReadBooks"));

        }
        request.getSession().setAttribute("currentBookList", currentSearchList_Page);
        request.getSession().setAttribute("page", selectedPageNumber);
        
      

        if (request.getParameter("userPage") != null) {
            request.getSession().setAttribute("userPage", true);
            response.sendRedirect(request.getContextPath() + "/MainPagies/userPage.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/MainPagies/BookJSP.jsp");
        }
    }

    private void updateSession(HttpServletRequest request) {
        request.getSession().setAttribute("userPage", null);
        request.getSession().setAttribute("letter", null);
        request.getSession().setAttribute("genre_id", null);
        request.getSession().setAttribute("ReadingBooks", null);
        request.getSession().setAttribute("InterestingBooks", null);
        request.getSession().setAttribute("ReadBooks", null);
        request.getSession().setAttribute("searchValue", null);
        request.getSession().setAttribute("searchType", null);
        request.getSession().setAttribute("setting", null);
        request.getSession().setAttribute("bookNumberOnPage", currentSearchList_Full.size());
        request.getSession().setAttribute("showedPageNumbers", showedPageNumbers);
    }

    public void bookListByGenre(int genreId, int pageNumber) {

        if (genreId == 17) {
            currentSearchList_Full = bookService.selectAll();
        } else {
            currentSearchList_Full = bookService.selectByGenreID(String.valueOf(genreId));
        }
        searchedBooksNumber = currentSearchList_Full.size();
        selectPage(pageNumber);

    }

    public void bookListByLetter(String letter, int pageNumber) {
        currentSearchList_Full = bookService.selectByLetter(letter);
        searchedBooksNumber = currentSearchList_Full.size();
        selectPage(pageNumber);
    }

    public void prepareUserReadingBookList(User user, int pageNumber) {

        currentSearchList_Full = bookService.selectReadingBookByUserID(user.getUserEmail());
        searchedBooksNumber = currentSearchList_Full.size();
        selectPage(pageNumber);

    }

    public void prepareUserReadedBookList(User user, int pageNumber) {

        currentSearchList_Full = bookService.selectReadedBookByUserID(user.getUserEmail());
        searchedBooksNumber = currentSearchList_Full.size();
        selectPage(pageNumber);

    }

    public void prepareUserInterestingBookList(User user, int pageNumber) {

        currentSearchList_Full = bookService.selectInterestingBookByUserID(user.getUserEmail());
        searchedBooksNumber = currentSearchList_Full.size();
        selectPage(pageNumber);

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
        SearchType searchType = null;
        if (request.getParameter("searchType").equals(SearchType.AUTHOR.getType())) {
            searchType = SearchType.AUTHOR;
        } else {
            searchType = SearchType.BOOK_NAME;
        }
        currentSearchList_Full = bookService.selectByFrace(request.getParameter("searchValue"), searchType);
        searchedBooksNumber = currentSearchList_Full.size();
        selectPage(1);
        request.getSession().setAttribute("searchValue", request.getParameter("searchValue"));
        request.getSession().setAttribute("searchType", request.getParameter("searchType"));
        request.getSession().setAttribute("currentBookList", currentSearchList_Page);
        request.getSession().setAttribute("bookNumberOnPage", currentSearchList_Full.size());
        request.getSession().setAttribute("showedPageNumbers", showedPageNumbers);
        request.getSession().setAttribute("page", selectedPageNumber);
        response.sendRedirect(request.getContextPath() + "/MainPagies/BookJSP.jsp");

    }

    public void selectPage(int currentPage) {
        selectedPageNumber = currentPage;
        fillPageNumberArray(currentSearchList_Full.size(), numberBookOnPage);
        preparePageList();
    }

    public void fillPageNumberArray(long totalBooksCount, int booksCountOnPage) {
        int pageCount = 0;
        if (totalBooksCount > 0) {
            if (totalBooksCount % (float) booksCountOnPage == 0) {
                pageCount = (int) (totalBooksCount / booksCountOnPage);
            } else {
                pageCount = (int) (totalBooksCount / booksCountOnPage) + 1;
            }
        }
        pageNumbers.clear();
        for (int i = 1; i <= pageCount; i++) {
            pageNumbers.add(i);
        }
        fillShowedPageNumberArray();
    }

    public void fillShowedPageNumberArray() {
        showedPageNumbers.clear();
        if (pageNumbers.size() > 4) {
            if (selectedPageNumber == 1 || selectedPageNumber == 2) {
                for (int i = 0; i < 5; i++) {
                    showedPageNumbers.add(pageNumbers.get(i));
                }
            } else if (selectedPageNumber == pageNumbers.size() || selectedPageNumber == pageNumbers.size() - 1) {
                for (int i = pageNumbers.size() - 5; i < pageNumbers.size(); i++) {
                    showedPageNumbers.add(pageNumbers.get(i));
                }
            } else {
                for (int i = selectedPageNumber - 3; i < selectedPageNumber + 2; i++) {
                    showedPageNumbers.add(pageNumbers.get(i));
                }
            }
        } else {
            for (int i = 0; i < pageNumbers.size(); i++) {
                showedPageNumbers.add(pageNumbers.get(i));
            }

        }

    }

    public void preparePageList() {
        currentSearchList_Page.clear();
        int firstBookNumber = numberBookOnPage * selectedPageNumber - numberBookOnPage;
        long lastBookNumber = 0;
        if (firstBookNumber + numberBookOnPage > searchedBooksNumber) {
            lastBookNumber = searchedBooksNumber;
        } else {
            lastBookNumber = firstBookNumber + numberBookOnPage;
        }
        for (int i = firstBookNumber; i < lastBookNumber; i++) {
            currentSearchList_Page.add(currentSearchList_Full.get(i));
        }

    }

    public int previousPage(int currentPage) {
        if (currentPage > 1) {
            currentPage--;
        } else {
            currentPage = pageNumbers.size();
        }
        return currentPage;
    }

    public int nextPage(int currentPage) {
        if (currentPage < pageNumbers.size()) {
            currentPage++;
        } else {
            currentPage = 1;
        }
        return currentPage;
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
