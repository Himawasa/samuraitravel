-- 民宿情報を格納するテーブル
CREATE TABLE IF NOT EXISTS houses (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,  
    name VARCHAR(50) NOT NULL,  
    image_name VARCHAR(255),  
    description VARCHAR(255) NOT NULL,  
    price INT NOT NULL,  
    capacity INT NOT NULL,  
    postal_code VARCHAR(50) NOT NULL,  
    address VARCHAR(255) NOT NULL,  
    phone_number VARCHAR(50) NOT NULL,  
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,  
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 役割情報を格納するテーブル
-- 各役割には一意のIDと名前が付けられる
CREATE TABLE IF NOT EXISTS roles (
    -- idカラム: 各役割を一意に識別するための主キー
    -- AUTO_INCREMENT: 新しい役割が追加されるたびに自動的に値を1ずつ増やす
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,

    -- nameカラム: 役割名を格納
    -- 例: "管理者"、"利用者"
    -- NOT NULL: 必須項目として設定されている
    name VARCHAR(50) NOT NULL
);

-- ユーザー情報を格納するテーブル
CREATE TABLE IF NOT EXISTS users (
    -- idカラム: 各ユーザーを一意に識別するための主キー
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,

    -- nameカラム: ユーザーの名前を格納
    -- VARCHAR(50): 最大50文字の文字列を格納可能
    -- NOT NULL: 必須項目
    name VARCHAR(50) NOT NULL,

    -- furiganaカラム: ユーザー名のふりがなを格納
    -- 例: "たなか たろう"
    furigana VARCHAR(50) NOT NULL,

    -- postal_codeカラム: ユーザーの郵便番号を格納
    -- 例: "123-4567"
    postal_code VARCHAR(50) NOT NULL,

    -- addressカラム: ユーザーの住所を格納
    -- VARCHAR(255): 最大255文字の文字列を格納可能
    address VARCHAR(255) NOT NULL,

    -- phone_numberカラム: ユーザーの電話番号を格納
    -- 例: "090-1234-5678"
    phone_number VARCHAR(50) NOT NULL,

    -- emailカラム: ユーザーのメールアドレスを格納
    -- UNIQUE: 同じメールアドレスは一度しか登録できないようにする制約
    email VARCHAR(255) NOT NULL UNIQUE,

    -- passwordカラム: ユーザーのパスワードを格納
    -- VARCHAR(255): 暗号化されたパスワードを格納するための十分な長さ
    password VARCHAR(255) NOT NULL,    

    -- role_idカラム: ユーザーが持つ役割（rolesテーブルの外部キー）
    -- FOREIGN KEY制約により、rolesテーブルのidカラムを参照
    role_id INT NOT NULL, 

    -- enabledカラム: ユーザーアカウントの有効/無効状態を示す
    -- BOOLEAN: trueまたはfalseの値を格納
    enabled BOOLEAN NOT NULL,

    -- created_atカラム: レコードが作成された日時を格納
    -- DEFAULT CURRENT_TIMESTAMP: レコードが挿入される際に現在の日時を自動設定
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- updated_atカラム: レコードが更新された日時を格納
    -- ON UPDATE CURRENT_TIMESTAMP: レコードが更新されるたびに現在の日時を自動設定
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,    

    -- 外部キー制約
    -- role_idカラムがrolesテーブルのidカラムを参照
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE IF NOT EXISTS verification_tokens (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL UNIQUE,
    token VARCHAR(255) NOT NULL,        
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id) 
);

CREATE TABLE IF NOT EXISTS reservations (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    house_id INT NOT NULL,
    user_id INT NOT NULL,
    checkin_date DATE NOT NULL,
    checkout_date DATE NOT NULL,
    number_of_people INT NOT NULL,
    amount INT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (house_id) REFERENCES houses (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

