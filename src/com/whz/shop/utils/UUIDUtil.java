package com.whz.shop.utils;

import java.util.UUID;

/** 
 * @author whz 
 * @version on:2019-3-17 上午11:27:44 
 */
public class UUIDUtil {
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
