-- --------------------------------------------------------
-- 호스트:                          j11c204.p.ssafy.io
-- 서버 버전:                        10.6.18-MariaDB-0ubuntu0.22.04.1 - Ubuntu 22.04
-- 서버 OS:                        debian-linux-gnu
-- HeidiSQL 버전:                  12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- 테이블 mydatabase.stock_pendings 구조 내보내기
DROP TABLE IF EXISTS `stock_pendings`;
CREATE TABLE IF NOT EXISTS `stock_pendings` (
  `pending_amount` int(11) NOT NULL,
  `pending_price` int(11) NOT NULL,
  `stock_id` bigint(20) NOT NULL,
  `stock_pending_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `trade_type` enum('BUY','SELL') NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`stock_pending_id`),
  KEY `FKcpy70opvd9p0ue6pv24w0fdnl` (`stock_id`),
  KEY `FKo3jet78rionddd9bbcev4vmgh` (`user_id`),
  CONSTRAINT `FKcpy70opvd9p0ue6pv24w0fdnl` FOREIGN KEY (`stock_id`) REFERENCES `stocks` (`stock_id`),
  CONSTRAINT `FKo3jet78rionddd9bbcev4vmgh` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- 테이블 데이터 mydatabase.stock_pendings:~9 rows (대략적) 내보내기
INSERT INTO `stock_pendings` (`pending_amount`, `pending_price`, `stock_id`, `stock_pending_id`, `user_id`, `trade_type`, `created_at`, `updated_at`) VALUES
	(1, 1658, 11, 1, 5, 'BUY', '2024-10-10 22:02:23.556084', '2024-10-10 22:02:23.556084'),
	(4, 585, 13, 2, 5, 'BUY', '2024-10-10 22:02:41.674366', '2024-10-10 22:02:41.674366'),
	(1, 2612, 1, 3, 5, 'SELL', '2024-10-10 22:03:03.157915', '2024-10-10 22:03:03.157915'),
	(2, 2600, 1, 4, 5, 'SELL', '2024-10-10 22:03:21.429996', '2024-10-10 22:03:21.429996'),
	(9, 776, 2, 5, 5, 'SELL', '2024-10-10 22:05:55.804565', '2024-10-10 22:05:55.804565'),
	(9, 776, 2, 6, 5, 'SELL', '2024-10-10 22:06:08.631281', '2024-10-10 22:06:08.631281'),
	(5, 4200, 5, 10, 1, 'SELL', '2024-10-11 01:24:34.371098', '2024-10-11 01:24:34.371098'),
	(10, 3300, 4, 11, 1, 'BUY', '2024-10-11 01:25:57.525568', '2024-10-11 01:25:57.525568'),
	(10, 4300, 5, 12, 1, 'SELL', '2024-10-11 01:27:01.239827', '2024-10-11 01:27:01.239827');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
