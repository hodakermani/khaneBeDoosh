<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*, domain.*, controller.*" %>

<table>
    <tr>
        <td>
            <p>نام کاربری: </p>
        </td>
        <td>
            <%
                Map<String, User> users = App.getUsers();
                User behnam = users.get("بهنام همایون");
            %>
            <%= behnam.getName() %>
        </td>
    </tr>
    <tr>
        <td>
            <p>اعتبار شما: </p>
        </td>
        <td id="balance">
            <%= ((Individual)behnam).getBalance() %> تومان
        </td>
    </tr>
</table>
