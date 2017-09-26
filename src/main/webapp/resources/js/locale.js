var select = document.getElementById('sell');
var locale;
var request;

select.addEventListener('change', function () {
    localStorage.setItem('selected', select.options[select.selectedIndex].value);
    locale = window.location.pathname;
    locale = locale.substring(locale.lastIndexOf("/") + 1);
    switch (locale) {
        case 'meals':
            request ='meals';
            break;
        case 'profile':
            request = 'profile';
            break;
        case 'users':
            request = 'users';
            break;
    }
    setNewLocale(request, select.options[select.selectedIndex].value);
});
document.addEventListener('DOMContentLoaded', function () {
    $('#sell').val(localStorage.getItem('selected'));
});

function setNewLocale(path, locale) {
    $.ajax({
        url: path,
        type: "get", //send it through get method
        data: {
            locale : locale },
        success: function(html) {
            document.location.reload();
        }
    });
}



