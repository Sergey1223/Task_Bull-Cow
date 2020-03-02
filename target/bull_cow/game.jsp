<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Бык-Корова</title>
        <script type="text/javascript" src="webjars/jquery/3.4.1/jquery.min.js"></script>
        <script type="text/javascript" src="js/main.js"></script>
        <link rel="stylesheet" type="text/css" href="styles.css">
    </head>
    <body>
        <table width="100%" cellpadding="1">
            <tr>
                <td colspan="4"> <h2 align="center">Бык-Корова</h2> </td>
            </tr>
            <tr>
                <td colspan="4" align="left">
                    <a id="rating" href="user">Пользователь: ${user}</a>
                </td>
            </tr>
            <tr>
                <td width="50%" colspan="2">
                    <div class="btn" id="newGameButton">Новая игра</div>
                </td>
                <td width="50%" colspan="2">
                    <div class="btn" id="ratingButton">Рейтинг</div>
                </td>
            </tr>
            <tr>
                <td width="35%">
                    <div class="btn" id="resetButton">Сброс</div>
                </td>
                <td width="30%" colspan="2">
                    <textarea id="inputArea" readonly></textarea>
                </td>
                <td width="35%">
                    <div class="btn" id="sendButton">Отправить</div>
                </td>
            </tr>
            <tr>
                <td colspan="4"><div id="digitPanel"></div> </td>
            </tr>
        </table>
        <table>
            <caption>Ходы</caption>
            <thead>
                <tr>
                    <th>Попытка</th>
                    <th>Ваше число</th>
                    <th>Результат</th>
                </tr>
            </thead>
            <tbody id="outputArea">
            </tbody>
        </table>
    </body>
</html>
