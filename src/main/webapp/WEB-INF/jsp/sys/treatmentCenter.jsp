<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <!--                <div class="col-lg-8">-->
            <!--                <section class="col-md-12 col-lg-7 table-responsive mb-3" style="height: 91vh">-->
            <section class=" col-lg-7 table-responsive mb-3" style="height: calc(100vh - 80px)" >
                <table class=" table" >
                    <thead>
                    <tr>
                        <th>치료센터 ID</th>
                        <th>치료센터명</th>
                        <th>위치</th>
                        <th>병원</th>
                    </tr>
                    </thead>
                    <tbody id="treatmentCenterTableBody">
                    </tbody>
                </table>
            </section>
            <section class="col-lg-5">
                <div class="card" >
                    <div class="card-header HcCardHeader"  >
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
                            <div class="row g-3 centerDisplay" id="treatmentCenterInfoCardBody">
                                <div class="col-lg-4">생활치료센터 ID</div>
                                <div class="col-lg-8 " >
                                    <input type="text" id="treatmentCenterIdInput" name="centerId" class="form-control" readonly />
                                </div>

                                <div class="col-lg-4 ">치료센터명</div>
                                <div class="col-lg-8">
                                    <input type="text" id="treatmentCenterNameInput" name="centerNm" class="form-control" onkeyup="treatmentCenterNameInputKeyUpFunc();checkMaxByte(this,100)" required/>
                                </div>
                                <div class="col-lg-4">위치</div>
                                <div class="col-lg-8"><input type="text" id="treatmentCenterPositionInput"  name="centerLocation" class="form-control" onkeyup="treatmentCenterPositionInputKeyUpFunc();checkMaxByte(this,500)" required/></div>
                                <div class="col-lg-4">병원</div>
                                <div class="col-lg-8">
                                    <select type="text" id="treatmentCenterInfoInput" name="hospitalCd" class="form-select" onfocusout="treatmentCenterInfoInputFocusOutFunc()">
                                        <option value="default">선택</option>
                                        <c:forEach items="${voComCdList}" var="vo">
                                            <option value=${vo.detailCd}>${vo.detailCdNm}</option>
                                        </c:forEach>
                                    </select>
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
<%@ include file="/common/footer.jsp" %>
<script>
    // input 문자열 제한
    function checkMaxByte(obj, maxLength) {
        if (getStringByte(obj.value) > maxLength) {
            obj.value = getByteSubstr(obj.value, maxLength);
        }
    }

    //센터명 검증
    function treatmentCenterNameInputKeyUpFunc() {
        const treatmentCenterNameInput = document.querySelector('#treatmentCenterNameInput');
        if (treatmentCenterNameInput.value===''){
            customValidation(treatmentCenterNameInput.id,'값이없습니다.','false');
        }

        else{
            customValidation(treatmentCenterNameInput.id,'','true');
        }
    }
    //센터위치 검증
    function treatmentCenterPositionInputKeyUpFunc() {
        const treatmentCenterPositionInput = document.querySelector('#treatmentCenterPositionInput');
        if (treatmentCenterPositionInput.value===''){
            customValidation(treatmentCenterPositionInput.id,'값이 없습니다.','false');
        }

        else{
            customValidation(treatmentCenterPositionInput.id,'','true');
        }
    }
    //병원 검증
    function treatmentCenterInfoInputFocusOutFunc() {
        const treatmentCenterInfoInput = document.querySelector('#treatmentCenterInfoInput');
        if (treatmentCenterInfoInput.value==='default'){
            customValidation(treatmentCenterInfoInput.id,'값을 선택해주세요.','false');
            return
        }
        else{
            customValidation(treatmentCenterInfoInput.id,'','true');
        }
    }
    //검증 모음
    function validationSeries() {
        treatmentCenterNameInputKeyUpFunc();
        treatmentCenterPositionInputKeyUpFunc();
        treatmentCenterInfoInputFocusOutFunc();
    }



    const treatmentCenterIdInput        = document.querySelector('#treatmentCenterIdInput')
    const treatmentCenterNameInput      = document.querySelector('#treatmentCenterNameInput')
    const treatmentCenterPositionInput  = document.querySelector('#treatmentCenterPositionInput')
    const treatmentCenterInfoInput      = document.querySelector('#treatmentCenterInfoInput')
    //tr 누르면 옆에 상세정보에 표시
    function selectTreatmentCenterTr() {
        let treatmentCenterTableBodyTrs   = document.querySelectorAll('tr[name="treatmentCenterTableBodyTr"]')
        for (let i = 0; i < treatmentCenterTableBodyTrs.length; i++) {
            treatmentCenterTableBodyTrs[i].classList.remove('hcTrSelect')
        }
        let targetTr=event.target.closest('tr');
        targetTr.classList.add('hcTrSelect')
        treatmentCenterIdInput.value       =targetTr.querySelector('td[name="treatmentCenterId"]').innerText;
        treatmentCenterNameInput.value     =targetTr.querySelector('td[name="treatmentCenterName"]').innerText;
        treatmentCenterPositionInput.value =targetTr.querySelector('td[name="treatmentCenterPosition"]').innerText;
        treatmentCenterInfoInput.value     =targetTr.querySelector('td[name="treatmentCenterInfo"]').innerText;

        for (let i = 0; i < treatmentCenterInfoInput.querySelectorAll('option').length; i++) {
            if(targetTr.querySelector('td[name="treatmentCenterInfo"]').innerText===treatmentCenterInfoInput.querySelectorAll('option')[i].innerText){
                treatmentCenterInfoInput.querySelectorAll('option')[i].selected=true;
            }
        }

        validationSeries();
    }


    (function () {



        //생활치료센터 List 불러오기
        window.addEventListener('load',function () {
            commonAjax('list.ajax',null,selectTreatmentCenterList,selectTreatmentCenterListComplete)
        })
        function selectTreatmentCenterList(data) {
            const treatmentCenterTableBody = $('#treatmentCenterTableBody');
            treatmentCenterTableBody.html('');
            data.forEach((obj,idx)=>{
                treatmentCenterTableBody.append(
                    '<tr name="treatmentCenterTableBodyTr" onclick="selectTreatmentCenterTr()">'
                    +'<td name="treatmentCenterId" >'+ obj.centerId +'</td>'
                    +'<td name="treatmentCenterName">'+ obj.centerNm +'</td>'
                    +'<td name="treatmentCenterPosition">'+ obj.centerLocation +'</td>'
                    +'<td name="treatmentCenterInfo">'+ obj.hospitalNm +'</td>'
                    +'</tr>'
                )
            })
        };
        function selectTreatmentCenterListComplete(){

        }

        //신규 버튼 누르면 Input&Selct 빈칸으로 세팅
        function newRowFunc() {
            treatmentCenterIdInput.value=''
            treatmentCenterNameInput.value=''
            treatmentCenterPositionInput.value=''
            treatmentCenterInfoInput.querySelectorAll('option')[0].selected=true;
            let clearValids = document.querySelectorAll('.is-valid')
            for (const clearValid of clearValids) {
                clearValid.classList.remove('is-valid');
            }
            // validationSeries();
        }
        const newRowBtn = document.querySelector('#newRowBtn');
        newRowBtn.addEventListener('click',function () {
            let treatmentCenterTableBodyTrs   = document.querySelectorAll('tr[name="treatmentCenterTableBodyTr"]')
            for (let i = 0; i < treatmentCenterTableBodyTrs.length; i++) {
                treatmentCenterTableBodyTrs[i].classList.remove('hcTrSelect')
            }
            newRowFunc()
        })
        //저장 & 업데이트
        const saveBtn = document.querySelector('#saveBtn');
        saveBtn.addEventListener('click',function () {
            let treatmentCenterInfo = document.querySelector('#treatmentCenterInfoCardBody')
            let centerIdValue =treatmentCenterInfo.querySelector('#treatmentCenterIdInput').value
            let saveConfirm = confirm('저장 하시겠습니까?');
            if(saveConfirm){
                validationSeries();
                if(document.querySelectorAll('.is-invalid').length>0){
                    alert(document.querySelectorAll('.is-invalid')[0].closest('div').previousElementSibling.innerText+'형식이 맞지 않습니다.')
                    return;
                }
                //신규
                let treatmentCenterVO= {
                    centerId: treatmentCenterInfo.querySelector('#treatmentCenterIdInput').value,
                    centerNm: treatmentCenterInfo.querySelector('#treatmentCenterNameInput').value,
                    centerLocation: treatmentCenterInfo.querySelector('#treatmentCenterPositionInput').value,
                    hospitalCd: treatmentCenterInfo.querySelector('#treatmentCenterInfoInput').value,
                    // regId : 'dev',
                    // updId:  'dev'
                };
                if(centerIdValue===''){

                    $.ajax({
                        type:'POST',
                        url:'insert.ajax',
                        data:JSON.stringify(treatmentCenterVO),
                        dataType: 'json',
                        contentType:'application/json;charset=UTF-8',
                        success: function (data) {
                            notification('저장되었습니다.','green',2000)
                            console.log('insert',data)
                            selectTreatmentCenterList(data);
                        },
                        error:function () {
                            notification('저장실패했습니다.','red',2000)
                        },
                    })
                }
                //업데이트
                else{
                    $.ajax({
                        type:'POST',
                        url:'update.ajax',
                        data:JSON.stringify(treatmentCenterVO),
                        dataType: 'json',
                        contentType:'application/json;charset=UTF-8',
                        success: function (data) {
                            console.log('update',data)
                            notification('수정되었습니다.','green',2000)
                            selectTreatmentCenterList(data);
                        },
                        error:function () {
                            notification('수정실패했습니다.','red',2000)
                        },
                    })
                }
            }
        })

        //삭제
        const deleteBtn = document.querySelector('#deleteBtn');
        deleteBtn.addEventListener('click',function () {
            let deleteConfirm = confirm('삭제 하시겠습니까?');
            let treatmentCenterInfo = document.querySelector('#treatmentCenterInfoCardBody')
            let centerIdValue =treatmentCenterInfo.querySelector('#treatmentCenterIdInput').value
            if(deleteConfirm){
                if(treatmentCenterIdInput.value===''){
                    notification('삭제할 항목을 선택해주세요.','red','2000');
                    return
                }
                else{
                    commonAjax('delete.ajax',{"centerId":centerIdValue},deleteTreatmentCenter,deleteTreatmentCenterListComplete)
                }
            }
        })

        function deleteTreatmentCenter(data) {
            notification('삭제되었습니다.','green',2000)
            treatmentCenterIdInput.value=''
            treatmentCenterNameInput.value=''
            treatmentCenterPositionInput.value=''
            treatmentCenterInfoInput.querySelectorAll('option')[0].selected=true;
            selectTreatmentCenterList(data);
        }
        function deleteTreatmentCenterListComplete() {
        }

    })()

    /**
     * 헤더에 현재메뉴 표시
     */
    setSelectMenuName('생활치료센터 관리',0,1);


</script>
</body>
</html>