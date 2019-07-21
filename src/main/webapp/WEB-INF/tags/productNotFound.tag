<%@ tag trimDirectiveWhitespaces="true" %>
<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product"></jsp:useBean>
<h1>Sorry! Product {product.id} not found!</h1>
<img src="${pageContext.servletContext.contextPath}/images/product_not_found.png"/>
