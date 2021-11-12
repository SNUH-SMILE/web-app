<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>--%>
<%--
  User: wooyoung
  Date: 2021-09-13
  Time: 오전 10:23
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/header.jsp" %>

<!--Main Start-->
<main id="main-pd" class="main-nonePd body-pd">
    <input type="text" id="admissionId" hidden value="${admissionId}">
    <div class="container-fluid">
        <div class="row">
            <section class="col-lg-12">
                <div class="row">
                    <div class="col-lg-3 d-flex">
                        <div class="card" data-bs-toggle="modal" data-bs-target="#patientModal" style="width: 100%;" id="patientInfoCardDiv">
                            <div class="card-header" id="centerNmDiv">

                            </div>
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-lg-12 mb-2">
                                        <strong style="font-size: 30px; font-weight: 600">
                                            <span id="patientName"></span>
                                            <span>/</span>
                                            <span id="patientLocation"></span>
                                        </strong>
                                    </div>
                                    <div class="col-lg-12">
                                        <div class="row">
                                            <div class="col-5 col-lg-4 pe-lg-0">생년월일</div>
                                            <div class="col-7 col-lg-8" id="patientBirthDate"></div>
                                        </div>
                                    </div>
                                    <div class="col-lg-12">
                                        <div class="row">
                                            <div class="col-5 col-lg-4 pe-lg-0">환자번호</div>
                                            <div class="col-7 col-lg-8" id="patientId"></div>
                                        </div>
                                    </div>
                                    <div class="col-lg-12">
                                        <div class="row">
                                            <div class="col-5 col-lg-4">연락처</div>
                                            <div class="col-7 col-lg-8" id="patientTelNo"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-9">
                        <div class="row g-1">
                            <!--혈압-->
                            <div class="col-lg-4">
                                <div class="card">
                                    <div class="card-header d-flex justify-content-between ">
                                        <span>${centerItemListVO[0].itemNm}</span>
                                        <span id="recentBPDt"></span>
                                    </div>
                                    <div class="card-body vsCardBody" id="recentBPValue" >
                                    </div>
                                </div>
                            </div>
                            <!--맥박-->
                            <div class="col-lg-4">
                                <div class="card">
                                    <div class="card-header d-flex justify-content-between">
                                        <span>${centerItemListVO[1].itemNm}</span>
                                        <span id="recentHRDt"></span>
                                    </div>
                                    <div class="card-body vsCardBody" id="recentHRValue">
                                    </div>
                                </div>
                            </div>
                            <!--체온-->
                            <div class="col-lg-4">
                                <div class="card">
                                    <div class="card-header d-flex justify-content-between">
                                        <span>${centerItemListVO[2].itemNm}</span>
                                        <span id="recentBodyTempDt"></span>
                                    </div>
                                    <div class="card-body vsCardBody" id="recentBodyTempValue">
                                    </div>
                                </div>
                            </div>
                            <!--호흡-->
                            <div class="col-lg-4">
                                <div class="card">
                                    <div class="card-header d-flex justify-content-between">
                                        <span>${centerItemListVO[3].itemNm}</span>
                                        <span id="recentRespiratoryDt">-</span>
                                    </div>
                                    <div class="card-body vsCardBody" id="recentRespiratoryValue">
                                    </div>
                                </div>
                            </div>
                            <!--산소포화도-->
                            <div class="col-lg-4">
                                <div class="card">
                                    <div class="card-header d-flex justify-content-between">
                                        <span>${centerItemListVO[4].itemNm}</span>
                                        <span id="recentSPO2Dt"></span>
                                    </div>
                                    <div class="card-body vsCardBody" id="recentSPO2Value">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <section class="mt-3">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="card">
                            <div class="card-header">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <div class="row">
                                            <%--                                            <div class="col-2 col-lg-1 pe-0 centerDisplay">기간</div>--%>
                                            <div class="col-7 col-lg-6 ps-0">
                                                <div class="input-group">
                                                    <label  class="me-1 ms-1 mt-2" >기간</label>
                                                    <input type="date" id="dateFrom" name="resultDtFrom" class="form-control" placeholder="Input" value="${patientInfoVO.admitDate}">
                                                    <label class="me-1 ms-1 d-flex align-items-center" style="font-size: 1.1vw">~</label>
                                                    <input type="date" id="dateTo" name="resultDtTo" class="form-control" placeholder="Input">
                                                    <button type="button" class="btn btn-success ms-3" id="patientDetailDataSearchBtn"><i class="bi bi-search me-1"></i>검색</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-lg-6" id="dataDiv" style="transition: 0.5s">
                                        <div class="row">
                                            <div class="col-lg-12 mb-2 d-flex justify-content-between align-items-center">측정데이터 <button id="dataDivBtn" class="btn btn-secondary"><i class="bi bi-plus"></i></button></div>
                                            <div class="col-lg-12 table-responsive" style="height: 27vw;">
                                                <table class="table text-center">
                                                    <thead>
                                                    <tr>
                                                        <th style="width: 40%;">측정일시</th>
                                                        <th>${centerItemListVO[0].itemNm}</th>
                                                        <th>${centerItemListVO[1].itemNm}</th>
                                                        <th>${centerItemListVO[2].itemNm}</th>
                                                        <th>${centerItemListVO[3].itemNm}</th>
                                                        <th>${centerItemListVO[4].itemNm}</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody id="patientDetailVsDataTableBody">

                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-6" id="graphDiv" >
                                        <div class="row">
                                            <div class="col-lg-12 mb-2 d-flex justify-content-between align-items-center">그래프 <button class="btn btn-secondary" id="graphDivBtn"><i class="bi bi-plus"></i></button></div>
                                            <div class="col-lg-12" id="chart" style="height: 100%;"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>
