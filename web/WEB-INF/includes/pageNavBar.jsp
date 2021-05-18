<%
    String usuario = "";
    try {
        usuario = (String) session.getAttribute("USUARIO");
    } catch (Exception excepcion) {
        usuario = "";
    }
%>  
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
        <!-- Button trigger modal -->   
        <% if (usuario.length() <= 0) { %>
        <button type="button" class="btn btn-warning" data-toggle="modal" data-target="#loginModal">Ingresar</button>
        <% } else {%>
        <a class="btn btn-warning" href="index.jsp?salir">Salir <%= usuario%></a>
        <% }%>
    </div>
</nav>

<!-- Modal -->
<div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="loginModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="loginModalLabel">Login</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group row">
                        <label for="user" class="col-sm-2 col-form-label">Usuario</label>
                        <div class="col-sm-10">
                            <input type="" class="form-control" id="user" placeholder="Ingrese su Usuario">  
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="password" class="col-sm-2 col-form-label">Contraseña</label>
                        <div class="col-sm-10">
                            <input type="password" class="form-control" id="password" placeholder="Ingrese su Contraseña">  
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Salir</button>
                <button type="button" class="btn btn-warning" id="btnLogin">Ingresar</button>
            </div>
        </div>
    </div>
</div>