-- phpMyAdmin SQL Dump
-- version 5.1.2
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:3306
-- Généré le : sam. 19 oct. 2024 à 14:10
-- Version du serveur : 5.7.24
-- Version de PHP : 8.3.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `bankiuttest`
--

-- --------------------------------------------------------

--
-- Structure de la table `compte`
--

CREATE TABLE `compte` (
  `numeroCompte` varchar(50) NOT NULL,
  `userId` varchar(50) NOT NULL,
  `solde` double NOT NULL,
  `avecDecouvert` varchar(5) NOT NULL,
  `decouvertAutorise` decimal(10,0) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `compte`
--

INSERT INTO `compte` (`numeroCompte`, `userId`, `solde`, `avecDecouvert`, `decouvertAutorise`) VALUES
('AB7328887341', 'j.doe2', 4242, 'AVEC', '123'),
('AV1011011011', 'g.descomptes', 5, 'AVEC', '100'),
('BD4242424242', 'j.doe1', 100, 'SANS', NULL),
('CADNV00000', 'j.doe1', 42, 'AVEC', '42'),
('CADV000000', 'j.doe1', 0, 'AVEC', '42'),
('CSDNV00000', 'j.doe1', 42, 'SANS', NULL),
('CSDV000000', 'j.doe1', 0, 'SANS', NULL),
('IO1010010001', 'j.doe2', 6868, 'SANS', NULL),
('KL4589219196', 'g.descomptesvides', 0, 'AVEC', '150'),
('KO7845154956', 'g.descomptesvides', 0, 'SANS', NULL),
('LA1021931215', 'j.doe1', 100, 'SANS', NULL),
('MD8694030938', 'j.doe1', 500, 'SANS', NULL),
('PP1285735733', 'a.lidell1', 37, 'SANS', NULL),
('SA1011011011', 'g.descomptes', 10, 'SANS', '0'),
('TD0398455576', 'j.doe1', 23, 'AVEC', '500'),
('XD1829451029', 'j.doe1', -48, 'AVEC', '100');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `userId` varchar(50) NOT NULL,
  `nom` varchar(45) NOT NULL,
  `prenom` varchar(45) NOT NULL,
  `adresse` varchar(100) NOT NULL,
  `userPwd` varchar(100) DEFAULT NULL,
  `male` bit(1) NOT NULL,
  `type` varchar(10) NOT NULL,
  `numClient` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`userId`, `nom`, `prenom`, `adresse`, `userPwd`, `male`, `type`, `numClient`) VALUES
('a.lidell1', 'Lidell', 'Alice', '789, grande rue, Metz', '$2a$10$n8mAXgbL5yxV1nYf9zTH.eUQITvkVmDOkM7zRWfJn2QBYtuDP9j36', b'1', 'CLIENT', '9865432100'),
('admin', 'Smith', 'Joe', '123, grande rue, Metz', '$2a$10$mYOdYYUmEh4ZxlMbyaGJQOwtWNNtzA35Q1DP.EmSpZKfJ07fl88yq', b'1', 'MANAGER', ''),
('c.exist', 'TEST NOM', 'TEST PRENOM', 'TEST ADRESSE', '$2a$10$7RCpMi9h5LRSC9ApuDDtHu2pNbZOuKtlz2UkQ6h4bUJBG4uqLDX92', b'1', 'CLIENT', '0101010101'),
('g.descomptes', 'TEST NOM', 'TEST PRENOM', 'TEST ADRESSE', '$2a$10$3qp8iL/1a9P8z0V2FEaYT.QrM8BaOyAXjx4IgNRsTwl6h2Neex1La\r\n', b'1', 'CLIENT', '1000000001'),
('g.descomptesvides', 'TEST NOM', 'TEST PRENOM', 'TEST ADRESSE', '$2a$10$58Pf7ns9hO72BjcIp4oVhONyF2eIGFfoUYFiv2mzkJ7EvOe4TmoYy', b'1', 'CLIENT', '0000000002'),
('g.exist', 'TEST NOM', 'TEST PRENOM', 'TEST ADRESSE', '$2a$10$vwAXpbUlreNuEwr9Qnt0CucWRSWqqevURmSHbKlWFomjHF5qx/dBW', b'1', 'CLIENT', '1010101010'),
('g.pasdecompte', 'TEST NOM', 'TEST PRENOM', 'TEST ADRESSE', '$2a$10$PosK2g7V880JLuPZ.A/JUePlnSvXc1.A6u2u205l/PQVbC.6elTbW', b'1', 'CLIENT', '5544554455'),
('j.doe1', 'Doe', 'Jane', '456, grand boulevard, Brest', '$2a$10$V3V0rEUO.EBF8eIvScwpQObBHO9UAlZmEjDvUG6n9TUhRzzMBlDTu', b'1', 'CLIENT', '1234567890'),
('j.doe2', 'Doe', 'John', '457, grand boulevard, Perpignan', '$2a$10$Yn1t08OhngNWSXJ7E6jlp.20rz9SBOt0w3mzM70eOvtGkRiwsV0hS', b'1', 'CLIENT', '0000000001');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `compte`
--
ALTER TABLE `compte`
  ADD PRIMARY KEY (`numeroCompte`),
  ADD KEY `index_userClient` (`userId`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`userId`),
  ADD UNIQUE KEY `numClient_UNIQUE` (`numClient`);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `compte`
--
ALTER TABLE `compte`
  ADD CONSTRAINT `fk_Compte_userId` FOREIGN KEY (`userId`) REFERENCES `utilisateur` (`userId`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
