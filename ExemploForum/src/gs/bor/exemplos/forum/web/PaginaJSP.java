package gs.bor.exemplos.forum.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public enum PaginaJSP {
  HOMEPAGE("index.jsp"), CADASTRO("cadastro.jsp"), LOGIN("login.jsp"),
  USUARIO("usuario.jsp"), FIO_FORM("formulario_fio.jsp"), FIO("fio.jsp"),
  COMENTARIO_FORM("comentario.jsp"), USUARIOS("usuarios.jsp");
  
  public final String jspFile, filePath;
  public static final String parent = "/jsp/";
  
  PaginaJSP(String jspFile) {
    this.jspFile = jspFile;
    this.filePath = parent + this.jspFile;
  };
  
  @Override
  public String toString() {
    return this.filePath;
  }
  
  // encaminhar uma requisição para este recurso
  public void encaminhar(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    RequestDispatcher d = req.getRequestDispatcher(this.filePath);
    d.forward(req, resp);
  }
}
