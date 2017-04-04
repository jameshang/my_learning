package com.jh.myweb.service.impl;

import com.jh.myweb.service.Architect;
import com.jh.myweb.service.Programmer;

public class SoftwareEngineer implements Architect, Programmer {

	@Override
	public String code(String source) {
		return "Code ==> " + source;
	}

	@Override
	public String design(String source) {
		return "Designed ==> " + source;
	}

}
