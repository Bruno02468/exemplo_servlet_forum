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
import gs.bor.exemplos.forum.persistencia.TokenSeguroDAO;

// além de dividir as requisições, ele já checa logo de cara se a requisição
// tem um cookie de login, pra gente não ter que checar em todo servler, e todo
// jsp pode ter acesso a estar ou não logado!

@WebFilter("/*")
public class ServletFiltro extends HttpServlet implements Filter {

  private static final long serialVersionUID = -3928700034451074508L;
  private TokenSeguroDAO tokenSeguroDAO;
  
  public ServletFiltro() {
    super();
  }
  
  public void init() {
    this.tokenSeguroDAO = TokenSeguroDAO.padrao;
  }
  
  // filtrar requisições
  @Override
  public void doFilter(
    ServletRequest sreq, ServletResponse sresp, FilterChain chain
  ) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) sreq;
    HttpServletResponse resp = (HttpServletResponse) sresp;
    req.setCharacterEncoding("UTF-8"); // eu nem sei pq utf8 não é o padrão
    // ver se tem cookie de login e já informar a todo mundo na cadeia de
    // encaminhamento se tem ou não usuário logado! isso não é muito foda?
    // eu achei isso muito foda.
    byte[] segredinho = WebUtils.extrairSegredo(req);
    Usuario logadoComo = tokenSeguroDAO.logado(segredinho);
    req.setAttribute("usuario", logadoComo);
    // enfim, agora vamos fazer a filtragem como sempre
    String path = req.getRequestURI().substring(req.getContextPath().length());
    if (path.equals("/")) {
      // caso especial -- página inicial!
      PaginaJSP.HOMEPAGE.encaminhar(req, resp);
    } else if (path.startsWith(PaginaJSP.parent)) {
      // servir JSP...
      chain.doFilter(req, resp);
    } else {
      // passar adiante (e.g. servlet controlador)
      req.setAttribute("fullPath", path);
      req.getRequestDispatcher(path).forward(req, resp); 
    }
  }

}
