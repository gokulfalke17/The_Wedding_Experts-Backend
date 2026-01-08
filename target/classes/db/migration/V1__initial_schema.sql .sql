-- =========================
-- USERS
-- =========================
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL UNIQUE,
    phone_verified BOOLEAN NOT NULL DEFAULT FALSE,
    email_verified BOOLEAN NOT NULL DEFAULT FALSE,
    role VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    remember_me_token VARCHAR(500),
    remember_me_expiry TIMESTAMP,
    reset_password_token VARCHAR(500),
    reset_password_expiry TIMESTAMP,
    email_verification_token VARCHAR(500),
    email_verification_expiry TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- =========================
-- PHONE OTP
-- =========================
CREATE TABLE phone_otp (
    id BIGSERIAL PRIMARY KEY,
    phone VARCHAR(20) NOT NULL,
    otp VARCHAR(10) NOT NULL,
    expiry_time TIMESTAMP NOT NULL,
    attempts INT NOT NULL,
    verified BOOLEAN NOT NULL
);

-- =========================
-- USER ADDRESSES
-- =========================
CREATE TABLE user_addresses (
    id BIGSERIAL PRIMARY KEY,
    address_line1 VARCHAR(255),
    address_line2 VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    country VARCHAR(100),
    pincode VARCHAR(20),
    is_deleted BOOLEAN DEFAULT FALSE,
    user_id BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_user_address_user
        FOREIGN KEY (user_id) REFERENCES users(id)
);

-- =========================
-- PROFESSIONAL PROFILES
-- =========================
CREATE TABLE professional_profiles (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    business_name VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    experience_years INT,
    city VARCHAR(100),
    state VARCHAR(100),
    is_verified BOOLEAN DEFAULT FALSE,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_professional_user
        FOREIGN KEY (user_id) REFERENCES users(id)
);
