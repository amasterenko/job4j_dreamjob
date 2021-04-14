<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.min.js" ></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    <script>
        $(document).ready(function() {
            $("#sel1").hide();
            let token = localStorage.getItem('ajaxtoken');
            $.ajax({
                type: 'GET',
                url: 'http://localhost:8080/dreamjob/cities.do',
                data: 'tk='+token,
                dataType: 'json',
            }).done (function(data) {
                let existingValue = $("#sel1").val();
                let items;
                const cities = data.cities;
                for (let i = 0; i < cities.length; i++) {
                    if (cities[i].id==existingValue) {continue;}
                    items += "<option value='" + cities[i].id + "'>" + cities[i].name + "</option>";
                }
                $("#sel1").append(items).show();
            });
        })
    </script>
    <title>Работа мечты</title>
</head>
<body>
<div class="container pt-3">
    <div class="row">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/posts.do">Вакансии</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/candidates.do">Кандидаты</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/post/edit.jsp">Добавить вакансию</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/candidates.do?id=0">Добавить кандидата</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/auth.do">
                    <c:out value="${sessionScope.user.name}"/> | Выйти</a>
            </li>
        </ul>
    </div>
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <c:if test="${candidate == null}">
                    <p>Новый кандидат</p>
                </c:if>
                <c:if test="${candidate != null}">
                    <p>Редактирование кандидата</p>
                </c:if>
            </div>
            <div class="card-body">
                <form id="form" action="<%=request.getContextPath()%>/candidates.do" method="post" class="was-validated">
                    <div class="form-group">
                        <label for="cname">Имя</label>
                        <input id="cname" type="text" class="form-control" required
                               name="cname" value="<c:out value="${candidate.name}" default=""/>">
                        <input type="hidden" name="id" value="<c:out value="${candidate.id}"/>">
                    </div>
                    <div class="form-group">
                        <label for="sel1">Город</label>
                        <select class="form-control" id="sel1" name="cityId" required>
                            <option value=<c:out value="${candidate.city.id}"/>><c:out value="${candidate.city.name}"/></option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary">Сохранить</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
