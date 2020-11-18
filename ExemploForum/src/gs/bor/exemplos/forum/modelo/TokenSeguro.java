package gs.bor.exemplos.forum.modelo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;

import gs.bor.exemplos.forum.util.Seguranca;
import gs.bor.exemplos.forum.web.WebUtils;

public class TokenSeguro {
  private Usuario dono;
  private boolean revogado;
  private byte[] segredo;
  
  public TokenSeguro(Usuario dono) {
    this.dono = dono;
    this.revogado = false;
    this.segredo = Seguranca.nextSalt();
  }
  
  public void revogar() {
    this.revogado = true;
  }
  
  // colocar esse cookie numa resposta http
  public void injetar(HttpServletResponse resp) {
    resp.addCookie(new Cookie(WebUtils.TOKEN_COOKIE_NAME, this.toString()));
  }
  
  @Override
  public String toString() {
    return Base64.encodeBase64String(segredo);
  }
  
  // abaixo gerado automaticamente

  public Usuario getDono() {
    return dono;
  }

  public boolean isRevogado() {
    return revogado;
  }

  public byte[] getSegredo() {
    return segredo;
  }
  
  
}
