package com.example.samuraitravel.event; // パッケージ名を指定

// 必要なクラスをインポート
import org.springframework.context.ApplicationEventPublisher; // アプリケーションイベントを発行するためのクラス
import org.springframework.stereotype.Component; // Springコンポーネントとして登録

import com.example.samuraitravel.entity.User; // ユーザー情報を扱うエンティティクラス

/**
 * SignupEventPublisherクラス
 * サインアップイベントを発行するためのクラス。
 * ユーザー登録後にイベントを発行し、リスナーがそれを処理できるようにする。
 */
@Component // Springコンポーネントとして登録
public class SignupEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher; // イベントを発行するためのクラス

    /**
     * コンストラクタ
     * ApplicationEventPublisherを初期化する。
     *
     * @param applicationEventPublisher イベント発行クラス
     */
    public SignupEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;                
    }

    /**
     * サインアップイベントを発行するメソッド
     * ユーザー情報とリクエストURLを基にSignupEventを発行する。
     *
     * @param user サインアップしたユーザー
     * @param requestUrl リクエストのベースURL
     */
    public void publishSignupEvent(User user, String requestUrl) {
        // SignupEventを作成し、イベントを発行
        applicationEventPublisher.publishEvent(new SignupEvent(this, user, requestUrl));
    }
}
