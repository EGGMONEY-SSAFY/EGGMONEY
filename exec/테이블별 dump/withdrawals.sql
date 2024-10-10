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

-- 테이블 mydatabase.withdrawals 구조 내보내기
DROP TABLE IF EXISTS `withdrawals`;
CREATE TABLE IF NOT EXISTS `withdrawals` (
  `withdrawal_price` int(11) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `withdrawal_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `withdrawal_status` enum('APPROVAL','PROGRESS','REFUSAL') DEFAULT NULL,
  PRIMARY KEY (`withdrawal_id`),
  KEY `FKesk6migh8b3x43q3740dh5fja` (`user_id`),
  CONSTRAINT `FKesk6migh8b3x43q3740dh5fja` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- 테이블 데이터 mydatabase.withdrawals:~6 rows (대략적) 내보내기
INSERT INTO `withdrawals` (`withdrawal_price`, `created_at`, `updated_at`, `user_id`, `withdrawal_id`, `withdrawal_status`) VALUES
	(100000, '2024-09-10 14:21:17.132168', '2024-09-10 03:13:31.975546', 1, 9, 'APPROVAL'),
	(1234, '2024-09-29 11:03:32.835955', '2024-09-30 11:21:09.310568', 5, 14, 'REFUSAL'),
	(200000, '2024-10-09 02:00:51.137611', '2024-10-10 22:05:35.573730', 1, 19, 'APPROVAL'),
	(4000, '2024-10-10 22:07:10.482147', '2024-10-10 22:07:10.482147', 11006, 20, 'PROGRESS'),
	(100000, '2024-10-10 22:13:57.011682', '2024-10-10 23:01:52.811975', 1, 21, 'REFUSAL'),
	(1000, '2024-10-10 22:35:21.844740', '2024-10-10 22:35:21.844740', 11005, 22, 'PROGRESS');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
