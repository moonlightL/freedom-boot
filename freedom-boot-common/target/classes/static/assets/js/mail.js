
// Mail.js
// ====================================================================
// This file should not be included in your project.
// This is just a sample how to initialize plugins or components.
//
// - ThemeOn.net -



 $(document).on('ready', function() {



    // MAILBOX-COMPOSE.HTML
    // =================================================================

    if ($('#freedom-mail-compose').length) {


        // SUMMERNOTE
        // =================================================================
        // Require Summernote
        // http://hackerwins.github.io/summernote/
        // =================================================================
        $('#freedom-mail-compose').summernote({
            height:500
        });


        // Show The CC Input Field
        // =================================================================
        $('#freedom-toggle-cc').on('click', function(){
            $('#freedom-cc-input').toggleClass('hide');
        });



        // Show The BCC Input Field
        // =================================================================
        $('#freedom-toggle-bcc').on('click', function(){
            $('#freedom-bcc-input').toggleClass('hide');
        });



        // Attachment button.
        // =================================================================
        $('.btn-file :file').on('fileselect', function(event, numFiles, label, fileSize) {
            $('#freedom-attach-file').html('<strong class="box-block text-capitalize"><i class="fa fa-paperclip fa-fw"></i> '+label+'</strong><small class="text-muted">'+fileSize+'</small>');
        });


        return;
    }





    // MAILBOX-MESSAGE.HTML
    // =================================================================

    // SUMMERNOTE
    // =================================================================
    // Require Summernote
    // http://hackerwins.github.io/summernote/
    // =================================================================
    if( $('#freedom-mail-textarea').length ){
        $('#freedom-mail-textarea').on('click', function(){
            $(this).empty().summernote({
                height:300,
                focus: true
            });
            $('#freedom-mail-send-btn').removeClass('hide');
        });
        return;
    }





});

