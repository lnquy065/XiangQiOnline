<%-- 
    Document   : manager
    Created on : Oct 25, 2017, 7:48:50 PM
    Author     : LN Quy
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="jdbc.MySQLConnUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Co Tuong - Quan Ly</title>
        <script src="js/jquery-3.2.1.min.js" type="text/javascript"></script>
        <style>
            .offline {
                color: red;
            }

            .online {
                color: green;
            }

            body {
                padding:0px;
                margin:0px;

                width: 100vw;
                height: 100vh;

                background-image: url("images/background/quanly.jpeg");
                background-size: 100% 100%;
                background-repeat: no-repeat;
            }
        </style>
    </head>
    <body>
        <div id="info" style="display: block; margin: 80px auto; width: 500px; height: 600px; border: 1px solid black;    overflow: scroll;
             overflow-x: hidden;text-align: center; border-radius: 10px; background: white">
            <h2>Quản Lý Tài Khoản</h2>
            <table border="thin" style="border: 2px solid black; width: 480px">

                <tr><th>Tên tài khoản</th><th>Trạng thái</th><th>Phòng chơi</th><th>Trận Thắng</th><th>Trận Thua</th></tr>
                        <%
                            MySQLConnUtils conn = new MySQLConnUtils();
                            conn.openConnection();
                            ResultSet rS = conn.excuteQuery("SELECT * FROM account WHERE username='" + session.getAttribute("loginName") + "'");
                            rS.next();
                            if (rS.getInt("role") == 0) {
                                RequestDispatcher rd = getServletContext().getRequestDispatcher("/lobby.jsp");
                                rd.forward(request, response);
                            }
                            rS.close();
                            String user = session.getAttribute("loginName").toString().trim();
                            ResultSet rS2 = conn.excuteQuery("SELECT * FROM account");
                            while (rS2.next()) {
                                if (!user.equals(rS2.getString("username").trim()))
                                out.println("<tr><td>" + rS2.getString("username") + "</td><td " + (rS2.getInt("online") == 0 ? "class='offline'" : "class='online'") + ">" + "●</td><td>" + rS2.getInt("inroom") + "</td><td>" + rS2.getInt("countwin") + "</td><td>" + rS2.getInt("countlose") + "</td><td><button onclick='del(\"" + rS2.getString("username") + "\");'>Xóa</button></td></tr>");
                            }
                            conn.closeConnection();
                        %>
            </table>
        </div>
        <script>
            function del(username) {
                $.ajax({
                    method: "POST",
                    url: "Manager",
                    data: {username: username},
                    success: function () {
                        window.location.reload();
                    }
                });
            }
        </script>
    </body>
</html>
