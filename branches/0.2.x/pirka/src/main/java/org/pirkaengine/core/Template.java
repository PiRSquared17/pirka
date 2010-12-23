package org.pirkaengine.core;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Templateインターフェイス.
 * 
 * @author shuji.w6e
 * @since 0.1.0
 */
public interface Template extends Serializable {

    /**
     * テンプレートの名前を取得する
     * @return テンプレート名
     */
    String getName();
    
    /**
     * タイムスタンプを取得する.
     * @return タイムスタンプ
     */
    long getTimeStamp();
    
    /**
     * タイムスタンプを設定する.
     * @param timeStamp タイムスタンプ
     */
    void setTimeStamp(long timeStamp);
    
    /**
     * テンプレートで使用される式をキーとして保持するMapオブジェクトを作成する．
     * <p>
     * このメソッドで生成されるMapはコントローラ層などで値を設定する雛形としての利用、
     * 管理ツールなどにおけるテンプレート内の動的項目の抽出などに使用されることを想定している。<br />
     * 各項目のvalueにはnullが設定され返す。
     * </p>
     * @return Mapオブジェクト
     */
    Map<String, Object> createViewModel();

    /**
     * キーを指定してMapオブジェクトを作成する.
     * @param arrayKey
     * @return Mapオブジェクト
     * @throws KeyNotContainsException キーが存在しない場合にthrowされる
     */
    Map<String, Object> createArrayItemModel(String arrayKey) throws KeyNotContainsException;

    
    /**
     * モデルに値を持たないレンダラーを生成する.
     * @return レンダラー
     */
    Renderer generate();

    /**
     * モデルを指定して、レンダラーを生成する
     * @param viewModel モデル
     * @return レンダラー
     */
    Renderer generate(Map<String, Object> viewModel);
    
    /**
     * PrkComponentを取得する
     * @return PrkComponentリスト
     */
    List<PrkComponent> getPrkComponents();
}
