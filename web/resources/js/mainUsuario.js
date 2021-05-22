function rowStyle(row, index) {
    return {
        css: {
            cursor: 'pointer'
        }
    }
}

function currencyFormatter(value, row, index) {
    return formatCurrency(value);
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
                    $.confirm({title: "error", content: "Se presentó un error al cargar el producto \n" + jqXHR.responseText});
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
                    $.confirm({title: "error", content: "Se presentó un error al cargar la entidad \n" + jqXHR.responseText});
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
                {field: 'VALOR', title: 'Valor', class: 'text-nowrap text-right font-weight-bolder', formatter: currencyFormatter},
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
                {field: 'ORIGEN', title: 'Cuenta Origen', class: 'text-nowrap'},
                {field: 'DESTINO', title: 'Cuenta Destino', class: 'text-nowrap'},
                {field: 'TITULARDESTINO', title: 'Titular Producto Destino', class: 'text-nowrap'},
                {field: 'VALOR', title: 'Valor', class: 'text-nowrap text-right font-weight-bolder', formatter: currencyFormatter},
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
                {field: 'ID', title: 'id', class: 'text-nowrap'},
                {field: 'NUMERO', title: 'Número de Producto', class: 'text-nowrap'},
                {field: 'TIPO', title: 'Tipo de Producto', class: 'text-nowrap'},
                {field: 'SALDO', title: 'Saldo', class: 'text-nowrap text-right font-weight-bolder', formatter: currencyFormatter},
                {field: 'TITULAR', title: 'Documento Titular', class: 'text-nowrap'}
            ]
        ]
    });

    $('#tableMovimientosProducto').bootstrapTable('destroy').bootstrapTable({
        locale: 'es-ES',
        classes: 'table table-bordered table-hover table-sm table-responsive-sm table-striped',
        theadClasses: 'thead-light',
        columns: [
            [
                {field: 'ID', title: 'id', class: 'text-nowrap'},
                {field: 'ORIGEN', title: 'Origen', class: 'text-nowrap'},
                {field: 'DESTINO', title: 'Destino', class: 'text-nowrap'},
                {field: 'TIPO', title: 'Tipo', class: 'text-nowrap'},
                {field: 'FECHA', title: 'Fecha', class: 'text-nowrap'},
                {field: 'VALOR', title: 'Valor', class: 'text-nowrap text-right font-weight-bolder', formatter: currencyFormatter}
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

    $('.mostrarValorPago').on("change", function () {

        $.ajax({
            type: "GET",
            url: "crudAjax.jsp",
            data: {accion: 'consulta', tabla: 'referencias', entidad: $('#entidadPago').val(), referencia: $('#referenciaPago').val()},
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
                    $.confirm({title: "error", content: "Se presentó un error al cargar el producto \n" + jqXHR.responseText});
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
                        if (response.RESULTADO != undefined) {
                            if (response.RESULTADO == true) {
                                $('#productoDestinoTitularTransferencia').val(response.TITULAR);
                                $('#valorTransferencia').removeAttr("disabled");
                                $('#conceptoTransferencia').removeAttr("disabled");
                            } else {
                                $.confirm({title: "error", content: "No se ha encontrado un producto con el numero indicado"});
                            }
                        } else {
                            $.confirm({title: "error", content: "Debe ingresar un numero de cuenta destino para realizar la transferencia"});
                        }
                    }
                })
                .fail(function (jqXHR, textStatus, errorThrown) {
                    $.confirm({title: "error", content: "Se presentó un error al cargar el producto destino \n" + jqXHR.responseText});
                });
    });

    $('#tableProductos').on('click-row.bs.table', function (row, $element, field) {
        let loTable = $('#tableMovimientosProducto');
        let lcTableUrl = 'crudAjax.jsp?accion=consulta&tabla=movimientos&id=' + $element.ID;
        let loModalMovimientos = $('#verMovimientos');

        loTable.bootstrapTable('refreshOptions', {url: lcTableUrl});
       // loTable.bootstrapTable('refresh', {url: lcTableUrl});

        $.each($element, function (key, value) {
            value = (key=='SALDO'?currencyFormatter(value):value);
            $('#movimiento-'+key).val(value);
        });

        loModalMovimientos.modal('show');
    });

    $('#verMovimientos').on('show.bs.modal', function (event) {
        //$('#tableMovimientosProducto').bootstrapTable('refresh');
    });


    $('.modal').on('show.bs.modal', function (event) {
        $(this).find('input[name], select[name]').each(function () {
            $(this).val("");
        })
    });

    $('.realizarInsercion').on("click", function () {
        let lcModal = '#' + $(this).attr('data-modal');
        let lcTable = '#' + $(this).attr('data-table');
        var codigo = $('#codigo').val();
        if ($(this).attr('data-tabla') === 'transferencias') {
            codigo = $('#codigoT').val();
        }
        var laParams = {accion: $(this).attr('data-accion'), tabla: $(this).attr('data-tabla'), codigo: codigo};
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
                            $.confirm({title: "Resultado", content: response.MENSAJE});
                            if (response.RESULTADO == true) {
                                $(lcModal).modal('hide');
                                $(lcTable).bootstrapTable('refresh');
                            }
                        }
                    }
                })
                .fail(function (jqXHR, textStatus, errorThrown) {
                    $.confirm({title: "error", content: "Se presentó un error al cargar el producto destino \n" + jqXHR.responseText});
                });
    });
    $('.confirmacion').on("click", function () {
        let lcModal = '#' + $(this).attr('data-modal');
        let lcTable = '#' + $(this).attr('data-table');
        var laParams = {accion: $(this).attr('data-accion')};
        $(lcModal).find('input[name], select[name]').each(function () {
            laParams[$(this).attr('name')] = $(this).val();
        })

        $.ajax({
            type: "GET",
            url: "crudAjax.jsp",
            data: laParams,
            dataType: "json"
        })

    });

});