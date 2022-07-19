<%--
  Created by IntelliJ IDEA.
  User: Onisi
  Date: 19.07.2022
  Time: 9:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<br>
<section>
    <h2>Validation Error</h2>
<thead>
<table border="1" cellpadding="8" cellspacing="0">
<tr>
    <th>Field</th>
    <th>Error message</th>
</tr>
</thead>
<c:forEach items="${requestScope.violations}" var="violation">
    <jsp:useBean id="violation" type="ru.javawebinar.topjava.web.validation.Violation"/>
    <tr>
        <td>
            <%=violation.getFieldName()%>
        </td>
        <td>
            <%=violation.getMessage()%>
        </td>
    </tr>
</c:forEach>
</table>
<section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
