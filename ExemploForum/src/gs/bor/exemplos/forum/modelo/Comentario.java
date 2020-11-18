package gs.bor.exemplos.forum.modelo;

// basicamente, um Post com pai

public class Comentario extends Post {
  private Fio pai;
  
  // construir sem data = agora
  public Comentario(int id, Usuario autor, Fio pai, String conteudo) {
    super(id, autor, conteudo);
    this.pai = pai;
  }
  
  //tudo isso aqui foi gerado automaticamente
  
  public Fio getPai() {
    return pai;
  }
  
}
