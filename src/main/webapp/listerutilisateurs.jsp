<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="pack.*"%>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

Liste des Utilisateurs : <br>
<br>
<% 
Collection<Utilisateur> us = (Collection<Utilisateur>) request.getAttribute("listeutilisateurs");
for(Utilisateur u : us) {		
	
	String s = u.getPrenom() + " " + u.getNom() ;
	
%>
<%= s %> <br>
<%
}
%>

</body>
</html>