<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order History</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

<div class="container py-5">
    <h1 class="mb-4">Your Order History</h1>

    <c:choose>
        <c:when test="${not empty orders}">
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Product Name</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Order Date</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${orders}">
                        <tr>
                            <td>${order.id}</td>
                            <td>${order.productName}</td>
                            <td>${order.quantity}</td>
                            <td>${order.price}</td>
                            <td>${order.orderDate}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div class="alert alert-warning" role="alert">
                You have not made any purchases yet.
            </div>
        </c:otherwise>
    </c:choose>

    <div class="mt-4">
        <a href="${pageContext.request.contextPath}/Homepage" class="btn btn-primary">Continue Shopping</a>
    </div>
</div>

</body>
</html>