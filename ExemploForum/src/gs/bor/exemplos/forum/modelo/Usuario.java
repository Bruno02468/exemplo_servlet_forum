package gs.bor.exemplos.forum.modelo;

import java.time.LocalDateTime;

import org.apache.tomcat.util.codec.binary.Base64;

import gs.bor.exemplos.forum.util.Seguranca;
import gs.bor.exemplos.forum.web.WebUtils;

public class Usuario {
  private String email, apelido;
  private LocalDateTime registradoQuando, ultimoAcesso;
  private byte[] hash, salt; // persistência de senha -- hasheada!
  
  public Usuario(String email, String apelido, String senha) {
    this.email = email;
    this.apelido = apelido;
    this.setSenha(senha);
    this.acessou();
    this.registradoQuando = this.ultimoAcesso;
  }
  
  // marcar último acesso
  public void acessou() {
    this.setUltimoAcesso(LocalDateTime.now());
  }
  
  // data de cadastro, formatada
  public String quandoCadastrou() {
    return WebUtils.formatarLDT(this.registradoQuando);
  }
  
  // último acesso, formatado
  public String quandoAcessou() {
    return WebUtils.formatarLDT(this.ultimoAcesso);
  }
  
  // setar senha, atualizando hash e salt
  public void setSenha(String senha) {
    System.out.println(this.toString() + " tem senha \"" + senha + "\"");
    this.setSalt(Seguranca.nextSalt());
    this.setHash(Seguranca.hashear(senha.toCharArray(), this.salt));
  }
  
  // essa é a minha senha?
  public boolean senhaBate(String senha) {
    String esta = Base64.encodeBase64String(this.hash);
    byte[] hb = Seguranca.hashear(senha.toCharArray(), this.salt);
    String aquela = Base64.encodeBase64String(hb);
    return esta.equals(aquela);
  }
  
  // forma apresentável do usuário
  @Override
  public String toString() {
    return this.apelido + " <" + this.email + ">";
  }
  
  // retorna true se dois usuários são a mesma pessoa (mesmo email).
  public boolean equals(Usuario outro) {
    return this.email.equals(outro.getEmail());
  }
  
  // tudo daqui pra baixo foi gerado 100% automaticamente
  
  public String getEmail() {
    return this.email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getApelido() {
    return apelido;
  }
  
  public void setApelido(String apelido) {
    this.apelido = apelido;
  }
  
  public LocalDateTime getRegistradoQuando() {
    return registradoQuando;
  }
  
  public void setRegistradoQuando(LocalDateTime registradoQuando) {
    this.registradoQuando = registradoQuando;
  }
  
  public LocalDateTime getUltimoAcesso() {
    return this.ultimoAcesso;
  }
  
  public void setUltimoAcesso(LocalDateTime ultimoAcesso) {
    this.ultimoAcesso = ultimoAcesso;
  }
  
  public byte[] getHash() {
    return this.hash;
  }
  
  public void setHash(byte[] hash) {
    this.hash = hash;
  }
  
  public byte[] getSalt() {
    return salt;
  }
  public void setSalt(byte[] salt) {
    this.salt = salt;
  }
  
  
}
