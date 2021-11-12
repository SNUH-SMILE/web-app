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
                <table class="table" id="tbPatientEquipList">
                    <thead>
                    <tr>
                        <th>환자</th>
                        <th>장비 </th>
                    </tr>
                    </thead>
                    <tbody>
                    
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
                            <button type="button" id="btnNew"    class="hcBtn1 m-1 p-1" style="font-weight: bold">신규</button>
                            <button type="button" id="btnSave"   class="hcBtn1 m-1 p-1">저장</button>
<!--                             <button type="button" id="btnDelete" class="hcBtn2 m-1 p-1">삭제</button> -->
                        </div>
                    </div>
                    <div class="card-body">
                        <div class="container-fluid">
                        	<form id="formInputArea">
                            <div class="row g-3 centerDisplay">
                                <div class="col-lg-3" hidden>입소내역 ID</div>
                                <div class="col-lg-9" hidden>
                                    <input type="text" id="admissionId" name="admissionId" class="form-control" />
                                    <input type="text" id="centerId" name="centerId" class="form-control" />
                                </div>
                                <div class="col-lg-3">환자</div>
                                <div class="col-lg-9">
                                    <div class="input-group">
                                    	<input type="text" id="patientId" name="patientId" class="form-control" readonly/>
                                        <input type="text" id="name" name="name" class="form-control" readonly/>
                                        <button type="button" id="admissionIdBtn" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#admissionModal">
                                        	<i class="bi bi-card-list"></i>
                                       	</button>
                                    </div>
                                </div>
                                <div class="col-lg-3 ">장비</div>
                                <div class="col-lg-9" >
                                    <div class="input-group">
                                        <button type="button" id="equipmentBtn" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#EquipmentModal" onclick="showEquipmentModal()">
                                        	<i class="bi bi-card-list"></i>
                                       	</button>
                                    </div>
                                </div>
                                <!-- 추가 장비 표현 영역 -->
                                <div id="equipAdd" class="offset-3 col-lg-9 mt-0">

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
<!--AdmissionModal Modal Start-->
<div id="admissionModal" class="modal" tabindex="-1" >
    <div class="modal-dialog modal-xl">
        <div class="modal-content hcModalContent" >
            <div class="modal-header ">
                <div style="color: darkblue">L</div>
                <div style="color: deepskyblue">O</div>
                <div style="color: darkblue">G</div>
                <div style="color: deepskyblue">O</div>
                <div>생활치료센터(입소자)</div>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" id="admissionCloseBtn"></button>
            </div>
            <div class="modal-body table-responsive" style="height: 20vw; padding-top: 0; ">
                <table class="table" id="tbAdmissionList">
                    <thead>
	                    <tr>
	                        <th class="text-center">선택</th>
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
            </div>
            <div class="modal-footer">
                <button type="button" class="btn" onclick="setAdmission()">선택</button>
            </div>
        </div>
    </div>
</div>
<!--AdmissionModal Modal End-->

<!--EquipmentModal Modal Start-->
<div id="EquipmentModal" class="modal" tabindex="-1" >
    <div class="modal-dialog modal-xl">
        <div class="modal-content">
            <div class="modal-header ">
                <div style="color: darkblue">L</div>
                <div style="color: deepskyblue">O</div>
                <div style="color: darkblue">G</div>
                <div style="color: deepskyblue">O</div>
                <div>생활치료센터(장비)</div>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" id="EquipmentCloseBtn"></button>
            </div>
            <div class="modal-body table-responsive" style="height: 20vw; padding-top: 0;">
                <table class="table" id="tbEquipment">
                    <thead>
	                    <tr>
	                        <th class="text-center">선택</th>
	                        <th>장비 ID</th>
	                        <th>장비 명칭</th>
	                        <th>생활치료센터</th>
	                        <th>리마크</th>
	                    </tr>
                    </thead>
                    <tbody id="equipmentModalTableBody">
                    
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn " data-bs-dismiss="modal" onclick="setEquipmentModal()">선택</button>
            </div>
        </div>
    </div>
