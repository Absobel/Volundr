<%@ include file="header.jsp" %>
    <%@ page language="java" import="pack.*, java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <% Utilisateur userSession=(Utilisateur) session.getAttribute("userSession"); %>



            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <title>Insert title here</title>
                <style>
                    @import url('https://fonts.googleapis.com/css2?family=Jost:ital,wght@0,100..900;1,100..900&display=swap');

                    * {
                        font-family: "Jost", sans-serif;
                        letter-spacing: 0.05em;
                        margin: 0 auto;
                    }

                    body {
                        height: 80vh;
                        display: flex;
                        flex-direction: column;
                        justify-content: center;
                        position: relative;
                    }

                    .spacer {
                        flex-grow: 1;
                    }

                    button,
                    p,
                    div,
                    strong {
                        margin: 0.5rem auto;
                    }

                    strong {
                        color: red;
                    }

                    div {
                        display: grid;
                        grid-auto-columns: 1fr 1fr;
                        grid-auto-rows: 1fr;
                        grid-template-columns: 1fr 2fr;
                        grid-template-rows: 1fr 1fr 1fr 1fr 1fr 1fr;
                        gap: 20px;
                    }

                    button {
                        border: none;
                        padding: 0.3rem 1rem;
                        border-radius: 2rem;
                    }

                    form {
                        display: inline-block;
                        margin: 0.5rem auto;
                    }
                </style>


            </head>

            <body>

                Mes groupes : <br>
                <br>
                <% Collection<Groupe> gs = (Collection<Groupe>) userSession.getGroupesU();
                        for(Groupe g : gs) {
                        if ( g.getIsNotEventGroup()){


                        String s = g.getNom();

                        %>
                        <%= s %> <br>
                            <blockquote>
                                <p>
                                    <% for (Utilisateur u : g.getUtilisateurs()){ String userName=u.getPrenom() + " " +
                                        u.getNom(); %>
                                        <%= userName %> <br>
                                            <% } %>
                                </p>
                            </blockquote>
                            <% } } %>

            </body>

            </html>