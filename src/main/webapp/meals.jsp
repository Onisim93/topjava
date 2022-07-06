<%@ page import="java.time.format.DateTimeFormatter" %><%--
  Created by IntelliJ IDEA.
  User: Onisi
  Date: 04.07.2022
  Time: 14:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>

<h2>Meals List</h2>

<a href="meals?action=create">Create meal</a>
<br>
<br>

<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th><a>Update</a></th>
        <th><a>Delete</a></th>
    </tr>
    <c:forEach var="mealTo" items="${mealsList}">
        <jsp:useBean id="mealTo" class="ru.javawebinar.topjava.model.MealTo"/>
        <tr class=<%= mealTo.isExcess() ? "red" : "green" %> >
            <td> <%= mealTo.getDateTime().toString().replace("T", " ") %></td>
            <td> ${mealTo.description} </td>
            <td> ${mealTo.calories} </td>
            <td> <a href="${pageContext.request.contextPath}/meals?action=update&id=${mealTo.id}">Update</a> </td>
            <td> <a href="${pageContext.request.contextPath}/meals?action=delete&id=${mealTo.id}">Delete</a> </td>
        </tr>
    </c:forEach>
</table>



</body>

<style>
    table {
        border: 2px solid grey;
        border-collapse: collapse;
    }
    th {
        border: 1px solid grey;
        padding: 10px;
    }
    td {
        border: 1px solid grey;
        padding: 5px 7px;
    }
    .red {
        color: red;
    }
    .green {
        color: green;
    }
</style>

</html>



