package cn.abr.inabr.entity

/**
 * .
 * Created by Administrator on 2018/5/28/028.
 */

data class CommentEntity(
        var presentId: String? = "", //C925474316778
        var userName: String? = "", //汽场QAQ
        var userId: String? = "", //952723706591
        var userImg: String? = "", //http://www.infoacq.com/userfiles/952723706591/952723706591head.jpg
        var createTime: String? = "", //2018/5/18 18:01:04
        var content: String? = "", //Fghcccf
        var isLike: Int? = 0, //0
        var SubCount: Int? = 0, //1
        var likeCount: String? = "" //5
)