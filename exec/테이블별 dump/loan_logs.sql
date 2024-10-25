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

-- 테이블 mydatabase.loan_logs 구조 내보내기
DROP TABLE IF EXISTS `loan_logs`;
CREATE TABLE IF NOT EXISTS `loan_logs` (
  `balance` int(11) NOT NULL,
  `repayment` int(11) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `loan_id` bigint(20) DEFAULT NULL,
  `loan_log_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`loan_log_id`),
  KEY `FK8l3r0oiiytvxr3wnv655fuvd` (`loan_id`),
  CONSTRAINT `FK8l3r0oiiytvxr3wnv655fuvd` FOREIGN KEY (`loan_id`) REFERENCES `loans` (`loan_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- 테이블 데이터 mydatabase.loan_logs:~13 rows (대략적) 내보내기
INSERT INTO `loan_logs` (`balance`, `repayment`, `created_at`, `loan_id`, `loan_log_id`, `updated_at`) VALUES
	(489584, 10416, '2024-09-26 11:47:21.306892', 1, 1, '2024-09-26 11:47:21.306892'),
	(479168, 10416, '2024-09-26 11:47:23.439253', 1, 2, '2024-09-26 11:47:23.439253'),
	(468752, 10416, '2024-09-26 11:47:24.200364', 1, 3, '2024-09-26 11:47:24.200364'),
	(458336, 10416, '2024-09-26 11:47:52.023872', 1, 4, '2024-09-26 11:47:52.023872'),
	(447920, 10416, '2024-09-27 14:47:19.332603', 1, 5, '2024-09-27 14:47:19.332603'),
	(437504, 10416, '2024-09-27 14:55:54.135560', 1, 6, '2024-09-27 14:55:54.135560'),
	(427088, 10416, '2024-09-27 14:55:57.662452', 1, 7, '2024-09-27 14:55:57.662452'),
	(100000, 50000, '2024-10-04 11:01:10.923342', 1, 8, '2024-10-04 11:01:10.923342'),
	(406256, 10416, '2024-09-29 19:16:01.644502', 1, 9, '2024-09-29 19:16:01.644502'),
	(500000, 1666, '2024-10-05 18:07:47.205089', 8, 10, '2024-10-05 18:07:47.205089'),
	(2000, 1010, '2024-10-10 22:18:21.801329', 14, 12, '2024-10-10 22:18:21.801329'),
	(1000, 1006, '2024-10-10 22:44:24.859153', 14, 13, '2024-10-10 22:44:24.859153'),
	(0, 1003, '2024-10-10 22:45:14.764591', 14, 14, '2024-10-10 22:45:14.764591');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
