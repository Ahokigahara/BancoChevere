﻿<!DOCTYPE html>
<html lang="UTF-8">
    <head>
        <jsp:include page="/WEB-INF/includes/pageHeaderMetaInc.jsp"></jsp:include>
        <jsp:include page="/WEB-INF/includes/pageHeaderStandarInc.jsp"></jsp:include>
        <jsp:include page="/WEB-INF/includes/pageHeaderStandarIncUser.jsp"></jsp:include>
        </head>
        <body>
        <jsp:include page="/WEB-INF/includes/pageNavBar.jsp"></jsp:include>

            <main role="main" class="pt-5 pb-5 mb-5">
                <div class="container-fluid">
                    <div class="card mt-5">
                        <div class="card-header">
                            <h5><i class="fas fa-landmark"></i> Portal Bancario</h5>				
                        </div>

                        <div class="card-body">
                            <div class="card-title">
                                <div class="row">

                                </div>
                                <div class="row edicion">
                                    <div class="col">
                                        <ul class="nav nav-tabs" id="propiedades" role="tablist">
                                            <li class="nav-item " role="presentation"><a class="text-dark nav-link active" id="tab-productos" data-toggle="tab" href="#tabProductos" role="tab" aria-controls="productos" aria-selected="true"><i class="fas fa-file-invoice-dollar mr-3"></i>Productos</a></li>
                                            <li class="nav-item " role="presentation"><a class="text-dark nav-link" id="tab-transferencias" data-toggle="tab" href="#tabTransferencias" role="tab" aria-controls="transferencias" aria-selected="false"><i class="fas fa-money-bill-wave-alt mr-3"></i>Transferencias</a></li>
                                            <li class="nav-item" role="presentation"><a class="text-dark nav-link" id="tab-pagos" data-toggle="tab" href="#tabPagos" role="tab" aria-controls="pagos" aria-selected="false"><i class="fas fa-hand-holding-usd mr-3"></i>Pagos</a></li>
                                        </ul>
                                        <div class="card border-top-0">
                                            <div class="tab-content" id="TabPropiedades">
                                                <!-- Modulo de Productos -->
                                                <div class="tab-pane fade show active" id="tabProductos" role="tabpanel" aria-labelledby="tab-productos">
                                                    <div class="card-body">
                                                        <div class="form-group row">
                                                            <div class="col-12">
                                                                <div id="toolbarProductos">

                                                                </div>
                                                                <table
                                                                    id="tableProductos"
                                                                    data-show-export="false"
                                                                    data-toolbar="#toolbarProductos"
                                                                    data-show-refresh="true"
                                                                    data-click-to-select="true"
                                                                    data-show-export="false"
                                                                    data-show-columns="true"
                                                                    data-show-columns-toggle-all="true"
                                                                    data-minimum-count-columns="5"
                                                                    data-id-field="CONSECUTIVO"
                                                                    data-query-params="queryParamsProductos"
                                                                    data-row-style="rowStyle"
                                                                    data-url="crudAjax.jsp?accion=consulta&tabla=productos&alcance=origen-tabla" >
                                                                </table>
                                                            </div>
                                                        </div>


                                                    </div>
                                                </div>
                                                <!-- Modulo Transferencia -->
                                                <div class="tab-pane fade" id="tabTransferencias" role="tabpanel" aria-labelledby="tab-transferencias">
                                                    <div class="card-body">

                                                        <div class="form-group row">
                                                            <div class="col-12">
                                                                <div id="toolbarTransferencias">
                                                                    <button id="btnRealizarTransferencia" type="button" class="btn btn-warning" data-toggle="modal" data-target="#realizarTransferencia" accesskey="R">Realizar transferencia</button>
                                                                </div>
                                                                <table
                                                                    id="tableTransferencias"
                                                                    data-show-export="false"
                                                                    data-toolbar="#toolbarTransferencias"
                                                                    data-show-refresh="true"
                                                                    data-click-to-select="true"
                                                                    data-show-export="false"
                                                                    data-show-columns="true"
                                                                    data-show-columns-toggle-all="true"
                                                                    data-minimum-count-columns="5"
                                                                    data-id-field="CONSECUTIVO"
                                                                    data-query-params="queryParamsTransferencias"
                                                                    data-row-style="rowStyle"
                                                                    data-url="crudAjax.jsp?accion=consulta&tabla=transferencias" >
                                                                </table>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <!-- Modal -->
                                                    <div class="modal fade" id="realizarTransferencia" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                                        <div class="modal-dialog modal-dialog-centered">
                                                            <div class="modal-content">
                                                                <div class="modal-header">
                                                                    <h5 class="modal-title" id="staticBackdropLabel">Realizar Transferencia</h5>
                                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                        <span aria-hidden="true">&times;</span>
                                                                    </button>
                                                                </div>
                                                                <div class="modal-body">
                                                                    <form>
                                                                        <div class="form-row">
                                                                            <div class="form-group col-12">
                                                                                <label for="productoOrigenTransferencia" class="required">Producto Origen</label>
                                                                                <select class="form-control producto" id="productoOrigenTransferencia" name="productoOrigenTransferencia" data-alcance="origen" required="required" ></select>
                                                                                <small id="productoOrigenAyuda" class="form-text text-muted">Selecione de la lista el producto desde donde quiere realizar el transferencia</small>
                                                                            </div>
                                                                        </div>

                                                                        <div class="form-row">
                                                                            <div class="form-group col-12">
                                                                                <label for="productoDestinoTransferencia" class="required">Número del producto destino</label>
                                                                                <input type="text" class="form-control" id="productoDestinoTransferencia" name="productoDestinoTransferencia" required="required">
                                                                                <small id="productoDestinoTransferenciaAyuda" class="form-text text-muted">Ecriba el numero de cuenta al cual va a realizar la transferencia</small>
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-row">
                                                                            <div class="form-group col-12">
                                                                                <label for="productoDestinoTitularTransferencia" class="required">Titular del producto destino</label>
                                                                                <input type="text" class="form-control" id="productoDestinoTitularTransferencia" name="productoDestinoTitularTransferencia" disabled="disabled">
                                                                                <small id="productoDestinoTitularTransferenciaAyuda" class="form-text text-muted">Aqui aparece el titular de la cuenta del producto destino</small>
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-row">
                                                                            <div class="form-group col-12">
                                                                                <label for="valorTransferencia" class="required">Valor del transferencia</label>
                                                                                <input type="number" class="form-control" id="valorTransferencia" name="valorTransferencia"  required="required" disabled="disabled">
                                                                                <small id="valorTransferenciaAyuda" class="form-text text-muted">Valor de transferencia a realizar</small>
                                                                            </div>
                                                                        </div>  
                                                                        <div class="form-row">
                                                                            <div class="form-group col-12">
                                                                                <label for="conceptoTransferencia" class="required">Concepto</label>
                                                                                <input type="text" class="form-control" id="conceptoTransferencia" name="conceptoTransferencia" required="required" disabled="disabled">
                                                                                <small id="conceptoTransferenciaAyuda" class="form-text text-muted">Concepto de la referencia</small>
                                                                            </div>
                                                                        </div>                                                                              
                                                                    </form>
                                                                </div>
                                                                <div class="modal-footer">
                                                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                                                                    <button type="button" class="btn btn-warning realizarInsercion" data-accion="insercion" data-tabla="transferencias" data-modal="realizarTransferencia" data-table="tableTransferencias">Realizar Transferencia</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>      
                                                </div>
                                                <!-- Modulo de Pagos -->
                                                <div class="tab-pane fade" id="tabPagos" role="tabpanel" aria-labelledby="tab-pagos">
                                                    <div class="card-body">

                                                        <div class="form-group row">
                                                            <div class="col-12">
                                                                <div id="toolbarPagos">
                                                                    <button id="btnRealizarPago" type="button" class="btn btn-warning" data-toggle="modal" data-target="#realizarPago" accesskey="R">Realizar pago</button>
                                                                </div>
                                                                <table
                                                                    id="tablePagos"
                                                                    data-show-export="false"
                                                                    data-toolbar="#toolbarPagos"
                                                                    data-show-refresh="true"
                                                                    data-click-to-select="true"
                                                                    data-show-export="false"
                                                                    data-show-columns="true"
                                                                    data-show-columns-toggle-all="true"
                                                                    data-minimum-count-columns="5"
                                                                    data-id-field="CONSECUTIVO"
                                                                    data-query-params="queryParamsPagos"
                                                                    data-row-style="rowStyle"
                                                                    data-url="crudAjax.jsp?accion=consulta&tabla=pagos" >
                                                                </table>
                                                            </div>
                                                        </div>

                                                        <!-- Modal -->
                                                        <div class="modal fade" id="realizarPago" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                                            <div class="modal-dialog modal-dialog-centered">
                                                                <div class="modal-content">
                                                                    <div class="modal-header">
                                                                        <h5 class="modal-title" id="staticBackdropLabel">Realizar Pago</h5>
                                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                            <span aria-hidden="true">&times;</span>
                                                                        </button>
                                                                    </div>
                                                                    <div class="modal-body">
                                                                        <form>
                                                                            <div class="form-row">
                                                                                <div class="form-group col-12">
                                                                                    <label for="productoOrigenPago" class="required">Producto Origen</label>
                                                                                    <select class="form-control producto" id="productoOrigenPago" name="productoOrigenPago" data-alcance="origen" required="required" ></select>
                                                                                    <small id="productoOrigenPagoAyuda" class="form-text text-muted">Selecione de la lista el producto desde donde quiere realizar el pago</small>
                                                                                </div>
                                                                            </div>
                                                                            <div class="form-row">
                                                                                <div class="form-group col-12">
                                                                                    <label for="entidadPago" class="required">Entidad</label>
                                                                                    <select class="form-control" id="entidadPago" name="entidadPago" required="required" ></select>
                                                                                    <small id="entidadPagoAyuda" class="form-text text-muted">Entidad a la que se le realizara el pago</small>
                                                                                </div>
                                                                            </div>  
                                                                            <div class="form-row">
                                                                                <div class="form-group col-12">
                                                                                    <label for="referenciaPago" class="required">Referencia de pago</label>
                                                                                    <input type="text" class="form-control" id="referenciaPago" name="referenciaPago" required="required" disabled="disabled">
                                                                                    <small id="referenciaPagoAyuda" class="form-text text-muted">Ingrese la referncia de pago</small>
                                                                                </div>
                                                                            </div>        
                                                                            <div class="form-row">
                                                                                <div class="form-group col-12">
                                                                                    <label for="valorPago" class="required">Valor del pago</label>
                                                                                    <input type="text" class="form-control" id="valorPago"  required="required" readonly="readonly">
                                                                                    <small id="valorPagoAyuda" class="form-text text-muted">Valor de pago a realizar</small>
                                                                                </div>
                                                                            </div>  
                                                                            <div class="form-row">
                                                                                <div class="form-group col-12">
                                                                                    <label for="conceptoPago" class="required">Concepto</label>
                                                                                    <input type="text" class="form-control" id="conceptoPago"  required="required" disabled="disabled">
                                                                                    <small id="conceptoPagoAyuda" class="form-text text-muted">Concepto de la referencia</small>
                                                                                </div>
                                                                            </div>                                                                              
                                                                        </form>
                                                                    </div>
                                                                    <div class="modal-footer">
                                                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                                                                        <button type="button" class="btn btn-warning realizarInsercion" id="btnConfirmarRealizarPago" data-accion="insercion" data-tabla="pagos" data-modal="realizarPago" data-table="tablePagos")>Realizar Pago</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>                                                        

                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="card-footer text-muted">
                            <p>info</p>
                        </div>
                    </div>
                </div>
            </main>

        <jsp:include page="/WEB-INF/includes/pageFooter.jsp"></jsp:include>

    </body>
</html>