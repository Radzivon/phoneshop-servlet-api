<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="order" type="com.es.phoneshop.model.order.Order" scope="request"></jsp:useBean>
<tags:master pageTitle="Checkout">
    <div>
        <div class="card">
            <table class="table-responsive">
                <thead>
                <tr>
                    <td>Image</td>
                    <td>Description
                    </td>
                    <td class="price">Price
                    </td>
                    <td>Quantity</td>
                </tr>
                </thead>
                <c:forEach var="cartItem" items="${order.cartItems}" varStatus="status">
                    <tr>
                        <td>
                            <img class="product-tile"
                                 src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${cartItem.product.imageUrl}">
                        </td>
                        <td>
                            <a href="<c:url value="/products/${cartItem.product.id}"/>">${cartItem.product.description}</a>
                        </td>
                        <td class="price">
                            <a href="<c:url value="/products/prices/${cartItem.product.id}"/>">
                                <c:set var="price" value="${cartItem.product.price}" scope="request"/>
                                <c:set var="currency" value="${cartItem.product.currency}" scope="request"/>
                                <tags:price></tags:price>
                            </a>
                        </td>
                        <td>
                            <p>${cartItem.quantity}</p>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td></td>
                    <td></td>
                    <td><c:set var="price" value="${order.subTotalCost}" scope="request"/>
                        <tags:price></tags:price></td>
                    <td></td>
                </tr>
            </table>

            <div class="card-body">
                <div class="card-text">First name: ${order.firstName}</div>
                <div class="card-text">Last name: ${order.lastName}</div>
                <div class="card-text">Phone number: ${order.phoneNumber}</div>
                <div class="card-text">Delivery Mode: ${order.deliveryMode} Delivery
                    cost: ${order.deliveryMode.deliveryCost}</div>
                <div class="card-text">Delivery date: ${order.deliveryDate}"</div>
                <div class="card-text">Delivery address:${order.deliveryAddress}</div>
                <div class="card-text">Payment method: ${order.paymentMethod}</div>
                <div class="card-text" style="font-weight: bold">Total cost: ${order.totalCost}</div>
            </div>
        </div>
        <a href="<c:url value="/products"/>">Go back to shopping</a>
    </div>
</tags:master>