package gs.bor.exemplos.forum.persistencia;

import java.util.ArrayList;
import java.util.List;

import gs.bor.exemplos.forum.modelo.Usuario;

public class UsuarioDAO {
  
  //versão "congelada" globalmente, já que usamos na memória
  public static final UsuarioDAO padrao = new UsuarioDAO();
  // banquinho na memória mesmo, pra testar
  private final List<Usuario> usuarios;
  
  // inicializar a lista em memória, e inserir dois usuários "padrão".
  // não se deixe enganar pelo nome, eu não inseri nenhuma funcionalidade de
  // administração, e provavelmente nem vou.
  public UsuarioDAO() {
    this.usuarios = new ArrayList<Usuario>();
    this.cadastrar("admin@example.com", "admin", "admin");
    this.cadastrar("usuario@example.com", "usuario", "usuario");
  }
  
  // retorna a lista
  public List<Usuario> todosUsuarios() {
    return this.usuarios;
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
  
  // insere na lista de vez, retorna null se falhou
  public Usuario cadastrar(String email, String apelido, String senha) {
    if (this.podeCadastrar(email, apelido)) {
      Usuario u = new Usuario(email, apelido, senha);
      this.usuarios.add(u);
      return u;
    } else {
      return null;
    }
  }
  
  // testa a corretude de um par (email, senha)
  public Usuario tentaLogin(String email, String senha) {
    Usuario tgt = porEmail(email);
    System.out.println(tgt);
    if (tgt == null || !tgt.senhaBate(senha)) return null;
    return tgt;
  }
  
}
