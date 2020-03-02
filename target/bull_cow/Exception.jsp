<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Exception</title>
    </head>
    <body>
        <h2>Exception</h2>
        <p>Type <%= pageContext.getException().getClass().toString() %></p>
        <p>Type <%= pageContext.getException().getMessage() %></p>
        <p>Type <%= pageContext.getException().printStackTrace() %></p>

    </body>
</html>
