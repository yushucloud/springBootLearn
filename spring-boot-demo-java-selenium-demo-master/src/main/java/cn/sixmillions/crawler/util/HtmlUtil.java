package cn.sixmillions.crawler.util;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;

/**
 * 自定义html工具了
 */
public class HtmlUtil {

    /**
     * html压缩
     * http://www.javacui.com/opensource/558.html
     */
    public static String compressor(String htmlStr) {
        HtmlCompressor compressor = new HtmlCompressor();
        // 如果false，则关闭所有压缩（默认值为true）
        compressor.setEnabled(true);
        // 如果false保留HTML注释（默认值为true）
        compressor.setRemoveComments(true);
        // 如果false保留多个空格字符（默认值为true）
        compressor.setRemoveMultiSpaces(true);
        // 删除iter标记空白字符
        compressor.setRemoveIntertagSpaces(true);
        // 删除不必要的标记属性引号
        compressor.setRemoveQuotes(true);
        // 简化现有doctype
        compressor.setSimpleDoctype(true);
        // 从script标签中移除可选属性
        compressor.setRemoveScriptAttributes(true);
        // 从style标签中移除可选属性
        compressor.setRemoveStyleAttributes(true);
        // 从link标签中移除可选属性
        compressor.setRemoveLinkAttributes(true);
        // 从form标签中移除可选属性
        compressor.setRemoveFormAttributes(true);
        // 从input标签中移除可选属性
        compressor.setRemoveInputAttributes(true);
        // 从布尔标签属性中移除值
        compressor.setSimpleBooleanAttributes(true);
        // 从内联事件处理程序中删除“javascript:”
        compressor.setRemoveJavaScriptProtocol(true);
        // 将“http://”替换为“//”内部标记属性
        compressor.setRemoveHttpProtocol(true);
        // 将“https://”替换为“//”内部标记属性
        compressor.setRemoveHttpsProtocol(true);
        // 保留原始换行符
        compressor.setPreserveLineBreaks(true);
        // 删除提供的标记周围的空格
        compressor.setRemoveSurroundingSpaces("br,p");
        // 压缩内联css
        compressor.setCompressCss(true);
        // 压缩内联js
        compressor.setCompressJavaScript(true);
        // Yahoo YUI压缩机的换行参数
        compressor.setYuiCssLineBreak(80);
        // 禁用Yahoo YUI压缩器的优化参数
        compressor.setYuiJsDisableOptimizations(true);
        // Yahoo YUI压缩机的换行参数
        compressor.setYuiJsLineBreak(-1);
        //--nomunge param for Yahoo YUI Compressor
        compressor.setYuiJsNoMunge(true);
        // 为Yahoo YUI Compressor保留半参数
        compressor.setYuiJsPreserveAllSemiColons(true);

        // 使用Google闭包编译器进行javascript压缩
        //compressor.setJavaScriptCompressor(new ClosureJavaScriptCompressor(CompilationLevel.SIMPLE_OPTIMIZATIONS));
        // 使用您自己的css压缩程序实现
        //compressor.setCssCompressor(new MyOwnCssCompressor());

        return compressor.compress(htmlStr);
    }
}
