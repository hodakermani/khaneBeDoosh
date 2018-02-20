<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*, domain.*, controller.*" %>

<form action="search" method="GET">
    <input type="number" name="area" placeholder="حداقل متراژ" min="0"/><br>
    <select name="buildingType">
        <option value="هیچی">--</option>
        <option value="ویلایی">ویلایی</option>
        <option value="آپارتمان">آپارتمان</option>
    </select><br>

    <select name="dealType">
        <option value="هیچی">--</option>
        <option value="خرید">خرید</option>
        <option value="اجاره">اجاره</option>
    </select><br>

    <input type="number" name="price" placeholder="حداکثر قیمت" min = "0"/><br>
    <input type="submit" value="جستجو"/>
</form>