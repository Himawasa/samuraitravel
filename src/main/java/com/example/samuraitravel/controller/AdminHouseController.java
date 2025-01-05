package com.example.samuraitravel.controller;

// 必要なクラスをインポート
import org.springframework.data.domain.Page; // ページネーション用クラス
import org.springframework.data.domain.Pageable; // ページネーション設定クラス
import org.springframework.data.domain.Sort.Direction; // ソートの方向（昇順・降順）を指定
import org.springframework.data.web.PageableDefault; // ページネーションのデフォルト設定
import org.springframework.stereotype.Controller; // コントローラーとして認識させるアノテーション
import org.springframework.ui.Model; // ビューにデータを渡すためのオブジェクト
import org.springframework.validation.BindingResult; // バリデーション結果を保持するクラス
import org.springframework.validation.annotation.Validated; // フォームバリデーションを有効化
import org.springframework.web.bind.annotation.GetMapping; // HTTP GETリクエストを処理するアノテーション
import org.springframework.web.bind.annotation.ModelAttribute; // モデル属性としてデータを渡すアノテーション
import org.springframework.web.bind.annotation.PathVariable; // URLのパスパラメータを取得するアノテーション
import org.springframework.web.bind.annotation.PostMapping; // HTTP POSTリクエストを処理するアノテーション
import org.springframework.web.bind.annotation.RequestMapping; // URLマッピングを設定
import org.springframework.web.bind.annotation.RequestParam; // クエリパラメータを取得するアノテーション
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // リダイレクト時に一時的なデータを渡すクラス

// 独自のクラスをインポート
import com.example.samuraitravel.entity.House; // 民宿データのエンティティクラス
import com.example.samuraitravel.form.HouseEditForm; // 民宿編集フォームクラス
import com.example.samuraitravel.form.HouseRegisterForm; // 民宿登録フォームクラス
import com.example.samuraitravel.repository.HouseRepository; // 民宿情報を操作するリポジトリ
import com.example.samuraitravel.service.HouseService; // 民宿に関するビジネスロジックを持つサービス

/**
 * 民宿管理用コントローラー
 * URL: /admin/houses
 * このクラスでは民宿の一覧表示、詳細表示、新規登録、編集を行います。
 */
@Controller
@RequestMapping("/admin/houses")
public class AdminHouseController {

    // 民宿データ操作用リポジトリ
    private final HouseRepository houseRepository;

    // 民宿に関するビジネスロジックを処理するサービス
    private final HouseService houseService;

    /**
     * コンストラクタ
     * HouseRepositoryとHouseServiceを依存性注入で取得します。
     *
     * @param houseRepository 民宿データ操作用リポジトリ
     * @param houseService 民宿に関するビジネスロジックを処理するサービス
     */
    public AdminHouseController(HouseRepository houseRepository, HouseService houseService) {
        this.houseRepository = houseRepository;
        this.houseService = houseService;
    }

    /**
     * 民宿一覧ページの表示
     *
     * @param model ビューにデータを渡すためのオブジェクト
     * @param pageable ページネーション設定（デフォルトで1ページ10件、ID昇順）
     * @param keyword 検索キーワード（任意）
     * @return 民宿一覧ページのテンプレート名
     */
    @GetMapping
    public String index(Model model, 
                        @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, 
                        @RequestParam(name = "keyword", required = false) String keyword) {
        // 民宿データをページ単位で取得（検索キーワードに応じた処理）
        Page<House> housePage;
        if (keyword != null && !keyword.isEmpty()) {
            // 検索キーワードが指定されている場合、部分一致検索
            housePage = houseRepository.findByNameLike("%" + keyword + "%", pageable);
        } else {
            // 検索キーワードがない場合、全件取得
            housePage = houseRepository.findAll(pageable);
        }

        // モデルにデータを追加してビューに渡す
        model.addAttribute("housePage", housePage);
        model.addAttribute("keyword", keyword);

        // 民宿一覧ページのテンプレートを返す
        return "admin/houses/index";
    }

