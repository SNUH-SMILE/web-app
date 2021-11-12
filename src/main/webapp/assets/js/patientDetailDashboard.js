const patientDetailVsDataTableBody = $('#patientDetailVsDataTableBody')
const centerNmDiv = document.querySelector('#centerNmDiv');            //센터명
const patientName = document.querySelector('#patientName');            //환자이름
const patientLocation = document.querySelector('#patientLocation');    //환자위치
const patientBirthDate = document.querySelector('#patientBirthDate');  //환자생일
const patientId = document.querySelector('#patientId');                //환자ID
const patientTelNo = document.querySelector('#patientTelNo');          //환자연락처
const recentBPValue = $('#recentBPValue');                                     //혈압
const recentBPDt = document.querySelector('#recentBPDt');              //혈압측정시간
const recentHRValue = $('#recentHRValue');                                     //맥박
const recentHRDt = document.querySelector('#recentHRDt');              //맥박측정시간
const recentBodyTempValue = $('#recentBodyTempValue');                         //체온
const recentBodyTempDt = document.querySelector('#recentBodyTempDt');  //체온측정시간
const recentRespiratoryValue = $('#recentRespiratoryValue');                   //호흡
const recentRespiratoryDt = document.querySelector('#recentRespiratoryDt');//호흡측정시간
const recentSPO2Value = $('#recentSPO2Value');                                 //산소포화도
const recentSPO2Dt = document.querySelector('#recentSPO2Dt');          //산소포화도측정시간
const dateFrom = document.querySelector('#dateFrom');                  //입소일
const dateTo = document.querySelector('#dateTo');                      //오늘날짜
let chart;
let dataStatus = true
//데이터 초기화
function reset() {
    //환자 정보
    centerNmDiv.innerText='';
    patientName.innerText = '';
    patientLocation.innerText = '';
    patientBirthDate.innerText = '';
    patientId.innerText = '';
    patientTelNo.innerText = '';

    //V/S 값
    recentBPValue.html('');
    recentHRValue.html('');
    recentBodyTempValue.html('');
    recentRespiratoryValue.html('');
    recentSPO2Value.html('');

    //V/S 측정시간
    recentBPDt.innerText = '';
    recentHRDt.innerText = '';
    recentBodyTempDt.innerText = '';
    recentRespiratoryDt.innerText = '';
    recentSPO2Dt.innerText = '';
}
function getData() {
    let admissionId = document.querySelector('#admissionId');
    commonAjax('patientDetails.ajax', { admissionId: admissionId.value }, patientDetailsSuccess, patientDetailsComplete);
    function patientDetailsSuccess(data) {
        patientDetailVsDataTableBody.html('')
        document.querySelector("#chart").innerHTML = '';

        //기간 오늘날짜로 세팅
        dateTo.value = new Date().toISOString().slice(0, 10);


        //초기화
        reset();
        // 데이터 세팅
        //환자 정보
        centerNmDiv.innerText = data.centerNm;
        patientName.innerText = data.name;
        patientLocation.innerText = data.locationNm;
        patientBirthDate.innerText = data.birthDate;
        patientId.innerText = data.patientId;
        patientTelNo.innerText = data.contact;

        //V/S 값
        if (data.bp === null) {
            data.bp = '-/-'
        }
        else{
            data.bp += data.bpUnit
        }
        if (data.pr === null) {
            data.pr = '-'
        }
        else{
            data.pr += data.prUnit
        }
        if (data.bt === null) {
            data.bt = '-'
        }
        else{
            data.bt += data.btUnit
        }
        if (data.rr === null) {
            data.rr = '-'
        }
        else{
            data.rr += data.rrUnit
        }
        if (data.spo2 === null) {
            data.spo2 = '-'
        }
        else{
            data.spo2 += data.spo2Unit
        }
        recentBPValue.append
        ('<span class="hcVitalSign" data-dangerstatus="' + data.bpRiskYn + '">' + data.bp + '</span>');
        recentHRValue.append
        ('<span class="hcVitalSign" data-dangerstatus="' + data.prRiskYn + '">' + data.pr + '</span>');
        recentBodyTempValue.append
        ('<span class="hcVitalSign" data-dangerstatus="' + data.btRiskYn + '">' + data.bt + '</span>');
        recentRespiratoryValue.append
        ('<span class="hcVitalSign" data-dangerstatus="' + data.rrRiskYn + '">' + data.rr + '</span>');
        recentSPO2Value.append
        ('<span class="hcVitalSign">' + data.spo2 + '</span>');

        //V/S 측정시간
        recentBPDt.innerText = data.bpResultDt
        recentHRDt.innerText = data.prResultDt;
        recentBodyTempDt.innerText = data.btResultDt;
        recentRespiratoryDt.innerText = data.rrResultDt;
        recentSPO2Dt.innerText = data.spo2ResultDt;

        //입소일
        dateFrom.value = data.admitDate;
        //차트 파일 저장시 사용할 파일이름 (환자명 기간)
        fileName = patientName.innerText+dateFrom.value+'~'+dateTo.value;


    }
    function patientDetailsComplete() {
        getVitalSignGraphData();
        cardVitalSingDanger();
    }
}

