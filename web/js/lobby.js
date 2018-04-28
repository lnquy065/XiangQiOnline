//Block Ban Choi:
//W: 180, H: 70
var first = true;
var roomIDGlobal = 0;
var selectedChair;
var xOffset = 50;
var yOffset = 220;
var xSpace = 60;
var ySpace = 40;
for (var i = 0; i < 3; i++) {
    drawTable(xOffset, yOffset + (i * (70 + ySpace)), i * 2 + 1);
    drawTable(xOffset + xSpace + 180, yOffset + (i * (70 + ySpace)), i * 2 + 2);
}

var audio_click = new Audio('sounds/click.mp3');
var audio_hover = new Audio('sounds/hover.mp3');
var audio_mess = new Audio('sounds/mess.mp3');
var audio_bg = new Audio('sounds/lobby.mp3');
audio_bg.loop = true;
audio_bg.play();

requestPlayerList();
refeshEvent();
//$('#roomList').fadeIn(500);
window.setInterval("roomRefesh()", 1000);
window.setInterval("roomListRefesh()", 1000);
window.setInterval("requestPlayerList()", 1000);
window.setInterval("getInvite()", 1000);

closeRoom();
roomListRefesh();
var $j_object = $(".chairs");
$j_object.each(function (i) {
    //console.log($j_object[i].name );
    if ($j_object[i].name === playerName) {
        exitRoomSafe(playerName, $j_object[i].id.charAt(0));
    }
});




function refeshEvent() {
    $('.table_div').on('click', function (e) {
        audio_click.play();
        var chairID = $(this).attr("id");
        // console.log(chairID.charAt(0));
        joinRoom(playerName, chairID.charAt(6));
    });

    $(".table_div").hover(function () {
        playSfxSound(audio_hover);
    });
}

function playSfxSound(audio) {
    if (audioState === true) {
        audio.play();
    }
}


function invite(item) {
    if (roomIDGlobal === 0) {
        QUI_MsgShow("Thông báo!", "Bạn chưa vào phòng!", 1,
                "Ok", null, "Từ Chối", null);
        refeshEvent();
        return;
    }
    $.ajax({
        method: "POST",
        url: "InviteListServlet",
        data: {inviteName: item.id, roomID: roomIDGlobal, playerName: playerName, cmd: "invite"}
    }).done(function (msg) {
        $(item).hide();
    });
}

function getInvite() {
    $.ajax({
        method: "POST",
        url: "InviteListServlet",
        async: false,
        data: {playerName: playerName, cmd: "getInvite"}
    }).done(function (msg) {
        var msgL = $.trim(msg).split('-');
        var playerInvite = msgL[0];
        var roomInvite = msgL[1];
        if (playerInvite === 'null' || playerInvite === '')
            return;


        QUI_MsgShow("Lời mời!", playerInvite + " muốn mời bạn vào phòng " + roomInvite + "!", 0,
                "Vào", function () {
                    joinRoom(playerName, roomInvite);
                }, "Hủy", null);
        refeshEvent();

        $.ajax({
            method: "POST",
            url: "InviteListServlet",
            async: false,
            data: {playerName: playerName, cmd: "resetInvite"}
        });
    });
}

