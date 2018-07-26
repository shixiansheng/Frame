package cn.abr.inabr.utils

import android.text.format.DateUtils
import android.webkit.WebView

import cn.abr.inabr.entity.ArticleContentEntity


/**
 * 设置文章数据
 * WebView
 *
 * @author Administrator
 */
object ContactPlugin {

    private var mWebView: WebView? = null

    fun setData(data: ArticleContentEntity?, WebView: WebView) {
        if (data == null) {
            return
        }
        ContactPlugin.mWebView = WebView
        setTopImg(data.pic)
        setTitle(data.title)
        //setColumn(data.getClassName());
        setAuthor(data.leftLabel)
        setTime(data.centerLabel)
        setReadcount(data.rightLabel)
        setIntroduction(data.detaile_content)
        //		MyLog.i("文章内容："+Data.getContent());
        setContent(data.content, "")//data.getColumn_Type()
    }

    // 设置标题
    fun setTopImg(src: String?) {
        mWebView!!.loadUrl("javascript:topImg('$src')")
    }

    // 设置标题
    fun setTitle(title: String?) {
        mWebView!!.loadUrl("javascript:title('$title')")
    }

    // 设置栏目
    fun setColumn(column: String) {
        //mWebView.loadUrl("javascript:column('" + column + "')");
    }

    // 设置作者
    fun setAuthor(author: String?) {
        mWebView!!.loadUrl("javascript:author('$author')")
    }

    // 设置时间
    fun setTime(time: String?) {
        mWebView!!.loadUrl("javascript:time('$time')")

    }

    // 设置阅读量
    fun setReadcount(readcount: String?) {
        mWebView!!.loadUrl("javascript:readcount('$readcount')")
    }

    // 设置导读
    fun setIntroduction(introduction: String?) {
        /*if(ExampleUtil.isEmpty(introduction)) {
			introduction = "懂车的人都在这里。";
		}*/
        mWebView!!.loadUrl("javascript:introduction('$introduction')")
    }


    // 设置内容（Content）
    fun setContent(content: String?, column: String) {
        mWebView!!.loadUrl("javascript:content('$content')")
        /*if (!ExampleUtil.isEmpty(column)) {
            if (column.equals("原创")) {
                column = "1";
            } else if (column.equals("广场")) {
                column = "0";

            } else {//if(column.equals("")) {
                column = "-1";
            }
        }
        setColumnstyle(column);*/
    }


    // 设置栏目样式
    fun setColumnstyle(column: String) {
        mWebView!!.loadUrl("javascript:columnstyle('$column')")
    }
}
