<%@include file="common.jsp" %>
<body>
<div class="table-responsive">
    <table class="table">
        <thead class="Table cell">
        <td>ID</td>
        <td>姓名</td>
        <td>密码</td>
        <td>number</td>
        </thead>
        <tbody>
        <c:forEach var="user" items="${user}">
            <tr class="success">
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.password}</td>
                <td>${user.number}</td>
            </tr>
        </c:forEach>>
        </tbody>
    </table>
    <a href="/export"><button type="button" class="btn btn-primary">导出</button></a>
    <form class="form-horizontal" id="form_table" action="/import" enctype="multipart/form-data" method="post">
        <br/>
        <br/>
        <button type="submit" class="btn btn-primary">导入</button>
        <input class="form-input" type="file" name="filename"></input>
    </form>
</div>
</body>