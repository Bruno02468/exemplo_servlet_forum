<!--
=================================== ATENÇÃO ===================================

Nessa página, tem MUITA coisa não explicada. A ideia é não explicar o mesmo
conceito mais do que uma vez, salvo coisas muito doidas.
Portanto, se tem algo que eu não expliquei aqui, provavelmente foi explicado em
outra página. Um bom lugar pra começar a olhar é o index.jsp.

===================================== FIM =====================================
-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!--
  se o fio não existir, teremos uma situação meio estranha.
  vamos só redirecionar pra homepage por enquanto.
-->
<c:if test="${fio == null}">
  <c:redirect url=".." />
</c:if>
<!DOCTYPE html>
<html lang="pt-br">
  <head>
    <meta charset="utf-8">
    <meta name="description" content="${inf} fio - Fórum Simples de Exemplo">
    <meta name="author" content="Bruno Borges Paschoalinoto">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Fórum Exemplo - Fio ${fio.id}</title>
    <link rel="stylesheet" type="text/css"
    href="//cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css">
    <link rel="stylesheet" type="text/css"
    href="//cdnjs.cloudflare.com/ajax/libs/skeleton/2.0.4/skeleton.min.css">
    <link rel="stylesheet" type="text/css"
    href="${pageContext.request.contextPath}/static/simples.css">
  </head>
  <body>
    <div class="container">
      <div class="row center">
        <div class="twelve columns">
          <h3><a href="../">Fórum Exemplo</a></h3>
          <h5>Visualizando fio #${fio.id}</h5>
          <jsp:include page="snippets/linha_logado.jsp" />
          <hr>
        </div>
      </div>
      <!-- título do fio -->
      <div class="row center">
        <div class="twelve columns">
          <h4 class="fio-titulo"><c:out value="${fio.titulo}" /></h4>
        </div>
      </div>
      <br>
      <!-- corpo do texto do fio -->
      <div class="row center fio-conteudo">
        <div class="twelve columns">
          <code class="u-full-width"><c:out value="${fio.conteudo}" /></code>
        </div>
      </div>
      <br>
      <!-- detalhes do fio -->
      <div class="row right detalhes">
        <div class="twelve columns">
          Postado em ${fio.quandoPostou()} por
          <!-- incluir a snippet de link de usuário!  -->
          <jsp:include page="snippets/link_usuario.jsp">
            <jsp:param name="u_email" value="${fio.autor.email}" />
            <jsp:param name="showEmail" value="true" />
          </jsp:include>
          <c:if test="${fio.autor == usuario}">
          --
          <i>
            <a href="editar?id=${fio.id}">Editar</a> /
            <a href="deleta?id=${fio.id}">Deletar</a>
          </i>
        </c:if>
          <c:if test="${fio.editado != null}">
            <br>
            (última edição: ${fio.quandoEditou()})
          </c:if>
        </div>
      </div>
      <!-- formulário para postar um comentário, só se estiver logado -->
      <br>
      <br>
      <c:choose>
        <c:when test="${usuario != null}">
          <div class="row center">
            <div class="twelve columns">
              <b>Postar um comentário:</b>
            </div>
          </div>
          <br>
          <form action="../comentario/cria" method="post" class="large-form">
            <input type="hidden" name="id_fio" value="${fio.id}" />
            <div class="row center">
              <div class="ten columns">
                <textarea id="conteudo-comentario" name="conteudo"></textarea>  
              </div>
              <div class="two columns">
                <label></label>
                <input type="submit" value="Postar!" id="btn-comentario">
              </div>
            </div>
          </form>
        </c:when>
        <c:otherwise>
          <div class="row center">
            <div class="twelve columns">
              <i>
                Para postar um comentário,
                <a href="../login/?next=../${easyNext}">faça login!</a>
              </i>
            </div>
          </div>
          <br>
        </c:otherwise>
      </c:choose>
      <div class="row center">
        <div class="twelve columns">
          <b>Comentários:</b>
        </div>
      </div>
      <!-- agora, os comentários um a um -->
      <c:forEach var="comentario" items="${forum.comentariosDe(fio)}">
        <!--
          note o ID do elemento. eu uso isso nos redirecionamentos!
          é bem legal. veja o ServletComentario (funções cria e edita) que eu
          explico melhor como funciona o fragmento, e por que este ID é necessário
          para isso.
        -->
        <hr>
        <div class="row center fio-comentario" id="c-${comentario.id}">
          <div class="three columns">
            <!-- incluir a snippet de link de usuário!  -->
            <jsp:include page="snippets/link_usuario.jsp">
              <jsp:param name="u_email" value="${fio.autor.email}" />
            </jsp:include>
            respondeu:
          </div>
          <div class="nine columns">
            <code class="u-full-width"
            ><c:out value="${comentario.conteudo}" /></code>
          </div>
        </div>
        <br>
        <!-- detalhes do comentário -->
        <div class="row right detalhes">
          <div class="five columns">
            Postado em: ${comentario.quandoPostou()}.
            <c:if test="${comentario.editado != null}">
              <!-- só mostrar a data de edição se tiver sido editado -->
              <br>(editado por último em ${comentario.quandoEditou()})
            </c:if>
          </div>
          <div class="seven columns">
            <!-- permalink -->
            Permalink:
            <jsp:include page="snippets/permalink.jsp">
              <jsp:param name="lid" value="${comentario.id}" />
              <jsp:param name="ltipo" value="comentario" />
              <jsp:param name="lprefixo" value="./" />
              <jsp:param name="showEmail" value="true" />
            </jsp:include>
            <!-- se o comentário for seu, vamos oferecer de editar/deletar -->
            <c:if test="${comentario.autor == usuario}">
              <br>
              <i>
                Este comentário é seu.
                <a href="../comentario/editar?id=${comentario.id}">Editar</a> /
                <a href="../comentario/deleta?id=${comentario.id}">Deletar</a>
              </i>
            </c:if>
          </div>
          
        </div>
      </c:forEach>
      <hr>
    </div>
  </body>
</html>
