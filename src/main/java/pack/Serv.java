package pack;

import java.io.IOException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.ejb.EJB;
// import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Serv")
public class Serv extends HttpServlet {

  @EJB
  Facade facade;

  HttpSession session;

  @Override
  public void init() {
    facade.loader();

  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    RequestDispatcher rd = req.getRequestDispatcher("index.html");
    // String op = req.getParameter("op");
    rd.forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    session = req.getSession(false);
    if (session != null) {
      session.setAttribute("userSession", facade.updateUserSession());
    } else {
      // req.getRequestDispatcher("index.html").forward(req, resp);
      switch (req.getParameter("op")) {
        case "chargerutilisateur":
          String mail = req.getParameter("mail");
          String password = req.getParameter("password");
          if (facade.verifierUtilisateur(mail, password)) {

            session = req.getSession();
            session.setMaxInactiveInterval(30 * 60);

            session.setAttribute("userSession", facade.getUserSession());
            req.getRequestDispatcher("default.jsp").forward(req, resp);
          } else {
            req.getRequestDispatcher("testlogin.html").forward(req, resp);
          }
          break;
        case "inscrireutilisateur":
          String nom = req.getParameter("nom");
          String prenom = req.getParameter("prenom");
          String newmail = req.getParameter("newmail");
          String cmail = req.getParameter("cmail");
          String newpassword = req.getParameter("newpassword");
          String cword = req.getParameter("cword");
          if (newmail.equals(cmail) && newpassword.equals(cword)) {
            if (facade.verifierMail(cmail)) {
              req.getRequestDispatcher("inscription3.html").forward(req, resp);
            } else {
              facade.ajoutUtilisateur(nom, prenom, newmail, newpassword);
              req.getRequestDispatcher("index.html").forward(req, resp);
            }
          } else {
            req.getRequestDispatcher("testinscription.html").forward(req, resp);
          }
          break;
        case "creerutilisateur":
          req.getRequestDispatcher("inscription.html").forward(req, resp);
          break;
        case "retournerarriere":
          req.getRequestDispatcher("index.html").forward(req, resp);
          break;
        default:
          req.getRequestDispatcher("index.html").forward(req, resp);
          break;
      }
    }
    switch (req.getParameter("op")) {
      case "deconnexion":
        session.invalidate();
        req.getRequestDispatcher("index.html").forward(req, resp);
        break;
      case "creeretablissement":
        req.getRequestDispatcher("ajoutEtablissement.html").forward(req, resp);
        break;
      case "ajoutetablissement":
        String ename = req.getParameter("nomEtablissement");
        facade.ajoutEtablissement(ename);
        req.setAttribute("userSession", facade.getUserSession());
        req.getRequestDispatcher("default.jsp").forward(req, resp);
        break;
      case "listeetablissements":
        req.setAttribute("listeetablissements", facade.listeEtablissements());
        req.getRequestDispatcher("listeretablissements.jsp").forward(req, resp);
        break;
      case "listeutilisateurs":
        req.setAttribute("listeutilisateurs", facade.listeUsers());
        req.getRequestDispatcher("listerutilisateurs.jsp").forward(req, resp);
        break;
      case "listeevenements":
        req.setAttribute("listeevenements", facade.listeEvenements());
        req.getRequestDispatcher("listeevenement.jsp").forward(req, resp);
        break;
      case "listegroupes":
        req.setAttribute("listegroupes", facade.listeGroupes());
        req.getRequestDispatcher("listegroupes.jsp").forward(req, resp);
        break;
      case "creerEvenement":
        req.getRequestDispatcher("creer_event.html").forward(req, resp);
        break;
      case "creerGroupe":
        req.getRequestDispatcher("creer_groupe.html").forward(req, resp);
        break;
      case "listeUserGroupe":
        req.getRequestDispatcher("mesGroupes.jsp").forward(req, resp);
        break;
      case "listeUserEvent":
        req.getRequestDispatcher("mesEvents.jsp").forward(req, resp);
        break;
      case "listersalles":
        req.getRequestDispatcher("listersalles.html").forward(req, resp);
        break;
      case "creersalle":
        req.getRequestDispatcher("creersalle.html").forward(req, resp);
        break;

      default:
        req.getRequestDispatcher("index.html").forward(req, resp);
        break;
    }

    // req.forward(req, resp);
  }
}
