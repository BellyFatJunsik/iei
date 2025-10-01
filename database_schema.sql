-- 물리치료 원격재활 플랫폼 데이터베이스 스키마
-- MySQL 8.0 이상 버전 지원

CREATE DATABASE IF NOT EXISTS pt_project CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE pt_project;

-- 회원 테이블 (환자, 물리치료사, 관리자)
CREATE TABLE members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL,
    role ENUM('PATIENT', 'THERAPIST', 'ADMIN') NOT NULL,
    profile_image VARCHAR(500),
    status ENUM('ACTIVE', 'INACTIVE', 'PENDING') DEFAULT 'ACTIVE',
    
    -- 물리치료사 전용 필드
    license_number VARCHAR(100),
    license_image VARCHAR(500),
    hospital_name VARCHAR(200),
    hospital_address TEXT,
    introduction TEXT,
    specialties JSON,
    experience INT DEFAULT 0,
    education TEXT,
    certifications JSON,
    
    -- 환자 전용 필드
    birth_date DATE,
    gender ENUM('MALE', 'FEMALE', 'OTHER'),
    address TEXT,
    emergency_contact VARCHAR(20),
    medical_history JSON,
    current_condition TEXT,
    treatment_goal TEXT,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_username (username),
    INDEX idx_email (email),
    INDEX idx_role (role),
    INDEX idx_status (status)
);

-- 관리자 테이블
CREATE TABLE admins (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL,
    role ENUM('ADMIN') DEFAULT 'ADMIN',
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    last_login_at TIMESTAMP NULL,
    permissions JSON,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 운동 프로그램 테이블
CREATE TABLE programs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    category VARCHAR(50) NOT NULL,
    difficulty ENUM('BEGINNER', 'INTERMEDIATE', 'ADVANCED') NOT NULL,
    duration INT NOT NULL, -- 분 단위
    target_muscles JSON,
    equipment JSON,
    instructions JSON,
    video_url VARCHAR(500),
    thumbnail_url VARCHAR(500),
    status ENUM('ACTIVE', 'INACTIVE', 'DRAFT') DEFAULT 'ACTIVE',
    created_by BIGINT NOT NULL,
    created_by_name VARCHAR(100),
    
    -- 통계 필드
    view_count INT DEFAULT 0,
    like_count INT DEFAULT 0,
    completed_count INT DEFAULT 0,
    average_rating DECIMAL(3,2) DEFAULT 0.00,
    
    -- AI 분석 결과
    ai_analysis JSON,
    recommended_for JSON,
    contraindications JSON,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_category (category),
    INDEX idx_difficulty (difficulty),
    INDEX idx_status (status),
    INDEX idx_created_by (created_by),
    INDEX idx_view_count (view_count),
    INDEX idx_like_count (like_count)
);

-- 일정 관리 테이블
CREATE TABLE daily_schedules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    therapist_id BIGINT NOT NULL,
    patient_id BIGINT,
    program_id BIGINT,
    date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    type ENUM('REMOTE', 'VISIT') NOT NULL,
    status ENUM('SCHEDULED', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED') DEFAULT 'SCHEDULED',
    title VARCHAR(200),
    description TEXT,
    location VARCHAR(500), -- 원격재활의 경우 null
    meeting_link VARCHAR(500), -- 원격재활의 경우 필수
    notes TEXT, -- 치료사 메모
    patient_notes TEXT, -- 환자 메모
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_therapist_id (therapist_id),
    INDEX idx_patient_id (patient_id),
    INDEX idx_program_id (program_id),
    INDEX idx_date (date),
    INDEX idx_status (status),
    INDEX idx_type (type),
    
    FOREIGN KEY (therapist_id) REFERENCES members(id) ON DELETE CASCADE,
    FOREIGN KEY (patient_id) REFERENCES members(id) ON DELETE SET NULL,
    FOREIGN KEY (program_id) REFERENCES programs(id) ON DELETE SET NULL
);

-- 프로그램 완료 기록 테이블
CREATE TABLE program_completions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    program_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    therapist_id BIGINT,
    completed_at TIMESTAMP NOT NULL,
    actual_duration INT, -- 실제 소요 시간 (분)
    completion_notes TEXT,
    patient_feedback TEXT,
    difficulty_rating INT CHECK (difficulty_rating >= 1 AND difficulty_rating <= 5),
    satisfaction_rating INT CHECK (satisfaction_rating >= 1 AND satisfaction_rating <= 5),
    status ENUM('COMPLETED', 'PARTIAL', 'SKIPPED') DEFAULT 'COMPLETED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_program_id (program_id),
    INDEX idx_patient_id (patient_id),
    INDEX idx_therapist_id (therapist_id),
    INDEX idx_completed_at (completed_at),
    
    FOREIGN KEY (program_id) REFERENCES programs(id) ON DELETE CASCADE,
    FOREIGN KEY (patient_id) REFERENCES members(id) ON DELETE CASCADE,
    FOREIGN KEY (therapist_id) REFERENCES members(id) ON DELETE SET NULL
);

