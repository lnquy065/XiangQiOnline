<!DOCTYPE>

<html>
<head>
<meta charset = "utf-8" />
<title> SMART GARDEN </title>
<meta name="description" content="Đề tài NCKH Nông trại thông minh" />
<meta name="author" content="Tuyet Trinh Pham" />
<meta name="keyword" content="Smart Garden,Nong trai thong minh, Iot" />
<style type="text/css">

	body {margin: 0;padding: 0;background: #EBA641 url(images/img03.jpg) no-repeat center top;font-family: Arial, Helvetica, sans-serif;font-size: 14px;color: #FFFFFF;}
	
	h1, h2, h3 {margin: 0;padding: 0;font-weight: normal;color: #2C2723;}
	h1 {font-size: 2em;}
	h2 {font-size: 2.4em;}
	h3 {font-size: 1.6em;}
	p, ul, ol {margin-top: 0;line-height: 180%;}
	ul, ol {}
	a {text-decoration: none;color: #FFFFFF;}
	a:hover {text-decoration: underline;}

	#wrapper {background: url(http://imgur.com/DB6Ov7n) repeat-x left top;}

	/* Header */
	#header {width: 980px;height: 63px;margin: 0 auto;}
	#logo {float: left;width: 660px;height: 63px;}
	#logo h1 {margin: 0;padding: 5px 0px 0px 30px;}
	#logo h1 {float: left;letter-spacing: -1px;text-transform: lowercase;text-shadow: #FFFFFF -1px 1px 2px;font-family: Georgia, Times New Roman, Times, serif;font-size: 42px;color: #2D2722;}

	#search {
		float: right;
		width: 320px;
		height: 63px;
	}

	#search form {
		padding: 18px 0px 0px 80px;
	}

	#search fieldset {
		margin: 0;
		padding: 0;
		border: none;
	}

	#search-text {
		width: 195px;
		padding: 6px 10px;
		border: none;
		background: #FFFFFF;
		text-transform: lowercase;
		font: normal 11px Arial, Helvetica, sans-serif;color: #7F7F81;}
	#search-submit {display: none;}

	/* Menu */
	#menu {width: 980px;height: 46px;margin: 0 auto;padding: 15px 0px 0px 0px;}
	#menu ul {margin: 0;padding: 0px 0px 0px 4px;list-style: none;line-height: normal;}
	#menu li {float: left;}
	#menu a {display: block;float: left;height: 24px;padding: 4px 30px 0px 30px;text-decoration: none;text-transform: lowercase;text-shadow: #8A5608 0px 1px 1px;font-family: Arial, Helvetica, sans-serif;font-size: 16px;font-weight: normal;color: #FFFFFF;border: none;}
	#menu a:hover {text-decoration: none;}
	#splash {width: 980px;height: 340px;margin: 0px auto;background: no-repeat left top;}

	/* Page */
	#page {width: 1000px;margin: 0 auto;padding: 0;}
	#page-bgtop {}
	#page-bgbtm {margin: 0px;padding: 20px 30px 0px 30px;}
	#tr, td {padding: 30px; color:	#FF4500;font-weight:bold; font-size: 20px}
	/* Content */
	#content {float: left;width: 540px;padding: 0px 0px 0px 20px;}
	.post {padding-top: 20px;padding-bottom: 10px;}
	.post .title {margin: 0px;padding-bottom: 10px;letter-spacing: -1px;color: #2C2723;text-decoration: none;border: none;}
	.post .entry {text-align: justify;margin-bottom: 25px;padding: 10px 0px 10px 0px;background-size: cover;background-position: center center;}
	.links {display: block;width: 96px;padding: 2px 0px 2px 0px;background: #A53602;text-align: center;text-transform: uppercase;font-size: 10px;color: #FFFFFF;}

	/* Sidebar */
	#sidebar {float: right;width: 330px;padding: 20px 0px 0px 0px;}
	#sidebar ul {margin: 0;padding: 0;list-style: none;}
	#sidebar li {margin: 0;padding: 0;}
	#sidebar li ul {margin: 0px 15px;padding-bottom: 30px;}
	#sidebar li li {padding-left: 15px;line-height: 35px;border-bottom: 1px solid #C89039;}
	#sidebar li li span {display: block;margin-top: -20px;padding: 0;font-size: 11px;font-style: italic;}
	#sidebar h2 {height: 38px;margin-bottom: 20px;padding: 12px 0px 0px 15px;letter-spacing: -1px;font-size: 28px;color: #2C2723;}
	#sidebar p {margin: 0 0px;padding: 0px 20px 20px 20px;text-align: justify;}
	#sidebar a {border: none;}
	#sidebar a:hover {text-decoration: underline;}
	
	/* Footer */
	#footer-wrapper {overflow: hidden;padding: 0px 0px 0px 0px;background: url(http://imgur.com/bBfQlGx) repeat-x left top;background-color: #2D2722;}
	#footer {clear: both;width: 980px;height: 100px;margin: 20px auto 0px auto;font-family: Arial, Helvetica, sans-serif;}
	#footer p {margin: 0;padding: 30px 0px 0px 0px;line-height: normal;font-size: 10px;text-transform: uppercase;text-align: center;color: #61544B;}
	#footer a {color: #61544B;}
	#three-columns {width: 900px;margin: 0px auto 30px auto;padding: 50px 0px 30px 0px;}
	#three-columns ul {margin: 0px;padding: 0px;list-style: none;}
	#three-columns li {padding: 4px 0px 6px 0px;border-bottom: 1px solid #29231F;}
	#three-columns h2 {padding: 0px 0px 20px 0px;font-size: 28px;color: #B0A792;}
	#column1 {float: left;width: 280px;margin-right: 30px;}
	#column2 {float: left;width: 280px;}
	#column3 {float: right;width: 280px;}
</style>
<script type="text/JavaScript">
    <!--
        function AutoRefresh( t ) {
        setTimeout("location.reload(true);", t);
        }
         //-->
</script>

</head>
<body onload="JavaScript:AutoRefresh(3000);">
<div id="wrapper">
	<div id="menu">
		<ul>
			<li><a href="#">Home</a></li>
			<li><a href="#">Blog</a></li>
			<li><a href="#">Photos</a></li>
			<li><a href="#">About</a></li>
			<li><a href="#">Links</a></li>
			<li><a href="#">Contact</a></li>
		</ul>
	</div>
	<!-- end #menu -->
	<div id="header">
		<div id="logo">
			<h1>Internet Of Things</h1>
		</div>
		<!-- code đăng nhập -->
		<div id="search">
			<form method="get" action="">
				<fieldset>
					<input type="text" name="s" id="search-text" size="15" value="enter keywords here..." />
					<input type="submit" id="search-submit" value="GO" />
				</fieldset>
			</form>
		</div>
	</div>
	<div id="splash"><img src="http://i.imgur.com/Be0vTdy.jpg" title="source: imgur.com" /></div>
	<!-- end #header -->
	<div id="page">
		<div id="page-bgtop">
			<div id="page-bgbtm">
				<div id="content">
					<div class="post">
						<h2 class="title">Welcome to Smart Garden</h2>
						<div class="entry">
							<p><strong>Smart Garden </strong>,hệ thống vườn thông minh với các thành phần có thể kết nối với nhau và kết nối với người dùng, điều này cho phép người sử dụng có thể sáng tạo, tương tác và chăm sóc khu vườn của mình. Được trang bị cảm biến nhiệt độ, độ ẩm để theo dõi độ ẩm đất, nhiệt độ và độ ẩm của không khí và các yếu tố môi trường khác, tất cả những điều này đều ảnh hưởng phần lớn đến sức khỏe các loài thực vật. Và sau đó gửi thông tin theo thời gian lên website để người dùng có thể tương tác với khu vườn. </p>
						</div>
					</div>
					<div class="post">
						<h2 class="title">Information of Garden</h2>
						<div class="entry" style="font-size: 16px; font-style: italic;">
							<br> </br>
							<?php
								date_default_timezone_set('Asia/Ho_Chi_Minh');
								echo date("d/m/Y h:i:s A") ."<br>";
			
								if (isset($_GET["temp"]))
								{
									$nhietdo_string = $_GET["temp"];
									$doam_string = $_GET["hum"];
					
									$filename = "data.txt";
									$handle = fopen($filename, "w");
									fwrite( $handle, $nhietdo_string."\n");
									fwrite( $handle, $doam_string);
									fclose($handle);
								}
								$filename = "data.txt";
								$handle = fopen($filename, "r");
								$temp = fgets($handle);
								$hum = fgets($handle);
								fclose($handle);
								
								echo "<table border=1 cellspacing=1 cellpading=1> <br>  
								<tr> <td><font color=blue>Nhiet do</td><td>$temp do C</font></td></tr>   
								<tr> <td><font color=blue>Do am</td> <td>$hum %</font></td></tr>  
								</table>";  
							?>	
						</div>
					</div>
					<div class="post">
						<h2 class="title">Control System</h2>
						<div class="entry">
							<ul><p> Chọn chế độ hoạt động của hệ thống :</p>
								<li><p>Chế độ tự động (Auto): Hệ thống tưới tự động bật cho đến khi nhiệt độ, độ ẩm phù hợp cho cây trồng thì sẽ tự động tắt.</p></li>
								<li><p>TurnOn: Bật hệ thống.</p></li>
								<li><p>TurnOff: Tắt hệ thống.</p></li>
							</ul>
							<?php
								if($_SERVER['REQUEST_METHOD'] == "POST" and isset($_POST['Auto']))
								{
									Auto();
								}
								function Auto()
								{
									echo "Auto Mode";  
									$filecontent=fopen("mode.txt","w");
									fwrite($filecontent,'auto');
									fclose($filecontent);
								}
								if($_SERVER['REQUEST_METHOD'] == "POST" and isset($_POST['TurnOn']))
								{
									TurnOn();
								}
								function TurnOn()
								{
									echo "Turn Motor is On";
									$filecontent=fopen("mode.txt","w");
									fwrite($filecontent,'on');
									fclose($filecontent);   
								}
								if($_SERVER['REQUEST_METHOD'] == "POST" and isset($_POST['TurnOff']))
								{
									TurnOff();
								}
								function TurnOff()
								{
									echo "Turn Motor is Off";  
									$filecontent=fopen("mode.txt","w");
									fwrite($filecontent,'off');
									fclose($filecontent);  
								}
							?>
							<form  method="post" style="text-align: center; ">
								<input type="submit" name="Auto" onclick="alert('Đã bật chế độ Auto')" value="Auto" size="20" />
								<input type="submit" name="TurnOn" onclick="alert('Đã bật hệ thống')" value="Turn On" size="20"/>
								<input type="submit" name="TurnOff" onclick="alert('Đã tắt hệ thống')" value="Turn Off" size="20"/>
							</form>
						</div>
					</div>

				</div>
				<!-- end #content -->
				<div id="sidebar">
					<ul>
						<li>
							<h2>Tài liệu tham khảo</h2>
							<ul>
								<li><a href="http://vietjack.com/php/index.jsp">Học PHP căn bản</a></li>
								<li><a href="https://thachpham.com/category/web-development/html-css"> HTML&CSS </a></li>
								<li><a href="http://vietjack.com/javascript/index.jsp">Học Javascript cơ bản và nâng cao</a></li>
								<li><a href="http://vietjack.com/bai-tap-php/index.jsp">Bài tập PHP</a></li>
								<li><a href="https://www.w3schools.com/html/default.asp"></a>HTML Tutorial</li>
							</ul>
						</li>
					</ul>
					<br> </br>
					<div >
						<a href="http://imgur.com/A0qdXsc"><img src="http://i.imgur.com/A0qdXsc.jpg" title="source: imgur.com" width="380px" height="300px"/></a>
						<br>
						</br>
						<a href="http://imgur.com/u2YQL7a"><img src="http://i.imgur.com/u2YQL7a.jpg" title="source: imgur.com" width="380px" height="300px"/></a>
					</div>
				</div>
				<!-- end #sidebar -->
				<div style="clear: both;">&nbsp;</div>
			</div>
		</div>
	</div>
	<!-- end #page -->
</div>
<div id="footer-wrapper">
	<div id="three-columns">
		<div id="column1">
			<h2>Contact</h2>
			<ul>
				<li>Tuyết Trinh Phạm</li>
				<li>Phone: 01692378870</li>
				<li>Email: phamtuyettrinh96@gmail.com</li>
				<li> Thành Đạt Phạm</li>
				<li>Phone: 0965758218</li>
				<li>Email: datpham7987@gmail.com</li>
			</ul>
		</div>
		<div id="column2">
			<h2>Follow us</h2>
			<ul>
				<li><a href="https://www.facebook.com/tuyettrinh.pham.756">Facebook</a></li>
				<li><a href="https://www.instagram.com/phamtuyettrinh/">Instagram</a></li>
			</ul>
		</div>
		<div id="column3">
			<h2>Associate</h2>
			<ul>
				<li><a href="http://vietjack.com/">Vietjack.com</a></li>
				<li><a href="https://www.w3schools.com/">w3schools.com</a></li>
				<li><a href="https://www.codecademy.com/learn/all">Codecademy</a></li>
			</ul>
		</div>
	</div>
	<div id="footer">
		<p>Copyright (c) 2017.</p>
	</div>
	<!-- end #footer -->
</div>
</body>
</html>
