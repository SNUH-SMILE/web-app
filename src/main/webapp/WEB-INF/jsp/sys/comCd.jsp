<%--
  User: wooyoung
  Date: 2021-09-01
  Time: 오후 3:31
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/header.jsp" %>
<!--Main Start-->
<!--Main Start-->
<main id="main-pd" class="main-nonePd body-pd">
    <div class="container-fluid">
        <div class="row">
            <!--                <div class="col-lg-8">-->
            <section class=" col-lg-7 mb-3" >
                <div class="row">
                    <!-- 검색 DIV -->
                    <div class="col-lg-12 pt-1 pb-1" style="background-color: #6b7995;border-radius: 5px; font-weight: bold">
                        <div class="row" id="divSearch">
                            <!--공통코드 검색-->
                            <div class="col-lg-4 mb-1">
                                <div class="row">
                                    <div class="col-3 col-lg-3 p-0 d-flex align-items-center justify-content-start justify-content-lg-end">
                                        <label class="form-label m-0 hcSearchTermName">공통코드</label>
                                    </div>
                                    <div class="col-9 col-lg-9">
                                        <input type="text" class="form-control mt-1" name="comCd"/>
                                    </div>
                                </div>
                            </div>
                            <!--공통코드명 검색-->
                            <div class="col-lg-4 mb-1">
                                <div class="row">
                                    <div class="col-3 col-lg-3 p-0 d-flex align-items-center justify-content-start justify-content-lg-end">
                                        <label class="form-label m-0 hcSearchTermName" >공통코드명</label>
                                    </div>
                                    <div class="col-9 col-lg-9">
                                        <input type="text" class="form-control mt-1" name="comCdNm"/>
                                    </div>
                                </div>
                            </div>
                            <!--공통코드 종료코드도 같이보기 -->
                            <div class="col-lg-4 mb-1">
                                <div class="row" style="height: 100%;">
                                    <div class="col-6 col-lg-9 d-flex align-items-center ps-0"  id="comCdUseYn" >
                                        <div class="col-6 col-lg-9 p-0 d-flex align-items-center justify-content-start justify-content-lg-end me-3">
                                            <label class="form-label m-0 hcSearchTermName">종료코드 포함</label>
                                        </div>
                                        <div class="col-3 col-lg-3 d-flex align-items-center">
                                            <input type="checkbox" class="form-check " style="margin-top: 0.125rem" name="useYn"/>
                                        </div>
                                    </div>
                                    <div class="col-6 col-lg-3 ps-0 ps-lg-2 d-flex justify-content-end justify-content-lg-center align-items-center">
                                        <button type="button" class="btn btn-dark" id="btnSearch" style=""><i class="bi bi-search"></i></button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- 검색 DIV -->
                    <!-- 공통코드 테이블 -->
                    <div class="col-lg-12 table-responsive mb-3 mt-1 ps-0 pe-0" style="height: calc(100vh - 170px)">
                        <table class="table" id="comCdTable" style="min-width: 100%">
                            <thead>
                            <tr>
                                <th>공통코드</th>
                                <th>공통코드명</th>
                                <th>사용여부</th>
                                <th>공통코드구분</th>
                            </tr>
                            </thead>
                            <!--(tr>td{코드$}+td{코드명$}+td{Y}+td{sys})*40-->
                            <tbody id="comCdTableBody">
