<%-- 
    Document   : index
    Created on : Aug 24, 2017, 2:17:49 PM
    Author     : nguye
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Co Tuong Online - Dang Nhap</title>
        <link rel="stylesheet" href="css/login.css">
        <script src="js/jquery-3.2.1.min.js" type="text/javascript"></script>
        <!--<script src="js/js.cookie.js"></script>-->

    </head>

    <audio autoplay loop>
        <source src="sounds/login.mp3" type="audio/mpeg">
    </audio>

    <body>
        <script>
            var playerName = '<%=session.getAttribute("loginName")%>'.trim();
            console.log(playerName);
            if (playerName !== 'null')
                window.location.replace('lobby.jsp');

        </script>
        <img src="images/background/login.gif">
        <img id="pikalong" src="images/background/rongpikachu.gif" style="position: fixed;left: -550px; top: 300px">

        <div id="gioiThieu" style="position: fixed; bottom: 5px; right: 5px; text-align: right"> 
            <img src="images/KQLogo.png" width="50px" style="display: block; margin: 0px auto">
            <b>Copyright © 2017 KQ Studio</b><br>
            <a href="https://fb.com/luongngocquy" style="color: black">Lương Ngọc Quý</a><br><a href="https://fb.com/khoanguyennt96" style="color: black">Nguyễn Đăng Khoa</a>

        </div>

        <div class="inner-container">
            <div class="box" id="loginBox">
               
                <form class="form-group" id="si" onsubmit="return false;" >
                    <input class="form-control" value=""  id="username" type="text" placeholder="Tài khoản" minlength = "5" maxlength="8" required autocomplete="false" autofocus="">
                    <input class="form-control" value=""  id="password" type="password" placeholder="Mật khẩu" minlength = "5" maxlength="45" required autocomplete="false">

                    <button class="form-control" id = "signinbtn" onsubmit="return false;" >Vào Game</button>

                    <button class="form-control" id="signup">Đăng Ký</button>
                </form>


                <form class="form-group" id="su" onsubmit="return false;">
                    <input class="form-control" value="" name="user" id="user" type="text" placeholder="Tên tài khoản (5 - 8 kí tự)" minlength = "5" maxlength="8" required >
                    <span id="user-result"></span>

                    <input class="form-control" value="" name="pass" id="pass" type="password" placeholder="Mật khẩu (5 - 45 kí tự)"  minlength = "5" maxlength="45" required>
                    <input class="form-control" value="" name="repass" id="repass" type="password" placeholder="Nhập lại mật khẩu"  minlength = "5" maxlength="45" required style="margin-bottom: 5px">
                    <span id = "pass-result"></span>

                    <button onsubmit="return false;" class="form-control" id="signupbtn">Xác Nhận</button>
                    <button class="form-control"  id="back">Trở Về</button>

                </form>
                <br><span id="thongbao" style="width: 100%; display: none;text-align: center;margin-top:-20px;color: red">&#9888;&nbsp;<b id="thongbaoText"></b></span>
            </div>
        </div>

        <script src="js/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script type="text/javascript">
