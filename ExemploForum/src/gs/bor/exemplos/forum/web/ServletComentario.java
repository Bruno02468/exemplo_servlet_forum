package gs.bor.exemplos.forum.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gs.bor.exemplos.forum.modelo.Comentario;
import gs.bor.exemplos.forum.modelo.Fio;
import gs.bor.exemplos.forum.modelo.Usuario;
import gs.bor.exemplos.forum.persistencia.Forum;

// esse servlet que cuida de requisições que tangem a comentários

// ações com GET:
//   - /comentario/editar?id=ID
//   - /comentario/deleta?id=ID

// ações com POST:
//   - /comentario/cria
//   - /comentario/edita

@WebServlet("/comentario/*")
public class ServletComentario extends HttpServlet {

  private static final long serialVersionUID = -1505350444954077410L;
  private Forum forum;

  public void init() {
    this.forum = Forum.padrao;
  }

  // ações get
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    String action = (String) req.getAttribute("fullPath");
    switch (action) {
      case "/comentario/editar":
        mostraEditar(req, resp);
        break;
      case "/comentario/deleta":
        deletaComentario(req, resp);
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
      case "/comentario/cria":
        criaComentario(req, resp);
        break;
      case "/comentario/edita":
        editaComentario(req, resp);
        break;
      default:
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        break;
    }
  }
  
  // mostra a tela de editar comentario
  public void mostraEditar(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    int id_comentario = Integer.parseInt(req.getParameter("id"));
    Comentario c = forum.comentarioPorId(id_comentario);
    Usuario u = (Usuario) req.getAttribute("usuario");
    if (u == null) {
      // usuário não logado, vamos exigir login primeiro
      WebUtils.pedirLogin(resp, "comentario/editar?id=" + id_comentario);
    } else {
      // usuário logado, vamos ver melhor...
      int id_fio = c.getPai().getId();
      if (c.getAutor().equals(u)) {
        // é o autor, tudo ok!
        req.setAttribute("comentario", c);
        PaginaJSP.COMENTARIO_FORM.encaminhar(req, resp);
      } else {
        // não é o autor!
        resp.sendRedirect("?id=" + id_fio);
      }
    }
  }

  // deleta o comentario e redireciona volta pro fio
  public void deletaComentario(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    int id = Integer.parseInt(req.getParameter("id"));
    Comentario c = forum.comentarioPorId(id);
    Usuario u = (Usuario) req.getAttribute("usuario");
    if (u == null) {
      // usuário não logado, vamos exigir login primeiro
      WebUtils.pedirLogin(resp, "fio/?id=" + c.getPai().getId() + "#" + id);
    } else {
      // usuário logado, mas ele é o autor?
      if (c.getAutor().equals(u)) {
        // yep! deletar comentário e voltar pro fio.
        forum.deletarComentario(forum.comentarioPorId(c.getPai().getId()));
      }
      resp.sendRedirect("../fio/?id=" + c.getPai().getId());
    }
  }

  // cria o comentario e volta pro fio
  public void criaComentario(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    Usuario u = (Usuario) req.getAttribute("usuario");
    int id_fio = Integer.parseInt(req.getParameter("id_fio"));
    if (u == null) {
      // usuário não logado, vamos exigir login primeiro
      WebUtils.pedirLogin(resp, "fio/?id=" + id_fio);
    } else {
      // usuário logado, bora postar e redirecionar!
      Fio f = forum.fioPorId(id_fio);
      String conteudo = req.getParameter("conteudo");
      int id = forum.comentar(u, f, conteudo).getId();
      resp.sendRedirect("../fio/?id=" + id_fio + "#c-" + id);
      // esse # acima no redirect é um "fragmento". a ideia é que isso indica
      // pro navegador em qual parte da página ele deve "focar". por padrão,
      // isso scrolla a página até revelar um elemento com um certo ID.
      // você pode ver que, no fio.jsp, os comentários ficam em elementos com
      // IDs da forma "c-ID_COMENTARIO"!
      // fragmentos são super úteis pra passar informação pro navegador, ao
      // da mesma forma que a querystring (?var=valvar2=val2) é útil pra passar
      // informação ao SERVIDOR que responde a requisição.
    }
  }

  // edita o comentário e volta pro fio, ou só volta mesmo
  public void editaComentario(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    int id = Integer.parseInt(req.getParameter("id"));
    Comentario c = forum.comentarioPorId(id);
    Usuario u = (Usuario) req.getAttribute("usuario");
    int id_fio = c.getPai().getId();
    if (u == null) {
      // usuário não logado, vamos exigir login primeiro
      WebUtils.pedirLogin(resp, "next=comentario/editar?id=" + id);
    } else {
      // usuário logado, mas ele é o autor?
      if (c.getAutor().equals(u)) {
        // yep! editar comentário e voltar pro fio.
        c.setConteudo(req.getParameter("conteudo"));
      }
      // tá na disney, tentando editar o comentário de outro.
      resp.sendRedirect("../fio/?id=" + id_fio + "#c-" + id);
    }
  }
}
