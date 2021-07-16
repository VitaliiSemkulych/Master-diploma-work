/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import bean.User;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import utilities.ConnectionDBUtility;

/**
 *
 * @author Admin
 */
// servlet  process functionality which upadete user data
@WebServlet(name = "EditPersonalData", urlPatterns = {"/EditPersonalData"})
@MultipartConfig
public class EditPersonalData extends HttpServlet {

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
        User user = (User) request.getSession().getAttribute("user");
        FileItemFactory factory = new DiskFileItemFactory();
// Set factory constraints
// factory.setSizeThreshold(yourMaxMemorySize);
// factory.setRepository(yourTempDirectory);
// Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
// upload.setSizeMax(yourMaxRequestSize);

// Parse the request
        List<FileItem> uploadItems = null;
        try {
            uploadItems = upload.parseRequest(request);
        } catch (FileUploadException ex) {
            Logger.getLogger(FileUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (FileItem uploadItem : uploadItems) {
            String fieldName = uploadItem.getFieldName();

            if (uploadItem.isFormField()) {

                if (fieldName.equals("editPassword")) {
                    String password = uploadItem.getString();
                    if (password != null && password.length() > 3) {
                        user.setPassword(password);
                    }
                }
                if (fieldName.equals("editName")) {
                    String userName = uploadItem.getString();
                    if (userName != null && userName.length() > 2) {
                        user.setUserName(userName);
                    }
                }
                if (fieldName.equals("editPhone")) {
                    String telephoneNumber = uploadItem.getString();
                    if (telephoneNumber != null && telephoneNumber.length() > 3) {
                        user.setTelephoneNumber(telephoneNumber);
                    }
                }

            }
            if (fieldName.equals("editFile")) {
                byte[] image = null;
                try {

                    InputStream imageStream = uploadItem.getInputStream();
                    if (imageStream != null) {
                        try {
                            image = new byte[imageStream.available()];
                            if (image != null && image.length > 0) {
                                imageStream.read(image);
                                user.setImage(image);
                            }

                        } catch (IOException ex) {
                            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                } catch (IOException ex) {
                    Logger.getLogger(FileUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        changePersonalData(user);

        request.getSession().setAttribute("editInfo", "Information Updated");
        response.sendRedirect(request.getContextPath() + "/MainPagies/settings.jsp?settings=true");
    }

    public void changePersonalData(User user) {
        Connection conn = ConnectionDBUtility.getInstance();
        try (
                PreparedStatement st = conn.prepareStatement("update users set password=?,tephoneNumber=?,userName=? where userid=?");) {

            st.setString(1, user.getPassword());
            st.setString(2, user.getTelephoneNumber());
            st.setString(3, user.getUserName());
            st.setString(4, user.getUserEmail());
            st.executeUpdate();
            updateImage(conn, user);
        } catch (SQLException ex) {
            Logger.getLogger(EditPersonalData.class.getName()).log(Level.SEVERE, null, ex);
        }

        // return "Settings";
        // return "Settings";
    }

    private void updateImage(Connection conn, User user) {

        try (PreparedStatement st = conn.prepareStatement("update users set image=? where userid=?");) {
            st.setBytes(1, user.getImage());
            st.setString(2, user.getUserEmail());
            st.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(EditPersonalData.class.getName()).log(Level.SEVERE, null, ex);
        }
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
