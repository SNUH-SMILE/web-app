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
                <table class="table" >
                    <thead>
                    <tr>
                        <th>측정항목 ID</th>
                        <th>측정항목 명칭</th>
                        <th>단위</th>
                    </tr>
                    </thead>
                    <tbody id="measuresTableBody">
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
                            <div class="row g-3 centerDisplay ">
                                <div class="col-lg-4">측정항목 ID</div>
                                <div class="col-lg-8"><input type="text" id="measuresIdInput" name="itemId" class="form-control" readonly/></div>
                                <div class="col-lg-4">측정항목 명칭</div>
                                <div class="col-lg-8"><input type="text" id="measuresNameInput" name="itemNm" class="form-control" onkeyup="measuresNameInputKeyUpFunc();checkMaxByte(this,50)"/></div>
                                <div class="col-lg-4">측정항목 단위</div>
                                <div class="col-lg-8"><input type="text" id="measuresUnitInput" name="unit"class="form-control"/></div>
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

    const measuresIdInput        = document.querySelector('#measuresIdInput')  //측정항목ID Input
    const measuresNameInput      = document.querySelector('#measuresNameInput')//측정항목명 Input
    const measuresUnitInput      = document.querySelector('#measuresUnitInput')//측정항목 단위 Input
    //테이블 선택이벤트
    function selectItemTr() {
        let measuresTableBodyTrs   = document.querySelectorAll('tr[name="measuresTableBodyTr"]')//측정항목테이블 Tr전부다

        //선택 바뀔때 이전에 선택된거 하이라이트 제거할려고 반복문 작성
        for (let i = 0; i < measuresTableBodyTrs.length; i++) {
            measuresTableBodyTrs[i].classList.remove('hcTrSelect')
        }

        let targetTr=event.target.closest('tr');//선택한 Tr
        targetTr.classList.add('hcTrSelect')
        measuresIdInput.value    =targetTr.querySelector('td[name="measuresId"]').innerText;
        measuresNameInput.value  =targetTr.querySelector('td[name="measuresName"]').innerText;
        measuresUnitInput.value     =targetTr.querySelector('td[name="measuresUnit"]').innerText;
        measuresNameInputKeyUpFunc();
    }

    /**
     * 측정항목명 빈칸인지 체크
     */
    function measuresNameInputKeyUpFunc() {
        if (measuresNameInput.value===''){
            customValidation(measuresNameInput.id,'측정항목 명칭을 입력해주세요.','false');
        }

        else{
            customValidation(measuresNameInput.id,'','true');
        }
    }

    (function () {
        //측정항목 리스트 로드
        window.addEventListener('load',function () {
            commonAjax('list.ajax',null,selectItemList,selectItemComplete)
        })

        /**
         * 측정항목 조회 성공시 실행되는 함수
         * @param data 측정항목 목록
         */
        function selectItemList(data) {
            const measuresTableBody = $('#measuresTableBody')
            measuresTableBody.html('');
            data.forEach((obj,idx)=>{
                measuresTableBody.append(
                    '<tr name="measuresTableBodyTr" onclick="selectItemTr()">'
                    +'<td name="measuresId" >'+ obj.itemId +'</td>'
                    +'<td name="measuresName">'+ obj.itemNm +'</td>'
                    +'<td name="measuresUnit">'+ obj.unit +'</td>'
                    +'</tr>'
                )
            })
        }
        function selectItemComplete() {

        }

        /**
         * Input 값 비우는 함수
         */
        function newRowFunc() {
            measuresIdInput.value = '';
            measuresNameInput.value = '';
            measuresUnitInput.value = '';
            let clearValids = document.querySelectorAll('.is-valid')
            for (const clearValid of clearValids) {
                clearValid.classList.remove('is-valid');
            }
            // measuresNameInputKeyUpFunc();
        }
        //신규 버튼 함수
        const newRowBtn = document.querySelector('#newRowBtn');
        newRowBtn.addEventListener('click', function () {
            let measuresTableBodyTrs   = document.querySelectorAll('tr[name="measuresTableBodyTr"]')//측정항목테이블 Tr전부다

            //선택 바뀔때 이전에 선택된거 하이라이트 제거할려고 반복문 작성
            for (let i = 0; i < measuresTableBodyTrs.length; i++) {
                measuresTableBodyTrs[i].classList.remove('hcTrSelect')
            }
            newRowFunc()
        });

        //측정항목 저장&수정
        const saveBtn = document.querySelector('#saveBtn');
        saveBtn.addEventListener('click',function () {
            let saveConfirm = confirm('저장하시겠습니까?')
            if(saveConfirm) {
                //빈칸체크
                measuresNameInputKeyUpFunc();
                if (document.querySelectorAll('.is-invalid').length > 0) {
                    alert(document.querySelectorAll('.is-invalid')[0].closest('div').previousElementSibling.innerText + document.querySelectorAll('.is-invalid')[0].nextElementSibling.innerText)
                    return;
                }
                //측정항목 데이터
                let ItemVo = {
                    itemId: measuresIdInput.value,
                    itemNm: measuresNameInput.value,
                    unit: measuresUnitInput.value,
                    // regId : 'dev',
                    // updId:  'dev'
                }

                //입력
                if(measuresIdInput.value === ''){
                    $.ajax({
                        type:'POST',
                        url:'insert.ajax',
                        data:JSON.stringify(ItemVo),
                        dataType: 'json',
                        contentType:'application/json;charset=UTF-8',
                        success: function (data) {
                            notification('저장되었습니다.','green',2000)
                            selectItemList(data)
                        },
                        error:function () {
                            notification('저장실패했습니다.','red',2000)
                        },
                    })
                }
                //수정
                else{
                    $.ajax({
                        type:'POST',
                        url:'update.ajax',
                        data:JSON.stringify(ItemVo),
                        dataType: 'json',
                        contentType:'application/json;charset=UTF-8',
                        success: function (data) {
                            notification('수정되었습니다.','green',2000)
                            selectItemList(data)
                        },
                        error:function () {
                            notification('수정실패했습니다.','red',2000)
                        },
                    })
                }
            }
        })

        //측정항목 삭제
        const deleteBtn = document.querySelector('#deleteBtn');
        deleteBtn.addEventListener('click',function () {
            let deleteConfirm = confirm('삭제하시겠습니까?')
            if(deleteConfirm){
                if(measuresIdInput.value===''){
                    notification('삭제할 항목을 선택해주세요.','red','2000');
                    return
                }
                else{
                    commonAjax('delete.ajax',{"itemId":measuresIdInput.value},deleteTreatmentCenter,deleteTreatmentCenterListComplete)
                }

            }
        })

        /**
         * 삭제성공시 실행할 함수
         * @param data 측정리스트 목록
         */
        function deleteTreatmentCenter(data) {
            notification('삭제되었습니다.','green',2000)
            measuresIdInput.value = '';
            measuresNameInput.value = '';
            measuresUnitInput.value = '';
            selectItemList(data)
        };
        function deleteTreatmentCenterListComplete() {

        };
    })()
    /**
     * 헤더에 현재메뉴 표시
     */
    setSelectMenuName('측정항목 관리',0,4);

</script>
</body>
</html>