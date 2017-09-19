var ajaxUrl = "ajax/profile/meals/";
var datatableApi;


function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize(),
        success: updateTableByData
    });
}

function saveMeal() {
    var dateFromPicker = $('#dateTimePicker').data('DateTimePicker').date();
    if (dateFromPicker) {
        console.log($('#dateTime').val(dateFromPicker.format().substr(0, 19)));
    }
    save();
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, updateTableByData);
}

$(function () {
    datatableApi = $("#datatable").DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime",
                "render": function (date, type, row) {
                    if (type === "display") {
                        return date.substring(0, 10) + " " +date.substring(11, 16);
                    }
                    return date;
                }
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }

        ],
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "createdRow": function (row, data, dataIndex) {
            if (data.exceed) {
                $(row).addClass("exceeded");
            }else{
                $(row).addClass("normal");
            }
        },
        "initComplete": makeEditable
    });
    // makeEditable();
    $('#datePickerStart').datetimepicker({
        format: 'L',
        format: 'YYYY-MM-DD'
    });
    $('#datePickerEnd').datetimepicker({
        format: 'L',
        format: 'YYYY-MM-DD'
    });
    $('#timePickerStart').datetimepicker({
        format: 'LT',
        format: 'HH:mm'
    });
    $('#timePickerEnd').datetimepicker({
        format: 'LT',
        format: 'HH:mm'
    });

    $('#dateTimePicker').datetimepicker({
        format: 'DD.MM.YYYY HH:mm'
    });


});

