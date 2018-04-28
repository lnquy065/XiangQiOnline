//////Do: 0 -> 6
//Den: 7 -> 13
// Phe | Tuong | Si | Bo | Ma | Xe | Phao | Tot
//  Do |   0   |  1 |  2 | 3  |  4 |  5   |  6
//  Den|   7   |  8 |  9 | 10 | 11 | 12   | 13



document.title = playerName;
var RED = 0;
var BLACK = 1;
var NULL = -2;
var NONE = -1;

var turn;
var redSide = true;
var totalTime = 15 * 60;
var totalTurnTime = 3 * 60;
var pWidth = 60;
var pieceStyle = 'images/style2/';
var pieceSelected = -1;
var pieceSelectedPos = [-1, -1];
var stepIMG = 'images/available.png';
var stepHover = 'images/availableHover.png';
var selectedIMG = 'images/selected.png';
var targetIMG = 'images/focus.png';
var targetHover = 'images/focusHover.png';
var checkMateIMG = 'images/checkmate.png';
var pieceIMG = new Array('tuongDo.png', 'siDo.png', 'boDo.png', 'maDo.png',
        'xeDo.png', 'phaoDo.png', 'totDo.png', 'tuongDen.png', 'siDen.png', 'boDen.png', 'maDen.png',
        'xeDen.png', 'phaoDen.png', 'totDen.png');



var audioState = true;
var audio_bg = new Audio('sounds/room.mp3');
audio_bg.loop = true;
var audio_move = new Audio('sounds/move2.mp3');
var audio_kill = new Audio('sounds/kill.mp3');
var audio_mess = new Audio('sounds/mess.mp3');
var audio_gameover = new Audio('sounds/endGame.wav');
var audio_checkmate = new Audio('sounds/gameover.mp3');
var audio_click = new Audio('sounds/click.mp3');
audio_bg.play();


var banCo = $("#banCo");
var piecesPostion = [[-1, -1, -1, -1, -1, -1, -1, -1, -1],
    [-1, -1, -1, -1, -1, -1, -1, -1, -1],
    [-1, -1, -1, -1, -1, -1, -1, -1, -1],
    [-1, -1, -1, -1, -1, -1, -1, -1, -1],
    [-1, -1, -1, -1, -1, -1, -1, -1, -1],
    [-1, -1, -1, -1, -1, -1, -1, -1, -1],
    [-1, -1, -1, -1, -1, -1, -1, -1, -1],
    [-1, -1, -1, -1, -1, -1, -1, -1, -1],
    [-1, -1, -1, -1, -1, -1, -1, -1, -1],
    [-1, -1, -1, -1, -1, -1, -1, -1, -1]];


requestBoard();

loadAvatar();
$("#roomID").text(roomID);
loadPieces(piecesPostion, pieceStyle, pieceIMG);
requestTimeInit();
clockTimer(updateClock);
requestLoop();
window.setInterval("requestLoop()", 1000);
drawDebugSprite(0,0);
//loadSkins(pieceStyle, redSide);


/**
 * @description Bắt sự kiện click chuột.
 */