function joinRoom(playerName, roomID) {
    $.ajax({
        method: "POST",
        url: "RoomServlet",
        async: false,
        data: {cmd: "join", playerName: playerName, roomID: roomID}
    })
            .done(function (msg) {
                msg = $.trim(msg);
              //  console.log(msg);

                if (msg !== "full") {
                    if (msg !== "trungten") {
                        if (roomIDGlobal !== 0) {
                            exitRoom(playerName, roomIDGlobal);
                        }
                        roomIDGlobal = roomID;

                        $("#roomID").text(roomID);
                        if (msg === 'host') { //=> Player la HOST
                            $("#pHost").text(playerName);
                            $("#pClient").text("");
                            $("#avatar1").attr("src", "avatars/" + playerName + ".jpg");
                            $("#pHost").css('display', 'block');
                            $("#notice").text("Đang chờ người chơi khác!");
                            $("#" + roomID + "_1").attr("src", getAvatar(playerName));
                            $("#tabP" + roomID + "_1").text(playerName);
                            $("#selectedChair").text("#" + roomID + "_1");
                        } else {    //=> Player la CLIENT
                            var tmp = msg.split("|");
                            $("#pHost").text(tmp[1]);
                            $("#pClient").text(playerName);
                            $("#avatar1").attr("src", getAvatar(tmp[1]));
                            $("#avatar2").attr("src", getAvatar(playerName));
                            $("#pHost").css('display', 'block');
                            $("#pClient").css('display', 'block');
                            $("#notice").text("Đang chờ " + tmp[1] + " bắt đầu!");
                            $("#" + roomID + "_2").attr("src", getAvatar(playerName));
                            $("#tabP" + roomID + "_2").text(playerName);
                            $("#selectedChair").text("#" + roomID + "_2");
                        }
                        //  console.log(123);
                        openRoom();
                    }
//                    } else {
//
//                        QUI_MsgShow("Thông báo!", "Bạn đang trong phòng này!", 1,
//                                "Ok", null, "Hủy", null);
//                        refeshEvent();
//                    }
                } else {
                    QUI_MsgShow("Thông báo!", "Phòng đã đầy!", 1,
                            "Ok", null, "Hủy", null);
                    refeshEvent();
                    closeRoom();
                }
            });
}

function KickClient() {
    exitRoom($("#pClient").text().trim(), $("#roomID").text().trim());
    $("#btnKick").fadeOut(500);
}

function ExitRoom() {
    exitRoom(playerName, roomIDGlobal);
}


function exitRoom(player, roomID) {
    if (player===playerName) {
    $($("#selectedChair").text()).attr("src", "images/roomList/chairs.png");
    if ($("#tabP" + roomID + "_1").text()===player) $("#tabP" + roomID + "_1").text("");
    else $("#tabP" + roomID + "_2").text("");
    closeRoom();
    roomIDGlobal=0;
    }
    

    $.ajax({
        method: "POST",
        url: "RoomServlet",
        async: false,
        data: {cmd: "exit", playerName: player, roomID: roomID}
    })
            .done(function (msg) {

            });
}

function exitRoomSafe(player, roomID) {
   // console.log("exit");
    if (player===playerName) {
    $($("#selectedChair").text()).attr("src", "images/roomList/chairs.png");
    if ($("#tabP" + roomID + "_1").text()===player) $("#tabP" + roomID + "_1").text("");
    else $("#tabP" + roomID + "_2").text("");
    closeRoom();
    roomIDGlobal=0;
    }
    

    $.ajax({
        method: "POST",
        url: "RoomServlet",
        data: {cmd: "exitSafe", playerName: player, roomID: roomID}
    })
            .done(function (msg) {
                msg = $.trim(msg);
                if (msg!=="done") {
                    QUI_MsgShow("Thông báo!", "Bạn có muốn vào lại phòng "+msg+"!", 0,
                "Ok", function() {
                        document.location = "room.jsp";
                }, "Không", function() {
                    exitRoom(playerName, msg);
                    
                });
                refeshEvent();
                }
            });
}

function playGame(side) {
    var roomID = $("#roomID").text();
    if (side === 'host') {
        requestStartToServer(roomID, playerName);
        requestCreateSession(roomID, playerName, $("#pClient").text(), 'h');

    } else {
        requestCreateSession(roomID, playerName, $("#pHost").text(), 'c');

    }
    document.location = "room.jsp";
//                   document.location = "room.jsp?roomID=" + roomID + "&playerName=" + playerName + "&e=" + $("#pClient").text() + "&p=h";
}






function requestCreateSession(roomID, playerName, e, p) {
    $.ajax({
        method: "POST",
        url: "RoomServlet",
        data: {cmd: "createSession", roomID: roomID, playerName: playerName, e: e, p: p}
    });
}


function requestPlayerList() {
    $.ajax({
        method: "POST",
        url: "PlayerListServlet",
        data: {playerName: playerName, cmd: "playerList"}
    }).done(function (msg) {
        var playerList = $.trim(msg).split('|');
        var html = "";
        for (i = 0; i < playerList.length; i++) {
            if (playerList[i] === playerName || playerList[i]==="")
                continue;
            if (playerList[i].toString().charAt(0)!=="?") {
               
                            html += "<div class='playerOnline_item'><span style='color: green;font-size: 20px'>●</span>&nbsp;<b>" + playerList[i] + "</b><button class='playerOnline_btn' onclick='invite(this)' id='" + playerList[i] + "'>Mời</button></div>";
            } else {
                 playerList[i] = playerList[i].toString().substring(1);
                html += "<div class='playerOnline_item'><span style='color: red;font-size: 20px'>●</span>&nbsp;<b>" + playerList[i] + "</b></div>";
            }
        }
        $("#dsOnline").html(html);
    });
}


