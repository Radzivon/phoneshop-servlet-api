<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:formatNumber value="${price}" type="currency"
                  currencySymbol="${currency.symbol}"/>