<%--                                <tr name="comCdTr" onclick="selectComCdTr()">--%>
<%--                                    <td name="comCdCode">코드1</td>--%>
<%--                                    <td name="comCdName">코드명1</td>--%>
<%--                                    <td name="comCdUseYn">Y</td>--%>
<%--                                    <td name="comCdDiv">sys</td>--%>
<%--                                </tr>--%>
                            </tbody>
                        </table>
                    </div>
                    <!-- 공통코드 테이블 -->
                </div>
            </section>

            <section class="col-lg-5 p-0 ps-lg-2">
                <div class="card" >
                    <div class="card-header HcCardHeader" style="min-height: 54px">
                        <div>
                            상세정보
                        </div>
                        <div>
                            <button type="button" class="hcBtn1 m-1 p-1" id="btnNew">신규</button>
                            <button type="button" class="hcBtn1 m-1 p-1" id="btnSave">저장</button>
                            <button type="button" class="hcBtn2 m-1 p-1" id="btnDelete">삭제</button>
                        </div>
                    </div>
                    <div class="card-body">
                        <div class="container-fluid">
                            <div class="row g-3 centerDisplay">
                                <div class="col-lg-4">공통코드</div>
                                <div class="col-lg-8 " >
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="comCdCodeInput" name="comCd" onkeyup="checkMaxByte(this,5)" readonly/>
                                        <button class="btn btn-success" id="btnDetail" data-bs-toggle="modal" data-bs-target="#comCdDetailModal" disabled><i class="bi bi-table me-1"></i>상세보기</button>
                                    </div>
                                </div>

                                <div class="col-lg-4 ">공통코드명</div>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" name="comCdNm" id="comCdNameInput" onkeyup="checkMaxByte(this,50)"/>
                                </div>
                                <div class="col-lg-4">공통코드구분</div>
                                <div class="col-lg-8"><input type="text" class="form-control" name="comCdDiv" id="comCdDivInput" onkeyup="checkMaxByte(this,5)" required/></div>
                                <div class="col-lg-4">리마크</div>
                                <div class="col-lg-8"><textarea type="text"  id="comCdRemark" name="remark" class="form-control" rows="15"></textarea></div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>
</main>
<!--Main End-->


<%@ include file="/common/footer.jsp" %>
<!--comCdDetailModal Modal Start-->
<div id="comCdDetailModal" class="modal" tabindex="-1" style="font-size: 15px">
    <div class="modal-dialog modal-xl">
        <div class="modal-content">
            <div class="modal-header fs-5">
                <div >공통코드:&nbsp;&nbsp;</div>
                <div id="selectComCdCodeDiv"></div>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" id="treatmentCenterCloseBtn"></button>
            </div>
            <div class="modal-body g-3">
                <div class="row">
                    <div class="col-lg-8 ">
                        <div class="row">
                            <div class="col-lg-12 pe-0 ps-0" >
                                <!-- 세부코드 검색 -->
                                <div class="col-lg-12 pt-1 pb-1 mb-1" style="background-color: #6b7995;border-radius: 5px; font-weight: bold">
                                    <div class="row" id="searchDiv">
                                        <!--세부코드 검색-->
                                        <div class="col-lg-4 mb-1">
                                            <div class="row">
                                                <div class="col-3 col-lg-4 ps-3 p-lg-0 d-flex align-items-center justify-content-start justify-content-lg-end">
                                                    <label class="form-label m-0">세부코드</label>
                                                </div>
                                                <div class="col-9 col-lg-8">
                                                    <input type="text" name="detailCd" class="form-control"/>
                                                </div>
                                            </div>
                                        </div>
                                        <!--세부코드명 검색-->
                                        <div class="col-lg-4 mb-1">
                                            <div class="row">
                                                <div class="col-3 col-lg-4  ps-3 p-lg-0 d-flex align-items-center justify-content-start justify-content-lg-end">
                                                    <label class="form-label m-0" >세부코드명</label>
                                                </div>
                                                <div class="col-9 col-lg-8">
                                                    <input type="text" name="detailCdNm" class="form-control" />
                                                </div>
                                            </div>
                                        </div>
                                        <!--세부코드 종료코드도 같이보기-->
                                        <div class="col-lg-4 mb-1">
                                            <div class="row" style="height: 100%;">
                                                <div class="col-3 col-lg-6  ps-3 p-lg-0 d-flex align-items-center justify-content-start justify-content-lg-end">
                                                    <label class="form-label m-0" for="comDetailCdUseYn">종료코드 포함</label>
                                                </div>
                                                <div class="col-3 col-lg-2 d-flex align-items-center">
                                                        <input type="checkbox" id="comDetailCdUseYn" name="useYn" class="form-check"/>
                                                </div>
                                                <div class="col-6 col-lg-4 d-flex justify-content-end justify-content-lg-center align-items-center">
                                                    <button type="button" class="btn btn-dark me-2" id="btnDetailSearch"><i class="bi bi-search"></i></button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- 세부코드Table -->
                            <div class="col-lg-12 table-responsive ps-0 pe-0 mt-1 mb-2" style="height:35vw">
                                <table class="table" id="comCdDetailTable">
                                    <thead>
                                    <tr>
                                        <th id="detailCd">세부코드</th>
                                        <th id="detailCdNm">세부코드명</th>
                                        <th id="property1">PROPERTY 1</th>
                                        <th id="property2">PROPERTY 2</th>
                                        <th id="property3">PROPERTY 3</th>
                                        <th id="useYn">사용여부</th>
                                    </tr>
                                    </thead>
                                    <tbody id="comCdDetailTableBody">
