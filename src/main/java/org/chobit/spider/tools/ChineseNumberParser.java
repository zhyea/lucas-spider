package org.chobit.spider.tools;


import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 中文数字解析
 *
 * @author robin
 */
public class ChineseNumberParser {


	/**
	 * 中文数字和阿拉伯数字的映射
	 */
	private static final Map<Character, Integer> CHINESE_2_ARAB = new HashMap<Character, Integer>() {
		{
			put('零', 0);
			put('一', 1);
			put('二', 2);
			put('三', 3);
			put('四', 4);
			put('五', 5);
			put('六', 6);
			put('七', 7);
			put('八', 8);
			put('九', 9);
			put('十', 10);
		}
	};


	/**
	 * 单位对应数量级的表
	 */
	private static final Map<Character, Integer> UNIT_MAP = new HashMap<Character, Integer>() {
		{
			put('十', 10);
			put('百', 100);
			put('千', 1000);
			put('万', 10000);
			put('亿', 10000 * 10000);

		}
	};


	/**
	 * 中文数字及单位正则表达式
	 */
	private static final Pattern PATTERN_CHINESE_NUM = Pattern.compile("[零一二三四五六七八九十]?[十百千万亿]?");


	/**
	 * 将汉字转为阿拉伯数字
	 * <p>
	 * 处理思路：
	 * <p>
	 * 可能的类型：
	 * 一千三百万  130000000
	 * 十一       11
	 * 一万       10000
	 * 一百二十五  125
	 * <p>
	 * 通过正则将字符串处理为 数字+单位的情况
	 * 然后处理的时候通过数字*单位+数字*单位...得到最后结果
	 *
	 * @param chinese 汉字输入，比如一千三百二十八
	 * @return 阿拉伯输入，比如 1328
	 */
	public static Integer parse(String chinese) {

		Objects.requireNonNull(chinese);

		if (chinese.startsWith("十")) {
			chinese = "一十" + chinese.substring(1);
		}
		//判断参数合法性
		if (!PATTERN_CHINESE_NUM.matcher(chinese).replaceAll("").trim().equals("")) {
			throw new InvalidParameterException();
		}

		Integer result = 0;

		Matcher matcher = PATTERN_CHINESE_NUM.matcher(chinese);

		//正则提取所有数字
		while (matcher.find()) {
			String res = matcher.group(0);
			if (res.length() == 2) {
				result += CHINESE_2_ARAB.get(res.charAt(0)) * UNIT_MAP.get(res.charAt(1));
			} else if (res.length() == 1) {

				//处理三十、一百万的情况
				if (UNIT_MAP.containsKey(res.charAt(0))) {
					result *= UNIT_MAP.get(res.charAt(0));
				} else if (CHINESE_2_ARAB.containsKey(res.charAt(0))) {
					result += CHINESE_2_ARAB.get(res.charAt(0));
				}
			}
		}
		return result;
	}

}
