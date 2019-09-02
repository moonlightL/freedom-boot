var Server = (function($) {
    var ServerInfo = {
        init: function(url) {
            $.freedom.action.request(url,null, function(resp) {
                var server = resp.data;
                ServerInfo.setJvmInfo(server.jvm);
                ServerInfo.setBasicInfo(server.basic);
                ServerInfo.setCpuInfo(server.cpu);
                ServerInfo.setMemoryInfo(server.memory);
                ServerInfo.setHardwareInfo(server.hardwareList);
            });
        },
        setJvmInfo: function(jvm) {
            for (var key in jvm) {
                $("#" + key).text(jvm[key]);
            }
        },
        setBasicInfo: function(basic) {
            for (var key in basic) {
                $("#" + key).text(basic[key]);
            }
        },
        setCpuInfo: function(cpu) {
            for (var key in cpu) {
                $("#" + key).text(cpu[key]);
            }
        },
        setMemoryInfo: function(memory) {
            for (var key in memory) {
                $("#" + key).text(memory[key]);
            }
        },
        setHardwareInfo: function(hardwareList) {
            var html = [];
            for(var i = 0, len = hardwareList.length; i < len; i++) {
                var hardware = hardwareList[i];
                html.push("<tr><td class='w200' style='text-align: left'>" + hardware.name + "</td>");
                html.push("<td class=''>" + hardware.type + "</td>");
                html.push("<td class=''>" + hardware.totalSpace + "</td>");
                html.push("<td class=''>" + hardware.usabledSpace + "</td>");
                html.push("<td class=''>" + hardware.freeSpace + "</td></tr>");
            }

            $("#hardwareTable").find("tbody").html(html.join(""));
        }

    }

    return {
        init: ServerInfo.init
    }

})(jQuery);