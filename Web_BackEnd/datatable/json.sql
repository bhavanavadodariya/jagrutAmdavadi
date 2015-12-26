-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jun 08, 2014 at 12:26 PM
-- Server version: 5.6.12-log
-- PHP Version: 5.4.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `thecodeb_test`
--

-- --------------------------------------------------------

--
-- Table structure for table `json`
--

CREATE TABLE IF NOT EXISTS `json` (
  `id` int(2) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `email` varchar(32) NOT NULL,
  `city` varchar(15) NOT NULL,
  `country` varchar(15) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

--
-- Dumping data for table `json`
--

INSERT INTO `json` (`id`, `name`, `email`, `city`, `country`) VALUES
(1, 'Ashik', 'test@gmail.com', 'chennai', 'India'),
(2, 'Mohan', 'test@gmail.com', 'mumbai', 'India'),
(3, 'Sarathi', 'test@gmail.com', 'chennai', 'India'),
(4, 'Mani', 'test@gmail.com', 'Ahmedabad', 'India'),
(5, 'Siva', 'test@gmail.com', 'Madurai', 'India'),
(6, 'Ashik', 'test@gmail.com', 'Pune', 'India'),
(7, 'Mohan', 'test@gmail.com', 'Delhi', 'India'),
(8, 'Sarathi', 'test@gmail.com', 'chennai', 'India'),
(9, 'Mani', 'test@gmail.com', 'Ahmedabad', 'India'),
(10, 'Siva', 'test@gmail.com', 'Salem', 'India'),
(11, 'Ashik', 'test@gmail.com', 'Jammu', 'India'),
(12, 'Mohan', 'test@gmail.com', 'Indore', 'India');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
