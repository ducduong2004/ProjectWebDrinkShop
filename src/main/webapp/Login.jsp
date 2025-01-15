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
<link href="css/styles.css" rel="stylesheet" />

<title>Login into Boba Station</title>
</head>


<body>
	
	<!-- Navbar -->
	<%@ include file="/template/includes/navbar.jsp"%>

	<!-- Content -->
	<section class="bg-light py-3 py-md-5">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-8">
					<div class="card border border-light-subtle rounded-3 shadow-sm">
						<div class="card-body p-3 p-md-4 p-xl-5">
							<div class="text-center mb-3">
								<a href="#!"> <img src="image/website/logo.png" alt="Logo"
									width="100" height="100">
								</a>
							</div>
							<h2 class="fs-6 fw-normal text-center text-secondary mb-4">
								Sign in to your account</h2>
							<form action="login" method="POST">
								<div class="row gy-2 overflow-hidden">
									<div class="col-12">
										<div class="form-floating mb-3">
											<input type="email" class="form-control" name="email"
												placeholder="name@example.com" required> <label
												for="email" class="form-label">Email</label>
										</div>
									</div>
									<div class="col-12">
										<div class="form-floating mb-3">
											<input type="password" class="form-control" name="password"
												id="password" placeholder="Password" required> <label
												for="password" class="form-label">Password</label>
										</div>
									</div>
									<div class="col-12">
										<div class="d-flex gap-2 justify-content-between">
											<div class="form-check">
												<input class="form-check-input" type="checkbox" value=""
													name="rememberMe" id="rememberMe"> <label
													class="form-check-label text-secondary" for="rememberMe">
													Keep me logged in </label>
											</div>
											<a
												href="${pageContext.request.contextPath}/ResetPassword.jsp"
												class="link-primary text-decoration-none">Forgot
												password?</a>
										</div>
									</div>
									<div class="col-12">
										<c:if test="${ not  empty requestScope.message}">
											<p style="color: red;" class="text-center">
												${requestScope.message} !</p>
										</c:if>
									</div>
									<div class="col-12">
										<div class="d-grid my-3">
											<button class="btn btn-primary btn-lg" type="submit">Log
												in</button>
										</div>
									</div>
									<div class="col-12">
										<p class="m-0 text-secondary text-center">
											Don't have an account? <a href="Register.jsp"
												class="link-primary text-decoration-none">Sign up</a>
										</p>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<%@ include file="template/includes/footer.jsp"%>


</body>
</html>