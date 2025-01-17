package com.example.samuraitravel.entity;

// 必要なクラスをインポート
import java.sql.Timestamp; // データベースのタイムスタンプ型に対応

import jakarta.persistence.Column; // データベースのカラムとフィールドを関連付ける
import jakarta.persistence.Entity; // このクラスをエンティティとして指定
import jakarta.persistence.GeneratedValue; // 主キーの値を自動生成するアノテーション
import jakarta.persistence.GenerationType; // 主キーの生成方法を指定する列挙型
import jakarta.persistence.Id; // 主キーを指定
import jakarta.persistence.JoinColumn; // 外部キーの指定
import jakarta.persistence.ManyToOne; // 多対一のリレーションを指定
import jakarta.persistence.Table; // テーブル名を指定

import lombok.Data; // Lombokを使用して、ゲッター、セッターなどを自動生成

/**
 * Userエンティティクラス
 * このクラスはデータベースの "users" テーブルと対応しており、
 * アプリケーションとデータベース間のデータをやり取りするために使用されます。
 */
@Entity // このクラスがエンティティであることを指定
@Table(name = "users") // このエンティティがデータベースの "users" テーブルと関連付けられることを指定
@Data // Lombokを使用して、標準的なゲッター、セッター、toString、equals、hashCodeを自動生成
public class User {

    // 主キー（ID）
    @Id // 主キーとして設定
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    // IDを自動生成（データベースのAUTO_INCREMENTに依存）
    @Column(name = "id") // "id"列にマッピング
    private Integer id;

    // ユーザー名
    @Column(name = "name") // "name"列にマッピング
    private String name;

    // ユーザーのふりがな
    @Column(name = "furigana") // "furigana"列にマッピング
    private String furigana;

    // 郵便番号
    @Column(name = "postal_code") // "postal_code"列にマッピング
    private String postalCode;

    // 住所
    @Column(name = "address") // "address"列にマッピング
    private String address;

    // 電話番号
    @Column(name = "phone_number") // "phone_number"列にマッピング
    private String phoneNumber;

    // メールアドレス（ログイン時のユーザー名としても使用）
    @Column(name = "email") // "email"列にマッピング
    private String email;

    // 暗号化されたパスワード
    @Column(name = "password") // "password"列にマッピング
    private String password;

    // ユーザーの役割（Roleエンティティとの関連付け）
    @ManyToOne // Roleエンティティとの多対一の関係を指定
    @JoinColumn(name = "role_id") // "role_id"列を外部キーとして指定
    private Role role;

    // アカウントの有効/無効状態
    @Column(name = "enabled") // "enabled"列にマッピング
    private Boolean enabled;

    // レコードの作成日時（自動設定され、更新不可）
    @Column(name = "created_at", insertable = false, updatable = false) 
    private Timestamp createdAt;

    // レコードの更新日時（自動更新される）
    @Column(name = "updated_at", insertable = false, updatable = false) 
    private Timestamp updatedAt;
}
