<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>P�gina de Teste de Recebimento de Par�metros</title>
</head>
<body>

<%
   String nome  = request.getParameter("nome");
   String idade = request.getParameter("idade");
   out.println("Nome : " + nome);
   out.println("Idade : " + idade);
%>

</body>
</html>