CREATE DATABASE IF NOT EXISTS bankiut;
USE bankiut;

-- phpMyAdmin SQL Dump
-- version 5.1.2
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:3306
-- Généré le : sam. 19 oct. 2024 à 14:11
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
-- Base de données : `bankiut`
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
('AB7328887341', 'client2', -97, 'AVEC', '123'),
('BD4242424242', 'client1', 150, 'SANS', NULL),
('FF5050500202', 'client1', 705, 'SANS', NULL),
('IO1010010001', 'client2', 6868, 'SANS', NULL),
('LA1021931215', 'client1', 150, 'SANS', NULL),
('MD8694030938', 'client1', 70, 'SANS', NULL),
('PP1285735733', 'a', 37, 'SANS', NULL),
('TD0398455576', 'client2', 34, 'AVEC', '700'),
('XD1829451029', 'client2', -93, 'AVEC', '100'),
('XX7788778877', 'client2', 90, 'SANS', NULL),
('XX9999999999', 'client2', 0, 'AVEC', '500');

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
('a', 'a', 'a', 'a', '$2a$10$LscVcTRIgf99HFpBtLV.bO7sM/GGO5LRuwvsodts56sjLqfqEvAiu', b'1', 'MANAGER', NULL),
('admin', 'Smith', 'Joe', '123, grande rue, Metz', '$2a$10$mYOdYYUmEh4ZxlMbyaGJQOwtWNNtzA35Q1DP.EmSpZKfJ07fl88yq', b'1', 'MANAGER', ''),
('client1', 'client1', 'Jane', '45, grand boulevard, Brest', '$2a$10$FLZXzuypetHrVADfUDujA.MmnjpFF37uEGk9h/V4z.hVY4lqPBTAG', b'1', 'CLIENT', '123456789'),
('client2', 'client2', 'Jane', '45, grand boulevard, Brest', '$2a$10$gBP0zrO5xvgBTXO3KV51feXIbsVYWvHqWrrT5VCLfLoYam0lzu3dm', b'1', 'CLIENT', '123456788');

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