$(document).ready(function () {
    //Click chuot
    $("#banCo").mousedown(function (event) {
        if (event.which !== 1 || turn !== playerName)
            return; //Chi nhan chuot Trai

        //Lay toa do
        var xBC = parseInt(banCo.offset().left);
        var yBC = parseInt(banCo.offset().top);
        var xPos = parseInt((event.pageX - xBC) / pWidth);
        var yPos = parseInt((event.pageY - yBC) / pWidth);
        if(!isInBoard(xPos, yPos)) return;
        
        //console.log(xPos+" "+yPos);
        var pSelected = piecesPostion[yPos][ xPos]; //quan co dc chon
        //Kiem tra mode
        if (pieceSelected === -1 || getPieceColor(pSelected) === 0) { //Chua chon co, chon lai quan khac
            playSfxSound(audio_click);
            clearGUI();
            if (getPieceColorPos(xPos, yPos)===BLACK) return;
            selectPiece(pSelected, xPos, yPos);
            
        } else {    //Da chon
            // console.log("Room: "+roomID);
            var p, xStep, yStep;
            $(".step").each(function () {
                p = $(this).position();
                xStep = parseInt((p.left + 30) / pWidth);
                yStep = parseInt((p.top + 30) / pWidth);
                //console.log("    Step Pos: "+xStep+" - "+yStep);
                if (xStep === xPos && yStep === yPos) {
                    // console.log("Di chuyen");
                    // movePiece(pieceSelectedPos[0], pieceSelectedPos[1], xPos, yPos);
                    requestMovingToServer(piecesPostion[pieceSelectedPos[1]][pieceSelectedPos[0]], pieceSelectedPos[0] + "-" + pieceSelectedPos[1], xPos + "-" + yPos);

                    switchTurn(enemyName);
                    return false;
                }
                ;
            });
            $(".focus").each(function () {
                p = $(this).position();
                xStep = parseInt((p.left + 30) / pWidth);
                yStep = parseInt((p.top + 30) / pWidth);
                // console.log("  Focus Pos: "+p.left+" - "+p.top);
                if (xStep === xPos && yStep === yPos) {
                    //   console.log("An doi phuong");
                    // movePiece(pieceSelectedPos[0], pieceSelectedPos[1], xPos, yPos);
                    requestMovingToServer(piecesPostion[pieceSelectedPos[1]][pieceSelectedPos[0]], pieceSelectedPos[0] + "-" + pieceSelectedPos[1], xPos + "-" + yPos);


                    switchTurn(enemyName);
                    return false;
                }
            });
            clearGUI();
            pieceSelected = -1;
        }
    });
    $("#chatBoxEmoj").click(function () {
        if ($("#chatBoxEmojDiv").is(":visible")) {
            $("#chatBoxEmojDiv").hide(300);

        } else {
            $("#chatBoxEmojDiv").show(300);
        }
    });

    $(".emo").click(function () {
        var tmp = $("#chatBoxInput").val();
        $("#chatBoxInput").val(tmp + $(this).text());
    });
    $("#endGameOk").click(function() {
       exitRoom();
       window.location.replace("lobby.jsp");
    });
});

function imgError(img) {
    img.src = "avatars/default.jpg";
}

function changeSkin(styleID) {
    var style = 'images/style' + styleID + '/';


    if ($('.tuongDen').attr('src') !== pieceStyle + 'tuongDen' + '.png') { //Neu dang doi phe
        pieceStyle = style;
        for (i = 0; i < pieceIMG.length; i++) {
            var className = pieceIMG[i].replace(/.png*$/, "");
            var classNew = pieceIMG[i < 7 ? i + 7 : i - 7].replace(/.png*$/, "");
            $('.' + className).fadeOut(200);
            $('.' + className).attr('src', style + classNew + '.png');
            $('.' + className).fadeIn(200);

        }
    } else {
        // console.log('else');
        pieceStyle = style;
        for (i = 0; i < pieceIMG.length; i++) {
            var className = pieceIMG[i].replace(/.png*$/, "");
            $('.' + className).fadeOut(200)
            $('.' + className).attr('src', style + className + '.png');
            $('.' + className).fadeIn(200);
        }
    }



}

function changeSkinSide() {
    var style = pieceStyle;
    for (i = 0; i < pieceIMG.length; i++) {
        var className = pieceIMG[i].replace(/.png*$/, "");
        var classNew = pieceIMG[i].replace(/.png*$/, "");

        if ($('.' + className).attr('src') !== style + classNew + '.png') {
            $('.' + className).fadeOut(200);
            $('.' + className).attr('src', style + classNew + '.png');
            $('.' + className).fadeIn(200);
        } else {

            var classNew = pieceIMG[i < 7 ? i + 7 : i - 7].replace(/.png*$/, "");
            $('.' + className).fadeOut(200);
            $('.' + className).attr('src', style + classNew + '.png');
            $('.' + className).fadeIn(200);
        }
    }
}


function playSfxSound(audio) {
    if (audioState === true) {
        audio.play();
    }
}

/**
 * @description Tải và hiển thị Avatar cho Player và Enemy
 */
function loadAvatar() {
    if (playerSide === "h") {
        $("#avatar1").attr("src", getAvatar(playerName));
        $("#avatar2").attr("src", getAvatar(enemyName));
    } else {
        $("#avatar2").attr("src", getAvatar(playerName));
        $("#avatar1").attr("src", getAvatar(enemyName));
    }
}

