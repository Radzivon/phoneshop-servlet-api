<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="true" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<html>
<head>
    <title>${pageTitle}</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css"
          integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
</head>
<body class="product-list">
<header>
    <a href="${pageContext.servletContext.contextPath}">
        <img src="${pageContext.servletContext.contextPath}/images/logo.svg"/>
        PhoneShop
    </a>
   <jsp:include page="/minicart"></jsp:include>
</header>
<main>
    <jsp:doBody/>
    <p class="text" style="font-weight: bold">Recently viewed</p>
    <div class="row">
        <c:forEach var="product" items="${recentlyviewed}">
            <div class="col-1">
                <div class="card">
                    <div class="card-img-top">
                        <img class="product-tile"
                             src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                    </div>
                    <div class="card-body">
                        <div class="card-link">
                            <a href="<c:url value="/products/${product.id}"/>">${product.description}</a>
                        </div>
                        <div class="card-text">
                            <c:set var="price" value="${product.price}" scope="request"/>
                            <c:set var="currency" value="${product.currency}" scope="request"/>
                            <tags:price></tags:price>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</main>
(c) Expert Soft
</body>
</html>