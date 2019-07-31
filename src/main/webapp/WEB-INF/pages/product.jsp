<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"></jsp:useBean>
<tags:master pageTitle="Products">
    <div>
        <p>${product.description}</p>
        <c:if test="${not empty param.message}">
            <p>${param.message}</p>
        </c:if>
        <c:if test="${not empty error}">
            <p class="error">Error</p>
        </c:if>
        <p>
            <img class="product-tile"
                 src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
            <br>
            <fmt:formatNumber value="${product.price}" type="currency"
                              currencySymbol="${product.currency.symbol}"/><br>
            Current stock: ${product.stock}
        </p>
        <form method="post" action="<c:url value="/products/${product.id}"/>">
            <input name="quantity" type="number" min="1" value="${param.quantity == null? 1 : param.quantity}">
            <button>Add to cart</button>
            <br>
            <c:if test="${not empty error}">
            <p class="error">${error}<p>
            </c:if>
        </form>
    </div>
</tags:master>