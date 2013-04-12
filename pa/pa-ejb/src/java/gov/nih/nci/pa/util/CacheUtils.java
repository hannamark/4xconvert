/**
 * 
 */
package gov.nih.nci.pa.util;

import gov.nih.nci.pa.service.PAException;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * Methods to interact with EhCache-backed cache.
 * 
 * @author Denis G. Krylov
 * 
 */
public final class CacheUtils {

    private static final CacheManager CACHE_MANAGER = CacheManager.create();

    private static final String CRITERIA_COLLECTIONS_CACHE_KEY = "CRITERIA_COLLECTIONS_CACHE";
    private static final String SEARCH_RESULTS_CACHE_KEY = "SEARCH_RESULTS_CACHE";
    private static final String SUBMITTER_REGISTRY_USERS_KEY = "SUBMITTER_REGISTRY_USERS";
    private static final String CADSR_CLASSIFICATION_SCHEMES_KEY = "CADSR_CLASSIFICATION_SCHEMES";

    /**
     * Cache used for storing criteria's referenced collections, usually Lead
     * Orgs and Investigators on Search Trial UI.
     * 
     * @return Cache
     */
    public static Cache getCriteriaCollectionsCache() {
        return CACHE_MANAGER.getCache(CRITERIA_COLLECTIONS_CACHE_KEY);

    }

    /**
     * Cache used for storing search results, usually Trial Search results.
     * 
     * @return Cache
     */
    public static Cache getSearchResultsCache() {
        return CACHE_MANAGER.getCache(SEARCH_RESULTS_CACHE_KEY);
    }
    
    /**
     * Cache used for storing Submitters.
     * 
     * @return Cache
     */
    public static Cache getSubmittersCache() {
        return CACHE_MANAGER.getCache(SUBMITTER_REGISTRY_USERS_KEY);
    }
    
    /**
     * Cache used for storing caDSR classification schemes.
     * 
     * @return Cache
     */
    public static Cache getCaDSRClassificationSchemesCache() {
        return CACHE_MANAGER.getCache(CADSR_CLASSIFICATION_SCHEMES_KEY);
    }

    /**
     * Attempts to find the given item in the given cache. If not found,
     * retrieves the item from the backend using the given {@link Closure} and
     * stores the result in the cache.
     * 
     * @param cache
     *            Cache
     * @param elementKey
     *            elementKey
     * @param backendAccessClosure
     *            Closure
     * @return Object
     * @throws PAException PAException
     */
    public static Object getFromCacheOrBackend(Cache cache, String elementKey,
            Closure backendAccessClosure) throws PAException {
        Element element = cache.get((Object) elementKey);
        if (element != null) {
            Object cachedObj = element.getObjectValue();
            if (cachedObj != null) {
                return cachedObj;
            }
        }
        Object result = backendAccessClosure.execute();
        if (result != null) {
            element = new Element(elementKey, result);
            cache.put(element);
        }
        return result;

    }
    
    /**
     * Removes item from cache.
     * 
     * @param cache
     *            Cache
     * @param elementKey
     *            elementKey
     */
    public static void removeItemFromCache(Cache cache, String elementKey) {
        cache.remove((Object) elementKey);
    }

    /**
     * Closure used in
     * {@link CacheUtils#getFromCacheOrBackend(Cache, String, Closure)}.
     * 
     * @author Denis G. Krylov
     * 
     */
    public interface Closure {
        /**
         * Executes and results a result.
         * 
         * @return Object result.
         * @throws PAException
         *             PAException
         */
        Object execute() throws PAException;
    }

}
