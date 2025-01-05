package com.example.samuraitravel.security;

// 必要なクラスをインポート
import java.util.Collection; // 権限のコレクションを扱うためのクラス

import org.springframework.security.core.GrantedAuthority; // ユーザーの権限を表すインターフェース
import org.springframework.security.core.userdetails.UserDetails; // Spring Securityのユーザー情報を扱うインターフェース

import com.example.samuraitravel.entity.User; // アプリケーションのユーザーデータを表すエンティティ

/**
 * UserDetailsImplクラス
 * Spring Securityで認証に使用するためのユーザー情報を管理するクラス。
 * Userエンティティを基に、Spring Securityが必要とする形式に変換します。
 */
public class UserDetailsImpl implements UserDetails {

    // ユーザーエンティティ（アプリケーション固有のユーザー情報を保持）
    private final User user;

    // ユーザーの権限（ロール）を保持するコレクション
    private final Collection<GrantedAuthority> authorities;

    /**
     * コンストラクタ
     * ユーザー情報と権限情報を初期化します。
     *
     * @param user ユーザーエンティティ
     * @param authorities ユーザーの権限（ロール）のコレクション
     */
    public UserDetailsImpl(User user, Collection<GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    /**
     * アプリケーションのUserエンティティを取得します。
     *
     * @return Userエンティティ
     */
    public User getUser() {
        return user;
    }

    /**
     * ユーザーのハッシュ化済みパスワードを返します。
     * Spring Securityが認証時に利用します。
     *
     * @return ユーザーのハッシュ化済みパスワード
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * ログイン時に利用するユーザー名（この場合はメールアドレス）を返します。
     * Spring Securityが認証時に利用します。
     *
     * @return ユーザーのメールアドレス
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * ユーザーの権限（ロール）のコレクションを返します。
     * 権限はSpring Securityがアクセス制御に使用します。
     *
     * @return ユーザーの権限（ロール）のコレクション
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * アカウントが期限切れでない場合にtrueを返します。
     * 常にtrueを返すように設定しています（カスタマイズ可能）。
     *
     * @return アカウントが期限切れでない場合はtrue
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * アカウントがロックされていない場合にtrueを返します。
     * 常にtrueを返すように設定しています（カスタマイズ可能）。
     *
     * @return アカウントがロックされていない場合はtrue
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * パスワードの有効期限が切れていない場合にtrueを返します。
     * 常にtrueを返すように設定しています（カスタマイズ可能）。
     *
     * @return パスワードの有効期限が切れていない場合はtrue
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * アカウントが有効である場合にtrueを返します。
     * Userエンティティの`enabled`フィールドを基に判断します。
     *
     * @return アカウントが有効である場合はtrue
     */
    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }
}
