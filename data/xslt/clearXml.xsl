<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0"  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <!--<xsl:output method="xml" version="1.0" indent="yes"/>-->
  <xsl:template match="node()">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/>
    </xsl:copy>
  </xsl:template>
    <xsl:template match="@*">
    <!--不需要任何属性
    <xsl:attribute namespace="{namespace-uri()}" name="{name()}"/>
    -->
    </xsl:template>
  <!--清除内容-->
  <xsl:template match="text()"/>
  
</xsl:stylesheet>