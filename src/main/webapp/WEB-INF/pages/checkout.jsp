<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="order" type="com.es.phoneshop.model.order.Order" scope="request"></jsp:useBean>
<tags:master pageTitle="Checkout">
    <div>
        <c:if test="${not empty hasError}">
            <p class="error" style="color: red">Error</p>
        </c:if>
        <form method="post">
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
                    <td><p style="text-align: right">${order.totalQuantity}</p></td>
                </tr>
            </table>
            <label>First Name<input name="firstName"></label>
            <c:if test="${not empty errorFirstName}">
                <p style="color: red">${errorFirstName}</p>
            </c:if>
            <label>Last Name <input name="lastName"></label>
            <c:if test="${not empty errorLastName}">
                <p style="color: red">${errorLastName}</p>
            </c:if>
            <br>
            <label>Phone Number <input name="phoneNumber"></label>
            <c:if test="${not empty errorPhoneNumber}">
                <p style="color: red">${errorPhoneNumber}</p>
            </c:if>
            <br>
            <label>Delivery Mode<select name="deliveryMode">
                <option value="courier">Courier</option>
                <option value="storePickUp">Store pick up</option>
            </select>
            </label><br>
            <label>Delivery date <input name="deliveryDate" value="${order.deliveryDate}" readonly="true"></label><br>
            <label>Delivery address <input name="deliveryAddress"></label>'
            <c:if test="${not empty errorDeliveryAddress}">
                <p style="color: red">${errorDeliveryAddress}</p>
            </c:if>
            <br>
            <label>Payment method <select name="paymentMethod">
                <option value="cash">Cash</option>
                <option value="creditCard">Credit card</option>
            </select></label><br>
            <button formaction="<c:url value="/checkout"/>" formmethod="post">Place order</button>
        </form>
    </div>
</tags:master>