//                                var side = "L";
//                                pika();
//                              window.setInterval("pika()", 5000);
//                                            
//                                            function pika() {
//                                                
//                                                if (side == "L") {
//                                                    $("#pikalong").css("-webkit-transform", "scaleX(-1)");
//                                                    $("#pikalong").animate({
//                                                        left: "100vw"
//                                                    }, 5000);
//                                                    side="R";
//                                                } else {
//                                                    $("#pikalong").css("-webkit-transform", "scaleX(1)");
//                                                    $("#pikalong").animate({
//                                                        left: "-550px"
//                                                    }, 5000);
//                                                    side="L";
//                                                }
//                                            }
//




                        $(document).ready(function () {
                            var x_timer;
                            $("#user").keyup(function (e) {
                                clearTimeout(x_timer);
                                var user_name = $(this).val();
                                x_timer = setTimeout(function () {
                                    check_username_ajax(user_name);
                                }, 1000);
                            });

                            function check_username_ajax(user) {
                                $("#user-result").html('<img src="images/login/ajax-loader.gif" />');
                                $.post('CheckUserServlet', {'user': user}, function (data) {
                                    $("#user-result").html(data);
                                });
                            }
                        });

                        $("#pass, #repass").on("keyup", function () {
                            if ($("#pass").val() === $("#repass").val()) {
                                if ($("#pass").val().length > 4) {
                                    $("#pass-result").html('<img src="images/login/available.png" />');
                                }
                            } else {
                                $("#pass-result").html('<img src="images/login/not-available.png" />');
                            }
                        });
                        
                        function showThongBao(text) {
                            if (text=="") {
                                $("#thongbao").hide(200);
                                return;
                            }
                            $("#thongbao").css("display", "block");
                            $("#thongbao").show(200);
                            $("#thongbaoText").text(text);
                        }


                        $('#signinbtn').click(function ()
                        {
                            showThongBao("");
                            var username = $('#username').val();
                            var password = $('#password').val();
                            if (username.length > 4 && password.length > 4) {
                                var xmlhttp = new XMLHttpRequest();
                                xmlhttp.open("POST", "CheckLoginServlet?username=" + username + " &password=" + password, false);
                                xmlhttp.send();
                                var response = xmlhttp.responseText;
                                if (response === "NONE") {
                                    showThongBao("Tài khoản không tồn tại");
                                    showThongBao();
                                    $('#username').val("");
                                    $('#password').val("");
                                } else if (response === "WRONGPASS") {
                                    showThongBao("Mật khẩu không đúng");
                                    $('#password').val("");
                                } else if (response === "ONLINE") {
                                    showThongBao("Tài khoản đang đăng nhập");
                                    $('#username').val("");
                                    $('#password').val("");
                                } else if (response === "BANNED") {
                                    showThongBao("Tài khoản đã bị khóa (3 phút)");
                                    $('#username').val("");
                                    $('#password').val("");
                                } else {
                                    $("#loginBox").fadeOut(300, function () {
                                        window.location.replace("LobbyServlet");
                                    });
                                }
                            }
                        });


                        $('#signupbtn').click(function ()
                        {
                            showThongBao("");
                            var user = $('#user').val();
                            var pass = $('#pass').val();
                            var repass = $('#repass').val();
                            if (user.length > 5 && pass.length > 4 && repass.length > 4) {
                                var xmlhttp = new XMLHttpRequest();
                                xmlhttp.open("POST", "CheckSignupServlet?user=" + user + "&pass=" + pass + "&repass=" + repass, false);
                                xmlhttp.send();

                                if (xmlhttp.responseText === "USER") {
                                    showThongBao("Tên tài khoản đã tồn tại");
                                    $('#user').val("");
                                    $('#pass').val("");
                                    $('#repass').val("");
                                } else if (xmlhttp.responseText === "REPASS") {
                                    showThongBao("Mật khẩu lặp lại không trùng");
                                    $('#pass').val("");
                                    $('#repass').val("");
                                } else {
                                    if (xmlhttp.responseText === "OK") {
                                        showThongBao("Chúc mừng bạn đã đăng kí thành công");
                                        $('#user').val("");
                                        $('#pass').val("");
                                        $('#repass').val("");
                                        Back();
                                        $('#username').val(user);
                                        $('#password').focus();
                                    } else {
                                        showThongBao("Đăng kí thất bại! Lỗi kết nối");
                                    }
                                }
                            }
                        });

                        function Back() {
                            $('#user').removeAttr('required');
                            $('#pass').removeAttr('required');
                            $('#repass').removeAttr('required');

                            $("#si").show(500);
                            $("#su").hide(500);
                            $('#username').attr('required', '');
                            $('#password').attr('required', '');
                             showThongBao("");
                        }

                        $(document).ready(function () {

                            $("#signup").click(function () {
                                showThongBao("");
                                $('#username').removeAttr('required');
                                $('#password').removeAttr('required');

                                $("#su").show(500);
                                $("#si").hide(500);

                                $('#user').attr('required', '');
                                $('#pass').attr('required', '');
                                $('#repass').attr('required', '');
                            });

                            $("#back").click(function () {
                                Back();
                            });
                        });

        </script>
    </body>

</html>
