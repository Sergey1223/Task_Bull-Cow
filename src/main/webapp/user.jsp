<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Пользователь</title>
        <link rel="stylesheet" type="text/css" href="styles.css">
    </head>
    <body>
        <table>
            <caption>Пользователь: ${user}</caption>
            <thead>
                <tr>
                    <th>Дата</th>
                    <th>Результат</th>
                </tr>
            </thead>
            <tbody id="userAttempts">
                <c:forEach var="elem" items="${userAttempts}">
                    <tr>
                        <td>${elem.getDate()}</td>
                        <td>${elem.getValue()}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
