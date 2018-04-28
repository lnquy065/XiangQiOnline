$("#profile_ava").attr('src', getAvatar($("#profile_name").text()));
var audioState=true;
function imgError(img) {
    img.src = "avatars/default.jpg";
}

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

function fileExist(url) {
    var http = new XMLHttpRequest();
    http.open('HEAD', url, false);
    http.send();
    return http.status !== 404;
}

function NavOption() {
    $("#btnNavOption").css('display', 'none');
    $("#btnNavOptionOff").css('display', 'block');

    $("#btnExit").animate({
        bottom: '60px',
        left: '5px'
    }, 400);
    $("#btnMusicOn").animate({
        bottom: '110px',
        left: '5px'
    }, 300);
    $("#btnSoundOn").animate({
        bottom: '160px',
        left: '5px'
    }, 200);
    $("#btnMusicOff").animate({
        bottom: '110px',
        left: '5px'
    }, 300);
    $("#btnSoundOff").animate({
        bottom: '160px',
        left: '5px'
    }, 200);

    $('#btnChangeColor').animate({
        bottom: '210px',
        left: '5px'
    }, 100);
    $("#btnThemeOn").animate({
        bottom: '260px',
        left: '5px'
    }, 100);
    $("#btnThemeOff").animate({
        bottom: '260px',
        left: '5px'
    }, 100);
    $("#btnStyle1").animate({
        bottom: '260px',
        left: '5px'
    }, 100);
    $("#btnStyle2").animate({
        bottom: '260px',
        left: '5px'
    }, 100);
    $("#btnStyle3").animate({
        bottom: '260px',
        left: '5px'
    }, 100);
    $("#btnStyle4").animate({
        bottom: '260px',
        left: '5px'
    }, 100);

}
function NavOptionOff() {
    $("#btnNavOptionOff").css('display', 'none');
    $("#btnNavOption").css('display', 'block');
    $('#btnThemeOff').css('display', 'none');
    $('#btnThemeOn').css('display', 'block');

    $("#btnExit").animate({
        bottom: '5px',
        left: '5px'
    }, 400);
    $("#btnMusicOn").animate({
        bottom: '5px',
        left: '5px'
    }, 300);
    $("#btnMusicOff").animate({
        bottom: '5px',
        left: '5px'
    }, 300);
    $("#btnSoundOn").animate({
        bottom: '5px',
        left: '5px'
    }, 200);
    $("#btnSoundOff").animate({
        bottom: '5px',
        left: '5px'
    }, 200);
    $("#btnThemeOn").animate({
        bottom: '5px',
        left: '5px'
    }, 100);
    $("#btnThemeOff").animate({
        bottom: '5px',
        left: '5px'
    }, 100);
    $("#btnChangeColor").animate({
        bottom: '5px',
        left: '5px'
    }, 100);
    $("#btnStyle1").animate({
        bottom: '5px',
        left: '5px'
    }, 100);
    $("#btnStyle2").animate({
        bottom: '5px',
        left: '5px'
    }, 100);
    $("#btnStyle3").animate({
        bottom: '5px',
        left: '5px'
    }, 100);
    $("#btnStyle4").animate({
        bottom: '5px',
        left: '5px'
    }, 100);
}

function ThemeOn(id) {
    var theme = $("#" + id);

    var x = theme.offset().left;
    var y = theme.offset().top;
    theme.css('display', 'none');
    $('#btnThemeOff').css('display', 'block');
    showStyle(4, 40, 100, 7);
}

function ThemeOff(id) {
    var theme = $("#" + id);

    theme.css('display', 'none');
    $('#btnThemeOn').css('display', 'block');

    hideStyle(4, 100);
}

function ChangeSide() {

    changeSkinSide();
}

function profile_down() {
    $('#profile_btnDown').css("display", "none");
    $('#profile_btnUp').css("display", "block");

    $('#profile_extend').animate({
        top: '50px',
    }, 200);
}
function profile_up() {
    $('#profile_btnDown').css("display", "block");
    $('#profile_btnUp').css("display", "none");



    $('#profile_extend').animate({
        top: '-500px',
    }, 200);
}

function SoundOn() {
    //Tat am thanh
    $("#btnSoundOn").css("display", "none");
    $("#btnSoundOff").css("display", "inline");
    audioState = false;
}

function SoundOff() {
    //Bat am thanh
    $("#btnSoundOff").css("display", "none");
    $("#btnSoundOn").css("display", "inline");
    audioState = true;
}

function MusicOn() {
    //Tat am thanh
    $("#btnMusicOn").css("display", "none");
    $("#btnMusicOff").css("display", "inline");
    audio_bg.pause();
}

function MusicOff() {
    //Bat am thanh
    $("#btnMusicOff").css("display", "none");
    $("#btnMusicOn").css("display", "inline");
    audio_bg.play();
    audio_click.play();
}

function Exit() {
    var con = confirm("Bạn sẽ thua nếu thoát khỏi phòng chơi!");
    if (con === true) {
        exitRoom();
    }
}

function showStyle(max, stepPos, stepTime, offset) {
    for (i = 1; i <= max; i++)
        $("#btnStyle" + i).animate({
            left: offset + (stepPos * i) + 'px'
        }, stepTime * i);
}

function hideStyle(max, stepTime) {
    for (i = 1; i <= max; i++)
        $("#btnStyle" + i).animate({
            left: '5px'
        }, stepTime * i);
}

