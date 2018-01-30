package com.upic.po;

public enum SexEntity {
	UNKNOW {
		@Override
		public String getLabel() {
			return "未知";
		}
	},
	MAN {
		@Override
		public String getLabel() {
			return "男";
		}
	},
	WOMAN {
		@Override
		public String getLabel() {
			return "女";
		}
	};
	public abstract String getLabel();
}
