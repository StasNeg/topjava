var ajaxUrl = "ajax/admin/users/";
var datatableApi;
var dataFilterApi = function () {
    isSet:false;
};


// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });
    makeEditable();
});


function changeIsEnabled(id, isEnabled) {
    $.ajax({
        type: "POST",
        url: ajaxUrl + "enabled",
        data: {idUser: id, enabled : isEnabled},
        success: function () {
            updateTable();
            successNoty("Change");
        }
    });
}

