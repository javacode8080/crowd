package com.company.crowd.test;

import org.junit.Test;

import com.company.crowd.util.CrowdUtil;

public class StringTest {
	
	@Test
	public void testMd5() {
		String source = "164525";
		String encoded = CrowdUtil.md5(source);
		System.out.println(encoded);
	}


}