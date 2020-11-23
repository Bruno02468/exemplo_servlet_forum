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
<c:set var="fios" value="${forum.fiosMaisRecentes(1000)}" />
<!DOCTYPE html>
<html lang="pt-br">
  <head>
    <meta charset="utf-8">
    <meta name="description" content="Lista de fios - Fórum Simples de Exemplo">
    <meta name="author" content="Bruno Borges Paschoalinoto">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Fórum Exemplo - Lista de fios</title>
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
          <h5>Lista de fios"</h5>
          <jsp:include page="snippets/linha_logado.jsp" />
          <hr>
        </div>
      </div>
      <!-- dados dos usuários, publicamente acessíveis -->
      <div class="row center">
        <div class="twelve columns">
          Lista de fios (atualizados mais recentemente primeiro):
          <br>
          <br>
          <table class="usuarios">
            <thead>
              <tr>
                <th>Permalink</th>
                <th>Autor</th>
                <th>nº comentários</th>
                <th>Data de criação</th>
                <th>Última atividade em</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="fio" items="${fios}">
                <tr>
                  <td>
                    <jsp:include page="snippets/permalink.jsp">
                      <jsp:param name="lid" value="${fio.id}" />
                      <jsp:param name="ltipo" value="fio" />
                      <jsp:param name="lprefixo" value="./" />
                      <jsp:param name="hideAuthor" value="true" />
                    </jsp:include>
                  </td>
                  <td>
                    <jsp:include page="snippets/link_usuario.jsp">
                      <jsp:param name="u_email" value="${fio.autor.email}" />
                      <jsp:param name="showEmail" value="false" />
                    </jsp:include>
                  </td>
                  <td>${forum.comentariosDe(fio).size()}</td>
                  <td>${fio.quandoPostou()}</td>
                  <td>${fio.quandoAtivou()}</td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </body>
</html>
