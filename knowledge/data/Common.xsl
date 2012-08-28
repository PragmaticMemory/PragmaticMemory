<?xml version="1.0"?>
<!DOCTYPE spaceDefinition[<!ENTITY space "&#160;" >]> 
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:msxsl="urn:schemas-microsoft-com:xslt">
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

    <!-- Template permettant de formatter les données pour un media donné -->
    <!-- le template "process" ne doit être appellé que sur des noeuds sur les enfants desquels doivent être appliqué uniquement template de langage mais pas de template de vue -->
    <xsl:template name="process">
        <xsl:param name="node" />
        <xsl:variable name="processedModifiedNode">
            <xsl:apply-templates select="msxsl:node-set($node)"/>
        </xsl:variable>
        <xsl:copy-of select="$processedModifiedNode"/>
    </xsl:template >

    <!-- Template d'appel du template "process"
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <xsl:copy-of select="."/>
            </xsl:with-param>
        </xsl:call-template>
    -->

</xsl:stylesheet>