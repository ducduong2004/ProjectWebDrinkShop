<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.ResourceBundle, java.util.Locale"%>
<%
Locale locale = (Locale) session.getAttribute("locale");
if (locale == null) {
	locale = Locale.forLanguageTag("vi");
	session.setAttribute("locale", locale);
}

ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!-- Header-->
<div class="bg-dark py-5 px-5">
	<div class="container-fluid">
		<div class="text-center text-white">
			<h1 class="display-4 fw-bolder">Boba Station</h1>
			<p class="lead fw-normal text-white-50 mb-0"><%=bundle.getString("Quote") %></p>
		</div>
	</div>
</div>



<!-- Section-->
<section class="py-5">
	<div class="container px-4 px-lg-5 mt-5">
		<div class="text-center mb-4"><h1><%=bundle.getString("List") %></h1></div>
		<c:choose>
			<c:when test="${not empty productList}">
				<div
					class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
					<c:forEach var="product" items="${productList }">
						<div class="col mb-5">
							<div class="card h-100">
								<a href="./product?id=${product.id}"> <!-- Product image-->
									<img class="card-img-top bg-dark"
									src="${pageContext.request.contextPath}/image/product/${product.photo }"
									alt="${product.name}" />
								</a>
								<!-- Product details-->
								<div class="card-body p-4">
									<div class="text-center">
										<!-- Product name-->
										<h5 class="fw-bolder">${product.name}</h5>
										<!-- Product price-->
										$ ${product.price}
									</div>
								</div>
								<!-- Product actions-->
								<div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
									<div class="text-center">
										<a class="btn btn-outline-dark mt-auto" href="#"><%=bundle.getString("ViewOption") %></a>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</c:when>
			<c:otherwise>
				<div class="col-md-12">
					<p>No products available.</p>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</section>

