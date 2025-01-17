package com.example.samuraitravel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
// 必要なクラスをインポート
import org.springframework.data.jpa.repository.JpaRepository; // JpaRepositoryインターフェースを使用するためのインポート

import com.example.samuraitravel.entity.User; // Userエンティティを使用するためのインポート

/**
 * UserRepositoryインターフェース
 * 
 * Userエンティティに対応するリポジトリクラス。
 * Spring Data JPAを利用して、データベース操作を簡略化するための抽象化されたレイヤーを提供します。
 * 主な役割：
 * - Userエンティティに対するCRUD操作（作成、読み取り、更新、削除）を提供。
 * - カスタムメソッドを定義することで、特定条件でデータを取得可能。
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * メールアドレスを基にUserエンティティを検索するメソッド。
     * 
     * @param email 検索対象のメールアドレス
     * @return 該当するUserエンティティ（存在しない場合はnullを返す）
     * 
     * メソッド名を「findBy<フィールド名>」形式にすることで、Spring Data JPAが自動的に適切なクエリを生成します。
     * この場合、Userエンティティの`email`フィールドを検索条件として使用します。
     */
    public User findByEmail(String email);
    public Page<User> findByNameLikeOrFuriganaLike(String nameKeyword, String furiganaKeyword, Pageable pageable);
    

    /**
     * JpaRepositoryインターフェースの主な機能：
     * - findAll(): 全てのエンティティを取得する。
     * - findById(ID id): 主キー（id）を基にエンティティを取得。
     * - save(S entity): エンティティを保存または更新。
     * - delete(S entity): エンティティを削除。
     * 
     * カスタムメソッド（例: findByEmail）は、独自のビジネスロジックに対応するクエリを提供します。
     */
}
