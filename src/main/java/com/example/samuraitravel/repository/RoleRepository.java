package com.example.samuraitravel.repository;

// 必要なクラスをインポート
import org.springframework.data.jpa.repository.JpaRepository; // Spring Data JPAが提供する基本的なリポジトリ機能を使用

import com.example.samuraitravel.entity.Role; // Roleエンティティクラスを使用

/**
 * RoleRepositoryインターフェース
 * 
 * このインターフェースは、"roles" テーブルに対するデータ操作を行います。
 * 
 * 役割:
 * - "roles" テーブルに関連するデータ操作（CRUD操作）を提供します。
 * - Spring Data JPAの JpaRepository を継承しているため、標準的なデータ操作が自動的に実装されます。
 * - カスタムクエリメソッドを定義することで、特定の条件に基づく検索も簡単に実装できます。
 * 
 * 使用ポイント:
 * - JpaRepositoryを継承することで、以下のような標準的なデータ操作を利用可能：
 *   - データの保存 (`save`)
 *   - 主キーでの検索 (`findById`)
 *   - 全件取得 (`findAll`)
 *   - 削除 (`delete`)
 * - さらに、Spring Data JPAのクエリメソッドを使用して、独自の検索ロジックを追加できます。
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {

    /**
     * ロール名を基にRoleエンティティを検索するメソッド。
     * 
     * 役割:
     * - "roles" テーブルの `name` カラムを検索して、該当するロールを取得します。
     * - Spring Data JPAがメソッド名からSQLクエリを自動生成します。
     * - 例えば、"ROLE_ADMIN" という名前のロールを取得する場合に使用します。
     * 
     * 使用例:
     * - `roleRepository.findByName("ROLE_ADMIN")` を呼び出すと、"ROLE_ADMIN" という名前のロールを検索します。
     * 
     * 自動生成されるクエリの例:
     * - 実行されるSQL:
     *   ```sql
     *   SELECT * FROM roles WHERE name = ?;
     *   ```
     * 
     * @param name 検索対象のロール名
     * @return 該当するRoleエンティティ（存在しない場合はnullを返す）
     */
    public Role findByName(String name);

    // JpaRepository<Role, Integer> の詳細:
    // - Role: 操作対象のエンティティクラス（"roles" テーブルに対応）
    // - Integer: エンティティの主キー（ID）のデータ型
}
