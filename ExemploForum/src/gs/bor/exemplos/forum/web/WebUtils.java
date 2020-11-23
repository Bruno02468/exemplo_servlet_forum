package gs.bor.exemplos.forum.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// funções gerais usadas por todos os servlets

public class WebUtils {

  public static final String TOKEN_COOKIE_NAME = "segredinho_forum";

  public static final DateTimeFormatter formatoData
    = DateTimeFormatter.ofPattern("dd/MM/yyyy");
  public static final DateTimeFormatter formatoHora
    = DateTimeFormatter.ofPattern("HH:mm:ss");
  
  //funções pra retornar versões bem-formatadas das datas relevantes
    
   public static String formatarLDT(LocalDateTime data) {
     return formatoData.format(data) + " às "
       + formatoHora.format(data);
   }
  
  // puxa o segredo (token) de uma requisição
  public static byte[] extrairSegredo(HttpServletRequest req) {
    String val = extrairSegredoCodificado(req);
    if (val == null) return null;
    return Base64.getDecoder().decode(val);
  }
  
  // retorna o segredo codificado de uma requisição
  public static String extrairSegredoCodificado(HttpServletRequest req) {
    if (req.getCookies() == null) return null;
    for (Cookie c : req.getCookies()) {
      if (c.getName().equals(TOKEN_COOKIE_NAME)) {
        return c.getValue();
      }
    }
    return null;
  }
  
  // arranca o segredo (token) do cliente
  public static void removerSegredo(HttpServletResponse resp) {
    Cookie c = new Cookie(TOKEN_COOKIE_NAME, "");
    c.setMaxAge(0);
    resp.addCookie(c);
  }
  
  // redireciona alguém pro login
  public static void pedirLogin(
      HttpServletResponse resp, String next, boolean failed
  ) throws IOException {
    try {
      String s = URLEncoder.encode(next, StandardCharsets.UTF_8.toString());
      String k = "";
      if (failed) k = "&failed=true";
      resp.sendRedirect("../login/?next=../" + s + k);
    } catch (UnsupportedEncodingException e) {
      // se UTF-8 não for suportado, temos problemas maiores.
      e.printStackTrace();
    }
  }
  
  // redireciona alguém pro login, sem falha
  public static void pedirLogin(HttpServletResponse resp, String next)
      throws IOException {
    pedirLogin(resp, next, false);
  }
  
  
}
