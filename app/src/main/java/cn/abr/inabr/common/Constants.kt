package cn.abr.inabr.common

/**
 * .
 * Created by Administrator on 2018/5/8/008.
 */
object Constants {

    val About_Raw = formatJson("tags","contentId")//文章相关推荐

    val ARTICLE_COMMENT = formatJson("id","page","pageSize","userId")//文章相关推荐

    val SEND_COMMENT = formatJson("content","userId","parentId","target_user_id","type")//文章相关推荐

    val SMS_CODE = formatJson("mobile")//短信验证码

    val PWD_LOGIN =formatJson("mobile","password") //密码登录

    val PHONE_REGISTER =formatJson("mobile","password","code") //手机号注册

    val THIRD_LOGIN =formatJson("name","openid","type","pic") //三方登录

    val SMS_LOGIN = formatJson("mobile","code")//短信登录

    val PWD_BACK = PHONE_REGISTER//短信登录


    private fun formatJson(vararg name: String): String {
        var buffer=StringBuffer()
        return  buffer.apply {
            append("{")
            name.indices.forEach {

                append(name[it]).append(":\"%").append((it+1).toString()).append("\$s\"")
                if (it!=name.size-1)
                    append(",")
                else append("}")
            }
        }.toString()
    }
}