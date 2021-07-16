<%-- 
    Document   : settings
    Created on : 28.07.2019, 13:26:15
    Author     : Admin
--%>
<!-- Update user info page.-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@include file="../WEB-INF/jspf/leters.jspf" %>
<div class="row">
    <%@include file="../WEB-INF/jspf/userMenu.jspf" %>
    <div class="col-8" style="background-color: #e8e8e8;">
        <p class="authorsListTitle">Update personal data!</p>
        <form  action="${pageContext.request.contextPath}/EditPersonalData" method="POST" style="margin-left: 40%;" enctype="multipart/form-data">
            <input type="text" class="active-cyan-4" style="width: 200px; border-radius: 5px;" placeholder="Name" name="editName" size="20" /><br>

            <input type="password" class="active-cyan-4" style="width: 200px; border-radius: 5px;" placeholder="Password" name="editPassword" size="20" /><br>

            <input type="text" class="active-cyan-4" style="width: 200px; border-radius: 5px;" placeholder="Phone number" name="editPhone" size="20" /><br>
            <div class="file-field">
                <div class="z-depth-1-half mb-4" style="width: 100px; height: 100px;">
                    <img src="https://mdbootstrap.com/img/Photos/Others/placeholder.jpg" class="img-fluid" style="width: 100px; height: 100px;"
                         alt="choose file">
                </div>
                <div class="d-flex justify-content">
                    <div class="btn btn-mdb-color btn-rounded float-left">
                        <span>Choose file</span>
                        <input type="file"  name="editFile">
                    </div>
                </div>
            </div>
            <input type="submit" class="btn btn-lg btn-primary" value="Edit" >


        </form>
        <c:if test="${sessionScope.editInfo != null}">
            <div class="passwordErrorStyle">
                <c:out value="${sessionScope.editInfo}" />
                <c:remove var="editInfo" scope="session" />
            </div>
        </c:if>

    </div>

    <%@include file="../WEB-INF/jspf/menu.jspf" %>
</div>