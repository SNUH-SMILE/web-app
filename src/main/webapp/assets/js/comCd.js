//변수 리스트

const $btnNew = document.querySelector("#btnNew");
const $btnSave = document.querySelector("#btnSave");
const $btnDelete = document.querySelector("#btnDelete");
const $btnSearch = document.querySelector("#btnSearch");
const $btnAlertClose = document.querySelector("#btnAlertClose");
const $comCdTable = document.querySelector("table");
const $searchDiv = document.querySelector("#divSearch");

//reset ENUM
const resetEnum = {SEARCH:"SEARCH", DETAIL:"DETAIL"};

//table body object
/**
 * 테이블 자동생성 (TableGenerator.js) 에 필요한 Object 입니다.
 * 해당 테이블 thead 에 존재하는 column[name attribute]와 동일한 id를 가지고 있는 데이터를 자동 바인딩 해줌.
 * @param [
 *   id = 물리명
 * , name = 논리명
 * , type = td, checkbox, input 등 element tag 입력
 * , hidden = 페이지에 출력할지 말지]
 *
 * @type {[
 *   {boolUseYn: string, name: string, id: string, type: string}
 * , {boolUseYn: string, name: string, id: string, type: string}
 * , {boolUseYn: string, name: string, id: string, type: string}
 * , {boolUseYn: string, name: string, id: string, type: string}
 * , {hidden: string, boolUseYn: string, name: string, id: string, type: string}]}
 */
const tblObj = [
    // {  id: "select"        , type: "checkbox"   , boolUseYn: "N"  , fnCallback: "fnCallback()" }
      {  id: "comCd"        , name: "공통코드"       , type: "td"         , boolUseYn: "N"                     }
    , {  id: "comCdNm"      , name: "공통코드명"     , type: "td"         , boolUseYn: "N"                     }
    , {  id: "useYn"        , name: "사용여부"       , type: "td"         , boolUseYn: "Y"                     }
    , {  id: "comCdDiv"     , name: "공통코드구분"    , type: "td"         , boolUseYn: "Y"                     }
    , {  id: "remark"       , name: "remark"        , type: "td"         , boolUseYn: "Y"   ,hidden:"true"    }
    , {  id: "regId"        , name: "등록자"         , type: "td"         , boolUseYn: "N"   ,hidden:"true"    }
    , {  id: "regDt"        , name: "등록일시"       , type: "td"         , boolUseYn: "N"    ,hidden:"true"   }
    , {  id: "updDt"        , name: "수정자"         , type: "td"         , boolUseYn: "N"   ,hidden:"true"    }
    , {  id: "updId"        , name: "수정일시"       , type: "td"         , boolUseYn: "N"    ,hidden:"true"   }
]

/**
 * 공통코드 javascript 입니다.
 * functions :
 *              1. init - javascript function 로드, 버튼 이벤트 걸기, 및 초기 공통코드 테이블 로딩
 *              2. search - 공통코드 리스트 검색
 *              3. save - 내용 저장
 *              4. delete - 공통코드 삭제
 *              5. insert - 공통코드 생성
 *
 */

