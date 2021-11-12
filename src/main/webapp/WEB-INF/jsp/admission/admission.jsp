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
            <!--                <section class="col-lg-6 table-responsive mb-3" style="height: 48.4vw">-->
            <section class="col-lg-6 table-responsive mb-3" style="height: calc(100vh - 80px)">
                <table id="tbAdmissionList" class="table" >
                    <thead>
                    <tr>
                        <th>환자 ID</th>
                        <th>병원 ID</th>
                        <th>병원 환자 ID</th>
                        <th>이름</th>
                        <th>생년월일</th>
                        <th>연락처</th>
                        <th>성별</th>
                    </tr>
                    </thead>
                    <tbody>
                    
                    </tbody>
                </table>
            </section>
            <section class="col-lg-6 table-responsive mb-3" style="height: 47.5vw">
                <div class="card" >
                    <div class="card-header HcCardHeader" >
                        <div>
							상세정보
                        </div>
                        <div>
                            <button type="button" id="btnNew"       class="hcBtn1 m-1 p-1" >신규</button>
                            <button type="button" id="btnSave"      class="hcBtn1 m-1 p-1" >저장</button>
                            <button type="button" id="btnDischarge" class="hcBtn2 m-1 p-1" >퇴실</button>
                        </div>
                    </div> 
                    <div class="card-body">
                        <div class="container-fluid">
                        	<form id="formAdmissionInfo">
                            <div class="row g-3 centerDisplay">
                                <div class="col-lg-4" >입소내역 ID</div>
                                <div class="col-lg-8" >
                                	<input type="text" id="admissionId" name="admissionId" class="form-control" readonly/>
                               	</div>
                                <div class="col-lg-4">환자 ID</div>
                                <div class="col-lg-8">
                                	<input type="text" id="patientId" name="patientId" class="form-control" readonly/>
                               	</div>
                                <div class="col-lg-4">병원 환자</div>
                                <div class="col-lg-8">
                                	<input type="text" id="hospitalPatientId" name="hospitalPatientId" class="form-control" onInput="checkMaxByte(this, 20)"/>
                               	</div>
                                <div class="col-lg-4">생활치료센터</div>
                                <div class="col-lg-8">
                                    <div class="input-group">
                                    	<input type="text" id="centerId" name="centerId" class="form-control" readonly />
                                        <input type="text" id="centerNm" name="centerNm" class="form-control" readonly />
                                        <button type="button" id="btnModalTreatMent" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#treatmentCenterModal"><i class="bi bi-card-list"></i></button>
                                    </div>
                                </div>
                                <div class="col-lg-4">이름</div>
                                <div class="col-lg-8">
                                	<input type="text" class="form-control" id="name" name="name" onInput="checkMaxByte(this, 50)"/>
                               	</div>
                                <div class="col-lg-4">생년월일</div>
                                <div class="col-lg-8">
                                	<input type="date" class="form-control" id="birthDate" name="birthDate" max="9999-12-31"/>
                               	</div>
                                <div class="col-lg-4">연락처</div>
                                <div class="col-lg-8">
                                	<input type="text" class="form-control" id="contact" name="contact" onInput="checkMaxByte(this, 20)"/>
                               	</div>
                                <div class="col-lg-4">성별</div>
                                <div class="col-lg-8">
                                    <select class="form-select" id="sex" name="sex">
                                        <option id="" value="">선택</option>
                                        <option id="selectMale" value="M">남</option>
                                        <option id="selectFeMale" value="F">여</option>
                                    </select>
                                </div>
                                <div class="col-lg-4">입소일자</div>
                                <div class="col-lg-8">
                                	<input type="date" id="admitDate" name="admitDate" class="form-control" max="9999-12-31" onchange="admitDateFunc()"/>
                               	</div>
                                <div class="col-lg-4">퇴소일자</div>
                                <div class="col-lg-8">
                                	<input type="date" id="dischargeDate" name="dischargeDate" class="form-control" max="9999-12-31" onchange="admitDateFunc()"/>
                               	</div>                                
                               	<div class="col-lg-4">위치</div>
                                <div class="col-lg-8">
                                    <div class="input-group">
                                    	<input type="text" id="location" name="location" class="form-control" readonly />
                                        <input type="text" id="locationNm" name="locationNm" class="form-control" readonly />
                                        <button type="button" id="btnModalLocation" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#locationModal" onclick="showLocationModal()"><i class="bi bi-card-list"></i></button>
                                    </div>
                                </div>
                                <div class="col-lg-4">리마크</div>
                                <div class="col-lg-8">
                                    <textarea id="remark" name="remark" class="form-control" onKeyUp="checkMaxByte(this, 10)"></textarea>
                                </div>
                            </div>
                            </form>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>
