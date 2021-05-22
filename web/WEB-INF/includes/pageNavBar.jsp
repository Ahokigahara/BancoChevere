<%@page import="Model.Usuario"%>
<%
    Usuario usuario = null;
    try {
        usuario = (Usuario) session.getAttribute("USUARIO");
    } catch (Exception excepcion) {
        usuario = null;
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
                <% if (usuario == null) { %>
                <a class="nav-link" href="index.jsp">Inicio</a>
                <% } else {%>
                <a class="nav-link" href="indexUser.jsp">Inicio</a>
                <% }%>                
            </li>
        </ul>
        <!-- Button trigger modal -->   
        <% if (usuario == null) { %>
        <button type="button" class="btn btn-warning" data-toggle="modal" data-target="#loginModal">Ingresar</button>
        <% } else {%>
        <a class="btn btn-warning" href="index.jsp?salir">Cerrar sesión <b><%= usuario.getUsuario()%></b></a>
        <% }%>
    </div>
</nav>

<!-- Modal inicio de sesion -->
<div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="loginModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="loginModalLabel">Credenciales</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-row">
                        <div class="form-group col-6">
                            <label for="user">Usuario</label>
                            <input type="text" class="form-control" id="user" name="user" autocomplete="false" required="required">  
                        </div>
                        <div class="form-group col-6">
                            <label for="password">Clave</label>
                            <input type="password" class="form-control" id="password" name="password" autocomplete="off" required="required">  
                        </div>
                    </div>
                    <small class="text-muted">Ingrese sus credenciales de acceso para poder iniciar sesión</small>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Salir</button>
                <button type="button" class="btn btn-warning" id="btnLogin">Ingresar</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal codigo confirmacion inicio de sesion -->
<div class="modal fade" id="codigoConfirmacionSesion" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="codigoConfirmacionSesionLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="staticBackdropLabel">C&oacutedigo de seguridad</h5>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-row">
                        <div class="form-group col-12">
                            <label for="confirLoginCode" class="required">Ingrese el código de seguridad de 6 dígitos que fue enviado a su correo eléctronico registrado.</label>
                            <input type="password" class="form-control" id="confirLoginCode" name="confirLoginCode" autocomplete="off" required="required" >
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-warning realizarInsercion" id="btnConfirLoginCode">Entrar</button>
            </div>
        </div>
    </div>
</div> 