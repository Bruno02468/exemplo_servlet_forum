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
  se o usuário não existir, teremos uma situação meio estranha.
  vamos só redirecionar pra homepage por enquanto.
-->
<c:if test="${perfilado == null}">
  <c:redirect url=".." />
</c:if>
<c:set var="posts" value="${forum.postsMaisRecentes(perfilado, 5)}" />
<!DOCTYPE html>
<html lang="pt-br">
  <head>
    <meta charset="utf-8">
    <meta name="description"
    content="Perfil de &quot;<c:out value="${perfilado.apelido}" />&quot;
    - Fórum Simples de Exemplo">
    <meta name="author" content="Bruno Borges Paschoalinoto">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Perfil de &quot;<c:out value="${perfilado.apelido}" /></title>
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
          <h5>Perfil de "<c:out value="${perfilado.apelido}" />"</h5>
          <jsp:include page="snippets/linha_logado.jsp" />
          <hr>
        </div>
      </div>
      <!-- dados do usuário, publicamente acessíveis -->
      <div class="row center">
        <div class="six columns">
          Informações básicas:
          <br>
          <br>
          <ul class="left">
            <li><b>Apelido:</b> <c:out value="${perfilado.apelido}" /></li>
            <li><b>E-mail:</b> <c:out value="${perfilado.email}" /></li>
            <li><b>Cadastrado em:</b> ${perfilado.quandoCadastrou()}</li>
            <li><b>Último acesso:</b> ${perfilado.quandoAcessou()}</li>
          </ul>
        </div>
        <div class="six columns">
          Postagens mais recentes:
          <br>
          <br>
          <ul class="left">
            <c:forEach var="post" items="${posts}">
              <li>
                <c:set var="tipo" value="${post['class'].simpleName}" />
                <c:choose>
                  <c:when test="${tipo eq 'Fio'}">
                    <!-- atividade é um fio -->
                    Fio
                    <jsp:include page="snippets/permalink.jsp">
                      <jsp:param name="lid" value="${post.id}" />
                      <jsp:param name="ltipo" value="fio" />
                      <jsp:param name="lprefixo" value="../fio/" />
                      <jsp:param name="hideAuthor" value="true" />
                    </jsp:include>
                  </c:when>
                  <c:when test="${tipo eq 'Comentario'}">
                    <!-- atividade é um comentário -->
                    Comentário
                    <jsp:include page="snippets/permalink.jsp">
                      <jsp:param name="lid" value="${post.id}" />
                      <jsp:param name="ltipo" value="comentario" />
                      <jsp:param name="lprefixo" value="../fio/" />
                      <jsp:param name="showParent" value="true" />
                      <jsp:param name="hideAuthor" value="true" />
                    </jsp:include>
                  </c:when>
                </c:choose>
                <c:remove var="tipo" />
              </li>
            </c:forEach>
            <c:if test="${posts.size() == 0}">
              <!-- vazio! -->
              <li><i>Nenhuma atividade recente.</i></li>
            </c:if>
          </ul>
        </div>
      </div>
      <!-- se esse usuário for você, vamos listar algumas ações -->
      <c:if test="${usuario eq perfilado}">
        <div class="row center">
          <div class="six columns">
            <form action="./mudaApelido" method="post">
              <label for="apelido">Quer mudar seu apelido?</label>
              <input type="text" name="apelido"
              value="<c:out value="${usuario.apelido}" />"><br>
              <input type="submit" value="Mudar apelido!">
            </form>
          </div>
          <div class="six columns">
            <form action="./mudaSenha" method="post">
              <label for="apelido">Quer mudar sua senha?</label>
              <input type="text" name="senha"><br>
              <input type="submit" value="Mudar senha!">
            </form>
          </div>
        </div>
      </c:if>
    </div>
  </body>
</html>