</div>
<!--EquipmentModal Modal End-->
<%@ include file="/common/footer.jsp" %>
<script>

	// initialize
	$(document).ready(function() {
		// 환자별 장비리스트 조회
		searchPatientEquipList();
		// 환자(입소내역 리스트) 조회
		searchAdmissionList();
	});
	
	// 신규 Click
	$("#btnNew").click(function() {
		// 기존 선택 내역 초기화
		$("#tbPatientEquipList tbody tr").removeClass("hcTrSelect");
		// 입력내역 초기화
		initInputArea();
		// 장비 모달 초기화
		$("#tbEquipment").find("tbody").empty();
	});
	
	// 저장 Click
	$("#btnSave").click(function() {
		savePatientEquip();
	});
	
	// 삭제 Click
	$("#btnDelete").click(function() {
		alert("삭제");
	});
	
	// 입력영역 초기화
	function initInputArea() {
		// 입력영역
		$("#formInputArea")[0].reset();
		// 장비 추가 영역
 		$('#equipAdd').empty();
	}
	
	// 환자별 장비리스트 조회
	function searchPatientEquipList() {
		$.ajax({
			url		: "/patientEquip/patientEquipList.ajax",
			type	: "post",
			success	: function(data) {
				setPatientEquipList(data);
			}
		});
	}
	
	// 환자별 장비리스트 데이터 셋팅
	function setPatientEquipList(data) {
		console.log(data);
		// 기존 내역 초기화
		const table = $("#tbPatientEquipList");
		table.find("tbody").empty();
		
		// 조회 데이터 표현
		data.forEach(function(obj, idx) {
			table.find("tbody").append(
			  		'<tr onclick="getPatientEquipInfo(\'' + obj.admissionId + '\', \'' + obj.centerId + '\' )" data-admission-id="' + obj.admissionId + '" data-center-id="' + obj.centerId + '">'
                +   '  <td>' + obj.name			+ '</td>'
                +   '  <td>' + obj.equipListNm	+ '</td>'
			    + 	'</tr>'				
			);
		});
	}
	
	// 환자 사용장비 정보 상세 내역 조회
	function getPatientEquipInfo(admissionId, centerId) {		
		// 하이라이트 처리
		$("#tbPatientEquipList tbody tr").removeClass("hcTrSelect");
		const selectTr = $("#tbPatientEquipList tbody tr[data-admission-id='" + admissionId + "']");
		if (selectTr.length > 0) {
			$(selectTr).addClass("hcTrSelect");
		}
		
		// 입력영역 초기화
		initInputArea();
		
		$.ajax({
			url		: "/patientEquip/patientEquipInfoList.ajax",
			type	: "post",
			data	: {admissionId:admissionId, centerId:centerId},
			datatype: "json",
			success	: function(data) {
				setPatientEquipInfo(data)				
			}
		});
	}
	
	// 환자 사용장비 정보 데이터 셋팅
	function setPatientEquipInfo(data) {
		// 입소내역 정보
		const admissionData = JSON.parse(data)["admissionInfoVO"];
		// 환자 사용장비 정보
		const patientEquipData = JSON.parse(data)["patientEquipVOs"];
		// 센터별 장비 리스트
		const equipmentData = JSON.parse(data)["equipmentVOs"];
						
		// 1. 환자정보
		$("#admissionId").val(admissionData.admissionId);
		$("#centerId").val(admissionData.centerId);
		$("#patientId").val(admissionData.patientId);
		$("#name").val(admissionData.name);
		
		// 2. 장비정보
		setEquipment(patientEquipData);
		
		// 3. 장비 모달 리스트
		// 장비 모달 초기화
		let table = $("#tbEquipment"); 
		table.find("tbody").empty();
		
		// 장비 모달 데이터 표현
		equipmentData.forEach(function(obj, idx) {
			table.find("tbody").append(
			  		'<tr onclick="focusEquipment(\'' + obj.equipId + '\')" data-equip-id="' + obj.equipId + '" data-equip-nm="' + obj.equipNm + '" >'
                +   '   <td class="text-center">'
                +   '       <input type="checkbox" class="text-center mt-1"  name="chkFocus" />'
                +   '   </td>'
                +   '  <td>' + obj.equipId  + '</td>'
                +   '  <td>' + obj.equipNm  + '</td>'
                +   '  <td>' + obj.centerNm + '</td>'
                +   '  <td>' + obj.remark   + '</td>'
			    + 	'</tr>'						
			);
		});
	}
	
	//------------------------------------
	// 저장
	//------------------------------------
	function savePatientEquip() {
		const admissionId = $("#admissionId").val();	// 입소내역ID
		const centerId    = $("#centerId").val();       // 선터ID
		const patientId   = $("#patientId").val();		// 환자ID
		
		if (!admissionId || !centerId || !patientId) {
			alert("환자를 선택하세요.");
			return;			
		}
		
		// 입소정보
		let admissionInfo = {};
		admissionInfo.admissionId = admissionId;
		admissionInfo.centerId    = centerId;
		admissionInfo.patientId   = patientId;
		
		// 장비데이터
		let patientEquip = {};
		$("#equipAdd")
			.find("input[name=equipId]")
			.each(function(index, item) {
				patientEquip['patientEquipVOs[' + index + '].admissionId']	= admissionId; 
				patientEquip['patientEquipVOs[' + index + '].patientId']	= patientId; 
				patientEquip['patientEquipVOs[' + index + '].equipId']		= $(this).val();
			});
		
		// 저장데이터-PatientEquipSaveVO
		let saveData = {...admissionInfo, ...patientEquip};
		
		// TODO.선택장비데이터가 없을 경우 메시지 처리..? 별도 삭제 기능 추가?
		if (!confirm("저장하시겠습니까?")) {
			return;
		}
		
		$.ajax({
			url		: "/patientEquip/save.ajax",
			type	: "post",
			data	: saveData,
			success	: function(data) {
				alert("저장되었습니다.");
								
				// 입력영역 초기화
				initInputArea();
				// 리스트 셋팅
				setPatientEquipList(JSON.parse(data)["patientEquipListVOs"]);				
				// 상세정보 셋팅
				setPatientEquipInfo(data);
			}
		})	
		
	}

	//------------------------------------
	// 환자 모달
	//------------------------------------
	// 입소자 리스트 조회
	function searchAdmissionList() {
		$.ajax({
			url		: "/admission/list.ajax",
			success	: function(data) {
				setAdmissionList(data);
			}
		});
	}
	
	// 환자 모달 오픈
	$("#admissionModal").on("show.bs.modal", function(e) {
		// 기존 입소자 선택
		focusAdmission($("#admissionId").val());
	});
	
	// 입소자 리스트 데이터 셋팅
	function setAdmissionList(data) {
		// 조회 내역 초기화
		let table = $("#tbAdmissionList"); 
		table.find("tbody").empty();
		
		// 조회 데이터 표현
		data.forEach(function(obj, idx) {
			table.find("tbody").append(
			  		'<tr onclick="focusAdmission(\'' + obj.admissionId + '\')" data-admission-id="' + obj.admissionId + '" data-center-id="' + obj.centerId + '" >'
                +   '   <td class="text-center">'
                +   '       <input type="radio" class="text-center mt-1"  name="raFocus" />'
                +   '   </td>'
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
	
	// 환자 tr 선택 처리
	function focusAdmission(admissionId) {
		// 기존 선택 내역 초기화
		$("#tbAdmissionList tbody tr").removeClass("hcTrSelect");
		$("#tbAdmissionList tbody tr input[name=raFocus]").prop("checked", false);
		
		// 대상 tr이 없을 경우 return
		if (!admissionId) {
			return;
		}
		
		// 선택 대상 tr 검색
		let trAdmission = $("#tbAdmissionList tbody tr[data-admission-id='" + admissionId + "']");
		
		// 하이라이트 처리		
		$(trAdmission).addClass("hcTrSelect");
		
		// radio 선택 처리		
		$(trAdmission).find("input[name=raFocus]").prop("checked", true);
	}
	
	// 환자 선택
	function setAdmission() {
		const selectTr = $("#tbAdmissionList tbody tr input[name=raFocus]:checked");
		const befAdmissionId = $("#admissionId").val();
		
		// 환자 선택 여부 확인
		if (selectTr.length === 0) {
			alert("환자를 선택하세요.");
			return;
		} else if (selectTr.length > 1) {
			alert("환자는 한명만 선택 가능합니다.");
			return;
		}
		
		// 입소ID
		const admissionId = selectTr.eq(0).closest("tr").data("admissionId");
		// 센터ID
		const centerId = selectTr.eq(0).closest("tr").data("centerId");
		
		// 선택 입소정보가 다를 경우 입소자 장비정보 조회
		if (befAdmissionId !== admissionId) {
			getPatientEquipInfo(admissionId, centerId);
		}
		
		// 모달 닫기
		$('#admissionModal').modal('hide');
		$('.modal-backdrop').remove();
	}
	
	//------------------------------------
	// 장비 모달
	//------------------------------------
	// 장비 모달 호출
	function showEquipmentModal() {
		let isShow = true;
		
		// 환자 선택 여부
		if (!$("#patientId").val()) {
			alert("환자를 선택하세요.");
			isShow = false;
		}
		// 장비 데이터 존재여부
		else if ($("#tbEquipment tbody tr").length === 0) {
			alert("선택 가능한 장비가 존재하지 않습니다.");
			isShow = false;
		}
		
		if (!isShow) {
			// 모달 닫기
			$('#EquipmentModal').modal('hide');
			$('.modal-backdrop').remove();
		}
	}
	
	// 장비 모달 오픈
	$("#EquipmentModal").on("shown.bs.modal", function(e) {
		// 기존 선택 내역 초기화
		$("#tbEquipment tbody tr").removeClass("hcTrSelect");
		$("#tbEquipment tbody tr").find("input[name=chkFocus]").prop("checked", false);
		
		// 기존 장비 내역 선택
		$("#equipAdd")
			.find("input[name=equipId]")
			.each(function(index, item) {
				console.log($(this).val());
				focusEquipment($(this).val());
			});
	});
	
	// 장비 선택 처리
	function focusEquipment(equipId) {
		if (!equipId) {
			return;
		}
		
		let trSelect = $("#tbEquipment tbody tr[data-equip-id='" + equipId + "']");
		
		// class 존재여부에 따른 checkbox 및 하이라이트 적용
		if (trSelect.hasClass("hcTrSelect")) {
			trSelect.removeClass("hcTrSelect");
			trSelect.find("input[name=chkFocus]")[0].checked = false;
		} else {
			trSelect.addClass("hcTrSelect");
			trSelect.find("input[name=chkFocus]")[0].checked = true;
		}
	}
	
	// 장비 선택-모달
	function setEquipmentModal() {
		// 선택 장비 데이터 Array 변경
		let patientEquipData = $("#tbEquipment tbody tr input[name=chkFocus]:checked").closest("tr").map(function() { return $(this).data() }).toArray();
		
		setEquipment(patientEquipData);		
	}

	// 장비 선택
	function setEquipment(arr) {
		// 장비 추가 영역
 		const addArea = $('#equipAdd');

 		// 장비영역 초기화
 		addArea.empty();

		// 선택 장비 정보 추가
		arr.forEach(function(obj, idx) {
			addArea.append(
				'<div class="input-group mt-1">'
          + '    <input type="text" name="equipId" class="form-control" readonly value="' + obj.equipId + '"/>'
          + '    <input type="text" name="equipNm" class="form-control" readonly value="' + obj.equipNm + '"/>'
          + '    <button type="button" class="btn btn-danger" onclick="delEquipment(this)"><i class="bi bi-x"></i></button>'
          + '</div>'
			);
		});
	}
	
	// 장비 삭제
	function delEquipment(obj) {
		$(obj).closest("div").remove();
	}
    setSelectMenuName('입소자별 사용장비',1,1);
</script>
</body>
</html>