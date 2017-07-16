<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@page pageEncoding="UTF-8"%>
<head>
    <style>
        .green {
            color: green;
        }

        .red {
            color: red;
        }
    </style>
    <meta charset="UTF-8">
    <title>Meals</title>
</head>
<html>
<body>
<h3><a href="index.html">Home</a></h3>
<h3>Meals</h3>
<table width="500px" border = "1" cellpadding="1">
    <tr>
        <th>Id</th>
        <th>Descriptions</th>
        <th>Date and Time</th>
        <th>Calories</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <c:forEach items="${meals}" var="meal">
        <c:set var="color" scope="page"/>
        <c:choose>
            <c:when test = "${!meal.exceed}">
                <tr class="green">
            </c:when>
            <c:otherwise>
                <tr class="red">
            </c:otherwise>
        </c:choose>
        <td >${meal.id}</td>
        <td >${meal.description}</td>
        <javatime:format value="${meal.dateTime}" pattern="dd.MM.yyyy HH:mm:ss" var="parsedDate" />
        <td >${parsedDate}</td>
        <td >${meal.calories}</td>
        <td ><a href="meals?action=edit&mealsId=<c:out value="${meal.id}"/>">Edit</a></td>
        <td ><a href="meals?action=delete&mealsId=<c:out value="${meal.id}"/>">Delete</a></td>
        </tr>
    </c:forEach>
</table>
<p><a href="meals?action=insert">Add Meal</a></p>
</body>
</html>