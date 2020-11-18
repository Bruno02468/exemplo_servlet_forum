package gs.bor.exemplos.forum.modelo;

import java.time.LocalDateTime;

// classe-pai para tudo que é postável (fios, comentários)
// evita repetição de código entre Fio e Comentário, mas nunca é usada
// explicitamente

public class Post {
  protected int id;
  protected Usuario autor;
  protected String conteudo;
  protected LocalDateTime postado, editado;
  
  // construir sem data = agora
  public Post(int id, Usuario autor, String conteudo) {
    this.id = id;
    this.autor = autor;
    this.conteudo = conteudo;
    this.postado = LocalDateTime.now();
    this.editado = null;
  }
  
  // atualizar data de edição
  public void editou() {
    this.editado = LocalDateTime.now();
  }
  
  //tudo isso aqui foi gerado quase automaticamente

  public Usuario getAutor() {
    return autor;
  }

  public String getConteudo() {
    return conteudo;
  }

  public void setConteudo(String conteudo) {
    this.conteudo = conteudo;
    this.editou();
  }

  public LocalDateTime getPostado() {
    return postado;
  }

  public void setPostado(LocalDateTime postado) {
    this.postado = postado;
  }

  public LocalDateTime getEditado() {
    return editado;
  }

  public void setEditado(LocalDateTime editado) {
    this.editado = editado;
  }

  public int getId() {
    return id;
  }
}
