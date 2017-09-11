var ajaxUrl = "ajax/meal/";
var datatableApi;

var dataFilterApi = function () {
    isSet:false;
};

$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ]
    });
    makeEditable();
})

function filter() {
    var form = $("#filterForm");
    dataFilterApi.isSet = ($("#filterForm #startDate").val() || $("#filterForm #endDate").val() || $("#filterForm #startTime").val() || $("#filterForm #endTime").val());
    if (dataFilterApi.isSet) {
        $.ajax({
            type: "POST",
            url: ajaxUrl + "filter",
            data: form.serialize(),
            success: function (data) {
                datatableApi.clear().rows.add(data).draw();
                successNoty("Filtered");
            }
        });
    } else updateTable();
}

function filterCancel() {
    if (dataFilterApi.isSet) {
        $("#filterForm").find(":input").val("");
        dataFilterApi.isSet = false;
        updateTable()
    }
}