//카드에 V/S이 정상치를 넘어가면 빨간색으로 표시해주는 함수
function cardVitalSingDanger() {
    let cardVitalSigns =  document.querySelectorAll('.hcVitalSign');
    console.log('cardVitalSingDanger')
    for (let i = 0; i < cardVitalSigns.length; i++) {
        if(cardVitalSigns[i].dataset.dangerstatus==='Y'){
            cardVitalSigns[i].setAttribute('style','color:red')
        }
    }

}
window.addEventListener('load', function () {
    const patientDetailVsDataTableBody = $('#patientDetailVsDataTableBody')
    patientDetailVsDataTableBody.html('')
    document.querySelector("#chart").innerHTML = '';
    const admissionId = document.querySelector('#admissionId');        //입소자Id

    //입소자Id가없으면 바로 Modal이 표시되도록
    if(admissionId.value===''){
        let patientInfoCardDiv = document.querySelector('#patientInfoCardDiv');
        commonAjax('/patientDashboard/list.ajax', null, patientLoadSuccess, patientLoadComplete);
        patientInfoCardDiv.click();
        return;
    }

    //상단에 현재 메뉴표시
    setSelectMenuName('DashBoard', 0, 5);
    //환자 상세정보 조회
    makeChart();
    getData();
    //모달창 환자리스트 조회
    commonAjax('/patientDashboard/list.ajax', null, patientLoadSuccess, patientLoadComplete);
    function patientLoadSuccess(data) {
        const patientDashboardListTableBody = $('#patientDashboardListTableBody');
        data.forEach((obj) => {
            patientDashboardListTableBody.append(
                '<tr name="patientDashboardListTableTr" onclick="modalSelectTr()">'
                + '<td>'
                + '<input class="mt-1" type="radio" name="selectPatientRadio" data-admission="' + obj.admissionId + '">'
                + '</td>'
                + '<td>'
                + obj.name
                + '</td>'
                + '<td>'
                + obj.sexNm
                + '</td>'
                + '<td>'
                + obj.age
                + '</td>'
                + '<td>'
                + obj.locationNm
                + '</td>'
                + '</tr>'
            )
        })

    }
    function patientLoadComplete() {}
})

