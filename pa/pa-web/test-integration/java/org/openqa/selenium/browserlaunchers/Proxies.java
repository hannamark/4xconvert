/**
 * 
 */
package org.openqa.selenium.browserlaunchers;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Proxy;

/**
 * @see https://github.com/detro/ghostdriver/issues/397
 * @author dkrylov
 *
 */
public class Proxies {
    public static Proxy extractProxy(Capabilities capabilities) {
        return Proxy.extractFrom(capabilities);
    }
}