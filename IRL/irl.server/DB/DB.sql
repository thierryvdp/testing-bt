-- phpMyAdmin SQL Dump
-- version 2.8.2-Debian-0.2
-- http://www.phpmyadmin.net
--
-- Serveur: localhost
-- Généré le : Mercredi 21 Novembre 2007 à 22:06
-- Version du serveur: 5.0.24
-- Version de PHP: 5.1.6

SET FOREIGN_KEY_CHECKS=0;

SET AUTOCOMMIT=0;
START TRANSACTION;

-- --------------------------------------------------------

--
-- Structure de la table `User`
--

DROP TABLE IF EXISTS `User`;
CREATE TABLE IF NOT EXISTS `User` (
  `UserId` 		  bigint(15) NOT NULL auto_increment,
  `FirstName`		VARCHAR(30),
  `LastName`		 VARCHAR(30),
  `Address1`		 VARCHAR(30),
  `Address2`		 VARCHAR(30),
  `PostalCode`	 VARCHAR(10),
  `Town`			   VARCHAR(30),
  `Country`		  VARCHAR(30),
  `FixedPhone`	 VARCHAR(30),
  `MobilePhone`	VARCHAR(30),
PRIMARY KEY  (`UserId`)
) ENGINE=InnoDb DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `User`
--

DROP TABLE IF EXISTS `Email`;
CREATE TABLE IF NOT EXISTS `Email` (
  `EmailId` 		 bigint(15) NOT NULL auto_increment,
  `Email` 	     varchar(20) NOT NULL,
  `Password`     varchar(20) NOT NULL,
  `UserId` 		  bigint(15) NOT NULL,
PRIMARY KEY  (`EmailId`),
FOREIGN KEY (`UserId`) REFERENCES `User` (`UserId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDb DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;


SET FOREIGN_KEY_CHECKS=1;

COMMIT;
