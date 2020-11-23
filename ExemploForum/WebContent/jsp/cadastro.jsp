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
    <meta name="description" content="Criar conta - Fórum Simples de Exemplo">
    <meta name="author" content="Bruno Borges Paschoalinoto">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Fórum Exemplo - Criar conta</title>
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
          <h5>Criar conta</h5>
          <hr>
          <br>
        </div>
      </div>
      <div class="row center">
        <div class="twelve columns">
          Para criar uma conta, preencha os seguintes dados:
        </div>
      </div>
      <br>
      <br>
      <form action="cadastro" method="post" class="thin-form">
        <div class="row center">
          <div class="six columns right">
            <label for="email">Endereço de e-mail:</label>
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
          <div class="six columns right">
            <label for="apelido">Apelido (alterável):</label>
            <br>
            <input type="text" name="apelido" id="apelido">
          </div>
          <div class="six columns left">
            <label>Tudo certo?</label>
            <input type="submit" value="Criar conta!">
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
