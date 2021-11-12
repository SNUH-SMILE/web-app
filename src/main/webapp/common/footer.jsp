<%@ page contentType="text/html;charset=UTF-8" %>
<!--Logout Modal Start-->
<div id="logOutModal" class="modal" tabindex="-1">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header ">
        <div style="color: darkblue">L</div>
        <div style="color: deepskyblue">O</div>
        <div style="color: darkblue">G</div>
        <div style="color: deepskyblue">O</div>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" id="modalCloseBtn"></button>
      </div>
      <div class="modal-body centerDisplay">
        로그아웃 하시겠습니까?
      </div>
      <div class="modal-footer">
        <form action="/login/logout.do" method="post">
          <button type="submit" class="btn SnuhBtn" id="modalLogOutBtn">LogOut</button>
        </form>
      </div>
    </div>
  </div>
</div>
<!--Logout Modal End-->
<!--position Modal End-->

<%--<script src="../../assets/plugins/jquery/dist/jquery.slim.js"></script>--%>
<%--<script src="../../assets/plugins/jquery/dist/core.js"></script>--%>
<script src="${pageContext.request.contextPath}/assets/plugins/jquery/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/plugins/jquery/js/jquery.inputmask.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/plugins/bootstrap/js/bootstrap.bundle.js"></script>
<script src="${pageContext.request.contextPath}/assets/plugins/bootstrap/js/bootstrap.js" ></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<!--Side&HeaderBarScript-->
<script src="${pageContext.request.contextPath}/assets/js/hcScript.js"></script>
<!--
<script>
  const modalLogOutBtn = document.querySelector('#modalLogOutBtn');
  modalLogOutBtn.addEventListener('click',function () {
    location.replace('/');
  })
</script>
-->