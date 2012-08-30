<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:strip-space elements="*"/>
<xsl:output method="html"/>
    <!--...........................................................................-->
    <!-- Templates de language = template correspondant au language de destination -->
    <!--...........................................................................-->

   <!-- Template permettant de répéter plusieurs fois une même séquence de caractères -->
    <xsl:template name="iter-text">
        <xsl:param name="level" select="1"/>
        <xsl:param name="text"/>
        <xsl:copy-of select="$text"/>
        <xsl:if test="$level &gt; 1">
            <xsl:call-template name="iter-text"><!-- la récursivité est utilisée pour boucler sur l'entier level -->
                <xsl:with-param name="level" select="$level - 1"/>
                <xsl:with-param name="text" select="$text"/>
            </xsl:call-template>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>