<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="query" required="true" %>
<%@ attribute name="sort" required="true" %>
<%@ attribute name="order" required="true" %>

<a href="${pageContext.servletContext.contextPath}/products?query=${param.query}&sort=${sort}&order=asc">&#8595</a>
<a href="${pageContext.servletContext.contextPath}/products?query=${param.query}&sort=${sort}&order=desc">&#8593</a>