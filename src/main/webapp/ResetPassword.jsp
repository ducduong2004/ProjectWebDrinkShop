<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css"
	rel="stylesheet" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<link href="css/styles.css" rel="stylesheet" /><title>ResetPassword</title>
</head>
<body>
	<!-- Navbar -->
	<%@ include file="template/includes/navbar.jsp"%>

	<!-- Main Content Area -->
	<div class="container">
		<jsp:include page="template/static/EmailInput.jsp"></jsp:include>
	</div>

	<%@ include file="template/includes/footer.jsp"%>
</body>
</html>