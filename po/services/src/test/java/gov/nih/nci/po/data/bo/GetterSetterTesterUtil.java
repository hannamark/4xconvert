/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The po
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This po Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the po Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the po Software; (ii) distribute and
 * have distributed to and by third parties the po Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.po.data.bo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * @author Jevon Gill
 */
public class GetterSetterTesterUtil {

    private static final Logger LOG = Logger
            .getLogger(GetterSetterTesterUtil.class);

    public static final ArrayList<Object> DEFAULT_TYPES = new ArrayList<Object>();
    public static final ArrayList<Object> DEFAULT_ARGUMENTS = new ArrayList<Object>();

    static {

        /**************  Java Primitive Type to Sample Data mapping ***************/
        DEFAULT_TYPES.add(ArrayList.class);
        DEFAULT_ARGUMENTS.add(new ArrayList<Object>());

        DEFAULT_TYPES.add(HashMap.class);
        DEFAULT_ARGUMENTS.add(new HashMap<Object, Object>());

        DEFAULT_TYPES.add(String.class);
        DEFAULT_ARGUMENTS.add("test");

        DEFAULT_TYPES.add(int.class);
        DEFAULT_ARGUMENTS.add(23);

        DEFAULT_TYPES.add(long.class);
        DEFAULT_ARGUMENTS.add(23);

        DEFAULT_TYPES.add(double.class);
        DEFAULT_ARGUMENTS.add(23);

        DEFAULT_TYPES.add(boolean.class);
        DEFAULT_ARGUMENTS.add(false);

        DEFAULT_TYPES.add(List.class);
        DEFAULT_ARGUMENTS.add(new ArrayList<Object>());

        DEFAULT_TYPES.add(byte.class);
        DEFAULT_ARGUMENTS.add(100);

        DEFAULT_TYPES.add(char.class);
        DEFAULT_ARGUMENTS.add('t');

        DEFAULT_TYPES.add(float.class);
        DEFAULT_ARGUMENTS.add(20.3);

        DEFAULT_TYPES.add(short.class);
        DEFAULT_ARGUMENTS.add(10);

        /**************  Java Object Type to Sample Data mapping ***************/

        DEFAULT_TYPES.add(Long.class);
        DEFAULT_ARGUMENTS.add(new Long(20));

        DEFAULT_TYPES.add(Double.class);
        DEFAULT_ARGUMENTS.add(new Double(20.0));

        DEFAULT_TYPES.add(Integer.class);
        DEFAULT_ARGUMENTS.add(new Integer(20));

        DEFAULT_TYPES.add(Set.class);
        DEFAULT_ARGUMENTS.add(new HashSet<Object>());

        DEFAULT_TYPES.add(Boolean.class);
        DEFAULT_ARGUMENTS.add(true);

        DEFAULT_TYPES.add(EntityStatus.class);
        EntityStatus cs = EntityStatus.NULLIFIED;
        DEFAULT_ARGUMENTS.add(cs);

        DEFAULT_TYPES.add(Person.class);
        DEFAULT_ARGUMENTS.add(new Person());
    }

    /**
     * See {@link #assertBasicGetterSetterBehavior(Object,String)} method. Only difference is that here we accept an
     * explicit argument for the setter method.
     *
     * @param target   the object on which to invoke the getter and setter
     * @param property the property name, e.g. "firstName"
     */
    public static void assertBasicGetterSetterBehavior(Object target,
            String property) {
        try {

            PropertyDescriptor descriptor = new PropertyDescriptor(property,
                    target.getClass());
            assertBasicGetterSetterBehavior(target, descriptor);
        } catch (IntrospectionException e) {
            String msg = "Error creating PropertyDescriptor for property ["
                    + property + "]. Do you have a getter and a setter?";
            LOG.error(msg, e);
            fail(msg);
        }
    }

    private static void assertBasicGetterSetterBehavior(Object target,
            PropertyDescriptor descriptor) {
        String property = descriptor.getDisplayName();
        try {

            LOG.debug("Testing property: " + descriptor.getDisplayName());

            Object arg = new Object();

            Class<?> type = descriptor.getPropertyType();
            LOG.debug("Testing property: " + descriptor.getDisplayName() + "; type=" + type.getName());

            if (DEFAULT_TYPES.contains(type)) {
                arg = DEFAULT_ARGUMENTS.get(DEFAULT_TYPES.indexOf(type));
            } else {
                try {

                    arg = type.newInstance();
                } catch (InstantiationException e) {
                    String msg = "Error instantiating property [" + property
                            + "].Is this a property of a custom type?";
                    LOG.error(msg, e);
                    fail(msg);
                }
            }

            LOG.debug("Attempting to perform get/set for property:" + descriptor.getDisplayName());
            Method readMethod = descriptor.getReadMethod();
            Method writeMethod = descriptor.getWriteMethod();
            writeMethod.invoke(target, arg);
            Object propertyValue = readMethod.invoke(target);
            assertEquals(property + " getter/setter failed test", arg,
                    propertyValue);
            LOG.debug("Completed attempt to perform get/set for property:" + descriptor.getDisplayName());
            LOG.debug("\n");

        } catch (IllegalAccessException e) {
            String msg = "Error accessing property. Are the getter and setter both accessible?";
            LOG.error(msg, e);
            fail(msg);
        } catch (InvocationTargetException e) {
            String msg = "Error invoking method on target";
            fail(msg);
            LOG.error(msg, e);
        }

    }

    /**
     * See {@link #assertBasicGetterSetterBehavior(Object,String)} method. Big difference here is that we try to
     * automatically introspect the target object, finding read/write properties, and automatically testing the getter
     * and setter. Note specifically that read-only properties are ignored, as there is no way for us to know how to set
     * the value (since there isn't a public setter).
     * <p/>
     * Note also that array properties are ignored.
     *
     * @param target the object on which to invoke the getter and setter
     */
    public static void assertBasicGetterSetterBehavior(Object target) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(target.getClass());
            PropertyDescriptor[] descriptors = beanInfo
                    .getPropertyDescriptors();
            for (PropertyDescriptor descriptor : descriptors) {
                if (descriptor.getWriteMethod() == null) {
                    continue;
                }
                if (descriptor.getPropertyType().isArray()) {
                    continue;
                }
                assertBasicGetterSetterBehavior(target, descriptor);
            }
        } catch (IntrospectionException e) {
            fail("Failed while introspecting target " + target.getClass());
        }
    }
}
