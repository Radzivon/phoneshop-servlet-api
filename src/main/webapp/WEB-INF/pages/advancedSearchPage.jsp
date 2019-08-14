<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="products" type="java.util.ArrayList" scope="request"></jsp:useBean>
<tags:master pageTitle="Checkout">
    <div>
        <c:if test="${not empty hasError}">
            <p class="error" style="color: red">Error</p>
        </c:if>
        <form method="post">
            <label>Description<input name="description"></label>
            <c:if test="${not empty errorDescription}">
                <p style="color: red">${errorDescription}</p>
            </c:if>
            <label>Min price <input name="minPrice" type="number"></label>
            <c:if test="${not empty errorMinPrice}">
                <p style="color: red">${errorMinPrice}</p>
            </c:if>
            <br>
            <label>Max price <input name="maxPrice" type="number"></label>
            <c:if test="${not empty errorMaxPrice}">
                <p style="color: red">${errorMaxPrice}</p>
            </c:if>
            </label><br>
            <label>Min stock <input name="minStock" type="number"></label>'
            <c:if test="${not empty errorMinStock}">
                <p style="color: red">${errorMinStock}</p>
            </c:if>
            <label>Max stock <input name="maxStock" type="number"></label>'
            <c:if test="${not empty errorMaxStock}">
                <p style="color: red">${errorMaxStock}</p>
            </c:if>
            <button formaction="<c:url value="/search"/>" formmethod="post">Search</button>
        </form>
        <c:if test="${empty hasError && not empty products}">
            <table class="table-responsive">
                <thead>
                <tr>
                    <td>Image</td>
                    <td>Description
                    </td>
                    <td class="price">Price
                    </td>
                    <td>Stock
                    </td>
                </tr>
                </thead>
                <c:forEach var="product" items="${products}">
                    <tr>
                        <td>
                            <img class="product-tile"
                                 src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                        </td>
                        <td>
                            <a href="<c:url value="/products/${product.id}"/>">${product.description}</a>
                        </td>
                        <td class="price">
                            <a href="<c:url value="/products/prices/${product.id}"/>">
                                <c:set var="price" value="${product.price}" scope="request"/>
                                <c:set var="currency" value="${product.currency}" scope="request"/>
                                <tags:price></tags:price>
                            </a>
                        </td>
                        <td>
                                ${product.stock}
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </div>
</tags:master>