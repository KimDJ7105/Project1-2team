-- 0. 충돌 방지를 위해 DB 전부 밀어버리기
DROP TABLE IF EXISTS fds, trading_history, login_history, account, user;
-- 1. 회원 테이블 (user)
CREATE TABLE user (
    user_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '회원번호',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '로그인 ID',
    password VARCHAR(60) NOT NULL COMMENT '비밀번호',
    name VARCHAR(30) NOT NULL COMMENT '이름',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '가입일',
    age INT NOT NULL COMMENT '나이',
    login_fail_count INT DEFAULT 0 COMMENT '로그인 실패 횟수',
    CONSTRAINT chk_user_age CHECK (age > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 2. 계좌 테이블 (account)
CREATE TABLE account (
    account_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '계좌 식별 번호',
    user_id INT NOT NULL COMMENT '회원번호',
    account_number VARCHAR(20) NOT NULL UNIQUE COMMENT '계좌번호',
    balance INT DEFAULT 0 COMMENT '잔액',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '계좌 생성일',
    is_blocked BOOLEAN DEFAULT FALSE COMMENT '계좌가 fds로 막혔는지 여부',
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 3. 로그인 기록 테이블 (login_history)
CREATE TABLE login_history (
    history_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '기록 식별 번호',
    user_id INT NOT NULL COMMENT '회원번호',
    location VARCHAR(25) NOT NULL COMMENT '로그인 위치',
    ip VARCHAR(15) NOT NULL COMMENT '접속 ip',
    device VARCHAR(30) NOT NULL COMMENT '접속 기기',
    is_fail BOOLEAN NOT NULL DEFAULT FALSE COMMENT '로그인 성공 여부',
    login_date DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '로그인 일시',
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 4. 거래 내용 테이블 (trading_history)
CREATE TABLE trading_history (
    history_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '거래 기록 번호',
    trade_type VARCHAR(10) NOT NULL COMMENT '거래 종류 (송금, 출금, 입금)',
    sending_account INT NOT NULL COMMENT '출금 계좌 식별 번호(외래키)',
    receiving_account INT COMMENT '입금 계좌 식별 번호(외래키)',
    amount INT NOT NULL COMMENT '거래 금액',
    message VARCHAR(15) COMMENT '입금 이력에 출력될 메시지',
    trade_date DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '거래 일시',
    is_fail BOOLEAN NOT NULL DEFAULT FALSE COMMENT '거래 성공 여부',
    fail_msg VARCHAR(20) COMMENT '거래 실패 이유',
    FOREIGN KEY (sending_account) REFERENCES account(account_id),
    FOREIGN KEY (receiving_account) REFERENCES account(account_id),
    CONSTRAINT chk_trade_amount CHECK (amount > 0),
    CONSTRAINT chk_trade_type CHECK (trade_type IN ('송금', '출금', '입금'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 5. FDS 결과 테이블 (fds)
CREATE TABLE fds (
    fds_id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'fds 기록 번호',
    history_id INT NOT NULL COMMENT '거래 기록 번호(외래키)',
    account_id INT NOT NULL COMMENT '대상 계좌 식별 번호',
    risk_rank VARCHAR(10) NOT NULL COMMENT 'fds 검사 결과 (정상 / 주의 / 위험)',
    fds_date DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '탐지 시간',
    risk_score INT NOT NULL COMMENT '탐지 점수',
    risk_reason VARCHAR(100) NOT NULL COMMENT '탐지 이유',
    FOREIGN KEY (history_id) REFERENCES trading_history(history_id) ON DELETE CASCADE,
    FOREIGN KEY (account_id) REFERENCES account(account_id) ON DELETE CASCADE,
    CONSTRAINT chk_fds_risk_rank CHECK (risk_rank IN ('정상', '주의', '위험'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;