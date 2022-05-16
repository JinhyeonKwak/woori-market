package com.mayy5.admin.util;

import java.lang.reflect.Field;
import java.util.Objects;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EntityUtil {

	public static <T> T setValueExceptNull(T firstInstance,
		T secondInstance) {
		try {
			Class firstClass = firstInstance.getClass();
			for (Field field : firstClass.getDeclaredFields()) {
				field.setAccessible(true);
				if (Objects.nonNull(field.get(firstInstance))
					&& Objects.nonNull(field.get(secondInstance))) {
					field.set(firstInstance, field.get(secondInstance));
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return firstInstance;
	}
}