</main>
<!--Main End-->

<%-- 성공여부 ALERT (javascript alert 대신용) 창 --%>
<div class="alert-div">
    <div class="alert alert-danger align-items-center fade show" role="alert" id="errorAlert">
        <div class="alert-header row align-items-start p-0 m-0">
            <div class="col-12 ps-2 pe-2 d-flex justify-content-between align-items-center">
                <div class="d-flex align-items-center">
                    <div class="me-2">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-exclamation-triangle" viewBox="0 0 16 16" style="transform:scale(1.25)">
                            <path d="M7.938 2.016A.13.13 0 0 1 8.002 2a.13.13 0 0 1 .063.016.146.146 0 0 1 .054.057l6.857 11.667c.036.06.035.124.002.183a.163.163 0 0 1-.054.06.116.116 0 0 1-.066.017H1.146a.115.115 0 0 1-.066-.017.163.163 0 0 1-.054-.06.176.176 0 0 1 .002-.183L7.884 2.073a.147.147 0 0 1 .054-.057zm1.044-.45a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566z"/>
                            <path d="M7.002 12a1 1 0 1 1 2 0 1 1 0 0 1-2 0zM7.1 5.995a.905.905 0 1 1 1.8 0l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995z"/>
                        </svg>
                    </div>
                    <p class="alert-heading mb-0"></p>
                </div>
                <button type="button" class="btn-close" data-bs-target="dismiss" aria-label="Close" id="btnAlertClose"></button>
            </div>
        </div>
        <hr/>
        <div class="d-flex align-items-center align-content-center">

            <div class="mr-2 d-grid align-content-between" id="errorMessage">
                <%-- error message --%>
                <div class="error-status"></div>
                <div class="error-message"></div>
            </div>
        </div>
    </div>
</div>
<div id="patientModal" class="modal" tabindex="-1" >
    <div class="modal-dialog modal-xl">
        <div class="modal-content">
            <div class="modal-header ">
                <div style="color: darkblue">L</div>
                <div style="color: deepskyblue">O</div>
                <div style="color: darkblue">G</div>
                <div style="color: deepskyblue">O</div>
                <div style="font-weight: bold">환자</div>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="table-responsive" style="height: 30vw;">
                    <table class="table">
                        <thead>
                        <tr class="text-center">
                            <th style="width: 10%;">선택</th>
                            <th>이름</th>
                            <th>성별</th>
                            <th>나이</th>
                            <th>위치</th>
                        </tr>
                        </thead>
                        <tbody id="patientDashboardListTableBody" class="text-center">

                        </tbody>
                    </table>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn SnuhBtn" data-bs-dismiss="modal" id="selectNewPatientBtn">선택</button>
            </div>
        </div>
    </div>
</div>
<%@ include file="/common/footer.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/apexcharts"></script>

<script src="${pageContext.request.contextPath}/assets/js/patientDetailDashboard.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/apexChart.js"></script>

<%--<script src="${pageContext.request.contextPath}/assets/js/vitalResult.js"></script>--%>
</body>
</html>


