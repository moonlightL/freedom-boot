var Permission = (function ($) {

    var PermissionUI = {
        init: function(url, permissions) {
            PermissionUI.initModules(url);
            PermissionUI.initMenus(url, permissions);
            PermissionUI.bindEvent();
        },
        initModules: function(url) {
            $.freedom.action.request(url + "/moduleList.json", null, function (resp) {
                if (resp.data.length > 0) {
                    var htmlArr = [];
                    for (var i = 0; i < resp.data.length; i++) {
                        var module = resp.data[i];
                        htmlArr.push('<a href="#" class="list-group-item text-semibold text-main" data-id="' + module.id + '">');
                        var cls;
                        var state;
                        if (module.isLoad) {
                            cls = "badge-success";
                            state = "已加载";
                        } else {
                            cls = "badge-danger";
                            state = "未加载";
                        }
                        htmlArr.push(' <span class="badge ' + cls + ' pull-right">' + state + '</span>')
                        htmlArr.push('<span><i class="fa ' + module.icon + '"></i> ' + module.name + '</span>')
                        htmlArr.push('</a>')
                    }
                    $("#moduleList").html(htmlArr.join(""));

                    $("#moduleList").find("a").on("click", function () {

                        $(this).siblings().removeClass("active");
                        $(this).addClass("active");

                        var moduleId = $(this).data("id");
                        var options = $("#" + $.freedom.ui.table.options.id).bootstrapTreeTable('getOptions');
                        options.queryParams = {pid: moduleId};

                        $.freedom.ui.table.refreshOption(options);
                    });
                }
            });
        },
        initMenus: function(url, permissions) {
            $.freedom.ui.table.init({
                baseUrl: url,
                id: "treeTable",
                type: "tree-table",
                parentId: "pid",
                expandColumn: 1,
                height: $(window.parent).height() - 400,
                columns: [
                    {
                        radio: true
                    },
                    {
                        title: "名称",
                        field: "name"
                    },
                    {
                        title: "图标",
                        field: "icon"
                    },
                    {
                        title: "访问地址",
                        field: "url"
                    },
                    {
                        title: "模块编码",
                        field: "moduleCode"
                    },
                    {
                        title: "权限类型",
                        field: "perType",
                        align: "center",
                        formatter: function (value, item, index) {
                            var labelColor;
                            var info;
                            if (value == "1") {
                                info = "模块";
                                labelColor = "primary";
                            } else if (value == "2") {
                                info = "菜单";
                                labelColor = "success";
                            } else if (value == "3") {
                                info = "按钮";
                                labelColor = "warning";
                            }
                            return '<div class="label label-table label-' + labelColor + '"> ' + info + '</div>';
                        }
                    },
                    {
                        title: "权限编码",
                        field: "perCode"
                    },
                    {
                        title: "资源类型",
                        field: "resourceType",
                        align: "center",
                        formatter: function (value, item, index) {
                            var labelColor;
                            var info;
                            if (value == "1") {
                                info = "核心";
                                labelColor = "mint";
                            } else if (value == "2") {
                                info = "扩展";
                                labelColor = "info";
                            } else if (value == "3") {
                                info = "业务";
                                labelColor = "success";
                            }
                            return '<div class="label label-table label-' + labelColor + '"> ' + info + '</div>';
                        }
                    },
                    {
                        title: "状态",
                        field: "state",
                        align: "center",
                        formatter: function (value, item, index) {
                            var labelColor;
                            var info;
                            if (value == "0") {
                                info = "禁用";
                                labelColor = "danger";
                            } else if (value == "1") {
                                info = "正常";
                                labelColor = "success";
                            }
                            return '<div class="label label-table label-' + labelColor + '"> ' + info + '</div>';
                        }
                    },
                    {
                        title: "操作",
                        align: "center",
                        formatter: function (value, row) {
                            var btnArr = [];
                            btnArr.push(' <a class="btn btn-default btn-xs" href="javascript:;;" onclick="$.freedom.action.showDetailUI(\'' + row.id + '\')"><i class="fa fa-user-o"></i> 详情</a> ');
                            if (permissions.indexOf("core:permission:remove") > -1) {
                                btnArr.push('<a class="btn btn-danger btn-xs" href="javascript:;;" onclick="$.freedom.action.delete(\'' + row.id + '\')"><i class="fa fa-remove"></i> 删除</a>');
                            }

                            return btnArr.join('');
                        }
                    }
                ]
            });
        },
        bindEvent: function() {
            var state = false;
            $("#oc-btn").on("click", function () {
                if (state) {
                    $("#" + $.freedom.ui.table.options.id).bootstrapTreeTable("expandAll");
                } else {
                    $("#" + $.freedom.ui.table.options.id).bootstrapTreeTable("collapseAll");
                }
                state = !state;
            });
        }

    }

    return {
        init: PermissionUI.init
    };

})(jQuery);