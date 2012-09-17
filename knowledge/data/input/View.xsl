<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:strip-space elements="*"/>
<xsl:output method="xml"/>
    <!--..............................................................-->
    <!-- Templates de vues = templates définissant la vue des données -->
    <!--..............................................................-->

    <xsl:key name="metadata" match="metadata" use="@xpath"/>

    <!-- Root element -->
    <xsl:template match="//data">
    <root>
        <tableOfContent/>
        <endOfSection/>
        <xsl:apply-templates/>
        </root>
    </xsl:template>

    <!-- Dashboard -->
    <xsl:template match="dashboard">
        <header level="1">DASHBOARD</header>
        <xsl:call-template name="task">
            <xsl:with-param name="status">Todo</xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="task">
            <xsl:with-param name="status">Done</xsl:with-param>
        </xsl:call-template>
        <endOfSection/>
    </xsl:template>

    <xsl:template name="task">
        <xsl:param name="status"/>
        <xsl:if test="$status='Done'"><xsl:text><header level="2">FAIT</header></xsl:text></xsl:if>
        <xsl:if test="$status='Todo'"><xsl:text><header level="2">A FAIRE</header></xsl:text></xsl:if>
        <xsl:for-each select="task[status=$status]">
            <list level="1"><xsl:copy-of select="description"/></list>
            <xsl:for-each select="candidate">
                <list level="2"><xsl:copy-of select="name"/></list>
                <xsl:if test="advantage">
                    <list level="3">Avantages :</list>
                    <xsl:for-each select="advantage">
                        <list level="4"><xsl:copy-of select="."/></list>
                    </xsl:for-each>
                </xsl:if>
                <xsl:if test="disadvantage">
                    <list level="3">Inconvénients :</list>
                    <xsl:for-each select="disadvantage">
                        <list level="4"><xsl:copy-of select="."/></list>
                    </xsl:for-each>
                </xsl:if>
            </xsl:for-each>
            <xsl:if test="selected">
                <list level="2">Solution retenue : <xsl:copy-of select="selected"/></list>
            </xsl:if>
        </xsl:for-each>
    </xsl:template>

    <!--
    <xsl:key name="method-by-status" match="dashboard/group/method" use="status"/>
    <xsl:template match="dashboard">
        <xsl:for-each select="group/method[generate-id() = generate-id(key('method-by-status', status)[1])]">
            <xsl:sort select="status"/>
            <xsl:if test="status='Done'">FAIT</xsl:if>
            <xsl:if test="status='Todo'">A FAIRE</xsl:if><newLine/>
            <xsl:for-each select="key('method-by-status', status)">
                <xsl:sort select="goal"/>
                <list level="1"><xsl:copy-of select="goal"/></list>
            </xsl:for-each>
        </xsl:for-each>
        <endOfSection/>
    </xsl:template>
    -->

    <!-- Principles -->
    <xsl:template match="data/principles">
       <header level="1">PRINCIPES DE REDACTION</header>
       <xsl:copy-of select="goal"/><newLine/>
       <xsl:for-each select="group">
            <header level="2"><xsl:copy-of select="name"/></header>
            <xsl:for-each select="item">
                <header level="3"><xsl:copy-of select="name"/></header>
                <code><xsl:copy-of select="description"/><newLine/></code><newLine/>
                <xsl:copy-of select="comment"/>
            </xsl:for-each>
        </xsl:for-each>
        <endOfSection/>
    </xsl:template>

    <!-- Sandbox -->
    <xsl:template match="data/sandbox">
        <header level="1">SANDBOX</header>
        <xsl:for-each select="example">
            <xsl:copy-of select="."/>
            <newLine/>
        </xsl:for-each>
        <endOfSection/>
    </xsl:template>


    <!-- Powershell -->
    <xsl:template match="data/powershell">
        <header level="1">POWERSHELL</header>
        <header level="2">Notation de types</header>
        <xsl:for-each select="typeInformation/notation/item">
            <list level="1"><xsl:copy-of select="name"/> : <xsl:copy-of select="description"/></list>
        </xsl:for-each>
        <header level="2">Compatibilité entre types</header>
        <headerRow><cell>Type attendu</cell><cell>Type particulier utilisable</cell></headerRow>
        <xsl:for-each select="typeInformation/compatibility/item">
            <row><cell><xsl:copy-of select="expectedType"/></cell><cell><xsl:copy-of select="compatibleType"/></cell></row>
        </xsl:for-each>
        <header level="2">Membres par type</header>
        <xsl:for-each select="typeInformation/properties/group">
            <list>Objet de type <xsl:copy-of select="name"/> :</list>
            <xsl:for-each select ="item">
                <row><cell><xsl:copy-of select="name"/></cell><cell><xsl:copy-of select="description"/></cell></row>
            </xsl:for-each>
        </xsl:for-each>
        <xsl:for-each select="group">
            <header level="2"><xsl:copy-of select="name"/></header>
            <xsl:for-each select="method">
                <list level="1"><underline><xsl:copy-of select="goal"/></underline></list>
                <xsl:for-each select="solution/pipe">
                    <technic>
                        <xsl:copy-of select="source"/>
                        <xsl:text> > </xsl:text>
                        <colored><xsl:copy-of select="command"/></colored>
                        <xsl:text> > </xsl:text>
                        <xsl:copy-of select="target"/>
                    </technic>
                    <newLine/>
                </xsl:for-each>
                <xsl:if test="solution/alias">
                    <xsl:text>Alias :</xsl:text><newLine/>
                </xsl:if>
                <xsl:for-each select="solution/alias">
                    <list level="2"><technic><xsl:copy-of select="."/></technic></list>
                </xsl:for-each>
                <xsl:if test="solution/option">
                    <xsl:text>Options :</xsl:text><newLine/>
                </xsl:if>
                <xsl:for-each select="solution/option">
                    <list level="2">
                        <technic><xsl:copy-of select="name"/></technic>
                        <note><technic><xsl:copy-of select="shortcut"/></technic></note>
                    </list>
                </xsl:for-each>
                <xsl:if test="comment">
                    <xsl:text>Remarques :</xsl:text><newLine/>
                </xsl:if>
                <xsl:for-each select="comment">
                   <list level="2"><xsl:copy-of select="."/></list>
                </xsl:for-each>
            </xsl:for-each>
        </xsl:for-each>
        <header level="2">Exemples</header>
        <xsl:for-each select="example/method">
            <list level="1"><xsl:copy-of select="goal"/></list>
            <xsl:for-each select="solution">
                <list level="2"><xsl:copy-of select="."/></list>
            </xsl:for-each>
        </xsl:for-each>
        <endOfSection/>
    </xsl:template>

    <!-- File modifications -->
    <xsl:template match="data/fileModification">
        <header level="1">FILE MODIFICATIONS</header>
        <xsl:copy-of select="goal"/><newLine/>
        <header level="2">Fin de lignes dans les fichiers</header>
        <list level="1"><xsl:copy-of select="key('metadata', '//data/fileModification/endOfLine/character')"/></list>
        <xsl:for-each select="endOfLine/character">
            <list level="2"><xsl:copy-of select="."/></list>
        </xsl:for-each>
        <list level="1"><xsl:copy-of select="key('metadata', '//data/fileModification/endOfLine/sequence')"/></list>
        <xsl:for-each select="endOfLine/sequence">
            <list level="2"><xsl:copy-of select="."/></list>
        </xsl:for-each>
        <header level="2">Catalogue des méthodes de modifications</header>
        <xsl:for-each select="method">
            <list level="1"><xsl:copy-of select="goal"/></list>
            <xsl:if test="example">
                <headerRow><cell>Source</cell><cell>Cible</cell></headerRow>
                <row><cell><xsl:copy-of select="example/source"/></cell><cell><xsl:copy-of select="example/target"/></cell></row>
            </xsl:if>
            <xsl:for-each select="solution">
                <list level="2"><xsl:copy-of select="tool"/></list>
                <xsl:for-each select="step">
                    <list level="3"><xsl:copy-of select="."/></list>
                </xsl:for-each>
            </xsl:for-each>
        </xsl:for-each>
        <header level="2">Définitions</header>
        <xsl:for-each select="item">
            <list level="1"><xsl:copy-of select="name"/><xsl:text> : </xsl:text><xsl:copy-of select="description"/></list>
            <xsl:for-each select="comment">
                <list level="2"><xsl:copy-of select="."/></list>
            </xsl:for-each>
        </xsl:for-each>
        <endOfSection/>
    </xsl:template>

    <!-- Subversion -->
    <xsl:template match="data/subversion">
        <header level="1">SUBVERSION</header>
        <xsl:for-each select="method">
            <list level="1"><xsl:copy-of select="goal"/><newLine/><xsl:copy-of select="solution"/></list>
        </xsl:for-each>
        <endOfSection/>
    </xsl:template>

    <!-- Git -->
    <xsl:template match="data/git">
        <header level="1">GIT</header>
        <header level="2">Objets</header>
        <xsl:for-each select="item">
            <list level="1"><xsl:copy-of select="name"/><xsl:text> : </xsl:text><xsl:copy-of select="description"/></list>
        </xsl:for-each>
        <header level="2">Méthodes</header>
        <xsl:for-each select="method">
            <list level="1"><xsl:copy-of select="goal"/></list>
            <xsl:for-each select="solution">
                <xsl:copy-of select="."/><newLine/>
            </xsl:for-each>
            <xsl:for-each select="comment">
                <list level="1"><xsl:copy-of select="."/></list>
            </xsl:for-each>
        </xsl:for-each>
        <header level="2">Références</header>
        <xsl:apply-templates select="reference"/>
        <endOfSection/>
    </xsl:template>

    <!-- Sql -->
    <xsl:template match="data/sql">
        <header level="1">SQL</header>
        <xsl:for-each select="method">
            <list level="1"><xsl:copy-of select="goal"/></list>
            <xsl:if test="example"><list level="2">Exemple :<newLine/><xsl:copy-of select="example"/></list></xsl:if>
            <xsl:if test="solution"><list level="2">Requête :<newLine/><xsl:copy-of select="solution"/></list></xsl:if>
        </xsl:for-each>
        <endOfSection/>
    </xsl:template>

    <!-- Sybase -->
    <xsl:template match="data/sybase">
        <header level="1">SYBASE</header>
        <xsl:for-each select="method">
            <list level="1"><xsl:copy-of select="goal"/><newLine/><xsl:copy-of select="solution"/></list>
            <xsl:if test="example"><list level="2">example : <technic><xsl:copy-of select="example"/></technic></list></xsl:if>
        </xsl:for-each>
        <xsl:for-each select="item">
            <header level="2"><xsl:copy-of select="name"/></header>
            <xsl:copy-of select="definition"/><newLine/>
            <xsl:copy-of select="example"/><newLine/>
            <xsl:if test="comment">Remarques :<newLine/></xsl:if>
            <xsl:for-each select="comment">
                <list level="2"><xsl:copy-of select="."/></list>
            </xsl:for-each>
        </xsl:for-each>
        <endOfSection/>
    </xsl:template>


    <!-- Dos -->
    <xsl:template match="data/dos">
        <header level="1">DOS</header>
        <xsl:for-each select="item">
            <list level="1"><xsl:copy-of select="name"/><xsl:text> : </xsl:text><xsl:copy-of select="description"/></list>
            <xsl:for-each select="comment">
                <list level="2"><xsl:copy-of select="."/></list>
            </xsl:for-each>
            <xsl:for-each select="reference">
                <list level="2"><xsl:copy-of select="."/></list>
            </xsl:for-each>
        </xsl:for-each>
        <xsl:for-each select="method">
            <list level="1"><xsl:copy-of select="goal"/><newLine/><xsl:copy-of select="solution"/></list>
            <xsl:for-each select="options/option">
                <list level="1">
                    <technic><xsl:copy-of select="shortcut"/></technic>
                    <xsl:if test="meaning">
                        <xsl:text> (</xsl:text><xsl:copy-of select="meaning"/><xsl:text>)</xsl:text>
                    </xsl:if>
                    <xsl:if test="description">
                        <xsl:text> :</xsl:text><xsl:copy-of select="description"/>
                    </xsl:if>
                </list>
            </xsl:for-each>
        </xsl:for-each>
        <endOfSection/>
    </xsl:template>

    <!-- Keyboard -->
    <xsl:template match="data/keyboard">
        <header level="1">KEYBOARD</header>
        <xsl:for-each select="method">
            <list level="1"><xsl:copy-of select="goal"/> : <xsl:copy-of select="solution"/></list>
        </xsl:for-each>
        <endOfSection/>
    </xsl:template>

    <!-- Regular expressions -->
    <xsl:template match="data/regexp">
        <header level="1">REGULAR EXPRESSIONS</header>
        <xsl:for-each select="tool">
            <header level="2"><xsl:copy-of select="name"/></header>
             <xsl:for-each select="matching">
                <list level="1"><technic><xsl:copy-of select="expression"/></technic> : <xsl:copy-of select="behaviour"/></list>
            </xsl:for-each>
            <xsl:for-each select="replacement">
                <list level="1"><xsl:copy-of select="key('metadata', '//data/regexp/tool/remplacement')"/> : <technic><xsl:copy-of select="."/></technic></list>
            </xsl:for-each>
        </xsl:for-each>
        <endOfSection/>
    </xsl:template>


    <!-- Language -->
    <xsl:template match="data/language">
        <xsl:variable name="hasPhonetic" select="word/phonetic!=''"/>
        <xsl:variable name="hasDescription" select="word/description!=''"/>
        <header level="1"><xsl:copy-of select="name"/></header>
        <header level="2">Références</header>
        <xsl:apply-templates select="reference"/>
        <header level="2">Liste</header>
        <headerRow>
            <cell>Mot</cell>
            <xsl:if test="$hasPhonetic"><cell>Phonétique</cell></xsl:if>
            <xsl:if test="$hasDescription"><cell>Description</cell></xsl:if>
        </headerRow>
        <xsl:for-each select="word">
            <xsl:if test="name!=''">
                <row>
                    <cell><xsl:copy-of select="name"/></cell>
                    <xsl:if test="$hasPhonetic"><cell><xsl:if test="phonetic"><xsl:text>[</xsl:text><xsl:copy-of select="phonetic"/><xsl:text>]</xsl:text></xsl:if></cell></xsl:if>
                    <cell><xsl:copy-of select="description"/></cell>
                </row>
            </xsl:if>
        </xsl:for-each>
        <endOfSection/>
    </xsl:template>

    <!-- WebAndWiki -->
    <xsl:template match="data/webAndWiki">
        <header level="1">WEB AND WIKI</header>
            <xsl:apply-templates select="reference"/>
        <endOfSection/>
    </xsl:template>

    <!-- xslt -->
    <xsl:template match="data/xslt">
        <header level="1">XSLT</header>
        <xsl:for-each select="group">
            <header level="2"><xsl:copy-of select="name"/></header>
            <xsl:apply-templates select="reference"/>
        </xsl:for-each>
        <endOfSection/>
    </xsl:template>

    <!-- xmlSchema -->
    <xsl:template match="data/xsd">
        <header level="1">XSD et DTD</header>
        <xsl:for-each select="group">
            <header level="2"><xsl:copy-of select="name"/></header>
            <xsl:apply-templates select="reference"/>
        </xsl:for-each>
        <endOfSection/>
    </xsl:template>

    <!-- Java -->
    <xsl:template match="data/java">
        <header level="1">JAVA</header>
        <header level="2">Objets</header>
        <xsl:for-each select="item">
            <list level="1"><xsl:copy-of select="name"/></list>
            <xsl:copy-of select="description"/>
        </xsl:for-each>
        <header level="2">Méthodes</header>
        <xsl:for-each select="method">
            <header level="2"><xsl:copy-of select="goal"/></header>
            <xsl:if test="solution/package">
                <table>
                    <headerRow>
                        <cell>Package</cell><cell>Classes</cell>
                    </headerRow>
                    <xsl:for-each select="solution/package">
                        <row>
                            <cell>
                                <xsl:copy-of select="name"/>
                            </cell>
                            <cell>
                                <xsl:for-each select="class">
                                    <xsl:copy-of select="."/>
                                    <xsl:if test="not(position()=last())"><xsl:text>, </xsl:text></xsl:if>
                                </xsl:for-each>
                            </cell>
                        </row>
                    </xsl:for-each>
                </table>
            </xsl:if>
            <xsl:if test="solution/code">
                <xsl:copy-of select="solution/code"/><newLine/>
            </xsl:if>
            <xsl:for-each select="solution/step">
                <xsl:copy-of select="."/><newLine/>
            </xsl:for-each>
            <xsl:if test="comment">
                <xsl:text>Remarques :</xsl:text><newLine/>
                <xsl:for-each select="comment">
                   <list level="1"><xsl:copy-of select="."/></list>
                </xsl:for-each>
            </xsl:if>
        </xsl:for-each>
        <xsl:if test="reference">
             <header level="2">Références</header>
            <xsl:apply-templates select="reference"/>
         </xsl:if>
        <endOfSection/>
    </xsl:template>

    <!-- Unix -->
    <xsl:template match="data/unix">
        <header level="1">UNIX</header>
        <xsl:for-each select="item">
            <list level="1"><xsl:copy-of select="name"/></list>
            <list level="2"><xsl:text>Description : </xsl:text><xsl:copy-of select="description"/></list>
            <xsl:for-each select="comment">
                <xsl:copy-of select="."/>
            </xsl:for-each>
        </xsl:for-each>
        <endOfSection/>
    </xsl:template>

    <!-- Security -->
    <xsl:template match="data/security">
        <header level="1">SECURITEE</header>
        <xsl:apply-templates/>
        <endOfSection/>
    </xsl:template>

    <!-- Divers -->
    <xsl:template match="data/miscellaneous">
        <header level="1">DIVERS</header>
        <xsl:apply-templates/>
        <endOfSection/>
    </xsl:template>

    <!-- COMMON -->
    <xsl:template match="reference">
        <list level="1"><xsl:copy-of select="."/></list>
    </xsl:template>
</xsl:stylesheet>