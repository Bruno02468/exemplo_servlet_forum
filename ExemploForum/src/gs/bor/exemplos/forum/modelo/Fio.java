package gs.bor.exemplos.forum.modelo;

// basicamente, um editável com título!

public class Fio extends Post {
  private String titulo;
  
  // construir sem data = agora
  public Fio(int id, Usuario autor, String titulo, String conteudo) {
    super(id, autor, conteudo);
    this.titulo = titulo;
  }
  
  //tudo isso aqui foi gerado quase automaticamente

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
    this.editou();
  }
  
  
}
