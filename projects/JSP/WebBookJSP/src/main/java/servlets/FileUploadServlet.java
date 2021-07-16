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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


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
//@MultipartConfig(fileSizeThreshold = 20848820, // 10 MB 
//        maxFileSize = 418018841, // 50 MB
//        maxRequestSize = 1048576)
// servlet  process registration functionality
@WebServlet(urlPatterns = {"/FileUploadServlet"})
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

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
    //
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
       String massage= registrate(request);
       if(massage!=null){
           request.getSession().setAttribute("registrationInfo", massage);
        response.sendRedirect(request.getContextPath() + "/LoginPage/LoginPage.jsp");
       }else{
       response.sendRedirect(request.getContextPath() + "/LoginPage/LoginPage.jsp");
       }

    }

    public String registrate(HttpServletRequest request) {

        String userEmail = null;
        String password = null;
        String userName = null;
        String telephoneNumber = null;

       
        InputStream imageStream=null;
        byte[] image = null;

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
                
                
                if (fieldName.equals("registrEmail")) {
                    userEmail = uploadItem.getString();
                }
                if (fieldName.equals("registrPassword")) {
                    password = uploadItem.getString();
                }
                if (fieldName.equals("registrName")) {
                    userName = uploadItem.getString();
                }
                if (fieldName.equals("Phone")) {
                    telephoneNumber = uploadItem.getString();
                }
            

            }
                if(fieldName.equals("file")){
                    
                    try {
                        
                        imageStream=uploadItem.getInputStream();
                    } catch (IOException ex) {
                        Logger.getLogger(FileUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        }

        String registEmailMassage = null;
        String registPasswordMassage = null;

        registEmailMassage = registrEmailValidation(userEmail);
        registPasswordMassage = registrPasswordValidation(password);
        if (registEmailMassage != null) {
           return registEmailMassage;

        }
        if (registPasswordMassage != null) {
            return registPasswordMassage;

        }
        if (registEmailMassage == null && registPasswordMassage == null) {
          

            image = fillImage(imageStream);
            Connection conn = ConnectionDBUtility.getInstance();

            String sql1 = "INSERT INTO users (userid, password, tephoneNumber, userName,image) "
                    + "VALUES ('" + userEmail + "','" + password + "','" + telephoneNumber + "','" + userName + "',?)";
            String sql2 = "INSERT INTO users_groups (GROUPID,USERID) VALUES ('user','" + userEmail + "')";

            try (Statement stmt = conn.createStatement();
                    PreparedStatement ps = conn.prepareStatement(sql1)) {

                ps.setBytes(1, image);
                ps.execute();
                stmt.execute(sql2);

            } catch (SQLException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }

            
            return "Registration successful. Please login.";

        }
        return null;

    }

    public byte[] fillImage(InputStream inputImage) {
        byte[] image = null;
        try  {
            image = new byte[inputImage.available()];
            inputImage.read(image);

        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return image;
    }

    public String registrEmailValidation(String regEmail) {
        boolean isExist = false;

        Connection conn = ConnectionDBUtility.getInstance();
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select * from users");) {
            while (rs.next()) {
                if (rs.getString("userid").equals(regEmail)) {
                    isExist = true;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (isExist) {
            return "User with current email already exist.";

        }
        return null;
    }

    public String registrPasswordValidation(String regPassword) {

        boolean isErrorOccurred = false;
        boolean isNumberContains = false;
        boolean isBigLetterContains = false;
        //check if password contains only number or big and small english letter
        for (int i = 0; i < regPassword.length(); i++) {
            char c = regPassword.charAt(i);
            if ((int) c < 48 || ((int) c > 57 && (int) c < 65) || ((int) c > 90 && (int) c < 97) || (int) c > 122) {
                isErrorOccurred = true;
            }
            if ((int) c >= 48 && (int) c <= 57) {
                isNumberContains = true;
            }
            if ((int) c >= 65 && (int) c <= 90) {
                isBigLetterContains = true;
            }
        }
        if (!isBigLetterContains || !isNumberContains || isErrorOccurred) {
            return "Password have to contain at least one big letter and one number symbol. You can use just english letter.";

        }
        return null;
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
