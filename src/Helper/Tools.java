package Helper;

public class Tools {

	
    public static String SafeJSON(String sIn)
    {
        StringBuilder sbOut = new StringBuilder(sIn.length());
        
        char[] chars=sIn.toCharArray(); 
        for(char ch:chars){ 
            //if (char.IsControl(ch) || ch == '\'')
            if (ch == '\'')            	
            {
                int ich = (int)ch;
                //sbOut.Append("\\u" + ich.toString("x4"));
                sbOut.append("\\u");
                continue;
            }
            else if (ch == '\"' || ch == '\\' || ch == '/')
            {
                sbOut.append('\\');
            }
            sbOut.append(ch);
        } 
 
        return sbOut.toString();
    }
}
