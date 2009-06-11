package gov.nih.nci.pa.pdq;

import java.util.ArrayList;


/**
 * @author hreinhart
 *
 */
public enum Rule {
    /**
     * PDQ file does not match any of the accepted formats.
     */
    INVALID(null), 

    /**
     * XML record has TermTypeName item, it is semantic type or obsolete term.
     * TermTypeName is ‘Semantic type’ and  PreferredName is in the list.
     */
    RULE1(PDQDisease.class),

    /**
     * XML record has TermTypeName item, it is semantic type or obsolete term.
     * TermTypeName is ‘Semantic type’ and  PreferredName is NOT in the list.
     */
    RULE2(PDQIntervention.class),

    /**
     * XML record has TermTypeName item, it is semantic type or obsolete term.
     * TermTypeName is ‘Obsolete term’.
     */
    RULE3(PDQIntervention.class),

    /**
     * XML record has SemanticType item, it is a disease or intervention record.
     * SemanticType is in the list.
     */
    RULE4(PDQDisease.class),

    /**
     * XML record has SemanticType item, it is a disease or intervention record.
     * SemanticType is not in the list.
     */
    RULE5(PDQIntervention.class);
    
    @SuppressWarnings("unchecked")
    Class clazz;

    public static ArrayList<String> diseaseList;
    static {
        diseaseList = new ArrayList<String>();
        diseaseList.add("Disease/diagnosis");
        diseaseList.add("Cancer diagnosis");
        diseaseList.add("Cancer stage");
        diseaseList.add("Cancer grade");
        diseaseList.add("Genetic condition");
        diseaseList.add("Secondary related condition");
    }
    
    /**
     * @param clazz
     */
    @SuppressWarnings("unchecked")
    private Rule(Class clazz) {
        this.clazz = clazz;
    }
    
    public static final String TERM_TYPE_SEMANTIC = "Semantic type";
    public static final String TERM_TYPE_OBSOLETE = "Obsolete term";
    
    public static final String NODE_NAME_PREFERRED_NAME = "PreferredName";
    public static final String NODE_NAME_DEFINITION = "Definition";
    public static final String NODE_NAME_DEFINITION_TEXT = "DefinitionText";
    public static final String NODE_NAME_OTHER_NAME = "OtherName";
    public static final String NODE_NAME_OTHER_TERM_NAME = "OtherTermName";
    public static final String NODE_NAME_TERM = "Term";
    public static final String NODE_NAME_TERM_TYPE_NAME = "TermTypeName";
    public static final String NODE_NAME_SEMANTIC_TYPE = "SemanticType";
    public static final String NODE_NAME_TERM_RELATIONSHIP = "TermRelationship";
    public static final String NODE_NAME_PARENT_TERM = "ParentTerm";
    public static final String NODE_NAME_PARENT_TERM_NAME = "ParentTermName";
    public static final String NODE_NAME_PARENT_TYPE = "ParentType";
    public static final String NODE_NAME_MENU_INFO = "MenuInformation";
    public static final String NODE_NAME_MENU_ITEM = "MenuItem";
    public static final String NODE_NAME_MENU_DISPLAY_NAME = "DisplayName";
    
    public static final String ATTR_NAME_NCI_TERM = "NCIThesaurusConceptID";
    public static final String ATTR_NAME_REF = "ref";
    public static final String ATTR_NAME_ID = "id";
    
    
}
