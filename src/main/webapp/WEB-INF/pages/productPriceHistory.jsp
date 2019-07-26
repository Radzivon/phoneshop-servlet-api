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
        <c:forEach var="productprice" items="${product.prices}">
            <tr>
                <td>
                        ${productprice.date}
                </td>
                <td class="price">
                                            <c:set var="price" value="${productprice.price}" scope="request"/>
                                            <c:set var="currency" value="${product.currency}" scope="request"/>
                                            <tags:price></tags:price>
                </td>
            </tr>
        </c:forEach>
    </table>
</tags:master>