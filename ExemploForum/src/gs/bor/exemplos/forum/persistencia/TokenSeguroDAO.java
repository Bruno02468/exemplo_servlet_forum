package gs.bor.exemplos.forum.persistencia;

import java.util.ArrayList;
import java.util.List;

import gs.bor.exemplos.forum.modelo.TokenSeguro;
import gs.bor.exemplos.forum.modelo.Usuario;

public class TokenSeguroDAO {

  // versão "congelada" globalmente, já que usamos na memória
  public static final TokenSeguroDAO padrao = new TokenSeguroDAO();
  //banquinho na memória mesmo, pra testar
  private final List<TokenSeguro> tokens;
 
  public TokenSeguroDAO() {
    this.tokens = new ArrayList<TokenSeguro>();
  }
  
  // retorna a qual usuário corresponde um token
  public Usuario logado(String dele) {
    for (TokenSeguro token : this.tokens) {
      String meu = token.segredoCodificado();
      if (meu.equals(dele) && !token.isRevogado()) {
        return token.getDono();
      }
    }
    return null;
  }
  
  //revoga tokens correspondentes
  public void revogar(String segredinho) {
    for (TokenSeguro token : this.tokens) {
      System.out.println(token);
      if (token.segredoCodificado().equals(segredinho)) {
        token.revogar();
      }
    }
  }
  
  // cria um token!
  public TokenSeguro criar(Usuario u) {
    TokenSeguro t = new TokenSeguro(u);
    this.tokens.add(t);
    return t;
  }
  
}
