$(document).ready(function() {


    // FORM VALIDATION
    // =================================================================
    // Require Bootstrap Validator
    // http://bootstrapvalidator.votintsev.ru/getting-started/
    // http://bootstrapvalidator.votintsev.ru/api/
    // =================================================================

    var faIcon = {
        valid: 'fa fa-check-circle fa-lg text-success',
        invalid: 'fa fa-times-circle fa-lg',
        validating: 'fa fa-refresh'
    }

    $('#demo').bootstrapValidator({
        excluded: [':disabled'],
        feedbackIcons: faIcon,
        fields: {
            username: {
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    }
                }
            },
            nickname: {
                validators: {
                    notEmpty: {
                        message: '昵称不能为空'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    different: {
                        field: 'username',
                        message: '密码不能与用户名相同'
                    },
                    stringLength: {
                        min: 8,
                        message: '密码长度不能少于8位'
                    }
                }
            },
            gender: {
                validators: {
                    notEmpty: {
                        message: '性别不能为空'
                    }
                }
            },
            birthday: {
                validators: {
                    notEmpty: {
                        message: '出生日期不能为空'
                    },
                    date: {
                        format: 'YYYY-MM-DD',
                        message: '出生日期格式不正确'
                    }
                }
            },
            email: {
                validators: {
                    notEmpty: {
                        message: '邮箱地址不能为空'
                    },
                    emailAddress: {
                        message: '邮箱地址格式不正确'
                    }
                }
            },
        }
    }).on('success.form.bv', function(e) {
        e.preventDefault();
        var $form = $(e.target);
        // $.post($form.attr('action'), $form.serialize(), function(result) {
        //
        // }, 'json');
    });

});
