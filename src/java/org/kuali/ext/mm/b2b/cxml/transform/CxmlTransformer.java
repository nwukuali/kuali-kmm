/**
 *
 */
package org.kuali.ext.mm.b2b.cxml.transform;

/**
 * @author harsha07
 */
public interface CxmlTransformer<Target, Source> {
    /**
     *
     */
    public Target transform(Source obj, Object... options);
}