/**
 * @description Hiển thị các quân cờ
 * @param positionArray Mảng chứa vị trí
 * @param style Kiểu quân cờ
 * @param imageArray Mảng chứa hình ảnh
 */
function loadPieces(positionArray, style, imageArray) {

    for (i = 0; i < 10; i++) {
        for (j = 0; j < 9; j++) {

            var pIndex = positionArray[i][j];
            //console.log(i+" - " +j + ": "+pIndex)
            if (pIndex !== -1)
                drawPiece(pIndex, style + imageArray[pIndex], j * 60, i * 60);
        }
    }
}


/**
 * @description Chuyển lượt chơi: Viền Avatar và Hiện thông báo
 * @param player Tên người chơi
 */
function switchTurn(player) {
    if (playerSide === "h") {
        if (player === playerName) {
            $("#avatar1").css("border", "3px solid green");
            $("#avatar2").css("border", "none");
        } else {
            $("#avatar2").css("border", "3px solid green");
            $("#avatar1").css("border", "none");
        }
    } else {
        if (player !== playerName) {
            $("#avatar1").css("border", "3px solid green");
            $("#avatar2").css("border", "none");
        } else {
            $("#avatar2").css("border", "3px solid green");
            $("#avatar1").css("border", "none");
        }
    }
//    var turn=playerName;
//    if (player !== playerName)
//        turn = 'đối thủ!';
//    $("#notice").html("Tới lượt " + turn);
    $("#notice").html("Tới lượt <b id='turnPlayerName'>" + player + "<b>");


}

function getEnemyG(pIndex) {
    if (pIndex < 7 && pIndex >= 0)
        return 7;
    else if (pIndex >= 7)
        return 0;
    return pIndex;
}



function ktChieuTuong(x, y) {
    x=parseInt(x);
    y=parseInt(y);
    var i=0;
    var pIndex = getPiecesIndex(x,y);
   // console.log("Di: "+pIndex);
    switch(pIndex) {
        case 10:
          case 3: //Ma
            if (getPiecesIndex(x, y - 1) < 0) {  //Chieu tuong
                    drawCheckMateSprite(pIndex, x + 1, y - 2);
                }
            
            if (getPiecesIndex(x, y - 1) < 0)
                drawCheckMateSprite(pIndex, x - 1, y - 2);

            if (getPiecesIndex(x, y + 1) < 0)
                drawCheckMateSprite(pIndex, x + 1, y + 2);
            if (getPiecesIndex(x, y + 1) < 0)
                drawCheckMateSprite(pIndex, x - 1, y + 2);

            if (getPiecesIndex(x + 1, y) < 0)
                drawCheckMateSprite(pIndex, x + 2, y - 1);
            
            if (getPiecesIndex(x + 1, y) < 0)
                drawCheckMateSprite(pIndex, x + 2, y + 1);

            if (getPiecesIndex(x - 1, y) < 0)
                drawCheckMateSprite(pIndex, x - 2, y - 1);
            
            if (getPiecesIndex(x - 1, y) < 0 )
                drawCheckMateSprite(pIndex, x - 2, y + 1);
            break;
        case 11:
        case 4: //Xe
            //Tren
          //  console.log("tren");
            i = y - 1;
            while (getPiecesIndex(x, i) === -1) {
                //drawStepsInRange(pIndex, x, i);
               // console.log(x+" "+y+ ": "+getPiecesIndex(x, i));
                i--;
            }
          //  console.log(x+" "+y+ ": "+getPiecesIndex(x, i));
                    drawCheckMateSprite(pIndex, x, i);
            //Duoi
          //  console.log("duoi");
            i = y + 1;
            
            while (getPiecesIndex(x, i) === -1) {
                //drawStepsInRange(pIndex, x, i);
               // console.log(x+" "+y+ ": "+getPiecesIndex(x, i));
                i++;
            }
           // console.log(x+" "+y+ ": "+getPiecesIndex(x, i));
                    drawCheckMateSprite(pIndex, x, i);
            //Trai
            i = x - 1;
            while (getPiecesIndex(i, y) === -1) {
               // drawStepsInRange(pIndex, i, y);
                i--;
            }
                    drawCheckMateSprite(pIndex, i, y);
            //Phai
            i = x + 1;
            while (getPiecesIndex(i, y) === -1) {
                //drawStepsInRange(pIndex, i, y);
                i++;
            }
                    drawCheckMateSprite(pIndex, i, y);
            break;
        case 13:
        case 6: //Tot
             drawCheckMateSprite(pIndex, x, y - 1);
            if (y <= 4) {
                drawCheckMateSprite(pIndex, x + 1, y);
                drawCheckMateSprite(pIndex, x - 1, y);
            }
            break;
        case 5:
        case 12: //phao
            //Tren
            i = y - 1;
            var stepScan = 0;
            while (stepScan < 2) {
                //console.log("Step: "+ stepScan);
                if (!isInBoard(x, i))
                    break;
                if (stepScan>0) drawCheckMateSprite(pIndex, x, i);
                if (getPieceColorPos(x, i) !== -1)
                    stepScan++;
                

              //  console.log(getPiecesIndex(x, i));
                i--;
            }
            //Duoi
           // console.log("duoi");
            i = y + 1;
            stepScan = 0;
            while (stepScan < 2) {
                //console.log(x+" "+i+": " + getPiecesIndex(x, i));
                if (!isInBoard(x, i))
                    break;
                if (stepScan>0)     drawCheckMateSprite(pIndex, x, i);
                if (getPieceColorPos(x, i) !== -1)
                    stepScan++;
                
                
                i++;
            }
            //Trai
            i = x - 1;
            stepScan = 0;
            while (stepScan < 2) {
                if (!isInBoard(i, y))
                    break;
                if (stepScan>0)    drawCheckMateSprite(pIndex, x, i);
                if (getPieceColorPos(i, y) !== -1)
                    stepScan++;
                
                i--;
            }
            //Phai
            i = x + 1;
            stepScan = 0;
            while (stepScan < 2) {
                if (!isInBoard(i, y))
                    break;
                 if (stepScan>0)   drawCheckMateSprite(pIndex,x, i);
                if (getPieceColorPos(i, y) !== -1)
                    stepScan++;
                  
                i++;
            }
            break;
            
    }
    if ($(".checkMate").length) {
        playSfxSound(audio_checkmate);
    }
}


