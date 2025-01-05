package com.example.samuraitravel.repository;

// 必要なクラスをインポート
import org.springframework.data.jpa.repository.JpaRepository; // Spring Data JPAが提供する基本的なリポジトリ機能を使用

import com.example.samuraitravel.entity.Role;

/**
 * RoleRepositoryインターフェース
 * このインターフェースは "roles" テーブルに対するデータ操作を行います。
 * JpaRepositoryを継承することで、標準的なCRUD操作やクエリメソッドを利用可能にします。
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
    // JpaRepository<Role, Integer> の意味:
    // - Role: 操作対象のエンティティクラス
    // - Integer: エンティティの主キー（ID）のデータ型
}
