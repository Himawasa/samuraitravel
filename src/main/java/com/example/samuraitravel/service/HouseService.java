package com.example.samuraitravel.service;

import java.io.IOException; // 入出力エラー処理用
import java.nio.file.Files; // ファイル操作用クラス
import java.nio.file.Path; // ファイルのパスを表すクラス
import java.nio.file.Paths; // ファイルパスを操作するユーティリティクラス
import java.util.UUID; // 一意の識別子を生成するクラス

import org.springframework.stereotype.Service; // サービスクラスを示すアノテーション
import org.springframework.transaction.annotation.Transactional; // トランザクション管理用アノテーション
import org.springframework.web.multipart.MultipartFile; // ファイルアップロード用クラス

import com.example.samuraitravel.entity.House; // 民宿エンティティクラス
import com.example.samuraitravel.form.HouseEditForm; // 民宿編集フォームクラス
import com.example.samuraitravel.form.HouseRegisterForm; // 民宿登録フォームクラス
import com.example.samuraitravel.repository.HouseRepository; // 民宿データ操作用リポジトリ

/**
 * HouseServiceクラス
 * 民宿に関するビジネスロジックを処理するサービスクラス。
 * Springの@Serviceアノテーションにより、DIコンテナで管理されるBeanとして登録されます。
 */
@Service
public class HouseService {

    // 民宿情報を操作するリポジトリ
    private final HouseRepository houseRepository;

    /**
     * コンストラクタ
     * HouseRepositoryを依存性注入（DI）で取得
     *
     * @param houseRepository 民宿情報を操作するリポジトリ
     */
    public HouseService(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    /**
     * 民宿の新規作成処理
     * 入力されたフォームデータをもとに民宿データを登録します。
     *
     * @param houseRegisterForm フォームデータ（ユーザー入力）
     */
    @Transactional // トランザクション管理を指定
    public void create(HouseRegisterForm houseRegisterForm) {
        // 新しいHouseエンティティを作成
        House house = new House();

        // 画像ファイルの取得
        MultipartFile imageFile = houseRegisterForm.getImageFile();

        // 画像ファイルが存在する場合の処理
        if (!imageFile.isEmpty()) {
            // 元のファイル名を取得
            String imageName = imageFile.getOriginalFilename();

            // 新しいファイル名を生成（UUIDを使用してユニーク化）
            String hashedImageName = generateNewFileName(imageName);

            // 保存先のパスを作成
            Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);

            // 画像ファイルを指定した場所にコピー
            copyImageFile(imageFile, filePath);

            // 画像名をエンティティにセット
            house.setImageName(hashedImageName);
        }

        // フォームデータをエンティティにマッピング
        house.setName(houseRegisterForm.getName());
        house.setDescription(houseRegisterForm.getDescription());
        house.setPrice(houseRegisterForm.getPrice());
        house.setCapacity(houseRegisterForm.getCapacity());
        house.setPostalCode(houseRegisterForm.getPostalCode());
        house.setAddress(houseRegisterForm.getAddress());
        house.setPhoneNumber(houseRegisterForm.getPhoneNumber());

        // エンティティをデータベースに保存
        houseRepository.save(house);
    }

    /**
     * 民宿の更新処理
     * 入力されたフォームデータをもとに民宿データを更新します。
     *
     * @param houseEditForm フォームデータ（ユーザー入力）
     */
    @Transactional // トランザクション管理を指定
    public void update(HouseEditForm houseEditForm) {
        // 更新対象の民宿データを取得
        House house = houseRepository.getReferenceById(houseEditForm.getId());

        // 画像ファイルの取得
        MultipartFile imageFile = houseEditForm.getImageFile();

        // 画像ファイルが存在する場合の処理
        if (!imageFile.isEmpty()) {
            // 元のファイル名を取得
            String imageName = imageFile.getOriginalFilename();

            // 新しいファイル名を生成（UUIDを使用してユニーク化）
            String hashedImageName = generateNewFileName(imageName);

            // 保存先のパスを作成
            Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);

            // 画像ファイルを指定した場所にコピー
            copyImageFile(imageFile, filePath);

            // 画像名をエンティティにセット
            house.setImageName(hashedImageName);
        }

        // フォームデータをエンティティにマッピング
        house.setName(houseEditForm.getName());
        house.setDescription(houseEditForm.getDescription());
        house.setPrice(houseEditForm.getPrice());
        house.setCapacity(houseEditForm.getCapacity());
        house.setPostalCode(houseEditForm.getPostalCode());
        house.setAddress(houseEditForm.getAddress());
        house.setPhoneNumber(houseEditForm.getPhoneNumber());

        // エンティティをデータベースに保存
        houseRepository.save(house);
    }

    /**
     * 新しいファイル名を生成
     * 元のファイル名を基にUUIDでユニークな名前に変換します。
     *
     * @param fileName 元のファイル名
     * @return UUIDを使用して生成されたユニークなファイル名
     */
    public String generateNewFileName(String fileName) {
        // ファイル名を"."で分割
        String[] fileNames = fileName.split("\\.");
        for (int i = 0; i < fileNames.length - 1; i++) {
            // 拡張子以外の部分をUUIDに置き換え
            fileNames[i] = UUID.randomUUID().toString();
        }
        // ファイル名を"."で結合して新しいファイル名を返す
        String hashedFileName = String.join(".", fileNames);
        return hashedFileName;
    }

    /**
     * 画像ファイルを指定したパスにコピー
     * ユーザーがアップロードした画像ファイルを指定の場所に保存します。
     *
     * @param imageFile ユーザーがアップロードした画像ファイル
     * @param filePath 保存先のパス
     */
    public void copyImageFile(MultipartFile imageFile, Path filePath) {
        try {
            // 入力ストリームから指定したパスにファイルをコピー
            Files.copy(imageFile.getInputStream(), filePath);
        } catch (IOException e) {
            // エラーが発生した場合の処理
            e.printStackTrace();
        }
    }
}
