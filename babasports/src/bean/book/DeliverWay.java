package bean.book;
/**
 * 送货方式
 * @author Administrator
 *
 */
public enum DeliverWay {
	/**平邮**/
	GENERALPOST {
		public String getName() {return "平邮";
		}
	},
	/**快递送货上门 **/
	EXPRESSDELIVERY {
		@Override
		public String getName() {return "快递送货上门";
		}
	},
	/**加急快递送货上门**/
	EXIGENCEEXPRESSDELIVERY {
		@Override
		public String getName() {return "加急快递送货上门";
		}
	},
	/**国内特快专递EMS**/
	EMS {
		@Override
		public String getName() {return "国内特快专递EMS";
		}
	};
	public abstract String getName();
}
