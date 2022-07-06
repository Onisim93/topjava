<%--
  Created by IntelliJ IDEA.
  User: Onisi
  Date: 06.07.2022
  Time: 15:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="meal" class="ru.javawebinar.topjava.model.Meal" scope="request"/>
<html>
<head>
    <title>Edit meal</title>
</head>
<body>
    <a href="${pageContext.request.contextPath}/">HOME</a>

    <h3><%= meal.getId() == null ? "Create meal" : "Edit meal" %> </h3>
    <br>

    <form method="post" action="meals">
        <table>
        <input name="id" value="${meal.id}" hidden>
        <tr>
            <td><label>DateTime:</label></td>
            <td><input name="dateTime" type="datetime-local" required value="${meal.dateTime}" ></td>
        </tr>
        <tr>
            <td><label>Description:</label></td>
            <td><input name="description" type="text" maxlength="150" required value="${meal.description}"></td>
        </tr>
        <tr>
            <td><label>Calories:</label></td>
            <td><input name="calories" type="number" min="10" max="5000" required value="${meal.calories}"></td>
        </tr>
        </table>
        <br>
            <button type="submit">Save</button>
            <button type="reset">Cancel</button>


    </form>


</body>
</html>
