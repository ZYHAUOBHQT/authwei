package com.upic.api;

import java.util.List;

/**
 * 发送被动响应消息
 * 对于每一个POST请求，开发者在响应包（Get）中返回特定XML结构，对该消息进行响应（现支持回复文本、图片、图文、语音、视频、音乐）。请注意
 * ，回复图片等多媒体消息时需要预先上传多媒体文件到微信服务器，只支持认证服务号。
 * 
 * 微信服务器在五秒内收不到响应会断掉连接，并且重新发起请求，总共重试三次，如果在调试中，发现用户无法收到响应的消息，可以检查是否消息处理超时。
 * 
 * 关于重试的消息排重，有msgid的消息推荐使用msgid排重。事件类型消息推荐使用FromUserName + CreateTime 排重。
 * 
 * 假如服务器无法保证在五秒内处理并回复，可以直接回复空串，微信服务器不会对此作任何处理，并且不会发起重试。 这种情况下，可以使用客服消息接口进行异步回复。
 */
// http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E8%A2%AB%E5%8A%A8%E5%93%8D%E5%BA%94%E6%B6%88%E6%81%AF
public class ReplyMsgApi {

	private static String getXML(String toUserName, String fromUserName, MessageType msgType, String subXML) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		sb.append("<ToUserName><![CDATA[" + toUserName + "]]></ToUserName>");
		sb.append("<FromUserName><![CDATA[" + fromUserName + "]]></FromUserName>");
		sb.append("<CreateTime>" + System.currentTimeMillis() / 1000 + "</CreateTime>");
		sb.append("<MsgType><![CDATA[" + msgType.name() + "]]></MsgType>");
		sb.append(subXML);
		sb.append("</xml>");
		return sb.toString();
	}

	/**
	 * 回复文本消息 ToUserName 是 接收方帐号（收到的OpenID） FromUserName 是开发者微信号 Content
	 * 是回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
	 */
	public static String newTextMessage(String toUserName, String fromUserName, String content) {
		return getXML(toUserName, fromUserName, MessageType.text, "<Content><![CDATA[" + content + "]]></Content>");
	}

	/**
	 * 回复图片消息 ToUserName 是 接收方帐号（收到的OpenID） FromUserName 是 开发者微信号 MediaId 是
	 * 通过上传多媒体文件，得到的id。
	 */
	public static String newImageMessage(String toUserName, String fromUserName, String mediaId) {
		StringBuffer sb = new StringBuffer();
		sb.append("<Image>");
		sb.append("<MediaId><![CDATA[" + mediaId + "]]></MediaId>");
		sb.append("</Image>");
		return getXML(toUserName, fromUserName, MessageType.image, sb.toString());
	}

	/**
	 * 回复语音消息 ToUserName 是 接收方帐号（收到的OpenID） FromUserName 是 开发者微信号 MediaId 是
	 * 通过上传多媒体文件，得到的id。
	 */
	public static String newVoiceMessage(String toUserName, String fromUserName, String mediaId) {
		StringBuffer sb = new StringBuffer();
		sb.append("<Voice>");
		sb.append("<MediaId><![CDATA[" + mediaId + "]]></MediaId>");
		sb.append("</Voice>");
		return getXML(toUserName, fromUserName, MessageType.voice, sb.toString());
	}

	/**
	 * 回复视频消息 ToUserName 是 接收方帐号（收到的OpenID） FromUserName 是 开发者微信号 MediaId 是
	 * 通过上传多媒体文件，得到的id。 Title 否 视频消息的标题 Description 否 视频消息的描述
	 */
	public static String newVideoMessage(String toUserName, String fromUserName, String mediaId, String title, String description) {
		StringBuffer sb = new StringBuffer();
		sb.append("<Video>");
		sb.append("<MediaId><![CDATA[" + mediaId + "]]></MediaId>");
		sb.append("<Title><![CDATA[" + title + "]]></Title>");
		sb.append("<Description><![CDATA[" + description + "]]></Description>");
		sb.append("</Video>");
		return getXML(toUserName, fromUserName, MessageType.video, sb.toString());
	}

	/**
	 * 回复音乐消息 ToUserName 是 接收方帐号（收到的OpenID） FromUserName 是 开发者微信号 Title 否 音乐标题
	 * Description 否 音乐描述 MusicURL 否 音乐链接 HQMusicUrl 否 高质量音乐链接，WIFI环境优先使用该链接播放音乐
	 * ThumbMediaId 是 缩略图的媒体id，通过上传多媒体文件，得到的id
	 */
	public static String newMusicMessage(String toUserName, String fromUserName, String title, String description, String music_Url, String hq_music_url, String thumbMediaId) {
		StringBuffer sb = new StringBuffer();
		sb.append("<Music>");
		sb.append("<Title><![CDATA[" + title + "]]></Title>");
		sb.append("<Description><![CDATA[" + description + "]]></Description>");
		if (music_Url != null && !"".equals(music_Url)) {
			sb.append("<MusicUrl><![CDATA[" + music_Url + "]]></MusicUrl>");
		}
		if (hq_music_url != null && !"".equals(hq_music_url)) {
			sb.append("<HQMusicUrl><![CDATA[" + hq_music_url + "]]></HQMusicUrl>");
		}
		sb.append("<ThumbMediaId><![CDATA[" + thumbMediaId + "]]></ThumbMediaId>");
		sb.append("</Music>");
		return getXML(toUserName, fromUserName, MessageType.music, sb.toString());
	}

	/**
	 * 回复图文消息 ToUserName 是 接收方帐号（收到的OpenID） FromUserName 是 开发者微信号 MsgType 是 news
	 * ArticleCount 是 图文消息个数，限制为10条以内 Articles 是
	 * 多条图文消息信息，默认第一个item为大图,注意，如果图文数超过10，则将会无响应 Title 否 图文消息标题 Description 否
	 * 图文消息描述 PicUrl 否 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200 Url 否
	 * 点击图文消息跳转链接
	 * 
	 * @return
	 */
	public static String newNewsMessage(String toUserName, String fromUserName, List<Article> articles) {
		StringBuffer sb = new StringBuffer();
		sb.append("<ArticleCount>" + articles.size() + "</ArticleCount>");
		sb.append("<Articles>");
		for (Article article : articles) {
			sb.append("<item>");
			sb.append("<Title><![CDATA[" + article.getTitle() + "]]></Title>");
			sb.append("<Description><![CDATA[" + article.getDescription() + "]]></Description>");
			sb.append("<PicUrl><![CDATA[" + article.getPicUrl() + "]]></PicUrl>");
			sb.append("<Url><![CDATA[" + article.getUrl() + "]]></Url>");
			sb.append("</item>");
		}
		sb.append("</Articles>");
		return getXML(toUserName, fromUserName, MessageType.news, sb.toString());
	}

	public static class Article {
		private String title;
		private String description;
		private String picUrl;
		private String url;

		public Article() {
		}

		public Article(String title, String description, String picUrl, String url) {
			this.title = title;
			this.description = description;
			this.picUrl = picUrl;
			this.url = url;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getPicUrl() {
			return picUrl;
		}

		public void setPicUrl(String picUrl) {
			this.picUrl = picUrl;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
	}

	public enum MessageType {
		text, image, voice, video, music, news
	}
}
