//변수 리스트

const $btnSearch = document.querySelector("#btnSearch");
const $btnAlertClose = document.querySelector("#btnAlertClose");
// const $searchDiv = document.querySelector("#divSearch");

const $patientName = document.querySelector("#patientName");
const $patientLocation = document.querySelector("#patientLocation");
const $patientBirthDate = document.querySelector("#patientBirthDate");
const $patientId = document.querySelector("#patientId");
const $patientTelNo = document.querySelector("#patientTelNo");

const $recentBPDt = document.querySelector('#recentBPDt');
const $recentBpValue = document.querySelector('#recentBpValue');
const $recentHRDt = document.querySelector('#recentHRDt');
const $recentHRValue = document.querySelector('#recentHRValue');
const $recentBodyTempDt = document.querySelector('#recentBodyTempDt');
const $recentBodyTempValue = document.querySelector('#recentBodyTempValue');
const $recentRespiratoryDt = document.querySelector('#recentRespiratoryDt');
const $recentRespiratoryValue = document.querySelector('#recentRespiratoryValue');
const $recentSPO2Dt = document.querySelector('#recentSPO2Dt');
const $recentSPO2Value = document.querySelector('#recentSPO2Value');

const $itemList = document.querySelector('#itemIdList');

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

let VitalResult = {

    /**
     * 상세코드 조회
     */
    // search : function() {
    //     let resultData;
    //     const searchTerm = {};
    //
    //     $searchDiv.querySelectorAll('input').forEach((item)=>{
    //
    //         if(item.type === "checkbox"){
    //             searchTerm[item.getAttribute("name")] = item.checked;
    //         }else{
    //
    //             searchTerm[item.getAttribute("name")] = item.value;
    //         }
    //     });
    //
    //     $.ajax({
    //         url: "/common/commonCodeList.ajax",
    //         method: "GET",
    //         data: searchTerm,
    //         async: false,
    //         success: (data)=>{
    //             resultData = data;
    //         },
    //         error: (error)=>{
    //             this.showErrorAlert(error.status, error.responseText);
    //             // alert('ERROR : \n' + 'Status : ' + error.status + '\nMessage : ' + error.responseText);
    //         },
    //         complete: ()=>{
    //             TableGenerator.tableInit($comCdTable.getAttribute("id"), resultData, tblObj
    //                 , "selectComCdTr()");
    //         }
    //     });
    //
    // },

    /**
     * fetch 입소내역
     */
    getAdmissionData: function() {
        let result = null;
        let admissionId = document.cookie.split('=')[1];

        $.ajax({
            url: "/admission/info.ajax",
            method: "GET",
            data: {"admissionId": admissionId},
            async:false,
            success: (data)=>{
                console.log('data :: ', data);
                let jsonStr = JSON.stringify(data);
                result = JSON.parse(jsonStr);
            },
            error: (error)=>{
                console.log("STATUS : " + error.status + "\nMESSAGE : " + error.responseText);
                alert('!! ERROR !!\n' + 'STATUS : ' + error.status + '\nMESSAGE : ' + error.responseText);
            },
            complete:()=>{
                const admissionInfo = JSON.parse(result).admissionInfoVO;
                $patientName.innerText = admissionInfo.name;
                $patientLocation.innerText = admissionInfo.location;
                $patientBirthDate.innerText = admissionInfo.birthDate;
                $patientId.innerText = admissionInfo.patientId;
                $patientTelNo.innerText = admissionInfo.contact;

            }
        })

    },

    /**
     * AJAX (GET) 최근 측정결과내요
     */
    getRecentData: function() {
        let result = null;
        let admissionId = document.cookie.split('=')[1];
        let patientId   = document.querySelector("#patientId").innerText;

        console.log("PATIENT ID :: ", patientId);
        console.log("ADMISSION ID :: ", admissionId);

        $.ajax({
            url: "/vital/" + admissionId + "/" + patientId + "/recentInfo.ajax",
            method: "GET",
            async:false,
            success: (data)=>{
                console.log('data :: ', data);
                let jsonStr = JSON.stringify(data);
                result = JSON.parse(jsonStr);
            },
            error: (error)=>{
                console.log("STATUS : " + error.status + "\nMESSAGE : " + error.responseText);
                alert('!! ERROR !!\n' + 'STATUS : ' + error.status + '\nMESSAGE : ' + error.responseText);
            },
            complete:()=>{
                console.log('result :: ', result);

                $recentBPDt.innerText = result.bloodPressureList[0].resultDt;
                let bpValues = result.bloodPressureList[0].result.split("/");
                bpValues.forEach((item,index)=>{
                    $recentBpValue.children[index].append(item + " " + result.bloodPressureList[0].unit);
                });

                $recentHRDt.innerText = result.heartRateList[0].resultDt;
                $recentHRValue.innerText = result.heartRateList[0].result + result.heartRateList[0].unit;

                $recentBodyTempDt.innerText = result.bodyTemperatureList[0].resultDt;
                $recentBodyTempValue.firstChild.before(result.bodyTemperatureList[0].result);

                $recentRespiratoryDt.innerText = result.respirationList[0].resultDt;
                $recentRespiratoryValue.innerText = result.respirationList[0].result + result.respirationList[0].unit;

                $recentSPO2Dt.innerText = result.oxygenSaturationList[0].resultDt;
                $recentSPO2Value.innerText = result.oxygenSaturationList[0].result + result.oxygenSaturationList[0].unit;

            }
        })

    },

    /**
     * AJAX (GET) 최근 측정결과내요
     */
    getMeasurementResults: function() {
        let result = null;
        let itemType = [];
        let admissionId = document.cookie.split('=')[1];
        let patientId   = document.querySelector("#patientId").innerText;

        console.log("PATIENT ID :: ", patientId);
        console.log("ADMISSION ID :: ", admissionId);

        $.ajax({
            url: "/vital/" + admissionId + "/" + patientId + "/resultList.ajax",
            method: "GET",
            async:false,
            success: (data)=>{
                console.log('data :: ', data);
                result = data;
            },
            error: (error)=>{
                console.log("STATUS : " + error.status + "\nMESSAGE : " + error.responseText);
                alert('!! ERROR !!\n' + 'STATUS : ' + error.status + '\nMESSAGE : ' + error.responseText);
            },
            complete:()=>{
                console.log('result :: ', result);
                let keys = Object.keys(result);

                keys.forEach((key)=>{
                    itemType.push(this.groupBy(result[key], 'itemId', 'itemNm'));
                });

                itemType.forEach((item, index)=>{
                    let optValue = Object.keys(item)[0];
                    let optText = item[optValue];
                    console.log('optValue :: ', optValue, "optText :: ", optText);
                    $itemList.options.add(new Option(optText, optValue));

                });


                console.log('item type :: ', itemType);
                this.test(result);
            }
        })

    },

    paramInit : function() {
        // comCdValidationSeries();
        // const inputObjects = comCdCodeInput.parentElement.offsetParent.parentNode.querySelectorAll("input, textarea");
        // const param = {};
        //
        // //임시 사용자 정보 지정 -- TODO remove
        // param['regId'] = "dev";
        // param['updId'] = "dev";
        //
        // //querySelector로 선택된 input, selectbox, 및 textarea 의 name + value 로 param오브젝트 만들기.
        // // (key[태그의 name 속성값] : value[실재값])
        // inputObjects.forEach((item)=>{
        //     param[item.getAttribute("name")] = item.value;
        // });
        // return param;
    },

    //input란 초기화
    // reset : function(section) {
    //     if(section === resetEnum.SEARCH){
    //         $searchDiv.querySelectorAll('input').forEach((item)=>{
    //             if(item.type === 'checkbox') { item.checked = false; }
    //             else {item.value=''};
    //         });
    //     }
    //     else if(section === resetEnum.DETAIL){
    //         const inputObjects = comCdCodeInput.parentElement.offsetParent.parentNode.querySelectorAll("input, textarea");
    //
    //         inputObjects.forEach((item)=>{
    //
    //             item.value = "";
    //         });
    //     }
    //     let validElements= document.querySelectorAll('.is-valid'); //초록색있는 엘리먼트
    //     for (const validElement of validElements) {
    //         validElement.classList.remove('is-valid');
    //     }
    //
    // },

    /**
     * 에러 발생시 에러 창 보이기
     */
    showErrorAlert : function(status, message) {
        $('.alert').removeClass('alert-success').addClass('alert-danger');
        document.querySelector('.alert p').innerText = '!! ERROR !!';
        $('.alert-div .error-status').html('');
        $('.alert-div .error-message').html('');
        $('.alert-div .error-status').append('STATUS CODE : ' + status);
        $('.alert-div .error-message').append('MESSAGE : ' + message);
        $('.alert-div').show();
    },

    /**
     * 성공 창 보이기
     */
    showSuccessAlert : function(status, message) {
        $('.alert').removeClass('alert-danger').addClass('alert-success');
        document.querySelector('.alert p').innerText = '!! SUCCESS !!';
        $('.alert-div .error-status').html('');
        $('.alert-div .error-message').html('');
        $('.alert-div .error-status').append('STATUS CODE : ' + status);
        $('.alert-div .error-message').append('MESSAGE : ' + message);
        $('.alert-div').show();
    },

    /**
     * init
     */
    init : function() {
        // --------------    eventListener List ---------------------
        // const searchInputTextTypeBoxes = $searchDiv.querySelectorAll('input[type="text"]');
        // searchInputTextTypeBoxes.forEach((item)=>{
        //     item.addEventListener('keydown', (e)=>{
        //         if(13 == e.keyCode) { this.search(); }
        //     });
        // });


        //검색
        // $btnSearch.addEventListener('click', ()=>{ this.search(); });

        $btnAlertClose.addEventListener('click', ()=>{ $('.alert-div').hide(); });

        // this.search();
        // this.reset(resetEnum.DETAIL);
        // this.getAdmissionData();
        // this.getRecentData();
        // this.getMeasurementResults();
    }
}

VitalResult.init();