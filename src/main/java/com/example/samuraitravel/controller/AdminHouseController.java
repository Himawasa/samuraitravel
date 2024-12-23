package com.example.samuraitravel.controller;

// 必要なパッケージをインポート
import org.springframework.data.domain.Page; // ページネーション用のデータ構造
import org.springframework.data.domain.Pageable; // ページネーション設定
import org.springframework.data.domain.Sort.Direction; // ソートの方向を指定（昇順/降順）
import org.springframework.data.web.PageableDefault; // Pageableのデフォルト設定
import org.springframework.stereotype.Controller; // コントローラーとして認識するアノテーション
import org.springframework.ui.Model; // ビューにデータを渡すためのオブジェクト
import org.springframework.web.bind.annotation.GetMapping; // HTTP GETリクエストを処理
import org.springframework.web.bind.annotation.PathVariable; // URLのパスパラメータを取得
import org.springframework.web.bind.annotation.RequestMapping; // ベースパスを設定
import org.springframework.web.bind.annotation.RequestParam; // クエリパラメータを取得

import com.example.samuraitravel.entity.House; // Houseエンティティをインポート
import com.example.samuraitravel.repository.HouseRepository; // HouseRepositoryをインポート

/**
 * 管理者向け民宿管理コントローラー
 * URLベース: /admin/houses
 * 民宿の一覧表示、検索、詳細表示を提供します。
 */
@Controller
@RequestMapping("/admin/houses") // このクラスのすべてのリクエストに適用されるベースURL
public class AdminHouseController {

    // データベース操作用のリポジトリ
    private final HouseRepository houseRepository;

    /**
     * コンストラクタ
     * Springの依存性注入（DI）でリポジトリを注入
     *
     * @param houseRepository HouseRepositoryインスタンス
     */
    public AdminHouseController(HouseRepository houseRepository) {
        this.houseRepository = houseRepository; // DIによって注入されたリポジトリをセット
    }

    /**
     * 民宿一覧ページを表示する
     *
     * @param model ビューにデータを渡すためのオブジェクト
     * @param pageable ページネーション設定（デフォルト: 1ページ10件、ID順昇順）
     * @param keyword 検索キーワード（オプション）
     * @return 使用するビューのテンプレート名（admin/houses/index）
     */
    @GetMapping
    public String index(Model model, 
                        @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, 
                        @RequestParam(name = "keyword", required = false) String keyword) {
        // ページネーション対応のデータを保持
        Page<House> housePage;

        // 検索キーワードが指定されている場合は部分一致検索
        if (keyword != null && !keyword.isEmpty()) {
            housePage = houseRepository.findByNameLike("%" + keyword + "%", pageable);
        } else {
            // 検索キーワードが指定されていない場合は全件取得
            housePage = houseRepository.findAll(pageable);
        }

        // モデルにデータを追加
        model.addAttribute("housePage", housePage); // 民宿のページネーションデータ
        model.addAttribute("keyword", keyword);    // 再表示用の検索キーワード

        // 使用するテンプレートを返す
        return "admin/houses/index";
    }

    /**
     * 民宿の詳細ページを表示する
     *
     * @param id 民宿のID（URLパスから取得）
     * @param model ビューにデータを渡すためのオブジェクト
     * @return 使用するビューのテンプレート名（admin/houses/show）
     */
    @GetMapping("/{id}")
    public String show(@PathVariable(name = "id") Integer id, Model model) {
        // 指定されたIDに対応する民宿データを取得
        // getReferenceByIdは、データベースから該当するデータを遅延ロードで取得
        House house = houseRepository.getReferenceById(id);

        // モデルにデータを追加
        model.addAttribute("house", house); // 詳細表示用の民宿データ

        // 使用するテンプレートを返す
        return "admin/houses/show";
    }
}
