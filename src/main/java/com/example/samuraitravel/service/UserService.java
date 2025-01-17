package com.example.samuraitravel.service;

// 必要なクラスをインポート
import org.springframework.security.crypto.password.PasswordEncoder; // パスワードをハッシュ化するためのクラス
import org.springframework.stereotype.Service; // サービスクラスを示すアノテーション
import org.springframework.transaction.annotation.Transactional; // トランザクション管理をするためのアノテーション

import com.example.samuraitravel.entity.Role; // 役割情報を表すエンティティ
import com.example.samuraitravel.entity.User; // ユーザー情報を表すエンティティ
import com.example.samuraitravel.form.SignupForm; // サインアップフォームデータを扱うクラス
import com.example.samuraitravel.form.UserEditForm;
import com.example.samuraitravel.repository.RoleRepository; // Roleエンティティを操作するリポジトリ
import com.example.samuraitravel.repository.UserRepository; // Userエンティティを操作するリポジトリ

/**
 * UserServiceクラス
 * ユーザー関連のビジネスロジックを処理するサービスクラス。
 */
@Service
public class UserService {

    // ユーザー情報を操作するリポジトリ
    private final UserRepository userRepository;

    // ロール情報を操作するリポジトリ
    private final RoleRepository roleRepository;

    // パスワードのハッシュ化を担当するエンコーダ
    private final PasswordEncoder passwordEncoder;

    /**
     * コンストラクタ
     * UserRepository、RoleRepository、PasswordEncoderを依存性注入で取得します。
     *
     * @param userRepository ユーザー情報を操作するリポジトリ
     * @param roleRepository ロール情報を操作するリポジトリ
     * @param passwordEncoder パスワードをハッシュ化するエンコーダ
     */
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 新規ユーザーの登録処理
     *
     * @param signupForm サインアップフォームデータ
     * @return 登録されたUserエンティティ
     */
    @Transactional // トランザクション管理を指定（途中でエラーが発生した場合はロールバック）
    public User create(SignupForm signupForm) {
        // 新しいユーザーエンティティを作成
        User user = new User();

        // 一般ユーザー用のロールを取得
        Role role = roleRepository.findByName("ROLE_GENERAL");

        // フォームデータをUserエンティティにマッピング
        user.setName(signupForm.getName());
        user.setFurigana(signupForm.getFurigana());
        user.setPostalCode(signupForm.getPostalCode());
        user.setAddress(signupForm.getAddress());
        user.setPhoneNumber(signupForm.getPhoneNumber());
        user.setEmail(signupForm.getEmail());

        // パスワードをハッシュ化してセット
        user.setPassword(passwordEncoder.encode(signupForm.getPassword()));

        // ユーザーにロールをセット
        user.setRole(role);

        // ユーザーを有効化
        user.setEnabled(false);

        // ユーザーをデータベースに保存
        return userRepository.save(user);
    }
    
    @Transactional
    public void update(UserEditForm userEditForm) {
        User user = userRepository.getReferenceById(userEditForm.getId());
        
        user.setName(userEditForm.getName());
        user.setFurigana(userEditForm.getFurigana());
        user.setPostalCode(userEditForm.getPostalCode());
        user.setAddress(userEditForm.getAddress());
        user.setPhoneNumber(userEditForm.getPhoneNumber());
        user.setEmail(userEditForm.getEmail());      
        
        userRepository.save(user);
    }    
    
    
    /**
     * メールアドレスが既に登録されているかをチェック
     *
     * @param email チェックするメールアドレス
     * @return 登録済みであればtrue、未登録であればfalse
     */
    public boolean isEmailRegistered(String email) {
        // メールアドレスに一致するユーザーを検索
        User user = userRepository.findByEmail(email);

        // ユーザーが存在すればtrueを返す
        return user != null;
    }

    /**
     * パスワードとパスワード確認用の値が一致しているかをチェック
     *
     * @param password 入力されたパスワード
     * @param passwordConfirmation 確認用パスワード
     * @return 一致していればtrue、そうでなければfalse
     */
    public boolean isSamePassword(String password, String passwordConfirmation) {
        // 2つのパスワードが同じかを比較
        return password.equals(passwordConfirmation);
    }
    
    // ユーザーを有効にする
    @Transactional
    public void enableUser(User user) {
        user.setEnabled(true); 
        userRepository.save(user);
     }
    
    // メールアドレスが変更されたかどうかをチェックする
    public boolean isEmailChanged(UserEditForm userEditForm) {
        User currentUser = userRepository.getReferenceById(userEditForm.getId());
        return !userEditForm.getEmail().equals(currentUser.getEmail());      
    }  
    
    
}
