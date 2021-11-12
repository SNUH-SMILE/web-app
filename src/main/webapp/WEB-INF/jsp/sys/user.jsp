<%--
  User: wooyoung
  Date: 2021-09-01
  Time: 오후 3:31
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/header.jsp" %>
<!--Main Start-->
<main id="main-pd" class="main-nonePd body-pd" >
    <div class="container-fluid">
        <div class="row">
            <section class="col-lg-7 table-responsive mb-3" style="height: calc(100vh - 80px)">
                <table class=" table" >
                    <thead>
                    <tr>
                        <th>사용자ID</th>
                        <th>사용자명</th>
                        <th>리마크</th>
                        <th>생활치료센터</th>
                    </tr>
                    </thead>
                    <tbody id="userTableBody">
                </table>
            </section>
            <section class="col-lg-5">
                <div class="card" >
                    <div class="card-header HcCardHeader" >
                        <div>
                            상세정보
                        </div>
                        <div>
                            <button type="button" class="hcBtn1 m-1 p-1" id="newRowBtn">신규</button>
                            <button type="button" class="hcBtn1 m-1 p-1" id="saveBtn">저장</button>
                            <button type="button" class="hcBtn2 m-1 p-1" id="deleteBtn">삭제</button>
                        </div>
                    </div>
                    <div class="card-body">
                        <div class="container-fluid">
                            <div class="row g-3 centerDisplay">
                                <div class="col-lg-4">사용자ID</div>
                                <div class="col-lg-8"><input type="text" id="userIdInput" name="userId" class="form-control" readonly/></div>
                                <div class="col-lg-4">비밀번호</div>
                                <div class="col-lg-8"><input type="password" id="userPassInput" name="password" class="form-control" onkeyup="userPassInputKeyUpFunc();checkMaxByte(this,20)"/></div>
                                <div class="col-lg-4">사용자명</div>
                                <div class="col-lg-8"><input type="text" id="userNameInput" name="userNm" class="form-control"onkeyup="userNameInputKeyUpFunc();checkMaxByte(this,50)"/></div>
                                <div class="col-lg-4">생활치료센터</div>
                                <div class="col-lg-8">
                                    <div class="input-group">
                                        <input type="text" id="userTreatmentCenterIdInput" name="centerId" class="form-control" data-centerid="" onchange="userTreatmentCenterIdInputOnChangeFunc()" readonly/>
                                        <button type="button" id="treatmentCenterModalBtn" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#treatmentCenterModal"><i class="bi bi-card-list"></i></button>
                                    </div>
                                </div>
                                <div class="col-lg-4">리마크</div>
                                <div class="col-lg-8"><textarea type="text"  id="userRemark" name="remark" class="form-control"></textarea></div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>
</main>
<!--Main End-->

<!--treatmentCenter Modal Start-->
<div id="treatmentCenterModal" class="modal" tabindex="-1" >
    <div class="modal-dialog modal-xl">
        <div class="modal-content">
            <div class="modal-header ">
                <div style="color: darkblue">L</div>
                <div style="color: deepskyblue">O</div>
                <div style="color: darkblue">G</div>
                <div style="color: deepskyblue">O</div>
                <div style="font-weight: bold">생활치료센터</div>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" id="treatmentCenterCloseBtn"></button>
            </div>
            <div class="modal-body table-responsive" style="height: 20vw; padding-top: 0;">
                <table class=" table" >
                    <thead>
                    <tr>
                        <th class="text-center">선택</th>
                        <th>센터ID</th>
                        <th>센터명</th>
                        <th>위치</th>
                        <th>병원정보</th>
                    </tr>
                    </thead>
                    <tbody id="treatmentCenterModalTableBody">
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn SnuhBtn" data-bs-dismiss="modal" id="treatmentCenterBtn">선택</button>
            </div>
        </div>
    </div>
</div>
<!--treatmentCenter Modal End-->

