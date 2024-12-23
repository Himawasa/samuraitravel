-- housesテーブルを作成するSQL文。
-- IF NOT EXISTS: 指定したテーブルが既に存在している場合は、このSQL文をスキップしてエラーを回避する。
-- 新しいテーブルを作成する前に、テーブルが存在しているかどうかを確認するための条件。
CREATE TABLE IF NOT EXISTS houses (
	
    -- idカラム: 各レコードを一意に識別するための主キー。
    -- INT型: 整数を格納するデータ型。
    -- NOT NULL: このカラムにはNULL値を許容しない。
    -- AUTO_INCREMENT: 新しいレコードが挿入されるたびに自動的に値を1ずつ増やす。
    -- PRIMARY KEY: テーブル内で一意の値を保証する制約。主キーとして設定。
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,  

    -- nameカラム: 物件名を格納するカラム。
    -- VARCHAR(50): 最大50文字の可変長文字列を格納可能なデータ型。
    -- NOT NULL: このカラムにはNULL値を許容しない。必ず値を入力する必要がある。
    name VARCHAR(50) NOT NULL,  

    -- image_nameカラム: 物件画像のファイル名を格納するカラム。
    -- VARCHAR(255): 最大255文字の可変長文字列を格納可能なデータ型。
    -- NULLを許容（NOT NULL制約がないため）。画像が設定されていない場合はNULL値が格納される可能性がある。
    image_name VARCHAR(255),  

    -- descriptionカラム: 物件の説明を格納するカラム。
    -- VARCHAR(255): 最大255文字の可変長文字列を格納可能なデータ型。
    -- NOT NULL: このカラムにはNULL値を許容しない。物件の説明は必須項目。
    description VARCHAR(255) NOT NULL,  

    -- priceカラム: 物件の価格を格納するカラム。
    -- INT型: 整数値を格納するデータ型。例: 価格が100000円の場合、100000として格納。
    -- NOT NULL: NULL値を許容しない。価格は必須項目。
    price INT NOT NULL,  

    -- capacityカラム: 物件の収容可能人数を格納するカラム。
    -- INT型: 整数値を格納するデータ型。例: 最大収容人数が4人の場合、4として格納。
    -- NOT NULL: NULL値を許容しない。収容人数は必須項目。
    capacity INT NOT NULL,  

    -- postal_codeカラム: 郵便番号を格納するカラム。
    -- VARCHAR(50): 最大50文字の可変長文字列を格納可能なデータ型。
    -- NOT NULL: NULL値を許容しない。郵便番号は必須項目。
    -- 例: "123-4567" のような郵便番号が入力される。
    postal_code VARCHAR(50) NOT NULL,  

    -- addressカラム: 物件の住所を格納するカラム。
    -- VARCHAR(255): 最大255文字の可変長文字列を格納可能なデータ型。
    -- NOT NULL: NULL値を許容しない。住所は必須項目。
    -- 例: "東京都渋谷区道玄坂1-2-3" のような住所が入力される。
    address VARCHAR(255) NOT NULL,  

    -- phone_numberカラム: 物件の連絡先電話番号を格納するカラム。
    -- VARCHAR(50): 最大50文字の可変長文字列を格納可能なデータ型。
    -- NOT NULL: NULL値を許容しない。電話番号は必須項目。
    -- 例: "03-1234-5678" のような電話番号が入力される。
    phone_number VARCHAR(50) NOT NULL,  

    -- created_atカラム: レコードが作成された日時を格納するカラム。
    -- DATETIME型: 年月日と時刻を記録するデータ型。
    -- NOT NULL: NULL値を許容しない。作成日時は必須項目。
    -- DEFAULT CURRENT_TIMESTAMP: レコードが挿入される際に、デフォルトで現在の日時を自動的に格納。
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,  

    -- updated_atカラム: レコードが更新された日時を格納するカラム。
    -- DATETIME型: 年月日と時刻を記録するデータ型。
    -- NOT NULL: NULL値を許容しない。更新日時は必須項目。
    -- DEFAULT CURRENT_TIMESTAMP: レコードが挿入される際に、デフォルトで現在の日時を格納。
    -- ON UPDATE CURRENT_TIMESTAMP: レコードが更新されるたびに、現在の日時に自動的に更新される。
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
