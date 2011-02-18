package org.orman.mapper;

import java.lang.reflect.Method;

import org.orman.mapper.annotation.Column;
import org.orman.mapper.annotation.Id;
import org.orman.mapper.annotation.Index;

/**
 * Holds field information such as its original name, generated (physical) name,
 * data type, nullability, whether it is a {@link Index} or {@link Id} field and
 * getter-setter methods to obtain and set its value during object mapping if it
 * is a non-public field.
 * 
 * @author alp
 * 
 */
public class Field {
	private Class<?> clazz;
	private String originalName;
	private String customName;
	private String generatedName;
	private String type;
	private String customType;
	private FieldIndexHolder index;
	private boolean isId = false;
	private boolean nullable = true;

	// Reflection fields
	private Method setterMethod;
	private Method getterMethod;

	public Field(Class<?> clazz, String name) {
		this.clazz = clazz;
		this.originalName = name;

		if (clazz.isAnnotationPresent(Column.class)) {
			String tmpCustomName = clazz.getAnnotation(Column.class).name();
			String tmpCustomType = clazz.getAnnotation(Column.class).type();

			this.customName = (tmpCustomName == null || ""
					.equals(tmpCustomName)) ? null : tmpCustomName;
			this.customType = (tmpCustomType == null || ""
					.equals(tmpCustomType)) ? null : tmpCustomType;
		}
	}

	public String getCustomName() {
		return customName;
	}

	public String getCustomType() {
		return customType;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public String getOriginalName() {
		return originalName;
	}

	public String getGeneratedName() {
		return generatedName;
	}

	public void setGeneratedName(String generatedName) {
		this.generatedName = generatedName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Compares two fields using their physical name.
	 */
	public int compareTo(Field f) {
		return this.getGeneratedName().compareTo(f.getGeneratedName());
	}

	public boolean equals(Field f) {
		return this.compareTo(f) == 0;
	}

	public void setIndex(FieldIndexHolder index) {
		this.index = index;
	}

	public FieldIndexHolder getIndex() {
		return index;
	}

	/**
	 * @param isId
	 *            use <code>true</code> to make this field an Id field (can
	 *            exist only once in an {@link Entity} and create index on this
	 *            {@link Field}.
	 */
	public void makeId(boolean isId) {
		this.isId = isId;
	}

	/**
	 * @return <code>false</code> if this field is not a {@link Index}.
	 */
	public boolean isId() {
		return isId;
	}

	public void setSetterMethod(Method setterMethod) {
		this.setterMethod = setterMethod;
	}

	public Method getSetterMethod() {
		return setterMethod;
	}

	public void setGetterMethod(Method getterMethod) {
		this.getterMethod = getterMethod;
	}

	public Method getGetterMethod() {
		return getterMethod;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	/**
	 * @return <code>false</code> if this field is a NOT NULL field. true in
	 *         default case.
	 */
	public boolean isNullable() {
		return nullable;
	}
}
