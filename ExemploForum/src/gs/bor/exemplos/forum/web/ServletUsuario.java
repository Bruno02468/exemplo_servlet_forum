package gs.bor.exemplos.forum.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gs.bor.exemplos.forum.persistencia.TokenSeguroDAO;
import gs.bor.exemplos.forum.persistencia.UsuarioDAO;

// faz todas as operações com usuários, como sei lá, cadastro

// ações GET:
//   - /usuario/listar
//   - /usuario/detalhes?apelido=APELIDO
//   - /usuario/cadastrar

// ações POST:
//   - /usuario/cadastro
//   - /usuario/mudar_apelido

@WebServlet("/usuario/*")
public class ServletUsuario extends HttpServlet {

  private static final long serialVersionUID = -293378038323135052L;
  private UsuarioDAO usuarioDAO;

  public void init() {
    this.usuarioDAO = UsuarioDAO.padrao;
  }

  // ações get
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    String action = (String) req.getAttribute("fullPath");
    switch (action) {
    case "/usuario/listar":
      //listarUsuarios(req, resp);
      break;
    case "/usuario/detalhes":
      //detalharUsuario(req, resp);
      break;
    case "/usuario/cadastrar":
      //mostraCadastro(req, resp);
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
    case "/usuario/cadastro":
      //tentaCadastro(req, resp);
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

}
