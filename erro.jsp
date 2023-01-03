<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Tela para mostrar os erros</title>
</head>
<body>
<h1>Mensagem de erro---> Entre em contato 9810</h1>

</body>

<% 
 out.print(request.getAttribute("msg"));
%>

</html>