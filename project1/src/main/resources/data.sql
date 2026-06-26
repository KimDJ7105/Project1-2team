-- 테스트 회원 생성
INSERT IGNORE INTO user (username, password, name, age)
VALUES ('hong123', 'hongpasswd', '홍길동', 30);

-- 홍길동 회원당 계좌 3개 개설 (계좌 ID: 1, 2, 3)
INSERT IGNORE INTO account (user_id, account_number, balance, is_blocked)
VALUES
(1, '110-123-000001', 500000, FALSE),  -- 정상 계좌 1
(1, '110-123-000002', 1200000, FALSE), -- 정상 계좌 2
(1, '110-123-000003', 10000, TRUE);    -- FDS 차단 시나리오용 계좌 is_blocked : TRUE

-- FDS 점수 100점 기록
INSERT IGNORE INTO trading_history (trade_type, sending_account, receiving_account, amount, message, is_fail, fail_msg)
VALUES ('송금', 3, 1, 5000000, '의심거래테스트', TRUE, '의심 거래 발견:고액이체');

-- 1번 거래 기록(위험 거래)에 대한 FDS 탐지 결과 매핑
INSERT IGNORE INTO fds (history_id, risk_rank, risk_score, risk_reason)
VALUES (1, '위험', 100, '단시간 내 비정상적 고액 송금 시도 및 해외 IP 우회 접속 탐지');