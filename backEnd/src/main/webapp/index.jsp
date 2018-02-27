<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*, domain.*, controller.*" %>

<html dir="rtl">
<body>

<%
    if (request.getAttribute("msg") != null) {
%>
<h4><%= request.getAttribute("msg") %></h4>
<%
    }
%>

<jsp:include page="header.jsp"/>
<jsp:include page="searchForm.jsp"/>
<jsp:include page="addHouseForm.jsp"/>
<jsp:include page="addBalanceForm.jsp"/>

</body>
</html>