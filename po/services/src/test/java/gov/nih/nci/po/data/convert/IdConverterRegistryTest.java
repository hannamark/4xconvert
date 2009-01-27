/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The COPPA PO
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This COPPA PO Software License (the License) is between NCI and You. You (or
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
 * its rights in the COPPA PO Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the COPPA PO Software; (ii) distribute and
 * have distributed to and by third parties the COPPA PO Software and any
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
package gov.nih.nci.po.data.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.data.bo.Organization;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.junit.Test;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;

public class IdConverterRegistryTest {
    @Test(expected = IllegalArgumentException.class)
    public void unknownClass() {
        IdConverterRegistry.find(Foo.class);
    }

    @Test
    public void knownClass() {
        assertEquals(IdConverter.OrgIdConverter.class, IdConverterRegistry.find(Organization.class).getClass());
    }

    private static class Foo implements PersistentObject {
        private static final long serialVersionUID = 1L;

        public Long getId() {
            return null;
        }
    }

    private static final String PACKAGENAME = "gov.nih.nci.po.data.convert";
    private static final Class<IdConverterRegistryTest> THIS_CLZ = IdConverterRegistryTest.class;

    @Test
    public void ensureAllSubclassesOfIdConverterAreInRegistry() throws ClassNotFoundException {

        List<Class<?>> classesForPackage = getClassesForPackage(PACKAGENAME);
        assertNotNull(classesForPackage);
        assertFalse(classesForPackage.isEmpty());

        for (Class<?> clz : classesForPackage) {
            if (clz.getName().endsWith("Test")) {
                continue;
            }
            if (clz.isAnonymousClass()) {
                continue;
            }
            if (IdConverter.class.isAssignableFrom(clz) && !clz.equals(IdConverter.class)) {
                boolean found = false;
                for (IdConverter converter : IdConverterRegistry
                        .getRegistry().values()) {
                    if (converter.getClass().equals(clz)) {
                        found = true;
                        break;
                    }
                }
                assertTrue(clz.getName() + " is not listed in registry", found);
            }
        }
    }

    public static List<Class<?>> getClassesForPackage(String pckgname) throws ClassNotFoundException {
        // This will hold a list of directories matching the pckgname.
        // There may be more than one if a package is split over multiple jars/paths
        List<Class<?>> classes = new ArrayList<Class<?>>();
        ArrayList<File> directories = new ArrayList<File>();
        try {
            ClassLoader cld = Thread.currentThread().getContextClassLoader();
            if (cld == null) {
                throw new ClassNotFoundException("Can't get class loader.");
            }
            // Ask for all resources for the path
            Enumeration<URL> resources = cld.getResources(pckgname.replace('.', '/'));
            while (resources.hasMoreElements()) {
                URL res = resources.nextElement();
                URL turl = THIS_CLZ.getProtectionDomain().getCodeSource().getLocation();
                /* SMM - Added to exclude test related classes */
                if (res.toString().startsWith(turl.toString())) {
                    continue;
                }
                if (res.getProtocol().equalsIgnoreCase("jar")) {
                    JarURLConnection conn = (JarURLConnection) res.openConnection();
                    JarFile jar = conn.getJarFile();
                    for (JarEntry e : Collections.list(jar.entries())) {

                        if (e.getName().startsWith(pckgname.replace('.', '/')) && e.getName().endsWith(".class")
                                && !e.getName().contains("$")) {
                            String className = e.getName().replace("/", ".").substring(0, e.getName().length() - 6);
                            classes.add(Class.forName(className));
                        }
                    }
                } else {
                    directories.add(new File(URLDecoder.decode(res.getPath(), "UTF-8")));
                }
            }
        } catch (NullPointerException x) {
            throw new ClassNotFoundException(pckgname + " does not appear to be "
                    + "a valid package (Null pointer exception)");
        } catch (UnsupportedEncodingException encex) {
            throw new ClassNotFoundException(pckgname + " does not appear to be "
                    + "a valid package (Unsupported encoding)");
        } catch (IOException ioex) {
            throw new ClassNotFoundException("IOException was thrown when trying " + "to get all resources for "
                    + pckgname);
        }

        // For every directory identified capture all the .class files
        for (File directory : directories) {
            if (directory.exists()) {
                // Get the list of the files contained in the package
                String[] files = directory.list();
                for (String file : files) {
                    // we are only interested in .class files
                    if (file.endsWith(".class")) {
                        // removes the .class extension
                        classes.add(Class.forName(pckgname + '.' + file.substring(0, file.length() - 6)));
                    }
                }
            } else {
                throw new ClassNotFoundException(pckgname + " (" + directory.getPath()
                        + ") does not appear to be a valid package");
            }
        }
        return classes;
    }

}
