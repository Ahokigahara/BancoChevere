function queryParamsPagos() {
    var params = {};
    $('#toolbarPagos').find('input[name]').each(function () {
        params[$(this).attr('name')] = $(this).val();
    })
    return params;
}

function rowStyle(row, index) {
    return {
        css: {
            cursor: 'pointer'
        }
    }
}
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

$.fn.producto = function (toOpciones) {
    var opciones = $.extend({
        alcance: "",
        valor: "",
        funcion: false
    }, toOpciones);

    this.each(function () {
        var loSelect = $(this);
        var lcAlcance = loSelect.attr('data-alcance') ? loSelect.attr('data-alcance') : opciones.alcance;
        var lcValor = loSelect.attr('data-valor') ? loSelect.attr('data-valor') : opciones.valor;
        var lvFuncion = loSelect.attr('data-funcion') ? loSelect.attr('data-funcion') : opciones.funcion;

        loSelect.append('<option value=""></option>');

        $.ajax({
            type: "GET",
            url: "crudAjax.jsp",
            data: {accion: 'consulta', tabla: 'productos', alcance: lcAlcance},
            dataType: "json"
        })
                .done(function (response) {
                    if (response !== undefined) {
                        $.each(response, function (key, row) {
                            loSelect.append($("<option></option>").attr("value", row.VALOR).text(row.TEXTO));
                        });
                        if (typeof lvFuncion === 'function') {
                            lvFuncion();
                        }
                    }
                    loSelect.val(lcValor);
                })
                .fail(function (jqXHR, textStatus, errorThrown) {
                    console.log();
                    alert("Se presentó un error al cargar el producto \n" + jqXHR.responseText);
                });
    });
    return this;
};

$.fn.entidad = function (toOpciones) {
    var opciones = $.extend({
        valor: "",
        funcion: false
    }, toOpciones);

    this.each(function () {
        var loSelect = $(this);
        var lcValor = loSelect.attr('data-valor') ? loSelect.attr('data-valor') : opciones.valor;
        var lvFuncion = loSelect.attr('data-funcion') ? loSelect.attr('data-funcion') : opciones.funcion;

        loSelect.append('<option value=""></option>');

        $.ajax({
            type: "GET",
            url: "crudAjax.jsp",
            data: {accion: 'consulta', tabla: 'entidades'},
            dataType: "json"
        })
                .done(function (response) {
                    if (response !== undefined) {
                        $.each(response, function (key, row) {
                            loSelect.append($("<option></option>").attr("value", row.ID).text(row.NOMBRE));
                        });
                        if (typeof lvFuncion === 'function') {
                            lvFuncion();
                        }
                    }
                    loSelect.val(lcValor);
                })
                .fail(function (jqXHR, textStatus, errorThrown) {
                    console.log();
                    alert("Se presentó un error al cargar el entidad \n" + jqXHR.responseText);
                });
    });
    return this;
};

