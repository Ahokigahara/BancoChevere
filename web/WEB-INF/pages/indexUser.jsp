<!DOCTYPE html>
<html lang="UTF-8">
    <head>
        <jsp:include page="/WEB-INF/includes/pageHeaderMetaInc.jsp"></jsp:include>
        <jsp:include page="/WEB-INF/includes/pageHeaderStandarInc.jsp"></jsp:include>

        </head>
        <body>
        <jsp:include page="/WEB-INF/includes/pageNavBar.jsp"></jsp:include>

            <main role="main" class="pt-5 pb-5 mb-5">
                <div class="container-fluid">
                    <div class="card mt-5">
                        <div class="card-header">
                            <h5>Portal Bancario</h5>				
                        </div>

                        <div class="card-body">
                            <div class="card-title">
                                <div class="row">

                                </div>
                                <div class="row edicion">
                                    <div class="col">
                                        <ul class="nav nav-tabs" id="propiedades" role="tablist">
                                            <li class="nav-item " role="presentation"><a class="text-dark nav-link active" id="tab-productos" data-toggle="tab" href="#tabProductos" role="tab" aria-controls="productos" aria-selected="true">Productos</a></li>
                                            <li class="nav-item " role="presentation"><a class="text-dark nav-link" id="tab-transferencias" data-toggle="tab" href="#tabTransferencias" role="tab" aria-controls="transferencias" aria-selected="false">Transferencias</a></li>
                                            <li class="nav-item" role="presentation"><a class="text-dark nav-link" id="tab-pagos" data-toggle="tab" href="#tabPagos" role="tab" aria-controls="pagos" aria-selected="false">Pagos</a></li>
                                            <li class="nav-item" role="presentation"><a class="text-dark nav-link" id="d-tab" data-toggle="tab" href="#dTab" role="tab" aria-controls="d" aria-selected="false">D</a></li>
                                        </ul>
                                        <div class="card border-top-0">
                                            <div class="tab-content" id="TabPropiedades">
                                                <div class="tab-pane fade show active" id="tabProductos" role="tabpanel" aria-labelledby="tab-productos">
                                                    <div class="card-body">

                                                    </div>
                                                </div>

                                                <div class="tab-pane fade" id="tabTransferencias" role="tabpanel" aria-labelledby="tab-transferencias">
                                                    <div class="card-body">
                                                        <form>
                                                            <div class="form-group row">
                                                                <label for="realizarTransferencia" class="col-sm-2 col-form-label">Realizar Transferencia</label>
                                                                <div class="col-sm-10 ">
                                                                    <button type="submit" class="btn btn-warning btn">Ver transferencias</button>
                                                                </div>
                                                            </div>
                                                            <div class="form-group row">
                                                                <label for="productoOrigen" class="col-sm-2 col-form-label">Producto Origen</label>
                                                                <div class="col-sm-10">
                                                                    <select class="form-control" id="exampleFormControlSelect1">
                                                                        <option>xxx1</option>
                                                                        <option>xxx2</option>
                                                                        <option>xxx3</option>
                                                                        <option>xxx4</option>
                                                                        <option>xxx5</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                            <div class="form-group row">
                                                                <label for="productoDestino" class="col-sm-2 col-form-label">Producto Destino</label>
                                                                <div class="col-sm-10">
                                                                    <select class="form-control" id="exampleFormControlSelect1">
                                                                        <option>xxx1</option>
                                                                        <option>xxx2</option>
                                                                        <option>xxx3</option>
                                                                        <option>xxx4</option>
                                                                        <option>xxx5</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                            <div class="form-group row">
                                                                <label for="valorTransferir" class="col-sm-2 col-form-label">Valor a Transferir</label>
                                                                <div class="col-sm-10">
                                                                  <input type="" class="form-control" id="inputValorTransferir" placeholder="Ingrese el valor a transferir">  
                                                                </div>
                                                            </div>
                                                            <div class="form-group row ">
                                                                  <button type="submit" class="btn btn-warning btn">Realizar transferencia</button>  
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>

                                                <div class="tab-pane fade" id="tabPagos" role="tabpanel" aria-labelledby="tab-pagos">
                                                    <div class="card-body">

                                                    </div>
                                                </div>

                                                <div class="tab-pane fade" id="dTab" role="tabpanel" aria-labelledby="d-tab">
                                                    <div class="card-body">

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