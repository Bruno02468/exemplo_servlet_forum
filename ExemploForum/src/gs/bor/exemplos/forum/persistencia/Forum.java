package gs.bor.exemplos.forum.persistencia;

import java.util.ArrayList;
import java.util.List;

import gs.bor.exemplos.forum.modelo.Comentario;
import gs.bor.exemplos.forum.modelo.Fio;
import gs.bor.exemplos.forum.modelo.Post;
import gs.bor.exemplos.forum.modelo.Usuario;

// esse treco não é fechadinho num objeto, já que fios e comentários são quase
// indissociáveis, então eu fiz um negócio meio conjunto. não é um DAO no
// sentido mais restrito da palavra.

public class Forum {
  
  // padrão, pra residir em memória
  public static Forum padrao = new Forum();
  private List<Fio> fios;
  private List<Comentario> comentarios;
  
  public Forum() {
    this.fios = new ArrayList<Fio>();
    this.comentarios = new ArrayList<Comentario>();
  }
  
  // caso geral, usada abaixo
  private Post postPorId(int id, List<? extends Post> lista) {
    for (Post p : lista) {
      if (p.getId() == id) return p;
    }
    return null;
  }
  
  // caso especial
  public Fio fioPorId(int id) {
    return (Fio) this.postPorId(id, this.fios);
  }
  
  // outro caso especial
  public Comentario comentarioPorId(int id) {
    return (Comentario) this.postPorId(id, this.comentarios);
  }
  
  // comentários de um fio
  public List<Comentario> comentariosDe(Fio f) {
    List<Comentario> filhos = new ArrayList<Comentario>();
    for (Comentario c : this.comentarios) {
      if (c.getPai().equals(f)) filhos.add(c);
    }
    return filhos;
  }
  
  // cria postagem e retorna ID
  public Fio postar(Usuario autor, String titulo, String conteudo) {
    int novo_id = fios.size() + 1;
    Fio f = new Fio(novo_id, autor, titulo, conteudo);
    fios.add(f);
    return f;
  }
  
  // cria comentário e retorna ID
  public Comentario comentar(Usuario autor, Fio pai, String conteudo) {
    int novo_id = comentarios.size() + 1;
    Comentario c = new Comentario(novo_id, autor, pai, conteudo);
    comentarios.add(c);
    return c;
  }
  
  // deleta fio
  public void deletarFio(Fio f) {
    this.fios.remove(f);
  }
  
  // deleta comentário
  public void deletarComentario(Comentario c) {
    this.comentarios.remove(c);
  }
  
}
