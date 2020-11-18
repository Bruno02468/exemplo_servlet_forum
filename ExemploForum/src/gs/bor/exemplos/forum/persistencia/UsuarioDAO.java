package gs.bor.exemplos.forum.persistencia;

import java.util.ArrayList;
import java.util.List;

import gs.bor.exemplos.forum.modelo.Usuario;

public class UsuarioDAO {
  
  //versão "congelada" globalmente, já que usamos na memória
  public static final UsuarioDAO padrao = new UsuarioDAO();
  // banquinho na memória mesmo, pra testar
  private final List<Usuario> usuarios;
  
  public UsuarioDAO() {
    this.usuarios = new ArrayList<Usuario>();
  }
  
  // pesquisar o usuário com um certo email
  public Usuario porEmail(String email) {
    for (Usuario usuario : this.usuarios) {
      if (usuario.getEmail().equals(email)) return usuario;
    }
    return null;
  }
  
  // pesquisar o usuário com um certo apelido
  public Usuario porApelido(String apelido) {
    for (Usuario usuario : this.usuarios) {
      if (usuario.getApelido().equals(apelido)) return usuario;
    }
    return null;
  }
  
  // apelido e nome de usuário devem estar ambos livres
  public boolean podeCadastrar(String email, String apelido) {
    return this.porEmail(email) == null && this.porApelido(apelido) == null;
  }
  
  // testa a validez de um par (email, senha)
  public Usuario tentaLogin(String email, String senha) {
    Usuario tgt = porEmail(email);
    if (tgt == null || !tgt.senhaBate(senha)) return null;
    return tgt;
  }
  
  
}
