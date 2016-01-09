package com.mirzaakhena.apps;

import com.eaio.stringsearch.BNDM;

public class Test {

	public static void main(String[] args) {

		BNDM bndm = new BNDM();
		int i = bndm.searchBytes("abacadabrabracabracadabrabrabracad".getBytes(), "cad".getBytes());
		System.out.println(i);
	}
}
