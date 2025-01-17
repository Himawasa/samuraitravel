package com.example.samuraitravel.repository;

// 必要なクラスをインポート
import org.springframework.data.jpa.repository.JpaRepository; // JpaRepositoryインターフェースを使用するためのインポート

import com.example.samuraitravel.entity.VerificationToken; // VerificationTokenエンティティを使用するためのインポート

/**
 * VerificationTokenRepositoryインターフェース
 * 
 * VerificationTokenエンティティに対応するリポジトリクラス。
 * Spring Data JPAを使用して、データベースとのやり取りを簡単に行うことができます。
 * 主な役割：
 * - VerificationTokenエンティティのCRUD操作（作成、読み取り、更新、削除）を提供。
 * - カスタムメソッドを定義して特定の条件でデータを取得可能。
 */
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer> {

    /**
     * トークン文字列を基にVerificationTokenエンティティを検索するメソッド。
     * 
     * @param token 検索対象のトークン文字列
     * @return 該当するVerificationTokenエンティティ（存在しない場合はnullを返す）
     * 
     * Spring Data JPAが自動的にクエリを生成するため、
     * メソッド名を「findBy<フィールド名>」形式にする必要があります。
     * この場合、VerificationTokenエンティティの`token`フィールドを検索条件として使用します。
     */
    public VerificationToken findByToken(String token);

    /**
     * JpaRepositoryインターフェースの機能：
     * - findAll(): 全てのエンティティを取得する。
     * - findById(ID id): 主キー（id）を基にエンティティを取得。
     * - save(S entity): エンティティを保存または更新。
     * - delete(S entity): エンティティを削除。
     * 
     * カスタムメソッド（例: findByToken）は独自のビジネスロジックに対応したクエリを提供します。
     */
}
