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
    href="../static/simples.css">
  </head>
  <body>
    <div class="container">
      <div class="row center">
        <div class="twelve columns">
          <h3><a href="..">Fórum Exemplo</a></h3>
          <h5>Erro!</h5>
          <hr>
          <br>
        </div>
      </div>
      <div class="row center">
        <div class="twelve columns">
          Parece que uma exceção escapou!
          <br>
          <br>
          Mensagem:
          <code>
            <c:out value="${exception.message}" />
          </code>
          <br>
          <br>
          Stack trace:
          <br>
          <code>
            <c:out value="${exception.stackTrace}" />
          </code>
        </div>
      </div>
    </div>
  </body>
</html>
