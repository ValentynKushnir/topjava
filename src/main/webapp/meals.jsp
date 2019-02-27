<%--
  Created by IntelliJ IDEA.
  User: valentyn.kushnir
  Date: 2/26/2019
  Time: 12:28 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <style>
        .red {
            color: red;
        }

        table, th, td {
            border: 1px solid black;
            text-align: center;
        }
    </style>
    <title>Meals</title>
</head>
<body>
<jsp:useBean id="meals" scope="request" type="java.util.List"/>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<form action="meals" method="get">
    <input type="text" name="action" value="new" hidden>
    <input type="submit" value="Add new meal">
</form>
<table>
    <thead>
    <tr>
        <th width="20">Id</th>
        <th width="150">DateTime</th>
        <th width="90">Description</th>
        <th width="70">Calories</th>
        <th width="50"></th>
        <th width="50"></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="meal" items="${meals}">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr class="<c:if test="${meal.exceeds}">red</c:if>">
            <td width="20">
                <jsp:text>${meal.id}</jsp:text>
            </td>
            <td width="150">
                <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
                <fmt:formatDate value="${parsedDateTime}" pattern="dd.MM.yyyy HH:mm"/>
            </td>
            <td width="70">
                <jsp:text>${meal.description}</jsp:text>
            </td>
            <td width="70">
                <jsp:text>${meal.calories}</jsp:text>
            </td>
            <td>
                <form action="meals" method="get">
                    <input type="number" name="id" hidden="hidden" value="${meal.id}"/>
                    <input type="text" name="action" value="edit" hidden/>
                    <input type="submit" value="Edit"/>
                </form>
            </td>
            <td>
                <form action="meals" method="post">
                    <input type="number" name="id" hidden="hidden" value="${meal.id}"/>
                    <input type="text" name="action" value="delete" hidden/>
                    <input type="submit" value="Delete"/>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
