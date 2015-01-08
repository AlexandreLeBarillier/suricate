-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le: Jeu 08 Janvier 2015 à 12:12
-- Version du serveur: 5.6.12-log
-- Version de PHP: 5.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `suricatedb`
--
CREATE DATABASE IF NOT EXISTS `suricatedb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `suricatedb`;

-- --------------------------------------------------------

--
-- Structure de la table `accesslog`
--

CREATE TABLE IF NOT EXISTS `accesslog` (
  `idaccesslog` int(11) NOT NULL AUTO_INCREMENT,
  `create_date` date NOT NULL,
  `content` varchar(50) NOT NULL,
  PRIMARY KEY (`idaccesslog`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

--
-- Structure de la table `guest`
--

CREATE TABLE IF NOT EXISTS `guest` (
  `idguest` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `photopath` varchar(50) NOT NULL,
  PRIMARY KEY (`idguest`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=42 ;


-- --------------------------------------------------------

--
-- Structure de la table `nfcaccess`
--

CREATE TABLE IF NOT EXISTS `nfcaccess` (
  `idnfcaccess` int(11) NOT NULL AUTO_INCREMENT,
  `nfccode` varchar(250) NOT NULL,
  `validity` varchar(2) NOT NULL,
  `validity_rule` varchar(50) NOT NULL,
  `idguest` int(11) NOT NULL,
  PRIMARY KEY (`idnfcaccess`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=29 ;

-- --------------------------------------------------------

--
-- Structure de la table `pinaccess`
--

CREATE TABLE IF NOT EXISTS `pinaccess` (
  `idpinaccess` int(11) NOT NULL AUTO_INCREMENT,
  `pincode` varchar(10) NOT NULL,
  `validity` varchar(2) NOT NULL,
  `validity_rule` varchar(50) NOT NULL,
  `idguest` int(11) NOT NULL,
  PRIMARY KEY (`idpinaccess`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

-- --------------------------------------------------------

--
-- Structure de la table `suricate_user`
--

CREATE TABLE IF NOT EXISTS `suricate_user` (
  `idSuricateUser` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(25) NOT NULL,
  `lastName` varchar(25) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` blob NOT NULL,
  PRIMARY KEY (`idSuricateUser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `version`
--

CREATE TABLE IF NOT EXISTS `version` (
  `idVersion` int(11) NOT NULL AUTO_INCREMENT,
  `numero` varchar(5) NOT NULL,
  PRIMARY KEY (`idVersion`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Contenu de la table `version`
--

INSERT INTO `version` (`idVersion`, `numero`) VALUES
(1, '0.0.1');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
