/**
 * Created by fu on 2017/5/16.
 */
function checkInput(){
    var user = document.getElementById("user").value;
    var pwd = document.getElementById("pwd").value;

    if(user == ""){
        alert("请输入用户名");
        return;
    }

    if(pwd == ""){
        alert("请输入密码");
        return;
    }


    jQuery.ajax({
        dataType:"text",
        url:"queryData",
        success:function (data) {
        	var obj = JSON.parse(data);
        	if(obj.errorCode == 200){
        	    //document.cookie = obj.result;
        		//alert("登陆成功");
        		$('#login_html').html(obj.result);
                setDefalutDate();
        	}else{
        		alert(obj.errorMsg);
        	}
        },
        error:function (timeout, error, notmodified) {
            alert("网络错误！");
        },
        timeout:10000,
        type:"POST",
        data:{
            action:"login",
            account:user,
            pwd:pwd
        }
    });

}