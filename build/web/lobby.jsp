<%-- 
    Document   : roomList
    Created on : Sep 4, 2017, 1:27:02 PM
    Author     : LN Quy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Co Tuong Online - Sanh</title>
        <script src="js/jquery-3.2.1.min.js"></script>
        <link href="css/lobby.css" rel="stylesheet" type="text/css"/>
    </head>
    <link href="css/QUI.css" rel="stylesheet" type="text/css"/>
    <body>
        <script>
            var playerName = '<%=session.getAttribute("loginName")%>'.trim();
            console.log(playerName);
            if (playerName === 'null' || playerName === '')
                window.location.replace('login.jsp');

        </script>
        <%@ include file="profile.jsp" %>
        <!--<img src="images/background/lobby.gif" width="100%" height="100%">-->

        <div id="roomList">
            <img src="images/roomList/roomList.jpg" width="800px" height="600px">
            <b id="roomID">null</b>
            <b id="pHost"></b>
            <b id="pClient"></b>
            <img id="btnExitRoom" src="images/btn/exit.png" width="40px" height="40px" onclick="ExitRoom();" onmouseover="this.src = 'images/btn/exitHover.png';" onmouseout="this.src = 'images/btn/exit.png';" title="Thoát"> 
            <img id="btnKick" src="images/btn/kick.png" width="20px" height="20px" onclick="KickClient();"> 


            <img id="avatar1" class="avatar" src="avatars/null.jpg" onerror="imgError(this);">
            <img id="avatar2" class="avatar" src="avatars/null.jpg" onerror="imgError(this);">

            <img id="emptyRoom" src="images/roomList/empty.jpg">
            <input type="button" value="Bắt đầu!" id="btnBatDau" onclick="playGame('host')" style="display: none" class="btn">
            <b id="notice"></b>
            <b id="selectedChair" hidden="1"></b>

            <div id="dsOnline">

            </div>
        </div>





        <script src="js/QUI.js" type="text/javascript"></script>
        <script src="js/lobby.js" type="text/javascript"></script>
    </body>
</html>
