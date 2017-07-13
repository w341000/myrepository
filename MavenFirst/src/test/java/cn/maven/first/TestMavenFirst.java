package cn.maven.first;

import org.junit.Test;

import junit.framework.Assert;

public class TestMavenFirst {
	@Test
	public void test1(){
		MavenFirst hw=new MavenFirst();
		Assert.assertEquals("hello abc", hw.hello("abc"));
	}

}