    /**
     * 民宿の詳細ページの表示
     *
     * @param id 民宿ID（URLパスから取得）
     * @param model ビューにデータを渡すためのオブジェクト
     * @return 民宿詳細ページのテンプレート名
     */
    @GetMapping("/{id}")
    public String show(@PathVariable(name = "id") Integer id, Model model) {
        // 民宿IDに基づいてデータを取得（遅延ロード）
        House house = houseRepository.getReferenceById(id);

        // モデルに取得したデータを追加
        model.addAttribute("house", house);

        // 民宿詳細ページのテンプレートを返す
        return "admin/houses/show";
    }

    /**
     * 民宿の登録ページの表示
     *
     * @param model ビューにデータを渡すためのオブジェクト
     * @return 民宿登録ページのテンプレート名
     */
    @GetMapping("/register")
    public String register(Model model) {
        // 空のフォームデータをモデルに追加
        model.addAttribute("houseRegisterForm", new HouseRegisterForm());
        return "admin/houses/register";
    }

    /**
     * 民宿の新規登録処理
     *
     * @param houseRegisterForm ユーザーから送信された登録フォームデータ
     * @param bindingResult バリデーションの結果
     * @param redirectAttributes リダイレクト時にメッセージを渡すオブジェクト
     * @return 登録成功時は民宿一覧ページにリダイレクト。エラー時は登録ページを再表示。
     */
    @PostMapping("/create")
    public String create(@ModelAttribute @Validated HouseRegisterForm houseRegisterForm, 
                         BindingResult bindingResult, 
                         RedirectAttributes redirectAttributes) {
        // フォームデータのバリデーション
        if (bindingResult.hasErrors()) {
            // バリデーションエラーがある場合、登録ページを再表示
            return "admin/houses/register";
        }

        // フォームデータを使って民宿を登録
        houseService.create(houseRegisterForm);

        // リダイレクト先で成功メッセージを表示
        redirectAttributes.addFlashAttribute("successMessage", "民宿を登録しました。");

        // 民宿一覧ページにリダイレクト
        return "redirect:/admin/houses";
    }

    /**
     * 民宿の編集ページの表示
     *
     * @param id 民宿ID（URLパスから取得）
     * @param model ビューにデータを渡すためのオブジェクト
     * @return 民宿編集ページのテンプレート名
     */
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable(name = "id") Integer id, Model model) {
        // 民宿IDに基づいてデータを取得
        House house = houseRepository.getReferenceById(id);

        // 編集フォーム用のデータを作成
        String imageName = house.getImageName();
        HouseEditForm houseEditForm = new HouseEditForm(
                house.getId(),
                house.getName(),
                null,
                house.getDescription(),
                house.getPrice(),
                house.getCapacity(),
                house.getPostalCode(),
                house.getAddress(),
                house.getPhoneNumber()
        );

        // モデルにデータを追加
        model.addAttribute("imageName", imageName);
        model.addAttribute("houseEditForm", houseEditForm);

        // 民宿編集ページのテンプレートを返す
        return "admin/houses/edit";
    }

    /**
     * 民宿の編集処理
     *
     * @param houseEditForm ユーザーから送信された編集フォームデータ
     * @param bindingResult バリデーションの結果
     * @param redirectAttributes リダイレクト時にメッセージを渡すオブジェクト
     * @return 編集成功時は民宿一覧ページにリダイレクト。エラー時は編集ページを再表示。
     */
    @PostMapping("/{id}/update")
    public String update(@ModelAttribute @Validated HouseEditForm houseEditForm, 
                         BindingResult bindingResult, 
                         RedirectAttributes redirectAttributes) {
        // フォームデータのバリデーション
        if (bindingResult.hasErrors()) {
            // バリデーションエラーがある場合、編集ページを再表示
            return "admin/houses/edit";
        }

        // フォームデータを使って民宿を更新
        houseService.update(houseEditForm);

        // リダイレクト先で成功メッセージを表示
        redirectAttributes.addFlashAttribute("successMessage", "民宿情報を編集しました。");

        // 民宿一覧ページにリダイレクト
        return "redirect:/admin/houses";
    }
    
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {        
        houseRepository.deleteById(id);
                
        redirectAttributes.addFlashAttribute("successMessage", "民宿を削除しました。");
        
        return "redirect:/admin/houses";
    }    
    
    
}