function getVitalSignGraphData() {
    let dateFrom1 = document.querySelector('#dateFrom')
    let dateTo1 = document.querySelector('#dateTo');
    let admissionId1 = document.querySelector('#admissionId')
    const patientDetailVsDataTableBody = $('#patientDetailVsDataTableBody')

    /**
     * 입소ID          admissionId
     * 측정결과 시작일시 resultFromDt
     * 측정결과 종료일시 resultToDt
     */
    let PatientDetailDashboardResultSearchVO = {
        admissionId: admissionId1.value,
        resultFromDt: dateFrom1.value,
        resultToDt: dateTo1.value,
    };
    console.log(PatientDetailDashboardResultSearchVO)
    $.ajax({
        url: 'patientDetailResultList.ajax',
        data: PatientDetailDashboardResultSearchVO,
        dataType: 'json',
        contentType: 'application/json;charset=UTF-8',
        success: function (data) {
            patientDetailVsDataTableBody.html('')
            document.querySelector("#chart").innerHTML = '';
            if (data.length === 0) {
                notification('V/S 데이터가 없습니다.', 'red', 5000);
                prDataArray   = [];
                btDataArray   = [];
                rrDataArray   = [];
                spo2DataArray = [];
                bpDataArray   = [];
                dataStatus = false;
            }
            else {
                prDataArray   = [];
                btDataArray   = [];
                rrDataArray   = [];
                spo2DataArray = [];
                bpDataArray   = [];
                data.forEach((obj) => {
                    if(obj.sbp !== null) {
                        pushBpData(obj.resultDt, obj.sbp, obj.dbp);   //혈압
                    }
                    else{
                        obj.bp = '-';
                    }
                    if(obj.pr!==null){
                        pushPrData(obj.resultDt, obj.pr);             //맥박
                    }
                    else{
                        obj.pr = '-';
                    }
                    if(obj.bt !== null){
                        pushBtData(obj.resultDt, obj.bt);             //체온
                    }
                    else{
                        obj.bt = '-';
                    }
                    if(obj.rr !== null){
                        pushRrData(obj.resultDt, obj.rr);             //호흡
                    }
                    else{
                        obj.rr = '-';
                    }
                    if(obj.spo2 !== null){
                        pushSpo2Data(obj.resultDt, obj.spo2);         //산소포화도
                    }
                    else{
                        obj.spo2 = '-';
                    }
                    patientDetailVsDataTableBody.append(
                        '<tr>'
                        + '<td>' + obj.resultDt + '</td>'
                        + '<td name="vsTd" data-RiskYn="'+ obj.bpRiskYn +'">' + obj.bp + '</td>'
                        + '<td name="vsTd" data-RiskYn="'+ obj.prRiskYn +'">' + obj.pr + '</td>'
                        + '<td name="vsTd" data-RiskYn="'+ obj.btRiskYn +'">' + obj.bt + '</td>'
                        + '<td name="vsTd" data-RiskYn="'+ obj.rrRiskYn +'">' + obj.rr + '</td>'
                        + '<td name="vsTd" data-RiskYn="'+ obj.spo2RiskYn +'">' + obj.spo2 + '</td>'
                        + '</tr>'
                    )
                })
            }
        },
        error: function () {
            notification('서버오류입니다.', 'red', 5000);
        },
        complete:function () {
            patientLoadComplete();
            updateChartSeriesData();
        }
    });
}

/**
 * 정상치를 넘어갔을때 표시
 */
function patientLoadComplete() {
    let vsTds = document.querySelectorAll('td[name="vsTd"]');
    //V/S 수치를 가지고있는 Div들 전부 검사할려고 루프문 작성
    for (const vsTd of vsTds) {
        //정상치 넘어갔는지 상태 보려고 작성
        if (vsTd.dataset.riskyn !== 'N') {
            vsTd.setAttribute('style','background-color: rgba(255, 105, 105, 0.8);');
        }
    }
}
//검색버튼 이벤트
const patientDetailDataSearchBtn = document.querySelector('#patientDetailDataSearchBtn');
patientDetailDataSearchBtn.addEventListener('click', function () {
    const patientDetailVsDataTableBody = $('#patientDetailVsDataTableBody')
    patientDetailVsDataTableBody.html('')
    getVitalSignGraphData();
})