function requestStartToServer(roomID, playerName) {
    $.ajax({
        method: "POST",
        url: "RoomServlet",
        data: {cmd: "start", roomID: roomID, startTotalTime: new Date().getTime() / 1000, playerName: playerName}
    })
            .done(function (msg) {
                requestWaitToServer(roomID, playerName);
            });
}



function requestWaitToServer(roomID, playerName) {
    $.ajax({
        method: "POST",
        url: "RoomServlet",
        data: {cmd: "wait", playerName: playerName, roomID: roomID}
    }).done(function (msg) {
        $("#startTurnTime").text(msg);
    });
}

function closeRoom() {
    $(".avatar").css('display', 'none');
    $(".avatar").attr('src', 'avatars/null.jpg');
    $("#roomID").css('display', 'none');
    $("#pHost").css('display', 'none');
    $("#pClient").css('display', 'none');
    $("#btnBatDau").css('display', 'none');
    $("#notice").css('display', 'none');
    $("#emptyRoom").css('display', 'block');
}

function openRoom() {
    $(".avatar").css('display', 'block');
    $("#roomID").css('display', 'block');
    $("#pHost").css('display', 'block');
    $("#pClient").css('display', 'block');
    $("#notice").css('display', 'block');
    $("#emptyRoom").css('display', 'none');
    $("#btnKick").css('display', 'none');
}

function roomListRefesh() {
    $.ajax({
        method: "POST",
        url: "RoomsManager",
        async: false,
        data: {cmd: "getRoomList", playerName: playerName, timeRequest: parseInt(new Date().getTime() / 1000)}
    })
            .done(function (msg) {
                if (roomIDGlobal===0) closeRoom();
                // console.log(msg);
                var roomList = msg.split("|");
                for (var i = 0; i < 6; i++) {
                    var players = roomList[i].split(":");
                 //   console.log(roomList[i]);
                    if (first && (players[0] == playerName || players[1] == playerName) && players[2]=='false') {
                        //console.log("thoat"+playerName);
                        exitRoom(playerName, i + 1);
                        return;
                    }
                    first = false;
                    var chair1 = $("#" + (i + 1) + "_1"), chair2 = $("#" + (i + 1) + "_2");
                    chair1.attr("name", players[0]);
                    chair2.attr("name", players[1]);
                    if (players[0] !== "null") {
                        chair1.attr("src", getAvatar(players[0]));
                        $("#tabP" + (i + 1) + "_1").text(players[0]);
                    } else {
                        chair1.attr("src", "images/roomList/chairs.png");
                         $("#tabP" + (i + 1) + "_1").text("");
                    }
                    if (players[1] !== "null") {
                        chair2.attr("src", getAvatar(players[1]));
                         $("#tabP" + (i + 1) + "_2").text(players[1]);
                    } else {
                        chair2.attr("src", "images/roomList/chairs.png");
                         $("#tabP" + (i + 1) + "_2").text("");
                    }


                }
            });
}

function roomRefesh() {
    var roomID = roomIDGlobal;
    if (roomID === 0)
        return;
    $.ajax({
        method: "POST",
        url: "RoomServlet",
        data: {cmd: "getDB", roomID: roomID},
        dataType: 'json'
    })
            .done(function (msg) {
                //console.log(msg);
                                if (msg.player1 === "null") {
                                    exitRoom(playerName, roomID);
                                }
                if (msg.player1 === playerName) {
                    if (msg.player2 !== "null") { //Co client vao phong
                        $("#pClient").text(msg.player2);
                        $("#avatar2").attr("src", getAvatar(msg.player2));
                        $("#pClient").css('display', 'block');
                        $("#notice").css("display", "none");
                        $("#btnBatDau").css("display", "block");
                        $("#btnKick").css("display", "block");
                        $("#" + roomID + "_2").attr("src", getAvatar(msg.player2));
                        audio_mess.play();
                    } else { //Ko co client vao phong
                        $("#pClient").text("");
                        $("#avatar2").attr("src", "avatars/null.jpg");
                        $("#pClient").css('display', 'block');
                        $("#notice").text("Đang chờ người chơi khác!");
                        $("#notice").css("display", "block");
                         $("#btnKick").css("display", "none");
                        $("#btnBatDau").css("display", "none");
                    }
                } else {
                    if (msg.player2 !== playerName) { 
                        roomIDGlobal=0;
                        closeRoom();
                    }
                    if (msg.playing === true) {
//                                    document.location = "room.jsp?roomID=" + roomID + "&playerName=" + playerName + "&e=" + $("#pHost").text() + "&p=c";
                        playGame('client');
                    }
                }

            });
}



