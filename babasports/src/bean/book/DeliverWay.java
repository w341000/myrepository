package bean.book;
/**
 * �ͻ���ʽ
 * @author Administrator
 *
 */
public enum DeliverWay {
	/**ƽ��**/
	GENERALPOST {
		public String getName() {return "ƽ��";
		}
	},
	/**����ͻ����� **/
	EXPRESSDELIVERY {
		@Override
		public String getName() {return "����ͻ�����";
		}
	},
	/**�Ӽ�����ͻ�����**/
	EXIGENCEEXPRESSDELIVERY {
		@Override
		public String getName() {return "�Ӽ�����ͻ�����";
		}
	},
	/**�����ؿ�ר��EMS**/
	EMS {
		@Override
		public String getName() {return "�����ؿ�ר��EMS";
		}
	};
	public abstract String getName();
}
