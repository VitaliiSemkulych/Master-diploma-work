<%-- 
    Document   : singleBookPage
    Created on : 28.07.2019, 13:26:35
    Author     : Admin
--%>
<!-- Single book page-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../WEB-INF/jspf/leters.jspf" %>
<div class="row">
    <%@include file="../WEB-INF/jspf/userMenu.jspf" %>


    <div class="col-8" style="background-color: #e8e8e8;">   
        <div class="row">
            <div class="col-3">                     
                <c:if test="${sessionScope.isEvaluated eq 'false'}">
                    <form  action="${pageContext.request.contextPath}/updateMarckServlet" method="post">
                        <span class="singleBookTitle">Evaluate</span>
                        <select name="mark" size="1" class="mdb-select">
                            <option selected  value="1" >1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                        </select> 
                        <input type="submit" class="btn btn-primary btn-sm" value="Evaluate" />
                    </form>
                </c:if>
                <img src="${pageContext.request.contextPath}/ImageProcessing?idB=${sessionScope.singleBook.getId()}&type=book" class="img-responsive" style="width: 100%;height: 55%;"/>          
                <div> <a href="${pageContext.request.contextPath}/PDFProcessing" target="_blank"><img style="margin-top:7px;margin-left:-3px; height:60px;width:60px;" src="${pageContext.request.contextPath}/images/read.png" alt="Read image"></a>
                    <a href="${pageContext.request.contextPath}/DownloadPDFProcessing" target="_blank"><img style="margin-left:7px;margin-top:7px; height:60px;width:140px;" src="${pageContext.request.contextPath}/images/download.png" alt="Download image"></a>
                </div>
                <div>
                    <c:choose>
                        <c:when test="${sessionScope.readingMode eq 'true'}">
                            <a href="${pageContext.request.contextPath}/AddInReadingModeServlet"><img src="${pageContext.request.contextPath}/images/readingChecked.png" class="checkBoxIMG"/> </a>
                            </c:when>
                            <c:otherwise>
                            <a href="${pageContext.request.contextPath}/AddInReadingModeServlet"><img src="${pageContext.request.contextPath}/images/readingUnchecked.png" class="checkBoxIMG"/> </a> 
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${sessionScope.readedMode eq 'true'}">
                            <a href="${pageContext.request.contextPath}/AddInReadModeServlet"><img src="${pageContext.request.contextPath}/images/readedChecked.png" class="checkBoxIMG"/> </a>
                            </c:when>
                            <c:otherwise>
                            <a href="${pageContext.request.contextPath}/AddInReadModeServlet"><img src="${pageContext.request.contextPath}/images/readedUnchecked.png" class="checkBoxIMG"/> </a> 
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${sessionScope.interestingMode eq 'true'}">
                            <a href="${pageContext.request.contextPath}/AddInInterestingModeServlet"><img src="${pageContext.request.contextPath}/images/interestingChecked.png" class="checkBoxIMG"/> </a>
                            </c:when>
                            <c:otherwise>
                            <a href="${pageContext.request.contextPath}/AddInInterestingModeServlet"><img src="${pageContext.request.contextPath}/images/interestingUnchecked.png" class="checkBoxIMG"/> </a> 
                            </c:otherwise>
                        </c:choose>
                </div>
            </div>

            <div id="bookContentForm" class="col-6">
                <p class="authorsListTitle">
                    ${sessionScope.singleBook.getBookName()}
                </p>
                <p> 
                    <strong style="background-color: #e8e8e8;">Description: </strong>
                    ${sessionScope.singleBook.getDescription()}
                </p>
                <p>
                    <strong style="background-color: #e8e8e8;">ISBN:</strong> 
                    ${sessionScope.singleBook.getIsbn()}
                </p>
                <p>
                    <strong style="background-color: #e8e8e8;">Publisher:</strong>
                    ${sessionScope.singleBook.getPublisher().getPublisherName()}
                </p>

                <p>
                    <strong style="background-color: #e8e8e8;">Number of Pagies:</strong>
                    ${sessionScope.singleBook.getPageNumber()}
                </p>   

                <p>
                    <strong style="background-color: #e8e8e8;">Publish Date:</strong>  
                    ${sessionScope.singleBook.getPublishYear()}
                </p>     

                <p>
                    <strong style="background-color: #e8e8e8;">Author:</strong>
                    ${sessionScope.singleBook.getAuthor().getName()}
                </p>
                <p>
                    <strong style="background-color: #e8e8e8;">Mark:</strong>
                    ${sessionScope.markValue}
                </p>    
            </div>
            <div class="col-3">
                <h4 class="authorsListTitle">Recension:</h4>
                <div id="active-panel-container" class="recensionPanel searchedPanel">
                    <c:forEach var="recensionObject" items="${sessionScope.recensionList}" >
                        <div class="singleRecension">
                            <p class="authorsRecensionTitle">${recensionObject.getUserName()}: </p>
                            <img src="${pageContext.request.contextPath}/ImageProcessing?idU=${recensionObject.getUnformedUserEmail()}&amp;type=user" class="recensionImage"/> 
                            <p style="margin-left: 3px;">${recensionObject.getRecensionText()} </p>
                            <div style="clear:both;"></div>
                            <c:if test="${(sessionScope.user.getFormedEmail() eq recensionObject.getUserEmail()) and (sessionScope.modifyRecensionId eq '-1')}">
                                <div>
                                    <a href="${pageContext.request.contextPath}/DeleteRecencion?recensionID=${recensionObject.getRecensionId()}" class="deleteRecensionLink">Delete</a>                
                                    <a href="${pageContext.request.contextPath}/ModifyRecencionServlet?modifyRecension=true&recensionID=${recensionObject.getRecensionId()}" class="deleteRecensionLink">Modify</a>
                                </div>
                            </c:if>
                            <c:if test="${(sessionScope.user.getFormedEmail() eq recensionObject.getUserEmail()) and (recensionObject.getRecensionId() eq sessionScope.modifyRecensionId)}">
                                <div>
                                    <form id="modifyRecentionForm" action="${pageContext.request.contextPath}/UpdateRecencion?recensionID=${recensionObject.getRecensionId()}" method="POST">
                                        <textarea rows="7" cols="20" name="modifyRecensionText" placeholder="Input Your Book Recension" class="recensionTextArea" required></textarea>
                                        <input type="submit" class="btn btn-lg btn-primary" value="Modify"/>
                                        <a href="${pageContext.request.contextPath}/ModifyRecencionServlet?modifyRecension=false" class="deleteRecensionLink">Cancel</a>
                                    </form>
                                </div>
                            </c:if>
                        </div>
                    </c:forEach>
                </div>
                <c:if test="${sessionScope.modifyRecensionId eq '-1'}">
                    <form id="recentionForm" action="${pageContext.request.contextPath}/AddRecencion" method="POST">
                        <textarea rows="7" cols="20" name="recensionText" placeholder="Input Your Book Recension" class="recensionTextArea" required></textarea>
                        <input type="submit" class="btn btn-lg btn-primary" style="margin-left: 10%;" value="Leave Recension"/>
                    </form>
                </c:if>
            </div>

        </div>
    </div>



    <%@include file="../WEB-INF/jspf/menu.jspf" %>
</div>