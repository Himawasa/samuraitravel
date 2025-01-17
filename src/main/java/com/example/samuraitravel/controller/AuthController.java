package com.example.samuraitravel.controller;

import jakarta.servlet.http.HttpServletRequest; // HTTPリクエスト情報を取得するためのクラス

// 必要なクラスをインポート
import org.springframework.stereotype.Controller; // コントローラーとして動作するクラスであることを指定
import org.springframework.ui.Model; // ビューにデータを渡すためのクラス
import org.springframework.validation.BindingResult; // バリデーション結果を保持するクラス
import org.springframework.validation.FieldError; // フィールドごとのエラーを表現するクラス
import org.springframework.validation.annotation.Validated; // バリデーションを有効化するアノテーション
import org.springframework.web.bind.annotation.GetMapping; // GETリクエストを処理するためのアノテーション
import org.springframework.web.bind.annotation.ModelAttribute; // モデル属性を処理するためのアノテーション
import org.springframework.web.bind.annotation.PostMapping; // POSTリクエストを処理するためのアノテーション
import org.springframework.web.bind.annotation.RequestParam; // クエリパラメータを取得するためのアノテーション
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // リダイレクト時にデータを渡すためのクラス

import com.example.samuraitravel.entity.User; // ユーザー情報を表すエンティティ
import com.example.samuraitravel.entity.VerificationToken; // 認証トークンを表すエンティティ
import com.example.samuraitravel.event.SignupEventPublisher; // サインアップイベントを発行するクラス
import com.example.samuraitravel.form.SignupForm; // サインアップ用のフォームクラス
import com.example.samuraitravel.service.UserService; // ユーザー関連のサービスクラス
import com.example.samuraitravel.service.VerificationTokenService; // 認証トークン関連のサービスクラス

/**
 * 認証関連のコントローラークラス
 * ユーザーのログイン、サインアップ、メール認証に関連するリクエストを処理します。
 */
@Controller
public class AuthController {
    private final UserService userService; // ユーザー関連の処理を行うサービス
    private final SignupEventPublisher signupEventPublisher; // サインアップイベントを発行するクラス
    private final VerificationTokenService verificationTokenService; // 認証トークン関連のサービス

    /**
     * コンストラクタ
     * DIコンテナがサービスクラスとイベントパブリッシャーを注入します。
     *
     * @param userService ユーザー関連のサービスクラス
     * @param signupEventPublisher サインアップイベントを発行するクラス
     * @param verificationTokenService 認証トークン関連のサービスクラス
     */
    public AuthController(UserService userService, SignupEventPublisher signupEventPublisher, VerificationTokenService verificationTokenService) {
        this.userService = userService;
        this.signupEventPublisher = signupEventPublisher;
        this.verificationTokenService = verificationTokenService;
    }

    /**
     * ログイン画面を表示するためのメソッド
     * @return ログイン画面のテンプレート名
     */
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    /**
     * サインアップ画面を表示するためのメソッド
     * @param model ビューにデータを渡すためのモデル
     * @return サインアップ画面のテンプレート名
     */
    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("signupForm", new SignupForm());
        return "auth/signup";
    }

    /**
     * サインアップ処理を行うためのメソッド
     * @param signupForm サインアップフォームのデータ
     * @param bindingResult バリデーション結果を保持するオブジェクト
     * @param redirectAttributes リダイレクト時にデータを渡すためのオブジェクト
     * @param httpServletRequest HTTPリクエスト情報を取得するためのオブジェクト
     * @return 処理結果に応じたリダイレクト先
     */
    @PostMapping("/signup")
    public String signup(@ModelAttribute @Validated SignupForm signupForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
        // メールアドレスが既に登録されている場合、エラーを追加
        if (userService.isEmailRegistered(signupForm.getEmail())) {
            FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "すでに登録済みのメールアドレスです。");
            bindingResult.addError(fieldError);
        }

        // パスワードと確認用パスワードが一致しない場合、エラーを追加
        if (!userService.isSamePassword(signupForm.getPassword(), signupForm.getPasswordConfirmation())) {
            FieldError fieldError = new FieldError(bindingResult.getObjectName(), "password", "パスワードが一致しません。");
            bindingResult.addError(fieldError);
        }

        // バリデーションエラーが存在する場合、サインアップ画面を再表示
        if (bindingResult.hasErrors()) {
            return "auth/signup";
        }

        // ユーザー情報を登録
        User createdUser = userService.create(signupForm);

        // 現在のリクエストURLを取得
        String requestUrl = new String(httpServletRequest.getRequestURL());

        // サインアップイベントを発行
        signupEventPublisher.publishSignupEvent(createdUser, requestUrl);

        // 成功メッセージをリダイレクト属性に追加
        redirectAttributes.addFlashAttribute("successMessage", "ご入力いただいたメールアドレスに認証メールを送信しました。メールに記載されているリンクをクリックし、会員登録を完了してください。");

        // ホーム画面にリダイレクト
        return "redirect:/";
    }

    /**
     * メール認証を処理するためのメソッド
     * @param token 認証トークン
     * @param model ビューにデータを渡すためのモデル
     * @return 認証結果画面のテンプレート名
     */
    @GetMapping("/signup/verify")
    public String verify(@RequestParam(name = "token") String token, Model model) {
        // トークンでユーザーを検索
        VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);

        if (verificationToken != null) {
            // トークンが有効な場合、ユーザーを有効化
            User user = verificationToken.getUser();
            userService.enableUser(user);
            String successMessage = "会員登録が完了しました。";
            model.addAttribute("successMessage", successMessage);
        } else {
            // トークンが無効な場合、エラーメッセージを設定
            String errorMessage = "トークンが無効です。";
            model.addAttribute("errorMessage", errorMessage);
        }

        // 認証結果画面を表示
        return "auth/verify";
    }
}
