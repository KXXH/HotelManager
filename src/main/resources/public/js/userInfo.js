$(function(){
    $("#nav").load("/public/nav.html");
});

var userInfo = new Vue({
    methods: {
        getList: function () {
            $.ajax(
                {
                    url: '/change/PersonInfo',
                    type: 'POST',
                    dataType: "json",
                    contentType: "application/json; charset=utf-8",
                    success: function (json) {
                        console.log(json.username);
                        $('#username').val(json.username);
                        $('#email').val(json.email);
                        $('#Gender').val(json.gender);
                        $('#Avatar').val(json.avatar);
                        $('#avatar_img').attr("src", json.avatar);
                    }
                });
        }
    }
});



function changePassword() {
    var url = '/user/changePasswd';

    var j = {"OldPassword":$('#oldPassword').val(),"NewPassword":$('#newPassword').val(),"Confirmation":$('#verification').val()};

    $.post(url,j,function (json) {
        if(json.status=="ok")
            alert("修改密码成功!");
        else
            alert(json.msg);
        $('#oldPassword').val("");
        $('#newPassword').val("");
        $('#verification').val("");
    })

}
function changeInfo() {
    var j = {"username":$('#username').val(),"email":$('#email').val(),"gender":$('#Gender').val(),"avatar":$('#Avatar').val()};
    $.post("/user/updateUserinfo",j,function (json) {
        if (json.status=="ok"){
            alert("修改信息成功！");
        }
        else{
            alert(json.msg);
        }
    }).error(function(json){
        console.log(JSON.stringify(json));
        alert(JSON.parse(json.responseText).msg);
        userInfo.getList();
    })
}
function upload() {
    var file=$('#Smfile')[0].files[0];
    if(file.size>500*1024){
        alert('您选择的图片太大，不能上传！');
        return;
    }
    var j=new FormData();
    j.append('smfile',file);
    var url='https://sm.ms/api/upload';
    $.ajax(
        {
            type:'POST',
            url:url,
            data:j,
            cache: false,
            processData: false,
            contentType: false,
            success:function(json){
                $("#Avatar").val(json.data.url);
                console.log($("#Avatar").val());
                document.getElementById("avatar_img").src=json.data.url;
            }
        }
    );
}