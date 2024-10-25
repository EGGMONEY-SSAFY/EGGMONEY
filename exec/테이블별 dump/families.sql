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

-- 테이블 mydatabase.families 구조 내보내기
DROP TABLE IF EXISTS `families`;
CREATE TABLE IF NOT EXISTS `families` (
  `created_at` datetime(6) NOT NULL,
  `family_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `present_id` bigint(20) DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL,
  `intro` varchar(255) DEFAULT NULL,
  `qr_code` varchar(255) DEFAULT NULL,
  `profile_image_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`family_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- 테이블 데이터 mydatabase.families:~3 rows (대략적) 내보내기
INSERT INTO `families` (`created_at`, `family_id`, `present_id`, `updated_at`, `intro`, `qr_code`, `profile_image_url`) VALUES
	('2024-09-11 15:55:27.000000', 1, 3, '2024-09-13 10:05:41.923159', '소영이네', NULL, NULL),
	('2024-09-12 11:07:37.276731', 2, 11006, '2024-09-12 11:26:56.788023', '싸피패밀리', NULL, NULL),
	('2024-10-10 21:03:07.269539', 3, 8, '2024-10-10 21:04:34.164050', '경준이네 가족', NULL, 'https://egg1money1nest.s3.amazonaws.com/family/Cap 2024-08-20 17-30-58-164.jpg');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
