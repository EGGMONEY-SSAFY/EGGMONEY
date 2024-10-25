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

-- 테이블 mydatabase.deposit_products 구조 내보내기
DROP TABLE IF EXISTS `deposit_products`;
CREATE TABLE IF NOT EXISTS `deposit_products` (
  `deposit_date` int(11) NOT NULL,
  `deposit_rate` double DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `deposit_product_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `updated_at` datetime(6) NOT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`deposit_product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- 테이블 데이터 mydatabase.deposit_products:~6 rows (대략적) 내보내기
INSERT INTO `deposit_products` (`deposit_date`, `deposit_rate`, `created_at`, `deposit_product_id`, `updated_at`, `product_name`) VALUES
	(4, 4.5, '2024-09-19 11:18:04.000000', 1, '2024-09-19 11:18:11.000000', '적금 같은 예금'),
	(8, 4, '2024-09-19 11:21:18.000000', 2, '2024-09-19 11:21:18.000000', '너와 나의 멋진 예금'),
	(2, 3.5, '2024-10-09 13:08:12.000000', 3, '2024-10-09 13:08:22.000000', '반짝반짝 빛나는 예금'),
	(5, 3.8, '2024-10-09 13:09:43.000000', 4, '2024-10-09 13:09:49.000000', '당신을 위한 예금'),
	(6, 4.2, '2024-10-09 13:18:53.000000', 5, '2024-10-09 13:19:11.000000', '티끌 모아 태산 예금'),
	(6, 4, '2024-10-09 22:41:55.000000', 6, '2024-10-09 22:41:58.000000', '티끌 같은 예금');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
