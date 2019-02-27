<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Meal Edit</title>
    <style>
        table, th, td {
            border: 1px solid black;
            text-align: center;
        }
    </style>
</head>
<body>
<c:if test="${newMeal}">
    <h2>New Meal</h2>
</c:if>
<c:if test="${!newMeal}">
    <h2>Meal edit</h2>
    <jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
</c:if>
<form action="
                <c:if test="${!newMeal}">meals?action=put</c:if>
                <c:if test="${newMeal}">meals?action=new</c:if>
                    " method="post">
    <table>
        <thead>
        <tr>
            <th width="20">Id</th>
            <th width="150">DateTime</th>
            <th width="90">Description</th>
            <th width="70">Calories</th>
            <th width="50"></th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>
                <input type="number" name="id" value="${meal.id}" hidden="hidden">
            </td>
            <td>
                <input type="datetime-local" name="dateTime" value="${meal.dateTime}">
            </td>
            <td>
                <input type="text" name="description" value="${meal.description}">
            </td>
            <td>
                <input type="number" name="calories" value="${meal.calories}">
            </td>
            <td>
                <input type="submit" value="Save">
            </td>
        </tr>
        </tbody>
    </table>
</form>
<a href="meals">
    Back
</a>
</body>
</html>
