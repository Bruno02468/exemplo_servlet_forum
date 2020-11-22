package gs.bor.exemplos.forum.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gs.bor.exemplos.forum.modelo.TokenSeguro;
import gs.bor.exemplos.forum.modelo.Usuario;
import gs.bor.exemplos.forum.persistencia.TokenSeguroDAO;
import gs.bor.exemplos.forum.persistencia.UsuarioDAO;

// esse servlet é usado pra login.

// ações GET:
//   - /login?next=URL
//   - /login/out?next=URL

// ações POST:
//   - /login/go

@WebServlet("/login/*")
public class ServletLogin extends HttpServlet {

  private static final long serialVersionUID = -6105917866193696486L;
  private TokenSeguroDAO tokenSeguroDAO;
  private UsuarioDAO usuarioDAO;
  
  public void init() {
    this.tokenSeguroDAO = TokenSeguroDAO.padrao;
    this.usuarioDAO = UsuarioDAO.padrao;
  }

  // ações get
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    String action = (String) req.getAttribute("fullPath");
    switch (action) {
      case "/login":
        resp.sendRedirect("../login/");
        break;
      case "/login/":
        mostraLogin(req, resp);
        break;
      case "/login/out":
        logOut(req, resp);
        break;
      default:
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        break;
    }
  }

  // ações post
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    String action = (String) req.getAttribute("fullPath");
    switch (action) {
      case "/login/go":
        tentaLogin(req, resp);
        break;
      default:
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        break;
    }
  }
  
  // mostra a tela de login
  public void mostraLogin(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    PaginaJSP.LOGIN.encaminhar(req, resp);
  }
  
  // arranca o segredo e volta.
  public void logOut(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    tokenSeguroDAO.revogar(WebUtils.extrairSegredoCodificado(req));
    String next = req.getParameter("next");
    if (next == null) next = "../"; // assumir homepage
    resp.sendRedirect(next);
  }
  
  
  // recebimento do formulário de login
  public void tentaLogin(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    String email = req.getParameter("email");
    String senha = req.getParameter("senha");
    String next = req.getParameter("next");
    if (next == null) next = "../"; // assumir homepage
    Usuario u = usuarioDAO.tentaLogin(email, senha);
    if (u != null) {
      // credenciais ok, redirecionar pra "próxima" página
      TokenSeguro t = tokenSeguroDAO.criar(u);
      t.injetar(resp);
      resp.sendRedirect(next);
    } else {
      // credenciais não ok, continuar aqui
      WebUtils.pedirLogin(resp, next, true);
    }
  }
  
}
