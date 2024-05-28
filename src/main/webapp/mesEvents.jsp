<%@ page language="java" import="pack.*, java.util.*, java.text.SimpleDateFormat, java.time.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
Utilisateur userSession = (Utilisateur) session.getAttribute("userSession");
%>



<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>

<body>
    <!-- //dateFormat.format(ev.getDebutInscr()) + " | " + dateFormat.format(ev.getFinInscr());-->
    Mes Evenements : <br>
    <br>
    <% Collection<Groupe> gs = (Collection<Groupe>) userSession.getGroupesU();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for(Groupe g : gs) {
                if (!(g.getIsNotEventGroup())){
                    for( Evenement ev : g.getEvenements()){
                        
                        String s = ev.getNom();
                        %>
                        <%= s %><br><br>
                        <%
                        if(LocalDateTime.now().isBefore((ev.getDebutInscr().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()))){
                            %>
                            Début Event : <%= dateFormat.format(ev.getDebutInscr()) %>
                            <%
                        }
                        else { %>
                         <input type="hidden" id="eventTime" value="2024-05-29" >
                         <a href="webscheduler/page_ajout_choix.html?user=<%= userSession.getMail()%>&id=<%= ev.getId()%>" id="pach"> Choix Créneau</a> 
                         <div id="countdown"></div>
                         <script>
                            var countdownElement = document.getElementById("countdown");
                            function time() {
                                var d = new Date();
                                var s = d.getSeconds();
                                var m = d.getMinutes();
                                var h = d.getHours();
                                var e = document.getElementById("eventTime").value;
                                var date2 = new Date(e);
                                var timeDifference = e - d;

                                var daysRemaining = Math.floor(timeDifference / (1000 * 60 * 60 * 24));
                                var hoursRemaining = Math.floor((timeDifference % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
                                var minutesRemaining = Math.floor((timeDifference % (1000 * 60 * 60)) / (1000 * 60));
                                var secondsRemaining = Math.floor((timeDifference % (1000 * 60)) / 1000);

                                var countdownText = daysRemaining + " jours, " + hoursRemaining + " heures, " + minutesRemaining + " minutes, " + secondsRemaining + " secondes";
                                countdownElement.textContent = countdownText;

                            }
                            setInterval(time, 1000);
                         </script>                
                        
                        <% } 
                    }
                }
            } %>

</body>

</html>