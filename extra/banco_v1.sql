-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 18-05-2021 a las 04:40:54
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
-- Base de datos: `banco_v1`
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
('', 'C');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `movimientos`
--

DROP TABLE IF EXISTS `movimientos`;
CREATE TABLE IF NOT EXISTS `movimientos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productos_id_origen` int(11) NOT NULL,
  `productos_id_destino` int(11) NOT NULL,
  `movimientosTipo_id` int(11) NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `monto` double DEFAULT NULL,
  `referencia` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_movimientos_movimientosTipo1_idx` (`movimientosTipo_id`),
  KEY `fk_movimientos_productos1_idx` (`productos_id_origen`),
  KEY `fk_movimientos_productos2_idx` (`productos_id_destino`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

DROP TABLE IF EXISTS `productos`;
CREATE TABLE IF NOT EXISTS `productos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `saldo` double DEFAULT NULL,
  `productosTipo_id` int(11) NOT NULL,
  `terceros_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_productos_productosTipo1_idx` (`productosTipo_id`),
  KEY `fk_productos_terceros1_idx` (`terceros_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `referenciaspago`
--

DROP TABLE IF EXISTS `referenciaspago`;
CREATE TABLE IF NOT EXISTS `referenciaspago` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `referencia` varchar(45) DEFAULT NULL,
  `monto` double DEFAULT NULL,
  `terceros_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_referenciasPago_terceros1_idx` (`terceros_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `terceros`
--

DROP TABLE IF EXISTS `terceros`;
CREATE TABLE IF NOT EXISTS `terceros` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `documento` varchar(45) DEFAULT NULL,
  `documentosTipo_id` varchar(1) NOT NULL,
  `documentoExpedicion` date DEFAULT NULL,
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
  KEY `fk_terceros_documentosTipo_idx` (`documentosTipo_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `terceros`
--

INSERT INTO `terceros` (`id`, `documento`, `documentosTipo_id`, `documentoExpedicion`, `nombres`, `apellidos`, `direccion`, `telefono`, `email`) VALUES
(1, '123', '', '2021-05-01', 'a', 'b', 'c', '1', '2');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE IF NOT EXISTS `usuarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `terceros_id` int(11) NOT NULL,
  `usuario` varchar(45) DEFAULT NULL,
  `clave` varchar(45) DEFAULT NULL,
  `activo` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `usuario` (`usuario`),
  KEY `activo` (`activo`),
  KEY `fk_usuarios_terceros1_idx` (`terceros_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `terceros_id`, `usuario`, `clave`, `activo`) VALUES
(2, 1, 'usuario', 'clave', 1);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `movimientos`
--
ALTER TABLE `movimientos`
  ADD CONSTRAINT `fk_movimientos_movimientosTipo1` FOREIGN KEY (`movimientosTipo_id`) REFERENCES `movimientostipo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_movimientos_productos1` FOREIGN KEY (`productos_id_origen`) REFERENCES `productos` (`numero`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_movimientos_productos2` FOREIGN KEY (`productos_id_destino`) REFERENCES `productos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `productos`
--
ALTER TABLE `productos`
  ADD CONSTRAINT `fk_productos_productosTipo1` FOREIGN KEY (`productosTipo_id`) REFERENCES `productostipo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_productos_terceros1` FOREIGN KEY (`terceros_id`) REFERENCES `terceros` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `referenciaspago`
--
ALTER TABLE `referenciaspago`
  ADD CONSTRAINT `fk_referenciasPago_terceros1` FOREIGN KEY (`terceros_id`) REFERENCES `terceros` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `terceros`
--
ALTER TABLE `terceros`
  ADD CONSTRAINT `fk_terceros_documentosTipo` FOREIGN KEY (`documentosTipo_id`) REFERENCES `documentostipo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD CONSTRAINT `fk_usuarios_terceros1` FOREIGN KEY (`terceros_id`) REFERENCES `terceros` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
