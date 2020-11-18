package gs.bor.exemplos.forum.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gs.bor.exemplos.forum.modelo.Fio;
import gs.bor.exemplos.forum.modelo.Usuario;
import gs.bor.exemplos.forum.persistencia.Forum;

// esse servlet que cuida de requisições que tangem a fios (e.g. CRUDar)

// ações com GET:
//   - /fio?id=ID
//   - /fio/criar
//   - /fio/editar?id=ID
//   - /fio/deleta?id=ID

// ações com POST:
//   - /fio/cria
//   - /fio/edita

@WebServlet("/fio/*")
public class ServletFio extends HttpServlet {

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
      case "/fio":
        mostraFio(req, resp);
        break;
      case "/fio/criar":
        mostraCriarFio(req, resp);
        break;
      case "/fio/editar":
        mostraEditarFio(req, resp);
        break;
      case "/fio/deleta":
        deletaFio(req, resp);
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
      case "/fio/cria":
        criaFio(req, resp);
        break;
      case "/fio/edita":
        editaFio(req, resp);
        break;
      default:
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        break;
    }
  }

  // mostrar um fio
  public void mostraFio(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    Fio f = forum.fioPorId(Integer.parseInt(req.getParameter("id")));
    req.setAttribute("fio", f);
    PaginaJSP.FIO.encaminhar(req, resp);
  }

  // redireciona o usuário pro formulario de criar fio
  public void mostraCriarFio(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    if (req.getAttribute("usuario") == null) {
      // usuário não logado, vamos exigir login primeiro
      WebUtils.pedirLogin(resp, "fio/criar");
    } else {
      // usuário logado, mostrar jsp
      PaginaJSP.FIO_FORM.encaminhar(req, resp);
    }
  }

  // redireciona o usuário pro formulario de criar fio
  public void mostraEditarFio(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    Usuario u = (Usuario) req.getAttribute("usuario");
    if (u == null) {
      // usuário não logado, vamos exigir login primeiro
      WebUtils.pedirLogin(resp, "fio/editar?id=" + req.getParameter("id"));
    } else {
      // usuário logado, vamos ver melhor...
      int id_fio = Integer.parseInt(req.getParameter("id"));
      Fio f = forum.fioPorId(id_fio);
      if (f.getAutor().equals(u)) {
        // é o autor, tudo ok!
        req.setAttribute("fio", f);
        PaginaJSP.FIO_FORM.encaminhar(req, resp);
      } else {
        // não é o autor!
        resp.sendRedirect("?id=" + id_fio);
      }
    }
  }

  // deleta o fio e redireciona pra homepage, ou exige login e volta pro fio
  public void deletaFio(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    Usuario u = (Usuario) req.getAttribute("usuario");
    int id_fio = Integer.parseInt(req.getParameter("id"));
    if (u == null) {
      // usuário não logado, vamos exigir login primeiro
      WebUtils.pedirLogin(resp, "fio/deleta?id=" + id_fio);
    } else {
      // usuário logado, vamos ver melhor...
      Fio f = forum.fioPorId(id_fio);
      if (f.getAutor().equals(u)) {
        // é o autor, tudo ok!
        forum.deletarFio(forum.fioPorId(id_fio));
        resp.sendRedirect("..");
      } else {
        // não é o autor!
        resp.sendRedirect("?id=" + id_fio);
      }
    }
  }

  // cria o fio e redireciona pra lá, ou exige login e volta pro form
  public void criaFio(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    Usuario u = (Usuario) req.getAttribute("usuario");
    if (u == null) {
      // usuário não logado, vamos exigir login primeiro
      WebUtils.pedirLogin(resp, "fio/criar");
    } else {
      // usuário logado, criamos o fio e redirecionamos pra lá!
      String titulo = req.getParameter("titulo");
      String conteudo = req.getParameter("conteudo");
      Fio f = forum.postar(u, titulo, conteudo);
      resp.sendRedirect("?id=" + f.getId());
    }
  }

  // deleta o fio e redireciona pra homepage, ou exige login e volta pro fio
  public void editaFio(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    Usuario u = (Usuario) req.getAttribute("usuario");
    int id_fio = Integer.parseInt(req.getParameter("id"));
    if (u == null) {
      // usuário não logado, vamos exigir login primeiro
      WebUtils.pedirLogin(resp, "fio/editar?id=" + id_fio);
    } else {
      // usuário logado, vamos ver melhor...
      Fio f = forum.fioPorId(id_fio);
      if (f.getAutor().equals(u)) {
        // é o autor, tudo ok!
        f.setTitulo(req.getParameter("titulo"));
        f.setConteudo(req.getParameter("conteudo"));
      }
    }
    resp.sendRedirect("?id=" + id_fio);
  }

}
