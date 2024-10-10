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

-- 테이블 mydatabase.loans 구조 내보내기
DROP TABLE IF EXISTS `loans`;
CREATE TABLE IF NOT EXISTS `loans` (
  `balance` int(11) NOT NULL,
  `loan_amount` int(11) NOT NULL,
  `loan_date` int(11) NOT NULL,
  `loan_rate` double DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `expiration_date` datetime(6) DEFAULT NULL,
  `loan_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `updated_at` datetime(6) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `loan_reason` varchar(255) DEFAULT NULL,
  `refuse_reason` varchar(255) DEFAULT NULL,
  `loan_status` enum('APPROVAL','EXPIRED','PROGRESS','REFUSAL') DEFAULT NULL,
  `loan_type` enum('EQUALR','LUMPSUM') DEFAULT NULL,
  PRIMARY KEY (`loan_id`),
  KEY `FK6xxlcjc0rqtn5nq28vjnx5t9d` (`user_id`),
  CONSTRAINT `FK6xxlcjc0rqtn5nq28vjnx5t9d` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- 테이블 데이터 mydatabase.loans:~6 rows (대략적) 내보내기
INSERT INTO `loans` (`balance`, `loan_amount`, `loan_date`, `loan_rate`, `created_at`, `expiration_date`, `loan_id`, `updated_at`, `user_id`, `loan_reason`, `refuse_reason`, `loan_status`, `loan_type`) VALUES
	(300000, 300000, 5, 5, '2024-08-25 15:57:42.213899', '2025-01-25 15:57:42.195774', 1, '2024-08-25 19:16:01.649559', 1, '친구랑 여행 가기로 했어요', '지난달에 다녀와서 안돼', 'REFUSAL', 'LUMPSUM'),
	(533333, 500000, 5, 5, '2024-09-26 17:35:08.000000', '2024-09-30 17:35:13.000000', 6, '2024-09-26 17:35:08.000000', 5, '친구랑 팬시점에 가기로 했어요 ', '팬시를 5만원 어치 사는 건 너무 많다', 'EXPIRED', 'EQUALR'),
	(100000, 150000, 5, 4, '2024-09-04 14:51:41.166945', '2024-12-04 14:51:41.165939', 8, '2024-10-04 21:09:43.831074', 1, '사랑의 하츄핑 보러 가고 싶어요', '', 'APPROVAL', 'LUMPSUM'),
	(300000, 300000, 6, 4.5, '2024-10-10 14:52:24.816871', '2025-04-10 14:52:24.816871', 9, '2024-10-10 21:10:38.242724', 1, '지금 김영한님 강의 할인 중이라서 구매해야해요', '', 'APPROVAL', 'EQUALR'),
	(12000, 12000, 3, 4.5, '2024-10-10 16:27:02.936079', '2025-01-10 16:27:02.936079', 13, '2024-10-10 22:03:44.601234', 5, '친구 선물 살거에요', '', 'APPROVAL', 'LUMPSUM'),
	(0, 3000, 3, 4, '2024-10-10 21:58:12.992055', '2024-11-10 21:58:12.990813', 14, '2024-10-10 22:45:14.766549', 11006, '주식을 하고 시퍼요', NULL, 'APPROVAL', 'EQUALR');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
