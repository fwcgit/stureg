/**
 * Created by fu on 2017/4/17.
 */

var sendBtnTag = 0;

function sendCode(id,btnId) {
	
    var phone = "";
    var t;
    var button = document.getElementById(btnId);
    button.disabled = 'false';
    phone = document.getElementById(id).value;
    
    if(sendBtnTag != 0) return;
    
    sendBtnTag = 1;
    
    setTimeout(function () {
    	sendBtnTag = 0;
    },500);
    
    if(phone == null){
    	sendBtnTag = 0;
    	button.disabled = false;
        alert("请输入合法的手机号！")
        retunr;
    }
    if(phone.length == 0){
    	sendBtnTag = 0;
    	button.disabled = false;
        alert("请输入合法的手机号！")
        retunr;
    }
   
    
    jQuery.ajax({
        dataType:"text",
        url:"sendCode",
        success:function (data) {
        	if(data == "1"){
        		startTimeCount(btnId);
        	}else{
        		button.disabled = false;
        		alert(data);
        	}
        },
        error:function (timeout, error, notmodified) {
        	button.disabled = false;
            alert("网络错误！");
        },
        timeout:10000,
        type:"POST",
        data:{
            phone:phone,
            type:0
        }
    });

}

function checkStatus(id,thisObj) {
    var check = document.getElementById(id);
    if(check.checked){
        thisObj.style.backgroundImage="url(img/check_normal.png)";
        check.checked = false;
    }else{
        thisObj.style.backgroundImage="url(img/check_select.png)";
        check.checked = true;
    }
}

function sendCodeCountTime(id) {
    var button = document.getElementById(id);
    var t;
    time--;
    if(time == 0 || !timeOn){
        clearTimeout(t);
        button.disabled = false;
        button.innerHTML = "发送验证码";
        return;
    }
    button.innerHTML = time+"s";
    setTimeout(function () {
        sendCodeCountTime(id);
    },1000);
}
var time = 60;
var timeOn = false;

function startTimeCount(id) {
    var button = document.getElementById(id);
    time = 60;
    timeOn = true;
    button.disabled = 'false';
    sendCodeCountTime(id);
}

function register(phoneid,codeid,pwdId,checkid) {
	
	 var button = document.getElementById('sendBtn');
	 
    var phone = "";
    var code = "";
    var pwd = "";
   
    phone = document.getElementById(phoneid).value;
    code = document.getElementById(codeid).value;
    pwd = document.getElementById(pwdId).value;
    
    var source = $('#'+checkid).attr('source');

    if(phone == null){
        alert("请输入合法的手机号！")
        retunr;
    }
    if(phone.length == 0){
        alert("请输入合法的手机号！")
        retunr;
    }

    if(code == null){
        alert("短信验证码不正确")
        retunr;
    }
    if(code.length == 0){
        alert("短信验证码不正确")
        retunr;
    }

    if(pwd == null){
        alert("密码不正确")
        retunr;
    }
    if(pwd.length == 0){
        alert("密码不正确")
        retunr;
    }

    pwd = pwdToMd5(pwd);
    jQuery.ajax({
        dataType:"text",
        url:"register",
        success:function (data) {
        	timeOn = false;
        	$('#'+phoneid).val('');
        	$('#'+codeid).val('');
        	$('#'+pwdId).val('');
        	if(data == "1"){
        		alert("注册成功！");
        		osType();
        	}else{
        		 alert(data);
        	}        
        },error:function (timeout, error, notmodified) {
        	timeOn = false;
        	$('#'+phoneid).val('');
        	$('#'+codeid).val('');
        	$('#'+pwdId).val('');
            alert("注册失败");
        },
        timeout:10000,
        type:"POST",
        data:{
            phone:phone,
            yzm:code,
            pwd:pwd,
            source:source
        }
    });
}

function pwdToMd5(pwd){
	return md5(pwd);
}

function osType()
{
	var u = navigator.userAgent;
	
	if(u.indexOf("Android")> -1 || u.indexOf("Linux") > -1)
	{
		window.location.href = "brrowWhite.apk";
	}
	if(u.indexOf("iPhone") > -1)
	{
		window.location.href = "https://itunes.apple.com/cn/app/jie-zou-da-shi/id1274096438?mt=8";	
	}

	//location.reload();
}





