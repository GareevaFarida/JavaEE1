<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css"/>
    <title>Categories</title>
</head>

<body>
<%--<jsp:include page="navigation.jsp"/>--%>
<%@include file="/WEB-INF/views/menu.jspf" %>
<h1>Каталог</h1>
<div class="container">
    <div class="row py-2">
        <div class="col-12">
            <c:url value="/category/new" var="newCategoryUrl"/>
            <a class="btn btn-primary" href="${newCategoryUrl}">Add Category</a>
        </div>

        <div class="col-12">
            <table class="table table-bordered my-2">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Name</th>
                    <th scope="col">Actions</th>
                </tr>
                </thead>
                <tbody>

                <%-- for (ToDo toDo : (List<ToDo>) request.getAttribute("todos")) { --%>
                <c:forEach var="category" items="${requestScope.categories}">
                <tr>
                    <th scope="row">
                        <%--= toDo.getId() --%>
                        <c:out value="${category.id}"/>
                    </th>
                    <td>
                        <%--= toDo.getDescription() --%>
                        <c:out value="${category.name}"/>
                    </td>
                    <td>
                        <c:url value="/category/edit" var="editUrl">
                            <c:param name="id" value="${category.id}"/>
                        </c:url>
                        <c:url value="/category/delete" var="deleteUrl">
                            <c:param name="id" value="${category.id}"/>
                        </c:url>

                        <a class="btn btn-success" href="${editUrl}"><i class="fas fa-edit"></i></a>
                        <a class="btn btn-danger" href="${deleteUrl}"><i class="far fa-trash-alt"></i></a>
                    </td>
                </tr>
                </c:forEach>
                <%-- } --%>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>
