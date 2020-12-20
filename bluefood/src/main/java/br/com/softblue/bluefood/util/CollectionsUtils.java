package br.com.softblue.bluefood.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionsUtils {
	
	@SafeVarargs
	public static <T>  List<T> Listof(T... objs) {
		if (objs == null) {
			return Collections.emptyList();
		}
		
		return Arrays.stream(objs).collect(Collectors.toList());
		
	}

}