let ComCd = {

    /**
     * 상세코드 조회
     */
    search : function() {
        let resultData;
        const searchTerm = {};

        $searchDiv.querySelectorAll('input').forEach((item)=>{
            
            if(item.type === "checkbox"){
                searchTerm[item.getAttribute("name")] = item.checked;
            }else{
                
                searchTerm[item.getAttribute("name")] = item.value;
            }
        });

        $.ajax({
            url: "/common/commonCodeList.ajax",
            method: "GET",
            data: searchTerm,
            async: false,
            success: (data)=>{
                resultData = data;
            },
            error: (error)=>{
                this.showErrorAlert(error.status, error.responseText);
                // alert('ERROR : \n' + 'Status : ' + error.status + '\nMessage : ' + error.responseText);
            },
            complete: ()=>{
                TableGenerator.tableInit($comCdTable.getAttribute("id"), resultData, tblObj
                    , "selectComCdTr()");
                this.reset(resetEnum.DETAIL);
            }
        });

    },


    /**
     * 상세코드 내용 저장
     */
    save : function() {
        const param = this.paramInit();
        comCdValidationSeries();
        $.ajax({
            url: "/common/commonCode.ajax",
            method: "PUT",
            data: JSON.stringify(param),
            contentType : "application/json",
            success: ()=>{
                this.showSuccessAlert("200", "공통코드 수정 성공!");
            },
            error: (error)=>{
                this.showErrorAlert(error.status, error.responseText);
                // alert("ERROR : \n" + "status : " + error.status + "\nmessage : " + error.responseText);
            },
            complete: ()=>{ this.search(); }
        });
    },

    /**     * 상세코드 생성
     */
    create : function() {
        const param = this.paramInit();

        comCdValidationSeries();

        $.ajax({
            url: "/common/commonCode.ajax",
            method: "POST",
            data: param,
            success: ()=>{
                this.showSuccessAlert("200", "신규 공통코드 생성 성공.");
            },
            error: (error)=>{
                this.showErrorAlert(error.status, error.responseText);
                // alert("ERROR : \n" + "status : " + error.status + "\nmessage : " + error.responseText);
            },
            complete: ()=>{ this.search(); }
        });
    },

    /**
     * 상세코드 삭제
     */
    delete : function() {
        const param = this.paramInit();
        console.log('param.comCd', param);
        if(param.comCd === ''){this.showErrorAlert("","공통코드를 선택 후 다시 시도해주세요."); return;}
        $.ajax({
            url: "/common/commonCode.ajax?" + "comCd=" + param.comCd,
            method: "DELETE",
            success: ()=>{
                this.showSuccessAlert("200", "공통코드 삭제 성공.");
                this.reset();
            },
            error: (error)=>{
                this.showErrorAlert(error.status, error.responseText);
                // alert('ERROR : \n' + 'Status : ' + error.status + '\nMessage : ' + error.responseText);
            },
            complete: ()=>{ this.search(); }
        });

    },

    paramInit : function() {
        comCdValidationSeries();
        const inputObjects = comCdCodeInput.parentElement.offsetParent.parentNode.querySelectorAll("input, textarea");
        const param = {};

        //임시 사용자 정보 지정 -- TODO remove
        param['regId'] = "dev";
        param['updId'] = "dev";

        //querySelector로 선택된 input, selectbox, 및 textarea 의 name + value 로 param오브젝트 만들기.
        // (key[태그의 name 속성값] : value[실재값])
        inputObjects.forEach((item)=>{
            param[item.getAttribute("name")] = item.value;
        });
        return param;
    },

    //input란 초기화
    reset : function(section) {
        if(section === resetEnum.SEARCH){
            $searchDiv.querySelectorAll('input').forEach((item)=>{
                if(item.type === 'checkbox') { item.checked = false; }
                else {item.value=''}
            });
        }
        else if(section === resetEnum.DETAIL){
            const inputObjects = comCdCodeInput.parentElement.offsetParent.parentNode.querySelectorAll("input, textarea");

            inputObjects.forEach((item)=>{
                item.value = "";
            });
            btnDetail.setAttribute("disabled", "true");
        }
        let validElements= document.querySelectorAll('.is-valid'); //초록색있는 엘리먼트
        for (const validElement of validElements) {
            validElement.classList.remove('is-valid');
        }
    },

    /**
     * 에러 발생시 에러 창 보이기
     */
    showErrorAlert : function(status, message) {
        alert('STATUS : ' + status + "\nMESSAGE : " + message);
        // $('.alert').removeClass('alert-success').addClass('alert-danger');
        // document.querySelector('.alert p').innerText = '!! ERROR !!';
        // $('.alert-div .error-status').html('');
        // $('.alert-div .error-message').html('');
        // $('.alert-div .error-status').append('STATUS CODE : ' + status);
        // $('.alert-div .error-message').append('MESSAGE : ' + message);
        // $('.alert-div').show();
    },

    /**
     * 성공 창 보이기
     */
    showSuccessAlert : function(status, message) {

         alert('STATUS : ' + status + "\nMESSAGE : " + message);

        // $('.alert').removeClass('alert-danger').addClass('alert-success');
        // document.querySelector('.alert p').innerText = '!! SUCCESS !!';
        // $('.alert-div .error-status').html('');
        // $('.alert-div .error-message').html('');
        // $('.alert-div .error-status').append('STATUS CODE : ' + status);
        // $('.alert-div .error-message').append('MESSAGE : ' + message);
        // $('.alert-div').show();
    },

    /**
     * init
     */
    init : function() {
        // --------------    eventListener List ---------------------
        const searchInputTextTypeBoxes = $searchDiv.querySelectorAll('input[type="text"]');
        searchInputTextTypeBoxes.forEach((item)=>{
            item.addEventListener('keydown', (e)=>{
                if(13 === e.keyCode) { this.search(); }
            });
        });

        //종료코드 체크박스 label 클릭 시 checkbox checked로 변환하는 이벤트
        const $divUseYn = document.querySelector('div#comCdUseYn').closest('div');
        $divUseYn.addEventListener("click",function(){
            const checkbox = document.querySelector('input[type="checkbox"]');
            checkbox.toggleAttribute('checked');
        });

        //저장 버튼 클릭시
        $btnSave.addEventListener('click', ()=>{
            const comCd = comCdCodeInput.value;

            if(comCd !== null && comCd !== ''){
                if(!confirm('정말 수정하시겠습니까?')){return;}
                this.save();
                this.reset(resetEnum.DETAIL);
            } else{
                if(!confirm('정말 저장하시겠습니까?')){return;}
                this.create();
                this.reset(resetEnum.DETAIL);
                this.reset(resetEnum.SEARCH);
            }
        });

        //신규 버튼 이벤트
        $btnNew.addEventListener('click', ()=>{ this.reset(resetEnum.DETAIL); });

        //삭제 버튼 이벤트
        $btnDelete.addEventListener('click', ()=>{
            if(!confirm('정말 삭제하시겠습니까?')){return;}
            this.delete();
        });

        //검색
        $btnSearch.addEventListener('click', ()=>{ this.search(); });

        //alert 닫기 이벤트
        $btnAlertClose.addEventListener('click', ()=>{ $('.alert-div').hide();});

        this.search();
        this.reset(resetEnum.DETAIL);

    }
}

ComCd.init();