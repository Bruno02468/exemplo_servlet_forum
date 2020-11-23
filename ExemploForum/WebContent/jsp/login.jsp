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
<!DOCTYPE html>
<html lang="pt-br">
  <head>
    <meta charset="utf-8">
    <meta name="description" content="Fazer login - Fórum Simples de Exemplo">
    <meta name="author" content="Bruno Borges Paschoalinoto">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Fórum Exemplo - Login</title>
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
          <h3><a href="..">Fórum Exemplo</a></h3>
          <h5>Fazer login</h5>
          <hr>
          <br>
        </div>
      </div>
      <div class="row center">
        <div class="twelve columns">
          Entre com seus dados:
        </div>
      </div>
      <br>
      <form action="go" method="post" class="thin-form">
        <input type="hidden" name="next" value="${param.next}">
        <div class="row center">
          <div class="six columns right">
            <label for="email">E-mail:</label>
            <br>
            <input type="email" name="email" id="email">
          </div>
          <div class="six columns left">
            <label for="senha">Senha:</label>
            <br>
            <input type="password" name="senha" id="senha">
          </div>
        </div>
        <div class="row center">
          <div class="twelve columns center">
            <input type="submit" value="Fazer login!">
          </div>
        </div>
      </form>
      <!-- se o servlet de usuário informar erro, vamos imprimir! -->
      <c:if test="${param.failed != null}">
        <div class="row center err">
          <div class="twelve columns">
            Credenciais inválidas!
          </div>
        </div>
      </c:if>
    </div>
  </body>
</html>