-- 후기/댓글 테이블
CREATE TABLE reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    program_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    therapist_id BIGINT,
    content TEXT NOT NULL,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    status ENUM('ACTIVE', 'HIDDEN', 'DELETED') DEFAULT 'ACTIVE',
    parent_id BIGINT, -- 대댓글인 경우 부모 댓글 ID
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_program_id (program_id),
    INDEX idx_patient_id (patient_id),
    INDEX idx_therapist_id (therapist_id),
    INDEX idx_parent_id (parent_id),
    INDEX idx_status (status),
    
    FOREIGN KEY (program_id) REFERENCES programs(id) ON DELETE CASCADE,
    FOREIGN KEY (patient_id) REFERENCES members(id) ON DELETE CASCADE,
    FOREIGN KEY (therapist_id) REFERENCES members(id) ON DELETE SET NULL,
    FOREIGN KEY (parent_id) REFERENCES reviews(id) ON DELETE CASCADE
);

-- 공지사항 테이블
CREATE TABLE notices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    category VARCHAR(50) NOT NULL,
    status ENUM('ACTIVE', 'INACTIVE', 'DRAFT') DEFAULT 'ACTIVE',
    created_by BIGINT NOT NULL,
    created_by_name VARCHAR(100),
    view_count INT DEFAULT 0,
    priority ENUM('HIGH', 'MEDIUM', 'LOW') DEFAULT 'MEDIUM',
    published_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_category (category),
    INDEX idx_status (status),
    INDEX idx_priority (priority),
    INDEX idx_published_at (published_at),
    
    FOREIGN KEY (created_by) REFERENCES members(id) ON DELETE CASCADE
);

-- 파일 업로드 테이블
CREATE TABLE file_uploads (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    original_name VARCHAR(255) NOT NULL,
    stored_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    file_size BIGINT NOT NULL,
    content_type VARCHAR(100) NOT NULL,
    uploader_id BIGINT NOT NULL,
    uploader_type ENUM('MEMBER', 'ADMIN') NOT NULL,
    category VARCHAR(50), -- PROFILE, LICENSE, PROGRAM_VIDEO, PROGRAM_THUMBNAIL, etc.
    related_id BIGINT, -- 관련 엔티티 ID
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    INDEX idx_uploader (uploader_id, uploader_type),
    INDEX idx_category (category),
    INDEX idx_related_id (related_id)
);

-- 시스템 로그 테이블
CREATE TABLE system_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    user_type ENUM('MEMBER', 'ADMIN') NOT NULL,
    action VARCHAR(100) NOT NULL,
    description TEXT,
    ip_address VARCHAR(45),
    user_agent TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    INDEX idx_user (user_id, user_type),
    INDEX idx_action (action),
    INDEX idx_created_at (created_at)
);

-- 초기 데이터 삽입
INSERT INTO members (username, password, name, email, phone, role, status) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '관리자', 'admin@ptproject.com', '010-0000-0000', 'ADMIN', 'ACTIVE');

INSERT INTO admins (username, password, name, email, phone, role, status) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '시스템 관리자', 'admin@ptproject.com', '010-0000-0000', 'ADMIN', 'ACTIVE');

-- 샘플 프로그램 데이터
INSERT INTO programs (name, description, category, difficulty, duration, target_muscles, equipment, instructions, status, created_by, created_by_name) VALUES
('목과 어깨 스트레칭', '장시간 컴퓨터 작업으로 인한 목과 어깨 근육의 긴장을 완화하는 스트레칭 프로그램입니다.', 'STRETCHING', 'BEGINNER', 15, '["목", "어깨", "상체"]', '["없음"]', '["목을 좌우로 천천히 돌리기", "어깨를 위아래로 움직이기", "팔을 앞뒤로 돌리기"]', 'ACTIVE', 1, '관리자'),
('무릎 강화 운동', '무릎 관절 주변 근육을 강화하여 무릎 건강을 개선하는 운동 프로그램입니다.', 'STRENGTH', 'INTERMEDIATE', 20, '["대퇴사두근", "햄스트링", "무릎"]', '["의자", "매트"]', '["의자에 앉아 다리 들어올리기", "벽에 기대어 스쿼트", "한발로 서기"]', 'ACTIVE', 1, '관리자'),
('균형감각 향상 운동', '고령자를 위한 균형감각 향상 및 낙상 예방 운동입니다.', 'BALANCE', 'BEGINNER', 25, '["전신", "균형감각"]', '["의자", "매트", "쿠션"]', '["의자에 앉아 발가락 들기", "한발로 서기", "걷기 운동"]', 'ACTIVE', 1, '관리자');

-- 샘플 공지사항 데이터
INSERT INTO notices (title, content, category, status, created_by, created_by_name, priority, published_at) VALUES
('PT 프로젝트 서비스 오픈 안내', '물리치료 원격재활 플랫폼 PT 프로젝트가 정식 오픈되었습니다. 많은 이용 부탁드립니다.', 'GENERAL', 'ACTIVE', 1, '관리자', 'HIGH', NOW()),
('개인정보 처리방침 안내', '개인정보 보호를 위한 처리방침이 업데이트되었습니다. 자세한 내용은 개인정보 처리방침을 확인해주세요.', 'GENERAL', 'ACTIVE', 1, '관리자', 'MEDIUM', NOW()),
('시스템 점검 안내', '2024년 1월 15일 오전 2시부터 4시까지 시스템 점검으로 인해 서비스가 일시 중단됩니다.', 'MAINTENANCE', 'ACTIVE', 1, '관리자', 'HIGH', NOW());
