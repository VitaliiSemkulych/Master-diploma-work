<%-- 
    Document   : userPage
    Created on : 28.07.2019, 13:26:04
    Author     : Admin
--%>
<!-- User bookmarck page-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>

<%@include file="../WEB-INF/jspf/leters.jspf" %>
<div class="row">
    <%@include file="../WEB-INF/jspf/userMenu.jspf" %>




    <div class="col-8" style="background-color: #e8e8e8;">
        <div class="row">
            <div  class="col-12" style="background-color: dimgray;" >    
                <p class="findLable">Find Books:${sessionScope.bookNumberOnPage}</p>  
            </div>
        </div>
        <div class="row">
            <div style="background-color:lightgray;" class="col-12">
                <div style="margin-left: 15%;" class="nav">
                    <c:choose>
                        <c:when test="${not empty sessionScope.ReadingBooks}">
                            <a href="${pageContext.request.contextPath}/SearchServlet?userPage=true&ReadingBooks=true" class="nav-link disabled">Reading Books</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/SearchServlet?userPage=true&ReadingBooks=true" class="nav-link singleLetter">Reading Books</a>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${not empty sessionScope.InterestingBooks}">
                            <a href="${pageContext.request.contextPath}/SearchServlet?userPage=true&InterestingBooks=true" class="nav-link disabled">Interesting Books</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/SearchServlet?userPage=true&InterestingBooks=true" class="nav-link singleLetter">Interesting Books</a>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${not empty sessionScope.ReadBooks}">
                            <a href="${pageContext.request.contextPath}/SearchServlet?userPage=true&ReadBooks=true" class="nav-link disabled">Read Books</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/SearchServlet?userPage=true&ReadBooks=true" class="nav-link singleLetter">Read Books</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <div class="row" style=" padding-bottom: 2%;">
            <c:forEach var="book" items="${sessionScope.currentBookList}" >
                <div class="col-4">
                    <div class="card" style="margin-top: 10px;">
                        <a href="${pageContext.request.contextPath}/SigleBookServlet?idB=${book.getId()}" >
                            <img src="${pageContext.request.contextPath}/ImageProcessing?idB=${book.getId()}&type=book" alt="Book image" class="card-img-top" >
                        </a>
                        <div class="card-body">
                            <strong class="card-title">${book.getBookName()} </strong> 
                            <br><strong>ISBN:</strong> ${book.getIsbn()}
                            <br><strong>Publisher:</strong> ${book.getPublisher().getPublisherName()}
                            <br><strong>Number of Pagies:</strong> ${book.getPageNumber()}
                            <br><strong>Publish Date:</strong> ${book.getPublishYear()}
                            <br><strong>Author:</strong> ${book.getAuthor().getName()}
                        </div>                         
                    </div>
                </div>
            </c:forEach>
        </div>


        <div class="row">
            <div class="col-12" >
                <c:if test="${fn:length(sessionScope.showedPageNumbers) ge 1}">
                    <nav  style="margin-left: 33%;margin-top: 3%;">
                        <ul class="pagination">

                            <c:if test="${fn:length(sessionScope.showedPageNumbers) gt 1}">
                                <li class="page-item"><a href="${pageContext.request.contextPath}/SearchServlet?userPage=true&previousPage=true" class="page-link">Previous Page </a> </li>
                                </c:if>
                                <c:forEach var="singlePage" items="${sessionScope.showedPageNumbers}" >
                                    <c:choose>
                                        <c:when test="${sessionScope.page eq singlePage}">
                                        <li class="page-item active"> <a href="${pageContext.request.contextPath}/SearchServlet?userPage=true&page=${singlePage}" class="page-link">${singlePage}</a></li>
                                        </c:when>
                                        <c:otherwise>
                                        <li class="page-item"> <a href="${pageContext.request.contextPath}/SearchServlet?userPage=true&page=${singlePage}" class="page-link">${singlePage}</a></li>
                                        </c:otherwise>   
                                    </c:choose>
                                </c:forEach>
                                <c:if test="${fn:length(sessionScope.showedPageNumbers) gt 1}">
                                <li class="page-item"> <a href="${pageContext.request.contextPath}/SearchServlet?userPage=true&nextPage=true" class="page-link">Next Page</a>  </li>
                                </c:if>
                        </ul>
                    </nav>

                </c:if>
            </div> 
        </div>
    </div>


    <%@include file="../WEB-INF/jspf/menu.jspf" %>
</div>