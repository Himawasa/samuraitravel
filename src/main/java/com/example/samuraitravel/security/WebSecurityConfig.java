package com.example.samuraitravel.security;

// 必要なクラスをインポート
import org.springframework.context.annotation.Bean; // Bean定義用のアノテーション
import org.springframework.context.annotation.Configuration; // 設定クラスを示すアノテーション
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; // メソッド単位のセキュリティを有効化
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // HTTPセキュリティの設定
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; // Webセキュリティを有効化
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // パスワードのハッシュ化用
import org.springframework.security.crypto.password.PasswordEncoder; // パスワードエンコーダインターフェース
import org.springframework.security.web.SecurityFilterChain; // セキュリティフィルタチェーン

/**
 * WebSecurityConfigクラス
 * アプリケーション全体のセキュリティ設定を管理します。
 * - URLごとのアクセス制御
 * - ログイン/ログアウトの挙動設定
 * - パスワードのハッシュ化設定
 */
@Configuration // このクラスが設定クラスであることを示すアノテーション
@EnableWebSecurity // Spring Securityを有効化
@EnableMethodSecurity // メソッド単位のセキュリティ（@PreAuthorizeなど）を有効化
public class WebSecurityConfig {

    /**
     * SecurityFilterChainの定義
     * - アプリケーション全体のセキュリティポリシーを設定します。
     * - URLごとのアクセス制御、ログインページの設定、ログアウトの設定などを行います。
     *
     * @param http HttpSecurityオブジェクト（Spring Securityの設定を記述）
     * @return SecurityFilterChainオブジェクト
     * @throws Exception 設定に問題があった場合にスローされる例外
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // URLごとのアクセス制御を設定
            .authorizeHttpRequests((requests) -> requests
                // 静的リソース（CSS, JS, 画像など）はすべてのユーザーがアクセス可能
                .requestMatchers("/css/**", "/images/**", "/js/**", "/storage/**", "/").permitAll()
                // "/admin/**" のURLは管理者ロールを持つユーザーのみアクセス可能
                .requestMatchers("/admin/**").hasRole("ADMIN")
                // その他すべてのURLはログインが必要
                .anyRequest().authenticated()
            )
            // ログインの設定
            .formLogin((form) -> form
                .loginPage("/login")              // ログインページのURL
                .loginProcessingUrl("/login")     // ログインフォームの送信先URL
                .defaultSuccessUrl("/?loggedIn")  // ログイン成功時のリダイレクト先URL
                .failureUrl("/login?error")       // ログイン失敗時のリダイレクト先URL
                .permitAll()                      // ログインページへのアクセスをすべてのユーザーに許可
            )
            // ログアウトの設定
            .logout((logout) -> logout
                .logoutSuccessUrl("/?loggedOut")  // ログアウト成功時のリダイレクト先URL
                .permitAll()                      // ログアウトURLへのアクセスをすべてのユーザーに許可
            );

        // 設定を反映させたSecurityFilterChainオブジェクトを返す
        return http.build();
    }

    /**
     * パスワードエンコーダの定義
     * - ユーザーが登録したパスワードを安全にハッシュ化して保存するために使用します。
     * - BCryptPasswordEncoderは、安全性が高いハッシュアルゴリズムを使用します。
     *
     * @return PasswordEncoderオブジェクト
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCryptアルゴリズムを使用してパスワードをハッシュ化
    }
}