</main>
<!--Main End-->

<!--treatmentCenter Modal Start-->
<!-- 생활치료센터 모달 리스트 -->
<div id="treatmentCenterModal" class="modal" tabindex="-1" >
    <div class="modal-dialog modal-xl">
        <div class="modal-content">
            <div class="modal-header ">
                <div style="color: darkblue">L</div>
                <div style="color: deepskyblue">O</div>
                <div style="color: darkblue">G</div>
                <div style="color: deepskyblue">O</div>
                <div>생활치료센터</div>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" id="treatmentCenterCloseBtn"></button>
            </div>
            <div class="modal-body table-responsive" style="height: 20vw; padding-top: 0;">
                <table id="tbTreatmentCenter" class="table" >
                    <thead>
                    <tr>
                        <th class="text-center">선택</th>
                        <th>생활치료센터 ID</th>
                        <th>치료센터명</th>
                        <th>위치</th>
                        <th>병원정보</th>
                    </tr>
                    </thead>
                    <tbody>
                    
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn" onclick="setTreatmentCenter()">선택</button>
            </div>
        </div>
    </div>
</div>
<!--treatmentCenter Modal End-->

<!--position Modal Start-->
<!-- 위치 모달 리스트 -->
<div id="locationModal" class="modal" tabindex="-1" >
    <div class="modal-dialog modal-xl">
        <div class="modal-content">
            <div class="modal-header ">
                <div style="color: darkblue">L</div>
                <div style="color: deepskyblue">O</div>
                <div style="color: darkblue">G</div>
                <div style="color: deepskyblue">O</div>
                <div>위치</div>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" id="positionCloseBtn"></button>
            </div>
            <div class="modal-body table-responsive" style="height: 20vw; padding-top: 0;">
                <table id="tbLocation" class="table">
                    <thead>
	                    <tr>
	                        <th class="text-center">선택</th>
	                        <th>위치ID</th>
	                        <th>위치명</th>
	                    </tr>
                    </thead>
                    <tbody>
                    
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn" onclick="setLocation()">선택</button>
            </div>
        </div>
    </div>
</div>
<!--position Modal End-->

