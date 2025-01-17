package com.example.samuraitravel.service;

// 必要なクラスをインポート
import org.springframework.stereotype.Service; // サービスクラスであることを示すアノテーション
import org.springframework.transaction.annotation.Transactional; // トランザクション管理を行うアノテーション

import com.example.samuraitravel.entity.User; // ユーザーエンティティクラス
import com.example.samuraitravel.entity.VerificationToken; // トークンエンティティクラス
import com.example.samuraitravel.repository.VerificationTokenRepository; // トークンリポジトリクラス

/**
 * VerificationTokenServiceクラス
 * ユーザー認証に使用するトークンを操作するためのサービスクラスです。
 * トークンの生成や検索機能を提供します。
 */
@Service // サービス層のクラスであることを指定
public class VerificationTokenService {

    /**
     * VerificationTokenRepository
     * トークンデータを操作するためのリポジトリ
     */
    private final VerificationTokenRepository verificationTokenRepository;

    /**
     * コンストラクタ
     * VerificationTokenRepositoryを依存性注入（DI）で取得
     *
     * @param verificationTokenRepository トークンデータ操作用リポジトリ
     */
    public VerificationTokenService(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    /**
     * トークンを新規作成してデータベースに保存します。
     *
     * @param user トークンを関連付けるユーザーエンティティ
     * @param token トークン文字列
     */
    @Transactional // メソッド内の操作をトランザクションとして管理
    public void create(User user, String token) {
        // 新しいVerificationTokenエンティティを作成
        VerificationToken verificationToken = new VerificationToken();

        // ユーザーとトークンをセット
        verificationToken.setUser(user);
        verificationToken.setToken(token);

        // トークンデータをデータベースに保存
        verificationTokenRepository.save(verificationToken);
    }

    /**
     * トークン文字列を使用してVerificationTokenエンティティを取得します。
     *
     * @param token トークン文字列
     * @return 該当するVerificationTokenエンティティ
     */
    public VerificationToken getVerificationToken(String token) {
        // トークン文字列で検索し、結果を返す
        return verificationTokenRepository.findByToken(token);
    }
}
