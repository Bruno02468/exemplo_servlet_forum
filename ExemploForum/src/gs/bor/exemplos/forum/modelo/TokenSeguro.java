package gs.bor.exemplos.forum.modelo;

import java.io.IOException;

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
  public void injetar(HttpServletResponse resp) throws IOException {
    Cookie tk = new Cookie(WebUtils.TOKEN_COOKIE_NAME, segredoCodificado());
    tk.setMaxAge(30*24*3600);
    // tk.setSecure(true);
    tk.setPath("/");
    resp.addCookie(tk);
  }
  
  // codificação legal do segredo
  public String segredoCodificado() {
    return Base64.encodeBase64String(segredo);
  }
  
  // representação boa
  @Override
  public String toString() {
    final String s = "<TokenSeguro para %s, revogado=%s>";
    return String.format(s, this.dono, this.revogado);
  }

  public boolean equals(TokenSeguro outro) {
    return outro.segredoCodificado().equals(this.segredoCodificado());
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
