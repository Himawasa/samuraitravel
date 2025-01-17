package com.example.samuraitravel.entity;

// 必要なインポート文
import java.sql.Timestamp; // データベースのタイムスタンプ型を扱うためのクラス

import jakarta.persistence.Column; // カラムを指定するためのアノテーション
import jakarta.persistence.Entity; // エンティティ（データベースのテーブルと対応するクラス）を指定
import jakarta.persistence.GeneratedValue; // 主キー値の生成方法を指定
import jakarta.persistence.GenerationType; // 主キー値生成の戦略を指定
import jakarta.persistence.Id; // 主キーを指定するためのアノテーション
import jakarta.persistence.JoinColumn; // 外部キーを指定するためのアノテーション
import jakarta.persistence.OneToOne; // 1対1の関連付けを指定
import jakarta.persistence.Table; // エンティティが対応するデータベースのテーブル名を指定

import lombok.Data; // Lombokライブラリを使用してgetter/setterやtoStringを自動生成

/**
 * VerificationTokenエンティティクラス
 * 
 * このクラスは、ユーザー認証時に使用するトークン（ランダムな文字列）を管理するエンティティです。
 * データベースの「verification_tokens」テーブルに対応し、認証用トークンの生成や検証に使用されます。
 * 主に以下のシナリオで利用されます：
 * 1. ユーザーがメール認証を行う際、トークンを生成し保存する。
 * 2. ユーザーが認証リンクをクリックした際、トークンの有効性を確認する。
 * 
 * テーブル構造は以下のようになっています：
 * - id: トークンID（主キー、自動インクリメント）
 * - user_id: トークンが関連付けられているユーザーID（外部キー）
 * - token: トークン文字列（ランダムな値）
 * - created_at: トークンの作成日時
 * - updated_at: トークンの更新日時
 */
@Entity
@Table(name = "verification_tokens") // エンティティが「verification_tokens」テーブルに対応することを指定
@Data // Lombokを使用してgetter/setterやtoStringメソッドを自動生成
public class VerificationToken {

    @Id // 主キー（Primary Key）を指定
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    // 主キーの生成戦略を指定（IDENTITYはデータベース側で自動インクリメント）
    @Column(name = "id") // テーブルの「id」カラムと対応
    private Integer id; // トークンID（主キー）

    @OneToOne // Userエンティティと1対1の関連付けを指定
    @JoinColumn(name = "user_id") 
    // 「user_id」カラムを外部キーとして「users」テーブルの主キーを参照するように指定
    private User user; // トークンが関連付けられているユーザー

    @Column(name = "token") // テーブルの「token」カラムと対応
    private String token; // 認証用トークン文字列（ランダムな値）

    @Column(name = "created_at", insertable = false, updatable = false) 
    // 「created_at」カラムはレコード作成時にデータベース側で自動的に設定
    private Timestamp createdAt; // トークンの作成日時

    @Column(name = "updated_at", insertable = false, updatable = false) 
    // 「updated_at」カラムはレコード更新時にデータベース側で自動的に更新
    private Timestamp updatedAt; // トークンの更新日時

    /**
     * このエンティティは以下のような機能を提供します：
     * 1. ユーザー認証のためのトークンを管理。
     * 2. トークンの作成日時と更新日時を記録。
     * 3. トークンがどのユーザーに関連付けられているかを追跡。
     * 
     * 例: ユーザーが新規登録時、認証用トークンを生成して保存。
     * また、パスワードリセットのリンクなどにも応用可能です。
     */
}