<%--                                        <tr name="comCdDetailTr" onclick="selectComCdDetailTr()">--%>
<%--                                            <td name="comCdDetailCode">세부코드@</td>--%>
<%--                                            <td name="comCdDetailName">세부코드명#</td>--%>
<%--                                            <td name="comCdDetailUseYn">N</td>--%>
<%--                                        </tr>--%>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!-- 세부코드 상세DIV -->
                    <div class="col-lg-4 p-0 ps-lg-2">
                        <div class="card">
                            <div class="card-header HcCardHeader" style="height: 100%;">
                                <div>
                                    상세정보
                                </div>
                                <div>
                                    <button type="button" id="btnDetailNew" class="hcBtn1 m-1 p-1">신규</button>
                                    <button type="button" id="btnDetailSave" class="hcBtn1 m-1 p-1" >저장</button>
                                    <button type="button" id="btnDetailDelete" class="hcBtn2 m-1 p-1">삭제</button>
                                </div>
                            </div>
                            <div class="card-body">
                                <div class="container-fluid">
                                    <div class="row g-3 centerDisplay" id="comDetailCdDiv">
                                        <div class="col-lg-4 ">세부코드</div>
                                        <div class="col-lg-8">
                                            <input type="text" id="comCdDetailCodeInput" name="detailCd" class="form-control" onkeyup="checkMaxByte(this,5)" readonly/>
                                        </div>
                                        <div class="col-lg-4">세부코드명</div>
                                        <div class="col-lg-8"><input type="text" id="comCdDetailNameInput" name="detailCdNm" class="form-control" required onkeyup="checkMaxByte(this,20)"/></div>

                                        <div class="col-lg-4">정렬 순서</div>
                                        <div class="col-lg-8"><input type="text" id="comCdDetailSortSeq" name="sortSeq" class="form-control" required onkeyup="checkMaxByte(this,2)"/></div>

                                        <div class="col-lg-4">Property1</div>
                                        <div class="col-lg-8"><input type="text" id="comCdDetailProp1" name="property1" class="form-control" required onkeyup="checkMaxByte(this,50)"/></div>
                                        <div class="col-lg-4">Property2</div>
                                        <div class="col-lg-8"><input type="text" id="comCdDetailProp2" name="property2" class="form-control" required onkeyup="checkMaxByte(this,50)"/></div>
                                        <div class="col-lg-4">Property3</div>
                                        <div class="col-lg-8"><input type="text" id="comCdDetailProp3" name="property3" class="form-control" required onkeyup="checkMaxByte(this,50)"/></div>
                                        <div class="col-lg-4">리마크</div>
                                        <div class="col-lg-8"><textarea type="text"  id="comCdDetailRemark" name="remark" class="form-control" rows="15"></textarea></div>
                                    </div>
                                </div>
                            </div>
                            <!--              <div class="card-footer"></div>-->
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn SnuhBtn" data-bs-dismiss="modal" id="treatmentCenterBtn">선택</button>
            </div>
        </div>
    </div>