$(function () {
    $('#tablePagos').bootstrapTable('destroy').bootstrapTable({
        locale: 'es-ES',
        classes: 'table table-bordered table-hover table-sm table-responsive-sm table-striped',
        theadClasses: 'thead-light',
        columns: [
            [
                {field: 'CONSECUTIVO', title: 'ID'},
                {field: 'FECHA', title: 'Fecha de Pago', class: 'text-nowrap'},
                {field: 'ORIGEN', title: 'Producto Origen', class: 'text-nowrap'},
                {field: 'ENTIDAD', title: 'Entidad', class: 'text-nowrap'},
                {field: 'REFERENCIA', title: 'Referencia', class: 'text-nowrap'},
                {field: 'VALOR', title: 'Valor', class: 'text-nowrap'},
                {field: 'CONCEPTO', title: 'Concepto', class: 'text-nowrap'}
            ]
        ]
    });
    $('#tableTransferencias').bootstrapTable('destroy').bootstrapTable({
        locale: 'es-ES',
        classes: 'table table-bordered table-hover table-sm table-responsive-sm table-striped',
        theadClasses: 'thead-light',
        columns: [
            [
                {field: 'CONSECUTIVO', title: 'ID'},
                {field: 'FECHA', title: 'Fecha de Transferencia', class: 'text-nowrap'},
                {field: 'ORIGEN', title: 'Nombre Servicio - Referencia', class: 'text-nowrap'},
                {field: 'DEESTINO', title: 'Destino', class: 'text-nowrap'},
                {field: 'TITULARDESTINO', title: 'Titular Producto Destino', class: 'text-nowrap'},
                {field: 'VALOR', title: 'Valor', class: 'text-nowrap'},
                {field: 'CONCEPTO', title: 'Concepto', class: 'text-nowrap'}
            ]
        ]
    });
    $('#tableProductos').bootstrapTable('destroy').bootstrapTable({
        locale: 'es-ES',
        classes: 'table table-bordered table-hover table-sm table-responsive-sm table-striped',
        theadClasses: 'thead-light',
        columns: [
            [
                {field: 'NUMERO', title: 'Número de Producto', class: 'text-nowrap'},
                {field: 'TIPO', title: 'Tipo de Producto', class: 'text-nowrap'},
                {field: 'SALDO', title: 'Saldo', class: 'text-nowrap'},
                {field: 'ESTADO', title: 'Estado', class: 'text-nowrap'}
            ]
        ]
    });
    $('.producto').producto();
    $('#entidadPago').entidad();
    $('#entidadPago').on("change", function () {
        if ($(this).val() > 0) {
            $('#referenciaPago').removeAttr("disabled");
        } else {
            $('#referenciaPago').prop("disabled", "disabled").val("");
        }
    });

    $('#referenciaPago').on("change", function () {
        let loReferencia = $(this);

        $.ajax({
            type: "GET",
            url: "crudAjax.jsp",
            data: {accion: 'consulta', tabla: 'referencias', entidad: $('#entidadPago').val(), referecnia: loReferencia.val()},
            dataType: "json"
        })
                .done(function (response) {
                    if (response != undefined) {
                        if (response.VALOR != undefined) {
                            $('#valorPago').val(formatCurrency(response.VALOR));
                            $('#conceptoPago').val(response.CONCEPTO);
                        }
                    }
                })
                .fail(function (jqXHR, textStatus, errorThrown) {
                    console.log();
                    alert("Se presentó un error al cargar el producto \n" + jqXHR.responseText);
                });
    });
    $('#productoDestinoTransferencia').on("change", function () {
        let loProductoDestino = $(this);

        $.ajax({
            type: "GET",
            url: "crudAjax.jsp",
            data: {accion: 'consulta', tabla: 'productos', alcance: "destino", productoDestino: loProductoDestino.val()},
            dataType: "json"
        })
                .done(function (response) {
                    if (response != undefined) {
                        if (response.TITULAR != undefined) {
                            $('#productoDestinoTitularTransferencia').val(response.TITULAR);
                            $('#valorTransferencia').removeAttr("disabled");
                            $('#conceptoTransferencia').removeAttr("disabled");
                        }
                    }
                })
                .fail(function (jqXHR, textStatus, errorThrown) {
                    console.log();
                    alert("Se presentó un error al cargar el producto destino \n" + jqXHR.responseText);
                });
    });

    $('.modal').on('show.bs.modal', function (event) {
        $(this).find('input[name], select[name]').each(function () {
            $(this).val("");
        })
    });

    $('.realizarInsercion').on("click", function () {
        let lcModal = '#' + $(this).attr('data-modal');
        let lcTable= '#' + $(this).attr('data-table');
        var laParams = {accion: $(this).attr('data-accion'), tabla: $(this).attr('data-tabla')};
        $(lcModal).find('input[name], select[name]').each(function () {
            laParams[$(this).attr('name')] = $(this).val();
        })

        $.ajax({
            type: "GET",
            url: "crudAjax.jsp",
            data: laParams,
            dataType: "json"
        })
                .done(function (response) {
                    if (response != undefined) {
                        if (response.RESULTADO != undefined) {
                            alert(response.MENSAJE);
                            if (response.RESULTADO == true) {
                                $(lcModal).modal('hide');
                                $(lcTable).bootstrapTable('refresh');	
                            }
                        }
                    }
                })
                .fail(function (jqXHR, textStatus, errorThrown) {
                    console.log();
                    alert("Se presentó un error al cargar el producto destino \n" + jqXHR.responseText);
                });
    });
});