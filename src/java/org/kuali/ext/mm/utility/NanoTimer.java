/**
 * 
 */
package org.kuali.ext.mm.utility;

/**
 * @author schneppd
 *
 */
public class NanoTimer {
    
    private long startTime;
    
    private long startSplit;
    
    private String label;

    public NanoTimer(String label) {
        this.label = label;
    }
    
    public NanoTimer start() {
        this.startTime = System.nanoTime();
        this.startSplit = this.startTime;
        return this;
    }
    
    public void stop() {
        System.out.println(label + ": " + ((System.nanoTime() - startTime)*1E-6));
    }
    
    public void split(String splitLabel) {
        System.out.println(splitLabel + ": " + ((System.nanoTime() - startSplit)*1E-6));
        this.startSplit = System.nanoTime();
    }
}
