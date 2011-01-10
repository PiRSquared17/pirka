package org.pirkaengine.core;

import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.Charset;

/**
 * レンダリングインターフェイス.
 * 
 * @author shuji.w6e
 * @since 0.1.0
 */
public interface Renderer {

    /**
     * テンプレートの評価結果を文字列として取得する.
     * 
     * @return テンプレートの評価文字列.
     * @throws RenderException レンダリングに失敗した場合
     */
    String render() throws RenderException;

    /**
     * テンプレートの評価結果を出力ストリームに書き出す.
     * @param output 出力ストリーム
     * @throws RenderException レンダリングに失敗した場合
     */
    void render(OutputStream output) throws RenderException;
    
    /**
     * 文字コードを指定して、テンプレートの評価結果を出力ストリームに書き出す.
     * @param output 出力ストリーム
     * @param charset 文字コード
     * @throws RenderException レンダリングに失敗した場合
     * @since 0.2.0
     */
    void render(OutputStream output, String charset) throws RenderException;
    
    /**
     * 文字セットを指定して、テンプレートの評価結果を出力ストリームに書き出す.
     * @param output 出力ストリーム
     * @param charset 文字セット
     * @throws RenderException レンダリングに失敗した場合
     * @since 0.3.0
     */
    void render(OutputStream output, Charset charset) throws RenderException;

    /**
     * テンプレートの評価結果をwriterに書き出す.
     * 
     * @param writer writer
     * @throws RenderException レンダリングに失敗した場合
     */
    void render(Writer writer) throws RenderException;
}
