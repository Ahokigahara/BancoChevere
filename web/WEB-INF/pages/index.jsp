<!DOCTYPE html>
<html lang="UTF-8">
    <head>
        <jsp:include page="/WEB-INF/includes/pageHeaderMetaInc.jsp"></jsp:include>
        <jsp:include page="/WEB-INF/includes/pageHeaderStandarInc.jsp"></jsp:include>
        </head>
        <body>

            <nav class="navbar navbar-expand-md navbar navbar-light bg-light fixed-top">
                <a class="navbar-brand" href="index.jsp"><img src="/BancoChevere/resources/images/background/desk_icono_bmu.svg" alt="" class="img-logo"><soan class="text-bmu pl-3"><%= session.getAttribute("WEBSITE-TITLE")%></span></a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarsExampleDefault">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item active">
                            <a class="nav-link" href="index.jsp">Inicio</a>
                        </li>
                    </ul>
                </div>
            </nav>

            <main role="main" class="pb-5 mb-5">

                <!-- Main jumbotron for a primary marketing message or call to action -->
                <div class="jumbotron" style="background-image: url(/BancoChevere/resources/images/background/secure.jpg); background-repeat: no-repeat; background-size: cover;">
                    <div class="container mt-3">
                    <h1 class="display-3">Supla las necesidades de liquidez</h1>
                    <h1 class="display-2">con plazos flexibles y cómodos</h1>
                    <p >Cupo de Crédito destinado a cubrir las necesidades de Capital de Trabajo. Durante su vigencia puede ser usado total o parcialmente a través del Portal Transaccional, debido a que, a medida que se amortiza, libera el saldo del cupo para poder ser nuevamente utilizado. Cuenta con Garantía del Fondo Nacional de Garantías.</p>
                </div>
            </div>

            <div class="container">
                <!-- Example row of columns -->
                <div class="row">
                    <div class="col-md-4">
                        <h2>Créditos<br/><small>Financia lo que sueñas, quieres y necesitas.</small></h2>
                        <p>Es el momento de lograr tus sueños. Opciones ideales para esos proyectos o ideas que necesitan apoyo financiero..</p>
                        <p><a class="btn btn-secondary" href="#" role="button">View details &raquo;</a></p>
                    </div>
                    <div class="col-md-4">
                        <h2>Tarjetas de crédito<br/><small>Paga, compra y gana puntos usando tus tarjetas.</small></h2>
                        <p>Usando nuestras tarjetas de crédito tu también ganas..</p>
                        <p><a class="btn btn-secondary" href="#" role="button">View details &raquo;</a></p>
                    </div>
                    <div class="col-md-4">
                        <h2>Seguros<br/><small>Tu seguridad es la de todos, con nuestros seguros.</small></h2>
                        <p>Pensamos en ti y en la seguridad de tu familia..</p>
                        <p><a class="btn btn-secondary" href="#" role="button">View details &raquo;</a></p>
                    </div>
                </div>

                <hr>

            </div> <!-- /container -->

        </main>

        <footer class="container-fluid navbar fixed-bottom navbar-expand-sm navbar-dark bg-light" style="min-height: 5em;">
            <img src="/BancoChevere/resources/images/background/desk_logo_bmu.svg" alt="" class="img-logo">
            <img src="/BancoChevere/resources/images/background/desk_logo_vigilado.svg" alt="" class="img-logo">
        </footer>

    </body>
</html>