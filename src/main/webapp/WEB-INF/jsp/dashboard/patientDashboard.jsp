<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  User: wooyoung
  Date: 2021-09-09
  Time: 오후 3:31
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/header.jsp" %>

<!--Main Start-->
<main id="main-pd" class="main-nonePd body-pd">
    <div class="container-fluid">
        <div class="row">
            <!-- 환자 카드 -->
            <section class="col-lg-12 mb-3">
                <div class="row g-3" id="cardContainerDiv">
                    <div class="col-lg-12 ps-0"><span><h4 class="ps-2 mb-0" style="border-left: 5px solid #0C5DF4;"><c:out value="${centerInfo.centerNm}"></c:out><i class="bi bi-people-fill me-2 ms-3"></i>입소자수:<c:out value="${centerInfo.admissionCount}"></c:out>명 <i class="bi bi-pin-map-fill ms-3 me-2"></i>위치:<c:out value="${centerInfo.centerLocation}"></c:out> <i class="bi bi-building me-2 ms-3"></i>병원:<c:out value="${centerInfo.hospitalNm}"></c:out></h4> </span></div>
                </div>
            </section>
            <!-- 환자 카드 -->

        </div>
    </div>
</main>
<!--Main End-->


<%@ include file="/common/footer.jsp" %>

<script>
 /**
  * Document 생성후 환자 정보 조회
  */
 window.addEventListener('load',function () {

     commonAjax('list.ajax',null,patientLoadSuccess,patientLoadComplete);
     /**
      * 조회성공시 Card Append
      */
     function patientLoadSuccess(data) {
         console.log('patientLoadSuccess',data);
         const cardContainerDiv = $('#cardContainerDiv');

         data.forEach((obj,idx)=>{
             if(obj.bp === null){
                 obj.bp = '-';
             }
             else{
                 obj.bp += obj.bpUnit;
             }

             if(obj.bt === null){
                 obj.bt = '-';
             }
             else{
                 obj.bt +=obj.btUnit;
             }

             if(obj.pr === null){
                 obj.pr = '-';
             }
             else{
                 obj.pr += obj.prUnit;
             }

             if(obj.rr === null){
                 obj.rr = '-';
             }
             else{
                 obj.rr += obj.rrUnit;
             }

             if(obj.spo2 === null){
                 obj.spo2 = '-';
             }
             else{
                 obj.spo2 += obj.spo2Unit;
             }
             cardContainerDiv.append(
                 '<div class="col-12 col-md-6 col-lg-4 col-xl-2 p-0 cardBox" data-admission="'+ obj.admissionId +'" data-patient="'+ obj.patientId +'">'
                 +'<div class="card ms-1 me-1" >'
                 +'<div class="card-header ps-0" style=" background: #145b9b;">'
                 +'<div class="d-flex flex-column flex-lg-row align-items-start align-items-lg-center" name="dashBoardCardHeader">'
                 // +'<strong class="ps-2 mt-1">'+ obj.name +'('+ obj.sexNm +'/'+ obj.age +') 위치:'+ obj.locationNm +'</strong>'
                 +'<strong class="col-12 col-lg-6 ps-2 mt-1">'+ obj.name +'('+ obj.sexNm +'/'+ obj.age +')</strong>'
                 +'<strong class="col-12 col-lg-6 ps-lg-0 ps-2 mt-1"> 위치:'+ obj.locationNm +'</strong>'
                 +'</div>'
                 +'</div>'
                 +'<div class="card-body hcPatientDashboardCardBodyColor" style="font-weight: bold; font-size: 1.1rem">'
                 +'<div class="row">'
                 +'<div class="col-lg-12">'
                 +'<div class="row g-2">'
                 +'<div class="col-lg-5" >'+obj.bpNm
                 +'</div>'
                 +'<div class="col-lg-7" name="dangerStatus" data-dangerStatus ='+ obj.bpRiskYn +'>'+obj.bp
                 +'</div>'
                 +'<div class="col-lg-5" >'+obj.prNm
                 +'</div>'
                 +'<div class="col-lg-7" name="dangerStatus" data-dangerStatus ='+ obj.prRiskYn +'>'+obj.pr
                 +'</div>'
                 +'<div class="col-lg-5" >'+obj.btNm
                 +'</div>'
                 +'<div class="col-lg-7" name="dangerStatus" data-dangerStatus ='+ obj.btRiskYn +'>'+obj.bt
                 +'</div>'
                 +'<div class="col-lg-5" >'+obj.rrNm
                 +'</div>'
                 +'<div class="col-lg-7" name="dangerStatus" data-dangerStatus ='+ obj.rrRiskYn +'>'+obj.rr
                 +'</div>'
                 +'<div class="col-lg-5">'+obj.spo2Nm
                 +'</div>'
                 +'<div class="col-lg-7" name="dangerStatus" data-dangerStatus ='+ obj.spo2RiskYn +'>'+obj.spo2
                 +'</div>'
                 +'</div>'
                 +'</div>'
                 +'</div>'
                 +'</div>'
                 +'</div>'
                 +'</div>'
             );
         })

     };

     /**
      * 정상치를 넘어갔을때 표시
      */
     function patientLoadComplete() {
         let dangerStatusDivs = document.querySelectorAll('div[name="dangerStatus"]');
         //V/S 수치를 가지고있는 Div들 전부 검사할려고 루프문 작성
         for (const dangerStatusDiv of dangerStatusDivs) {
             let dangerStatus = false;
             //정상치 넘어갔는지 상태 보려고 작성
             if(dangerStatusDiv.dataset.dangerstatus !== 'N'){
                 dangerStatusDiv.classList.add('dangerStatusFontColor');

             }
             if(dangerStatusDiv.offsetParent.querySelectorAll('.dangerStatusFontColor').length === 0){
                 dangerStatusDiv.offsetParent.classList.add('normalCard');
                 dangerStatusDiv.offsetParent.querySelector('div[name="dashBoardCardHeader"]').setAttribute( 'style','border-left: 5px solid #67aadf');
             }
             else{
                 dangerStatusDiv.offsetParent.classList.add('dangerCard');
                 dangerStatusDiv.offsetParent.querySelector('div[name="dashBoardCardHeader"]').setAttribute( 'style','border-left: 5px solid lightcoral');
             }
         }
         /**
          * Card 눌렀을때 화면 이동 이벤트
          */
         let cardDivs = document.querySelectorAll('.cardBox');
         for (const cardDiv of cardDivs) {
             cardDiv.addEventListener('click',function () {
                 console.log(cardDiv.dataset.admission)
                 console.log(cardDiv.dataset.patient)
//                  location.href='/vital/'+ cardDiv.dataset.admission +'/'+ cardDiv.dataset.patient +'/result.do';
//                  location.href='/patientDetailDashboard/call.do?admissionId=' + cardDiv.dataset.admission;
                 location.href='/patientDetailDashboard/patientHome.do?admissionId=' + cardDiv.dataset.admission;
             });
         }
     }
 })
 setSelectMenuName('DashBoard',0,5);
</script>
</body>
</html>