<%@ include file="/common/footer.jsp" %>
<script>

	// 초기 데이터 셋팅
	$(document).ready(function() {
		// 입소내역 리스트 조회
		searchAdmissionList();
		// 생활치료센터 내역 조회
		searchTreatmentCenterList();
		
		// 입소일 당일 기본 값 처리
		setInputDateByToday("admitDate");
		
		// 필수입력여부 확인 및 invaild tooltip 제어 처리
		$("input").blur(function(e) {
			if ($(e.target).val() && $(e.target).hasClass("is-invalid")) {
				customValidation($(e.target)[0].id, '', true);
			}
		});
		
		// 연락처 마스크 적용
		$("#contact").inputmask({
			mask:"999-9999-9999",
			autoUnmask: true,
			removeMaskOnSubmit:true
		});
	});
	
	// 신규 Click
	$("#btnNew").click(function() {
		// 기존 선택 내역 초기화
		$("#tbAdmissionList tbody tr").removeClass("hcTrSelect");
		
		initInputArea(); 		// 입소내역 초기화
		setInputDateByToday();	// 입소일 당일 처리
	});
	
	// 저장 Click
	$("#btnSave").click(function() {
		saveAdmission();
	});
	
	// 퇴실 Click
	$("#btnDischarge").click(function() {
		updateDischargeDate();
	});
	
	// 입력영역 초기화
	function initInputArea() {
		initInvalidTooltip();
		$("#formAdmissionInfo")[0].reset();
		$("#name").focus();
	}
	
	// Date Control 당일 설정
	function setInputDateByToday(pName) {
        let today = new Date();
        let dd = String(today.getDate()).padStart(2, '0');
        let mm = String(today.getMonth() + 1).padStart(2, '0');
        let yyyy = today.getFullYear();

        today = yyyy + '-' + mm + '-' + dd;
		$("#" + pName).val(today);
	}

	// 입소자 리스트 조회
	function searchAdmissionList() {
		$.ajax({
			url		: "/admission/list.ajax",
			success	: function(data) {
				setAdmissionList(data);
			}
		});
	}
	
	// 입소자 리스트 데이터 셋팅
	function setAdmissionList(data) {
		// 조회 내역 초기화
		let table = $("#tbAdmissionList"); 
		table.find("tbody").empty();
		
		// 조회 데이터 표현
		data.forEach(function(obj, idx) {
			table.find("tbody").append(
			  		'<tr onclick="searchAdmissionInfo(this)" data-admission-id="' + obj.admissionId + '">'
                +   '  <td>' + obj.patientId         + '</td>'
                +   '  <td>' + obj.hospitalCd        + '</td>'
                +   '  <td>' + obj.hospitalPatientId + '</td>'
                +   '  <td>' + obj.name              + '</td>'
                +   '  <td>' + obj.birthDate         + '</td>'
                +   '  <td>' + obj.contact           + '</td>'
                +   '  <td>' + obj.dispSex           + '</td>'
			    + 	'</tr>'
				
			);
		});
	}
		
	// 입소자 상세 내역 조회
	function searchAdmissionInfo(trAdmission) {
		// 하이라이트 처리
		$("#tbAdmissionList tbody tr").removeClass("hcTrSelect");
		$(trAdmission).addClass("hcTrSelect");
		
		// 입력내역 초기화
		initInputArea();
		
		// 입소내역ID
		const admissionId = $(trAdmission).data("admissionId");

		// 입소내역 정보 조회
		$.ajax({
			url			: "/admission/info.ajax",
			type		: "post",
			data		: {admissionId : admissionId},
			datatype	: "json",
			success		: function(data) {
				// 입소자정보
				let admissionInfo = JSON.parse(data)["admissionInfoVO"];
				// 위치정보
				let locationVO = JSON.parse(data)["locationVO"];
				
				// 입소자정보 데이터 셋팅
				setAdmissionInfo(admissionInfo);
				
				// 위치 모달 데이터 셋팅
				setLocationData(locationVO);
			}
		});
	}
	
	// 알림 Tooltip 초기화
	function initInvalidTooltip() {
		// invalid 알림 제거
		$(".is-invalid").removeClass("is-invalid");
		$(".is-valid").removeClass("is-valid");
		$(".invalid-invalid").remove();
	}
	
	// 입소자정보 데이터 셋팅
	function setAdmissionInfo(data) {
		// 알림 초기화
		initInvalidTooltip();
		
		// 입소자정보 셋팅
		$("#admissionId").val(data.admissionId);				// 입소내역ID
		$("#patientId").val(data.patientId);					// 환자ID
		$("#hospitalPatientId").val(data.hospitalPatientId);	// 병원 환자				
		$("#centerId").val(data.centerId);						// 센터ID
		$("#centerNm").val(data.centerNm);                      // 센터명				
		$("#name").val(data.name);								// 이름
		$("#birthDate").val(data.birthDate);                    // 생년월일
		$("#contact").val(data.contact);                        // 연락처
		$("#sex").val(data.sex);                                // 성별
		$("#admitDate").val(data.admitDate);                    // 입소일자
		$("#dischargeDate").val(data.dischargeDate);            // 퇴소일자				
		$("#location").val(data.location);                      // 위치
		$("#locationNm").val(data.locationNm);                  // 위치명
		$("#remark").val(data.remark);                          // 리마크
	}

	// 입소내역 및 환자 정보 저장
	function saveAdmission() {
		// 저장 데이터 생성
		let arr = $("#formAdmissionInfo").serializeArray();
		let saveData = {};
		jQuery.each(arr, function() {
			saveData[this.name] = this.value;
		});
		
		initInvalidTooltip();
		
		// 데이터 입력 여부 확인
		let isInvalid = true;		
		if (!saveData.centerId) {
 			//alert("생활치료센터 내역이 누락되었습니다.");
 			customValidation("centerNm", '생활치료센터를 선택하세요', 'false');
			isInvalid = false;
		}
		if (!saveData.name) {
			//alert("이름이 누락되었습니다.");
			customValidation("name", '이름이 입력하세요.', 'false');
			isInvalid = false;
		}
		if (!saveData.birthDate) {
			//alert("생년월일이 누락되었습니다.");
			showRequired("birthDate", '생년월일을 입력하세요.');
			isInvalid = false;
		}
		if (!saveData.contact) {
			//alert("연락처가 누락되었습니다.");
			showRequired("contact", '연락처를 입력하세요.');
			isInvalid = false;
		}
		if (!saveData.sex) {
			//alert("성별이 누락되었습니다.");
			showRequired("sex", '성별을 선택하세요.');
			isInvalid = false;
		}
		if (!saveData.admitDate) {
			//alert("입소일자가 누락되었습니다.");
			showRequired("admitDate", '입소일자를 입력하세요.');
			isInvalid = false;
		}
		if (!saveData.location) {
			//alert("위치가 누락되었습니다.");
			showRequired("locationNm", '위치를 선택하세요.');
			isInvalid = false;
		}
		
		if (!isInvalid) {
			return;
		}
		
		// 저장 여부 확인
		let questionMsg = (!saveData.admissionId) ? "신규 입소내역을 추가하시겠습니까?"
											      : "입소내역ID : " + saveData.admissionId + "\n이름 : " + saveData.name + "\n\n입소정보를 수정하시겠습니까?";
		if (!confirm(questionMsg)) {
			return;	
		}
		
		$.ajax({
			type	: "post",
			url		: "/admission/save.ajax",
			data	: saveData,
			success	: function(data) {
				// 입소자 리스트 정보
				let admissionListVO = JSON.parse(data)["admissionListVO"];
				// 입소자정보
				let admissionInfoVO = JSON.parse(data)["admissionInfoVO"];
				// 위치정보
				let locationVO = JSON.parse(data)["locationVO"];
								
				// 입소자 리스트 데이터 셋팅
				setAdmissionList(admissionListVO);
				// 입소자정보 데이터 셋팅
				setAdmissionInfo(admissionInfoVO);				
				// 위치 모달 데이터 셋팅
				setLocationData(locationVO);
				
				alert("저장되었습니다.");
			}
		});
		
		
	}
	
	// 퇴실처리
	function updateDischargeDate() {
		let admissionId = $("#admissionId").val();
		let dischargeDate = $("#dischargeDate").val();
		let name = $("#name").val();
		
		if (!admissionId) {
			alert("입소내역을 선택하세요.");
			return;			
		} else if (!dischargeDate) {
			alert("퇴소일자를 선택하세요.");
			$("#dischargeDate").focus();
			return;
		}
		
		if (!confirm("이름 : [ " + name + " ]\n퇴소일자 : [ " + dischargeDate + " ]\n\n퇴실처리 하시겠습니까?")) {
			return;	
		}
		
		$.ajax({
			url		: "/admission/discharge.ajax",
			type	: "post",
			data	: {admissionId:admissionId, dischargeDate:dischargeDate},
			success : function(data) {
				// 입소내역 정보 초기화
				initInputArea();
				
				// 입소자 리스트 데이터 셋팅
				setAdmissionList(data);
				
				alert("입소자 : [ " + name + " ] 퇴실처리 하였습니다.");
			}
		});
	}
	
	// ---------------------------------------
	// 생활치료센터 모달 관련 - treatmentCenterModal
	// ---------------------------------------
	// 생활치료센터 리스트 조회(모달)
	function searchTreatmentCenterList() {
		// 조회 내역 초기화
		let table = $("#tbTreatmentCenter"); 
		table.find("tbody").empty();
		
		$.ajax({
			url		: "/admission/treatmentCenterList.ajax",
			success	: function(data) {				
				// 조회 데이터 표현
				data.forEach(function(obj, idx) {
					table.find("tbody").append(
                            '<tr onclick="focusTreatmentCenter(\'' + obj.centerId + '\')" data-center-id="' + obj.centerId + '" data-center-nm="' + obj.centerNm + '">'
                        +   '   <td class="text-center">'
                        +   '       <input type="radio" class="text-center mt-1" name="raCenter" />'
                        +   '   </td>'
                        +   '   <td>' + obj.centerId       + '</td>'
                        +   '   <td>' + obj.centerNm       + '</td>'
                        +   '   <td>' + obj.centerLocation + '</td>'
                        +   '   <td>' + obj.hospitalNm     + '</td>'
                        +   '</tr>'
					    
					);
				});				
			}
		});
	}
	
	// 생활치로센터 모달 오픈
	$("#treatmentCenterModal").on("show.bs.modal", function(e) {
		// 기존 생활치료센터 선택
		focusTreatmentCenter($("#centerId").val());
	});
	
	// 생활치료센터 tr 선택 처리	
	function focusTreatmentCenter(centerId) {
		// 센터ID를 기준으로 센터tr검색 
		let trTreatmentCenter = $("#tbTreatmentCenter tbody tr[data-center-id='" + centerId + "']");
		
		// 기존 선택 내역 초기화
		$("#tbTreatmentCenter tbody tr").removeClass("hcTrSelect");
		$("#tbTreatmentCenter tbody tr input[name=raCenter]").prop("checked", false);
		
		// 대상 tr이 없을 경우 return
		if (!centerId) {
			return;
		}
		
		// 하이라이트 처리		
		$(trTreatmentCenter).addClass("hcTrSelect");
		
		// radio 선택 처리		
		$(trTreatmentCenter).find("input[name=raCenter]").prop("checked", true);
	}
	
	// 생활치료센터 선택
	function setTreatmentCenter() {
		const selectTr = $("#tbTreatmentCenter tbody tr input[name=raCenter]:checked");
		const befCenterId = $("#centerId").val();
		
		// 생활치료센터 선택 여부 확인
		if (selectTr.length === 0) {
			alert("생활치료센터를 선택하세요.");
			return;
		} else if (selectTr.length > 1) {
			alert("하나의 생활치료센터를 선택하세요.");
			return;
		}
		
		const centerId = selectTr.eq(0).closest("tr").data("centerId");
		const centerNm = selectTr.eq(0).closest("tr").data("centerNm");
		
		$("#centerId").val(centerId);
		$("#centerNm").val(centerNm);
		
		// 위치 정보 초기화
		if (!befCenterId || befCenterId !== centerId) {
			$("#location").val("");
			$("#locationNm").val("");
			
			// 위치 정보 재조회
			searchLocationList();
		}
		
		// 모달 닫기
		$('#treatmentCenterModal').modal('hide');
		$('.modal-backdrop').remove();
		
		// valid 체크를 위해 센터명 포커스 이후 이름 변경
		$("#centerNm").focus();
		$("#name").focus();
	}

	// ---------------------------------------
	// 위치 모달 관련 - locationModal
	// ---------------------------------------
	// 위치 모달 호출 
	function showLocationModal() {
		// 위치 데이터 확인
		if ($("#tbLocation tbody tr").length === 0) {
			// 모달 닫기
			$('#locationModal').modal('hide');
			$('.modal-backdrop').remove();
			
			let centerId = $("#centerId").val();
			let centerNm = $("#centerNm").val();
			if (centerId && centerNm) {
				alert(centerNm + "센터의 위치내역이 존재하지 않습니다.");
			}
		}		
	}
	
	// 위치 리스트 조회(모달)
	function searchLocationList() {
		// 조회 내역 초기화
		let table = $("#tbLocation"); 
		table.find("tbody").empty();
		
		let centerId = $("#centerId").val();
		
		if (!centerId) {
			return;
		}
		
		$.ajax({
			url		: "/admission/locationList.ajax",
			data	: {centerId : centerId},
			success	: function(data) {
				if (data === null || data.length === 0) {
					
				} else {
					setLocationData(data);
				}
			}
		});
	}
	
	// 위치 리스트 데이터 표현
	function setLocationData(data) {
		let table = $("#tbLocation"); 
		table.find("tbody").empty();
		
		// 조회 데이터 표현
		data.forEach(function(obj, idx) {
			table.find("tbody").append(
                    '<tr onclick="focusLocation(\'' + obj.detailCd + '\')" data-location-cd="' + obj.detailCd + '" data-location-nm="' + obj.detailCdNm + '">'
                +   '   <td class="text-center">'
                +   '       <input type="radio" class="text-center mt-1"  name="raLocation" />'
                +   '   </td>'
                +   '   <td>' + obj.detailCd   	+ '</td>'
                +   '   <td>' + obj.detailCdNm	+ '</td>'
                +   '</tr>'
			    
			);
		});
	}
	
	// 위치 모달 오픈
	$("#locationModal").on("show.bs.modal", function(e) {
		// 기존 생활치료센터 선택
		focusLocation($("#location").val());
	});
	
	// 위치 tr 선택 처리	
	function focusLocation(detailCd) {
		// detailCd 기준으로 location tr검색 
		let trLocation = $("#tbLocation tbody tr[data-location-cd='" + detailCd + "']");
		
		// 기존 선택 내역 초기화
		$("#tbLocation tbody tr").removeClass("hcTrSelect");
		$("#tbLocation tbody tr input[name=raLocation]").prop("checked", false);
		
		// 대상 tr이 없을 경우 return
		if (!detailCd) {
			return;
		}
		
		// 하이라이트 처리		
		$(trLocation).addClass("hcTrSelect");
		
		// radio 선택 처리		
		$(trLocation).find("input[name=raLocation]").prop("checked", true);
	}
	
	// 위치 선택 처리
	function setLocation() {
		const selectTr = $("#tbLocation tbody tr input[name=raLocation]:checked");
		
		// 위치 선택 여부 확인
		if (selectTr.length === 0) {
			alert("위치를 선택하세요.");
			return;
		} else if (selectTr.length > 1) {
			alert("하나의 위치를 선택하세요.");
			return;
		}
		
		// 선택한 위치 데이터 셋팅
		const locationCd = selectTr.eq(0).closest("tr").data("locationCd");
		const locationNm = selectTr.eq(0).closest("tr").data("locationNm");		
		$("#location").val(locationCd);
		$("#locationNm").val(locationNm);
		
		// 모달 닫기
		$('#locationModal').modal('hide');
		$('.modal-backdrop').remove();
		
		// valid 체크를 위해 위치명 포커스 이후 리마크 변경
		$("#locationNm").focus();
		$("#remark").focus();
	}

	
	// ---------------------------------------
	// 입소자 정보 입력 컨트롤 데이터 확인
	// ---------------------------------------	
	// input 문자열 제한
	function checkMaxByte(obj, maxLength) {
		if (getStringByte(obj.value) > maxLength) {
			obj.value = getByteSubstr(obj.value, maxLength);
		}
	}
 
	// 입소/퇴소일자 확인
    function admitDateFunc() {
        let from = document.querySelector('input[name="admitDate"]');
        let to =  document.querySelector('input[name="dischargeDate"]');
        
        if (from.value && to.value) {
        	console.log("aa");
        	if (from.value > to.value) {
        		to.value = "";
        		customValidation(to.id,'입소일 보다 이전입니다.','false');
        	} else {
        		customValidation(to.id,'입소일 보다 이전입니다.','false');
        	}
        }
    }
	
	// 필수 입력 대상 알림
	function showRequired(id, msg) {
		customValidation(id, msg, 'false');
	}
    setSelectMenuName('입소자 정보 관리',1,0);
</script>
</body>
</html>