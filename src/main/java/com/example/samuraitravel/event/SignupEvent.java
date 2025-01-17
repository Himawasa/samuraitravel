package com.example.samuraitravel.event;

// 必要なクラスをインポート
import org.springframework.context.ApplicationEvent; // Springのイベント機能を利用するためのクラス

import com.example.samuraitravel.entity.User; // Userエンティティを利用するためのインポート

import lombok.Getter; // Lombokの@Getterアノテーションでゲッターを自動生成

/**
 * SignupEventクラス
 * 
 * ユーザーのサインアップ時に発生するカスタムイベントクラス。
 * SpringのApplicationEventを拡張しており、イベントリスナーがこのイベントを受け取って処理を行います。
 */
@Getter // Lombokを使用して、`getUser` と `getRequestUrl` のゲッターメソッドを自動生成
public class SignupEvent extends ApplicationEvent { // SpringのApplicationEventを継承
    /**
     * ユーザー情報を保持するフィールド
     * サインアップしたユーザーを格納します。
     */
    private User user;

    /**
     * リクエストURLを保持するフィールド
     * サインアップの処理を行う際のリクエストURLを格納します。
     */
    private String requestUrl;

    /**
     * コンストラクタ
     * 
     * サインアップイベントを作成する際に必要なデータ（ソースオブジェクト、ユーザー情報、リクエストURL）を受け取ります。
     *
     * @param source イベントのソースオブジェクト（通常は発生元のオブジェクト）
     * @param user サインアップしたユーザーの情報
     * @param requestUrl 処理中のリクエストURL
     */
    public SignupEvent(Object source, User user, String requestUrl) {
        super(source); // 親クラスのコンストラクタを呼び出し、ソースオブジェクトを初期化
        
        this.user = user; // ユーザー情報をセット
        this.requestUrl = requestUrl; // リクエストURLをセット
    }
}
