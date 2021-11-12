//변수 리스트
const $btnDetail = document.querySelector("#btnDetail");
const $btnDetailNew = document.querySelector("#btnDetailNew");
const $btnDetailSave = document.querySelector("#btnDetailSave");
const $btnDetailDelete = document.querySelector("#btnDetailDelete");
const $btnDetailSearch = document.querySelector("#btnDetailSearch");

const $comCdDetailTable = document.querySelector("#comCdDetailTable");
const $searchCdDetailDiv = document.querySelector("#searchDiv");

// 신규/수정 판정
let detailNewYn = false;

//table body object
const tblDetailObj = [
      {  id: "comCd"           , name: "공통코드"       , type: "td"         , boolUseYn: "N"   ,hidden:"true"  }
    , {  id: "detailCd"        , name: "세부코드"       , type: "td"         , boolUseYn: "N"                   }
    , {  id: "detailCdNm"      , name: "세부코드명"     , type: "td"         , boolUseYn: "N"                   }
    , {  id: "sortSeq"         , name: "정렬순서"       , type: "td"         , boolUseYn: "N"   ,hidden:"true"  }
    , {  id: "property1"       , name: "property1"     , type: "td"         , boolUseYn: "N"                   }
    , {  id: "property2"       , name: "property2"    , type: "td"          , boolUseYn: "Y"                   }
    , {  id: "property3"       , name: "property3"    , type: "td"          , boolUseYn: "Y"                   }
    , {  id: "useYn"           , name: "사용여부"       , type: "td"        , boolUseYn: "Y"                    }
    , {  id: "remark"          , name: "remark"       , type: "td"         , boolUseYn: "Y"   ,hidden:"true"   }
    , {  id: "regId"           , name: "등록자"        , type: "td"         , boolUseYn: "N"   ,hidden:"true"  }
    , {  id: "regDt"           , name: "등록일시"       , type: "td"        , boolUseYn: "N"   ,hidden:"true"  }
    , {  id: "updDt"           , name: "수정자"         , type: "td"        , boolUseYn: "N"   ,hidden:"true"  }
    , {  id: "updId"           , name: "수정일시"        , type: "td"       , boolUseYn: "N"   ,hidden:"true"  }
];

/**
 * 공통코드상세 javascript 입니다.
 * functions :
 *              1. init -
 *              2. search - 리스트 검색
 *              3. save - 내용 저장
 *              4. delete - 공통코드상세코드 삭제
 *              5. insert - 생성
 *
 */