//모달선택시 하이라이트 이벤트
function modalSelectTr() {
    let patientDashboardListTableTr = document.querySelectorAll('tr[name="patientDashboardListTableTr"]')//모달 생활치료센터 테이블 Tr들
    let selectPatientRadio = document.querySelectorAll('input[name="selectPatientRadio"]') //모달 생활치료센터 라디오 버튼들
    //체크된거 하나이상일시 지금 체크한거를 제외하고 전부다 체크해제 및 모든라인에 하이라이트 삭제
    for (let i = 0; i < selectPatientRadio.length; i++) {
        patientDashboardListTableTr[i].classList.remove('hcTrSelect')
    }
    //선택한 체크박스 체크하고 해당 라인에 하이라이트
    let selectedTr = event.target.closest('tr');
    selectedTr.querySelector('input[name="selectPatientRadio"]').checked = true;
    selectedTr.classList.add('hcTrSelect');
}

//영역 확장 이벤트
const dataDiv = document.querySelector('#dataDiv');
const graphDiv = document.querySelector('#graphDiv');
const dataDivBtn = document.querySelector('#dataDivBtn');
const graphDivBtn = document.querySelector('#graphDivBtn');
let dataDivStatus = true;
//영역 확장시 아이콘 변경 이벤트
function setExtendsIcon(target) {
    if(target.tagName==='BUTTON'){
        target.querySelector('i').classList.toggle('bi-dash');
        target.querySelector('i').classList.toggle('bi-plus');
    }
    else{
        target.classList.toggle('bi-dash');
        target.classList.toggle('bi-plus');
    }
}
//테이블 영역 확장이벤트
dataDivBtn.addEventListener('click', function () {
    if(dataDivStatus===true){
        graphDiv.classList.toggle('hcResizeShow');
        dataDiv.classList.toggle('col-lg-6');
        dataDiv.classList.toggle('col-lg-12');
        dataDivStatus=false;
    }
    else{
        dataDiv.classList.toggle('col-lg-6');
        dataDiv.classList.toggle('col-lg-12');
        setTimeout(function () {
            graphDiv.classList.toggle('hcResizeShow');
        },500);
        dataDivStatus=true;
    }
    setExtendsIcon(event.target);

})
//그래프 영역 확장이벤트
graphDivBtn.addEventListener('click', graphDivBtnFunc);
function graphDivBtnFunc() {
    dataDiv.classList.toggle('hcResizeShow');
    graphDiv.classList.toggle('col-lg-6');
    graphDiv.classList.toggle('col-lg-12');
    setExtendsIcon(event.target);
}

//모달에서 환자 선택시 재조회
let selectNewPatientBtn = document.querySelector('#selectNewPatientBtn');
selectNewPatientBtn.addEventListener('click', function () {
    document.querySelector('#admissionId').value = document.querySelector('input[name="selectPatientRadio"]:checked').dataset.admission;
    location.href='/patientDetailDashboard/patientHome.do?admissionId='+document.querySelector('input[name="selectPatientRadio"]:checked').dataset.admission;
})

let patientInfoCardDiv = document.querySelector('#patientInfoCardDiv');
//모달에 선택된거 체크되어서 나오게 하는 이벤트
patientInfoCardDiv.addEventListener('click',function () {
    let admissionIdValue = document.querySelector('#admissionId').value;
    let patientDashboardListTableTr = document.querySelectorAll('tr[name="patientDashboardListTableTr"]')//모달 생활치료센터 테이블 Tr들
    let selectPatientRadio = document.querySelectorAll('input[name="selectPatientRadio"]') //모달 생활치료센터 라디오 버튼들
    //체크된거 하나이상일시 지금 체크한거를 제외하고 전부다 체크해제 및 모든라인에 하이라이트 삭제
    for (let i = 0; i < selectPatientRadio.length; i++) {
        patientDashboardListTableTr[i].classList.remove('hcTrSelect')
        if(selectPatientRadio[i].dataset.admission===admissionIdValue){
            //선택한 체크박스 체크하고 해당 라인에 하이라이트
            let selectedTr = selectPatientRadio[i].closest('tr');
            selectedTr.querySelector('input[name="selectPatientRadio"]').checked = true;
            selectedTr.classList.add('hcTrSelect');
        }
    }
})