function drawTable(x, y, id) {
    var imgsrc = "images/roomList/chairs.png";
    var imgTable = "images/roomList/table.png";
    var div = document.createElement('div');
    div.style.position = 'absolute';
    div.style.left = x + 'px';
    div.style.top = y + 'px';
    div.style.width = 180 + 'px';
    div.style.height = 70 + 'px';
    div.id = 'tadiv_' + id;
    div.className = 'table_div';
    $("#roomList").append(div);

    //Ghe 1

    var imgPiece = document.createElement('img');
    imgPiece.src = imgsrc;
    imgPiece.id = id + "_1";
    imgPiece.className = "chairs";
    imgPiece.name = 'null';
    imgPiece.style.position = 'absolute';
    imgPiece.style.left = '0px';
    imgPiece.style.top = '10px';
    imgPiece.onerror = function () {
        imgError(this);
    };
    div.append(imgPiece);
    //Ten player 1
    imgPiece = document.createElement('b');
    var text = document.createTextNode("");
    imgPiece.appendChild(text);
    imgPiece.id = "tabP" + id + "_1";
    imgPiece.className = "tabPlayername";
    imgPiece.style.position = 'absolute';
    imgPiece.style.left = 0 + 'px';
    imgPiece.style.top = 0 + 60 + 'px';
    div.append(imgPiece);
    //Ghe 2
    imgPiece = document.createElement('img');
    imgPiece.src = imgsrc;
    imgPiece.id = id + "_2";
    imgPiece.className = "chairs";
    imgPiece.name = 'null';
    imgPiece.style.position = 'absolute';
    imgPiece.style.left = (50 + 80) + 'px';
    imgPiece.style.top = (10) + 'px';
    imgPiece.onerror = function () {
        imgError(this);
    };
    div.append(imgPiece);
    //Ten player 2
    imgPiece = document.createElement('b');
    var text = document.createTextNode("");
    imgPiece.appendChild(text);
    imgPiece.id = "tabP" + id + "_2";
    imgPiece.className = "tabPlayername";
    imgPiece.style.position = 'absolute';
    imgPiece.style.right = 0 + 'px';
    imgPiece.style.top = 0 + 60 + 'px';
    $(imgPiece).css("text-align", "right");
    div.append(imgPiece);
    //Cai ban`
    imgPiece = document.createElement('img');
    imgPiece.src = imgTable;
    imgPiece.className = "table";
    imgPiece.id = "table_" + id;
    imgPiece.style.position = 'absolute';
    imgPiece.style.left = (50 + 5) + 'px';
    imgPiece.style.top = '0px';
    div.append(imgPiece);
    //So ban
    imgPiece = document.createElement('b');
    var text = document.createTextNode(id);
    imgPiece.appendChild(text);
    imgPiece.id = "tabnu_" + id;
    imgPiece.className = "tableNumber";
    imgPiece.style.position = 'absolute';
    imgPiece.style.left = 0 + 78 + 'px';
    imgPiece.style.top = 0 + 8 + 'px';
    div.append(imgPiece);
}

function imgError(img) {
    img.src = "avatars/default.jpg";
}

function getAvatar(playerName) {
    return "avatars/" + playerName + ".jpg";
//                if (fileExist(url)) {
//                    return "avatars/" + playerName + ".jpg";
//                } else {
//                    return "avatars/default.jpg";
//                }
}

function fileExist(url) {
    var http = new XMLHttpRequest();
    http.open('HEAD', url, false);
    http.send();
    return http.status !== 404;
}