let ComCdDetail = {

    /**
     * 상세코드 조회
     */
    search : function() {
        
        let resultData;
        let comCd = selectComCdCodeDiv.innerText;
        const searchTerm = {};

        if(selectComCdCodeDiv.innerText === ""){
            return;
        }

        searchTerm['comCd'] = comCd;

        const searchDiv = $searchCdDetailDiv.querySelectorAll("input");
        searchDiv.forEach((item)=>{
            if(item.type === "checkbox"){
                searchTerm[item.getAttribute("name")] = item.checked;
            }else{
                
                searchTerm[item.getAttribute("name")] = item.value;
            }
        });

        $.ajax({
            url: "/common/commonDetailList.ajax",
            method: "GET",
            data: searchTerm,
            async: false,
            success: (data)=>{
                resultData = data;
            },
            error: (error)=>{
                ComCd.showErrorAlert(error.status, error.responseText);
                // alert('ERROR : \n' + 'Status : ' + error.status + '\nMessage : ' + error.responseText);
            },
            complete: ()=>{
                TableGenerator.tableInit($comCdDetailTable.getAttribute("id"), resultData, tblDetailObj
                    , "selectComCdDetailTr()");
                this.reset(resetEnum.DETAIL);
            }
        });

    },


    /**
     * 상세코드 내용 저장
     */
    save : function() {

        let detailCd
            = document.querySelector('#comCdDetailTableBody > tr[class="hcTrSelect"] td[name="detailCd"]').innerText;

        const param = this.paramInit();
        if(param.detailCd === ''){ComCd.showErrorAlert("","세부코드는 필수 입니다. 다시 시도해주세요."); return;}

        if(param.detailCd !== detailCd) {ComCd.showErrorAlert("", "세부코드는 수정할 수 없습니다."); return;}

        $.ajax({
            url: "/common/commonDetail.ajax",
            method: "PUT",
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(param),
            success: ()=>{
                this.reset(resetEnum.DETAIL);
                ComCd.showSuccessAlert("200", "세부코드 수정 성공!");
            },
            error: (error)=>{
                ComCd.showErrorAlert(error.status, error.responseText);
                // alert('ERROR : \n' + 'Status : ' + error.status + '\nMessage : ' + error.responseText);
            },
            complete: ()=>{ this.search(); }
        });

    },


    /**
     * 상세코드 생성
     */
    create : function(isDuplicate) {
        detailCdValidation();

        if(isDuplicate) { customValidation(comCdNameInput.id,'중복된 코드입니다.','false') }

        const param = this.paramInit();

        $.ajax({
            url: "/common/commonDetail.ajax",
            method: "POST",
            data: param,
            success: ()=>{
                this.reset(resetEnum.DETAIL);
                ComCd.showSuccessAlert("200", "세부코드 신규등록 성공!");
            },
            error: (error)=>{
                ComCd.showErrorAlert(error.status, error.responseText);
                // alert('ERROR : \n' + 'Status : ' + error.status + '\nMessage : ' + error.responseText);
            },
            complete: ()=>{
                this.reset(resetEnum.SEARCH);
                this.search();
            }
        });

    },


    /**
     * 상세코드 삭제
     */
    delete : function() {
        const param = this.paramInit();

        if(param.comCd === '') {ComCd.showErrorAlert("","공통코드는 필수값입니다. 다시 시도해주세요."); return;}
        if(param.detailCd === '') {ComCd.showErrorAlert("","세부코드는 필수값입니다. 다시 시도해주세요."); return;}

        $.ajax({
            url: "/common/commonDetail.ajax?" + "comCd=" + param.comCd + "&detailCd=" + param.detailCd,
            method: "DELETE",
            success: ()=>{
                this.reset(resetEnum.DETAIL);
                ComCd.showSuccessAlert("200", "세부코드 삭제 성공!");
            },
            error: (error)=>{
                ComCd.showErrorAlert(error.status, error.responseText);
                // alert('ERROR : \n' + 'Status : ' + error.status + '\nMessage : ' + error.responseText);
            },
            complete: ()=>{
                this.search();
            }
        });

    },

    /**
     * 중복 세부코드 확인
     * @return 중복 = true, 중복아님 = false
     */
    duplicateCheck : function() {
        let comCd = selectComCdCodeDiv.innerText;
        let detailCd = comCdDetailCodeInput.value;
        let isDuplicate = false;

        $.ajax({
            url: "/common/duplicateDetailCodeCheck.ajax",
            method: "GET",
            data: {"comCd": comCd, "detailCd": detailCd},
            async: false,
            success: (data)=>{
                console.log('dup check data :: ', data);
                if(data.comCd !== undefined && data.comCd !== null) { console.log('dupcheck', data.comCd);isDuplicate = true;}
            },
            error: (error)=>{
                alert('ERROR : \n' + 'Status : ' + error.status + '\nMessage : ' + error.responseText);
            }
        });
        return isDuplicate;
    },

    paramInit : function() {
        detailCdValidation();
        const inputObjects = comCdDetailCodeInput.parentElement.offsetParent.parentNode.querySelectorAll("input, textarea");
        const param = {};

        //임시 사용자 정보 지정 -- TODO remove
        param['regId'] = "dev";
        param['updId'] = "dev";

        //querySelector로 선택된 input, selectbox, 및 textarea 의 name + value 로 param오브젝트 만들기.
        // (key[태그의 name 속성값] : value[실재값])
        inputObjects.forEach((item)=>{
            param[item.getAttribute("name")] = item.value;
        });

        if(selectComCdCodeDiv.innerText === "") { alert('공통코드 값이 없습니다. 다시 시도해주세요.'); return;}
        param['comCd'] = selectComCdCodeDiv.innerText;
        return param;
    },

    //input란 초기화
    reset : function(section) {

        if(section === resetEnum.SEARCH){
            $searchCdDetailDiv.querySelectorAll('input').forEach((item)=>{
                if(item.type === 'checkbox') { item.checked = false; }
                else {item.value=''}
            });
        }
        else if(section === resetEnum.DETAIL){
            const inputObjects
                = comCdDetailCodeInput.parentElement.offsetParent.parentNode.querySelectorAll("input, textarea");
            inputObjects.forEach((item)=>{ item.value = ""; });
            comCdDetailCodeInput.setAttribute('readOnly', 'true');
        }

        let validElements= document.querySelectorAll('.is-valid'); //초록색있는 엘리먼트
        for (const validElement of validElements) {
            validElement.classList.remove('is-valid');
        }
    },

    /**
     * init
     */
    init : function() {
        //eventListener List
        //검색 input에서 ENTER키 치면 검색
        const searchInputTextTypeBoxes = $searchCdDetailDiv.querySelectorAll('input[type="text"]');
        searchInputTextTypeBoxes.forEach((item)=>{
            item.addEventListener('keydown', (e)=>{
                if(13 === e.keyCode) { this.search(); }
            });
        });

        //상세보기 버튼 클릭시
        $btnDetail.addEventListener('click', ()=>{
            if(comCdCodeInput.value === ''){
                ComCd.showErrorAlert("","선택된 공통코드가 없습니다. 선택후 상세버튼을 클릭하세요.");
                return;
            }
            setComCd();
            ComCdDetail.search();
        });

        //저장 버튼 클릭시
        $btnDetailSave.addEventListener('click', ()=>{

            if(detailNewYn === false){
                if(!confirm('정말 수정하시겠습니까?')){return;}
                this.save();
            }
            if(detailNewYn === true){
                let isDuplicate = this.duplicateCheck();
                if(isDuplicate) {
                    ComCd.showErrorAlert("중복 세부코드 에러", "중복된 세부코드입니다. 신규저장 불가합니다.");
                    this.reset(resetEnum.DETAIL);
                    detailNewYn = false;
                    return;
                }
                if(!confirm('정말 저장하시겠습니까?')){return;}
                this.create(isDuplicate);
                detailNewYn = false;
            }
        });

        //신규 버튼 이벤트
        $btnDetailNew.addEventListener('click', ()=>{
            this.reset(resetEnum.DETAIL);
            comCdDetailCodeInput.removeAttribute('readOnly');
            detailNewYn = true;
        });

        //삭제 버튼 이벤트
        $btnDetailDelete.addEventListener('click', ()=>{
            if(!confirm('정말 삭제하시겠습니까?')){return;}
            this.delete();
        });

        //검색
        $btnDetailSearch.addEventListener('click', ()=>{
            this.search();
        });

    }
}

ComCdDetail.init();