;(function($) {
    $.extend({
        freedom: {
            /**
             * ui 展示
             */
            ui: {
                table: {
                    options: {
                        id: "listTable", // table id
                        baseUrl: "",
                        addUIUrl: "saveUI.html", // 新增页面地址
                        editUIUrl: "updateUI/{id}.html", // 编辑页面地址
                        detailUIUrl: "detailUI/{id}.html", // 详情页面地址
                        listUrl: "list.json", // 列表数据地址
                        deleteUrl: "remove.json", // 删除地址
                        queryFormId: "queryForm", // 搜索框表单 id
                        pagination: true, // 是否分页
                        toolbar: "#toolbar",
                        type: "table", // 类型 table/tree-table
                        expandColumn: 1,
                        height: $(window.parent).height() - 320,
                        parentId: ""
                    },
                    init: function(options) {
                        $.extend($.freedom.ui.table.options, options || {});
                        var $table = $("#" + $.freedom.ui.table.options.id);
                        if ($.freedom.ui.table.options.type == "table") {
                            $table.bootstrapTable('destroy').bootstrapTable({
                                url: $.freedom.ui.table.getListUrl(),
                                columns: options.columns,
                                height: $.freedom.ui.table.options.height,
                                idField : 'id',
                                fixedColumns: true,
                                fixedNumber: $.freedom.ui.table.options.fixedNumber,
                                toolbar: $.freedom.ui.table.options.toolbar,
                                detailView : false,
                                cache: false,
                                minimumCountColumns : 2,
                                clickToSelect : true,
                                pagination: $.freedom.ui.table.options.pagination,
                                sidePagination : 'server',
                                paginationLoop : true,
                                silentSort : false,
                                smartDisplay : false,
                                escape : true,
                                maintainSelected : true,
                                showRefresh: true,
                                showExport: true,
                                showFooter: true,
                                showToggle: false, // 切换视图
                                showFullscreen: false,
                                showColumns: true,
                                pageList: [10, 25, 50, 100],
                                queryParams: function(params) {
                                    return {
                                        pageNum: (params.offset / params.limit + 1) || 1, // 当前页
                                        pageSize: params.limit || 10,    // 页面大小
                                        searchData: params.searchData, // 查询内容
                                        sortName: params.sort,     // 排序字段名
                                        sortOrder: params.order,    // 排序方式 asc/desc
                                        search: params.search
                                    };
                                },
                                responseHandler : function(resp) {
                                    if (resp.success) {
                                        return {
                                            "total" : resp.data.total || 0, // 总页数
                                            "rows" : resp.data.list || [] // 数据
                                        };
                                    } else {
                                        return {
                                            "total" : 0, // 总页数
                                            "rows" : [] // 数据
                                        };
                                    }
                                },
                                detailFormatter: function(index, row) {
                                    var html = [];
                                    $.each(row, function(key, value) {
                                        html.push('<p><b>' + key + ':</b> ' + value + '</p>');
                                    });
                                    return html.join('');
                                },
                                onLoadSuccess: function(data) {
                                    // 控制按鈕失效状态
                                    $table.on('check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table', function () {
                                        $(".freedom-edit, .freedom-delete").each(function(index,domEle) {
                                            $(domEle).prop('disabled', !$table.bootstrapTable('getSelections').length);
                                        });
                                    });

                                    if (typeof options.init == "function") {
                                        options.init();
                                    }

                                    return false;
                                }
                            });
                        } else {
                            $table.bootstrapTreeTable('destroy').bootstrapTreeTable({
                                url: $.freedom.ui.table.getListUrl(),
                                toolbar: $.freedom.ui.table.options.toolbar,
                                expandColumn: $.freedom.ui.table.options.expandColumn,
                                columns: $.freedom.ui.table.options.columns,
                                height: $.freedom.ui.table.options.height,
                                parentId: $.freedom.ui.table.options.parentId,
                                responseHandler : function(resp) {
                                    return resp.success ? resp.data.list : [];
                                },
                                onClickRow: function(data) {
                                    // 控制按鈕失效状态
                                    $(".freedom-edit, .freedom-delete").each(function(index,domEle) {
                                        $(domEle).prop('disabled', false);
                                    });
                                }
                            });
                        }

                        // 绑定工具栏按钮事件
                        $.freedom.action.bindEvent();
                    },
                    getListUrl: function() {
                        return $.freedom.ui.table.formatUrl($.freedom.ui.table.options.listUrl);
                    },
                    getAddUIUrl: function() {
                        return $.freedom.ui.table.formatUrl($.freedom.ui.table.options.addUIUrl);
                    },
                    getEditUrl: function(id) {
                        return $.freedom.ui.table.formatUrl($.freedom.ui.table.options.editUIUrl.replace(/{id}/, id));
                    },
                    getDeleteUrl: function() {
                        return $.freedom.ui.table.formatUrl($.freedom.ui.table.options.deleteUrl);
                    },
                    getDetailUIUrl: function(id) {
                        return $.freedom.ui.table.formatUrl($.freedom.ui.table.options.detailUIUrl.replace(/{id}/, id));
                    },
                    formatUrl: function(url) {
                        return $.freedom.ui.table.options.baseUrl + "/" + url;
                    },
                    getSelections: function() {
                        if ($.freedom.ui.table.options.type == "table") {
                            return $("#" + $.freedom.ui.table.options.id).bootstrapTable('getSelections');
                        } else if ($.freedom.ui.table.options.type == "tree-table") {
                            return $("#" + $.freedom.ui.table.options.id).bootstrapTreeTable('getSelections');
                        }
                    },
                    refreshData: function() {
                        if ($.freedom.ui.table.options.type == "table") {
                            $("#" + $.freedom.ui.table.options.id).bootstrapTable('refresh');
                        } else if ($.freedom.ui.table.options.type == "tree-table") {
                            $("#" + $.freedom.ui.table.options.id).bootstrapTreeTable('refresh');
                        }
                    },
                    refreshOption: function(options) {
                        if ($.freedom.ui.table.options.type == "table") {
                            $("#" + $.freedom.ui.table.options.id).bootstrapTable('refreshOptions', options);
                        } else if ($.freedom.ui.table.options.type == "tree-table") {
                            $("#" + $.freedom.ui.table.options.id).bootstrapTreeTable('refreshOptions', options);
                        }
                    }
                },
                tree: {
                    zTreeObj: null,
                    setting: function() {
                        var setting = {
                            data: {
                                simpleData: {
                                    enable: true,
                                    idKey: "id",
                                    pIdKey: "pid"
                                }
                            },
                            check: {
                                enable: true,
                                chkStyle: "checkbox",
                                chkboxType: { "Y": "ps", "N": "s" }
                            }
                        };

                        return setting;
                    },
                    init: function(id, data) {
                        $.freedom.ui.tree.zTreeObj = $.fn.zTree.init($("#" + id), $.freedom.ui.tree.setting(), data);
                        return $.freedom.ui.tree.zTreeObj;
                    }
                }
            },
            /**
             * 事件处理
             */
            action: {
                bindEvent: function() {
                    $($.freedom.ui.table.options.toolbar).on("click", function(e) {
                        var $target = $(e.target);
                        if ($target.hasClass("freedom-add")) {
                            $.freedom.action.showAddUI($target);
                        } else if ($target.hasClass("freedom-edit")) {
                            $.freedom.action.showEditUI($target);
                        } else if ($target.hasClass("freedom-delete")) {
                            $.freedom.action.delete();
                        } else if ($target.hasClass("freedom-query")) {
                            $.freedom.action.query();
                        }
                    });
                },
                showAddUI: function($target) {
                    if (!$.freedom.ui.table.options.addUIUrl) {
                        $.freedom.modal.msg("未设置新增页面地址");
                        return;
                    }

                    var width = $target.data("width") || 800;
                    var height = $target.data("height") || $(window).height() - 80;
                    var isFull = $target.data("full");

                    $.freedom.modal.window("新增", $.freedom.ui.table.getAddUIUrl(), width, height, isFull);
                },
                showEditUI: function($target) {
                    if (!$.freedom.ui.table.options.editUIUrl) {
                        $.freedom.modal.msg("未设置编辑页面地址");
                        return;
                    }

                    var selections = $.freedom.ui.table.getSelections();
                    var rowNum = selections.length;
                    if (rowNum == 0 || rowNum > 1) {
                        $.freedom.modal.msg("请选择一条记录进行编辑操作");
                        return;
                    }

                    var width = $target.data("width") || 800;
                    var height = $target.data("height") || $(window).height() - 80;
                    var isFull = $target.data("full");

                    $.freedom.modal.window("编辑", $.freedom.ui.table.getEditUrl(selections[0].id),  width, height, isFull);
                },
                showDetailUI: function(id, $target) {
                    if (!$.freedom.ui.table.options.detailUIUrl) {
                        $.freedom.modal.msg("未设置详情页面地址");
                        return;
                    }

                    if (!id) {
                        $.freedom.modal.msg("请选择一条记录进行编辑操作");
                        return;
                    }

                    var width = $target.data("width") || 800;
                    var height = $target.data("height") || $(window).height() - 80;
                    var isFull = $target.data("full");

                    $.freedom.modal.window("详情", $.freedom.ui.table.getDetailUIUrl(id),  width, height, isFull);
                },
                /**
                 *  注意：此函数对内使用，外部不要直接使用
                 * @param url
                 * @param data
                 * @param fn
                 */
                save: function(url, data, fn) {
                    if (typeof fn != "function") {
                        fn = function(resp) {
                            $.freedom.modal.msg(resp.msg, 1, function() {
                                window.parent.$.freedom.modal.closeAll();
                                window.parent.$.freedom.ui.table.refreshData();
                            });
                        }
                    }

                    if (data instanceof FormData) {
                        $.freedom.action.requestByFormData(url, data, fn);
                    } else {
                        $.freedom.action.request(url, data, fn);
                    }
                },
                delete: function(id) {
                    if (!$.freedom.ui.table.options.deleteUrl) {
                        $.freedom.modal.msg("未设置删除地址");
                        return;
                    }
                    var idArr = [];
                    if (!id) {
                        var selections = $.freedom.ui.table.getSelections();
                        var rowNum = selections.length;
                        if (rowNum == 0) {
                            $.freedom.modal.msg("请选择记录进行删除操作");
                            return;
                        }

                        for(var i = 0,len = selections.length; i < len; i++) {
                            idArr.push(selections[i].id);
                        }
                    } else {
                        idArr.push(id);
                    }

                    $.freedom.modal.confirm("确定要删除该记录吗？", function() {
                        $.freedom.action.submit($.freedom.ui.table.getDeleteUrl(), {idStr: idArr.join(",")}, function(resp) {
                            $.freedom.ui.table.refreshData();
                        });
                    });
                },
                query: function() {
                    var $queryForm = $("#" + $.freedom.ui.table.options.queryFormId);
                    var $queryBtn = $queryForm.find("button[type='submit']");

                    $queryBtn.on("click", function(e) {
                        e.preventDefault();
                        var formParamArr = $queryForm.serializeArray();
                        var options = $("#" + $.freedom.ui.table.options.id).bootstrapTable('getOptions');
                        options.queryParams = function(params) {
                           var parameter = {
                                pageNum: (params.offset / params.limit + 1) || 1, // 当前页
                                pageSize: params.limit || 10,    // 页面大小
                                sortName: params.sort,     // 排序字段名
                                sortOrder: params.order    // 排序方式 asc/desc
                            };

                           for (var i=0, len=formParamArr.length; i<len; i++) {
                               parameter[formParamArr[i].name] = formParamArr[i].value;
                           }
                           return parameter;
                        }

                        $.freedom.ui.table.refreshOption(options);
                    });

                    $queryForm.find("button[type='button']").on("click", function(e) {
                        $queryForm[0].reset();
                        $queryBtn.trigger("click");
                    });
                },
                /**
                 * 对外暴露的表单提交方法
                 * @param formId    表单 id
                 * @param formData  FormData 对象
                 * @param fn        回调函数
                 */
                submit: function(formId, fn) {
                    // 表单通过内嵌属性校验数据，具体 http://bootstrapvalidator.votintsev.ru/getting-started/
                    $('#' + formId).bootstrapValidator().on('success.form.bv', function(e) {
                        e.preventDefault();
                        var $form = $(e.target);
                        // 发送请求
                        $.freedom.action.save($form.attr('action'), $form.serialize(), fn);
                    });
                },
                submitByFormData: function(formId, formData, fn) {
                    $('#' + formId).bootstrapValidator().on('success.form.bv', function(e) {
                        e.preventDefault();
                        var $form = $(e.target);
                        // 发送请求
                        $.freedom.action.save($form.attr('action'), formData, fn);
                    });
                },
                download: function(url) {
                    var index = $.freedom.modal.window("下载", url, 600, 400);
                    setTimeout(function() {
                        $.freedom.modal.close(index);
                    },1500)
                },
                /**
                 * 对外暴露的请求方法
                 * @param url   请求地址
                 * @param data  请求参数（obj 类型）
                 * @param fn    回调函数
                 */
                request: function(url, data, fn) {
                    $.ajax({
                        type: "POST",
                        url: url,
                        data: data,
                        dataType: "JSON",
                        success: function(resp) {
                            if (typeof fn == "function") {
                                fn(resp);
                            } else {
                                // 默认操作
                                $.freedom.modal.msg(resp.msg, 1);
                            }
                        }
                    });
                },
                /**
                 * 对外暴露的请求方法
                 * @param url       请求地址
                 * @param formData  FormData 对象
                 * @param fn        回调地址
                 */
                requestByFormData: function(url, formData, fn) {
                    $.ajax({
                        type: "POST",
                        url: url,
                        data: formData,
                        processData : false,
                        contentType : false,
                        dataType: "JSON",
                        success: function(resp) {
                            if (typeof fn == "function") {
                                fn(resp);
                            } else {
                                // 默认操作
                                $.freedom.modal.msg(resp.msg, 1);
                            }
                        }
                    });
                }
            },
            /**
             * 拟态窗
             */
            modal: {
                msg: function(msg, icon, fn) {
                    layer.msg(msg, {icon: icon, time: 1500}, function() {
                        if (typeof fn == 'function') {
                            fn();
                        }
                    });
                },
                confirm: function(content, fn) {
                    var me = layer.confirm(content, function(index){
                        if (typeof fn == 'function') {
                            fn(index);
                        }
                        layer.close(me)
                    });
                },
                window: function(title, url, width, height, isFull) {
                    if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {
                        width = 'auto';
                        height = 'auto';
                    }

                    var index = layer.open({
                        title: title,
                        content: url,
                        type: 2,
                        area: [width + 'px', height + 'px'],
                        fix: false,
                        maxmin: true,
                        shade: 0.3,
                        shadeClose: true,
                        btn:[]
                    });

                    if (isFull) {
                        layer.full(index);
                    }

                    return index;
                },
                close: function(index) {
                    layer.close(index)
                },
                closeAll: function () {
                    layer.closeAll();
                }
            },
            /**
             * 存储
             */
            storage: {
                set: function(k ,v) {
                    if (typeof v != "string") {
                        v = JSON.stringify(v);
                    }
                    localStorage.setItem(k, v);
                },
                get: function(k) {
                    return localStorage.getItem(k);
                },
                remove: function(k) {
                    localStorage.removeItem(k);
                },
                clear: function() {
                    localStorage.clear();
                }
            }
        }
    })
})(jQuery);


$.ajaxSetup({
    dataType: "json",
    cache: false,
    xhrFields: { withCredentials: true },
    crossDomain: true,
    complete: function(xhr) {
        if (xhr.responseJSON && xhr.responseJSON.code && xhr.responseJSON.code != 200) {
            $.freedom.modal.msg(xhr.responseJSON.msg, 2, function() {
                if (xhr.responseJSON.code == 1004) {
                    window.parent.location.href = "/";
                }
            });
        }

        $(".freedom-edit, .freedom-delete").each(function(index,domEle) {
            $(domEle).prop('disabled', true);
        });
    }
});