<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>Начальная страница</title>
		<link rel="stylesheet" href="css/board.css">
	</head>
	
	<body>
		
		${banner}
		<div class="centered_text"><h1>board title</h1></div>
		<hr/>
		${page_locator}
		<hr/>
		${create_thread_form}
		${threads_area}
		<hr/>
		${page_locator}
		<hr/>
		${footer}
	</body>
</html>
