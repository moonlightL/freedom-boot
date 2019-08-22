$(function() {

    Terminal.applyAddon(fit);

    terminalManager.term = new Terminal({
        cols: 120,
        rows: 24,
        cursorBlink: true
    });

    terminalManager.term.open(document.getElementById('terminal'));

    $("#connect-btn").on("click", function () {

        var $hostname = $("#hostname").val();
        var $username = $("#username").val();
        var $password = $("#password").val();

        if ($hostname == "" || $username == "" || $password == "") {
            $.freedom.modal.msg("连接信息不能为空", 2);
            return;
        }

        terminalManager.connect($hostname, $username, $password);
    });

    $("#disconnect-btn").on("click", function () {
        terminalManager.disconnect();
    });

    window.onresize = function() {
        terminalManager.term.fit();
        terminalManager.term.scrollToBottom();
    };

});

var terminalManager = {
    webSocket: null,
    term: null,
    bindStatus: false,
    connect: function(hostname, username, password) {
        if (terminalManager.webSocket != null) {
            $.freedom.modal.msg("webSocket 已连接", 1);
            return false;
        }

        var host = window.location.host;
        var url = "ws://" + host + "/webSocketServer?hostname=" + hostname + "&username=" + username + "&password=" +  Crypto.AES.enc(password);
        if ('WebSocket' in window) {
            terminalManager.webSocket = new WebSocket(url);
        } else if ('MozWebSocket' in window) {
            terminalManager.webSocket = new MozWebSocket(url);
        }

        terminalManager.webSocket.onopen = function(evnt) {
            $("#result").html("连接服务器成功!<br/>")

            if (!terminalManager.bindStatus) {
                terminalManager.term.on('data', function(data) {
                    terminalManager.webSocket.send(data);
                });
                terminalManager.bindStatus = !terminalManager.bindStatus;
            }
        };

        terminalManager.webSocket.onmessage = function(evnt) {
            terminalManager.term.write(evnt.data);
        };

        terminalManager.webSocket.onerror = function(evnt) {
            $("#result").html($("#result").html() + "报错!<br/>")
            terminalManager.disconnect();
        };

        terminalManager.webSocket.onclose = function(evnt) {
            $("#result").html($("#result").html() + "与服务器断开连接!<br/>");
        }

        $("#connect-btn").prop("disabled", true);
        $("#disconnect-btn").prop("disabled", false);
    },
    disconnect: function () {
        if (terminalManager.webSocket == null) {
            $.freedom.modal.msg("webSocket 连接已断开");
            return;
        }

        $("#connect-btn").prop("disabled", false);
        $("#disconnect-btn").prop("disabled", true);

        terminalManager.webSocket.close();
        terminalManager.term.reset();
        terminalManager.webSocket = null;
    }
}