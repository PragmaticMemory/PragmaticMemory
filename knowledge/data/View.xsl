<?xml version="1.0"?>
<!DOCTYPE biblio SYSTEM "entities.dtd">
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:msxsl="urn:schemas-microsoft-com:xslt">
<xsl:strip-space elements="*"/>
<xsl:output method="html"/>
    <!--..............................................................-->
    <!-- Templates de vues = templates définissant la vue des données -->
    <!--..............................................................-->

    <xsl:key name="metadata" match="metadata" use="@xpath"/>
    
    <!-- Root element -->
    <xsl:template match="//data">
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <tableOfContent/>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <endOfSection/>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:apply-templates/>
    </xsl:template>

    <!-- Dashboard -->
    <xsl:template match="data/dashboard">
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <header level="1">DASHBOARD</header>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:for-each select="group">
            <xsl:call-template name="process">
                <xsl:with-param name="node">
                    <header level="2"><xsl:copy-of select="name"/></header>
                </xsl:with-param>
            </xsl:call-template>
            <xsl:for-each select="task">
                <xsl:call-template name="process">
                    <xsl:with-param name="node">
                        <list level="1"><xsl:copy-of select="goal"/></list>
                    </xsl:with-param>
                </xsl:call-template>
                <xsl:for-each select="candidate">
                    <xsl:call-template name="process">
                            <xsl:with-param name="node">
                                <list level="2"><xsl:copy-of select="name"/></list>
                            </xsl:with-param>
                        </xsl:call-template>
                    <xsl:if test="advantage">
                        <xsl:call-template name="process">
                            <xsl:with-param name="node">
                                <list level="3">Avantages : </list>
                            </xsl:with-param>
                        </xsl:call-template>
                        <xsl:for-each select="advantage">
                            <xsl:call-template name="process">
                                <xsl:with-param name="node">
                                    <list level="4"><xsl:copy-of select="."/></list>
                                </xsl:with-param>
                            </xsl:call-template>
                        </xsl:for-each>
                    </xsl:if>
                    <xsl:if test="disadvantage">
                        <xsl:call-template name="process">
                            <xsl:with-param name="node">
                                <list level="3">Inconvénients : </list>
                            </xsl:with-param>
                        </xsl:call-template>
                        <xsl:for-each select="disadvantage">
                           <xsl:call-template name="process">
                                <xsl:with-param name="node">
                                    <list level="4"><xsl:copy-of select="."/></list>
                                </xsl:with-param>
                            </xsl:call-template>
                        </xsl:for-each>
                    </xsl:if>
                </xsl:for-each>
                <xsl:if test="selected">
                    <xsl:call-template name="process">
                        <xsl:with-param name="node">
                            <list level="2">Solution retenue : <xsl:copy-of select="selected"/></list>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>
            </xsl:for-each>
        </xsl:for-each>
        <xsl:for-each select="example">
            <xsl:call-template name="process">
                <xsl:with-param name="node">
                    <xsl:copy-of select="."/>
                    <newLine/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:for-each>
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <endOfSection/>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <!-- Principles -->
    <xsl:template match="data/principles">
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <header level="1">PRINCIPES DE REDACTION</header>
            </xsl:with-param>
        </xsl:call-template>
       <xsl:copy-of select="goal"/><br/>
       <xsl:for-each select="principle">
            <xsl:call-template name="process">
                <xsl:with-param name="node">
                    <header level="2"><xsl:copy-of select="name"/></header>
                </xsl:with-param>
            </xsl:call-template>
            <xsl:for-each select="item">
                <xsl:call-template name="process">
                    <xsl:with-param name="node">
                        <code><xsl:copy-of select="description"/><newLine/></code><newLine/>
                        <xsl:copy-of select="remark"/>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:for-each>
        </xsl:for-each>
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <endOfSection/>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>
    
    <!-- Sandbox -->
    <xsl:template match="data/sandbox">
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <header level="1">SANDBOX</header>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:for-each select="example">
            <xsl:call-template name="process">
                <xsl:with-param name="node">
                    <xsl:copy-of select="."/>
                    <newLine/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:for-each>
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <endOfSection/>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <!-- Powershell -->
    <xsl:template match="data/powershell">
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <header level="1">POWERSHELL</header>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <header level="2">Notation de types</header>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:for-each select="typeInformation/notation/item">
            <xsl:call-template name="process">
                <xsl:with-param name="node">
                    <list level="1"><xsl:copy-of select="type"/> : <xsl:copy-of select="description"/></list>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:for-each>
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <header level="2">Compatibilité entre types</header>
                <headerRow><cell>Type attendu</cell><cell>Type particulier utilisable</cell></headerRow>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:for-each select="typeInformation/compatibility/item">
            <xsl:call-template name="process">
                <xsl:with-param name="node">
                    <row><cell><xsl:copy-of select="expectedType"/></cell><cell><xsl:copy-of select="compatibleType"/></cell></row>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:for-each>
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <header level="2">Membres par type</header>                
            </xsl:with-param>
        </xsl:call-template>
        <xsl:for-each select="typeInformation/properties/type">
            <xsl:call-template name="process">
                <xsl:with-param name="node">
                    <list>Objet de type <xsl:copy-of select="name"/> : </list>
                </xsl:with-param>
            </xsl:call-template>
            <xsl:for-each select ="property">
                <xsl:call-template name="process">
                    <xsl:with-param name="node">
                        <row><cell><xsl:copy-of select="name"/></cell><cell><xsl:copy-of select="description"/></cell></row>
                </xsl:with-param>
            </xsl:call-template>
            </xsl:for-each>
        </xsl:for-each>
        <xsl:for-each select="group">
            <xsl:call-template name="process">
                <xsl:with-param name="node">
                    <header level="2"><xsl:copy-of select="name"/></header>
                </xsl:with-param>
            </xsl:call-template>
            <xsl:for-each select="goal">
                <xsl:call-template name="process">
                    <xsl:with-param name="node">
                        <list level="1"><underline><xsl:copy-of select="target"/></underline></list>
                    </xsl:with-param>
                </xsl:call-template>
                <xsl:for-each select="pipe">
                    <xsl:call-template name="process">
                        <xsl:with-param name="node">
                            <technic>
                                <xsl:copy-of select="source"/>
                                <xsl:text> > </xsl:text>
                                <colored><xsl:copy-of select="command"/></colored>
                                <xsl:text> > </xsl:text>
                                <xsl:copy-of select="target"/>
                            </technic>
                            <newLine/>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:for-each>
                <xsl:if test="alias">
                    <xsl:text>Alias : </xsl:text><br/>
                </xsl:if>
                <xsl:for-each select="alias">
                    <xsl:call-template name="process">
                        <xsl:with-param name="node">
                            <list level="2"><technic><xsl:copy-of select="."/></technic></list>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:for-each>
                 <xsl:if test="option">
                    <xsl:text>Options : </xsl:text><br/>
                </xsl:if>
                <xsl:for-each select="option">
                    <xsl:call-template name="process">
                        <xsl:with-param name="node">
                            <list level="2">
                                <technic><xsl:copy-of select="name"/></technic>
                                <note><technic><xsl:copy-of select="shortcut"/></technic></note>
                            </list>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:for-each>
                <xsl:if test="remark">
                    <xsl:text>Remarques : </xsl:text><br/>
                </xsl:if>
                <xsl:for-each select="remark">
                    <xsl:call-template name="process">
                        <xsl:with-param name="node">
                           <list level="2"><xsl:copy-of select="."/></list>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:for-each>
            </xsl:for-each>
        </xsl:for-each>
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <header level="2">Exemples</header>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:for-each select="example/item">
            <xsl:call-template name="process">
                <xsl:with-param name="node">
                    <list level="1"><xsl:copy-of select="goal"/></list>
                </xsl:with-param>
            </xsl:call-template>
            <xsl:for-each select="answer">
                <xsl:call-template name="process">
                    <xsl:with-param name="node">
                        <list level="2"><xsl:copy-of select="."/></list>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:for-each>
        </xsl:for-each>
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <endOfSection/>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <!-- File modifications -->
    <xsl:template match="data/fileModification">       
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <header level="1">FILE MODIFICATIONS</header>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:copy-of select="goal"/><br/>
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <header level="2">Fin de lignes dans les fichiers</header>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <list level="1"><xsl:copy-of select="key('metadata', '//data/fileModification/endOfLine/character')"/></list>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:for-each select="endOfLine/character">
            <xsl:call-template name="process">
                <xsl:with-param name="node">
                    <list level="2"><xsl:copy-of select="."/></list>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:for-each>
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <list level="1"><xsl:copy-of select="key('metadata', '//data/fileModification/endOfLine/sequence')"/></list>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:for-each select="endOfLine/sequence">
            <xsl:call-template name="process">
                <xsl:with-param name="node">
                    <list level="2"><xsl:copy-of select="."/></list>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:for-each>
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <header level="2">Catalogue des méthodes de modifications</header>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:for-each select="item">
            <xsl:call-template name="process">
                <xsl:with-param name="node">
                    <list level="1"><xsl:copy-of select="goal"/></list>
                </xsl:with-param>
            </xsl:call-template>
            <xsl:if test="example">
                <xsl:call-template name="process">
                    <xsl:with-param name="node">
                        <headerRow><cell>Source</cell><cell>Cible</cell></headerRow>
                        <row><cell><xsl:copy-of select="example/source"/></cell><cell><xsl:copy-of select="example/target"/></cell></row>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:if>
            <xsl:for-each select="answer">
                <xsl:call-template name="process">
                    <xsl:with-param name="node">
                        <list level="2"><xsl:copy-of select="tool"/></list>
                    </xsl:with-param>
                </xsl:call-template>
                <xsl:for-each select="method">
                    <xsl:call-template name="process">
                        <xsl:with-param name="node">
                            <list level="3"><xsl:copy-of select="."/></list>
                        </xsl:with-param>
                    </xsl:call-template>
            </xsl:for-each>
            </xsl:for-each>
        </xsl:for-each>
        <xsl:for-each select="endOfLine/sequence">
            <xsl:call-template name="process">
                <xsl:with-param name="node">
                    <list level="2"><xsl:copy-of select="."/></list>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:for-each>
    </xsl:template>
    
    <!-- Subversion -->
    <xsl:template match="data/subversion">
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <header level="1">SUBVERSION</header>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:for-each select="item">
            <xsl:call-template name="process">
                <xsl:with-param name="node">
                    <list level="1"><xsl:copy-of select="description"/><newLine/><xsl:copy-of select="command"/></list>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:for-each>
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <endOfSection/>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <!-- Sql -->
    <xsl:template match="data/sql">
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <header level="1">SQL</header>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:for-each select="item">
            <xsl:call-template name="process">
                <xsl:with-param name="node">
                    <list level="1"><xsl:copy-of select="goal"/></list>
                    <list level="2">Exemple :<newLine/><xsl:copy-of select="example"/></list>
                    <list level="2">Requête :<newLine/><xsl:copy-of select="query"/></list>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:for-each>
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <endOfSection/>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

     <!-- Sybase -->
    <xsl:template match="data/sybase">
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <header level="1">SYBASE</header>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:for-each select="item">
            <xsl:call-template name="process">
                <xsl:with-param name="node">
                    <list level="1"><xsl:copy-of select="goal"/><newLine/><xsl:copy-of select="command"/></list>
                    <xsl:if test="example"><list level="2">example : <technic><xsl:copy-of select="example"/></technic></list></xsl:if>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:for-each>
        <xsl:for-each select="object">
            <xsl:call-template name="process">
                <xsl:with-param name="node">
                    <header level="2"><xsl:copy-of select="name"/></header>
                    <xsl:copy-of select="definition"/><newLine/>
                    <xsl:copy-of select="example"/><newLine/>
                </xsl:with-param>
            </xsl:call-template>
            <xsl:if test="remark">Remarques :<br/></xsl:if>
            <xsl:for-each select="remark">
                <xsl:call-template name="process">
                    <xsl:with-param name="node">
                        <list level="2"><xsl:copy-of select="."/></list>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:for-each>
        </xsl:for-each>
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <endOfSection/>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <!-- Dos -->
    <xsl:template match="data/dos">
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <header level="1">DOS</header>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:for-each select="item">
            <xsl:call-template name="process">
                <xsl:with-param name="node">
                    <list level="1"><xsl:copy-of select="goal"/><newLine/><xsl:copy-of select="command"/></list>
                </xsl:with-param>
            </xsl:call-template>
            <xsl:for-each select="options/option">
                <xsl:call-template name="process">
                    <xsl:with-param name="node">
                        <list level="1">
                            <technic><xsl:copy-of select="shortcut"/></technic>
                            <xsl:if test="meaning">
                                <xsl:text> (</xsl:text><xsl:copy-of select="meaning"/><xsl:text>)</xsl:text>
                            </xsl:if>
                            <xsl:if test="description">
                                <xsl:text> :</xsl:text><xsl:copy-of select="description"/>
                            </xsl:if>
                        </list>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:for-each>
        </xsl:for-each>
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <endOfSection/>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <!-- Keyboard -->
    <xsl:template match="data/keyboard">
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <header level="1">KEYBOARD</header>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:for-each select="item">
            <xsl:call-template name="process">
                <xsl:with-param name="node">
                    <list level="1"><xsl:copy-of select="goal"/> : <xsl:copy-of select="shortcut"/></list>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:for-each>
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <endOfSection/>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <!-- Regular expressions -->
    <xsl:template match="data/regexp">
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <header level="1">REGULAR EXPRESSIONS</header>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:for-each select="tool">
            <xsl:call-template name="process">
                <xsl:with-param name="node">
                    <header level="2"><xsl:copy-of select="name"/></header>
                </xsl:with-param>
            </xsl:call-template>
             <xsl:for-each select="matching">
                <xsl:call-template name="process">
                    <xsl:with-param name="node">
                        <list level="1"><technic><xsl:copy-of select="expression"/></technic> : <xsl:copy-of select="behaviour"/></list>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:for-each>
            <xsl:for-each select="replacement">
                <xsl:call-template name="process">
                    <xsl:with-param name="node">
                        <list level="1"><xsl:copy-of select="key('metadata', '//data/regexp/tool/remplacement')"/> : <technic><xsl:copy-of select="."/></technic></list>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:for-each>
        </xsl:for-each>
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <endOfSection/>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <!-- Language -->
    <xsl:template match="data/language">
        <xsl:variable name="hasPhonetic" select="word/phonetic!=''"/>
        <xsl:variable name="hasDescription" select="word/description!=''"/>
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <header level="1"><xsl:copy-of select="name"/></header>
            </xsl:with-param>
        </xsl:call-template>        
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <header level="2">Références</header>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:for-each select="reference">
            <xsl:call-template name="process">
                <xsl:with-param name="node">
                    <list level="1"><xsl:copy-of select="name"/> : <xsl:copy-of select="link"/></list>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:for-each>
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <header level="2">Liste</header>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="process">
            <xsl:with-param name="node">
            <headerRow>
                <cell>Mot</cell>
                <xsl:if test="$hasPhonetic"><cell>Phonétique</cell></xsl:if>
                <xsl:if test="$hasDescription"><cell>Description</cell></xsl:if>
            </headerRow>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:for-each select="word">
            <xsl:if test="name!=''">
                <xsl:call-template name="process">
                    <xsl:with-param name="node">
                    <row>
                        <cell><xsl:copy-of select="name"/></cell>
                        <xsl:if test="$hasPhonetic"><cell><xsl:if test="phonetic"><xsl:text>[</xsl:text><xsl:copy-of select="phonetic"/><xsl:text>]</xsl:text></xsl:if></cell></xsl:if>
                        <cell><xsl:copy-of select="description"/></cell>
                    </row>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:if>
        </xsl:for-each>
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <endOfSection/>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <!-- WebAndWiki -->
    <xsl:template match="data/webAndWiki">
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <header level="1">WEB AND WIKI</header>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:for-each select="reference">
            <xsl:call-template name="process">
                <xsl:with-param name="node">
                    <list level="1"><xsl:copy-of select="name"/> : <xsl:copy-of select="link"/></list>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:for-each>
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <endOfSection/>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>
    
    <!-- XmlTechno -->
    <xsl:template match="data/xmlTechno">
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <header level="1">XML TECHNO</header>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:for-each select="group">
            <xsl:call-template name="process">
                <xsl:with-param name="node">
                    <header level="2"><xsl:copy-of select="name"/></header>
                </xsl:with-param>
            </xsl:call-template>
            <xsl:for-each select="item">
                <xsl:call-template name="process">
                    <xsl:with-param name="node">
                        <list level="1"><xsl:copy-of select="name"/></list>
                    </xsl:with-param>
                </xsl:call-template>
                <xsl:for-each select="reference">
                    <xsl:call-template name="process">
                        <xsl:with-param name="node">
                            <list level="2"><xsl:copy-of select="name"/> : <xsl:copy-of select="link"/></list>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:for-each>
            </xsl:for-each>
        </xsl:for-each>
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <endOfSection/>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>
    
    <!-- Java -->
    <xsl:template match="data/java">
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <header level="1">JAVA</header>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:for-each select="item">
            <xsl:call-template name="process">
                <xsl:with-param name="node">
                    <header level="2"><xsl:copy-of select="goal"/></header>
                </xsl:with-param>
            </xsl:call-template>
            <xsl:if test="answer">
                <xsl:call-template name="process">
                    <xsl:with-param name="node">
                        <list level="1">Solution : <xsl:copy-of select="answer"/></list>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:if>
            <xsl:for-each select="package">
                <xsl:call-template name="process">
                    <xsl:with-param name="node">
                        <list level="1"><xsl:copy-of select="name"/> : 
                             <xsl:for-each select="class">
                                <xsl:copy-of select="."/><xsl:text>, </xsl:text>
                            </xsl:for-each>
                        </list>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:for-each>
            <xsl:call-template name="process">
                <xsl:with-param name="node">
                    <xsl:copy-of select="code"/><newLine/>
                </xsl:with-param>
            </xsl:call-template>
            <xsl:if test="remark">
                <xsl:text>Remarques :</xsl:text><br/>
                <xsl:for-each select="remark">
                    <xsl:call-template name="process">
                        <xsl:with-param name="node">
                           <list level="1"><xsl:copy-of select="."/></list>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:for-each>
            </xsl:if>
        </xsl:for-each>
        <xsl:call-template name="process">
            <xsl:with-param name="node">
                <endOfSection/>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>
</xsl:stylesheet>