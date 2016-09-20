/**
 * 
 */
package com.huateng.p3.hub.common.constant.enums;


/**
 * 公用常量类
 * 
 * @author cmt
 * 
 */

public class Common {

	/**
	 * 是否有效
	 * 
	 * @author cmt
	 * 
	 */
	// 特殊符号常量
	public enum SpeSymbol {
		SPACE(" ", "空格符"), BLANK("", "空"), ONE("1", "1"), TWO("2", "2"), THREE(
				"3", "3"), FOUR("4", "4"), NUM_SIX("6", "6"), EQUAL("=",
				"String等号"), SEP("./", "String分隔符'./'"), Y("Y", "Y"), IN("in",
				"in"), EQUAL_MARK("=", "等号"), COMMA_MARK(",", "逗号"), CNEQUAL_MARK(
				"CN=", "CN="), LISTSEPARATOR("|", "管道符"), NEXTROW("\n", "回车符"), LINE_MARK(
				"-", "横杠"), WELL_MARK("#", "井号"), UN_CHECK_CODE("XYZA", "特殊验证码"), CONTENT_TYPE(
				"Content-type", "Head参数 CONTENT类型"), APP_XML_TYPE(
				"application/xml;charset=utf-8", "Head参数 APP_XML类型"), NAMESPACE(
				"http://www.w3.org/2000/09/xmldsig#", "Head参数 命名空间"), XML_ENCODE_UTF8(
				"UTF-8", "UTF——8"), ZERO("0", "0");

		String value;
		String desc;

		SpeSymbol(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public String getValue() {
			return this.value;
		}

		public String getDesc() {
			return this.desc;
		}
	}

	public enum IsActive {
		True("00", "有效"), False("01", "无效");

		String value;
		String desc;

		IsActive(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public String getValue() {
			return this.value;
		}

		public String getDesc() {
			return this.desc;
		}

		public static String getDescByValue(String value) {
			if (True.toString().equals(value)) {
				return True.desc;
			} else {
				return False.desc;
			}
		}
	}

	/**
	 * 是否历史
	 * 
	 * @authorcmt
	 * 
	 */
	public enum IsHistory {
		Normal("00", "正常"), History("01", "历史记录");

		String value;
		String desc;

		IsHistory(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public String getValue() {
			return this.value;
		}

		public String getDesc() {
			return this.desc;
		}
	}

	/**
	 * 是否冻结
	 * 
	 * @author cmt
	 * 
	 */
	public enum IsFrozen {
		True("01", "冻结"), False("00", "正常");

		String value;
		String desc;

		IsFrozen(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public String getValue() {
			return this.value;
		}

		public String getDesc() {
			return this.desc;
		}
	}

	/**
	 * 成功标志
	 * 
	 * @author cmt
	 * 
	 */
	public enum SuccessFlag {
		Success("00", "成功"), Error("01", "失败");

		String value;
		String desc;

		SuccessFlag(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public String getValue() {
			return this.value;
		}

		public String getDesc() {
			return this.desc;
		}

		public static String getDescByValue(String value) {

			if (Success.value.equals(value))
				return Success.desc;
			else
				return Error.desc;
		}
	}

	/**
	 * 是，否
	 * 
	 * @author cmt
	 * 
	 */
	public enum YesOrNo {
		Yes("1", "是"), No("0", "否");

		String value;
		String desc;

		YesOrNo(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public String getValue() {
			return this.value;
		}

		public String getDesc() {
			return this.desc;
		}

		public static String getDescByValue(String value) {
			if (Yes.value.equals(value)) {
				return Yes.desc;
			} else {
				return No.desc;
			}
		}
	}

	/**
	 * 是否默认
	 * 
	 * @author cmt
	 * 
	 */
	public enum IsDefault {
		True("00", "默认"), False("01", "普通");

		String value;
		String desc;

		IsDefault(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public String getValue() {
			return this.value;
		}

		public String getDesc() {
			return this.desc;
		}

		public static String getDescByValue(String value) {
			if (True.toString().equals(value)) {
				return True.desc;
			} else {
				return False.desc;
			}
		}
	}

	/**
	 * 交易动作代码
	 * 
	 * @author cmt
	 * 
	 */
	public enum ActionCode {
		Requset("0", "请求"), Respone("1", "应答"), ;
		String value;
		String desc;

		ActionCode(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return this.value.toString();
		}

		public String getValue() {
			return this.value;
		}

		public String getDesc() {
			return this.desc;
		}

	}

	/**
	 * 交易状态代码
	 * 
	 * @author cmt
	 * 
	 */
	public enum TxnStatus {
		InitStatus("99", "初始"), TxnSuccess("00", "成功"), TxnFail("01", "失败"), TxnTimeOut(
				"02", "超时");

		String value;
		String desc;

		TxnStatus(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return this.value.toString();
		}

		public String getValue() {
			return this.value;
		}

		public String getDesc() {
			return this.desc;
		}

	}

	/**
	 * 交易步骤代码
	 * 
	 * @author cmt
	 * 
	 */
	public enum TxnStep {
		InitStep("99", "初始");

		String value;
		String desc;

		TxnStep(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return this.value.toString();
		}

		public String getValue() {
			return this.value;
		}

		public String getDesc() {
			return this.desc;
		}

	}

	/**
	 * 路由状态
	 * 
	 * @author Gary
	 * 
	 */
	public enum RouteStatus {
		START("00", "正常"), STOP("01", "停用");
		private String value;
		private String desc;

		private RouteStatus(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public String getValue() {
			return this.value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
	}

}
