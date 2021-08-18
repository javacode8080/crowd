<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><!--使用c:foreach必须引入的库-->
<!--自动台转到指定页面-->
<jsp:forward page="WEB-INF/admin-login.jsp"></jsp:forward>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"> <!--/*一些先开始，不以斜线结束*/-->
    <title>员工列表</title>


    <!--1.将绝对路径描述为APP_PATH，简便
        2.利用base标签引入绝对路径
        <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/"/>
        注：①：contextpath前面不饿能够加/
           ②：contextPath后面必须加/
           ③：页面上所有参照base标签的标签都必须啊放在base 标签的后面
           ④：页面上所有参照base 标签的标签都不能以/开头
    -->
    <%
        pageContext.setAttribute("APP_PATH",request.getContextPath());
    %>

    <!--web路径
        不以/开始的相对路径找资源，以当前资源的路径为基准，经常出现问题
        以/开始的相对路径，以服务器的路径为标准（http://localhost:3306）：需要加上项目名
        http://localhost:3306/crud
    -->
    <!--引入JQuery:必须先引用-->
    <script type="text/javascript" src="${APP_PATH}/static/js/jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="${APP_PATH}/static/layer/layer.js"></script>
    <script type="text/javascript">

        $(function(){
            $("#btn5").click(function () {
                /*alert("aaa");//浏览器自带弹框*/
                layer.msg("layer的弹框测试");

            });

            $("#btn4").click(function(){

                // 准备要发送的数据
                var student = {
                    "stuId": 5,
                    "stuName":"tom",
                    "address": {
                        "province": "广东",
                        "city": "深圳",
                        "street":"后瑞"
                    },
                    "subjectList": [
                        {
                            "subjectName": "JavaSE",
                            "subjectScore": 100
                        },{
                            "subjectName": "SSM",
                            "subjectScore": 99
                        }
                    ],
                    "map": {
                        "k1":"v1",
                        "k2":"v2"
                    }
                };

                // 将JSON对象转换为JSON字符串
                var requestBody = JSON.stringify(student);

                // 发送Ajax请求
                $.ajax({
                    "url":"${APP_PATH}/send/compose/object.json",
                    "type":"post",
                    "data":requestBody,
                    "contentType":"application/json;charset=UTF-8",
                    "dataType":"json",
                    "success":function(response){
                        console.log(response);
                    },
                    "error":function(response){
                        console.log(response);
                    }
                });
  /*              $.ajax({
                    "url":"${APP_PATH}/send/compose/object.html",
                    "type":"post",
                    "data":requestBody,
                    "contentType":"application/json;charset=UTF-8",
                    "dataType":"text",
                    "success":function(response){
                        console.log(response);
                    },
                    "error":function(response){
                        console.log(response);
                    }
                });*/
            });

            $("#btn3").click(function(){

                // 准备好要发送到服务器端的数组
                var array = [5, 8, 12];
                console.log(array.length);

                // 将JSON数组转换为JSON字符串
                var requestBody = JSON.stringify(array);/*这才是真正的发送ajax请求，数据封装为ajax发送给服务器端*/
                // "['5','8','12']"
                console.log(requestBody.length);

                $.ajax({
                    "url": "${APP_PATH}/send/array/three.html",			// 请求目标资源的地址
                    "type": "post",						// 请求方式
                    "data": requestBody,				// 请求体
                    "contentType": "application/json;charset=UTF-8",	// 设置请求体的内容类型，告诉服务器端本次请求的请求体是JSON数据
                    "dataType": "text",					// 如何对待服务器端返回的数据
                    "success": function(response) {		// 服务器端成功处理请求后调用的回调函数，response是响应体数据
                        alert(response);
                    },
                    "error":function(response) {		// 服务器端处理请求失败后调用的回调函数，response是响应体数据
                        alert(response);
                    }
                });

            });

            $("#btn2").click(function(){

                $.ajax({
                    "url": "${APP_PATH}/send/array/two.html",			// 请求目标资源的地址
                    "type": "post",						// 请求方式
                    "data": {							// 要发送的请求参数
                        "array[0]": 5,
                        "array[1]": 8,
                        "array[2]": 12
                    },
                    "dataType": "text",					// 如何对待服务器端返回的数据
                    "success": function(response) {		// 服务器端成功处理请求后调用的回调函数，response是响应体数据
                        alert(response);
                    },
                    "error":function(response) {		// 服务器端处理请求失败后调用的回调函数，response是响应体数据
                        alert(response);
                    }
                });

            });

            $("#btn1").click(function(){

                $.ajax({
                    "url": "${APP_PATH}/send/array/one.html",			// 请求目标资源的地址
                    "type": "post",						// 请求方式
                    "data": {							// 要发送的请求参数
                        "array": [5,8,12]
                    },
                    "dataType": "text",					// 如何对待服务器端返回的数据
                    "success": function(response) {		// 服务器端成功处理请求后调用的回调函数，response是响应体数据
                        alert(response);
                    },
                    "error":function(response) {		// 服务器端处理请求失败后调用的回调函数，response是响应体数据
                        alert(response);
                    }
                });

            });
        });

    </script>
</head>
<body>
            <h1>我是一个示例页面</h1>
            <br/>
            <a href="${APP_PATH}/test/ssm.html">测试SSM整合环境</a>
            <br/>
            <br/>

            <button id="btn1">Send [5,8,12] One</button>

            <br/>
            <br/>

            <button id="btn2">Send [5,8,12] Two</button>

            <br/>
            <br/>

            <button id="btn3">Send [5,8,12] Three</button>

            <br/>
            <br/>

            <button id="btn4">Send Compose Object</button>
            <br/>
            <br/>

            <button id="btn5">测试layer弹框</button>

</body>
</html>
