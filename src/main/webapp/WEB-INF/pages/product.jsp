<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"></jsp:useBean>
<tags:master pageTitle="Products">
    <div>
        <img class="product-tile"
             src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
        <br>
        <p>${product.description}<br>
            <fmt:formatNumber value="${product.price}" type="currency"
                              currencySymbol="${product.currency.symbol}"/><br>
            Current stock: ${product.stock}
        </p>
        <form>
            <input name="quantity">
            <button>Add to cart</button>
        </form>
    </div>
</tags:master>