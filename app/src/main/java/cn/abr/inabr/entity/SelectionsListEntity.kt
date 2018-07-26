package cn.abr.inabr.entity

/**
 * .
 * Created by Administrator on 2018/4/27/027.
 */



data class SelectionsListEntity(
		var attribute: Int? = 0, //2
		var centerLabel: String? = "", //2018/4/3 11:47:44
		var imageUrl: List<String?>? = listOf(),
		var leftLabel: String? = "", //阿蛮评车
		var newsID: String? = "", //V695660789570
		var rightLabel: String? = "", //播放 3036
		var title: String? = "", //【视频】换装7速双离合 阿蛮现场体验哈弗M6
		var type: Int? = 0, //4
		var videoColumn: String? = "", //阿蛮评车
		var videoColumnID: String? = "", //692789447789
		var videoColumnImage: String? = "", //http://www.infoacq.com/files/videocolumn/amanpingche.png
		var videoTime: String? = "", //4:37
		var videoUrl: String? = ""
)