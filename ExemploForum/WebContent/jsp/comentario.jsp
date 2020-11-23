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
  Este arquivo é semelhante ao formulario_fio.jsp, mas como cometários são
  criados direto na página do fio (fio.jsp), aqui não temos os dois casos,
  apenas edição.
  
  se o comentário não existir, teremos uma situação meio estranha.
  vamos só redirecionar pra homepage por enquanto.
-->
<c:if test="${comentario == null}">
  <c:redirect url=".." />
</c:if>
<!DOCTYPE html>
<html lang="pt-br">
  <head>
    <meta charset="utf-8">
    <meta name="description" content="${inf} fio - Fórum Simples de Exemplo">
    <meta name="author" content="Bruno Borges Paschoalinoto">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Fórum Exemplo - ${inf} fio</title>
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
          <h5>
            Editando comentário #${comentario.id} no fio
            <jsp:include page="snippets/permalink.jsp">
              <jsp:param name="lid" value="${comentario.pai.id}" />
              <jsp:param name="ltipo" value="fio" />
              <jsp:param name="lprefixo" value="../fio/" />
              <jsp:param name="showEmail" value="true" />
            </jsp:include>
          </h5>
          <hr>
          <br>
        </div>
      </div>
      <form action="edita" method="post" class="large-form">
        <input type="hidden" name="id" value="${comentario.id}">
        <div class="row center">
          <div class="twelve columns center">
            <label for="conteudo">Corpo do texto:</label>
            <br>
            <textarea name="conteudo"
            id="conteudo"><c:out value="${comentario.conteudo}" /></textarea>
          </div>
        </div>
        <div class="row center">
          <div class="six columns left">
            <input type="submit" value="Salvar alterações!">
          </div>
          <div class="six columns left">
            <a href="../fio/?id=${comentario.pai.id}#c-${comentario.id}"
            class="button">
              Cancelar e voltar ao fio
            </a>
          </div>
        </div>
      </form>
    </div>
  </body>
</html>
