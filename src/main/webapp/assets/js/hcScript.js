/*
	함수 리스트
    getStringByte 	  : 문자열 byte 계산
    getByteSubstr	  : byte기준 문자열 자르기
    commonAjax   	  : ajax(Type: GET)으로 호출
    customValidation  : 조건에따라서 맞지않으면 빨간색 X 표시 맞으면 초록색 V 표시
    notification      : 알림(커스텀Alert) 함수
    setSelectMenuName : 헤더에 선택한 메뉴 이름 표시되도록 하는 함수
*/
/**
 * 헤더에 선택한 메뉴 이름 표시되도록 하는 함수
 * @param menuName        선택한 메뉴이름
 * @param parentMenuCount 선택한 부모메뉴순서
 * @param childrenCount   선택한 자식메뉴순서
 */
function setSelectMenuName(menuName,parentMenuCount,childrenCount) {
    const commonMenuList = document.querySelector('#commonMenuList');
    const selectMenuName = document.querySelector('#selectMenuName');
    selectMenuName.innerText=menuName;
    const parentMenu = commonMenuList.querySelectorAll('.HcCollapse')[parentMenuCount];
    parentMenu.querySelector('.HcCollapse_link').classList.add('HcRotate');
    parentMenu.classList.add('active')
    parentMenu.querySelector('ul').classList.add('showCollapse');
    parentMenu.querySelectorAll('li')[childrenCount].querySelector('a').classList.add('activeSideMenuDisplay');
}

// 문자열 byte 계산
// str : 대상 문자열
function getStringByte(str) {
  return str
    .split('') 
    .map(s => s.charCodeAt(0))
    .reduce((prev, c) => (prev + ((c === 10) ? 2 : ((c >> 7) ? 2 : 1))), 0);
}

// byte기준 문자열 자르기
// str : 대상 문자열
// maxByte : split byte
function getByteSubstr(str, maxByte) {
    let byteLength = str.split('')
                        .map(s => s.charCodeAt(0))
                        .reduce((prev, c, idx, arr) => {
                        	prev = prev + ((c === 10) ? 2 : ((c >> 7) ? 2 : 1));
                        	if (prev > maxByte) {
                        		arr.splice(idx);
                        		return idx;
                        	} else {
                        		return prev;
                        	}
                        }, 0);
                        
	return str.substr(0, byteLength);
}

//검증(elementId, 문구, status: invalid->false(빨간색), valid->true(초록색))
function customValidation(id,content,status) {
    const customValidationDiv      = document.querySelector('#'+id+'').closest('div');
    const customValidationDivQuery = $('#'+id+'').closest('div');
    const customValidationInput = document.querySelector('#'+id+'');
    if(status==='false'){

        if(customValidationInput.classList.contains('is-valid')){
            customValidationInput.classList.remove('is-valid');
        }
        customValidationInput.classList.add('is-invalid')
        let validationPos = customValidationInput.offsetTop + customValidationInput.offsetHeight;
        if(customValidationDiv.querySelector('.invalid-tooltip')===null){
            customValidationDivQuery.append('<div class="invalid-tooltip" style="position: absolute;top: '+validationPos+'px;">'+content+'</div>');
        }
        else{
            customValidationDiv.querySelector('.invalid-tooltip').innerText=content;
        }

    }
    else{
        if(customValidationInput.classList.contains('is-invalid')){
            customValidationInput.classList.remove('is-invalid');
        }
        customValidationInput.classList.add('is-valid');
    }

}

//메뉴 열고 닫기
const showMenu = (toggleId,navbarId,bodyId)=>{

    let windowWidth = document.querySelector('.HcHeader').clientWidth
    const toggle = document.getElementById(toggleId);
    let navbar = document.getElementById(navbarId);
    const bodyPadding = document.getElementById(bodyId);

    if(toggle && navbar){
        toggle.addEventListener('click',()=> {
            navbar.classList.toggle('expander');
            bodyPadding.classList.toggle('body-pd');
        })
    }
}
showMenu('nav_toggle','navbar','main-pd');
//사이드바 선택
const linkColor = document.querySelectorAll('.nav_link');
// const selectMenuNameDiv = document.querySelector('#selectMenuName');
function colorLink() {
    linkColor.forEach(l=> l.classList.remove('active'));
    this.classList.add('active');
    // let selectMenuName = this.querySelector('span').innerText
    // selectMenuNameDiv.innerText=selectMenuName;
}
linkColor.forEach(l=>l.addEventListener('click',colorLink));

