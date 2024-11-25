package com.ljk.healthcheck;

public class TestPlayground {

	public static void main(String[] args) {
		String a = "/proxy/adasdas";

		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000000000; i++) {
			if (i == 1000000000 - 1) {
				System.out.println(a.replace("/proxy", ""));
			}
		}
		long end = System.currentTimeMillis();

		System.out.println((end - start) / 1000.0);

		long start2 = System.currentTimeMillis();
		for (int i = 0; i < 1000000000; i++) {
			if (i == 1000000000 - 1) {
				System.out.println(a.substring(6));
			}
		}
		long end2 = System.currentTimeMillis();

		System.out.println((end2 - start2) / 1000.0);

		long start3 = System.currentTimeMillis();
		for (int i = 0; i < 1000000000; i++) {
			if (i == 1000000000 - 1) {
				System.out.println(a.replaceFirst("^/proxy", ""));
			}
		}
		long end3 = System.currentTimeMillis();

		System.out.println((end3 - start3) / 1000.0);
	}
}
