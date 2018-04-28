function QUI_MsgInfoForm() {
 var link = window.location;
 
if (link.toString().indexOf("room")!==-1) {
                    return;
}
var html = '<div id="QUI_Msgbox" style="width: 100%;height: 100%; display: none">\
            <div id="QUI_GlassPane" style="width: 100%;height: 100%;background-color: gray;opacity: 0.5;position: absolute;top:0px;left:0px">\
            </div>\
            <div id="QUI_Msgbox_Form" >  \
                <b id="QUI_Msgbox_Form_Caption">Thông Tin Tài Khoản</b>\
                <img id="info_avatar" src="" width="100px" height="100px" onerror="imgError(this)">\
                <form id="upload-form" onsubmit="return false;"  method="post" action="UploadServlet" enctype="multipart/form-data">\
                    <label id="myAvatarLB" for="myAvatar" >\
                        <b > &#8679;</b>\
                    </label>\
                    <input id="myAvatar" type="file" name="file" size="1024"  multiple accept="image/jpeg" style="display: none"/>\
                    <input class="avatarBtn" id="btnUpload" type="submit" value="Thay đổi" onclick="Change();" hidden/>\
                    <input class="avatarBtn" id="btnCancel" type="submit" value="Hủy bỏ" onclick="Hide()" hidden/>\
                </form>\
                <b id="info_playerName" style="font-family: cursive;font-size: 20px;margin-top: 15px;color:green"></b><br>\
                <div style="text-align: left; font-family: cursive;font-size: 15px;margin: 20px auto">\
                    <table style="margin: 0px auto">\
                        <tr><td  style="width: 150px">Điểm kinh nghiệm:</td><td style="text-align: right" id="info_exp"></td></tr>\
                        <tr><td>Thắng/Thua: </td><td style="text-align: right" id="info_winlose"></td></tr>\
                    </table>\
                </div>\
                <div>\
                    <a class="QUI_btn" id="QUI_Msgbox_Form_Ok" onclick="QUI_Form_close();">Đóng</a>\
                </div>\
            </div>\
        </div>';
        document.body.innerHTML = document.body.innerHTML + html;
        refeshEvent();
        $("#QUI_Msgbox").fadeIn(300);
        $("#info_avatar").attr("src", $("#profile_ava").attr("src"));
        $("#info_exp").text(parseInt($("#win").text()) * 3);
        $("#info_winlose").text($("#win").text() + "/" + $("#lose").text())
        $("#info_playerName").text(playerName);

        $('#myAvatar').bind('change', function () {
var sizefile = parseInt(this.files[0].size);
        var maxsize = 1024 * 1024;
        if (sizefile > maxsize) {
alert('Dung lượng ảnh không vượt quá 1MB!');
        $('#myAvatar').val('');
} else {
//document.getElementById('btnCancel').style.display = "inline-block";
//        document.getElementById('btnUpload').style.display = "inline-block";
        $('#upload-form').attr("onsubmit", "return true;");
        $('#upload-form').submit();
}
});
        }


function QUI_MsgShow(caption, content, btnMode, okText, callBackOk, cancelText, callBackCancel) {
var mode = "";
        if (btnMode === 1)
        mode = 'style="display: none;"';
        var html = '<div id="QUI_Msgbox" style="width: 100%;height: 100%;display: none">\
                <div id="QUI_GlassPane" style="width: 100%;height: 100%;background-color: gray;opacity: 0.5;position: absolute;top:0px;left:0px">  \
            </div>\
            <div id="QUI_Msgbox_Dialog">  \
                    <img src="images/QUI/msg.png" style="position: absolute; left: 0px; top: 0px">\
                    <div style="position: absolute; top: 20px; left: 0px; width: 300px" >\
                    <b id="QUI_Msgbox_Dialog_Caption" style="font-family: cursive; font-size: 25px">' + caption + '</b>\
                    </div>\
                    <div style="position: absolute; top: 55px; left: 0px; width: 300px" >\
                    <p id="QUI_Msgbox_Dialog_Content" style="font-family: cursive; font-size: 20px">' + content + '</p>\
                    </div>\
                     <div style="position: absolute; top: 150px; left: 0px; width: 300px" >\
                         <a id="QUI_Msgbox_Dialog_Ok"">' + okText + '</a>\
                    <a id="QUI_Msgbox_Dialog_Canel"" ' + mode + '>' + cancelText + '</a>\
                    </div>\
            </div>\
        </div>';
        document.body.innerHTML = document.body.innerHTML + html;
        $("#QUI_Msgbox").fadeIn(300);
        $("#QUI_Msgbox_Dialog_Ok").click(function () {
if (callBackOk != null) callBackOk();
        $("#QUI_Msgbox").fadeOut(200, function() {
$("#QUI_Msgbox").remove();
});
});
        $("#QUI_Msgbox_Dialog_Canel").click(function () {
if (callBackCancel != null) callBackCancel();
        $("#QUI_Msgbox").fadeOut(200, function() {
$("#QUI_Msgbox").remove();
});
});
        }


function imgError(img) {
img.src = "avatars/default.jpg";
        }

function Hide() {
document.getElementById('btnCancel').style.display = "none";
        document.getElementById('btnUpload').style.display = "none";
}


function Change() {
if (document.getElementById("myAvatar").files.length === 0) {
alert("Bạn chưa chọn ảnh");
} else {
$('#upload-form').attr("onsubmit", "return true;");
        $('#upload-form').submit();
}
}

function QUI_Form_close() {
$("#QUI_Msgbox").fadeOut(200, function() {
$("#QUI_Msgbox").remove();
});
}
