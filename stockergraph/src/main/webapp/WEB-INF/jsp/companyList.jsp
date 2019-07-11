<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
    </head>
	<body>
	    <c:if test="${not empty companies}">
            <ul>
                <c:forEach var="company" items="${companies}">
                    <li>${company.symbol}</li>
                </c:forEach>
            </ul>

        </c:if>
	</body>
</html>
