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

-- 테이블 mydatabase.savings_products 구조 내보내기
DROP TABLE IF EXISTS `savings_products`;
CREATE TABLE IF NOT EXISTS `savings_products` (
  `max_price` int(11) NOT NULL DEFAULT 500000,
  `savings_date` int(11) NOT NULL,
  `savings_rate` double DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `savings_product_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `updated_at` datetime(6) NOT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`savings_product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- 테이블 데이터 mydatabase.savings_products:~6 rows (대략적) 내보내기
INSERT INTO `savings_products` (`max_price`, `savings_date`, `savings_rate`, `created_at`, `savings_product_id`, `updated_at`, `product_name`) VALUES
	(500000, 3, 4, '2024-09-19 11:39:39.000000', 1, '2024-09-19 11:39:41.000000', '시험 잘봐 적금'),
	(300000, 6, 4.5, '2024-10-09 13:12:07.000000', 2, '2024-10-09 13:12:11.000000', '활기찬 청소년 적금'),
	(400000, 2, 3.8, '2024-10-09 13:13:20.000000', 3, '2024-10-09 13:13:22.000000', '울트라 슈퍼 엑셀런트 적금'),
	(500000, 9, 5, '2024-10-09 13:15:09.000000', 4, '2024-10-09 13:15:18.000000', '인내심 만땅 적금'),
	(300000, 5, 3.5, '2024-10-09 13:16:30.000000', 5, '2024-10-09 13:16:40.000000', '2학기 열심히 해 적금'),
	(500000, 4, 4.2, '2024-10-09 13:17:54.000000', 6, '2024-10-09 13:18:00.000000', '뭐가 달라도 달라 적금');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
