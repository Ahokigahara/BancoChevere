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
                            <h5>Titulo</p>				
                        </div>

                        <div class="card-body">
                            <div class="card-title">
                                <div class="row">

                                </div>
                                <div class="row edicion">
                                    <div class="col">
                                        <ul class="nav nav-tabs" id="propiedades" role="tablist">
                                            <li class="nav-item" role="presentation"><a class="text-dark nav-link active" id="a-tab" data-toggle="tab" href="#aTab" role="tab" aria-controls="a" aria-selected="true">A</a></li>
                                            <li class="nav-item" role="presentation"><a class="text-dark nav-link" id="b-tab" data-toggle="tab" href="#bTab" role="tab" aria-controls="b" aria-selected="false">B</a></li>
                                            <li class="nav-item" role="presentation"><a class="text-dark nav-link" id="c-tab" data-toggle="tab" href="#cTab" role="tab" aria-controls="c" aria-selected="false">C</a></li>
                                            <li class="nav-item" role="presentation"><a class="text-dark nav-link" id="d-tab" data-toggle="tab" href="#dTab" role="tab" aria-controls="d" aria-selected="false">D</a></li>
                                        </ul>
                                        <div class="card border-top-0">
                                            <div class="tab-content" id="TabPropiedades">
                                                <div class="tab-pane fade show active" id="aTab" role="tabpanel" aria-labelledby="a-tab">
                                                    <div class="card-body">

                                                    </div>
                                                </div>

                                                <div class="tab-pane fade" id="bTab" role="tabpanel" aria-labelledby="b-tab">
                                                    <div class="card-body">

                                                    </div>
                                                </div>

                                                <div class="tab-pane fade" id="cTab" role="tabpanel" aria-labelledby="c-tab">
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