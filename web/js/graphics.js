/**
 * @description Hiển thị quân cờ theo vị trí
 * @param imgsrc Nguồn hình
 * @param x Vị trí X
 * @param y Vị trí Y
 */
function drawPiece(pIndex, imgsrc, x, y) {
    var imgPiece = document.createElement('img');
    var fileName = imgsrc.split("/");
    imgPiece.src = imgsrc;
   // console.log(fileName[fileName.length-1]);
    imgPiece.className = fileName[fileName.length-1].replace(/.png*$/, "");
    imgPiece.style.position = 'absolute';
    imgPiece.style.left = x + 'px';
    imgPiece.style.top = y + 'px';
    imgPiece.hidden = 1;
    $("#banCo").append(imgPiece);
    $(imgPiece).fadeIn(500);
}


/**
 * @description Hiển thị tâm nhắm vào quân địch
 * @param x Vị trí X
 * @param y Vị trí Y
 */
function drawTargetSprite(x, y) {
    var imgPiece = document.createElement('img');
    imgPiece.className = 'focus';
    imgPiece.src = targetIMG;
    imgPiece.style.position = 'absolute';
    imgPiece.style.left = (x * 60) + 'px';
    imgPiece.style.top = (y * 60) + 'px';
    $(imgPiece).hover(function () {
        imgPiece.src = targetHover;
    }, function () {
        imgPiece.src = targetIMG;
    })
    $("#banCo").append(imgPiece);
}

function drawDebugSprite(x, y) {
    return;
    var imgPiece = document.createElement('img');
    imgPiece.className = 'debugPoint';
    imgPiece.src = 'images/debugPoint.png';
    imgPiece.style.position = 'fixed';
    imgPiece.style.left = x + 'px';
    imgPiece.style.top = y + 'px';
    $(document.body).append(imgPiece);
}

/**
 * @description Hiển thị vị trí quân cờ có thể đi
 * @param x Vị trí X
 * @param y Vị trí Y
 */
function drawStepSprite(x, y) {
    if (x < 0 || x > 8 || y < 0 || y > 9)
        return false;
    if (piecesPostion[y][x] !== -1)
        return false;
    var imgPiece = document.createElement('img');
    imgPiece.className = 'step';
    imgPiece.src = stepIMG;
    imgPiece.style.position = 'absolute';
    imgPiece.style.left = (x * 60) + 'px';
    imgPiece.style.top = (y * 60) + 'px';
    $(imgPiece).hover(function () {
        imgPiece.src = stepHover;
    }, function () {
        imgPiece.src = stepIMG;
    })
    $("#banCo").append(imgPiece);
}

/**
 * @description Hiển thị tâm chọn quân cờ đang chọn
 * @param x Vị trí X
 * @param y Vị trí Y
 */
function drawSelectedSprite(x, y) {
    var imgPiece = document.createElement('img');
    imgPiece.id = "selected";
    imgPiece.src = selectedIMG;
    imgPiece.style.position = 'absolute';
    imgPiece.style.left = (x * 60) + 'px';
    imgPiece.style.top = (y * 60) + 'px';
    $("#banCo").append(imgPiece);
}

function drawCheckMateSprite(pIndex, x, y) {
    if (getPiecesIndex(x, y)!==getEnemyG(pIndex)) return;
    var imgPiece = document.createElement('img');
    imgPiece.className = "checkMate";
    imgPiece.src = checkMateIMG;
    imgPiece.style.position = 'absolute';
    imgPiece.style.left = (x * 60) + 'px';
    imgPiece.style.top = (y * 60) + 'px';
    $("#banCo").append(imgPiece);
}