</div>
<!--comCdDetailModal Modal End-->

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

<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
    // input 문자열 제한
    function checkMaxByte(obj, maxLength) {
        if (getStringByte(obj.value) > maxLength) {
            obj.value = getByteSubstr(obj.value, maxLength);
        }
    }


    //comCd Element
    const comCdCodeInput        = document.querySelector('#comCdCodeInput');       //공통코드 Input
    const comCdNameInput        = document.querySelector('#comCdNameInput');       //공통코드명 Input
    const comCdDivInput         = document.querySelector('#comCdDivInput');        //공통코드구분
    const comCdRemark           = document.querySelector('#comCdRemark');          //리마크
    const comCdBtnNew           = document.querySelector('#btnNew');               //신규버튼

    //comCdDetail Element
    const selectComCdCodeDiv    = document.querySelector('#selectComCdCodeDiv');    //선택한 공통코드 보여줄 Div
    const comCdDetailCodeInput  = document.querySelector('#comCdDetailCodeInput '); //공통상세코드 Input
    const comCdDetailNameInput  = document.querySelector('#comCdDetailNameInput');  //공통상세코드명 Input
    const comCdDetailSortSeq    = document.querySelector('#comCdDetailSortSeq');    //공통상세코드 정렬순서
    const comCdDetailProp1      = document.querySelector('#comCdDetailProp1');      //공통상세속성1
    const comCdDetailProp2      = document.querySelector('#comCdDetailProp2');      //공통상세속성2
    const comCdDetailProp3      = document.querySelector('#comCdDetailProp3');      //공통상세속성3
    const comCdDetailRemark     = document.querySelector('#comCdDetailRemark');     //공통상세리마크
    const comCdDetailBtnNew     = document.querySelector('#btnDetailNew');          //공통상세신규버튼
    const btnDetail             = document.querySelector('#btnDetail');             //공통상세신규버튼

    //공통코드 코드 검증
    function comNmValidation() {
        if (comCdNameInput.value===""){
            customValidation(comCdNameInput.id,'빈칸입니다.','false');
        }
        else{
            customValidation(comCdNameInput.id,'','true');
        }
    }
    comCdNameInput.addEventListener("keyup",comNmValidation);


    //공통코드구분 검증
    function comCdDivValidation() {
        if (comCdDivInput.value===""){
            customValidation(comCdDivInput.id,'빈칸입니다.','false');
        }
        else{
            customValidation(comCdDivInput.id,'','true');
        }
    }
    comCdDivInput.addEventListener("keyup",comCdDivValidation);

    //세부사항코드 검증
    function detailCdValidation() {
        if (comCdDetailCodeInput.value===""){
            customValidation(comCdDetailCodeInput.id,'빈칸입니다.','false');
        }
        else{
            customValidation(comCdDetailCodeInput.id,'','true');
        }
    }
    comCdDetailCodeInput.addEventListener("keyup",detailCdValidation);

    //세부사항명 검증
    function detailNameValidation() {
        if (comCdDetailNameInput.value===""){
            customValidation(comCdDetailNameInput.id,'빈칸입니다.','false');
        }
        else{
            customValidation(comCdDetailNameInput.id,'','true');
        }
    }
    comCdDetailNameInput.addEventListener("keyup",detailNameValidation);

    //공통코드 신규버튼
    comCdBtnNew.addEventListener('click',function () {
        comCdCodeInput.value                  ='';
        comCdNameInput.value                  ='';
        comCdDivInput.value                   ='';
        comCdRemark.value                     ='';

        // comCdValidationSeries();

        let comCdTr   = document.querySelectorAll('tbody > tr')   //공통코드 Tr
        for (let i = 0; i < comCdTr.length; i++) {
            comCdTr[i].classList.remove('hcTrSelect');
        }
    })

    // //공통상세코드 신규버튼
    comCdDetailBtnNew.addEventListener('click',function () {
        comCdDetailCodeInput.value  ='';
        comCdDetailNameInput.value  ='';
        comCdDetailProp1.value      ='';
        comCdDetailProp2.value      ='';
        comCdDetailProp3.value      ='';
        comCdDetailRemark.value     ='';

        // detailComCdValidationSeries();

        let comCdDetailTr   = document.querySelectorAll('#comCdDetailTable > tbody > tr')   //공통코드 Tr
        for (let i = 0; i < comCdDetailTr.length; i++) {
            comCdDetailTr[i].classList.remove('hcTrSelect');
        }
    })

    //검증하는거 모음집(신규나 저장삭제시 사용)
    function comCdValidationSeries() {
        //공통코드 코드 검증
        comNmValidation();
        //공통코드구분 검증
        comCdDivValidation();

    }
    function detailComCdValidationSeries() {
        //세부사항코드 검증
        detailCdValidation();
        //세부사항명 검증
        detailNameValidation();
    }


    let comCdSortVo;




        // 메인 테이블 양식 (Append 시 사용)
        // const comCdTableBody = $('#comCdTableBody');
        // comCdTableBody.append(
        //     '<tr name="comCdTr" onclick="selectComCdTr()"> ' +
        //     '<td name="comCdCode">'+ obj. +'</td> ' +
        //     '<td name="comCdName">'+ obj. +'</td> ' +
        //     '<td name="comCdUseYn">'+ obj. +'</td> ' +
        //     '<td name="comCdDiv">'+ obj. +'</td> ' +
        //     '</tr>')

        //메인 테이블(공통코드) 선택이벤트
        function selectComCdTr() {

            //종료코드는 상세보기 disable 하기...
            if(event.target.closest('tr').querySelector('td[name="useYn"]').innerText === 'Y'){
                btnDetail.removeAttribute("disabled");
            }
            else{
                btnDetail.setAttribute("disabled", "true");
            }

            let comCdTr   = document.querySelectorAll('tbody > tr');   //공통코드 Tr

            for (let i = 0; i < comCdTr.length; i++) {
                comCdTr[i].classList.remove('hcTrSelect');
            }
            let targetTr=event.target.closest('tr');

            targetTr.classList.add('hcTrSelect');
            comCdCodeInput.value                  =targetTr.querySelector('td[name="comCd"]').innerText;
            comCdNameInput.value                  =targetTr.querySelector('td[name="comCdNm"]').innerText;
            comCdDivInput.value                   =targetTr.querySelector('td[name="comCdDiv"]').innerText;
            comCdRemark.value                     =targetTr.querySelector('td[name="remark"]').innerText === "null"?"N/A":targetTr.querySelector('td[name="remark"]').innerText;
            comCdValidationSeries();
        }

        // 모달 테이블 양식 (Append 시 사용)
        // const comCdDetailTableBody =$('#comCdDetailTableBody')
        // comCdDetailTableBody.append('<tr name="comCdDetailTr" onclick="selectComCdDetailTr()">'         +
        //                             '<td name="comCdCode">'+ obj. +'</td>'        +
        //                             '<td name="comCdDetailCode">'+ obj. +'</td>'  +
        //                             '<td name="comCdDetailName">'+ obj. +'</td>'  +
        //                             '<td name="comCdDetailUseYn">'+ obj. +'</td>' +
        //                             '</tr>')

        //Modal테이블 Tr 선택이벤트
        function selectComCdDetailTr() {
            let comCdDetailTr   = document.querySelectorAll('#comCdDetailTable > tbody > tr');   //공통코드 Tr

            for (let i = 0; i < comCdDetailTr.length; i++) {
                comCdDetailTr[i].classList.remove('hcTrSelect');
            }
            let targetTr=event.target.closest('tr');
            targetTr.classList.add('hcTrSelect');
            comCdDetailCodeInput.value                  =  targetTr.querySelector('td[name="detailCd"]').innerText;
            comCdDetailNameInput.value                  =  targetTr.querySelector('td[name="detailCdNm"]').innerText;
            comCdDetailSortSeq.value                    =  targetTr.querySelector('td[name="sortSeq"]').innerText;
            comCdDetailProp1.value                      =  targetTr.querySelector('td[name="property1"]').innerText;
            comCdDetailProp2.value                      =  targetTr.querySelector('td[name="property2"]').innerText;
            comCdDetailProp3.value                      =  targetTr.querySelector('td[name="property3"]').innerText;
            comCdDetailRemark.value                     =  targetTr.querySelector('td[name="remark"]').innerText;
            detailComCdValidationSeries();
        }

        /**
         * 선택한 공통코드 모달 타이틀로 설정
         */
        function setComCd() {
            selectComCdCodeDiv.innerText=comCdCodeInput.value;
        }


        /**
         * 헤더에 현재메뉴 표시
         */
        setSelectMenuName('공통코드 관리',0,0);

        /**
         * Modal Table Tr Drag & Drop change
         */
        $(function() {
            let startTrNumber;

            $('#comCdDetailTableBody').sortable({
                helper: function(e, tr)
                {
                    if(tr.hasClass('hcTrSelect')===false){
                        e.cancelable;
                    }
                    else{
                        let $originals = tr.children();
                        let $helper = tr.clone();
                        $helper.children().each(function(index)
                        {
                            $(this).width($originals.eq(index).width());
                        });
                        return $helper;
                    }
                },
                update: function(e, tr) {
                    console.log('update');
                    let comCdDetailTableBodyTrs = document.querySelector('#comCdDetailTableBody').querySelectorAll('tr');
                    if(startTrNumber<tr.item.index()){
                        for (let i = startTrNumber; i <= tr.item.index(); i++) {
                            comCdDetailTableBodyTrs[i].setAttribute('data-change','Y');
                            comCdDetailTableBodyTrs[i].setAttribute('data-changeNum',i);
                        }
                    }
                    else{
                        for (let i = tr.item.index(); i <= startTrNumber; i++) {
                            comCdDetailTableBodyTrs[i].setAttribute('data-change','Y');
                            comCdDetailTableBodyTrs[i].setAttribute('data-changeNum',i);
                        }
                    }
                    tr.item[0].removeAttribute('style')
                    console.log('변경된 라인들',document.querySelector('#comCdDetailTableBody').querySelectorAll('tr[data-change="Y"]'));
                    let comCdDetailTableBodyChangeTrs = $('#comCdDetailTableBody tr[data-change="Y"]');
                    console.log(comCdDetailTableBodyChangeTrs)

                    comCdSortVo = comCdDetailTableBodyChangeTrs.map(function() {
                        return {
                            comCd: document.querySelector('#selectComCdCodeDiv').innerText,
                            detailCd: this.closest('tr').querySelector('td[name="detailCd"]').innerText,
                            sortSeq: this.dataset.changenum,
                        }
                    }).get();

                    let changeConfirm = confirm('정렬순서를 변경하시겠습니까?');
                    if(changeConfirm === true){
                        $.ajax({
                            type:'post',
                            url:'/Test/sortNum.ajax',
                            data:JSON.stringify(comCdSortVo),
                            contentType	: "application/json;charset=utf-8;",
                            dataType	: "json",
                            success:function (data) {
                                notification('변경되었습니다.','green',2000);
                            },
                            error:function () {
                                alert('ERROR');
                            },
                            complete:function () {
                                ComCdDetail.search();
                            }
                        })
                    }
                    else{
                        ComCdDetail.search();
                    }

                },
                start: function(e, tr) {
                    console.log('start',tr);
                    startTrNumber=tr.item.index();
                }
            });

        });


</script>

<script src="${pageContext.request.contextPath}/assets/js/tableGenerator.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/comCd.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/comCdDetail.js"></script>

</body>
</html>


