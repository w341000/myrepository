package bean.book;
/**
 * ֧����ʽ
 * @author Administrator
 *
 */
public enum PaymentWay {
	/**����֧��**/
	NET {
		@Override
		public String getName() {return "����֧��";
		}
	},
	/**��������**/
	COD {
		@Override
		public String getName() {return "��������";
		}
	},
	/**���е��**/
	BANKREMITTANCE {
		@Override
		public String getName() {return "���е��";
		}
	},
	/**�ʾֻ��**/
	POSTOFFICEREMITTANCE {
		@Override
		public String getName() {return "�ʾֻ��";
		}
	};

	public abstract String getName();
}
