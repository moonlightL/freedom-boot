
// Forms-Text-Editor.js
// ====================================================================
// This file should not be included in your project.
// This is just a sample how to initialize plugins or components.
//
// - ThemeOn.net -


$(document).ready(function() {

    // SUMMERNOTE
    // =================================================================
    // Require Summernote
    // http://hackerwins.github.io/summernote/
    // =================================================================
    $('#freedom-summernote, #freedom-summernote-full-width').summernote({
        height : '230px'
    });




    // SUMMERNOTE AIR-MODE
    // =================================================================
    // Require Summernote
    // http://hackerwins.github.io/summernote/
    // =================================================================
    $('#freedom-summernote-airmode').summernote({
        airMode: true
    });





    // SUMMERNOTE CLICK TO EDIT
    // =================================================================
    // Require Summernote
    // http://hackerwins.github.io/summernote/
    // =================================================================
    $('#freedom-edit-text').on('click', function(){
        $('#freedom-summernote-edit').summernote({focus: true});
    });


    $('#freedom-save-text').on('click', function(){
        $('#freedom-summernote-edit').summernote('destroy');
    });

})
