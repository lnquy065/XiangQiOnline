<%-- 
    Document   : lobby
    Created on : Aug 23, 2017, 5:47:24 PM
    Author     : LN Quy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>Co Tuong Online</title>
        <link href="css/room.css" rel="stylesheet" type="text/css"/>
        <script src="js/jquery-3.2.1.min.js"></script>
    </head>
    <body>
        <script>
            var playerName = '<%=session.getAttribute("loginName")%>'.trim();
            var roomID = '<%=session.getAttribute("roomID")%>'.trim();
            var enemyName = '<%=session.getAttribute("e")%>'.trim();
            var playerSide = '<%=session.getAttribute("p")%>'.trim();
            if (playerName === 'null' || playerName === '') {
                window.location.replace('login.jsp');
            }
            if (roomID === 'null') {
                window.location.replace('lobby.jsp');
            }
        </script>

        <%@ include file="profile.jsp" %>

        <div id="PlayContent">  
            <div id="banCo" style="display: block" height="600px" width="800px">
                <img id="banCoIMG" src="images/BanCo.png" style="position: absolute; left: 0px; top: 0px;padding: 0px; margin: 0px">
            </div>
            <div id="clock">
                <b id="totalTime"></b>
                <b id="startTime" hidden="1"></b>
            </div>
            <div id="clock2">
                <b id="turnTime">03:00</b>
                <b id="startTurnTime" hidden="1"></b>
            </div>

            <div id="roomID"></div>
            <div id="notice"></div>
            <img id="avatar1" class="avatar" src="" onerror="imgError(this);">
            <img id="avatar2" class="avatar" src="" onerror="imgError(this);">

            <div id="chatBox">
                <div id="messengePool"> 
                </div>

                <div id="chatBoxSendBox">
                    <input id="chatBoxInput" size="25" maxlength="50" placeholder='Bấm "Enter" để gửi tin nhắn...'><button id="chatBoxSubmit" onclick="sendMessage()" hidden><b>&#x21AA;&nbsp;</b></button>
                    <img id="chatBoxEmoj" src="images/chatbox/emo.png" width="20px">
                </div>

                <div id="chatBoxEmojDiv" hidden >
                    <table>
                        <tr><td class="emo">&#128512;</td><td class="emo">&#128513;</td><td class="emo">&#128514;</td><td class="emo">&#128516;</td></tr>
                        <tr><td class="emo">&#128517;</td><td class="emo">&#128518;</td><td class="emo">&#128519;</td><td class="emo">&#128520;</td></tr>
                        <tr><td class="emo">&#128521;</td><td class="emo">&#128522;</td><td class="emo">&#128523;</td><td class="emo">&#128524;</td></tr>
                        <tr><td class="emo">&#128525;</td><td class="emo">&#128526;</td><td class="emo">&#128527;</td><td class="emo">&#128528;</td></tr>
                        <tr><td class="emo">&#128529;</td><td class="emo">&#128530;</td><td class="emo">&#128531;</td><td class="emo">&#128532;</td></tr>
                        <tr><td class="emo">&#128533;</td><td class="emo">&#128534;</td><td class="emo">&#128535;</td><td class="emo">&#128536;</td></tr>
                        <tr><td class="emo">&#128537;</td><td class="emo">&#128538;</td><td class="emo">&#128539;</td><td class="emo">&#128540;</td></tr>
                        <tr><td class="emo">&#128541;</td><td class="emo">&#128542;</td><td class="emo">&#128543;</td><td class="emo">&#128544;</td></tr>
                        <tr><td class="emo">&#128545;</td><td class="emo">&#128546;</td><td class="emo">&#128547;</td><td class="emo">&#128548;</td></tr>
                        <tr><td class="emo">&#128549;</td><td class="emo">&#128550;</td><td class="emo">&#128551;</td><td class="emo">&#128552;</td></tr>
                        <tr><td class="emo">&#128553;</td><td class="emo">&#128554;</td><td class="emo">&#128555;</td><td class="emo">&#128556;</td></tr>
                        <tr><td class="emo">&#128557;</td><td class="emo">&#128558;</td><td class="emo">&#128559;</td><td class="emo">&#128560;</td></tr>
                        <tr><td class="emo">&#128562;</td><td class="emo">&#128563;</td><td class="emo">&#128564;</td><td class="emo">&#128565;</td></tr>
                        <tr><td class="emo">&#128566;</td><td class="emo">&#128567;</td><td class="emo">&#128568;</td><td class="emo">&#128569;</td></tr>
                    </table>
                </div>
            </div>

            <div id="buttonList" hidden>
                <img id="btnExit" src="images/btn/exit.png" width="40px" height="40px" onclick="Exit();" onmouseover="this.src = 'images/btn/exitHover.png';" onmouseout="this.src = 'images/btn/exit.png';" title="Thoát"> 
                <!--<img id="btnSoundOn" src="images/btn/sound.png" width="40px" height="40px" onclick="SoundOn();" onmouseover="this.src = 'images/btn/soundHover.png';" onmouseout="this.src = 'images/btn/sound.png';" title="Tắt âm hiệu ứng">-->
                <!--<img id="btnSoundOff" src="images/btn/soundDis.png" width="40px" height="40px" onclick="SoundOff();" onmouseover="this.src = 'images/btn/soundDisHover.png';" onmouseout="this.src = 'images/btn/soundDis.png';" style="display: none;"  title="Bật âm hiệu ứng">-->
                <!--<img id="btnMusicOn" src="images/btn/musicEn.png" width="40px" height="40px" onclick="MusicOn();" onmouseover="this.src = 'images/btn/musicEnHover.png';" onmouseout="this.src = 'images/btn/musicEn.png';"  title="Tắt nhạc nền">--> 
                <!--<img id="btnMusicOff" src="images/btn/musicDis.png" width="40px" height="40px" onclick="MusicOff();" onmouseover="this.src = 'images/btn/musicDisHover.png';" onmouseout="this.src = 'images/btn/musicDis.png';" style="display: none;"  title="Bật nhạc nền">--> 
            </div>
        </div>


        <div id="endGame" style="width: 100%;height: 100%;display: none;">
            <div id="endGameGlassPane" style="width: 100%;height: 100%;background-color: gray;opacity: 0;position: absolute;top:0px;left:0px">  
            </div>
            <div id="endGameDialog">
                <img id="endGameIMG" src="images/victory.png" width="100%" height="100%">
                <div id="endGameAvatarPane">
                    <img class="endGameFlagW" src="images/winFlagL.png" width="40px" height="40px">
                    <img class="endGameFlagL"src="images/whiteFlagL.png" width="40px" height="40px">
                    <b id="endGameText" style="display: none;">Đối thủ đã bỏ chạy!</b>
                    <img id="endGameAvatar" style="border-radius: 10px;" src="" width="50px" height="50px">
                    <img class="endGameFlagW" src="images/winFlagR.png" width="40px" height="40px">
                    <img class="endGameFlagL" src="images/whiteFlagR.png" width="40px" height="40px">
                </div>

                <div style="position: absolute;top:150px;left:0px;width: 300px;text-align: center;"><a id="endGameOk" onclick="exitRoom()">Ok</a></div>
            </div>
        </div>

        <script src="js/graphics.js" type="text/javascript"></script>
        <script src="js/room.js" type="text/javascript"></script>
    </body>
</html>
