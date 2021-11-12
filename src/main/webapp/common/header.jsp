<%@ page contentType="text/html;charset=UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!--SideBar Stylesheet-->
  <link rel="stylesheet" id="pageStyle" href="${pageContext.request.contextPath}/assets/css/hcStyle.css">
  <!--Bootstrap-->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/plugins/bootstrap/css/bootstrap.css" >
  <!--Bootstrap Icon-->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/plugins/bootstrap-icons/font/bootstrap-icons.css">
  <%-- Fontawesome --%>
  <%--TODO: 사용확정되면 다운로드 해서 변경  --%>
  <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
  <title>Title</title>
</head>
<style>
  .was-validated .form-control:invalid, .form-control.is-invalid  {
    border-color: #dc3545;
    padding-right: calc(1.5em + 0.75rem);
    background-image: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="rgba(220,53,69,.9)" class="bi bi-x-lg" viewBox="0 0 16 16"><path d="M1.293 1.293a1 1 0 0 1 1.414 0L8 6.586l5.293-5.293a1 1 0 1 1 1.414 1.414L9.414 8l5.293 5.293a1 1 0 0 1-1.414 1.414L8 9.414l-5.293 5.293a1 1 0 0 1-1.414-1.414L6.586 8 1.293 2.707a1 1 0 0 1 0-1.414z"/></svg>');
    background-repeat: no-repeat;
    background-position: right calc(0.375em + 0.1875rem) center;
    background-size: calc(0.75em + 0.375rem) calc(0.75em + 0.375rem);
  }
  .ui-sortable-helper {
    display: table;
  }
  .card-header{
    font-weight:600;
    color:white;
    min-height: 50px;

  }
  .dangerCard{
    box-shadow: 0 0 3px 3px #f67e7e;
  }
  .normalCard{
    box-shadow: 0 0 3px 3px #67aadf;
  }
</style>

<body id="body-pd">
<!--SideBarStart-->
<aside class="l-navbar expander" id="navbar">
  <nav class="HcNav">
    <div>
      <div class="nav_list" id="commonMenuList">
        <div class="nav_link HcCollapse ">
          <i class="bi bi-square-fill nav_icon HcCollapseEl"></i>
          <span class="ps-3 nav_name HcCollapseEl">기준정보관리</span>
          <i class="bi bi-caret-down-fill HcCollapse_link HcCollapseEl" style="width: 100%;"></i>
          <ul class="HcCollapse_menu">
            <li>
              <a href="/common/commonCode.do" class="HcCollapse_subLink">공통코드 관리</a>
            </li>
            <li>
              <a href="/treatmentCenter/home.do" class="HcCollapse_subLink">생활치료센터 관리</a>
            </li>
            <li>
              <a href="/user/home.do" class="HcCollapse_subLink">사용자 관리</a>
            </li>
            <li>
              <a href="/equipment/home.do" class="HcCollapse_subLink">장비 관리</a>
            </li>
            <li>
              <a href="/item/home.do" class="HcCollapse_subLink">측정항목 관리</a>
            </li>
            <li>
              <a href="/patientDashboard/call.do" class="HcCollapse_subLink">샘플 대쉬보드</a>
            </li>
          </ul>
        </div>
        <div class="nav_link HcCollapse">
          <i class="bi bi-square-fill nav_icon HcCollapseEl"></i>
          <span class="ps-3 nav_name HcCollapseEl">입소내역 관리</span>
          <i class="bi bi-caret-down-fill HcCollapse_link HcCollapseEl" style="width: 100%;"></i>
          <ul class="HcCollapse_menu">
            <li>
              <a href="/admission/admission.do" class="HcCollapse_subLink">입소자 정보 관리</a>
            </li>
            <li>
              <a href="/patientEquip/patientEquip.do" class="HcCollapse_subLink">입소자별 사용장비</a>
            </li>
          </ul>
        </div>
      </div>
      <!--Menu List-->
    </div>
  </nav>
</aside>
<!--SideBarEnd-->
<!--HeaderBarStart-->
<header class="HcHeader">
  <div class="nav_brand">
    <div class="row" style="height: 100%; width: 100%;">
      <div class="col-9 col-lg-7 centerDisplay">

        <div class="HeaderLogo centerDisplay justify-content-center">LOGO</div>
        <i class="bi bi-list-ul fs-5 nav_toggle pointerImg" id="nav_toggle" ></i>
        <div class="ps-lg-4 ps-0 pe-0" id="selectMenuName">메뉴정보</div>
      </div>

      <div class="col-3 col-lg-5 centerDisplay justify-content-end" >
        <div class="row">
<%--          <div id="darkModeDiv" class="col-lg-6 centerDisplay" style="height: 100%;" >--%>
<%--            DarkMode--%>
<%--            <div class="form-check form-switch ms-3">--%>
<%--              <input class="form-check-input fs-6 bi bi-moon-stars-fill d-flex align-items-center" type="checkbox" id="darkModeSwitch" style="height: 30px; width: 50px;" >--%>
<%--            </div>--%>
<%--          </div>--%>
          <div class="col-12 col-lg-6 HeaderUserInfo">
            UserInfo
            <i class="bi bi-box-arrow-right fs-5 pointerImg" id="logoutBtn" data-bs-toggle="modal" data-bs-target="#logOutModal"></i>
          </div>
        </div>
      </div>
    </div>
  </div>
</header>
<!--HeaderBarEnd-->
