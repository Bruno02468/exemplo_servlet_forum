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
  public Usuario logado(byte[] teste) {
    for (TokenSeguro token : this.tokens) {
      if (token.getSegredo().equals(teste) && !token.isRevogado()) {
        return token.getDono();
      }
    }
    return null;
  }
  
  // revoga tokens correspondentes
  public void revogar(byte[] segredo) {
    for (TokenSeguro token : this.tokens) {
      if (token.getSegredo().equals(segredo)) {
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