//Collapse Menu
const linkCollapseDivs =document.getElementsByClassName('HcCollapseEl');
for(const linkCollapseDiv of linkCollapseDivs){
    linkCollapseDiv.addEventListener('click',function () {
        console.log('TEST',linkCollapseDiv.closest('div'))
        const linkCollapse =linkCollapseDiv.closest('div').querySelector('.HcCollapse_link');
        const collapseMenu = linkCollapseDiv.closest('div').querySelector('.HcCollapse_menu');
        collapseMenu.classList.toggle('showCollapse');
        linkCollapse.classList.toggle('HcRotate');
    })
}


(function () {
    const logoutBtn     = document.querySelector('#modalLogOutBtn');
    const logOutModal   = document.querySelector('#logOutModal');
    const modalCloseBtn = document.querySelector('#modalCloseBtn');
    //로그아웃
    logoutBtn.addEventListener('click',function () {
        // alert("OUT")
        logOutModal.classList.add('SnuhModalClose');
        setTimeout(function () {
            modalCloseBtn.click();
            logOutModal.classList.remove('SnuhModalClose');
        },550);

    });

})();

/**
 * ajax(Type: GET)으로 호출 함수
 * @param url   호출URL
 * @param param 데이터
 * @param successFunc 성공시 호출할 함수
 * @param completeFunc 성공/실패 상관없이 호출할함수
 */
function commonAjax(url,param,successFunc,completeFunc) {
    $.ajax({
        url:url,
        data:param,
        dataType: 'json',
        contentType:'application/json;charset=UTF-8',
        success: function (data) {
            successFunc(data);
        },
        error:function (xhr) {
            console.log('error :: ', xhr.responseText);
            alert('Error'+xhr.statusText+url);
        },
        complete:function () {
            completeFunc();
        }
    });
}

/**
 * 알림(커스텀Alert) 함수
 * @param content 표시할내용
 * @param selectColor 표시할 타입(Green: 성공,Red: 실패)
 * @param showTime   표시할 시간(ms)
 */
function notification(content,selectColor,showTime) {
    alert(content);
    // let today = new Date();
    // let date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
    // let time = today.getHours() + ":" + today.getMinutes();
    // let dateTime = date+' '+time;
    // let popupTop=document.querySelector("main").offsetTop-30;
    // let popupLeft1=document.querySelector("header").offsetWidth/2;
    // let popupLeft=popupLeft1-100;
    // let color;
    // let icon;
    // if(selectColor==="green"){
    //     color='rgba(178,253,183,0.5)';
    //     icon ='bi bi-check-lg me-2';
    // }
    // else if(selectColor==="red"){
    //     color='rgba(255,180,196,0.5)';
    //     icon ='bi bi-x-lg me-2';
    // }
    // $('main').append(
    //     '<div id="popupNotification" class="position-fixed p-3" style="z-index: 999;top:'+popupTop+';left: '+popupLeft+';">' +
    //     '    <div id="liveToast" class="toast fade show" role="alert" aria-live="assertive" aria-atomic="true">' +
    //     '        <div class="toast-header " style=" background-color:'+color+'">' +
    //     '            <i class="'+ icon +' " style="color:'+ selectColor +'"></i>' +
    //     '            <strong class="me-auto">IITP&nbsp;&nbsp;'+content+'</strong>' +
    //     '            <small>'+ dateTime +'</small>' +
    //     '        </div>' +
    //     '    </div>' +
    //     '</div>'
    // );
    // setTimeout(function () {
    //     document.querySelector('#popupNotification').remove();
    // },showTime)

}

/**
 * 페이지 생성시 가로 크기가 991px 보다 작으면 사이드바 버튼 숨김
 */
let resizeStatus;
window.addEventListener('load',function () {
    let navToggle = document.querySelector('#nav_toggle');
    let navbar = document.querySelector('#navbar');
    let mainPd = document.querySelector('#main-pd');
    if(window.outerWidth<768){
        navToggle.setAttribute('hidden','true')
        resizeStatus=false;
        mainPd.classList.remove('body-pd')
        navbar.classList.remove('expander')
    }
    else{
        resizeStatus=true;
    }
});
/**
 * 페이지 크기변경시 가로 크기가 991px 이상이면 사이드바 버튼 활성화 이하면 숨김
 */
window.addEventListener('resize',function () {
    let navToggle = document.querySelector('#nav_toggle');
    let navbar = document.querySelector('#navbar');
    let mainPd = document.querySelector('#main-pd');
    if(window.outerWidth<768 && resizeStatus===true){
        navToggle.setAttribute('hidden','true');
        mainPd.classList.remove('body-pd')
        navbar.classList.remove('expander')
        resizeStatus=false;
    }
    else if(window.outerWidth>991 && resizeStatus===false){
        navToggle.removeAttribute('hidden');
        resizeStatus=true;
    }
})