/**
 * @description Xử lý khi chọn quân cờ
 * @@param pIndex Mã số quân cờ  
 * @param x Vị trí X
 * @param y Vị trí Y
 */
function selectPiece(pIndex, x, y) {
    pieceSelected = pIndex;
    pieceSelectedPos[0] = x;
    pieceSelectedPos[1] = y;
    if (piecesPostion[y][x] !== -1)
        drawSelectedSprite(x, y);
    drawAllSteps(pIndex, x, y);
}


/**
 * @description Kiểm tra vị trí X,Y có trong bàn cờ không.
 * @param x
 * @param y
 * @returns {Boolean}
 */
function isInBoard(x, y) {
    if (x < 0 || x > 8 || y < 0 || y > 9)
        return false;
    return true;
}

/**
 * @description Trả về màu sắc của quân cờ
 * @param pIndex Mã số quân cờ
 * @returns {Number} 0: Đỏ | 1: Đen | pIndex
 */
function getPieceColor(pIndex) {
    if (pIndex < 7 && pIndex >= 0)
        return 0;
    else if (pIndex >= 7)
        return 1;
    return pIndex;
}

/**
 * @description Trả về màu sắc của quân cờ.
 * @param {type} x
 * @param {type} y
 * @returns {Number}
 */
function getPieceColorPos(x, y) {
    if (!isInBoard(x, y))
        return -1;
    return getPieceColor(piecesPostion[y][x]);
}

/**
 * @description Trả về Mã số quân cờ tại vị trí X,Y.
 * @param {type} x
 * @param {type} y
 * @returns {Number}
 */
function getPiecesIndex(x, y) {
    //Ra ngoai
    if (x < 0 || x > 8 || y < 0 || y > 9)
        return -2;   //Ra bien
    else
        return piecesPostion[y][x];
}

/**
 * @description Xóa Seleted, Steps, Target.
 */
function clearGUI() {
    //Xoa GUI
    $(".step").remove();
    $(".focus").remove();
    $("#selected").remove();
}

/**
 * @description Di chuyển quân cờ
 * @param {type} xO Vị trí cũ
 * @param {type} yO
 * @param {type} x Vị trí mới
 * @param {type} y
 */
