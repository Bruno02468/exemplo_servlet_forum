package gs.bor.exemplos.forum.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gs.bor.exemplos.forum.modelo.Usuario;
import gs.bor.exemplos.forum.persistencia.Forum;
import gs.bor.exemplos.forum.persistencia.TokenSeguroDAO;
import gs.bor.exemplos.forum.persistencia.UsuarioDAO;

// TODA requisição, sem exceção, passa primeiro por este cara. isso permite que
// a gente decida o que fazer com cada tipo de requisição, além de ser
// necessário para evitar loops de redirecionamento e outras bostas que podem
// rolar quando você tem mais do que um servlet.

// além disso, aqui a gente já pode setar um monte de variáveis que queremos
// que todos os servlets e JSPs enxerguem, sem ter que ficar repetindo lógica.

// por exemplo, todo JSP sabe automaticamente se estamos logados, porque setamos
// o atributo "usuario" aqui! :)

@WebFilter("/*")
public class ServletFiltro extends HttpServlet implements Filter {

  private static final long serialVersionUID = -3928700034451074508L;
  private TokenSeguroDAO tokenSeguroDAO;
  private UsuarioDAO usuarioDAO;
  private Forum forum;
  
  
  public ServletFiltro() {
    super();
    this.tokenSeguroDAO = TokenSeguroDAO.padrao;
    this.forum = Forum.padrao;
    this.usuarioDAO = UsuarioDAO.padrao;
  }
  
  public void init() {
  }
  
  // filtrar requisições
  @Override
  public void doFilter(
    ServletRequest sreq, ServletResponse sresp, FilterChain chain
  ) throws IOException, ServletException {
    // cast para requisições HTTP
    HttpServletRequest req = (HttpServletRequest) sreq;
    HttpServletResponse resp = (HttpServletResponse) sresp;
    // eu nem sei pq utf8 não é o padrão
    req.setCharacterEncoding("UTF-8");
    
    // ver se tem cookie de login e já informar a todo mundo na cadeia de
    // encaminhamento se tem ou não usuário logado! isso não é muito foda?
    // eu achei isso muito foda.
    String segredinho = WebUtils.extrairSegredoCodificado(req);
    Usuario logadoComo = tokenSeguroDAO.logado(segredinho);
    req.setAttribute("usuario", logadoComo);
    if (logadoComo != null) logadoComo.acessou();
    
    // vamos expor os objetos de persistência aos JSPs também!
    req.setAttribute("forum", this.forum);
    req.setAttribute("tokens", this.tokenSeguroDAO);
    req.setAttribute("usuarios", this.usuarioDAO);
    
    
    // além disso, é bom que todo JSP saiba a sua "URL relativa" pra ficar mais
    // fácil de passar um parâmetro "next" pra páginas de login/logout etc
    String path = req.getRequestURI().substring(req.getContextPath().length());
    String easyNext = path.substring(1);
    String qs = req.getQueryString();
    if (qs != null && qs.length() > 0) easyNext += "?" + qs;
    req.setAttribute("easyNext", easyNext);
    req.setAttribute("fullPath", path);
    
    // enfim, agora vamos fazer a filtragem como sempre
    System.out.println("requisição a \"" + path + "\"; usuário: " + logadoComo);
    if (path.equals("")) {
      resp.sendRedirect(req.getContextPath() + "/");
    } else if (path.equals("/")) {
      // caso especial -- página inicial!
      PaginaJSP.HOMEPAGE.encaminhar(req, resp);
    } else if (path.startsWith(PaginaJSP.parent)) {
      // servir JSP...
      chain.doFilter(req, resp);
    } else {
      // passar adiante (e.g. servlet controlador, ou arquivo estático)
      req.getRequestDispatcher(path).forward(req, resp); 
    }
  }

}
