<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"></jsp:useBean>
<tags:master pageTitle="Cart">
    <div>
        <c:if test="${not empty param.message}">
            <span style="color:green">${param.message}</span>
        </c:if>
    </div>
    <div>
        <c:if test="${not empty cart}">
            <form method="post">
                <table class="table-responsive">
                    <thead>
                    <tr>
                        <td>Image</td>
                        <td>Description
                        </td>
                        <td class="price">Price
                        </td>
                        <td>Stock</td>
                        <td>Quantity</td>
                        <td></td>
                    </tr>
                    </thead>
                    <c:forEach var="carItem" items="${cart.cartItems}" varStatus="status">
                        <tr>
                            <td>
                                <img class="product-tile"
                                     src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${cartItem.product.imageUrl}">
                            </td>
                            <td>
                                <a href="<c:url value="/products/${carItem.product.id}"/>">${carItem.product.description}</a>
                            </td>
                            <td class="price">
                                <a href="<c:url value="/products/prices/${carItem.product.id}"/>">
                                    <c:set var="price" value="${carItem.product.price}" scope="request"/>
                                    <c:set var="currency" value="${carItem.product.currency}" scope="request"/>
                                    <tags:price></tags:price>
                                </a>
                            </td>
                            <td>${carItem.product.stock}</td>
                            <td><input name="quantity" type="number" min="1" style="text-align: right"
                                       value="${not empty errors[status.index] ? paramValues.quantity[status.index] : carItem.quantity}">
                                <c:if test="${not empty errors[status.index]}"><br>
                                    <span style="color:red">${errors[status.index]}</span>
                                </c:if>
                                <input type="hidden" name="productId" value="${carItem.product.id}">
                            </td>
                            <td>
                                <button formaction="${pageContext.servletContext.contextPath}/cart/deleteCartItem/${carItem.product.id}">
                                    Delete
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td></td>
                        <td></td>
                        <td><c:set var="price" value="${cart.totalCost}" scope="request"/>
                            <tags:price></tags:price></td>
                        <td></td>
                        <td><p style="text-align: right">${cart.totalQuantity}</p></td>

                        <td></td>
                    </tr>
                </table>
                <button>Update</button>
            </form>
        </c:if>
    </div>
</tags:master>