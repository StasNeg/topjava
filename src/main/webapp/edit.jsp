<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h3>Meals</h3>
<form method="POST" action="meals" name="frmAddUser">
    <%--Meal ID : <input type="text" readonly="readonly" name="id" value="<c:out value="${meal.id}" />"/>--%>
    <%--<br/>--%>

    Description : <input type="text" name="description" value="<c:out value="${meal.description}" />"/>
    <br/>
    Data Time (format 22.01.2017 22:00:00): <input type="datetime" name="dateTime"
                                                   value="<javatime:format pattern="dd.MM.yyyy HH:mm:ss" value="${meal.dateTime}" />"/>
    <br/>
    Calories : <input type="text" name="calories" value="<c:out value="${meal.calories}" />"/>
    <br/>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>
