<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="xml" version="1.0" indent="yes"/>
<xsl:template match="/">
  <xconfig>
    <records>
    <xsl:apply-templates select="/xconfig/records/record">
      <xsl:sort select="fieldname" data-type = "text" order="ascending"/>
    </xsl:apply-templates>
    </records>
  </xconfig>
</xsl:template>
<xsl:template match="/xconfig/records/record">
  <xsl:copy-of select="."/>
</xsl:template>

</xsl:stylesheet>