function movePiece(xO, yO, x, y, tt) {
    $(".checkMate").remove();
    var xBC = parseInt(banCo.offset().left);
    var yBC = parseInt(banCo.offset().top);
    clearGUI();
    var piece = document.elementFromPoint(xO * pWidth + xBC + 30, yO * pWidth + yBC + 30);    //Tim quan co dang chon
    drawDebugSprite(xO * pWidth + xBC + 30, yO * pWidth + yBC + 30);
    
    $(piece).css("z-index", 10);
    playSfxSound(audio_move);
    $(piece).animate({
        left: x * pWidth,
        top: y * pWidth
    }, 400);
    $(piece).css("z-index", 0);

    if (getPieceColorPos(x, y) >= 0 && getPieceColorPos(x, y) !== getPieceColorPos(xO, yO)) {   //An co doi phuong
        var pieceEnemy = document.elementFromPoint(x * pWidth + xBC + 30, y * pWidth + yBC + 30);
        var pieceIndex = piecesPostion[y][x];
        playSfxSound(audio_kill);
        $(pieceEnemy).animate({
            left: "-=10",
            top: "-=10",
            width: "80px",
            heigh: "80px",
            opacity: "0"
        }, 400, function () {
            $(this).remove();
            checkwin(tt);
        });
    }

    piecesPostion[y][x] = piecesPostion[yO][xO];
    piecesPostion[yO][xO] = -1;


}

function checkwin(tt) {
    tt = $.trim(tt);
    //console.log(tt + " " + playerSide);
    if (tt === "0") {
        if (playerSide === "h") {
            gameOver("win");
        } else {
            gameOver("lose");
        }
    }

    if (tt === "7") {
        if (playerSide === "h") {
            gameOver("lose");
        } else {
            gameOver("win");
        }
    }
}

/**
 * @description Chuyển hh:mm:ss thành Giây.
 * @param {type} s
 * @returns {Number}
 */
function parseTimeToSecond(s) {
    var c = s.split(":");
    return parseInt(c[0]) * 60 * 60 + parseInt(c[1]) * 60 + parseInt(c[2]);
}

/**
 * @description Chuyển mm:ss thành Giây.
 * @param {type} s
 * @returns {Number}
 */
function parseMinuteToSecond(s) {
    var c = s.split(":");
    return parseInt(c[0]) * 60 + parseInt(c[1]);
}

/**
 * @description Chuyển Giây thành mm:ss.
 * @param {type} s
 * @returns {String}
 */
function parseSecondToTime(s) {
    var mm = parseInt(s / 60);
    var ss = s % 60;
    if (mm.toString().length === 1)
        mm = '0' + mm;
    if (ss.toString().length === 1)
        ss = '0' + ss;

    return mm + ":" + ss;
}

function updateClock() {
   // console.log("timeout");
    var dateObj = new Date().getTime() / 1000;
    var deltaTime = parseInt(dateObj - $("#startTime").text());
    var deltaTurnTime = totalTurnTime - parseInt(dateObj - $("#startTurnTime").text());
    //console.log(deltaTurnTime);
    if (deltaTurnTime <= 0) {
        if (turn === playerName) {
            gameOver("lose");
        } else {
            gameOver("win");
        }
    } else {
        $("#turnTime").text(parseSecondToTime(deltaTurnTime));
        $("#totalTime").text(parseSecondToTime(deltaTime));
    }
    //clockEl.innerHTML = pad(dateObj.getHours()) + ':' + pad(dateObj.getMinutes()) + ':' + pad(dateObj.getSeconds());
}

function clockTimer(fn) {
    var time = 1000 - (Date.now() % 1000);

    setTimeout(function () {
        fn();
        clockTimer(fn);
    }, time);
}

/**
 * @description Xử lý khi kết thúc game
 * @param {type} state "Win", "Lose", "Runout"
 */
