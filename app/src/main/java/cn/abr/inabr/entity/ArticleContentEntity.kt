package cn.abr.inabr.entity

/**
 * .
 * Created by Administrator on 2018/5/2/002.
 */

data class ArticleContentEntity(
        var newsID: String? = "", //427845412767
        var title: String? = "", //硬汉霸道的外观却是买菜骐达的内饰 日产硬派SUV途达能越野吗
        var content: String? = "",
        var detaile_content: String? = "", //日产途达将于4月12日正式上市。这台售价20万左右的日产硬派SUV，真的能越野吗？
        var leftLabel: String? = "", //汽场  马可
        var rightLabel: String? = "", //阅读 1455
        var centerLabel: String? = "", //2018/4/23 17:30:20
        var tags: String? = "", //日产
        var totleComm: String? = "", //评论个数
        var pic: String? = "" //http://www.infoacq.com/files/images/542725545716/images/201804/20180403/timg1.jpg
)
