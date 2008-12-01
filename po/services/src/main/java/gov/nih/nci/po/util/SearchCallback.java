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
package gov.nih.nci.po.util;

import gov.nih.nci.po.service.AbstractBaseServiceBean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Callback implementation.
 */
@SuppressWarnings({"PMD.AvoidStringBufferField", "PMD.TooManyMethods" })
class SearchCallback implements SearchableUtils.AnnotationCallback {

    private final StringBuffer whereClause;
    private final StringBuffer selectClause;
    private final Map<String, Object> params;
    private String whereOrAnd = SearchableUtils.WHERE;

    /**
     * @param whereClause stringbuffer
     * @param selectClause stringbuffer
     * @param params params
     */
    public SearchCallback(StringBuffer whereClause, StringBuffer selectClause, Map<String, Object> params) {
        this.whereClause = whereClause;
        this.selectClause = selectClause;
        this.params = params;
    }

    @SuppressWarnings({ "PMD.SignatureDeclareThrowsException", "PMD.UseStringBufferForStringAppends" })
    public void callback(Method m, Object result) throws Exception {
        String fieldName = StringUtils.uncapitalize(m.getName().substring("get".length()));
        String paramName = fieldName;
        if (result == null) {
            return;
        }
        String[] fields = m.getAnnotation(Searchable.class).fields();
        String searchMethod = m.getAnnotation(Searchable.class).matchMode();
        boolean startsWithSearch = searchMethod.equals(Searchable.MATCH_MODE_START);

        if (result instanceof Collection<?>) {
            processCollectionField(result, fieldName, paramName, fields, startsWithSearch);
        } else if (fields != null && fields.length > 0) {
            processFieldWithSubProp(result, fieldName, paramName, fields, startsWithSearch);
        } else {
            processSimpleField(result, fieldName, paramName, startsWithSearch);
        }
    }

    private void processSimpleField(Object result, String fieldName, String paramName, boolean startsWithSearch) {
        if (isStringAndBlank(result)) {
            return;
        }
        whereClause.append(whereOrAnd);
        whereOrAnd = SearchableUtils.AND;

        if (startsWithSearch && canBeUsedInLikeExpression(result)) {
            whereClause.append(String.format("lower(%s.%s) like :%s", SearchableUtils.ROOT_OBJ_ALIAS, fieldName,
                    paramName));
            params.put(paramName, result.toString().toLowerCase() + "%");
        } else {
            whereClause.append(String.format("%s.%s = :%s", SearchableUtils.ROOT_OBJ_ALIAS, fieldName, paramName));
            params.put(paramName, result);
        }
    }

    private void processFieldWithSubProp(Object result, String fieldName, String paramName, String[] fields,
            boolean startsWithQuery) {
        for (String currentProp : fields) {
            String subPropParamName = paramName + currentProp;
            Object subPropResult = getSimpleProperty(result, currentProp);

            if (subPropResult != null) {
                if (isStringAndBlank(subPropResult)) {
                    continue;
                }
                handleProcessFieldWithSubProp(subPropResult, fieldName, startsWithQuery, currentProp, subPropParamName);
            }
        }
    }

    private boolean isStringAndBlank(Object subPropResult) {
        return canBeUsedInLikeExpression(subPropResult) && StringUtils.isBlank((String) subPropResult);
    }

    private Object getSimpleProperty(Object bean, String property) {
        Object subPropResult = null;
        try {
            subPropResult = PropertyUtils.getSimpleProperty(bean, property);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to process property with name:" + property, e);
        }
        return subPropResult;
    }

    @SuppressWarnings("PMD.ExcessiveParameterList")
    private void handleProcessFieldWithSubProp(Object subPropResult, String fieldName, boolean startsWithQuery,
            String currentProp, String subPropParamName) {
        whereClause.append(whereOrAnd);
        whereOrAnd = SearchableUtils.AND;
        
        if (startsWithQuery && canBeUsedInLikeExpression(subPropResult)) {
            whereClause.append(String.format(" lower(%s.%s.%s) like :%s ",
                    SearchableUtils.ROOT_OBJ_ALIAS, fieldName, currentProp, subPropParamName));
            params.put(subPropParamName, subPropResult.toString().toLowerCase() + "%");
        } else {
            whereClause.append(String.format(" %s.%s.%s = :%s ", SearchableUtils.ROOT_OBJ_ALIAS,
                    fieldName, currentProp, subPropParamName));
            params.put(subPropParamName, subPropResult);
        }
    }

    private void processCollectionField(Object result, String fieldName, String paramName, String[] fields,
            boolean startsWithSearch) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (fields == null || fields.length == 0) {
            throw new IllegalArgumentException("Can not use the searchable annotation on a collection without"
                    + " specifying at least one field name.");
        }

