-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 23-05-2021 a las 03:42:31
-- Versión del servidor: 5.7.31
-- Versión de PHP: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `banco_v3`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `documentostipo`
--

DROP TABLE IF EXISTS `documentostipo`;
CREATE TABLE IF NOT EXISTS `documentostipo` (
  `id` varchar(1) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `documentostipo`
--

INSERT INTO `documentostipo` (`id`, `nombre`) VALUES
('C', 'CEDULA'),
('N', 'NIT'),
('P', 'PASAPORTE');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `movimientos`
--

DROP TABLE IF EXISTS `movimientos`;
CREATE TABLE IF NOT EXISTS `movimientos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productoOrigenId` int(11) NOT NULL,
  `productoDestinoId` int(11) NOT NULL,
  `movimientoTipoId` int(11) NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `monto` double DEFAULT NULL,
  `concepto` varchar(240) DEFAULT NULL,
  `referenciaId` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_movimientos_movimientosTipo1_idx` (`movimientoTipoId`),
  KEY `fk_movimientos_productos1_idx` (`productoOrigenId`),
  KEY `fk_movimientos_productos2_idx` (`productoDestinoId`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `movimientos`
--

INSERT INTO `movimientos` (`id`, `productoOrigenId`, `productoDestinoId`, `movimientoTipoId`, `fecha`, `monto`, `concepto`, `referenciaId`) VALUES
(12, 3, 3, 1, '2021-05-22 21:50:03', 1200000, 'Id:6, Monto: 1200000.0, Tercero:COLUMBUS NETWORKS DE COLOMBIA SAS C&W BUSINESS', 6),
(13, 1, 3, 2, '2021-05-22 21:50:49', 200000, 'Prueba', 0),
(14, 1, 2, 1, '2021-05-22 22:24:41', 150000, 'Id:1, Monto: 150000.0, Tercero:COLOMBIA TELECOMUNICACIONES S.A. ESP BIC', 1),
(15, 1, 3, 2, '2021-05-22 22:25:37', 1, 'prueba 2', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `movimientostipo`
--

DROP TABLE IF EXISTS `movimientostipo`;
CREATE TABLE IF NOT EXISTS `movimientostipo` (
  `id` int(11) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `movimientostipo`
--

INSERT INTO `movimientostipo` (`id`, `nombre`) VALUES
(1, 'Pago'),
(2, 'Transferencia');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

DROP TABLE IF EXISTS `productos`;
CREATE TABLE IF NOT EXISTS `productos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `numero` decimal(10,0) NOT NULL,
  `saldo` double DEFAULT NULL,
  `productoTipoId` int(11) NOT NULL,
  `terceroId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_productos_productosTipo1_idx` (`productoTipoId`),
  KEY `fk_productos_terceros1_idx` (`terceroId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`id`, `numero`, `saldo`, `productoTipoId`, `terceroId`) VALUES
(1, '45566888', 649999, 1, 1),
(2, '45879663', 2000000, 2, 1),
(3, '85296333', 2000001, 3, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productostipo`
--

DROP TABLE IF EXISTS `productostipo`;
CREATE TABLE IF NOT EXISTS `productostipo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `productostipo`
--

INSERT INTO `productostipo` (`id`, `nombre`) VALUES
(1, 'Cuenta Ahorros'),
(2, 'Cuenta Corriente'),
(3, 'Tarjeta Credito');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `referenciaspago`
--

DROP TABLE IF EXISTS `referenciaspago`;
CREATE TABLE IF NOT EXISTS `referenciaspago` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `referencia` varchar(45) DEFAULT NULL,
  `monto` double DEFAULT NULL,
  `terceroId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_referenciasPago_terceros1_idx` (`terceroId`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `referenciaspago`
--

INSERT INTO `referenciaspago` (`id`, `referencia`, `monto`, `terceroId`) VALUES
(1, '1', 150000, 2),
(2, '2', 150000, 2),
(3, '3', 150000, 2),
(4, '4', 152000, 2),
(5, '5', 152000, 2),
(6, '1', 1200000, 3),
(7, '2', 1200000, 3),
(8, '3', 1200000, 3),
(9, '4', 1200000, 3),
(10, '5', 1200000, 3),
(11, '6', 1200000, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `terceros`
--

DROP TABLE IF EXISTS `terceros`;
CREATE TABLE IF NOT EXISTS `terceros` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `documento` varchar(45) DEFAULT NULL,
  `documentoTipoId` varchar(1) NOT NULL,
  `documentoExpedicion` varchar(10) DEFAULT NULL,
  `nombres` varchar(70) DEFAULT NULL,
  `apellidos` varchar(70) DEFAULT NULL,
  `direccion` varchar(250) DEFAULT NULL,
  `telefono` varchar(50) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `documento` (`documento`),
  KEY `expedicion` (`documentoExpedicion`),
  KEY `telefono` (`telefono`),
  KEY `email` (`email`),
  KEY `fk_terceros_documentosTipo_idx` (`documentoTipoId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `terceros`
--

INSERT INTO `terceros` (`id`, `documento`, `documentoTipoId`, `documentoExpedicion`, `nombres`, `apellidos`, `direccion`, `telefono`, `email`) VALUES
(1, '1001987456', 'C', '2021-05-01', 'Megan Lucia', 'Ulloa Rojas', 'calle 99 # 99 -99', '2999999', 'jortizh5@ucentral.edu.co'),
(2, '830122566', 'N', '2000-01-01', 'COLOMBIA TELECOMUNICACIONES S.A.', 'ESP BIC', 'CALLE 99 # 99 - 99', '2999999', 'movistar@movistar.com'),
(3, '830078515', 'N', '2000-01-01', 'COLUMBUS NETWORKS DE COLOMBIA SAS', 'C&W BUSINESS', 'CALLE 108 # 45-30', '4998877', 'columbus@columbus.com');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE IF NOT EXISTS `usuarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `terceroId` int(11) NOT NULL,
  `usuario` varchar(45) DEFAULT NULL,
  `clave` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `usuario` (`usuario`),
  KEY `fk_usuarios_terceros1_idx` (`terceroId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `terceroId`, `usuario`, `clave`) VALUES
(1, 1, 'usuario', 'clave');

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `productos`
--
ALTER TABLE `productos`
  ADD CONSTRAINT `fk_productos_productosTipo1` FOREIGN KEY (`productoTipoId`) REFERENCES `productostipo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_productos_terceros1` FOREIGN KEY (`terceroId`) REFERENCES `terceros` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `referenciaspago`
--
ALTER TABLE `referenciaspago`
  ADD CONSTRAINT `fk_referenciasPago_terceros1` FOREIGN KEY (`terceroId`) REFERENCES `terceros` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `terceros`
--
ALTER TABLE `terceros`
  ADD CONSTRAINT `fk_terceros_documentosTipo` FOREIGN KEY (`documentoTipoId`) REFERENCES `documentostipo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD CONSTRAINT `fk_usuarios_terceros1` FOREIGN KEY (`terceroId`) REFERENCES `terceros` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
