/**
 * Created by fu on 2017/9/23.
 */
function createItem(index,phone,source,time) {
    var html =
        "<tr>" +
        "<td>" +
        index+
        "</td>"+
        "<td>" +
        phone+
        "</td>"+
        "<td>" +
        source+
        "</td>"+
        "<td>" +
        time+
        "</td>"+
        "</tr>";

    $("#dataTable").append(html);
}

function createDownItem(index,platform,source,time) {
    var html =
        "<tr>" +
        "<td>" +
        index+
        "</td>"+
        "<td>" +
        platform+
        "</td>"+
        "<td>" +
        source+
        "</td>"+
        "<td>" +
        time+
        "</td>"+
        "</tr>";

    $("#dataTable").append(html);
}

function querylist() {

    var token = document.cookie;
    var base = new Base64();

    var startTime = document.getElementById("start_year").value+
        "-"+document.getElementById("start_month").value+
        "-"+document.getElementById("start_day").value;

    var stopTime = document.getElementById("stop_year").value+
        "-"+document.getElementById("stop_month").value+
        "-"+document.getElementById("stop_day").value;

    jQuery.ajax({
        dataType:"text",
        url:"queryData",
        success:function (data) {

            var jsonObj = jQuery.parseJSON(data);
            var array = jsonObj.result;

            $("#dataTable").empty();

            var html =
                "<tr style='background-color: lightcyan'>"+
                "<th>序号</th>"+
                "<th>手机号</th>"+
                "<th>来源</th>"+
                "<th>注册时间</th>"+
                "</tr>";

            $("#dataTable").append(html);


            if(jsonObj.errorCode == 200){

                $('#reg_count').text("注册:"+jsonObj.d_size+"条");

                $.each(jsonObj.result,function (i,item) {

                    var date = new Date(item.time);
                    var year = date.getFullYear();
                    var month = date.getMonth()+1;
                    var day = date.getDate();
                    var hh = date.getHours();
                    var mm = date.getMinutes();
                    var ss = date.getSeconds();

                    var time = year+"-"+month+"-"+day+" "+hh+":"+mm+":"+ss;

                    createItem(i+1,item.phone,base.decode(item.source),time);
                });
            }else{
                window.location.href="index.html";
            }

        },
        error:function (timeout, error, notmodified) {

        },
        timout:10000,
        type:"POST",
        data:{
            action:"source",
            startTime:startTime,
            stopTime:stopTime,
            token:token
        }
    });
}

function queryDownlist() {

    var token = document.cookie;
    var base = new Base64();

    var startTime = document.getElementById("start_year").value+
        "-"+document.getElementById("start_month").value+
        "-"+document.getElementById("start_day").value;

    var stopTime = document.getElementById("stop_year").value+
        "-"+document.getElementById("stop_month").value+
        "-"+document.getElementById("stop_day").value;

    jQuery.ajax({
        dataType:"text",
        url:"queryData",
        success:function (data) {

            var jsonObj = jQuery.parseJSON(data);
            var array = jsonObj.result;

            $("#dataTable").empty();

            var html =
                "<tr style='background-color: lightcyan'>"+
                "<th>序号</th>"+
                "<th>平台</th>"+
                "<th>来源</th>"+
                "<th>下载时间</th>"+
                "</tr>";

            $("#dataTable").append(html);


            if(jsonObj.errorCode == 200){

                $('#reg_count').text("下载:"+jsonObj.d_size+"条");

                $.each(jsonObj.result,function (i,item) {

                    var date = new Date(item.time);
                    var year = date.getFullYear();
                    var month = date.getMonth()+1;
                    var day = date.getDate();
                    var hh = date.getHours();
                    var mm = date.getMinutes();
                    var ss = date.getSeconds();

                    var time = year+"-"+month+"-"+day+" "+hh+":"+mm+":"+ss;
                    var platform = "其它";
                    if(item.p_type == parseInt("1")){
                        platform = "安卓";
                    }else if(item.p_type == parseInt("2")){
                        platform = "苹果";
                    }
                    createDownItem(i+1,platform,base.decode(item.source),time);

                });
            }else{
                window.location.href="index.html";
            }

        },
        error:function (timeout, error, notmodified) {

        },
        timout:10000,
        type:"POST",
        data:{
            action:"down",
            startTime:startTime,
            stopTime:stopTime,
            token:token
        }
    });
}

function setDefalutDate() {

    var date = new Date();

    $('#start_year').val(date.getFullYear());
    $('#start_month').val(date.getMonth()+1);
    $('#start_day').val(date.getDate());
    $('#stop_year').val(date.getFullYear());
    $('#stop_month').val(date.getMonth()+1);
    $('#stop_day').val(date.getDate());
}