        Collection<?> col = (Collection<?>) result;
        if (col.isEmpty()) {
            // empty collection of values means no search criteria.
            return;
        }

        handleCollection(col, fieldName, paramName, fields, startsWithSearch);
    }

    @SuppressWarnings("PMD.ConsecutiveLiteralAppends")
    private void handleCollection(Collection<?> col, String fieldName, String paramName, String[] propNames,
            boolean startsWithSearch) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (col.size() > AbstractBaseServiceBean.MAX_IN_CLAUSE_SIZE) {
            throw new IllegalArgumentException(String.format("Cannot query on more than %s elements.",
                    AbstractBaseServiceBean.MAX_IN_CLAUSE_SIZE));
        }
        Class<? extends Object> fieldClass = col.iterator().next().getClass();
        String alias = " obj_" + fieldName;
        StringBuffer processCollectionPropertiesResult = processCollectionProperties(paramName, propNames, col,
                fieldClass, alias, startsWithSearch);
        if (processCollectionPropertiesResult.length() > 0) {

            whereClause.append(whereOrAnd);
            whereOrAnd = SearchableUtils.AND;

            // Need to add ", zClass obj_<field>" to select clause
            selectClause.append(", ");
            selectClause.append(fieldClass.getName());
            selectClause.append(alias);

            // now add the where clauses
            whereClause.append(String
                    .format("%s IN ELEMENTS(%s.%s) ", alias, SearchableUtils.ROOT_OBJ_ALIAS, fieldName));
            whereClause.append(SearchableUtils.AND);
            whereClause.append(" ( ");
            whereClause.append(processCollectionPropertiesResult);
            whereClause.append(" ) ");
        }
    }

    @SuppressWarnings("PMD.ExcessiveParameterList")
    private StringBuffer processCollectionProperties(String paramName, String[] propNames, Collection<?> col,
            Class<? extends Object> fieldClass, String alias, boolean startsWithSearch) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {
        StringBuffer result = new StringBuffer();
        String andClause = "";
        for (String propName : propNames) {
            Collection<Object> valueCollection = getCollectionValuesIntoNiceCollection(col, fieldClass, propName);

            if (!valueCollection.isEmpty()) {
                result.append(andClause);
                if (startsWithSearch) {
                    result.append(addLikeClausesForCollectionProp(paramName, alias, propName, valueCollection)
                            .toString());
                } else {
                    result.append(addInClauseForCollectionProp(paramName, alias, propName, valueCollection)
                            .toString());
                }
                andClause = SearchableUtils.AND;
            }
        }
        return result;
    }

    private Collection<Object> getCollectionValuesIntoNiceCollection(Collection<?> col,
            Class<? extends Object> fieldClass, String propName) throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException {
        // get the collection of values into a nice collection
        Method m2 = fieldClass.getMethod("get" + StringUtils.capitalize(propName));
        Collection<Object> valueCollection = new HashSet<Object>();
        for (Object collectionObj : col.toArray()) {
            Object val = m2.invoke(collectionObj);
            if (val != null) {
                if (isStringAndBlank(val)) {
                    continue;
                }
                valueCollection.add(val);
            }
        }
        return valueCollection;
    }

    private StringBuffer addLikeClausesForCollectionProp(String paramName, String alias, String propName,
            Collection<Object> valueCollection) {
        StringBuffer result = new StringBuffer();
        result.append(" ( ");
        int i = 0;
        for (Object val : valueCollection) {
            if (i != 0) {
                result.append(" OR ");
            }

            String subParamName = new String(paramName + propName + i).trim();

            if (canBeUsedInLikeExpression(val)) {
                result.append("lower(");
                result.append(alias);
                result.append('.');
                result.append(propName);
                result.append(") like :");
                result.append(subParamName);
                params.put(subParamName, val.toString().toLowerCase() + "%");
            } else {
                result.append(alias);
                result.append('.');
                result.append(propName);
                result.append(" = :");
                result.append(subParamName);
                params.put(subParamName, val);
            }
            i++;
        }
        result.append(" ) ");
        return result;
    }

    private StringBuffer addInClauseForCollectionProp(String paramName, String alias, String propName,
            Collection<Object> valueCollection) {
        StringBuffer result = new StringBuffer();
        result.append(alias);
        result.append('.');
        result.append(propName);

        String subParamName = new String(paramName + propName).trim();
        result.append(" IN (");
        result.append(String.format(":%s", subParamName));
        result.append(')');

        params.put(subParamName, valueCollection);
        return result;
    }

    public Object getSavedState() {
        return whereOrAnd;
    }

    private boolean canBeUsedInLikeExpression(Object o) {
        return o instanceof String;
    }
}
