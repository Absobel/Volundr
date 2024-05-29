<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page import="pack.*" %>
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
                <% Collection<Etablissement> es = (Collection<Etablissement>)
                        request.getAttribute("listeetablissements");
                        for(Etablissement e : es) {

                        String s = e.getNom() + " " + e.getId() ;

                        %>
                        <%= s %> <br>
                            <blockquote>
                                <p>
                                    <% for (Salle sa : e.getSalles()){ String numSalle=sa.getBatiment() + sa.getNumero()
                                        + " : " + sa.getCapacite() + " places" ; %>
                                        <%= numSalle %> <br>
                                            <% } %>
                                </p>
                            </blockquote>
                            <% } %>
                            <a href="index.jsp" id="retourAcceuil"/> Retour</a><br>
            </body>

            </html>