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
<c:set var="posts" value="${forum.postsMaisRecentes(perfilado, 5)}" />
<!DOCTYPE html>
<html lang="pt-br">
  <head>
    <meta charset="utf-8">
    <meta name="description" content="Lista de usuários - Fórum Simples de Exemplo">
    <meta name="author" content="Bruno Borges Paschoalinoto">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Fórum Exemplo - Lista de usuários</title>
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
          <h5>Lista de usuários"</h5>
          <jsp:include page="snippets/linha_logado.jsp" />
          <hr>
        </div>
      </div>
      <!-- dados dos usuários, publicamente acessíveis -->
      <div class="row center">
        <div class="twelve columns">
          Lista de usuários (mais antigos primeiro):
          <br>
          <br>
          <table class="usuarios">
            <thead>
              <tr>
                <th>Apelido</th>
                <th>E-mail</th>
                <th>Data de cadastro</th>
                <th>Último acesso</th>
                <th>nº fios</th>
                <th>nº comentários</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="u" items="${usuarios.todosUsuarios()}">
                <tr>
                  <td>
                    <jsp:include page="snippets/link_usuario.jsp">
                      <jsp:param name="u_email" value="${u.email}" />
                      <jsp:param name="showEmail" value="false" />
                    </jsp:include>
                  </td>
                  <td><c:out value="${u.email}" /></td>
                  <td class="menorzin">${u.quandoCadastrou()}</td>
                  <td class="menorzin">${u.quandoAcessou()}</td>
                  <td>${forum.fiosPorAutor(u).size()}</td>
                  <td>${forum.comentariosPorAutor(u).size()}</td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </body>
</html>
