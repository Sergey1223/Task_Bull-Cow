<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    session.setAttribute("auth", "signUp");
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Регистрация</title>
    </head>
    <body>
        <h2>Введите логин и пароль для регистрации</h2>
        <p>Логин должен содержать не менее 4-х и не более 15-ти символов. Не допускаются символы кириллицы и пробелы</p>
        <p>Пароль должен содеражать не менее 4-х и не более 20-ти символов</p>
        <form action="auth" method="post"  autocomplete="off">
            <p>
                <input type="text" name="logIn" placeholder="Логин" required pattern="[^ А-Яа-яЁё]{4,15}"/>
            </p>
            <p>
                <input type="password" name="password" placeholder="Пароль" required pattern="{4, 20}"/>
            </p>
            <p>
                <button>Подтвердить</button>
            </p>
        </form>
        <h2><span style="color: red; ">
            <%= request.getAttribute("msg") == null ? "" : request.getAttribute("msg") %>
        </span></h2>
    </body>
</html>