//function drawAllSteps(pIndex, x, y) {
//    if (x < 0 || x > 8 || y < 0 || y > 9)
//        return false;
//    switch (pIndex) {
//        case 0: //Tuong
//            drawStepsInRange(pIndex, x, y - 1);
//            drawStepsInRange(pIndex, x, y + 1);
//            drawStepsInRange(pIndex, x + 1, y);
//            drawStepsInRange(pIndex, x - 1, y);
//            break;
//        case 1: //Si
//            drawStepsInRange(pIndex, x - 1, y - 1);
//            drawStepsInRange(pIndex, x + 1, y + 1);
//            drawStepsInRange(pIndex, x + 1, y - 1);
//            drawStepsInRange(pIndex, x - 1, y + 1);
//            break;
//        case 2: //Bo
//            if (getPiecesIndex(x - 1, y - 1) < 0)
//                drawStepsInRange(pIndex, x - 2, y - 2);
//            if (getPiecesIndex(x + 1, y + 1) < 0)
//                drawStepsInRange(pIndex, x + 2, y + 2);
//            if (getPiecesIndex(x - 1, y + 1) < 0)
//                drawStepsInRange(pIndex, x - 2, y + 2);
//            if (getPiecesIndex(x + 1, y - 1) < 0)
//                drawStepsInRange(pIndex, x + 2, y - 2);
//            break;
//        case 3: //Ma
//            if (getPiecesIndex(x, y - 1) < 0)
//                drawStepsInRange(pIndex, x + 1, y - 2);
//            if (getPiecesIndex(x, y - 1) < 0)
//                drawStepsInRange(pIndex, x - 1, y - 2);
//
//            if (getPiecesIndex(x, y + 1) < 0)
//                drawStepsInRange(pIndex, x + 1, y + 2);
//            if (getPiecesIndex(x, y + 1) < 0)
//                drawStepSprite(x - 1, y + 2);
//
//            if (getPiecesIndex(x + 1, y) < 0)
//                drawStepsInRange(pIndex, x + 2, y - 1);
//            if (getPiecesIndex(x + 1, y) < 0)
//                drawStepsInRange(pIndex, x + 2, y + 1);
//
//            if (getPiecesIndex(x - 1, y) < 0)
//                drawStepsInRange(pIndex, x - 2, y - 1);
//            if (getPiecesIndex(x - 1, y) < 0)
//                drawStepsInRange(pIndex, x - 2, y + 1);
//            break;
//        case 5: //Phao
//            //Tren
//            var i = y - 1;
//            var stepScan = 0;
//            while (stepScan < 2) {
//                if (!isInBoard(x, i))
//                    break;
//                if (getPieceColorPos(x, i) !== -1)
//                    stepScan++;
//                drawStepsInRange(stepScan === 0 || stepScan === 2 ? pIndex : -4, x, i);
//                i--;
//            }
//            //Duoi
//            i = y + 1;
//            stepScan = 0;
//            while (stepScan < 2) {
//                if (!isInBoard(x, i))
//                    break;
//                if (getPieceColorPos(x, i) !== -1)
//                    stepScan++;
//                drawStepsInRange(stepScan === 0 || stepScan === 2 ? pIndex : -4, x, i);
//                i++;
//            }
//            //Trai
//            i = x - 1;
//            stepScan = 0;
//            while (stepScan < 2) {
//                if (!isInBoard(i, y))
//                    break;
//                if (getPieceColorPos(i, y) !== -1)
//                    stepScan++;
//                drawStepsInRange(stepScan === 0 || stepScan === 2 ? pIndex : -4, i, y);
//                i--;
//            }
//            //Phai
//            i = x + 1;
//            stepScan = 0;
//            while (stepScan < 2) {
//                if (!isInBoard(i, y))
//                    break;
//                if (getPieceColorPos(i, y) !== -1)
//                    stepScan++;
//                drawStepsInRange(stepScan === 0 || stepScan === 2 ? pIndex : -4, i, y);
//                i++;
//            }
//            break;
//        case 4: //Xe
//            //Tren
//            var i = y - 1;
//            while (getPiecesIndex(x, i) === -1) {
//                drawStepsInRange(pIndex, x, i);
//                i--;
//            }
//            drawStepsInRange(pIndex, x, i);
//            //Duoi
//            i = y + 1;
//            while (getPiecesIndex(x, i) === -1) {
//                drawStepsInRange(pIndex, x, i);
//                i++;
//            }
//            drawStepsInRange(pIndex, x, i);
//            //Trai
//            i = x - 1;
//            while (getPiecesIndex(i, y) === -1) {
//                drawStepsInRange(pIndex, i, y);
//                i--;
//            }
//            drawStepsInRange(pIndex, i, y);
//            //Phai
//            i = x + 1;
//            while (getPiecesIndex(i, y) === -1) {
//                drawStepsInRange(pIndex, i, y);
//                i++;
//            }
//            drawStepsInRange(pIndex, i, y);
//            break;
//        case 6: //Tot
//            drawStepsInRange(pIndex, x, y - 1);
//            if (y <= 4) {
//                drawStepsInRange(pIndex, x + 1, y);
//                drawStepsInRange(pIndex, x - 1, y);
//            }
//            break;
//    }
//}

/**
 * @description Hiển thị tất cả vị trí mà quân cờ có thể đi
 * @param pIndex Mã số quân cờ
 * @param x Vị trí X hiện tại
 * @param y Vị trí Y hiện tại
 * @returns {Boolean}
 */
