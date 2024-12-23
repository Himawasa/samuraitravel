// このパッケージは、エンティティクラスを格納するためのものです。
// 一般的に、エンティティクラスは`entity`という名前のパッケージに配置されます。
package com.example.samuraitravel.entity;

// Javaでタイムスタンプ型データを扱うためのクラス。
// データベースの日時情報を格納する際に使用されます。
import java.sql.Timestamp;

// Jakarta Persistence API（JPA）の`@Column`アノテーションを使用するためのインポート。
// このアノテーションは、フィールドとデータベース列のマッピングを指定します。
import jakarta.persistence.Column;
// JPAの`@Entity`アノテーションを使用するためのインポート。
// このアノテーションを付与したクラスはエンティティとして扱われます。
import jakarta.persistence.Entity;
// JPAの`@GeneratedValue`アノテーションを使用するためのインポート。
// 主キーの値を自動生成する戦略を指定します。
import jakarta.persistence.GeneratedValue;
// JPAの`GenerationType`列挙型を使用するためのインポート。
// 主キーの生成方法（例: AUTO, IDENTITY, SEQUENCE）を指定するために使用されます。
import jakarta.persistence.GenerationType;
// JPAの`@Id`アノテーションを使用するためのインポート。
// このアノテーションは、主キーを定義するフィールドに付与します。
import jakarta.persistence.Id;
// JPAの`@Table`アノテーションを使用するためのインポート。
// このアノテーションを使うことで、エンティティがマッピングされるテーブル名を指定します。
import jakarta.persistence.Table;

// Lombokライブラリの`@Data`アノテーションを使用するためのインポート。
// Lombokを使うことで、ゲッター、セッター、`toString`などを自動生成できます。
import lombok.Data;

/**
 * Houseエンティティクラス
 * このクラスはデータベースの`houses`テーブルと対応しています。
 * エンティティクラスは、データベースとアプリケーション間のデータを
 * マッピングおよび管理するために使用されます。
 */
@Entity // このアノテーションを付けることで、このクラスがエンティティとして扱われます。
@Table(name = "houses") // エンティティがデータベースの"houses"テーブルに対応していることを指定します。
@Data // Lombokのアノテーション。ゲッター、セッター、`toString`、`equals`、`hashCode`を自動生成します。
public class House {

    /**
     * 主キー（ID）
     * 自動生成されるユニークな識別子です。
     * このフィールドはデータベースの"id"列と対応しています。
     */
    @Id // このフィールドがエンティティの主キーであることを示します。
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主キーの値をデータベースで自動生成します。
    @Column(name = "id") // データベース列"houses.id"とこのフィールドをマッピングします。
    private Integer id;

    /**
     * ハウス名
     * データベースの"name"列に対応します。
     */
    @Column(name = "name") // 列名が"name"であることを指定します。
    private String name;

    /**
     * 画像ファイル名
     * データベースの"image_name"列に対応します。
     */
    @Column(name = "image_name") // 列名が"image_name"であることを指定します。
    private String imageName;

    /**
     * ハウスの説明
     * データベースの"description"列に対応します。
     */
    @Column(name = "description") // 列名が"description"であることを指定します。
    private String description;

    /**
     * 価格
     * ハウスの価格情報を格納します。
     * データベースの"price"列に対応します。
     */
    @Column(name = "price") // 列名が"price"であることを指定します。
    private Integer price;

    /**
     * 収容人数
     * このハウスに収容可能な最大人数を表します。
     * データベースの"capacity"列に対応します。
     */
    @Column(name = "capacity") // 列名が"capacity"であることを指定します。
    private Integer capacity;

    /**
     * 郵便番号
     * データベースの"postal_code"列に対応します。
     */
    @Column(name = "postal_code") // 列名が"postal_code"であることを指定します。
    private String postalCode;

    /**
     * 住所
     * ハウスの所在地を表します。
     * データベースの"address"列に対応します。
     */
    @Column(name = "address") // 列名が"address"であることを指定します。
    private String address;

    /**
     * 電話番号
     * ハウスの問い合わせ先電話番号を表します。
     * データベースの"phone_number"列に対応します。
     */
    @Column(name = "phone_number") // 列名が"phone_number"であることを指定します。
    private String phoneNumber;

    /**
     * 作成日時
     * データベースでレコードが作成された日時を表します。
     * デフォルト値はデータベース側で設定されます（挿入不可、更新不可）。
     */
    @Column(name = "created_at", insertable = false, updatable = false) 
    // データ挿入や更新時に、このフィールドの値は無視されます。
    private Timestamp createdAt;

    /**
     * 更新日時
     * レコードが最後に更新された日時を表します。
     * デフォルト値はデータベース側で設定されます（挿入不可、更新不可）。
     */
    @Column(name = "updated_at", insertable = false, updatable = false) 
    // データ挿入や更新時に、このフィールドの値は無視されます。
    private Timestamp updatedAt;
}
