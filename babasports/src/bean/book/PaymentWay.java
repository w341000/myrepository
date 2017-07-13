package bean.book;
/**
 * 支付方式
 * @author Administrator
 *
 */
public enum PaymentWay {
	/**网上支付**/
	NET {
		@Override
		public String getName() {return "网上支付";
		}
	},
	/**货到付款**/
	COD {
		@Override
		public String getName() {return "货到付款";
		}
	},
	/**银行电汇**/
	BANKREMITTANCE {
		@Override
		public String getName() {return "银行电汇";
		}
	},
	/**邮局汇款**/
	POSTOFFICEREMITTANCE {
		@Override
		public String getName() {return "邮局汇款";
		}
	};

	public abstract String getName();
}