function gameOver(state) {
    exitRoom();
    if ($("#endGame").css("display") === "block")
        return;
    audio_gameover.play();
    if (state === "win") {  //Thang
        $("#endGameIMG").attr("src", "images/victory.png");
        $('.endGameFlagL').attr("hidden", '1');
    } else if (state === "lose") {  //Bai
        $("#endGameIMG").attr("src", "images/defeat.png");
        $('.endGameFlagW').attr("hidden", '1');
    } else if (state === "loseCheat") {    
        $("#endGameIMG").attr("src", "images/defeat.png");
        $("#endGameAvatar").css("display", "none");
        $("#endGameText").text("Phát hiện gian lận!");
        $("#endGameText").css("display", "inline");
        $('.endGameFlagL').attr("hidden", '1');
        $('.endGameFlagW').attr("hidden", '1');
    } else if (state === "winCheat") {
        $("#endGameIMG").attr("src", "images/victory.png");
        $("#endGameAvatar").css("display", "none");
        $("#endGameText").text("Đối thủ chơi gian lận!");
        $("#endGameText").css("display", "inline");
        $('.endGameFlagL').attr("hidden", '1');
        $('.endGameFlagW').attr("hidden", '1');
    } else {//Doi thu thoat room
        $("#endGameIMG").attr("src", "images/victory.png");
        $("#endGameAvatar").css("display", "none");
        $("#endGameText").css("display", "inline");
        $('.endGameFlagL').attr("hidden", '1');
    }
    $("#endGameAvatar").attr("src", getAvatar(playerName));
    $("#endGameOk").text("Ok(15)");
    $("#endGame").css("display", "block");
    $("#endGameGlassPane").animate({
        opacity: 0.9
    }, 300);
    $("#endGameNotice").animate({
        width: "300px",
        height: "200px"
    }, 300);
    //Dem nguoc
    var time = 15;
    var timer = setInterval(function () {
        $("#endGameOk").text("Ok(" + time + ")");
        time--;
        if (time < 0) {
            clearInterval(timer);
            exitRoom();
            document.location = "lobby.jsp";
        }
    }, 1000);
}





/**
 * @description Xử lý thoát khỏi phòng: Báo cho Server, Quay trở về Sảnh.
 */
function exitRoom() {
    $.ajax({
        method: "POST",
        url: "RoomServlet",
        data: {cmd: "exit", playerName: playerName, roomID: roomID}
    });
}

/**
 * @description Kích người chơi khác ra khỏi phòng.
 * @param player Tên người chơi bị kick.
 */

/**
 * @description Trả về Link chứa Avatar.
 * @param {type} playerName
 */
function getAvatar(playerName) {
    playerName = $.trim(playerName);
    return "avatars/" + playerName + ".jpg";
    var url = "./avatars/" + playerName + ".jpg";
    if (fileExist(url)) {
        return "avatars/" + playerName + ".jpg";
    } else {
        return "avatars/default.jpg";
    }
}

function boardToArray(array, string) {
    var rows = string.split('|');
    for (i = 0; i < rows.length; i++) {
        var cels = rows[i].split(",");
        for (j = 0; j < cels.length; j++) {
            array[i][j] = parseInt(cels[j]);
        }
    }
}

function fileExist(url) {
    var http = new XMLHttpRequest();
    http.open('HEAD', url, false);
    http.send();
    return http.status !== 404;
}


function requestBoard() {
    $.ajax({
        method: "POST",
        url: "RoomServlet",
        async: false,
        data: {cmd: "getBoard", roomID: roomID, side: playerSide}
    })
            .done(function (msg) {
                //console.log(msg);
                boardToArray(piecesPostion, msg);
            });

}



function requestPlayerName() {
    $.ajax({
        method: "POST",
        url: "RoomServlet",
        data: {cmd: "done", playerName: playerName, roomID: roomID}
    })
            .done(function (msg) {
                //   alert("Data Saved: " + msg);
            });
}

/**
 * @description Gửi yêu cầu: Di chuyển quân cờ
 * @param {type} roomID
 * @param {type} playerName
 * @param {type} pieceIndex
 * @param {type} pieceOldPos
 * @param {type} pieceNewPos
 */
function requestMovingToServer(pieceIndex, pieceOldPos, pieceNewPos) {
    $.ajax({
        method: "POST",
        url: "PieceMovingServlet",
        async: false,
        data: {roomID: roomID, playerName: playerName, side: playerSide, pieceIndex: pieceIndex, oldPos: pieceOldPos, newPos: pieceNewPos, turnTime: new Date().getTime() / 1000}
    })
            .done(function (msg) {
               // console.log(msg);
                var data = msg.split("|")
                var pos = data[0].split(",");
                $("#startTurnTime").text(parseInt(data[1]) + 1);
                movePiece(pos[0], pos[1], pos[2], pos[3], data[2]);
                if (data[3].toString().trim()==="false") {
                    
                    gameOver("loseCheat");
                }
                
                
            });
}


