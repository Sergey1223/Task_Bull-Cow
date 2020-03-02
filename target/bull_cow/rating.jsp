<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Рейтинг</title>
        <link rel="stylesheet" type="text/css" href="styles.css">
    </head>
    <body>
        <table>
            <caption>Рейтинг</caption>
            <thead>
                <tr>
                    <th>Место</th>
                    <th>Пользователь</th>
                    <th>Среднее число попыток</th>
                </tr>
            </thead>
            <tbody id="rating">
                <c:forEach var="elem" items="${rating}">
                    <tr>
                        <td>${elem.getValue()}</td>
                        <td>${elem.getKey()}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>