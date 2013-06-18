<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:msxsl="urn:schemas-microsoft-com:xslt" exclude-result-prefixes="msxsl"
>      
  
  <xsl:template match="/">
    <html xmlns="http://www.w3.org/1999/xhtml">
        <head>
          <title>export data</title>
        </head>
     <body>
       <div>
    <xsl:for-each select="/xconfig/records">
       
        <table cellpadding="0" cellspacing="0" border="0" frame="border" width="100%" style="font-size: 10pt">
        <tr>
          <td>
            <table cellpadding="8" cellspacing="0" border="1" frame="border" width="100%">
              <thead>
              <tr>
              </tr>
              </thead>
              <tbody>
              <xsl:for-each  select="record">
              <tr>
              </tr>
            </xsl:for-each>
              </tbody>
              </table>
          </td>
        </tr>
      </table>
    </xsl:for-each>
         </div>
     </body>
   </html>
 
  </xsl:template>
 

 </xsl:stylesheet>