function drawAllSteps(pIndex, x, y) {
    if (x < 0 || x > 8 || y < 0 || y > 9)
        return false;
    switch (pIndex) {
        case 0: //Tuong
            drawStepsInRange(pIndex, x, y - 1);
            drawStepsInRange(pIndex, x, y + 1);
            drawStepsInRange(pIndex, x + 1, y);
            drawStepsInRange(pIndex, x - 1, y);
            
            //Tuong doi tuong
            var i = y - 1;
            while (getPiecesIndex(x, i) === -1) {   //Tim quan doi dien
                i--;
            }
            if (getPiecesIndex(x, i)===7) { //Gap tuong ben kia
                drawTargetSprite(x, i);
            }
            
            break;
        case 1: //Si
            drawStepsInRange(pIndex, x - 1, y - 1);
            drawStepsInRange(pIndex, x + 1, y + 1);
            drawStepsInRange(pIndex, x + 1, y - 1);
            drawStepsInRange(pIndex, x - 1, y + 1);
            break;
        case 2: //Bo
            if (getPiecesIndex(x - 1, y - 1) < 0)
                drawStepsInRange(pIndex, x - 2, y - 2);
            if (getPiecesIndex(x + 1, y + 1) < 0)
                drawStepsInRange(pIndex, x + 2, y + 2);
            if (getPiecesIndex(x - 1, y + 1) < 0)
                drawStepsInRange(pIndex, x - 2, y + 2);
            if (getPiecesIndex(x + 1, y - 1) < 0)
                drawStepsInRange(pIndex, x + 2, y - 2);
            break;
        case 3: //Ma
            if (getPiecesIndex(x, y - 1) < 0)
                drawStepsInRange(pIndex, x + 1, y - 2);
            if (getPiecesIndex(x, y - 1) < 0)
                drawStepsInRange(pIndex, x - 1, y - 2);

            if (getPiecesIndex(x, y + 1) < 0)
                drawStepsInRange(pIndex, x + 1, y + 2);
            if (getPiecesIndex(x, y + 1) < 0)
                drawStepSprite(x - 1, y + 2);

            if (getPiecesIndex(x + 1, y) < 0)
                drawStepsInRange(pIndex, x + 2, y - 1);
            if (getPiecesIndex(x + 1, y) < 0)
                drawStepsInRange(pIndex, x + 2, y + 1);

            if (getPiecesIndex(x - 1, y) < 0)
                drawStepsInRange(pIndex, x - 2, y - 1);
            if (getPiecesIndex(x - 1, y) < 0)
                drawStepsInRange(pIndex, x - 2, y + 1);
            break;
        case 5: //Phao
            //Tren
            var i = y - 1;
            var stepScan = 0;
            while (stepScan < 2) {
                if (!isInBoard(x, i))
                    break;
                if (getPieceColorPos(x, i) !== -1)
                    stepScan++;
                drawStepsInRange(stepScan === 0 || stepScan === 2 ? pIndex : -4, x, i);
                i--;
            }
            //Duoi
            i = y + 1;
            stepScan = 0;
            while (stepScan < 2) {
                if (!isInBoard(x, i))
                    break;
                if (getPieceColorPos(x, i) !== -1)
                    stepScan++;
                drawStepsInRange(stepScan === 0 || stepScan === 2 ? pIndex : -4, x, i);
                i++;
            }
            //Trai
            i = x - 1;
            stepScan = 0;
            while (stepScan < 2) {
                if (!isInBoard(i, y))
                    break;
                if (getPieceColorPos(i, y) !== -1)
                    stepScan++;
                drawStepsInRange(stepScan === 0 || stepScan === 2 ? pIndex : -4, i, y);
                i--;
            }
            //Phai
            i = x + 1;
            stepScan = 0;
            while (stepScan < 2) {
                if (!isInBoard(i, y))
                    break;
                if (getPieceColorPos(i, y) !== -1)
                    stepScan++;
                drawStepsInRange(stepScan === 0 || stepScan === 2 ? pIndex : -4, i, y);
                i++;
            }
            break;
        case 4: //Xe
            //Tren
            var i = y - 1;
            while (getPiecesIndex(x, i) === -1) {
                drawStepsInRange(pIndex, x, i);
                i--;
            }
            drawStepsInRange(pIndex, x, i);
            //Duoi
            i = y + 1;
            while (getPiecesIndex(x, i) === -1) {
                drawStepsInRange(pIndex, x, i);
                i++;
            }
            drawStepsInRange(pIndex, x, i);
            //Trai
            i = x - 1;
            while (getPiecesIndex(i, y) === -1) {
                drawStepsInRange(pIndex, i, y);
                i--;
            }
            drawStepsInRange(pIndex, i, y);
            //Phai
            i = x + 1;
            while (getPiecesIndex(i, y) === -1) {
                drawStepsInRange(pIndex, i, y);
                i++;
            }
            drawStepsInRange(pIndex, i, y);
            break;
        case 6: //Tot
            drawStepsInRange(pIndex, x, y - 1);
            if (y <= 4) {
                drawStepsInRange(pIndex, x + 1, y);
                drawStepsInRange(pIndex, x - 1, y);
            }
            break;
    }
}

/**
 * @description Hiển thị vị trí quân cờ có thể đi trong phạm vi nhất định.
 * @param {type} pIndex
 * @param {type} x
 * @param {type} y
 */
function drawStepsInRange(pIndex, x, y) {
    var checkOk = false;
    //Kiem tra vung di chuyen
    switch (pIndex) {
        case 0: //Tuong, Si
        case 1:
            if (x >= 3 && x <= 5 && y >= 7 && y <= 9)
                checkOk = true;
            break;
        case 2:
            if (y >= 5 && y <= 9)
                checkOk = true;
            break;
        default :
            checkOk = true;
    }
    // console.log(pIndex);
    if (checkOk) {
        //console.log(x+"-"+y+": "+ (isInBoard(x,y)?  piecesPostion[y][x]: -5 ));
        if (isInBoard(x, y) && getPieceColor(piecesPostion[y][x]) === 1 && pIndex !== -4) {
            drawTargetSprite(x, y);
        } else if (pIndex !== -4)
            drawStepSprite(x, y);
    }
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