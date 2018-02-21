<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*, domain.*, controller.*" %>

<html dir="rtl">
<body>

<jsp:include page="header.jsp"/>

<a href="khaneBeDoosh">بازگشت به صفحه اصلی</a>

<%
    House house = (House) request.getAttribute("house");
    if (house != null) {
%>

    <p>نوع ساختمان: </p>
    <%=house.getBuildingType()%>
    <p>متراژ: </p>
    <%=house.getArea()%>
    <%
        if (house.getDealType() == 0) {
    %>
        <p>نوع قرارداد : خرید</p>
        <p>قیمت: </p>
        <%=house.getPrice().getSellPrice()%>
    <%
        }
        else {
    %>
        <p>نوع قرارداد : اجاره</p>
        <p>قیمت پایه: </p>
        <%=house.getPrice().getBasePrice()%>
        <p>قیمت اجاره: </p>
        <%=house.getPrice().getRentPrice()%>
    <%
        }
    %>

    <p>آدرس: </p>
    <%=house.getAddress()%>
    <%
    if (house.getImageURL() != null) {
    %>
    <p>لینک عکس: </p>
    <img src="<%= house.getImageURL() %>">
    <%
        }
    %>

    <p>توضیحات: </p>
    <%=house.getDescription()%>

    <%
        String type = (String) request.getAttribute("type");
        String btnMsg = (String) request.getAttribute("btn-msg");
        String name = (String) request.getAttribute("name");

        if (btnMsg == null || btnMsg == "") {
            type = "submit";
            btnMsg = "دریافت شماره‌ی مالک/ مشاور";
        }
        if (name == null)
            name = "بهنام همایون";
    %>
    <form action="houseDetails?parentName=<%=house.getParentName()%>&id=<%=house.getId()%>&name=<%=name%>" method="POST">
        <button type=<%=type%>> <%=btnMsg%></button>
    </form>
<%
    }
    else {
%>
    <p>خانه با مشخصات درخواستی یافت نشد.</p>
<%
    }
%>

</body>
</html>
