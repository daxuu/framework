<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <!--<xsl:output method="xml" version="1.0" indent="yes"/>-->
<xsl:template match="/">
  <xmlconfig>
    <xsl:apply-templates select="/*/dsn[_datastatus=1 or _datastatus=2 or _datastatus=3]">
    </xsl:apply-templates>
    <xsl:apply-templates select="/*/fileinfo">
    </xsl:apply-templates>
  </xmlconfig>
</xsl:template>
<xsl:template match="/*/dsn">
  <!--child::node()[@chg='1']-->
  <xsl:copy-of select="."/>
</xsl:template>
  <xsl:template match="/*/fileinfo">
    <xsl:copy-of select="."/>
  </xsl:template>
</xsl:stylesheet>