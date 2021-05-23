function formatCurrency(num) {
    num = num.toString().replace(/\$|\,/g, '');
    if (isNaN(num))
        num = "0";
    sign = (num == (num = Math.abs(num)));
    num = Math.floor(num * 100 + 0.50000000001);
    cents = num % 100;
    num = Math.floor(num / 100).toString();
    if (cents < 10)
        cents = "0" + cents;
    for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
        num = num.substring(0, num.length - (4 * i + 3)) + ',' +
                num.substring(num.length - (4 * i + 3));
    return (((sign) ? '' : '-') + '$' + num);
}

function goToURL(tcUrl) {
    window.location = tcUrl;
}

$(function () {
    $('#btnLogin').click(function () {
        $.ajax({
            type: "GET",
            url: "crudAjax.jsp",
            data: {accion: 'confirmacion'},
            dataType: "json"
        }).done(function (response) {
            $('#codigoConfirmacionSesion').modal('show');
            $('#confirmCodigoAlert').alert('close');
            $('#confirmCodigo').append($("<div></div>").attr('id','confirmCodigoAlert').addClass("alert alert-warning alert-dismissible alert-confirm-code fade show").html('<strong>Codio de confirmación:</strong> ' + response.CODIGO + '. Aplicación con fines educativos.<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>'));

        }).fail(function (jqXHR, textStatus, errorThrown) {
            $.confirm({title: "error", content: "Se presentó un error al validar el usuario. \n" + jqXHR.responseText});
        });

    });


    $('#btnConfirLoginCode').click(function () {
        $('#codigoConfirmacionSesion').modal('hide');
        $.ajax({
            type: "GET",
            url: "crudAjax.jsp",
            data: {accion: 'consulta', tabla: 'usuarios', tipo: 'validar', usuario: $('#user').val(), clave: $('#password').val(), codigo: $('#confirLoginCode').val()},
            dataType: "json"
        }).done(function (response) {
            if (response !== undefined) {
                if (response.ACCESO !== undefined) {
                    if (response.ACCESO == true) {
                        window.location = "indexUser.jsp";
                    } else {
                        $.confirm({title: "Acceso", content: response.MENSAJE});
                    }
                }
            }
        }).fail(function (jqXHR, textStatus, errorThrown) {
            $.confirm({title: "error", content: "Se presentó un error al validar el usuario. \n" + jqXHR.responseText});
        });
    });

    $('.modal').on('show.bs.modal', function (event) {
        $(this).find('input[name], select[name]').each(function () {
            $(this).val("");
        })
    });
});