/**
 * Created by fu on 2017/9/24.
 */
function getOsType() {

    var u = navigator.userAgent;

    if(u.indexOf("Android")> -1 || u.indexOf("Linux") > -1)
    {
        return 1;
    }

    if(u.indexOf("iPhone") > -1)
    {
        return 2;
    }

    return 3;
}

function download(url)
{
    var u = navigator.userAgent;

    if(u.indexOf("Android")> -1 || u.indexOf("Linux") > -1)
    {
        window.location.href = url;
        return;
    }
    if(u.indexOf("iPhone") > -1)
    {
        window.location.href = url;
        return;
    }

    window.location.href = url;
}

function getDownUrl() {
    var osType = getOsType();
    var source = $('#body_html').attr('source');
    var key = $('#body_html').attr('key');
    jQuery.ajax({
       dataType:"text",
       url:"download",
       success:function (data) {
           var obj = JSON.parse(data);
            if(obj.errorCode == 200){
                download(obj.result);
            }
       },
       error:function (timeout, error, notmodified) {

       },
       timeout:10000,
       type:"POST",
       data:{
           action:"download",
           os:osType,
           key:key,
           source:source
       }
    });
}