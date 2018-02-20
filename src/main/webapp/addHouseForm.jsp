<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*, domain.*, controller.*" %>

<form action="addHouse" method="GET">
    <select name="buildingType">
        <option value="آپارتمان">آپارتمان</option>
        <option value="ویلایی">ویلایی</option>
    </select><br>

    <input type="number" name="area" placeholder="متراژ" min="0"/><br>

    <select name="dealType">
        <option value="خرید">خرید</option>
        <option value="اجاره">اجاره</option>
    </select><br>

    <input type="number" name="price" placeholder="قیمت فروش/ اجاره" min = "0"/><br>
    <input type="text" name="address" placeholder="آدرس"/><br>
    <input type="text" name="phone" placeholder="شماره تلفن"/><br>
    <input type="text" name="description" placeholder="توضیحات"/><br>
    <input type="submit" value="اضافه کردن خانه جدید"/>
</form>