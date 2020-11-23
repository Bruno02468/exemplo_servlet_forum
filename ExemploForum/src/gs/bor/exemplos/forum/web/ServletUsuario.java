package gs.bor.exemplos.forum.web;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gs.bor.exemplos.forum.modelo.TokenSeguro;
import gs.bor.exemplos.forum.modelo.Usuario;
import gs.bor.exemplos.forum.persistencia.TokenSeguroDAO;
import gs.bor.exemplos.forum.persistencia.UsuarioDAO;

// faz todas as operações com usuários, como sei lá, cadastro

// ações GET:
//   - /usuario/listar
//   - /usuario/perfil?apelido=APELIDO
//   - /usuario/cadastrar

// ações POST:
//   - /usuario/cadastro
//   - /usuario/mudar_apelido

@WebServlet("/usuario/*")
public class ServletUsuario extends HttpServlet {

  private static final long serialVersionUID = -293378038323135052L;
  private UsuarioDAO usuarioDAO;
  private TokenSeguroDAO tokenSeguroDAO;

  public void init() {
    this.usuarioDAO = UsuarioDAO.padrao;
    this.tokenSeguroDAO = TokenSeguroDAO.padrao;
  }

  // ações get
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    String action = (String) req.getAttribute("fullPath");
    switch (action) {
      case "/usuario/listar":
        listarUsuarios(req, resp);
        break;
      case "/usuario/perfil":
        mostrarPerfil(req, resp);
        break;
      case "/usuario/cadastrar":
        mostrarCadastro(req, resp);
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
      case "/usuario/cadastro":
        tentaCadastro(req, resp);
        break;
      case "/usuario/mudaApelido":
        mudaApelido(req, resp);
        break;
      case "/usuario/mudaSenha":
        mudaSenha(req, resp);
        break;
      default:
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        break;
    }
  }

  // simplesmente mostra a tela de usuários
  public void listarUsuarios(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    PaginaJSP.USUARIOS.encaminhar(req, resp);
  }
  
  // mostra o perfil de um usuário
  public void mostrarPerfil(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    Usuario u = usuarioDAO.porApelido(req.getParameter("apelido"));
    req.setAttribute("perfilado", u);
    PaginaJSP.USUARIO.encaminhar(req, resp);
  }
  
  // mostra a página de cadastro
  public void mostrarCadastro(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    PaginaJSP.CADASTRO.encaminhar(req, resp);
  }
  
  // usuário tentou se cadastrar (formulário)
  public void tentaCadastro(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    String email = req.getParameter("email");
    String apelido = req.getParameter("apelido");
    String senha = req.getParameter("senha");
    Usuario novo = usuarioDAO.cadastrar(email, apelido, senha);
    if (novo != null) {
      // cadastro ok! gerar um token, adicionar, e redirecionar pra homepage
      TokenSeguro t = tokenSeguroDAO.criar(novo);
      t.injetar(resp);
      resp.sendRedirect("..");
    } else {
      // cadastro falhou! setar mensagem de erro e prosseguir continuar aqui
      req.setAttribute("erro", "Email e/ou apelido já estão em uso!");
      PaginaJSP.CADASTRO.encaminhar(req, resp);
    }
  }
  
  //usuário quer mudar a própria senha(formulário)
  public void mudaSenha(HttpServletRequest req, HttpServletResponse resp)
     throws IOException, ServletException {
    Usuario atual = (Usuario) req.getAttribute("usuario");
    // usuário inválido? vai pra homepage.
    if (atual == null) resp.sendRedirect(req.getContextPath());
    String senha = req.getParameter("senha");
    atual.setSenha(senha);
    resp.sendRedirect("./perfil?apelido="
      + URLEncoder.encode(atual.getApelido(), "UTF-8"));
  }
  
  //usuário quer mudar a própria senha(formulário)
  public void mudaApelido(HttpServletRequest req, HttpServletResponse resp)
     throws IOException, ServletException {
    Usuario atual = (Usuario) req.getAttribute("usuario");
    // usuário inválido? vai pra homepage.
    if (atual == null) resp.sendRedirect(req.getContextPath());
    String apelido = req.getParameter("apelido");
    atual.setApelido(apelido);
    resp.sendRedirect("./perfil?apelido="
      + URLEncoder.encode(atual.getApelido(), "UTF-8"));
  }

}
