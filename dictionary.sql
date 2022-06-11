-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 11, 2022 at 11:55 AM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dp_p20041`
--

-- --------------------------------------------------------

--
-- Table structure for table `dictionary`
--

CREATE TABLE `dictionary` (
  `id` bigint(20) NOT NULL,
  `pattern` varchar(255) NOT NULL,
  `long_word` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `dictionary`
--

INSERT INTO `dictionary` (`id`, `pattern`, `long_word`) VALUES
(0, 'robot', 0),
(1, 'roboti', 0),
(2, 'sobota', 0),
(3, 'robota', 0),
(4, 'soboty', 0),
(5, 'chobot', 0),
(6, 'drak', 0),
(7, 'mrak', 0),
(8, 'prak', 0),
(9, 'vlak', 0),
(10, 'vrak', 0),
(11, 'abeceda', 0),
(12, 'agenda', 0),
(13, 'anketa', 0),
(14, 'balkón', 0),
(15, 'balón', 0),
(16, 'salón', 0),
(17, 'citrón', 0),
(18, 'banán', 0),
(19, 'brána', 0),
(20, 'vrána', 0),
(21, 'cifra', 0),
(22, 'cihla', 0),
(23, 'činka', 0),
(24, 'marek', 0),
(25, 'párek', 0),
(26, 'dárek', 0),
(27, 'cárech', 0),
(28, 'carech', 0),
(29, 'halek', 0),
(30, 'varech', 0),
(31, 'dálek', 0),
(32, 'racek', 0),
(33, 'sáček', 0),
(34, 'háček', 0),
(35, 'řádek', 0),
(36, 'ráček', 0),
(37, 'řádné', 0),
(38, 'lázeň', 0),
(39, 'bázeň', 0),
(40, 'báseň', 0),
(41, 'bazén', 0),
(42, 'kázeň', 0),
(43, 'klacek', 0),
(44, 'muška', 0),
(45, 'fuška', 0),
(46, 'fuska', 0),
(47, 'puška', 0),
(48, 'sušák', 0),
(49, 'tužka', 0),
(50, 'taška', 0),
(51, 'hračka', 0),
(52, 'rosa', 0),
(53, 'bosa', 0),
(54, 'vosa', 0),
(55, 'vosy', 0),
(56, 'voda', 0),
(57, 'móda', 0),
(58, 'dóza', 0),
(59, 'vozy', 0),
(60, 'vody', 0),
(61, 'celník', 0),
(62, 'cedník', 0),
(63, 'zedník', 0),
(64, 'vodník', 0),
(65, 'ceník', 0),
(66, 'četník', 0),
(67, 'seník', 0),
(68, 'počítač', 0),
(69, 'dočítáš', 0),
(70, 'počítáš', 0),
(71, 'obláček', 0),
(72, 'nováček', 0),
(73, 'omáček', 0),
(74, 'otáček', 0),
(75, 'váček', 0),
(76, 'tácek', 0),
(77, 'hraček', 0),
(78, 'vláček', 0),
(79, 'rvaček', 0),
(80, 'koláček', 0),
(81, 'perník', 0),
(82, 'náměstí', 0),
(83, 'návěští', 0),
(84, 'naštěstí', 0),
(85, 'náměstci', 0),
(86, 'veverka', 0),
(87, 'breberka', 0),
(88, 'berberka', 0),
(89, 'sekerka', 0),
(90, 'tatarka', 0),
(91, 'šnek', 0),
(92, 'šejk', 0),
(93, 'šek', 0),
(94, 'pokoj', 0),
(95, 'pokoje', 0),
(96, 'pokoře', 0),
(97, 'pokora', 0),
(98, 'hoboj', 0),
(99, 'obora', 0),
(100, 'automatizace', 1),
(101, 'aklimatizace', 1),
(102, 'klimatizace', 1),
(103, 'inicializace', 1),
(104, 'idealizace', 1),
(105, 'digitalizace', 1),
(106, 'aktualizace', 1),
(107, 'programátor', 1),
(108, 'programátory', 1),
(109, 'propagátor', 1),
(110, 'propagátoři', 1),
(111, 'programátoři', 1),
(112, 'klávesnice', 1),
(113, 'klábosnice', 1),
(114, 'stavebnice', 1),
(115, 'klávesníci', 1),
(116, 'nájemnice', 1),
(117, 'klávesnici', 1),
(118, 'počítačový', 1),
(119, 'počítadlový', 1),
(120, 'počítačové', 1),
(121, 'počítačová', 1),
(122, 'počítadlová', 1),
(123, 'počítačovi', 1),
(124, 'pokrývačovi', 1),
(125, 'dosazovaný', 1),
(126, 'dosazovací', 1),
(127, 'odsazování', 1),
(128, 'odsazovací', 1),
(129, 'dotazovací', 1),
(130, 'dotazování', 1),
(131, 'dosazovaní', 1),
(132, 'zahradníky', 1),
(133, 'zahradníci', 1),
(134, 'zahrádkáři', 1),
(135, 'zahradních', 1),
(136, 'zahraniční', 1),
(137, 'zapařovací', 1),
(138, 'naparovací', 1),
(139, 'zapařování', 1),
(140, 'naparování', 1),
(141, 'zapařovaný', 1),
(142, 'absolventi', 1),
(143, 'abstinenti', 1),
(144, 'absolutní', 1),
(145, 'abstraktní', 1),
(146, 'absolventy', 1),
(147, 'absolvovat', 1),
(148, 'abstrahovat', 1),
(149, 'obskakovat', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `dictionary`
--
ALTER TABLE `dictionary`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
