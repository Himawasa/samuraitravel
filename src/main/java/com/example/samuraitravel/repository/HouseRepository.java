// このパッケージは、リポジトリクラスを格納するためのものです。
// リポジトリはデータベース操作を担当し、アプリケーションのデータアクセス層を構成します。
package com.example.samuraitravel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
// Spring Data JPAのJpaRepositoryインターフェースをインポートします。
// JpaRepositoryは、基本的なCRUD操作（作成、読み取り、更新、削除）や
// ページネーション（ページ分割）、ソート機能を提供する強力なインターフェースです。
import org.springframework.data.jpa.repository.JpaRepository;

// Houseエンティティクラスをインポートします。
// このエンティティは、データベースの"houses"テーブルと対応しており、
// リポジトリを通じて操作されます。
import com.example.samuraitravel.entity.House;

/**
 * HouseRepositoryインターフェース
 * 
 * このインターフェースは、`houses`テーブルに対するデータベース操作を管理します。
 * JpaRepositoryを拡張することで、基本的なデータ操作メソッドを自動的に利用可能になります。
 * また、必要に応じてカスタムメソッドを追加することで、特定のクエリをサポートすることも可能です。
 */
public interface HouseRepository extends JpaRepository<House, Integer> {

	public Page<House> findByNameLike(String string, Pageable pageable);

	public Page<House> findByNameLikeOrAddressLikeOrderByCreatedAtDesc(String nameKeyword, String addressKeyword, Pageable pageable);  
    public Page<House> findByNameLikeOrAddressLikeOrderByPriceAsc(String nameKeyword, String addressKeyword, Pageable pageable);  
    public Page<House> findByAddressLikeOrderByCreatedAtDesc(String area, Pageable pageable);
    public Page<House> findByAddressLikeOrderByPriceAsc(String area, Pageable pageable);
    public Page<House> findByPriceLessThanEqualOrderByCreatedAtDesc(Integer price, Pageable pageable);
    public Page<House> findByPriceLessThanEqualOrderByPriceAsc(Integer price, Pageable pageable); 
    public Page<House> findAllByOrderByCreatedAtDesc(Pageable pageable);
    public Page<House> findAllByOrderByPriceAsc(Pageable pageable);    
	
	
	/*
	 * `extends JpaRepository<House, Integer>`の意味:
	 * - `House`: このリポジトリが操作するエンティティクラス。
	 *   データベースの`houses`テーブルとマッピングされています。
	 * - `Integer`: 主キー（id）の型。`House`エンティティでは`id`フィールドが`Integer`型です。
	 *
	 * JpaRepositoryによって自動的に提供される主なメソッド:
	 * 1. 保存・更新:
	 *    - `House save(House entity)`:
	 *      エンティティをデータベースに保存または更新します。
	 * 2. データ取得:
	 *    - `Optional<House> findById(Integer id)`:
	 *      主キー（id）を使ってエンティティを検索します。
	 *    - `List<House> findAll()`:
	 *      全てのエンティティを取得します。
	 * 3. 削除:
	 *    - `void deleteById(Integer id)`:
	 *      主キーを指定してエンティティを削除します。
	 *    - `void delete(House entity)`:
	 *      渡されたエンティティを削除します。
	 * 4. ページネーションとソート:
	 *    - `Page<House> findAll(Pageable pageable)`:
	 *      ページネーションを適用してエンティティを取得します。
	 *    - `List<House> findAll(Sort sort)`:
	 *      ソート順で全てのエンティティを取得します。
	 *
	 * カスタムクエリメソッドの例:
	 * - Spring Data JPAでは、規約に基づいたメソッド名を定義することで、特定のクエリを自動生成できます。
	 *   例: List<House> findByName(String name);
	 *   このメソッドは、`houses`テーブルの`name`列を使った検索クエリを生成します。
	 */
}
