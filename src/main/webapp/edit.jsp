<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="POST" action='MealsServlet' name="frmAddUser">
    Meal ID : <input type="text" readonly="readonly" name="mealid"
                     value="<c:out value="${meals.id}" />" /> <br />
    Description : <input type="text" name="description"
        value="<c:out value="${meals.description}" />" /> <br />
    Data Time : <input
        type="text" name="dob"
        value="<javatime:format pattern="MM/dd/yyyy HH:mm:ss" value="${meals.dateTime}" />" /> <br />
    Calories : <input type="text" name="calories"
                   value="<c:out value="${meals.calories}" />" /> <br /> <input
        type="submit" value="Submit" />
</form>
</body>
</html>
