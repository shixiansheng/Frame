package cn.abr.inabr.activity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import cn.abr.inabr.R
import cn.abr.inabr.adapter.ArticleAdapter
import cn.abr.inabr.base.BaseEntity
import cn.abr.inabr.base.BasePresenterActivity
import cn.abr.inabr.dagger2.component.DaggerArticleComponent
import cn.abr.inabr.dagger2.module.ArticleModule
import cn.abr.inabr.entity.ArticleContentEntity
import cn.abr.inabr.entity.CommentEntity
import cn.abr.inabr.entity.EmptyEntity
import cn.abr.inabr.entity.SelectionsListEntity
import cn.abr.inabr.listener.EndlessRecyclerOnScrollListener
import cn.abr.inabr.mvp.presenter.ArticlePresenter
import cn.abr.inabr.mvp.view.ArticleContentView
import cn.abr.inabr.utils.ContactPlugin
import cn.abr.inabr.weight.ReplyDialog
import com.just.agentweb.AgentWeb
import kotlinx.android.synthetic.main.activity_article.*
import java.util.*


class ArticleActivity : BasePresenterActivity<ArticlePresenter>(), ArticleContentView {

    override fun sendComment(response: BaseEntity<List<EmptyEntity>>) {
        if (response.Data?.first()?.res.equals("1")) {
            hideLoadingProgress()
        }
    }

    override fun showComment(response: BaseEntity<List<CommentEntity>>) {

        commentlist.addAll(response.Data!!)
        article_recyclerView.adapter.notifyDataSetChanged()
        hideLoadingProgress()
        if (response.Data!!.isEmpty()) {
            is_all = true
        }

    }



    override fun showAbout(response: BaseEntity<List<SelectionsListEntity>>) {
        datalist.addAll(response.Data!!)
    }

    private lateinit var article: ArrayList<ArticleContentEntity>

    private var page: Int = 0

    private var is_all: Boolean = false

    // 文章评论数据上拉加载
    internal inner class MyScrollListener(layoutmanager: LinearLayoutManager) : EndlessRecyclerOnScrollListener(layoutmanager) {


        override fun onLoadMore(currentPage: Int) {
            if (is_all) {
                return
            }
            mPresenter.getCommentList(newId, (++page).toString())
        }
    }

    override fun showArticle(response: BaseEntity<List<ArticleContentEntity>>) {
        if (response.Data!!.isEmpty())
            GONE(article_recyclerView)
        else {
            val data = response.Data!!
            article.addAll(data)
            mAgentWeb!!.webCreator.webView.loadUrl("file:///android_asset/index.html")
            mPresenter.getSelectionsList(data[0].tags!!, newId)
            mPresenter.getCommentList(newId, page.toString())
        }
    }

    override fun inject() {
        DaggerArticleComponent.builder().articleModule(ArticleModule(this)).build().inject(this)
    }

    private var mAgentWeb: AgentWeb? = null


    override fun initView() {
        showLoadingProgress()
        initWebView()
    }


    private var wv_layout: LinearLayout? = null

    private fun initWebView() {
        wv_layout = LinearLayout(this)
        mAgentWeb = AgentWeb
                .with(this)
                .setAgentWebParent(wv_layout!!, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .closeIndicator()
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .createAgentWeb()
                .ready()
                .go("")
        //取消滑动条
        mAgentWeb!!.webCreator.webView.apply {
            isVerticalScrollBarEnabled = false
            setLayerType(View.LAYER_TYPE_NONE, null)
        }
        //mAgentWeb.jsAccessEntrace.quickCallJs("callByAndroid");  调用js方法
    }



    private val mWebViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            // 响应文章内容中点击超链接调用系统浏览器打开
            val hit = view.hitTestResult
            if (hit != null) {
                val hitType = hit.type
                if (hitType == WebView.HitTestResult.SRC_ANCHOR_TYPE || hitType == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                    // 点击超链接
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                } else {
                    view.loadUrl(url)
                }
            } else {
                view.loadUrl(url)
            }
            return true
        }


        // setData
        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            //js 加载数据
            if (article.size > 0)
                ContactPlugin.setData(article[0], view)

            //解决webView在ScrollView中高度显示不全问题
            view.measure(0, 0)
        }


        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            view!!.settings.apply {
                //支持js
                javaScriptEnabled = true
                // 支持打开优酷视频
                domStorageEnabled = true
                // 这个从而播放视频
                pluginState = WebSettings.PluginState.ON
                // WebView不是用缓存
                cacheMode = WebSettings.LOAD_NO_CACHE
                // 无法根据浏览器居中显示内容这个问题（自适应屏幕）
                layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
                loadWithOverviewMode = true
                useWideViewPort = true
            }
        }


        // Error
        override fun onReceivedError(view: WebView, errorCode: Int,
                                     description: String, failingUrl: String) {
        }
    }

    private val mWebChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            //do you work
        }
    }


    override fun onPause() {
        mAgentWeb!!.webLifeCycle.onPause()
        super.onPause()

    }

    override fun onResume() {
        mAgentWeb!!.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mAgentWeb!!.webLifeCycle.onDestroy()
        super.onDestroy()
    }


    private var newId: String = ""
    private lateinit var datalist: MutableList<SelectionsListEntity>
    private lateinit var commentlist: MutableList<CommentEntity>
    override fun initData() {
        newId = intent.getStringExtra("NewsId")
        article = arrayListOf()
        datalist = arrayListOf()
        commentlist = arrayListOf()
        replyDialog= ReplyDialog(this@ArticleActivity)
        mPresenter.getArticleContent(newId)
        article_recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ArticleActivity)
            addOnScrollListener(MyScrollListener(layoutManager as LinearLayoutManager))
            adapter = ArticleAdapter().apply {
                wvParent = wv_layout
                datalist = this@ArticleActivity.datalist
                commentlist = this@ArticleActivity.commentlist
            }
        }


    }

    private lateinit var replyDialog: ReplyDialog


    // 弹出发表评论 Dialog
    private fun sendCommentDialog() {
        replyDialog.setHintText("回复某人的评论...")
                .setOnBtnCommitClickListener {

                    val text = replyDialog.content
                    if (TextUtils.isEmpty(text)) {
                        showToast("评论内容不能为空")
                        return@setOnBtnCommitClickListener
                    } else if (text.length > 1000) {
                        showToast("字数超出限制")
                        return@setOnBtnCommitClickListener
                    }
                    replyDialog.dismiss()
                    showLoadingProgress()
                    mPresenter.sendComment(text, "848935442295", newId, "")
                }
                .show()
    }

    override fun initListener() {
    }

    @SuppressWarnings("unchecked")
    fun send_comment(v: View) {
        sendCommentDialog()
    }
    @SuppressWarnings("unchecked")
    fun comment(v: View) {

    }
    @SuppressWarnings("unchecked")
    fun like(v: View) {

    }
    @SuppressWarnings("unchecked")
    fun collect(v: View) {

    }

    override fun share(v: View) {

    }

    override val initLayout: Int
        get() = R.layout.activity_article

}
