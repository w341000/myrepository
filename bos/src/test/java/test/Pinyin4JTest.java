package test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import bos.utils.PinYin4jUtils;

public class Pinyin4JTest {
	@Test
	public void test(){
		String province="北京市";
		String city="北京数市";
		String district="长安区";
		city=city.substring(0, city.length()-1);
		province=province.substring(0, city.length()-1);
		district=district.substring(0, city.length()-1);
		String[] stringToPinyin = PinYin4jUtils.stringToPinyin(city);
		//城市编码
		String citycode=StringUtils.join(stringToPinyin);
		String info=province+city+district;
		String[] headByString = PinYin4jUtils.getHeadByString(info);
		//简码
		String shortcode= StringUtils.join(headByString);
		System.out.println(shortcode);
		System.out.println(citycode);
	}
}
