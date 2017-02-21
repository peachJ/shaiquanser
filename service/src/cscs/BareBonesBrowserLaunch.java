package cscs;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JOptionPane;

public class BareBonesBrowserLaunch {  
	  
    public static void openURL(String url) {  
        try {  
            browse(url);  
        } catch (Exception e) {  
            JOptionPane.showMessageDialog(null, "Error attempting to launch web browser:\n" + e.getLocalizedMessage());  
        }  
    }  
  
    private static void browse(String url) throws ClassNotFoundException, IllegalAccessException,  
            IllegalArgumentException, InterruptedException, InvocationTargetException, IOException,  
            NoSuchMethodException {  
        String osName = System.getProperty("os.name", "");  
        if (osName.startsWith("Mac OS")) {  
            @SuppressWarnings("rawtypes")
			Class fileMgr = Class.forName("com.apple.eio.FileManager");  
            @SuppressWarnings("unchecked")
			Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[] { String.class });  
            openURL.invoke(null, new Object[] { url });  
        } else if (osName.startsWith("Windows")) {  
//            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);  
        	Runtime.getRuntime().exec("C:\\Program Files\\Internet Explorer\\iexplore " + url);

        } else { // assume Unix or Linux  
            String[] browsers = { "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape" };  
            String browser = null;  
            for (int count = 0; count < browsers.length && browser == null; count++)  
                if (Runtime.getRuntime().exec(new String[] { "which", browsers[count] }).waitFor() == 0)  
                    browser = browsers[count];  
            if (browser == null)  
                throw new NoSuchMethodException("Could not find web browser");  
            else  
                Runtime.getRuntime().exec(new String[] { browser, url });  
        }  
    }  
     
}  