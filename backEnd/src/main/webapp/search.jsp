<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*, domain.*, controller.*" %>

<html dir="rtl">
<body>

<jsp:include page="header.jsp"/>

<a href="khaneBeDoosh">بازگشت به صفحه اصلی</a>

<%
    List<House> result = (List<House>)request.getAttribute("houses");
    if (result.size() == 0) {
%>
    <p>متاسفانه خانه‌ای با مشخصات درخواستی یافت نشد :(</p>
<%
    }
    for(House house: result) {
        // sell price only
        if (house.getDealType() == 0) {
%>
            <p>قیمت: </p>
            <p><%= house.getPrice().getSellPrice() %></p>
            <p>متراژ: </p>
            <p><%= house.getArea() %></p>
            <p>نوع: فروش</p>
<%
        }
        else {
%>
            <p>قیمت پایه: </p>
            <p><%= house.getPrice().getBasePrice() %></p>
            <p>مبلغ اجاره: </p>
            <p><%= house.getPrice().getRentPrice() %></p>
            <p>متراژ: </p>
            <p><%= house.getArea() %></p>
            <p>نوع: رهن و اجاره</p>
<%
        }
    if (house.getImageURL() != null) {
%>
        <p>لینک عکس: </p>
        <img src="<%= house.getImageURL() %>">
<%
    }
%>

    <br><a href="houseDetails?parentName=<%=house.getParentName()%>&id=<%=house.getId()%>">اطلاعات بیشتر</a>
    <hr>
<%
    }
%>

</body>
</html>
