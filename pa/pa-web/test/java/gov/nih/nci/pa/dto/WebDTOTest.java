package gov.nih.nci.pa.dto;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;



public class WebDTOTest {

	private static final Map<String, Comparable> DEFAULT_ARGUMENTS = new HashMap<String, Comparable>();

	@Test
	public void testProperties() {
		createDefaultArguments();
	  assertGetterSetterBehavior(new ContactWebDTO());
	  assertGetterSetterBehavior(new DiseaseWebDTO());
	  assertGetterSetterBehavior(new GeneralTrialDesignWebDTO());
	  assertGetterSetterBehavior(new InterventionWebDTO());
//	  assertGetterSetterBehavior(new ISDesignDetailsWebDTO());
	  assertGetterSetterBehavior(new InterventionalStudyProtocolWebDTO());
	  assertGetterSetterBehavior(new NCISpecificInformationWebDTO());
	  assertGetterSetterBehavior(new OSDesignDetailsWebDTO());
      assertGetterSetterBehavior(new RegulatoryAuthorityWebDTO());
      assertGetterSetterBehavior(new StudyIndldeWebDTO());
      assertGetterSetterBehavior(new StudyParticipationWebDTO());
      assertGetterSetterBehavior(new StudyProtocolWebDTO());
      assertGetterSetterBehavior(new StudyResourcingWebDTO());
      assertGetterSetterBehavior(new SubGroupsWebDTO());
      assertGetterSetterBehavior(new TrialArmsWebDTO());
//      assertGetterSetterBehavior(new TrialDescriptionWebDTO());
      assertGetterSetterBehavior(new TrialDocumentWebDTO());
      assertGetterSetterBehavior(new TrialFundingWebDTO());
     // assertGetterSetterBehavior(new TrialHistoryWebDTO());
	}

	/**
	 * The method introspects the target object, finding read/write properties, and tests the getter
	 * and setter.
	 * @param target the object on which to invoke the getter and setter
	 */
	public static void assertGetterSetterBehavior(Object target) {
	    try {
	        BeanInfo beanInfo = Introspector.getBeanInfo(target.getClass());
	        PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
	        for (PropertyDescriptor descriptor : descriptors) {
	            if (descriptor.getWriteMethod() == null) {
	                continue;
	            }
	            assertGetterSetterBehavior(target, descriptor.getDisplayName(),null);
	        }
	    }
	    catch (IntrospectionException e) {
	        fail("Failed while introspecting target " + target.getClass());
	    }
	}
	/**
	 * The method accepts an explicit argument for the setter method.
	 *
	 * @param target   the object on which to invoke the getter and setter
	 * @param property the property name, e.g. "firstName"
	 * @param argument the property value, i.e. the value the setter will be invoked with
	 */
	public static void assertGetterSetterBehavior(Object target, String property, Object argument) {
	    try {
	        PropertyDescriptor descriptor = new PropertyDescriptor(property, target.getClass());
	        Method writeMethod = descriptor.getWriteMethod();
	        Method readMethod = descriptor.getReadMethod();
	        Object arg = argument;

	        if (arg == null) {
	            Class<?> type = descriptor.getPropertyType();
	            if (DEFAULT_ARGUMENTS.containsKey(type.getName())) {
	                arg = DEFAULT_ARGUMENTS.get(type.getName());
	            }
	        }
   	        writeMethod.invoke(target, arg);
	        Object propertyValue = readMethod.invoke(target);
	        assertSame(property + " getter/setter test passed", arg, propertyValue);

	    }
	    catch (IntrospectionException e) {
	        String msg = "Error creating PropertyDescriptor for property [" + property +
	                "]. Do you have a getter and a setter?";

	        fail(msg);
	    }
	    catch (IllegalAccessException e) {
	        String msg = "Error accessing property. Are the getter and setter both accessible?";

	        fail(msg);
	    }
	    catch (InvocationTargetException e) {
	        String msg = "Error invoking method on target";
	        fail(msg);

	    }
	}


	/**
	 * creates default types and arguments.
	 */
	private static void createDefaultArguments(){
		Long default_long=1l;
		Boolean default_bool= true;
		DEFAULT_ARGUMENTS.put("java.lang.String", "test");
		DEFAULT_ARGUMENTS.put("java.lang.Long", default_long);
		DEFAULT_ARGUMENTS.put("java.lang.Boolean", default_bool);
	}

}
