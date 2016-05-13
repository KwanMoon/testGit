<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공통 예외 처리</title>
</head>
<body>

	<h2>${exception.getMessage()}</h2>
	
	<ul>
		<c:forEach items="${exception.getStackTrace()}" var="item">
			<li>
				${item.toString()}
			</li>
		</c:forEach>
	</ul>
</body>
</html>