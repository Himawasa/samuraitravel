package com.example.samuraitravel.controller;

// 必要なクラスをインポート
import org.springframework.data.domain.Page; // ページング機能を提供するクラス
import org.springframework.data.domain.Pageable; // ページング情報を持つインターフェース
import org.springframework.data.domain.Sort.Direction; // ソートの方向を指定するクラス
import org.springframework.data.web.PageableDefault; // デフォルトのページング設定を提供するアノテーション
import org.springframework.stereotype.Controller; // コントローラークラスを示すアノテーション
import org.springframework.ui.Model; // ビューにデータを渡すためのクラス
import org.springframework.web.bind.annotation.GetMapping; // HTTP GETリクエストを処理するためのアノテーション
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping; // ベースURLをマッピングするためのアノテーション
import org.springframework.web.bind.annotation.RequestParam; // URLパラメータをマッピングするためのアノテーション

import com.example.samuraitravel.entity.House; // 民宿エンティティクラス
import com.example.samuraitravel.repository.HouseRepository; // 民宿データを操作するリポジトリ

/**
 * 民宿情報を表示するためのコントローラークラス。
 * ベースURL: "/houses"
 */
@Controller
@RequestMapping("/houses")
public class HouseController {

    // 民宿データを操作するためのリポジトリ
    private final HouseRepository houseRepository;

    /**
     * コンストラクタでリポジトリを注入
     *
     * @param houseRepository 民宿リポジトリ
     */
    public HouseController(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    /**
     * 民宿一覧ページを表示する
     *
     * @param keyword 検索キーワード（オプション）
     * @param area エリア（オプション）
     * @param price 最大宿泊料金（オプション）
     * @param order ソート順（オプション: "priceAsc" or "createdAtDesc"）
     * @param pageable ページング設定（デフォルト値: 1ページ10件、ID順昇順）
     * @param model ビューにデータを渡すためのモデル
     * @return "houses/index" ビューのパス
     */
    @GetMapping
    public String index(@RequestParam(name = "keyword", required = false) String keyword,
                        @RequestParam(name = "area", required = false) String area,
                        @RequestParam(name = "price", required = false) Integer price,
                        @RequestParam(name = "order", required = false) String order,
                        @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
                        Model model) {
        // ページングされた民宿データ
        Page<House> housePage;

        // 検索条件に応じてデータを取得
        if (keyword != null && !keyword.isEmpty()) {
            // キーワード検索
            if (order != null && order.equals("priceAsc")) {
                housePage = houseRepository.findByNameLikeOrAddressLikeOrderByPriceAsc("%" + keyword + "%", "%" + keyword + "%", pageable);
            } else {
                housePage = houseRepository.findByNameLikeOrAddressLikeOrderByCreatedAtDesc("%" + keyword + "%", "%" + keyword + "%", pageable);
            }
        } else if (area != null && !area.isEmpty()) {
            // エリア検索
            if (order != null && order.equals("priceAsc")) {
                housePage = houseRepository.findByAddressLikeOrderByPriceAsc("%" + area + "%", pageable);
            } else {
                housePage = houseRepository.findByAddressLikeOrderByCreatedAtDesc("%" + area + "%", pageable);
            }
        } else if (price != null) {
            // 最大宿泊料金での検索
            if (order != null && order.equals("priceAsc")) {
                housePage = houseRepository.findByPriceLessThanEqualOrderByPriceAsc(price, pageable);
            } else {
                housePage = houseRepository.findByPriceLessThanEqualOrderByCreatedAtDesc(price, pageable);
            }
        } else {
            // 全データを取得
            if (order != null && order.equals("priceAsc")) {
                housePage = houseRepository.findAllByOrderByPriceAsc(pageable);
            } else {
                housePage = houseRepository.findAllByOrderByCreatedAtDesc(pageable);
            }
        }

        // モデルにデータをセット
        model.addAttribute("housePage", housePage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("area", area);
        model.addAttribute("price", price);
        model.addAttribute("order", order);

        // ビューのパスを返す
        return "houses/index";
    }
    
    @GetMapping("/{id}")
    public String show(@PathVariable(name = "id") Integer id, Model model) {
        House house = houseRepository.getReferenceById(id);
        
        model.addAttribute("house", house);         
        
        return "houses/show";
    }    
    
}
