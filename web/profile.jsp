<%-- 
    Document   : profile
    Created on : Sep 18, 2017, 11:54:44 PM
    Author     : LN Quy
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="jdbc.MySQLConnUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    MySQLConnUtils conn = new MySQLConnUtils();
    conn.openConnection();
    String username = (String) session.getAttribute("loginName");
    if (username == "" || username == null) {
        response.sendRedirect("login.jsp");
    } else {

        String sql = "SELECT * FROM account WHERE username='" + username + "'";
        ResultSet rs = conn.excuteQuery(sql);
        rs.next();

%>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/profile.css" rel="stylesheet" type="text/css"/>
        <script src="js/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="js/QUI.js" type="text/javascript"></script>
        <style>
            #btnLogout {
                position: fixed;
                left: 200px;
                top: 1px;
            }
             #btnManager {
                position: fixed;
                left: 230px;
                top: 1px;
            }
        </style>
    </head>
    <body>
        <img id="btnLogout" src="images/btn/btnLogout.png" width="30px" height="30px" onclick="window.location = 'LogoutServlet';" onmouseover="this.src = 'images/btn/btnLogoutHover.png';" onmouseout="this.src = 'images/btn/btnLogout.png';" title="Đăng xuất!"> 
               <%
                   if (session.getAttribute("role").toString().equals("1")) {
                       out.print("<img id='btnManager' src='images/btn/manager.png' width='30px' height='30px' onclick=\"window.location = 'manager.jsp';\" onmouseover=\"this.src = 'images/btn/managerHover.png';\" onmouseout=\"this.src = 'images/btn/manager.png';\" title='Quản Lý!'>");
                   }
                   %>
        <div id="profile_extend">
          


            <div style=" background-image: url('images/profile/profile_field.png');display: block;"><b class="profile_fText" style='padding-left:10px;'>Tỉ lệ thắng&nbsp;</b><b class="profile_par" id="tlt"></b></div>
            <div style=" background-image: url('images/profile/profile_field.png');display: block;"><img src="images/profile/win.png" width="20px" height="20px"><b class="profile_fText">Số Trận Thắng:&nbsp;</b><b class="profile_par" id="win"><%=rs.getInt("countwin")%></b></div>
            <!--<div style=" background-image: url('images/profile/profile_field.png');display: block;"><img src="images/profile/hoa.png" width="20px" height="20px"><b class="profile_fText">Số Trận Hòa:&nbsp;</b><b class="profile_par" id="tie"><%=rs.getInt("counttie")%></b></div>-->
            <div style=" background-image: url('images/profile/profile_field.png');display: block;"><img src="images/profile/lose.png" width="20px" height="20px"><b class="profile_fText">Số Trận Thua:&nbsp;</b><b class="profile_par" id="lose"><%=rs.getInt("countlose")%></b></div>
        </div>
        <div id="profile">
            <img id="profile_ava" src="" width="40px" height="40px" title="Đổi ảnh đại diện" onerror="imgError(this);" onclick="QUI_MsgInfoForm()">

            <b id="profile_name"><%=username%></b>
            <div id="profile_level"><img src="images/levelL.png" width="10px" height="20px"><b id="level"></b><img src="images/levelR.png" width="10px" height="20px"></div>
            <div id="profile_levelBar"></div>
            <img id="profile_btnDown" onclick="profile_down()" src="images/btn/btnDown.png" width="20px" height="20px" onmouseover="this.src = 'images/btn/btnDown.png';" onmouseout="this.src = 'images/btn/btnDownHover.png';">
            <img id="profile_btnUp" onclick="profile_up()" src="images/btn/btnUp.png" width="20px" height="20px" onmouseover="this.src = 'images/btn/btnUp.png';" onmouseout="this.src = 'images/btn/btnUpHover.png';" style="display: none;">

        </div>

        <div>
            <img id="btnStyle1" class="NavButton" src="images/btn/btnStyle1.png" onclick="changeSkin(1)" width="40px">
            <img id="btnStyle2" class="NavButton" src="images/btn/btnStyle2.png" onclick="changeSkin(2)" width="40px">
            <img id="btnStyle3" class="NavButton" src="images/btn/btnStyle3.png" onclick="changeSkin(3)" width="40px">
            <img id="btnStyle4" class="NavButton" src="images/btn/btnStyle4.png" onclick="changeSkin(4)" width="40px">

            <img id="btnThemeOn" class="NavButton" src="images/btn/btnTheme.png" width="40px" height="40px" onclick="ThemeOn(this.id);" onmouseover="this.src = 'images/btn/btnThemeHover.png';" onmouseout="this.src = 'images/btn/btnTheme.png';" title="Đổi giao diện"> 
            <img id="btnThemeOff" class="NavButton" style="display: none;" src="images/btn/optionOn.png" width="40px" height="40px" onclick="ThemeOff(this.id);" onmouseover="this.src = 'images/btn/btnThemeDisHover.png';" onmouseout="this.src = 'images/btn/btnThemeDis.png';" title="Đổi giao diện"> 
            <img id="btnChangeColor" class="NavButton" src="images/btn/changeColor.png" width="40px" height="40px" onclick="ChangeSide();" onmouseover="this.src = 'images/btn/changeColorHover.png';" onmouseout="this.src = 'images/btn/changeColor.png';" title="Đổi phe"> 


            <img id="btnExit" class="NavButton" src="images/btn/exit.png" width="40px" height="40px" onclick="Exit();" onmouseover="this.src = 'images/btn/exitHover.png';" onmouseout="this.src = 'images/btn/exit.png';" title="Thoát"> 
            <img id="btnSoundOn" class="NavButton" src="images/btn/sound.png" width="40px" height="40px" onclick="SoundOn();" onmouseover="this.src = 'images/btn/soundHover.png';" onmouseout="this.src = 'images/btn/sound.png';" title="Tắt âm hiệu ứng">
            <img id="btnSoundOff" class="NavButton" src="images/btn/soundDis.png" width="40px" height="40px" onclick="SoundOff();" onmouseover="this.src = 'images/btn/soundDisHover.png';" onmouseout="this.src = 'images/btn/soundDis.png';" style="display: none;"  title="Bật âm hiệu ứng">
            <img id="btnMusicOn" class="NavButton" src="images/btn/musicEn.png" width="40px" height="40px" onclick="MusicOn();" onmouseover="this.src = 'images/btn/musicEnHover.png';" onmouseout="this.src = 'images/btn/musicEn.png';"  title="Tắt nhạc nền"> 
            <img id="btnMusicOff" class="NavButton" src="images/btn/musicDis.png" width="40px" height="40px" onclick="MusicOff();" onmouseover="this.src = 'images/btn/musicDisHover.png';" onmouseout="this.src = 'images/btn/musicDis.png';" style="display: none;"  title="Bật nhạc nền"> 
            <img id="btnNavOption" class="NavButton" src="images/btn/option.png" width="40px" height="40px" onclick="NavOption();" onmouseover="this.src = 'images/btn/optionHover.png';" onmouseout="this.src = 'images/btn/option.png';" title="Mở cài đặt"> 
            <img id="btnNavOptionOff" class="NavButton" style="display: none;" src="images/btn/optionOn.png" width="40px" height="40px" onclick="NavOptionOff();" onmouseover="this.src = 'images/btn/optionOnHover.png';" onmouseout="this.src = 'images/btn/optionOn.png';" title="Đóng cài đặt"> 



        </div>

        <script src="js/profile.js" type="text/javascript"></script>
        <script>
                var link = window.location;
                if (link.toString().indexOf("lobby") !== -1 || link.toString().indexOf("LobbyServlet") !== -1 || link.toString().indexOf("UploadServlet") !== -1 ) {
                    $("#btnExit").remove();
                    $("#btnThemeOff").remove();
                    $("#btnThemeOn").remove();
                    $("#btnChangeColor").remove();
                    $("#btnStyle1").remove();
                    $("#btnStyle2").remove();
                    $("#btnStyle3").remove();
                    $("#btnStyle4").remove();
                }

                var win = parseInt($('#win').text());
                var lose = parseInt($('#lose').text());
                var tie = parseInt($('#tie').text());
                var sum = parseInt(win + lose);

                if (lose == 0 && win != 0) {
                    $('#tlt').text("100%");
                } else {
                    var tlt;
                    if (sum != 0) {
                        tlt = parseInt(win / sum * 100);
                    } else {
                        tlt = 0;
                    }
                    $('#tlt').text(tlt + "%");
                }
                var exp = win * 3;
                console.log(exp);
                var level = parseInt(exp / 10) + 1;
                $('#level').text(level);
                $("#profile_levelBar").css("width", (exp % 10 * 11) + "px");

        </script>
    </body>
</html>

<% conn.closeConnection();
    }%>