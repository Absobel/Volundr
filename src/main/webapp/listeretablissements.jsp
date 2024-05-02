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

Liste des etablissements : <br>
<br>
<% 
Collection<Etablissement> es = (Collection<Etablissement>) request.getAttribute("listeetablissements");
for(Etablissement e : es) {		
	
	String s = e.getNom() ;
	
%>
<%= s %> <br>
<%
}
%>

</body>
</html>