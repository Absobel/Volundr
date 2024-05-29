<%@ include file="header.jsp" %>
    <%@ page language="java" import="pack.*, java.util.*, java.text.SimpleDateFormat, java.time.*"
        contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <% Utilisateur userSession=(Utilisateur) session.getAttribute("userSession"); %>

            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <title>Insert title here</title>
                <link rel="stylesheet" type="text/css" href="style.css">
            </head>

            <body>
                <!-- //dateFormat.format(ev.getDebutInscr()) + " | " + dateFormat.format(ev.getFinInscr());-->
                <p>Mes Evenements :</p> <br>
                <br>
                <% Collection<Groupe> gs = (Collection<Groupe>) userSession.getGroupesU();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        for(Groupe g : gs) {
                        if (!(g.getIsNotEventGroup())){
                        for( Evenement ev : g.getEvenements()){

                        String s = ev.getNom();
                        %>
                        <%= s %><br><br>
                            <% if (ev.getDebutInscr() !=null && ev.getFinInscr() !=null){ LocalDateTime
                                debut=ev.getDebutInscr().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                                LocalDateTime
                                fin=ev.getFinInscr().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                                if(LocalDateTime.now().isBefore(debut)){ %>
                                L'inscription n'a pas commencée. Début de l'inscription : <%=
                                    dateFormat.format(ev.getDebutInscr()) %>
                                    <% } else if (debut.isBefore(LocalDateTime.now()) &&
                                        LocalDateTime.now().isBefore(fin)) { %>
                                        <input type="hidden" id="eventTime" value=<%=dateFormat.format(ev.getFinInscr())
                                            %>
                                        >
                                        <a href="webscheduler/page_ajout_choix.html?user=<%= userSession.getMail()%>&id=<%= ev.getId()%>"
                                            id="pach"> Choix Créneau</a>
                                        <div id="countdown"></div>
                                        <script>
                                            var countdownElement = document.getElementById("countdown");
                                            var e = new Date(eventTime.value);
                                            function time() {
                                                var d = new Date();
                                                var timeDifference = e - d;

                                                var daysRemaining = Math.floor(timeDifference / (1000 * 60 * 60 * 24));
                                                var hoursRemaining = Math.floor((timeDifference % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
                                                var minutesRemaining = Math.floor((timeDifference % (1000 * 60 * 60)) / (1000 * 60));
                                                var secondsRemaining = Math.floor((timeDifference % (1000 * 60)) / 1000);

                                                var countdownText = "Temps restant pour l'inscription :" + daysRemaining + "j" + hoursRemaining + "h" + minutesRemaining + "m" + secondsRemaining + "s";
                                                countdownElement.textContent = countdownText;

                                }
                                setInterval(time, 1000);
                            </script>                
                            
                            <% 
                            } else {
                            %>
                                L'inscription est terminée. Vous ne pouvez plus soumettre vos préférences. <br>
                                <% 
                                if (ev.getAffectationDone()) {
                                %>
                                <a href="webscheduler/page_affichage_choix.html?user=<%= userSession.getMail()%>&id=<%= ev.getId()%>" > Affichage du Choix</a> <br>
                                <% 
                                } else {
                                %>
                                    L'affectation n'a pas encore été faite. Veuillez revenir plus tard. <br>
                               <% }
                            }
                        } else {
                            %> Il n'y a pas encore de date associée à cet évènement. Veuillez revenir plus tard. <br>
                            <%        
                        } %> <br> <%
                    }
                }
            } %><br>

<a href="index.jsp" id="retourAcceuil"/> Retour</a><br>
</body>

            </html>