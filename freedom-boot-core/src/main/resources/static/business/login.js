$(function() {

    if (window.parent != window) {
        window.parent.location.href = "/";
    }

    $("#verify-code-img").on("click", function() {
        $(this).attr("src", $(this).attr("src") + "?id=" + Math.random());
    });

    $("#login-btn").on("click", function() {
        loginManager.getPublicKey();
    });

    $(document).on("keyup", function(e) {
        if (e.keyCode == 13) {
            loginManager.getPublicKey();
        }
    });
});

var loginManager = {
    getPublicKey: function() {
        $.ajax({
            url: "/core/login/publicKey.json",
            type: "POST",
            dataType: "json",
            success: function(resp) {
                if (resp.success) {
                    var encrypt = new JSEncrypt();
                    encrypt.setPublicKey(resp.data);
                    loginManager.login(encrypt.encrypt($("#password").val()))
                }
            }
        });

    },
    login: function(encryptPwd) {
        $.ajax({
            url: "/core/login/login.json",
            type: "POST",
            data: {username: $("#username").val(), password: encryptPwd, verifyCode: $("#verifyCode").val(), rememberMe: $("#rememberMe").prop("checked")},
            dataType: "json",
            success: function(resp) {
                if (!resp.success) {
                    layer.msg(resp.msg, {offset: 't'});
                    $("#verify-code-img").trigger("click");
                    return;
                }
                window.location.href = resp.data.path;
            }
        });
    }
}