/**
 * @description Gủi yêu cầu: Yêu cầu tới lượt chơi.
 * @param {type} roomID
 * @param {type} playerName
 * @returns {undefined}
 */
function requestTurnToServer(roomID, playerName) {
    $.ajax({
        method: "POST",
        url: "RoomServlet",
        async: false,
        data: {cmd: "wait", playerName: playerName, roomID: roomID}
    })
            .done(function (msg) {
                $("#startTurnTime").text(msg);
            });
}

/**
 * @description Gửi yêu cầu: Lấy mốc thời gian Tổng và thời gian lượt.
 */
function requestTimeInit() {
    $.ajax({
        method: "POST",
        url: "RoomServlet",
        data: {cmd: "time", roomID: roomID}
    })
            .done(function (msg) {
                var par = msg.split("|");
                $("#startTime").text(par[0]);
                $("#startTurnTime").text(par[1]);
            });

}




/**
 * @description Giả lập RealTime. Gửi yêu cầu liên tục lên Server. Nhận lại: Đối thủ thoát khỏi phòng.\nĐối thủ di chuyển.\nLượt đánh.
 */
function requestLoop() {
    $.ajax({
        method: "POST",
        url: "RoomServlet",
        async: false,
        data: {cmd: "refesh", roomID: roomID, playerName: playerName, enemyName: enemyName, timeRequest: parseInt(new Date().getTime() / 1000)}
    })
            .done(function (result) {
                result = $.trim(result);
        
                //console.log(result);
                var m = result.split("|");
                if (((3 * 60) - parseMinuteToSecond($("#turnTime").text())) > 5 && $.trim(m[1]) == 'false') { //Doi thu bo chay    
                    gameOver("enemyRun");
                    // kickPlayer(enemyName);
                    return;
                }

                var msg = JSON.parse(m[0]);
                if (msg.nonCheat===false) {
                    gameOver("winCheat");
                    return;
                }

                //Ben kia di
                //console.log("State: " + msg.state + " Player: " + msg.player);
                if (msg.state === "moving" && msg.player !== playerName) { //=>> Doi phuong di chuyen
                    // console.log("moving");
                    var oldPos = msg.oldPosition;
                    oldPos = oldPos.split("-");
                    var newPos = msg.newPosition;
                    newPos = newPos.split("-");
                    movePiece(oldPos[0], oldPos[1], newPos[0], newPos[1], m[2]);
                    ktChieuTuong(newPos[0], newPos[1]);
                    requestTurnToServer(roomID, playerName);    //=>> Yeu cau den luot Minh`

                }
                if (msg.state === "wait") {     //==>> Đang chờ đối phương Đi
                    turn = msg.player;
                    switchTurn(turn);
                    //  $("#notice").html("Tới lượt "+msg.player);
                }

                //kiem tra tin nhan
                if (m.length === 4) {
                    addMessage("enemy", m[3]);
                }

            });
}

function addMessage(player, mess) {

    var messPool = $("#messengePool");
    if (player !== playerName) { //Tin nhan cua doi phuong
        messPool.append('<div class="messBlock"> <p class="messYou">' + mess + '</p> </div>');
        playSfxSound(audio_mess);
    } else
        messPool.append('<div class="messBlock"> <p class="messMe">' + mess + '</p> </div>');
    $("#messengePool").each(function ()
    {
        // certain browsers have a bug such that scrollHeight is too small
        // when content does not fill the client area of the element
        var scrollHeight = Math.max(this.scrollHeight, this.clientHeight);
        this.scrollTop = scrollHeight - this.clientHeight;
    });
}

function sendMessage() {
    $("#chatBoxEmojDiv").hide(300);
    var mess = $("#chatBoxInput").val();
    $("#chatBoxInput").val('');
    addMessage(playerName, mess);
    $.ajax({
        method: "POST",
        url: "RoomServlet",
        data: {cmd: "chat", roomID: roomID, playerName: playerName, message: mess}
    });
}




$(window).keydown(function (e) {
    if (e.keyCode === 13 && $("#chatBoxInput").is(":focus")) {
        sendMessage();
    }
});