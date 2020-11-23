<!--
  Isso aqui é uma "snippet". Leia a explicação completa em linha_logado.jsp.
  
  Esta snippet gera um link para um usuário. Como é algo que fazemos muitas
  vezes, resolvi colocar numa snippet. Como você pode ver, ela referencia um
  usuário "u" que não foi definido aqui. Ele é passado via a diretiva jsp:param.
  
  Também recebemos um parâmetro showEmail, que se for definido para "true", faz
  incluir um link com o e-mail do usuário linkado também.
  
  Veja como é feito em qualquer página que usa esta snippet, como index.jsp ou
  fio.jsp.
  
  Ela também é bem doida: ela detecta quando o usuário a linkar é o usuário
  atual! Imagina ter que repetir toda essa lógica em cada JSP...
  
  Snippets são demais!
-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- como o parâmetro passado é um email de usuário, vamos puxar o usuário -->
<c:set var="u" value="${usuarios.porEmail(param.u_email)}" />
<!--
  aqui, usamos o choose pra bem mais do que um simples if-else.
  bem melhor do que ficar fazendo um monte de "if", né?
-->
<c:choose>
  <c:when test="${u == null}">
    <!-- e-mail recebido não corresponde a um usuário -->
    <span class="err">
      ERRO: usuário inválido linkado. E-mail especificado:
      "<c:out value="${param.u_email}" />"
    </span>
  </c:when>
  <c:when test="${usuario != null && u.equals(usuario)}">
    <!-- é você! -->
    <a href="../usuario/perfil?apelido=<c:out value="${u.apelido}" />">
      você
    </a>
  </c:when>
  <c:otherwise>
    <!-- não é você -->
    <a href="../usuario/perfil?apelido=<c:out value="${u.apelido}" />">
      <c:out value="${u.apelido}" />
    </a>
    <!--
      como queremos inserir os caracteres < e > em HTML, usamos as sequências de
      escape lt (less than) e gt (greater than) respectivamente.
      note que TUDO que é digitado pelo usuário é protegido por c:out.
      para saber mais o motivo (XSS), leia o index.jsp.
    -->
    <c:if test="${param.showEmail == 'true'}">
      &nbsp;&lt;<a href="mailto:<c:out value="${u.email}" />">
      <c:out value="${u.email}" /></a>&gt;
    </c:if>
  </c:otherwise>
</c:choose>
<!--
  remover a variável "u" do escopo, para evitar que ela contamine o JSP que nos
  incluiu.
-->
