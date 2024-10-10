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

-- 테이블 mydatabase.notifications 구조 내보내기
DROP TABLE IF EXISTS `notifications`;
CREATE TABLE IF NOT EXISTS `notifications` (
  `notification_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `is_read` bit(1) NOT NULL,
  `message` varchar(255) NOT NULL,
  `notification_type` enum('가족연결요청','대출거절','대출상환','대출승인','대출요청','예금만기','용돈변경','용돈입금','적금납부','적금만기','지정가매도체결','지정가매수체결','출금거절','출금승인','출금요청','투자비율변경') NOT NULL,
  `send_user_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`notification_id`),
  KEY `FKkbm62d1rto0ccqdpkiy4o3m09` (`send_user_id`),
  KEY `FK9y21adhxn0ayjhfocscqox7bh` (`user_id`),
  CONSTRAINT `FK9y21adhxn0ayjhfocscqox7bh` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKkbm62d1rto0ccqdpkiy4o3m09` FOREIGN KEY (`send_user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- 테이블 데이터 mydatabase.notifications:~18 rows (대략적) 내보내기
INSERT INTO `notifications` (`notification_id`, `created_at`, `updated_at`, `is_read`, `message`, `notification_type`, `send_user_id`, `user_id`) VALUES
	(1, '2024-10-10 16:12:21.367022', '2024-10-10 16:12:21.367022', b'0', '부모님이 대출을 승인하셨습니다.', '대출승인', 3, 1),
	(2, '2024-10-10 16:13:00.765302', '2024-10-10 16:13:00.765302', b'0', '부모님이 출금요청을 승인했습니다.', '출금승인', 3, 1),
	(5, '2024-09-02 16:27:02.978937', '2024-09-02 16:27:02.978937', b'1', '자녀가 대출을 요청했습니다.', '대출요청', 1, 3),
	(6, '2024-10-10 21:04:17.552347', '2024-10-10 21:04:17.552347', b'0', '자녀가 출금을 요청했습니다.', '출금요청', 1, 3),
	(9, '2024-08-25 21:19:52.618885', '2024-08-25 21:19:52.618885', b'1', '부모님이 대출을 거절하셨습니다.', '대출거절', 3, 1),
	(11, '2024-10-10 22:03:06.700661', '2024-10-10 22:03:06.700661', b'0', '부모님이 대출을 승인하셨습니다.', '대출승인', 3, 5),
	(12, '2024-10-10 22:03:44.597853', '2024-10-10 22:03:44.597853', b'0', '부모님이 대출을 승인하셨습니다.', '대출승인', 3, 5),
	(13, '2024-09-10 22:05:35.386544', '2024-09-10 22:05:35.386544', b'0', '부모님이 출금요청을 승인했습니다.', '출금승인', 3, 1),
	(15, '2024-10-10 22:09:21.066403', '2024-10-10 22:09:29.176135', b'1', '자녀가 대출을 요청했습니다.', '대출요청', 1, 3),
	(16, '2024-09-04 22:09:50.863652', '2024-09-04 22:09:50.863652', b'0', '부모님이 대출을 승인하셨습니다.', '대출승인', 3, 1),
	(17, '2024-10-10 22:13:57.019763', '2024-10-10 22:14:18.415153', b'1', '자녀가 출금을 요청했습니다.', '출금요청', 1, 3),
	(18, '2024-09-09 22:35:21.852100', '2024-09-09 22:35:21.852100', b'0', '자녀가 출금을 요청했습니다.', '출금요청', 1, 3),
	(19, '2024-09-18 22:38:17.751420', '2024-09-18 22:38:17.751420', b'1', '예금이 만기에 도달하여 자동해지되었습니다.', '예금만기', NULL, 1),
	(20, '2024-09-18 22:45:29.807631', '2024-09-18 22:45:29.807631', b'1', '적금이 만기에 도달하여 자동해지되었습니다.', '적금만기', NULL, 1),
	(21, '2024-10-10 23:01:52.809114', '2024-10-10 23:01:52.809114', b'1', '부모님이 출금요청을 거절했습니다.', '출금거절', 3, 1),
	(22, '2024-10-11 00:13:17.294492', '2024-10-11 00:13:17.294492', b'0', '용돈이 변경되었습니다.', '용돈변경', 3, 1),
	(23, '2024-08-24 00:59:53.447513', '2024-08-24 00:59:53.447513', b'0', '자녀가 대출을 요청했습니다.', '대출요청', 1, 3),
	(24, '2024-09-02 16:52:21.367022', '2024-10-11 01:54:29.497099', b'1', 'IT 5주를 주당 1579알에 매도하였습니다.', '지정가매도체결', NULL, 1);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
