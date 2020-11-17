var idFlag = false;
var pwFlag = false;
var pwcheckFlag = false;

var idRe = /^\w{5,20}$/;
//var pwRe = /^[\w!@#$%^&*()]{8,20}$/;
var lengRe = /.{8,20}/;
var engRe = /[a-zA-Z]/;
var numRe = /\d/;
var spcRe = /[!@#$%^&*()]/;


$(function () {
    // 아이디 유효성 체크
    //keydown . click
    $("#chkId").click(function () {
    	console.log("button click event")
        if ($("#id").val() == '') {
            $("#idNotice").text("아이디를 입력해 주세요");
            idFlag = false;
            flagChk();
        } else if (idRe.test($("#id").val())) {
        	
        	var userId = $("#id").val();
            $.ajax({
                url: "/idcheck?id=" + userId,
                type: "GET",
                success: function (data) {
                    if (data) {
                    	$("#idNotice").text("중복된 아이디 입니다");
                        idFlag = false;
                        flagChk();
                    } else {
                        $("#idNotice").text("사용 가능한 아이디 입니다");
                        idFlag = true;
                        flagChk();
                    }
                }
            });
        } else {
            $("#idNotice").text("5~20자 영어, 숫자, 특수문자 _ 사용 가능");
            idFlag = false;
            flagChk();
        }
    });

    // 비밀번호 유효성 체크
    $("#pw").keyup(function () {
        pwEqual();
		var match1 = engRe.test($("#pw").val())
		var match2 = numRe.test($("#pw").val())
		var match3 = spcRe.test($("#pw").val())
        if ($("#pw").val() == '') {
            $("#pwNotice").text("비밀번호를 입력해 주세요");
            pwFlag = false;
            flagChk();
        } else if (lengRe.test($("#pw").val())) {
			if( (match1 && match2) || (match2 && match3) || (match1 && match3) ){
				$("#pwNotice").text("사용 가능한 비밀번호 입니다");
				pwFlag = true;
				flagChk();
			} else {
				$("#pwNotice").text("영어, 숫자, 특수문자 !@#$%^&*()_중 두개이상 사용");
			}
        } else {
            $("#pwNotice").text("8~20자 만 사용 가능");
            pwFlag = false;
            flagChk();
        }
    });

    // 비밀번호 확인 유효성 체크
    $("#pwcheck").keyup(function () {
    	pwEqual();
    });
});

// 회원가입 버튼 활성화
function flagChk() {
    if (idFlag && pwFlag && pwcheckFlag) {
        $("#submitButton").attr("disabled", false);
    } else {
        $("#submitButton").attr("disabled", true);
    }
}

function pwEqual() {
	if ($("#pwcheck").val() == '') {
		console.log("여기는 공백");
		$("#pwChkNotice").text("비밀번호 확인을 입력해 주세요");
        pwcheckFlag = false;
        flagChk();
	} else if ($("#pw").val() == $("#pwcheck").val()) {
		console.log("여기는 비교");
        $("#pwChkNotice").text("비밀번호가 일치합니다");
        pwcheckFlag = true;
        flagChk();
    } else {
    	console.log("여기는 정답");
        $("#pwChkNotice").text("비밀번호를 확인해 주세요");
        pwcheckFlag = false;
        flagChk();
    }
}