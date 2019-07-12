package javamemory;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import lombok.extern.java.Log;
@Log
public class TestJavaReferences {
	public static void main(String[] args) throws InterruptedException{
		TestJavaReferences testRef=new TestJavaReferences();
		testRef.testWeakRef();
		testRef.testSoftRef();
	}
	public void testWeakRef() throws InterruptedException {
		log.info("Testing WeakReferences");
        // Initial Strong Ref
        Object obj = new Object();  

        log.info("Instance : " + obj);

        // Create a Weak Ref on obj
        WeakReference<Object> weakRef 
                  = new WeakReference<Object>(obj);
        
        // Make obj eligible for GC !
        obj = null;     
        TimeUnit.SECONDS.sleep(10);
        
        // Get a strong reference again. Now its not eligible for GC
        Object strongRef = weakRef.get();  

        log.info("(After delaying..check) Instance : " + strongRef);

        // Make the instance eligible for GC again
        strongRef = null;

        // Keep your fingers crossed    
        System.gc();    

        // should be null if GC collected
        log.info("Instance after GC: " + weakRef.get()); 
    }
	
	public void testSoftRef(){
		  // Initial Strong Ref
        Object obj = new Object();  
        log.info("Instance : " + obj);
        
       // Make a Soft Reference on obj
        SoftReference<Object> softReference = 
                    new SoftReference<Object>(obj); 

        // Make obj eligible for GC !
        obj = null;     
        
        System.gc();    // Run GC

        // should be null if GC collected
        log.info("Instance : " + softReference.get());
	}
}
