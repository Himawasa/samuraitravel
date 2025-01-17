package com.example.samuraitravel.event; // パッケージ名を指定

// 必要なクラスをインポート
import java.util.UUID; // ユニークなトークン生成に使用

import org.springframework.context.event.EventListener; // イベントリスナーとして動作するためのアノテーション
import org.springframework.mail.SimpleMailMessage; // シンプルなメールメッセージを作成するためのクラス
import org.springframework.mail.javamail.JavaMailSender; // メール送信に使用するクラス
import org.springframework.stereotype.Component; // Springコンポーネントとして登録

import com.example.samuraitravel.entity.User; // ユーザー情報を扱うエンティティクラス
import com.example.samuraitravel.service.VerificationTokenService; // トークンサービスを利用するためのクラス

/**
 * SignupEventListenerクラス
 * サインアップイベントをリッスンし、認証トークンの作成および認証メールの送信を行うクラス。
 */
@Component // Springコンポーネントとして登録
public class SignupEventListener {

    private final VerificationTokenService verificationTokenService; // 認証トークンを管理するサービス
    private final JavaMailSender javaMailSender; // メール送信に使用するクラス

    /**
     * コンストラクタ
     * 必要なサービスとメール送信クラスを初期化する。
     *
     * @param verificationTokenService トークンサービス
     * @param mailSender メール送信クラス
     */
    public SignupEventListener(VerificationTokenService verificationTokenService, JavaMailSender mailSender) {
        this.verificationTokenService = verificationTokenService;        
        this.javaMailSender = mailSender;
    }

    /**
     * サインアップイベント発生時に呼び出されるメソッド
     * ユーザーに認証メールを送信し、認証トークンを生成・保存する。
     *
     * @param signupEvent サインアップイベント
     */
    @EventListener // サインアップイベントをリッスン
    private void onSignupEvent(SignupEvent signupEvent) {
        // ユーザー情報を取得
        User user = signupEvent.getUser();

        // ランダムなトークンを生成
        String token = UUID.randomUUID().toString();

        // 認証トークンをデータベースに保存
        verificationTokenService.create(user, token);

        // ユーザーのメールアドレスを取得
        String recipientAddress = user.getEmail();

        // メールの件名を設定
        String subject = "メール認証";

        // 認証リンクを作成
        String confirmationUrl = signupEvent.getRequestUrl() + "/verify?token=" + token;

        // メールの本文を作成
        String message = "以下のリンクをクリックして会員登録を完了してください。";

        // メールメッセージを構築
        SimpleMailMessage mailMessage = new SimpleMailMessage(); 
        mailMessage.setTo(recipientAddress); // 宛先を設定
        mailMessage.setSubject(subject); // 件名を設定
        mailMessage.setText(message + "\n" + confirmationUrl); // 本文にメッセージと認証リンクを設定

        // メールを送信
        javaMailSender.send(mailMessage);
    }
}
