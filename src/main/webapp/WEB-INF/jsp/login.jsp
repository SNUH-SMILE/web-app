<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<%@ page language="java"   contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt"	   uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--Bootstrap-->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <!--Bootstrap Icon-->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
    <title>Login</title>
</head>
<style>
    .form-control:focus{
        border: none;
        box-shadow:none;
    }
    .btn-pink{
        color: pink;
        transition: 1.5s;
    }

    .btn-pink:hover{
        background-color: lightcoral;
        color: white;
        box-shadow: 0 0 0 0.25rem rgba(208, 42, 42, 0.25);

    }

    .btn:focus{
        border: none;
        box-shadow: none;
    }
    .hcBox{
        box-shadow: 0 0 0 0.25rem rgba(243, 233, 233, 1);
    }
    body {
        background: linear-gradient(-45deg, #e2957d, #e86699, #23a6d5, #23d5ab);
        background-size: 400% 400%;
        animation: gradient 15s ease infinite;
        height: 100vh;
    }

    @keyframes gradient {
        0% {
            background-position: 0% 50%;
        }
        50% {
            background-position: 100% 50%;
        }
        100% {
            background-position: 0% 50%;
        }
    }

</style>
<body class="d-flex justify-content-center align-items-center" style="height: 100vh;">
  <form id="loginInfo" class="form-signin" method="post" onsubmit="return false">
  <div class="container pt-5 pb-1 " style="background-color: rgba(53,57,74,0.7); width: 540px; height: 320px; border-radius: 10px">
      <h5 class="mb-5 ps-3" style="color: white"><span class="me-1" style="font-weight: bold">IITP</span><span style="font-weight: bold">감염병대응</span></h5>
      <div class="input-group mb-1 pe-3 ps-2" style="color: white;background-color: #afb1be; border: none;border-radius: 5px;  height: 15%;">
          <i class="bi bi-person-fill fs-5 ms-3 mr-1 d-flex align-items-center" style="color: white;background-color: #afb1be; border: none;border-bottom-left-radius: 5px; border-top-left-radius: 5px; height: 100%;"></i>
          <input type="text" class="form-control" id="userId" name="userId" style="background-color: #afb1be;border:none;height: 100%; " placeholder="Username" autofocus/>
      </div>
      <div class="input-group pe-3 ps-2 mb-3 mt-2 " style="color: white;background-color: #afb1be; border: none;border-radius: 5px; height: 15%;">
          <i class="bi bi-shield-lock fs-5 ms-3  mr-1 d-flex align-items-center" style="color: white;background-color: #afb1be; border: none;border-bottom-left-radius: 5px; border-top-left-radius: 5px; height: 100%;"></i>
          <input type="password" class="form-control" id="password" name="password" style="background-color: #afb1be;border: none;height: 100%; " placeholder="Password" onkeyup="enterLoginFunc()"/>
      </div>
      <div class="d-flex justify-content-between mt-3">
        <button type="button" class="btn btn-pink" id="btnLogin" style="border-radius: 5px; width: 100%; border: 0px solid pink; font-weight: 600;">로그인</button>
<!--        <a class="d-flex align-items-end" href="#">비밀번호를 잊어버리셨나요?</a>-->
      </div>
  </div>
  </form>

  <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>

  <script>
      function loginNotification(content,selectColor,showTime) {
          let today = new Date();
          let date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
          let time = today.getHours() + ":" + today.getMinutes();
          let dateTime = date+' '+time;
          let popupTop=document.querySelector("body").offsetTop;
          let popupLeft1=document.querySelector("body").offsetWidth/2;
          let popupLeft=popupLeft1-100;
          let color;
          let icon;
          if(selectColor==="green"){
              color='rgba(178,253,183,0.5)';
              icon ='bi bi-check-lg me-2';
          }
          else if(selectColor==="red"){
              color='rgba(255,180,196,0.5)';
              icon ='bi bi-x-lg me-2';
          }
          $('body').append(
              '<div id="popupNotification" class="position-fixed p-3" style="z-index: 999999999;top:'+popupTop+';left: '+popupLeft+';">' +
              '    <div id="liveToast" class="toast fade show" role="alert" aria-live="assertive" aria-atomic="true">' +
              '        <div class="toast-header " style=" background-color:'+color+'">' +
              '            <i class="'+ icon +' " style="color:'+ selectColor +'"></i>' +
              '            <strong class="me-auto">IITP&nbsp;&nbsp;'+content+'</strong>' +
              '            <small>'+ dateTime +'</small>' +
              '        </div>' +
              '    </div>' +
              '</div>'
          );
          setTimeout(function () {
              document.querySelector('#popupNotification').remove();
          },showTime)

      }

      /**
       * EnterKey 이벤트
       */
      function enterLoginFunc() {
          const btnLogin = document.querySelector('#btnLogin');
          const userId = document.querySelector('#userId');
          const userPass = document.querySelector('#password');
          if(userId.value===''){
              loginNotification('아이디를 입력해주세요.','red',2000);
              userId.focus()
              return;
          }
          if(event.keyCode===13){
              if(userPass.value===''){
                  loginNotification('패스워드를 입력해주세요.','red',2000);
                  userPass.focus()
                  return;
              }
              else{
                btnLogin.click()
              }
          }

      }
      window.addEventListener('load',function () {
          const userIdDiv = document.querySelector('#userId').closest('div');
          userIdDiv.classList.add('hcBox');
      })
      const userIdDiv = document.querySelector('#userId').closest('div');
      const userId = $('#userId');
      userId.focus(function () {
          userIdDiv.classList.add('hcBox');
      })
      userId.blur(function () {
          userIdDiv.classList.remove('hcBox');
      })
      const userPassDiv = document.querySelector('#password').closest('div');
      const userPass = $('#password');
      userPass.focus(function () {
          userPassDiv.classList.add('hcBox');
      })
      userPass.blur(function () {
          userPassDiv.classList.remove('hcBox');
      })
      
      $("#btnLogin").on("click", fnLogin);
      
      // 로그인
      function fnLogin() {    	 
    	  const userId = $("#userId").val();
    	  const password = $("#password").val();
    	  
    	  if (!userId) {
    		  $("#userId").focus();
    		  return;
    	  } else if (!password) {
    		  $("#password").focus();
    		  return;
    	  }
    	  
    	  $.ajax({
    		url      : "/login/checkLogin.ajax",
			type     : "post",
    		dataType : "json",
    		data     : $("#loginInfo").serialize(),
    		success  : function(data) {
    			// 로그인 실패 사유확인
    			if (data["loginFailMessage"]) {
    				alert(data["loginFailMessage"]);
    			} else {
    				$(location).attr('href','/patientDashboard/call.do');
    			}
    		}	
    	});
      }
      
  </script>
</body>
</html>