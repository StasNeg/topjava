<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<style>
    .green {
        color: green;
    }
    .red {
        color: red;
    }
</style>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>

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
        <c:choose>
            <c:when test = "${!meal.exceed}">
                <tr>
                    <td class="green">${meal.id}</td>
                    <td class="green">${meal.description}</td>
                    <javatime:format value="${meal.dateTime}" pattern="yyyy-MM-dd HH:mm" var="parsedDate" />
                    <td class="green">${parsedDate}</td>
                    <td class="green">${meal.calories}</td>
                    <td class="green"><a href="meals?action=edit&mealsId=<c:out value="${meal.id}"/>">Edit</a></td>
                    <td class="green"><a href="meals?action=delete&mealsId=<c:out value="${meal.id}"/>">Delete</a></td>
                </tr>
            </c:when>
            <c:otherwise>
                <tr>
                    <td class="green">${meal.id}</td>
                    <td class="red">${meal.description}</td>
                    <javatime:format value="${meal.dateTime}" pattern="yyyy-MM-dd HH:mm" var="parsedDate" />
                    <td class="red">${parsedDate}</td>
                    <td class="red">${meal.calories}</td>
                    <td class="red"><a href="meals?action=edit&mealsId=<c:out value="${meal.id}"/>">Edit</a></td>
                    <td class="red"><a href="meals?action=delete&mealsId=<c:out value="${meal.id}"/>">Delete</a></td>
                </tr>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</table>

</body>
</html>