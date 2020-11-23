<!--
  Isso aqui é uma "snippet". Leia a explicação completa em linha_logado.jsp.
  
  Esta snippet gera um link permamente para um fio ou comentário.
  
  Ela recebe como parâmetro umas coisas: um ID, e um "tipo", informando se é fio
  ou se é comentário. Ela também recebe um "prefixo", que é o caminho relativo
  necessário para se chegar no caminho "fio". Esse prefixo geralmente vai ser
  um dos seguintes: "./fio/", "../fio/", ou "./", nas ocasiões em que estamos
  abaixo, acima, ou dentro da "pasta" fio na URL, respectivamente.
  
  Esse prefixo pode ser computado na hora, mas isso seria complicado demais pra
  colocar num exemplo.
  
  Também há o parâmetro showParent, que se estiver definido, faz aparecer também
  o permalink do fio-pai no caso de um comentário, e o showEmail, que é passado
  para a snippet que gera os links dos usuários autores.
  
  Por fim, um parâmetro opcional hideAuthor. Quando 'true', não mostra os
  autores primários.
-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- converter o parâmetro de ID para inteiro! -->
<c:set var="nid" value="${Integer.valueOf(param.lid)}" />
<c:choose>
  <c:when test="${param.ltipo == 'fio'}">
    <!-- permalink a um fio -->
    <c:set var="lfio" value="${forum.fioPorId(nid)}" />
    <a href="${param.lprefixo}?id=${lfio.id}">
      #${lfio.id} - "<c:out value="${lfio.titulo}" />"
    </a>
    <c:if test="${param.hideAuthor != 'true'}">
      por
      <!-- yep, uma snippet incluindo outra! top, né? -->
      <jsp:include page="link_usuario.jsp">
        <jsp:param name="u_email" value="${lfio.autor.email}" />
        <jsp:param name="showEmail" value="${param.showEmail}" />
      </jsp:include>
    </c:if>
  </c:when>
  <c:when test="${param.ltipo == 'comentario'}">
    <!-- permalink a um comentário -->
    <c:set var="lcomentario" value="${forum.comentarioPorId(nid)}" />
    <a href="${param.lprefixo}?id=${lcomentario.pai.id}#c-${lcomentario.id}">
      #${lcomentario.pai.id}-${lcomentario.id}
    </a>
    <c:if test="${param.hideAuthor != 'true'}">
      por
      <jsp:include page="link_usuario.jsp">
        <jsp:param name="u_email" value="${lcomentario.autor.email}" />
        <jsp:param name="showEmail" value="${param.showEmail}" />
      </jsp:include>
    </c:if>
    <c:if test="${not empty param.showParent}">
      em
      <!-- quer mais? UMA SNIPPET INCLUINDO A SI MESMA. -->
      <jsp:include page="permalink.jsp">
        <jsp:param name="lid" value="${lcomentario.pai.id}" />
        <jsp:param name="ltipo" value="fio" />
        <jsp:param name="lprefixo" value="${param.lprefixo}" />
        <jsp:param name="hideAuthor" value="false" />
      </jsp:include>
    </c:if>
  </c:when>
  <c:otherwise>
    <!-- nenhum dois dois?? -->
    <span class="err">
      ERRO: invocação da snippet permalink.jsp com ltipo="${param.ltipo}".<br>
      Os tipos suportados são "fio" e "comentario".
    </span>
  </c:otherwise>
</c:choose>
<!--
  remover as variáveis que criamos, para evitar que ela contamine o JSP que nos
  incluiu.
-->
<c:remove var="lfio" />
<c:remove var="lcomentario" />
<c:remove var="nid" />
