;(function($) {
    $.extend({
        module: {
            file: {
                options: {
                    id: "upload",
                    language: 'zh', //设置语言
                    dropZoneTitle: '可以将图片拖放到这里 …支持多文件上传',
                    uploadUrl: "", //上传的地址
                    allowedFileExtensions: ['jpg','jpeg','png', 'txt','doc', 'docx', 'xls','xlsx', 'ppt', 'pdf'], //接收的文件后缀
                    uploadAsync: true, //同步上传，后台用数组接收，true 异步上传，每次上传一个file,会调用多次接口，默认异步上传
                    showUpload: true, //是否显示上传按钮
                    showRemove: true, //显示移除按钮
                    showPreview: true, //是否显示预览
                    showCancel:true,   //是否显示文件上传取消按钮。默认为true。只有在AJAX上传过程中，才会启用和显示
                    showCaption: true,//是否显示文件标题，默认为true
                    browseClass: "btn btn-primary", //文件选择器/浏览按钮的CSS类。默认为btn btn-primary
                    dropZoneEnabled: true,//是否显示拖拽区域
                    minImageWidth: 32, //图片的最小宽度
                    minImageHeight: 32,//图片的最小高度
                    maxImageWidth: 1920,//图片的最大宽度
                    maxImageHeight: 1080,//图片的最大高度
                    maxFileSize: 1024 * 10 ,//单位为kb，如果为0表示不限制文件大小
                    minFileCount: 1, //每次上传允许的最少文件数。如果设置为0，则表示文件数是可选的。默认为0
                    maxFileCount: 5, //每次上传允许的最大文件数。如果设置为0，则表示允许的文件数是无限制的。默认为0
                    previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",//当检测到用于预览的不可读文件类型时，将在每个预览文件缩略图中显示的图标。默认为<i class="glyphicon glyphicon-file"></i>
                    callback: null // 文件请求后的回调函数
                },
                init: function(options) {
                    $.extend($.module.file.options, options || {});
                    var id = $.module.file.options.id;
                    $("#" + id).fileinput({
                        language: $.module.file.options.language,
                        dropZoneTitle: $.module.file.options.dropZoneTitle,
                        uploadUrl: $.module.file.options.uploadUrl,
                        allowedFileExtensions: $.module.file.options.allowedFileExtensions,
                        uploadAsync: $.module.file.options.uploadAsync,
                        showUpload: $.module.file.options.showUpload,
                        showRemove: $.module.file.options.showRemove,
                        showPreview: $.module.file.options.showPreview,
                        showCancel:$.module.file.options.showCancel,
                        showCaption: $.module.file.options.showCaption,
                        browseClass: $.module.file.options.browseClass,
                        dropZoneEnabled: $.module.file.options.dropZoneEnabled,
                        minImageWidth: $.module.file.options.minImageWidth,
                        minImageHeight: $.module.file.options.minImageHeight,
                        maxImageWidth: $.module.file.options.maxImageWidth,
                        maxImageHeight: $.module.file.options.maxImageHeight,
                        maxFileSize: $.module.file.options.maxFileSize,
                        minFileCount: $.module.file.options.minFileCount,
                        maxFileCount: $.module.file.options.maxFileCount,
                        previewFileIcon: $.module.file.options.previewFileIcon,
                        layoutTemplates:{
                            actionUpload:'',//去除上传预览缩略图中的上传图片
                            actionZoom:'',   //去除上传预览缩略图中的查看详情预览的缩略图标
                            actionDownload:'', //去除上传预览缩略图中的下载图标
                            actionDelete:'', //去除上传预览的缩略图中的删除图标
                        },
                        msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",//字符串，当文件数超过设置的最大计数时显示的消息 maxFileCount。默认为：选择上传的文件数（{n}）超出了允许的最大限制{m}。请重试您的上传！
                    }).on('filebatchpreupload', function(event, data) { //该方法将在上传之前触发
                        var id = $('#id option:selected').val();
                        if(id == 0){
                            return {
                                message: "请选择", // 验证错误信息在上传前要显示。如果设置了这个设置，插件会在调用时自动中止上传，并将其显示为错误消息。您可以使用此属性来读取文件并执行自己的自定义验证
                                data:{}
                            };
                        }
                    });

                    $("#" + id).on("fileuploaded", function(event, data, previewId, index) {
                        if (typeof $.module.file.options.callback == "function") {
                            $.module.file.options.callback(data);
                        }
                    });
                }
            }
        }
    });

})(jQuery);
