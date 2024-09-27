-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        11.5.2-MariaDB - mariadb.org binary distribution
-- 서버 OS:                        Win64
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

-- 테이블 데이터 eggmoney.deposit_products:~4 rows (대략적) 내보내기
INSERT INTO `deposit_products` (`deposit_rate`, `created_at`, `deposit_product_id`, `updated_at`, `deposit_date`, `product_name`) VALUES
	(3.5, '2024-09-11 00:00:00.000000', 1, '2024-09-11 00:00:00.000000', 3, 'Dream Builder'),
	(4, '2024-09-11 00:00:00.000000', 2, '2024-09-11 00:00:00.000000', 6, 'Smart Strat'),
	(4.5, '2024-09-11 00:00:00.000000', 3, '2024-09-11 00:00:00.000000', 9, 'Future Rich'),
	(5, '2024-09-11 00:00:00.000000', 4, '2024-09-11 00:00:00.000000', 12, 'Platinum');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
