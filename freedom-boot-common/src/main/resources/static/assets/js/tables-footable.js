
// Tables-FooTable.js
// ====================================================================
// This file should not be included in your project.
// This is just a sample how to initialize plugins or components.
//
// - ThemeOn.net -



$(window).on('load', function() {


    // FOO TABLES
    // =================================================================
    // Require FooTable
    // -----------------------------------------------------------------
    // http://fooplugins.com/footable-demos/
    // =================================================================


    // Row Toggler
    // -----------------------------------------------------------------
    $('#freedom-foo-row-toggler').footable();




    // Expand / Collapse All Rows
    // -----------------------------------------------------------------
    var fooColExp = $('#freedom-foo-col-exp');
    fooColExp.footable().trigger('footable_expand_first_row');


    $('#freedom-foo-collapse').on('click', function(){
        fooColExp.trigger('footable_collapse_all');
    });
    $('#freedom-foo-expand').on('click', function(){
        fooColExp.trigger('footable_expand_all');
    })





    // Accordion
    // -----------------------------------------------------------------
    $('#freedom-foo-accordion').footable().on('footable_row_expanded', function(e) {
        $('#freedom-foo-accordion tbody tr.footable-detail-show').not(e.row).each(function() {
            $('#freedom-foo-accordion').data('footable').toggleDetail(this);
        });
    });





    // Pagination
    // -----------------------------------------------------------------
    $('#freedom-foo-pagination').footable();
    $('#freedom-show-entries').change(function (e) {
        e.preventDefault();
        var pageSize = $(this).val();
        $('#freedom-foo-pagination').data('page-size', pageSize);
        $('#freedom-foo-pagination').trigger('footable_initialized');
    });







    // Filtering
    // -----------------------------------------------------------------
    var filtering = $('#freedom-foo-filtering');
    filtering.footable().on('footable_filtering', function (e) {
        var selected = $('#freedom-foo-filter-status').find(':selected').val();
        e.filter += (e.filter && e.filter.length > 0) ? ' ' + selected : selected;
        e.clear = !e.filter;
    });

    // Filter status
    $('#freedom-foo-filter-status').change(function (e) {
        e.preventDefault();
        filtering.trigger('footable_filter', {filter: $(this).val()});
    });

    // Search input
    $('#freedom-foo-search').on('input', function (e) {
        e.preventDefault();
        filtering.trigger('footable_filter', {filter: $(this).val()});
    });








    // Add & Remove Row
    // -----------------------------------------------------------------
    var addrow = $('#freedom-foo-addrow');
    addrow.footable().on('click', '.freedom-delete-row', function() {

        //get the footable object
        var footable = addrow.data('footable');

        //get the row we are wanting to delete
        var row = $(this).parents('tr:first');

        //delete the row
        footable.removeRow(row);
    });

    // Search input
    $('#freedom-input-search2').on('input', function (e) {
        e.preventDefault();
        addrow.trigger('footable_filter', {filter: $(this).val()});
    });

    // Add Row Button
    $('#freedom-btn-addrow').click(function() {

        //get the footable object
        var footable = addrow.data('footable');

        //build up the row we are wanting to add
        var newRow = '<tr><td><button class="freedom-delete-row btn btn-danger btn-xs"><i class="pli-cross"></i></button></td><td>Adam</td><td>Doe</td><td>Traffic Court Referee</td><td>22 Jun 1972</td><td><span class="label label-table label-success">Active</span></td></tr>';

        //add it
        footable.appendRow(newRow);
    });
});
