<%--
  User: wooyoung
  Date: 2021-09-01
  Time: 오후 3:31
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/header.jsp" %>
<!--Main Start-->
<main id="main-pd" class="main-nonePd body-pd">
    <div class="container-fluid">
        <div class="row">
            <!--                <section class="col-lg-7 table-responsive mb-3" style="height: 48.4vw">-->
            <section class="col-lg-7 table-responsive mb-3" style="height: calc(100vh - 80px)">
                <table class="table">
                    <thead>
                    <tr>
                        <th>장비 ID</th>
                        <th>장비 명칭</th>
                        <th>생활치료센터</th>
                        <th>리마크</th>
                    </tr>
                    </thead>
                    <tbody id="equipTableBody">
                    </tbody>
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
                                <div class="col-lg-4">장비 ID</div>
                                <div class="col-lg-8"><input type="text" id="equipmentIdInput" name="equipId" class="form-control" onkeyup="equipmentIdInputKeyUpFunc();checkMaxByte(this,100);"/></div>
                                <div class="col-lg-4">장비 명칭</div>
                                <div class="col-lg-8"><input type="text" id="equipmentNameInput" name="equipNm" class="form-control" onkeyup="equipmentNameInputKeyUpFunc();checkMaxByte(this,100);"/></div>
                                <div class="col-lg-4">생활치료센터</div>
                                <div class="col-lg-8">
                                    <div class="input-group">
                                        <input type="text" id="userTreatmentCenterIdInput" name="centerId" data-centerId="" class="form-control" readonly/>
                                        <button type="button" id="treatmentCenterModalBtn" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#treatmentCenterModal"><i class="bi bi-card-list"></i></button>
                                    </div>
                                </div>
                                <div class="col-lg-4">리마크</div>
                                <div class="col-lg-8"><textarea type="text" id="equipmentRemark" name="remark" class="form-control"></textarea></div>
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
                        <th>생활치료센터 ID</th>
                        <th>치료센터명</th>
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
<script>

    // input 문자열 제한
    function checkMaxByte(obj, maxLength) {
        if (getStringByte(obj.value) > maxLength) {
            obj.value = getByteSubstr(obj.value, maxLength);
        }
    }

    /**
     * 장비Id 빈칸인지 체크
     */
    function equipmentIdInputKeyUpFunc() {
        const equipmentIdInput = document.querySelector('#equipmentIdInput');
        if (equipmentIdInput.value===''){
            customValidation(equipmentIdInput.id,'빈칸입니다.','false');
        }

        else{
            customValidation(equipmentIdInput.id,'','true');
        }
    }

    /**
     * 장비명 빈칸인지 체크
     */
    function equipmentNameInputKeyUpFunc() {
        const equipmentNameInput = document.querySelector('#equipmentNameInput');
        if (equipmentNameInput.value===''){
            customValidation(equipmentNameInput.id,'빈칸입니다.','false');
        }

        else{
            customValidation(equipmentNameInput.id,'','true');
        }
    }

    /**
     * 빈칸체크들 모아놓은거 저장이나 삭제시 빈칸있는지볼려고 작성
     */
    function validationSeries() {
        equipmentIdInputKeyUpFunc();
        equipmentNameInputKeyUpFunc();
    }
    //테이블 선택이벤트
    const equipmentIdInput        = document.querySelector('#equipmentIdInput') // 장비Id input
    const equipmentNameInput      = document.querySelector('#equipmentNameInput') // 장비명 input
    const userTreatmentCenterIdInput  = document.querySelector('#userTreatmentCenterIdInput')// 생활치료센터
    const equipmentRemark      = document.querySelector('#equipmentRemark') // 리마크

    /**
     * 테이블 선택이벤트
     */
    function selectEquipTr() {
        let equipmentTableBodyTrs   = document.querySelectorAll('tr[name="equipmentTableBodyTr"]') //장비 테이블 Tr들

        //이전에 선택된거 하이라이트 지울려고 작성
        for (let i = 0; i < equipmentTableBodyTrs.length; i++) {
            equipmentTableBodyTrs[i].classList.remove('hcTrSelect')
        }

        let targetTr=event.target.closest('tr');//선택된 Tr

        targetTr.classList.add('hcTrSelect')
        equipmentIdInput.value                      =targetTr.querySelector('td[name="equipmentId"]').innerText;
        equipmentNameInput.value                    =targetTr.querySelector('td[name="equipmentName"]').innerText;
        userTreatmentCenterIdInput.value            =targetTr.querySelector('td[name="equipmentCenter"]').innerText;
        userTreatmentCenterIdInput.dataset.centerid =targetTr.querySelector('td[name="equipmentCenter"]').dataset.centerid;
        equipmentRemark.value                       =targetTr.querySelector('td[name="equipmentRemark"]').innerText;
        equipmentIdInput.readOnly=true;
        validationSeries();
    };
    //모달 이벤트
    let treatmentCenterNameInputValue;//센터명 저장해놓을려고 작성
    let treatmentCenterIdInputValue; //센터 Id 저장해놓을려고 작성
    /**
     *모달 테이블 선택이벤트
     */
    function modalSelectTr(){
        let treatmentCenterTableBodyTrs   = document.querySelectorAll('tr[name="treatmentCenterTableBodyTr"]')//모달 테이블Tr들
        let treatmentCenterCheckBoxs   = document.querySelectorAll('input[name="treatmentCenterSelected"]')//체크박스들
        //체크된거 하나이상일시 지금 체크한거를 제외하고 전부다 체크해제 및 모든라인에 하이라이트 삭제
        for (let i = 0; i < treatmentCenterCheckBoxs.length; i++) {
            treatmentCenterTableBodyTrs[i].classList.remove('hcTrSelect')
        }
        //선택한 체크박스 체크하고 해당 라인에 하이라이트
        event.target.checked=true;
        let selectedTr = event.target.closest('tr');//선택한Tr
        selectedTr.classList.add('hcTrSelect')
        //선택한 라인의 센터Id저장
        treatmentCenterIdInputValue=selectedTr.querySelector('td[name="treatmentCenterId"]').innerText;
        treatmentCenterNameInputValue=selectedTr.querySelector('td[name="treatmentCenterName"]').innerText;
    }

    const treatmentCenterBtn      = document.querySelector('#treatmentCenterBtn')//모달선택버튼
    //모달에서 체크하고 선택하면 센터Id입력
    treatmentCenterBtn.addEventListener('click',function () {
        userTreatmentCenterIdInput.value=treatmentCenterNameInputValue;
        userTreatmentCenterIdInput.dataset.centerid=treatmentCenterIdInputValue;
        validationSeries();
    });
    (function () {
        /**
         * 장비목록&생활치료센터 목록 조회
         */
        window.addEventListener('load',function () {
            commonAjax('list.ajax',null,selectEquipmentList,selectEquipmentComplete)
        })

        /**
         * 장비목록&생활치료센터 목록 조회성공시 호출
         * @param data 장비목록 & 생활치료센터 목록
         */
        function selectEquipmentList(data) {
            const equipTableBody = $('#equipTableBody')
            //장비테이블 리스트 생성
            equipTableBody.html('');
            data.resultList.forEach((obj,idx)=>{
                if(obj.remark===null&&obj.remark==='null'){
                    obj.remark='';
                }
                equipTableBody.append(
                    '<tr name="equipmentTableBodyTr"onclick="selectEquipTr()">'
                    +'<td name="equipmentId" >'+ obj.equipId +'</td>'
                    +'<td name="equipmentName">'+ obj.equipNm +'</td>'
                    +'<td name="equipmentCenter" data-centerId="'+obj.centerId+'">'+ obj.centerNm +'</td>'
                    +'<td name="equipmentRemark">'+ obj.remark +'</td>'
                    +'</tr>'
                )
            })
            //생활치료센터 모달 리스트 생성
            const treatmentCenterModalTableBody = $('#treatmentCenterModalTableBody')
            treatmentCenterModalTableBody.html('');
            data.modalResultList.forEach((obj,idx)=>{
                treatmentCenterModalTableBody.append(
                    '<tr name="treatmentCenterTableBodyTr" >'
                    +'<td class="text-center" ><input type="radio" class="text-center mt-1" name="treatmentCenterSelected" onclick="modalSelectTr()"></td>'
                    +'<td name="treatmentCenterId" >'+ obj.centerId +'</td>'
                    +'<td name="treatmentCenterName">'+ obj.centerNm +'</td>'
                    +'<td name="treatmentCenterPosition">'+ obj.centerLocation +'</td>'
                    +'<td name="treatmentCenterInfo">'+ obj.hospitalNm +'</td>'
                    +'</tr>'
                )
            })
        }
        function selectEquipmentComplete() {

        }
        //환자 선택된거 모달에서 선택되어서 나오게
        const treatmentCenterModalBtn =document.querySelector('#treatmentCenterModalBtn');
        treatmentCenterModalBtn.addEventListener('click',function () {
            let treatmentCenterModal = document.querySelector('#treatmentCenterModal').querySelectorAll('td[name="treatmentCenterName"]')
            for (let i = 0; i < treatmentCenterModal.length; i++) {
                if(userTreatmentCenterIdInput.value === treatmentCenterModal[i].outerText){
                    treatmentCenterModal[i].previousElementSibling.previousElementSibling.querySelector('input[type="radio"]').click()
                }
            }
        })

        //신규버튼 카드 초기화
        const newRowBtn =document.querySelector('#newRowBtn');
        newRowBtn.addEventListener('click',function () {
            equipmentIdInput.value                      ='';
            equipmentNameInput.value                    ='';
            userTreatmentCenterIdInput.value            ='';
            equipmentRemark.value                       ='';
            let equipmentTableBodyTrs   = document.querySelectorAll('tr[name="equipmentTableBodyTr"]') //장비 테이블 Tr들
            //이전에 선택된거 하이라이트 지울려고 작성
            for (let i = 0; i < equipmentTableBodyTrs.length; i++) {
                equipmentTableBodyTrs[i].classList.remove('hcTrSelect')
            }
            equipmentIdInput.readOnly=false;
            let clearValids = document.querySelectorAll('.is-valid')
            for (const clearValid of clearValids) {
                clearValid.classList.remove('is-valid');
            }
            // validationSeries();
        })

        //저장버튼
        /**
         * 입력저장 이벤트
         */
        const saveBtn = document.querySelector('#saveBtn');
        saveBtn.addEventListener('click',function () {
            let saveConfirm = confirm('저장하시겠습니까?')
            if(saveConfirm) {
                //빈칸체크
                validationSeries();
                if (document.querySelectorAll('.is-invalid').length > 0) {
                    alert(document.querySelectorAll('.is-invalid')[0].closest('div').previousElementSibling.innerText + document.querySelectorAll('.is-invalid')[0].nextElementSibling.innerText)
                    return;
                }
                //장비데이터
                let equipVo = {
                    equipId: equipmentIdInput.value,
                    equipNm: equipmentNameInput.value,
                    centerId: userTreatmentCenterIdInput.dataset.centerid,
                    remark: equipmentRemark.value,
                    // regId : 'dev',
                    // updId:  'dev'
                }
                if(equipmentIdInput.readOnly){
                    $.ajax({
                        type:'POST',
                        url:'save.ajax',
                        data:JSON.stringify(equipVo),
                        dataType: 'json',
                        contentType:'application/json;charset=UTF-8',
                        success: function (data) {
                            notification('수정되었습니다.','green',2000)
                            const equipTableBody = $('#equipTableBody')
                            //장비테이블 리스트 생성
                            equipTableBody.html('');
                            data.forEach((obj,idx)=>{
                                if(obj.remark===null&&obj.remark==='null'){
                                    obj.remark='';
                                }
                                equipTableBody.append(
                                    '<tr name="equipmentTableBodyTr"onclick="selectEquipTr()">'
                                    +'<td name="equipmentId" >'+ obj.equipId +'</td>'
                                    +'<td name="equipmentName">'+ obj.equipNm +'</td>'
                                    +'<td name="equipmentCenter" data-centerId="'+obj.centerId+'">'+ obj.centerNm +'</td>'
                                    +'<td name="equipmentRemark">'+ obj.remark +'</td>'
                                    +'</tr>'
                                    )
                            })
                        },
                        error:function () {
                            alert('저장실패!');
                        },
                    })
                }
                else{
                    $.ajax({
                        url:'duplicateCheck.ajax',
                        data:{"equipmentId":equipmentIdInput.value},
                        dataType: 'json',
                        contentType:'application/json;charset=UTF-8',
                        success:function (data) {
                            console.log('data:::',data)
                            if(data===0){
                                $.ajax({
                                    type:'POST',
                                    url:'save.ajax',
                                    data:JSON.stringify(equipVo),
                                    dataType: 'json',
                                    contentType:'application/json;charset=UTF-8',
                                    success: function (data) {
                                        notification('생성되었습니다.','green',2000)
                                        const equipTableBody = $('#equipTableBody')
                                        //장비테이블 리스트 생성
                                        equipTableBody.html('');
                                        data.forEach((obj,idx)=>{
                                            if(obj.remark===null&&obj.remark==='null'){
                                                obj.remark='';
                                            }
                                            equipTableBody.append(
                                                '<tr name="equipmentTableBodyTr"onclick="selectEquipTr()">'
                                                +'<td name="equipmentId" >'+ obj.equipId +'</td>'
                                                +'<td name="equipmentName">'+ obj.equipNm +'</td>'
                                                +'<td name="equipmentCenter" data-centerId="'+obj.centerId+'">'+ obj.centerNm +'</td>'
                                                +'<td name="equipmentRemark">'+ obj.remark +'</td>'
                                                +'</tr>'
                                            )
                                        })
                                    },
                                    error:function () {
                                        alert('저장실패!');
                                    },
                                })
                            }
                            else{
                                notification('중복된 장비아이디가 있습니다.','red',2000)
                            }
                            return;
                        },
                        error:function () {
                            notification('조회실패!','red',2000)
                        }
                    })
                }
            }
        })
        //삭제버튼
        const deleteBtn = document.querySelector('#deleteBtn');
        //삭제버튼 이벤트
        deleteBtn.addEventListener('click',function () {
            let deleteConfirm = confirm('삭제하시겠습니까?')
            if(deleteConfirm){
                if(equipmentIdInput.value===''){
                    notification('삭제할 항목을 선택해주세요.','red','2000');
                    return
                }
                else{
                    commonAjax('delete.ajax',{"equipId":equipmentIdInput.value},deleteEquip,deleteEquipComplete)
                }
            }

        })

        /**
         * 삭제성공시 호출 함수
         * @param data 장비목록
         */
        function deleteEquip(data) {
            notification('삭제되었습니다.','green',2000)
            const equipTableBody = $('#equipTableBody')
            //장비테이블 리스트 생성
            equipTableBody.html('');
            data.forEach((obj,idx)=>{
                if(obj.remark===null&&obj.remark==='null'){
                    obj.remark='';
                }
                equipTableBody.append(
                    '<tr name="equipmentTableBodyTr"onclick="selectEquipTr()">'
                    +'<td name="equipmentId" >'+ obj.equipId +'</td>'
                    +'<td name="equipmentName">'+ obj.equipNm +'</td>'
                    +'<td name="equipmentCenter" data-centerId="'+obj.centerId+'">'+ obj.centerNm +'</td>'
                    +'<td name="equipmentRemark">'+ obj.remark +'</td>'
                    +'</tr>'
                )
            })
            equipmentIdInput.value                      ='';
            equipmentNameInput.value                    ='';
            userTreatmentCenterIdInput.value            ='';
            equipmentRemark.value                       ='';
        }
        function deleteEquipComplete() {
        }
    })()

    /**
     * 헤더에 현재메뉴 표시
     */
    setSelectMenuName('장비 관리',0,3);
</script>
</body>
</html>