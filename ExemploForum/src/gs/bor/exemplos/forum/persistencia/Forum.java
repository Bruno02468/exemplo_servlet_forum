package gs.bor.exemplos.forum.persistencia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gs.bor.exemplos.forum.modelo.Comentario;
import gs.bor.exemplos.forum.modelo.Fio;
import gs.bor.exemplos.forum.modelo.Post;
import gs.bor.exemplos.forum.modelo.Usuario;

// esse treco não é fechadinho num objeto, já que fios e comentários são quase
// indissociáveis, então eu fiz um negócio meio conjunto. não é um DAO no
// sentido mais restrito da palavra, então não chamei de DAO. mas ele se
// comporta basicamente como um!

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
    pai.ativou();
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
  
  // retorna uma lista com todos os posts (fios e comentários) mais recentes
  public List<Post> postsMaisRecentes() {
    // como tanto fios quanto comentários são Post, que implementa Comparable,
    // podemos simplesmente juntar tudo no mesmo saco e ordenar :)
    List<Post> todos = new ArrayList<Post>(this.fios);
    todos.addAll(this.comentarios);
    Collections.sort(todos);
    // como usamos o compareTo das datas dos posts, os primeiros na lista serão
    // os mais antigos, então invertemos.
    Collections.reverse(todos);
    return todos;
  }
  
  // como a lista está em memória, iteramos no código. idealmente, ela estaria
  // num MySQL ou algo assim, e seria muito, MUITO mais eficiente ordenar já na
  // query ao banco.
  public List<Fio> fiosMaisRecentes(int quantos) {
    // a ideia é simples: pegar todos os fios e todos os comentários, e do mais
    // recente, derivar os N fios com atividade mais recente.
    List<Post> todos = this.postsMaisRecentes();
    // finalmente, vamos iterar pela lista. quando encontrarmos um fio, vamos
    // colocá-lo na lista. quando encontrarmos um comentário, vamos colocar seu
    // pai na lista DESDE QUE ele já não esteja. paramos quando a lista de Posts
    // acabar OU quando ela tiver "quantos" itens.
    List<Fio> recentes = new ArrayList<Fio>(quantos);
    for (Post p : todos) {
      Fio f;
      if (p instanceof Fio) {
        f = (Fio) p;
      } else {
        f = ((Comentario) p).getPai();
      }
      if (!recentes.contains(f)) recentes.add(f);
      if (recentes.size() == quantos) break;
    }
    return recentes;
  }
  
  // retorna a lista de postagens mais recentes de um usuário, quantos=0=>todos
  public List<Post> postsMaisRecentes(Usuario autor, int quantos) {
    // partir novamente dos posts mais recentes...
    List<Post> todos = new ArrayList<Post>(this.postsMaisRecentes());
    // filtrar pelo autor...
    for (Post p : todos) if (p.getAutor() != autor) todos.remove(p);
    // e limitar o tamanho
    if (quantos > 0) {
      while (todos.size() > quantos) todos.remove(todos.size()-1);
    }
    return todos;
  }
  
  // meio na cara
  public List<Fio> fiosPorAutor(Usuario autor, int quantos) {
    // partir novamente dos posts mais recentes...
    List<Fio> todos = new ArrayList<Fio>(this.fios);
    // filtrar pelo autor...
    for (Post p : todos) if (p.getAutor() != autor) todos.remove(p);
    // e limitar o tamanho
    if (quantos > 0) {
      while (todos.size() > quantos) todos.remove(todos.size()-1);
    }
    return todos;
  }
  
  // padrão zero
  public List<Fio> fiosPorAutor(Usuario autor) {
    return this.fiosPorAutor(autor, 0);
  }

  // meio na cara
  public List<Comentario> comentariosPorAutor(Usuario autor, int quantos) {
    // partir novamente dos posts mais recentes...
    List<Comentario> todos = new ArrayList<Comentario>(this.comentarios);
    // filtrar pelo autor...
    for (Post p : todos) if (p.getAutor() != autor) todos.remove(p);
    // e limitar o tamanho
    if (quantos > 0) {
      while (todos.size() > quantos)
        todos.remove(todos.size() - 1);
    }
    return todos;
  }

  // padrão zero
  public List<Comentario> comentariosPorAutor(Usuario autor) {
    return this.comentariosPorAutor(autor, 0);
  }
  
  // retorna o comentário mais recente de um fio. se não tiver, retorna o fio.
  public Post ultimoRelacionado(Fio f) {
    List<Comentario> cd = this.comentariosDe(f);
    if (cd.size() > 0) return cd.get(cd.size()-1);
    else return f;
  }
  
  
}
