package luxk.jdbt;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.lang.reflect.Method;

public class UtilBean {
	public static void setProperty( Object bean, String prop, String value)
			throws Exception  {

		if(prop == null || value == null) return;

		BeanInfo info = null;
		Method method = null;
		Class<?> type = null;
		Class<?> propertyEditorClass = null;

		info = Introspector.getBeanInfo(bean.getClass());

		if(info != null) {
			PropertyDescriptor pd[] = info.getPropertyDescriptors();
			for(int i = 0; i < pd.length; i++) {
				if(pd[i].getName().equals(prop)) {
					method = pd[i].getWriteMethod();
					type = pd[i].getPropertyType();
					propertyEditorClass = pd[i].getPropertyEditorClass();
					break;
				}
			}

			if(method == null)
				throw new Exception("invalid property " + prop + " in " +
						bean.getClass().getName());

			// 추후 확장 구현 부분. array type에 대해서 처리할 필요 있음.
			if(type.isArray()) return;
			else {
				Object oVal = convertType(prop, value, type,
						propertyEditorClass);
				if(oVal != null)
					method.invoke(bean, new Object[] { oVal });
			}
		}
	}

	public static Object convertType(String propName, String propValue,
			Class<?> propType, Class<?> propEditorClass) throws Exception {

		if(propValue == null) {
			if(propType.equals(Boolean.class) || propType.equals(Boolean.TYPE))
				propValue = "false";
			else
				return null;
		}

		if(propEditorClass != null) {
			return getValueFromBeanInfoPropertyEditor(propEditorClass, propValue);
		} else if(propType.equals(Boolean.class) || propType.equals(Boolean.TYPE)) {
			if(propValue.equalsIgnoreCase("on") ||
					propValue.equalsIgnoreCase("true"))
				propValue = "true";
			else
				propValue = "false";

			return new Boolean(propValue);

		} else if(propType.equals(Byte.class) || propType.equals(Byte.TYPE)) {
			return new Byte(propValue);
		} else if(propType.equals(Character.class) ||
				propType.equals(Character.TYPE)) {
			return propValue.length() > 0 ?
					new Character(propValue.charAt(0)) : null;
		} else if(propType.equals(Short.class) ||
				propType.equals(Short.TYPE)) {
			return new Short(propValue);
		} else if(propType.equals(Integer.class) ||
				propType.equals(Integer.TYPE)) {
			return new Integer(propValue);
		} else if(propType.equals(Float.class) || propType.equals(Float.TYPE)) {
			return new Float(propValue);
		} else if(propType.equals(Long.class) || propType.equals(Long.TYPE)) {
			return new Long(propValue);
		} else if(propType.equals(Double.class) ||
				propType.equals(Double.TYPE)) {
			return new Double(propValue);
		} else if(propType.equals(String.class)) {
			return propValue;
		} else if(propType.equals(java.io.File.class)) {
			return new java.io.File(propValue);
		} else if(propType.getName().equals("java.lang.Object")) {
			return new Object[] { propValue };
		} else {
			return getValueFromPropertyEditorManager(propType, propValue);
		}
	}

	public static Object getValueFromBeanInfoPropertyEditor (
			Class<?> propertyEditorClass, String attrValue) throws Exception {

		PropertyEditor propEditor =
				(PropertyEditor)propertyEditorClass.newInstance();
		propEditor.setAsText(attrValue);
		return propEditor.getValue();
	}

	public static Object getValueFromPropertyEditorManager(
			Class<?> attrType, String attrValue) {

		PropertyEditor propEditor = PropertyEditorManager.findEditor(attrType);
		if(propEditor != null) {
			propEditor.setAsText(attrValue);
			return propEditor.getValue();
		} else {
			throw new IllegalArgumentException( 
				"Property Editor not registered with the PropertyEditorManager");
		}
	}

}
