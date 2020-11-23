<!--
  Isso aqui é uma "snippet". A ideia é ela ser um bloco que fale o usuário
  logado, tipo no index, e que ela possa ser inserida no topo das páginas de
  maneira transparente.
  
  Ao invés de copiar e colar o código, eu vou colocar ele num arquivo separado
  aqui, e vou usar a diretiva jsp:include para inserir este código ali no meio.
  
  Note que isso pode ser feito pra qualquer coisa, e tem como passar parâmetros
  pra essas snippets (pela diretiva param dentro da include). Ou seja, daria
  pra fazer isso pra toda aquela frunfa no começo de cada arquivo!
  
  Eu optei por não fazer isso pra não deixar o projeto muito avançado. Ao invés
  disso, vou fazer pro aviso de "logado como" no topo.
-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:choose>
  <c:when test="${usuario != null}">
    Logado como
    <a href="../usuario/perfil?apelido=<c:out value="${usuario.apelido}" />">
      <c:out value="${usuario.apelido}" />
    </a> -- 
    <a href="../login/out?next=../${easyNext}">Fazer logout</a>
  </c:when>
  <c:otherwise>
    Não logado -- <a href="../login/?next=../${easyNext}">Fazer login</a>
  </c:otherwise>
</c:choose>
<br>