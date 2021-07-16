<%-- 
    Document   : FirstPage
    Created on : 05.12.2018, 21:29:43
    Author     : Admin
--%>
<!--Login and registration page. Welcome application page. -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>



<%@include file="../WEB-INF/jspf/loginPageHeader.jspf" %>

<div class="row"  style="background-color: #e8e8e8;">

    <div class="col-6" >
        <img class="picture" src="${pageContext.request.contextPath}/images/bookSigh.png" alt="logo image">
        <p style="margin-left: 35%;" class="singleLetter">Welcome to the online library where you can find any book of your choice. Available search, browse, sort, and many others.</p>

    </div>


    <div class="col-6">
        <p class="authorsListTitle">Registration</p>
        <form  action="${pageContext.request.contextPath}/FileUploadServlet" method="POST" enctype="multipart/form-data" style="margin-left: 30%;padding-bottom: 10.5%">
            <p class="singleLetter">User Name</p>
            <input type="text" class="active-cyan-4" style="width: 400px;border-radius: 5px;margin-bottom: 2%;" placeholder="Name" name="registrName" size="20" required/><br>
            <p class="singleLetter">Email</p>
            <input type="email" class="active-cyan-4" style="width: 400px;border-radius: 5px;margin-bottom: 2%;" placeholder="Email" name="registrEmail" size="20" required/><br>
            <p class="singleLetter">Password</p>
            <input type="password" class="active-cyan-4" style="width: 400px;border-radius: 5px;margin-bottom: 2%;" placeholder="Password" name="registrPassword" size="20" required/><br>
            <p class="singleLetter">Telephone Numner</p>
            <input type="text" class="active-cyan-4" style="width: 400px;border-radius: 5px;margin-bottom: 2%;" placeholder="Phone number" name="Phone" size="20" required/><br>
            <div class="file-field">
                <div class="z-depth-1-half mb-4" style="width: 100px; height: 100px;">
                    <img src="https://mdbootstrap.com/img/Photos/Others/placeholder.jpg" class="img-fluid" style="width: 100px; height: 100px;"
                         alt="choose file">
                </div>
                <div class="d-flex justify-content">
                    <div class="btn btn-mdb-color btn-rounded float-left">
                        <span>Choose file</span>
                        <input type="file" name="file"/>
                    </div>
                </div>
            </div>
            <input type="submit" class="btn btn-lg btn-primary" value="Registrate" >
            <c:if test="${not empty sessionScope.registrationInfo}">
                <p class="passwordErrorStyle">${sessionScope.registrationInfo}</p>          
                <c:set var="registrationInfo" scope="session" value="" />
            </c:if>
        </form>

    </div>
</div>
</div>
<%request.setCharacterEncoding("UTF-8");%>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
</body>
</html>
