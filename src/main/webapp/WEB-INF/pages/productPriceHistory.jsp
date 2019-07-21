<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"></jsp:useBean>
<tags:master pageTitle="Products">
    <br>
    <h2>${product.description}</h2>
    <br>
    <table>
        <thead>
        <tr>
            <td>Start date
            </td>
            <td class="price">Price
            </td>
        </tr>
        </thead>
        <c:forEach var="productprice" items="${product.price}">
            <tr>
                <td>
                        ${productprice.date}
                </td>
                <td class="price">
                    <fmt:formatNumber value="${productprice.price}" type="currency"
                                      currencySymbol="${product.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
</tags:master>