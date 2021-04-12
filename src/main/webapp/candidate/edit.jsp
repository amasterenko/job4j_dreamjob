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
        $(document).ready(function () {
            $("#sel1").hide();
            $.ajax({
                type: 'GET',
                url: 'http://localhost:8080/dreamjob/cities.do',
                dataType: 'json'
            }).done (function(data) {
                let items = "<option disabled selected value> -- Город -- </option>";
                const cities = data.cities;
                for (let i = 0; i < cities.length; i++) {
                    items += "<option value='" + cities[i].id + "'>" + cities[i].name + "</option>";
                }
                $("#sel1").append(items);
            });
            let searchParams = new URLSearchParams(window.location.search);
            let param = searchParams.get('id');
            let path = 'http://localhost:8080/dreamjob/candidates.do';
            if(param===null) {
                $('#сhead').text('Новый кандидат');
                $("#sel1").prop("selected", false).show();
                $('#form').attr('action', path+'?id=0');
                return true;
            }
            $.ajax({
                type: 'GET',
                url: path,
                data: 'id=' + param,
                dataType: 'json'
            }).done(function (data) {
                $('#cname').val(data.name);
                $("#sel1").val(data.city.id).show();
                $('#form').attr('action', path+'?id='+param);
            });
        });
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
                <a class="nav-link" href="<%=request.getContextPath()%>/candidate/edit.jsp">Добавить кандидата</a>
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
                <p id="сhead">Редактирование кандидата</p>
            </div>
            <div class="card-body">
                <form id="form" action="" method="post" class="was-validated">
                    <div class="form-group">
                        <label for="cname">Имя</label>
                        <input id="cname" type="text" class="form-control" name="cname" placeholder="Имя" required>
                    </div>
                    <div class="form-group">
                        <label for="sel1">Город</label>
                        <select class="form-control" id="sel1" name="cityId" required></select>
                    </div>
                    <button type="submit" class="btn btn-primary">Сохранить</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
