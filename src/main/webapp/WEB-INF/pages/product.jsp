<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"></jsp:useBean>
<tags:master pageTitle="Products">
    <img class="product-tile"
         src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
    <br>
    <p>${product.description}</p><br>
    <fmt:formatNumber value="${product.price}" type="currency"
                      currencySymbol="${product.currency.symbol}"/>
</tags:master>