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

-- 테이블 mydatabase.stock_logs 구조 내보내기
DROP TABLE IF EXISTS `stock_logs`;
CREATE TABLE IF NOT EXISTS `stock_logs` (
  `trade_amount` int(11) NOT NULL,
  `trade_price` int(11) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `stock_log_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `stock_user_id` bigint(20) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `trade_type` enum('BUY','SELL') NOT NULL,
  PRIMARY KEY (`stock_log_id`),
  KEY `FKpg1hieqdvvgsuyv9aooggc9td` (`stock_user_id`),
  CONSTRAINT `FKpg1hieqdvvgsuyv9aooggc9td` FOREIGN KEY (`stock_user_id`) REFERENCES `stock_users` (`stock_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- 테이블 데이터 mydatabase.stock_logs:~15 rows (대략적) 내보내기
INSERT INTO `stock_logs` (`trade_amount`, `trade_price`, `created_at`, `stock_log_id`, `stock_user_id`, `updated_at`, `trade_type`) VALUES
	(1, 2594, '2024-10-10 21:22:50.142732', 1, 11001, '2024-10-10 21:22:50.142732', 'BUY'),
	(6, 778, '2024-10-10 21:23:32.973157', 2, 11002, '2024-10-10 21:23:32.973157', 'BUY'),
	(1, 2594, '2024-10-10 21:23:46.603361', 3, 11001, '2024-10-10 21:23:46.603361', 'SELL'),
	(2, 2599, '2024-10-10 22:00:54.139106', 4, 11003, '2024-10-10 22:00:54.139106', 'BUY'),
	(9, 775, '2024-10-10 22:01:08.705717', 5, 11004, '2024-10-10 22:01:08.705717', 'BUY'),
	(2, 1920, '2024-10-10 22:01:55.516671', 6, 11005, '2024-10-10 22:01:55.516671', 'BUY'),
	(7, 893, '2024-10-10 22:02:09.758392', 7, 11006, '2024-10-10 22:02:09.758392', 'BUY'),
	(5, 3190, '2023-10-30 22:24:22.069241', 8, 11007, '2024-10-10 22:24:22.069241', 'BUY'),
	(5, 1423, '2024-08-05 22:26:24.361904', 9, 11008, '2024-08-05 22:26:24.361904', 'BUY'),
	(5, 1579, '2024-09-02 22:37:53.219068', 10, 11008, '2024-09-02 22:37:53.219068', 'SELL'),
	(10, 1409, '2024-09-10 22:38:17.121127', 11, 11008, '2024-09-10 22:38:17.121127', 'BUY'),
	(11, 2732, '2024-07-26 22:38:42.390367', 12, 1, '2024-07-26 22:38:42.390367', 'BUY'),
	(100, 2599, '2024-10-10 23:03:19.047282', 13, 11009, '2024-10-10 23:03:19.047282', 'BUY'),
	(11, 860, '2024-04-16 01:07:41.926195', 14, 11010, '2024-04-16 01:07:41.926195', 'BUY'),
	(15, 3394, '2024-07-04 01:16:56.089894', 15, 11011, '2024-07-04 01:16:56.089894', 'BUY');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
