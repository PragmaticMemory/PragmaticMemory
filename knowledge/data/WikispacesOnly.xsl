<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:strip-space elements="*"/>
<xsl:output method="text" omit-xml-declaration="yes"/>
<xsl:include href="Common2.xsl"/>
    <!--........................................................................-->
    <!-- Templates de media = template correspondant au language de destination -->
    <!--........................................................................-->

    <xsl:variable name="newline">
<xsl:text>
</xsl:text>
    </xsl:variable>

    <xsl:template match="list">
        <xsl:call-template name="iter-text">
            <xsl:with-param name="level" select="@level"/>
            <xsl:with-param name="text" select="'*'"/><!-- * est entre ' ' car c'est un littéral -->
        </xsl:call-template>
        <xsl:text> </xsl:text>
        <xsl:apply-templates/>
        <xsl:value-of select="$newline"/>
    </xsl:template>

    <xsl:template match="header">
        <xsl:call-template name="iter-text">
            <xsl:with-param name="level" select="@level"/>
            <xsl:with-param name="text" select="'='"/>
        </xsl:call-template>
        <xsl:apply-templates/>
        <xsl:call-template name="iter-text">
            <xsl:with-param name="level" select="@level"/>
            <xsl:with-param name="text" select="'='"/>
        </xsl:call-template>
        <xsl:value-of select="$newline"/>
    </xsl:template>

     <xsl:template match="code">
        <xsl:text>[[code</xsl:text>
        <xsl:if test="@lang"><xsl:text> format="</xsl:text><xsl:value-of select="@lang"/><xsl:text>"</xsl:text></xsl:if>
        <xsl:text>]]</xsl:text><xsl:value-of select="$newline"/>
        <xsl:apply-templates/>
        <xsl:text>[[code]]</xsl:text>
    </xsl:template>

    <xsl:template match="codeLine">
        <xsl:call-template name="iter-text">
            <xsl:with-param name="level" select="@level"/>
            <xsl:with-param name="text" select="'  '"/><!-- les espaces sont entre ' ' car c'est un littéral -->
        </xsl:call-template>
        <xsl:value-of select="."/>
        <xsl:value-of select="$newline"/>
    </xsl:template>

    <xsl:template match="technic">
        <xsl:text>{{</xsl:text>
        <xsl:apply-templates/>
        <xsl:text>}}</xsl:text>
    </xsl:template>

    <xsl:template match="link">
        <xsl:text>[[</xsl:text>
        <xsl:apply-templates/>
        <xsl:text>]]</xsl:text>
    </xsl:template>

    <xsl:template match="underline">
        <xsl:text>__</xsl:text>
        <xsl:apply-templates/>
        <xsl:text>__</xsl:text>
    </xsl:template>

    <xsl:template match="note">
        <xsl:text> (</xsl:text>
        <xsl:apply-templates/>
        <xsl:text>)</xsl:text>
    </xsl:template>

    <xsl:template match="newLine">
        <xsl:value-of select="$newline"/>
    </xsl:template>

    <xsl:template match="colored">
        <xsl:text><![CDATA[<span style="background-color: #a5ddf8;">]]></xsl:text>
        <xsl:apply-templates/>
        <xsl:text><![CDATA[</span>]]></xsl:text>
    </xsl:template>

     <xsl:template match="endOfSection">
        <xsl:text>----</xsl:text><xsl:value-of select="$newline"/>
    </xsl:template>

    <xsl:template match="tableOfContent">
        <xsl:text>[[toc|flat]]</xsl:text><xsl:value-of select="$newline"/>
    </xsl:template>

    <xsl:template match="row">
        <xsl:text>||</xsl:text><xsl:apply-templates/><xsl:value-of select="$newline"/>
    </xsl:template>

    <xsl:template match="row/cell">
        <!-- une ligne de table doit se terminer par "||", qui ne doit PAS être suivi par un espace -->
        <!-- l'espace avant le contenu de la cellule et avant les "||" sont indispensable pour une bonne gestion de l'imbrication des tables et du code -->
        <xsl:text> </xsl:text><xsl:apply-templates/><xsl:text> ||</xsl:text>
    </xsl:template>

    <xsl:template match="headerRow">
        <xsl:text>||</xsl:text><xsl:apply-templates/><xsl:value-of select="$newline"/>
    </xsl:template>

    <xsl:template match="headerRow/cell">
        <xsl:text> **</xsl:text><xsl:apply-templates/><xsl:text>** ||</xsl:text>
    </xsl:template>

</xsl:stylesheet>