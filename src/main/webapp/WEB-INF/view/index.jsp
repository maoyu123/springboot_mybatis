<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@include file="common.jsp" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
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
</div>
    <form class="form-horizontal" id="form_table" action="/action/import" enctype="multipart/form-data" method="post">
        <br/>
        <br/>
        <button type="submit" class="btn btn-primary">导入</button>
        <input class="form-input" type="file" name="filename"/>
    </form>
    <input type="button" value="查询" onclick="listUser()"/>
    <%--<button type="button" class="btn btn-primary" onclick="exportExcel()">导出</button>--%>
    <a href="/action/exportExcel"><button type="button" class="btn btn-primary">导出</button></a>
</body>

<script type="text/javascript">
    function listUser(){
        $.ajax({
            url:'http://localhost:8080/action/listUser',
            type:'get',
            datatype:'json',
            // data:{
            //     "user":$("#user").valueOf()
            // },
            success:function(data){
                // $("#restult").val(data.resultInfo);
                var json = JSON.stringify(data);
                alert(json);
                var str = "";
                str +="<table><thead><tr><th>id</th><th>姓名</th><th>密码</th><th>number</th></tr></thead><tbody>";
                $.each(data,function(index,element){
                    str +="<tr><td>"+element['id']+"</td>&nbsp&nbsp<td>"+element['name']+"</td><td>"+element['password']+"</td><td>"+element['number']+"</td></tr>";
                })
                str +="</tbody></table>";
                $('body').append(str);
            },
            error:function () {
                alert("错误");
            }
        })
    }
</script>
<script type="text/javascript">
    function exportExcel(){
        $.ajax({
            url:'/action/exportExcel',
            type:'get',
            datatype:'json',
            // data:{
            //     "user":$("#user").valueOf()
            // },
            success:function(){
                alert("导出成功");
            },
            error:function () {
                alert("错误");
            }
        })
    }
</script>