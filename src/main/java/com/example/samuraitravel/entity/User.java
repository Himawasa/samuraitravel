package com.example.samuraitravel.entity;

// 必要なクラスをインポート
import java.security.Timestamp; // 日時を扱うクラス（データベースのタイムスタンプと対応）

import jakarta.persistence.Column; // カラムを指定するためのアノテーション
import jakarta.persistence.Entity; // このクラスをエンティティとして指定するアノテーション
import jakarta.persistence.GeneratedValue; // 主キーの値を自動生成するためのアノテーション
import jakarta.persistence.GenerationType; // 主キー生成戦略を指定するための列挙型
import jakarta.persistence.Id; // 主キーを指定するためのアノテーション
import jakarta.persistence.JoinColumn; // 外部キーの関連を指定するためのアノテーション
import jakarta.persistence.ManyToOne; // 多対一のリレーションを指定するためのアノテーション
import jakarta.persistence.Table; // テーブル名を指定するためのアノテーション

import lombok.Data; // Lombokの@Dataアノテーションで、ゲッター・セッター、toString、equals、hashCodeを自動生成

/**
 * Userエンティティクラス
 * このクラスはデータベースの "users" テーブルと対応しています。
 * エンティティはデータベースのテーブルとアプリケーション間のデータをやり取りするために使用されます。
 */
@Entity // このクラスがデータベースのエンティティであることを指定
@Table(name = "users") // このエンティティが "users" テーブルと対応していることを指定
@Data // Lombokを使用して、ゲッター、セッター、toString、equals、hashCodeを自動生成
public class User {
	
	/**
	 * 主キー（ID）
	 * データベースの "id" 列と対応しています。
	 * 自動生成されるユニークな識別子です。
	 */
	@Id // 主キーを指定
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	// 主キーの値を自動生成する戦略を指定。
	// GenerationType.IDENTITY は、データベースのAUTO_INCREMENT機能を使用する。
	@Column(name = "id") // データベースの列名を "id" にマッピング
	private Integer id;

	/**
	 * ユーザー名
	 * データベースの "name" 列と対応しています。
	 */
	@Column(name = "name") // データベースの列名を "name" にマッピング
	private String name;

	/**
	 * ユーザー名のふりがな
	 * データベースの "furigana" 列と対応しています。
	 * 例: "たなか たろう"
	 */
	@Column(name = "furigana") // データベースの列名を "furigana" にマッピング
	private String furigana;

	/**
	 * 郵便番号
	 * データベースの "postal_code" 列と対応しています。
	 * 例: "123-4567"
	 */
	@Column(name = "postal_code") // データベースの列名を "postal_code" にマッピング
	private String postalCode;

	/**
	 * 住所
	 * データベースの "address" 列と対応しています。
	 * 例: "東京都渋谷区道玄坂1-2-3"
	 */
	@Column(name = "address") // データベースの列名を "address" にマッピング
	private String address;

	/**
	 * 電話番号
	 * データベースの "phone_number" 列と対応しています。
	 * 例: "03-1234-5678"
	 */
	@Column(name = "phone_number") // データベースの列名を "phone_number" にマッピング
	private String phoneNumber;

	/**
	 * メールアドレス
	 * データベースの "email" 列と対応しています。
	 * 例: "example@example.com"
	 */
	@Column(name = "email") // データベースの列名を "email" にマッピング
	private String email;

	/**
	 * パスワード
	 * データベースの "password" 列と対応しています。
	 * 暗号化されたパスワードが格納されます。
	 */
	@Column(name = "password") // データベースの列名を "password" にマッピング
	private String password;

	/**
	 * 役割（Roleエンティティとの多対一のリレーション）
	 * データベースの "role_id" 列と対応しています。
	 * Roleテーブルのデータを参照する外部キー。
	 */
	@ManyToOne // ユーザーと役割の多対一の関係を指定
	@JoinColumn(name = "role_id") // 外部キー列を "role_id" にマッピング
	private Role role;

	/**
	 * アカウントの有効/無効状態
	 * データベースの "enabled" 列と対応しています。
	 * true: 有効、false: 無効
	 */
	@Column(name = "enabled") // データベースの列名を "enabled" にマッピング
	private Boolean enabled;

	/**
	 * 作成日時
	 * データベースの "created_at" 列と対応しています。
	 * デフォルトで現在の日時が設定され、挿入後に更新不可。
	 */
	@Column(name = "created_at", insertable = false, updatable = false) 
	// 挿入時のみ設定可能（更新不可）
	private Timestamp createdAt;

	/**
	 * 更新日時
	 * データベースの "updated_at" 列と対応しています。
	 * デフォルトで現在の日時が設定され、更新時に自動的に値が更新されます。
	 */
	@Column(name = "updated_at", insertable = false, updatable = false) 
	// 更新時に自動更新（挿入時には設定不可）
	private Timestamp updatedAt;
}
