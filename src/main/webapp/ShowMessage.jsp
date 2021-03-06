<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
<head>
    <title>Show Message</title>
</head>
<body>

<h2>Show Message with c:out</h2>
<c:out value="${param.name}" escapeXml="true" />

<h2>Show Message with spring:message</h2>
<br>1: <spring:message text="${param.name}" />
<br>2: <spring:message text="${param.name.replace('${', '{').replace('#{', '{')}" />
<br>3: <spring:message text="${param.name.replace('${', '&#x24;{').replace('#{', '&#x23;{')}" />

</body>
</html>
