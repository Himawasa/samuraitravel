package com.example.samuraitravel.entity;

// 必要なJPAアノテーションをインポート
import jakarta.persistence.Column; // カラムを指定するためのアノテーション
import jakarta.persistence.Entity; // このクラスをエンティティとして指定するアノテーション
import jakarta.persistence.GeneratedValue; // 主キーの値を自動生成するためのアノテーション
import jakarta.persistence.GenerationType; // 主キー生成戦略を指定するための列挙型
import jakarta.persistence.Id; // 主キーを指定するためのアノテーション
import jakarta.persistence.Table; // テーブル名を指定するためのアノテーション

// Lombokをインポート
import lombok.Data; // Lombokの@Dataアノテーションで、ゲッター・セッター、toString、equals、hashCodeを自動生成

/**
 * Roleエンティティクラス
 * このクラスはデータベースの "roles" テーブルと対応しています。
 * エンティティはデータベースのテーブルとアプリケーション間のデータを
 * やり取りするために使用されます。
 */
@Entity // このクラスがデータベースのエンティティであることを指定
@Table(name = "roles") // このエンティティが "roles" テーブルと対応していることを指定
@Data // Lombokを使用して、ゲッター、セッター、toString、equals、hashCodeを自動生成
public class Role {
	
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
	 * 役割名
	 * データベースの "name" 列と対応しています。
	 * 例: "ADMIN" や "USER" などの役割名が格納されます。
	 */
	@Column(name = "name") // データベースの列名を "name" にマッピング
	private String name;
}
