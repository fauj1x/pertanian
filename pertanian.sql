-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 15, 2023 at 05:35 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pertanian`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `Username` varchar(30) NOT NULL,
  `Password` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`Username`, `Password`) VALUES
('admin1', 'admin1');

-- --------------------------------------------------------

--
-- Table structure for table `alat`
--

CREATE TABLE `alat` (
  `id_Alat` char(5) NOT NULL,
  `Nama_Alat` varchar(50) DEFAULT NULL,
  `jumlah_Alat` int(11) DEFAULT NULL,
  `Harga_sewa` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `beras`
--

CREATE TABLE `beras` (
  `id_beras` char(5) NOT NULL,
  `nama` varchar(40) DEFAULT NULL,
  `harga` int(11) DEFAULT NULL,
  `stok` int(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `detail_pembelian`
--

CREATE TABLE `detail_pembelian` (
  `id_detailnota` char(5) NOT NULL,
  `tgl_transaksi` date DEFAULT NULL,
  `total_pembelian` int(11) DEFAULT NULL,
  `id_beras` char(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `detail_peminjaman`
--

CREATE TABLE `detail_peminjaman` (
  `id_Detpinjam` char(5) NOT NULL,
  `Total_alat` int(11) DEFAULT NULL,
  `jumlah_Alatpinjam` int(11) DEFAULT NULL,
  `id_alat` char(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `membeli`
--

CREATE TABLE `membeli` (
  `id_nota` char(5) NOT NULL,
  `tgl_transaksi` date DEFAULT NULL,
  `total_pembelian` int(11) DEFAULT NULL,
  `username` varchar(20) NOT NULL,
  `id_detailnota` char(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `meminjam`
--

CREATE TABLE `meminjam` (
  `id_Hpinjam` char(5) NOT NULL,
  `Tgl_pinjam` date DEFAULT NULL,
  `Tenggat_peminjaman` date DEFAULT NULL,
  `Username` varchar(20) NOT NULL,
  `id_Detpinjam` char(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `menyetorkan`
--

CREATE TABLE `menyetorkan` (
  `id_Setor` char(5) NOT NULL,
  `jumlah_setor` int(11) DEFAULT NULL,
  `Tgl_Setor` int(11) DEFAULT NULL,
  `Usaername` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `pembukuan`
--

CREATE TABLE `pembukuan` (
  `id_Pembukuan` char(5) NOT NULL,
  `Tgl_Pembukuan` date DEFAULT NULL,
  `Total_sewa` int(11) DEFAULT NULL,
  `id_Setor` char(5) NOT NULL,
  `id_Hpinjam` char(5) NOT NULL,
  `id_Nota` char(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `test`
--

CREATE TABLE `test` (
  `id_test` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `username` varchar(20) NOT NULL,
  `nama` varchar(40) DEFAULT NULL,
  `alamat` varchar(225) DEFAULT NULL,
  `no_telp` int(12) DEFAULT NULL,
  `password` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`username`, `nama`, `alamat`, `no_telp`, `password`) VALUES
('haha', 'haha', 'haha', 898, 'haha'),
('test', 'test', 'test', 89, 'test');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`Username`);

--
-- Indexes for table `alat`
--
ALTER TABLE `alat`
  ADD PRIMARY KEY (`id_Alat`);

--
-- Indexes for table `beras`
--
ALTER TABLE `beras`
  ADD PRIMARY KEY (`id_beras`);

--
-- Indexes for table `detail_pembelian`
--
ALTER TABLE `detail_pembelian`
  ADD PRIMARY KEY (`id_detailnota`);

--
-- Indexes for table `detail_peminjaman`
--
ALTER TABLE `detail_peminjaman`
  ADD PRIMARY KEY (`id_Detpinjam`);

--
-- Indexes for table `membeli`
--
ALTER TABLE `membeli`
  ADD PRIMARY KEY (`id_nota`);

--
-- Indexes for table `meminjam`
--
ALTER TABLE `meminjam`
  ADD PRIMARY KEY (`id_Hpinjam`);

--
-- Indexes for table `menyetorkan`
--
ALTER TABLE `menyetorkan`
  ADD PRIMARY KEY (`id_Setor`);

--
-- Indexes for table `pembukuan`
--
ALTER TABLE `pembukuan`
  ADD PRIMARY KEY (`id_Pembukuan`);

--
-- Indexes for table `test`
--
ALTER TABLE `test`
  ADD PRIMARY KEY (`id_test`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`username`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
