<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%
        session.setAttribute("auth", "logIn");
    %>
<!DOCTYPE html>
<html>
    <head>
        <title>Вход</title>
    </head>
    <body>
        <p2>Введите логин и пароль для входа</p2>
        <form action="auth" method="post">
            <p><input name="logIn"/></p>
            <p><input type="password" name="password"/></p>
            <p><input type="submit" value="Подтвердить"/></p>
        </form>
        <h2><span style="color: red; ">
            <%= request.getAttribute("msg") == null ? "" : request.getAttribute("msg") %>
        </span></h2>
    </body>
</html>
