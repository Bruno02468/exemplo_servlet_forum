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
  Aqui, um padrão de design diferente. Ao invés de criarmos um JSP pra criar
  e outro pra editar... já percebeu que eles são largamente os mesmos? Não muda
  quase nada! Que tal a gente usar o mesmo JSP, e inserir algumas coisas a mais
  ou diferentes na ocasião de estarmos editando?
  
  Quando estivermos editando, o servlet de fio nos vai passar um atributo "fio".
-->
<c:choose>
  <c:when test="${fio != null}">
    <!-- estamos editando um fio -->
    <c:set var="inf" value="Editar" />
    <c:set var="action" value="edita" />
    <c:set var="btn" value="Salvar alterações!" />
  </c:when>
  <c:otherwise>
    <!-- estamos criando um fio -->
    <c:set var="inf" value="Criar" />
    <c:set var="action" value="cria" />
    <c:set var="btn" value="Postar fio!" />
  </c:otherwise>
</c:choose>
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
    href="../static/simples.css">
  </head>
  <body>
    <div class="container">
      <div class="row center">
        <div class="twelve columns">
          <h3><a href="../">Fórum Exemplo</a></h3>
          <h5>${inf} fio</h5>
          <hr>
          <jsp:include page="snippets/linha_logado.jsp" />
          <br>
        </div>
      </div>
      <form action="${action}" method="post" class="large-form">
        <div class="row center">
          <div class="twelve columns center">
            <label for="titulo">Título:</label>
            <br>
            <input type="text" name="titulo" id="titulo">
          </div>
        </div>
        <div class="row center">
          <div class="twelve columns center">
            <label for="conteudo">Corpo do texto:</label>
            <br>
            <textarea name="conteudo" id="conteudo"></textarea>
          </div>
        </div>
        <div class="row center">
          <div class="twelve columns left">
            <input type="submit" value="${btn}">
          </div>
        </div>
      </form>
      <!-- se o servlet de usuário informar erro, vamos imprimir! -->
      <c:if test="${erro != null}">
        <div class="row center err">
          <div class="twelve columns">
            <c:out value="${erro}" />
          </div>
        </div>
      </c:if>
    </div>
  </body>
</html>
