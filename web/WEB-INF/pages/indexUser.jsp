<!DOCTYPE html>
<html lang="UTF-8">
    <head>
        <jsp:include page="/WEB-INF/includes/pageHeaderMetaInc.jsp"></jsp:include>
        <jsp:include page="/WEB-INF/includes/pageHeaderStandarInc.jsp"></jsp:include>
        <jsp:include page="/WEB-INF/includes/pageHeaderStandarIncUser.jsp"></jsp:include>
        </head>
        <body>
        <jsp:include page="/WEB-INF/includes/pageNavBar.jsp"></jsp:include>

        <%@page import="Model.Usuario"%>
        <%
            Usuario usuario = null;
            try {
                usuario = (Usuario) session.getAttribute("USUARIO");
            } catch (Exception excepcion) {
                usuario = null;
            }

            if (usuario != null) {
        %>        
        <main role="main" class="pt-5 pb-5 mb-5">
            <div class="container-fluid">
                <div class="card mt-5">
                    <div class="card-header">
                        <div class="row h5">
                            <div class="col-6">
                                <i class="fas fa-landmark"></i> Portal Bancario<br/>
                                <small class="text-muted"><%= usuario.getDocumentoTipo().getNombre() + " - " + usuario.getDocumento()%></small>
                            </div>
                            <div class="col-6 text-right">
                                <%= usuario.getNombres() + " " + usuario.getApellidos()%> <br/> <small class="text-muted"><%= usuario.getEmail()%></small>
                            </div>                                
                        </div>				
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
                                                                data-row-style="rowStyle"
                                                                data-url="crudAjax.jsp?accion=consulta&tabla=productos&alcance=origen-tabla" >
                                                            </table>
                                                        </div>
                                                    </div>

                                                    <!-- Modal transferencia-->
                                                    <div class="modal fade" id="verMovimientos" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="verMovimientosLabel" aria-hidden="true">
                                                        <div class="modal-dialog modal-lg modal-dialog-centered">
                                                            <div class="modal-content">
                                                                <div class="modal-header">
                                                                    <h5 class="modal-title" id="staticBackdropLabel">Movimientos</h5>
                                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                        <span aria-hidden="true">&times;</span>
                                                                    </button>
                                                                </div>
                                                                <div class="modal-body">
                                                                    <div class="form-row">
                                                                        <div class="col-6">
                                                                            <div class="form-group">
                                                                                <label>ID</label>
                                                                                <input type="text" class="form-control font-weight-bolder" id="movimiento-ID" readonly="readonly">  
                                                                            </div> 
                                                                            <div class="form-group">
                                                                                <label>Numero</label>
                                                                                <input type="text" class="form-control font-weight-bolder" id="movimiento-NUMERO" readonly="readonly">  
                                                                            </div>                                                                             
                                                                        </div>
                                                                        <div class="col-6">                                                                            
                                                                            <div class="form-group">
                                                                                <label>Saldo</label>
                                                                                <input type="text" class="form-control font-weight-bolder text-right" id="movimiento-SALDO" readonly="readonly">  
                                                                            </div> 
                                                                            <div class="form-group">
                                                                                <label>Tipo</label>
                                                                                <input type="text" class="form-control font-weight-bolder" id="movimiento-TIPO" readonly="readonly">  
                                                                            </div>                                                                             
                                                                        </div>                                                                        
                                                                    </div>
                                                                    <div class="form-row">
                                                                        <div class="col-12">
                                                                            <table
                                                                                id="tableMovimientosProducto"
                                                                                data-show-refresh="true"
                                                                                data-id-field="ID"
                                                                                data-row-style="rowStyle"
                                                                                data-url="" >
                                                                            </table>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="modal-footer">
                                                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                                                                </div>
                                                            </div>
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
                                                                data-row-style="rowStyle"
                                                                data-url="crudAjax.jsp?accion=consulta&tabla=transferencias" >
                                                            </table>
                                                        </div>
                                                    </div>
                                                </div>

                                                <!-- Modal transferencia-->
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
                                                                            <small id="productoDestinoTransferenciaAyuda" class="form-text text-muted">Escriba el numero de cuenta al cual va a realizar la transferencia</small>
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
                                                                <button id="btnConfirmarRealizarTransaccion" type="button" class="btn btn-warning confirmacion" data-toggle="modal" data-target="#codigoConfirmacionTransferencia" data-accion="confirmacion" accesskey="R">Realizar Transaccion</button>
                                                                <!--<button type="button" class="btn btn-warning realizarInsercion" data-accion="insercion" data-tabla="transferencias" data-modal="realizarTransferencia" data-table="tableTransferencias">Realizar Transferencia</button>-->
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
                                                                data-row-style="rowStyle"
                                                                data-url="crudAjax.jsp?accion=consulta&tabla=pagos" >
                                                            </table>
                                                        </div>
                                                    </div>

                                                    <!-- Modal pago-->
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
                                                                                <select class="form-control mostrarValorPago" id="entidadPago" name="entidadPago" required="required" ></select>
                                                                                <small id="entidadPagoAyuda" class="form-text text-muted">Entidad a la que se le realizara el pago</small>
                                                                            </div>
                                                                        </div>  
                                                                        <div class="form-row">
                                                                            <div class="form-group col-12">
                                                                                <label for="referenciaPago" class="required">Referencia de pago</label>
                                                                                <input type="text" class="form-control mostrarValorPago" id="referenciaPago" name="referenciaPago" required="required" disabled="disabled">
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
                                                                    <button id="btnConfirmarRealizarPago" type="button" class="btn btn-warning confirmacion" data-toggle="modal" data-target="#codigoConfirmacion" data-accion="confirmacion" accesskey="R">Realizar pago</button>
                                                                    <!--<button type="button" class="btn btn-warning realizarInsercion" id="btnConfirmarRealizarPago" data-accion="insercion" data-tabla="pagos" data-modal="realizarPago" data-table="tablePagos")>Realizar Pago</button>-->
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>                                                        

                                                </div>

                                            </div>
                                        </div>

                                        <!-- Confirmation code Modal-->
                                        <div class="modal fade" id="codigoConfirmacion" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                            <div class="modal-dialog modal-dialog-centered">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title" id="staticBackdropLabel">C&oacutedigo de seguridad</h5>
                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                            <span aria-hidden="true">&times;</span>
                                                        </button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <form>
                                                            <div class="form-row">
                                                                <div class="form-group col-12">
                                                                    <label for="codigo" class="required">Ingrese el código de seguridad de 6 dígitos que fue enviado a su correo eléctronico registrado.</label>
                                                                    <input type="password" class="form-control" id="codigo" name="codigo" required="required" >

                                                                </div>
                                                            </div>

                                                        </form>
                                                    </div>
                                                    <div class="modal-footer">

                                                        <button type="button" class="btn btn-warning realizarInsercion" id="btnConfirmarCodigo" data-dismiss="modal" data-accion="insercion" data-tabla="pagos" data-modal="realizarPago" data-table="tablePagos")>Enviar</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>  

                                        <!-- Confirmation code Modal Trans-->
                                        <div class="modal fade" id="codigoConfirmacionTransferencia" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                            <div class="modal-dialog modal-dialog-centered">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title" id="staticBackdropLabel">C&oacutedigo de seguridad</h5>
                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                            <span aria-hidden="true">&times;</span>
                                                        </button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <form>
                                                            <div class="form-row">
                                                                <div class="form-group col-12">
                                                                    <label for="codigoT" class="required">Ingrese el código de seguridad de 6 dígitos que fue enviado a su correo eléctronico registrado.</label>
                                                                    <input type="password" class="form-control" id="codigoT" name="codigoT" required="required" >

                                                                </div>
                                                            </div>

                                                        </form>
                                                    </div>
                                                    <div class="modal-footer">

                                                        <button type="button" class="btn btn-warning realizarInsercion" id="btnConfirmarCodigo" data-dismiss="modal" data-accion="insercion" data-tabla="transferencias" data-modal="realizarTransferencia" data-table="tableTransferencias")>Enviar</button>
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
        <% } else { %>
        <main role="main" class="pt-5 pb-5 mb-5">
            <div class="alert alert-danger m-5" role="alert">
                <h4 class="alert-heading">Acceso denegado</h4>
                <p>Usted esta intentando ingresar a una zona restringida. Vuelva al <a href="index.jsp" class="font-weight-bolder">inicio</a> e ingrese sus credenciales para iniciar sesión.</p>
                <hr>
                <p class="mb-0">Acceso denegado.</p>
            </div>
        </main>
        <% }%>

        <jsp:include page="/WEB-INF/includes/pageFooter.jsp"></jsp:include>

    </body>
</html>