<%@ include file="/common/footer.jsp" %>
<script>
    // input 문자열 제한
    function checkMaxByte(obj, maxLength) {
        if (getStringByte(obj.value) > maxLength) {
            obj.value = getByteSubstr(obj.value, maxLength);
        }
    }

    /**
     * 생활치료센터 빈칸 체크
     */
    function userTreatmentCenterIdInputOnChangeFunc() {
        const userTreatmentCenterIdInput = document.querySelector('#userTreatmentCenterIdInput');
        if (userTreatmentCenterIdInput.value===""){
            customValidation(userTreatmentCenterIdInput.id,'빈칸입니다.','false');
        }
        else{
            customValidation(userTreatmentCenterIdInput.id,'','true');
        }
    }

    /**
     * 사용자패스워드 빈칸 체크
     */
    function userPassInputKeyUpFunc() {
        const userPassInput = document.querySelector('#userPassInput');
        if (userPassInput.value===""){
            customValidation(userPassInput.id,'빈칸입니다.','false');
        }
        else{
            customValidation(userPassInput.id,'','true');
        }
    }

    /**
     * 사용자이름 빈칸체크
     */
    function userNameInputKeyUpFunc() {
        const userNameInput =document.querySelector('#userNameInput')
        if (userNameInput.value===''){
            customValidation(userNameInput.id,'빈칸입니다.','false');
        }

        else{
            customValidation(userNameInput.id,'','true');
        }
    }

    /**
     * 저장삭제시 사용할려고 빈칸체크 모음
     */
    function validationSeries() {
        userPassInputKeyUpFunc();
        userNameInputKeyUpFunc();
        userTreatmentCenterIdInputOnChangeFunc();
    }

    //테이블 선택이벤트
    const userTreatmentCenterIdInput      = document.querySelector('#userTreatmentCenterIdInput')//생활치료센터 Input
    const userIdInput        = document.querySelector('#userIdInput') //사용자 Id input
    const userPassInput      = document.querySelector('#userPassInput')//사용자 pw input
    const userNameInput      = document.querySelector('#userNameInput') // 사용자명 input
    const userRemark         = document.querySelector('#userRemark') //리마크

    function selectUserTr() {
        let userInfoTrs   = document.querySelectorAll('tr[name="userInfoTr"]') //사용자 테이블Tr들
        //이전에 선택한거 하이라이트 제거할려고 반복문 작성
        for (let i = 0; i < userInfoTrs.length; i++) {
            userInfoTrs[i].classList.remove('hcTrSelect')
        }
        //선택된 Tr
        let targetTr=event.target.closest('tr');
        targetTr.classList.add('hcTrSelect')
        userIdInput.value                    =targetTr.querySelector('td[name="userId"]').innerText;
        userPassInput.value                  =targetTr.querySelector('td[name="userId"]').dataset.pw;
        userNameInput.value                  =targetTr.querySelector('td[name="userName"]').innerText;
        userRemark.value                     =targetTr.querySelector('td[name="userRemark"]').innerText;
        userTreatmentCenterIdInput.value     =targetTr.querySelector('td[name="userCenterId"]').innerText;
        userTreatmentCenterIdInput.dataset.centerid =targetTr.querySelector('td[name="userCenterId"]').dataset.centerid;
        validationSeries();
    }
    //모달 이벤트
    //체크박스 하나만 선택되고 해당 tr에 하이라이트
    let treatmentCenterNameInputValue;
    let treatmentCenterIdInputValue;
    function modalSelectTr() {
        let treatmentCenterTableBodyTrs   = document.querySelectorAll('tr[name="treatmentCenterTableBodyTr"]')//모달 생활치료센터 테이블 Tr들
        let treatmentCenterCheckBoxs   = document.querySelectorAll('input[name="treatmentCenterSelected"]') //모달 생활치료센터 라디오 버튼들
        //체크된거 하나이상일시 지금 체크한거를 제외하고 전부다 체크해제 및 모든라인에 하이라이트 삭제
        for (let i = 0; i < treatmentCenterCheckBoxs.length; i++) {
            treatmentCenterTableBodyTrs[i].classList.remove('hcTrSelect')
        }
        //선택한 체크박스 체크하고 해당 라인에 하이라이트
        event.target.checked=true;
        let selectedTr = event.target.closest('tr');
        selectedTr.classList.add('hcTrSelect')
        //선택한 라인의 센터Id저장
        treatmentCenterIdInputValue=selectedTr.querySelector('td[name="treatmentCenterId"]').innerText;
        treatmentCenterNameInputValue=selectedTr.querySelector('td[name="treatmentCenterName"]').innerText;

    }

    //모달에서 체크하고 선택하면 센터Id입력
    const treatmentCenterBtn      = document.querySelector('#treatmentCenterBtn')
    treatmentCenterBtn.addEventListener('click',function () {
        userTreatmentCenterIdInput.value=treatmentCenterNameInputValue;
        userTreatmentCenterIdInput.dataset.centerid=treatmentCenterIdInputValue;
        validationSeries();
    })
    //환자 선택된거 모달에서 선택되어서 나오게
    const treatmentCenterModalBtn =document.querySelector('#treatmentCenterModalBtn');
    treatmentCenterModalBtn.addEventListener('click',function () {
        let treatmentCenterModal = document.querySelector('#treatmentCenterModal').querySelectorAll('td[name="treatmentCenterName"]')
        for (let i = 0; i < treatmentCenterModal.length; i++) {
            if(userTreatmentCenterIdInput.value === treatmentCenterModal[i].outerText){
                treatmentCenterModal[i].previousElementSibling.previousElementSibling.querySelector('input[type="radio"]').click()
            }
        }
    });
    (function () {
        /**
         * 유저&생활치료센터 리스트 조회
         */
        window.addEventListener('load',function () {
            commonAjax('list.ajax',null,selectUserList,selectUserComplete)
        })
        function selectUserList(data) {
            const userTableBody = $('#userTableBody')
            userTableBody.html('');
            //사용자 목록 테이블에 추가
            data.resultList.forEach((obj,idx)=>{
                if(obj.remark===null){
                    obj.remark=''
                }
                userTableBody.append(
                    '<tr name="userInfoTr" onclick="selectUserTr()">'
                    +'<td name="userId" data-pw="'+ obj.password +'" >'+ obj.userId +'</td>'
                    +'<td name="userName">'+ obj.userNm +'</td>'
                    +'<td name="userRemark">'+ obj.remark +'</td>'
                    +'<td name="userCenterId" data-centerId="'+obj.centerId+'">'+ obj.centerNm +'</td>'
                    +'</tr>'
                )
            })
            //생활치료센터 목록 테이블에 추가
            const treatmentCenterModalTableBody = $('#treatmentCenterModalTableBody')
            treatmentCenterModalTableBody.html('');
            data.modalResultList.forEach((obj,idx)=>{
                treatmentCenterModalTableBody.append(
                    '<tr name="treatmentCenterTableBodyTr" >'
                    +'<td class="text-center" ><input type="radio" class="text-center mt-1"  name="treatmentCenterSelected" onclick="modalSelectTr()"></td>'
                    +'<td name="treatmentCenterId" >'+ obj.centerId +'</td>'
                    +'<td name="treatmentCenterName">'+ obj.centerNm +'</td>'
                    +'<td name="treatmentCenterPosition">'+ obj.centerLocation +'</td>'
                    +'<td name="treatmentCenterInfo">'+ obj.hospitalNm +'</td>'
                    +'</tr>'
                )
            })
        }
        function selectUserComplete() {

        }

        /**
         * 저장 삭제시 유저목록 다시 그리는 함수
         * @param data 유저목록
         */
        function setTableData(data) {
            const userTableBody = $('#userTableBody')
            userTableBody.html('');
            data.forEach((obj,idx)=>{
                if(obj.remark === null){
                    obj.remark='';
                }
                userTableBody.append(
                    '<tr name="userInfoTr" onclick="selectUserTr()">'
                    +'<td name="userId" data-pw="'+ obj.password +'" >'+ obj.userId +'</td>'
                    +'<td name="userName">'+ obj.userNm +'</td>'
                    +'<td name="userRemark">'+ obj.remark +'</td>'
                    +'<td name="userCenterId" data-centerId="'+obj.centerId+'">'+ obj.centerNm +'</td>'
                    +'</tr>'
                )
            })
        }

        /**
         * 신규버튼 누르면 Input초기화되도록
         */
        const newRowBtn = document.querySelector('#newRowBtn')
        newRowBtn.addEventListener('click',function () {
            userIdInput.value                    ='';
            userPassInput.value                  ='';
            userNameInput.value                  ='';
            userRemark.value                     ='';
            userTreatmentCenterIdInput.value     ='';
            // validationSeries()
            let userInfoTrs   = document.querySelectorAll('tr[name="userInfoTr"]') //사용자 테이블Tr들
            //이전에 선택한거 하이라이트 제거할려고 반복문 작성
            for (let i = 0; i < userInfoTrs.length; i++) {
                userInfoTrs[i].classList.remove('hcTrSelect')
            }
            let clearValids = document.querySelectorAll('.is-valid')
            for (const clearValid of clearValids) {
                clearValid.classList.remove('is-valid');
            }
        })

        /**
         * 저장버튼
         */
        const saveBtn =document.querySelector('#saveBtn');
        saveBtn.addEventListener('click',function () {
            let saveConfirm = confirm('저장하시겠습니까?')
            if(saveConfirm){
                //빈칸체크
                validationSeries();
                if(document.querySelectorAll('.is-invalid').length>0){
                    alert(document.querySelectorAll('.is-invalid')[0].closest('div').previousElementSibling.innerText+document.querySelectorAll('.is-invalid')[0].nextElementSibling.innerText)
                    return;
                }
                //유저 data
                let userVo = {
                    userId: userIdInput.value,
                    password: userPassInput.value,
                    userNm: userNameInput.value,
                    centerId: userTreatmentCenterIdInput.dataset.centerid,
                    remark: userRemark.value,
                    // regId : 'dev',
                    // updId:  'dev'
                }

                //id가 빈칸이면 입력 빈칸이 아니면 수정
                if(userIdInput.value === ''){
                    //입력
                    $.ajax({
                        type:'POST',
                        url:'insert.ajax',
                        data:JSON.stringify(userVo),
                        dataType: 'json',
                        contentType:'application/json;charset=UTF-8',
                        success: function (data) {
                            notification('저장되었습니다.','green',2000);
                            setTableData(data);
                        },
                        error:function () {
                            alert('저장실패!');
                        },
                    })
                }
                //수정
                else{
                    $.ajax({
                        type:'POST',
                        url:'update.ajax',
                        data:JSON.stringify(userVo),
                        dataType: 'json',
                        contentType:'application/json;charset=UTF-8',
                        success: function (data) {
                            console.log(data)
                            notification('수정되었습니다.','green',2000)
                            setTableData(data);
                        },
                        error:function () {
                            alert('수정실패!');
                        },
                    })
                }

            }
        })
        /**
         * 삭제버튼 이벤트
         */
        const deleteBtn = document.querySelector('#deleteBtn');
        deleteBtn.addEventListener('click',function () {
            let userIdInput        = document.querySelector('#userIdInput')
            let deleteConfirm = confirm('삭제하시겠습니까?')
            if(deleteConfirm){
                if(userIdInput.value===''){
                    alert('삭제할 유저를 선택해주세요.')
                    return
                }
                else{
                    commonAjax('delete.ajax',{"userId":userIdInput.value},deleteTreatmentCenter,deleteTreatmentCenterListComplete)
                }

            }
        })

        /**
         * 삭제 성공시 호출하는 함수
         * @param data 유저 목록
         */
        function deleteTreatmentCenter(data) {
            notification('삭제되었습니다.','green',2000)
            const userTreatmentCenterIdInput      = document.querySelector('#userTreatmentCenterIdInput')
            const userIdInput        = document.querySelector('#userIdInput')
            const userPassInput      = document.querySelector('#userPassInput')
            const userNameInput      = document.querySelector('#userNameInput')
            const userRemark         = document.querySelector('#userRemark')
            userIdInput.value                    ='';
            userPassInput.value                  ='';
            userNameInput.value                  ='';
            userRemark.value                     ='';
            userTreatmentCenterIdInput.value     ='';
            setTableData(data);
        }
        function deleteTreatmentCenterListComplete() {
        }


    })()

    /**
     * 헤더에 현재메뉴 표시
     */
    setSelectMenuName('사용자 관리',0,2);
</script>
</body>
</html>


