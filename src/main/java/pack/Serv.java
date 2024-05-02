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

@WebServlet("/Serv")
public class Serv extends HttpServlet {

  @EJB
  Facade facade;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    RequestDispatcher rd = req.getRequestDispatcher("login.html");
    // String op = req.getParameter("op");
    rd.forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    switch (req.getParameter("op")) {
      case "chargerutilisateur":
        String mail = req.getParameter("mail");
        String password = req.getParameter("password");
        if (facade.verifierUtilisateur(mail, password)) {
          req.setAttribute("userSession", facade.getUserSession());
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
    }

    // req.forward(req, resp);
  }
}
