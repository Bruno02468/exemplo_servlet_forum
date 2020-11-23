<!-- aqui, informamos a engine JSP sobre alguns básicos -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<!--
  importamos a JSTL, uma biblioteca de tags padrão para uso com JSP, e definimos
  que as tags especiais serão usadas com o prefixo "c". além disso, também
  importamos as funções da JSTL e informamos que suas funções serão acessadas
  pelo prefixo "fn".
-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html> <!-- HTML5! -->
<html lang="pt-br">
  <head>
    <!--
      informar o navegador que esta página é UTF-8, pra cagar os acentos
    -->
    <meta charset="utf-8">
    <!--
      sabe quando você manda um link no whatsapp ou no facebook e aparece um
      título (diferente do da página) e uma descriçãozinha? eles pegam dessas
      tags abaixo. use-as, não custa nada!
    -->
    <meta name="description" content="Fórum Simples de Exemplo">
    <meta name="author" content="Bruno Borges Paschoalinoto">
    <!--
      essa é mais difícil de explicar, mas basicamente ela evita que o site
      fique pequenininho e zoado em celulares. use-a!
    -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Fórum Exemplo</title>
    <!--
      normalize.css é importante para manter a aparência consistente entre
      navegadores e plataformas! além disso, o skeleton o exige.
      
      como você também pode ver, eu importei esses .CSS não dos nossos arquivos
      estáticos, mas de uma CDN (content delivery network), pra facilitar
      caching por parte dos navegadores, e pra deixar o acesso mais rápidos ;)
    -->
    <link rel="stylesheet" type="text/css"
    href="//cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css">
    <!--
      skeleton.css é um boilerplate muito útil pra ter acesso a um grid
      responsivo básico... eu uso em todos os meus projetos onde aparência não
      é prioridade, e sim a funcionalidade!
      
      para aprender como usar bem rápido, veja o site deles: getskeleton.com
      é bem de boa, e o grid (veja o uso de classes row e columns) é 10/10
    -->
    <link rel="stylesheet" type="text/css"
    href="//cdnjs.cloudflare.com/ajax/libs/skeleton/2.0.4/skeleton.min.css">
    <!--
      por fim, importamos as nossas próprias diretivas de estilo.
      como é um site simples, vamos usar um arquivo só!
      
      note que eu usei o contextPath, que todos podem enxergar, pra criar um
      href absoluto com facilidade. e sim, isso pode ser feito com TODOS os
      links do sistema. apesar disso, abusar de links absolutos é considerado má
      prática, então eu só usei aqui.
      
      a longo prazo, ficar usando isso em todos os redirecionamentos, forwards
      e links do sistema só ia deixar tudo mais chato e engessado. além disso,
      aprender como funcionam links relativos é essencial.
    -->
    <link rel="stylesheet" type="text/css"
    href="${pageContext.request.contextPath}/static/simples.css">
  </head>
  <body>
    <!-- todos os elementos do skeleton devem ficar dentro de um .container -->
    <div class="container">
      <!-- aqui, criamos uma linha centralizada com uma só coluna -->
      <div class="row center">
        <div class="twelve columns">
          <h3>Fórum Exemplo</h3>
          <h5>Página Inicial</h5>
          <hr>
          <br>
        </div>
      </div>
      <!-- idem. -->
      <div class="row center">
        <div class="twelve columns">
          Esse fórum foi programado para servir como exemplo simples de um
          sistema funcional escrito usando JSP e Servlets.
          <br>
          <br>
          Código, .WAR e outras especificações podem ser encontradas no
          <a target="_blank" href="https://github.com/Bruno02468/ExemploForum">
            GitHub!
          </a>
          <!-- target="_blank" faz o link abrir em uma nova guia -->
          <br>
          <br>
          Alguns links úteis:
          <a href="./fio/listar">lista de fios</a>,
          <a href="./usuario/listar">lista de usuários</a>.
        </div>
      </div>
      <hr>
      <!--
        agora, queremos listar os posts. por sorte, o servlet de filtro nos
        deu acesso ao objeto do fórum. eba!
        
        vamos criar dois "blocos". um deles vai listar os 5 fios com atualização
        mais recente, e outro do lado vai ter botões de utilidade caso você
        esteja logado, ou um botão de login caso contrário.
      -->
      <div class="row center">
        <!-- primeiro bloco: fios mais recentes. -->
        <div class="six columns bordinha">
          <b>Fios com atualização mais recente:</b>
          <br>
          <br>
          <ul class="left">
            <!--
              loop no JSP. note a expressão EL contendo o parâmetro que o
              o servlet filtro inseriu, e o nome da variável de loop.
            -->
            <c:forEach var="fio" items="${forum.fiosMaisRecentes(5)}">
              <li>
                <!--
                  por quê usamos c:out aqui? porque esse texto é gerado por
                  usuários. se inseríssemos ele diretamente no código, um
                  usuário malicioso poderia inserir código HTML (como uma tag
                  <script>) nesta página via o título de um fio. esse tipo de
                  ataque chama XSS, e é muito efetivo em sites feitos por
                  iniciantes, porque é geralmente muito fácil de prevenir!
                -->
                <a href="./fio/?id=${fio.id}">
                  "<c:out value="${fio.titulo}" />"
                </a>
                por
                <jsp:include page="snippets/link_usuario.jsp">
                  <jsp:param name="u_email" value="${fio.autor.email}" />
                </jsp:include>
                <ul>
                  <li>
                    ${forum.comentariosDe(fio).size()} comentários
                  </li>
                  <li>
                    Postado em: ${fio.quandoPostou()}
                  </li>
                  <li>
                    Última atualização: ${fio.quandoAtualizou()}
                  </li>
                </ul>
                <!--
                  também note que não estamos acessando o atributo "titulo" do
                  fio diretamente. ele é privado (veja a classe Fio)! isso na
                  verdade chama o getter de nome getTitulo! por isso, crie
                  getters e setters com os nomes certinhos nas suas classes.
                  na dúvida, use o gerador automático do Eclipse.
                -->
              </li>
            </c:forEach>
            <c:if test="${forum.fiosMaisRecentes(5).size() == 0}">
              <!-- vazio! -->
              <li><i>Nenhuma atividade recente.</i></li>
            </c:if>
            <!-- sim, um espertalhão teria usado c:set. mas vamos com calma. -->
          </ul>
        </div>
        <!-- segundo bloco: uns botões -->
        <div class="six columns">
          <!--
            aqui, usamos um mecanismo chamado choose para emular um if-else.
            das tags dentro de um choose, só uma sobrevive.
            se você achou muito complicado, dois ifs testando condições opostas
            também funcionariam, mas são menos elegantes e mais vulneráveis a
            bugs. imagina se você edita uma condição mas esquece de editar a
            outra? aparecem duas coisas? ou nenhuma!
          -->
          <c:choose>
            <c:when test="${usuario == null}">
              <!-- usuário não está logado -->
              Você não está logado.
              <br>
              <br>
              <!-- formulário de login -->
              <form action="login/go" method="post">
                <b>E-mail:</b>
                <input type="email" name="email">
                <br>
                <b>Senha:</b>
                <input type="password" name="senha">
                <br>
                <input type="submit" value="Fazer login">
              </form>
              Se você não tiver uma conta, pode
              <a href="./usuario/cadastrar">criar uma</a>
              rapidinho!
            </c:when>
            <c:otherwise>
              <!-- usuário está logado -->
              Logado como <b><c:out value="${usuario.apelido}" /></b>.
              <br>
              <br>
              <a href="./usuario/perfil?apelido=${usuario.apelido}"
              class="button">
                Meu perfil
              </a><br>
              <a class="button" href="./fio/criar">Postar fio</a><br>
              <a class="button" href="./login/out">Logout</a><br>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div>
  </body>
</html>
