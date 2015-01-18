-- phpMyAdmin SQL Dump
-- version 4.0.4.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 03, 2014 at 07:06 PM
-- Server version: 5.6.13
-- PHP Version: 5.5.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `mydb`
--
CREATE DATABASE IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `mydb`;

-- --------------------------------------------------------

--
-- Table structure for table `car`
--

DROP TABLE IF EXISTS `car`;
CREATE TABLE IF NOT EXISTS `car` (
  `ResidentID` int(10) DEFAULT NULL,
  `CarLicence` int(10) NOT NULL,
  `CarModel` varchar(50) NOT NULL,
  `CarColour` varchar(50) NOT NULL,
  PRIMARY KEY (`CarLicence`),
  KEY `ResidentID` (`ResidentID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='List of cars';

--
-- Dumping data for table `car`
--

INSERT INTO `car` (`ResidentID`, `CarLicence`, `CarModel`, `CarColour`) VALUES
(2, 1958, 'Ford', 'Purple'),
(1, 728374, 'Ford', 'Purple');

-- --------------------------------------------------------

--
-- Table structure for table `county`
--

DROP TABLE IF EXISTS `county`;
CREATE TABLE IF NOT EXISTS `county` (
  `CountyID` int(10) NOT NULL,
  `CountyName` varchar(50) DEFAULT NULL,
  `StateID` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`CountyID`),
  KEY `StateID` (`StateID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='List of counties';

--
-- Dumping data for table `county`
--

INSERT INTO `county` (`CountyID`, `CountyName`, `StateID`) VALUES
(1, 'Surrey', '1');

-- --------------------------------------------------------

--
-- Table structure for table `joke`
--

DROP TABLE IF EXISTS `joke`;
CREATE TABLE IF NOT EXISTS `joke` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `joketext` text,
  `jokedate` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='jokess' AUTO_INCREMENT=25 ;

--
-- Dumping data for table `joke`
--

INSERT INTO `joke` (`id`, `joketext`, `jokedate`) VALUES
(2, 'A Man walks into a Bar', '2013-11-03'),
(15, 'secondg3dk', '2013-12-24'),
(16, 'thirdHardcodeCheck', '2013-12-24'),
(17, 'fwsed', '2011-01-01'),
(19, 'Eighth July TwoOhTwo', '2011-07-08'),
(20, '''98', '2011-01-01'),
(24, 'edvfchecking', '2003-06-01');

-- --------------------------------------------------------

--
-- Table structure for table `resident`
--

DROP TABLE IF EXISTS `resident`;
CREATE TABLE IF NOT EXISTS `resident` (
  `ResidentID` int(10) NOT NULL,
  `ResidentName` varchar(50) NOT NULL,
  `CountyID` int(10) NOT NULL,
  `StateID` int(10),
  PRIMARY KEY (`ResidentID`),
  KEY `CountyID` (`CountyID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='list of residents';

--
-- Dumping data for table `resident`
--

INSERT INTO `resident` (`ResidentID`, `ResidentName`, `CountyID`, `StateID`) VALUES
(1, 'Bob', 1, 1),
(2, 'John', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `state`
--

DROP TABLE IF EXISTS `state`;
CREATE TABLE IF NOT EXISTS `state` (
  `StateID` varchar(50) NOT NULL,
  `StateName` varchar(50) NOT NULL,
  PRIMARY KEY (`StateID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='List of states';

--
-- Dumping data for table `state`
--

INSERT INTO `state` (`StateID`, `StateName`) VALUES
('1', 'State');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `car`
--
ALTER TABLE `car`
  ADD CONSTRAINT `ResidentID` FOREIGN KEY (`ResidentID`) REFERENCES `resident` (`ResidentID`);

--
-- Constraints for table `county`
--
ALTER TABLE `county`
  ADD CONSTRAINT `StateID` FOREIGN KEY (`StateID`) REFERENCES `state` (`StateID`);

--
-- Constraints for table `resident`
--
ALTER TABLE `resident`
  ADD CONSTRAINT `CountyID` FOREIGN KEY (`CountyID`) REFERENCES `county` (`CountyID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
