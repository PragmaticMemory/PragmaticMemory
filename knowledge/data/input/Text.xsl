<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:msxsl="urn:schemas-microsoft-com:xslt">
<xsl:strip-space elements="*"/>
<xsl:output method="text"/>
<xsl:include href="Common.xsl"/>
    <!--........................................................................-->
    <!-- Templates de media = template correspondant au language de destination -->
    <!--........................................................................-->
    <xsl:template match="list">
        <xsl:call-template name="iter-text">
            <xsl:with-param name="level" select="@level"/>
            <xsl:with-param name="text" select="' '"/><!-- l'espace est entre ' ' car c'est un littéral -->
        </xsl:call-template>
        <xsl:text>- </xsl:text>
        <xsl:apply-templates/>
        <xsl:value-of select="$newline"/>
    </xsl:template>

    <xsl:template match="header">
        <xsl:call-template name="iter-text">
            <xsl:with-param name="level" select="@level"/>
            <xsl:with-param name="text" select="'='"/>
        </xsl:call-template>
        <xsl:text>> </xsl:text>
        <xsl:apply-templates/>
        <xsl:text> :</xsl:text>
        <xsl:value-of select="$newline"/>
    </xsl:template>

     <xsl:template match="codeLine">
        <xsl:call-template name="iter-text">
            <xsl:with-param name="level" select="@level"/>
            <xsl:with-param name="text" select="'  '"/><!-- les espaces sont entre ' ' car c'est un littéral -->
        </xsl:call-template>
        <xsl:apply-templates/>
        <xsl:value-of select="$newline"/>
    </xsl:template>
    
    <xsl:template match="newLine">
        <xsl:value-of select="$newline"/>
    </xsl:template>

    <xsl:template match="note">
        <xsl:text> (</xsl:text>
        <xsl:apply-templates/>
        <xsl:text>)</xsl:text>
    </xsl:template>

    <xsl:template match="row">
        <xsl:text>-</xsl:text><xsl:apply-templates/><xsl:value-of select="$newline"/>
    </xsl:template>

    <xsl:template match="row/cell">
        <xsl:apply-templates/><xsl:text>;</xsl:text>
    </xsl:template>

    <xsl:template match="headerRow">
        <xsl:text>-</xsl:text><xsl:apply-templates/><xsl:value-of select="$newline"/>
    </xsl:template>

    <xsl:template match="headerRow/cell">
        <xsl:apply-templates/><xsl:text>;</xsl:text>
    </xsl:template>
</xsl:stylesheet>