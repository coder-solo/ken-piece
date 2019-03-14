package com.ken.constants;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.EnumUtils;

import java.util.Arrays;

public class PieceEnum {

	public enum PieceType {

		JAVA(1), VUE(2);

		@Getter
		@Setter
		private Integer value;

		PieceType(Integer value) {
			this.value = value;
		}

		public static boolean contains(Integer value) {
			return Arrays.stream(PieceType.values()).map(PieceType::getValue).anyMatch(p -> p.equals(value));
		}

		public static boolean containsName(String name) {
			return EnumUtils.isValidEnum(PieceType.class, name);
		}
	}
}
