package com.example.samuraitravel.security;

// 必要なクラスをインポート
import java.util.ArrayList; // 権限リストを管理するためのコレクション
import java.util.Collection; // 権限のコレクションを扱うためのクラス

import org.springframework.security.core.GrantedAuthority; // ユーザーの権限を表すインターフェース
import org.springframework.security.core.authority.SimpleGrantedAuthority; // 単一の権限を表すクラス
import org.springframework.security.core.userdetails.UserDetails; // Spring Securityのユーザー情報を表すインターフェース
import org.springframework.security.core.userdetails.UserDetailsService; // Spring Securityのユーザー詳細サービスインターフェース
import org.springframework.security.core.userdetails.UsernameNotFoundException; // ユーザーが見つからない場合の例外
import org.springframework.stereotype.Service; // サービスクラスを示すアノテーション

import com.example.samuraitravel.entity.User; // ユーザーエンティティ
import com.example.samuraitravel.repository.UserRepository; // ユーザー情報を操作するリポジトリ

/**
 * UserDetailsServiceImplクラス
 * Spring Securityの認証処理で使用するユーザー情報を取得するサービスクラス。
 * UserDetailsServiceインターフェースを実装しています。
 */
@Service // このクラスをサービスとして登録し、DIコンテナに管理させる
public class UserDetailsServiceImpl implements UserDetailsService {

    // ユーザー情報をデータベースから取得するためのリポジトリ
    private final UserRepository userRepository;    

    /**
     * コンストラクタ
     * UserRepositoryを依存性注入（DI）で受け取ります。
     *
     * @param userRepository ユーザー情報を操作するリポジトリ
     */
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;        
    }

    /**
     * ユーザー名（ここではメールアドレス）を基にユーザー情報をロードします。
     *
     * @param email ユーザーのメールアドレス（ログインIDとして使用）
     * @return UserDetails ユーザー情報（Spring Securityの認証に使用される形式）
     * @throws UsernameNotFoundException ユーザーが見つからない場合にスローされる例外
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {  
        try {
            // メールアドレスでユーザー情報を検索
            User user = userRepository.findByEmail(email);
            
            // ユーザーのロール名を取得
            String userRoleName = user.getRole().getName();
            
            // ユーザー権限（GrantedAuthority）のリストを作成
            Collection<GrantedAuthority> authorities = new ArrayList<>();         
            authorities.add(new SimpleGrantedAuthority(userRoleName));
            
            // UserDetailsインターフェースを実装したクラスのインスタンスを返す
            return new UserDetailsImpl(user, authorities);
        } catch (Exception e) {
            // ユーザーが見つからない場合の例外をスロー
        	e.printStackTrace();
            throw new UsernameNotFoundException("ユーザーが見つかりませんでした。");
        }
    }   
}
