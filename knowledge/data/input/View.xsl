<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:strip-space elements="*"/>
<xsl:output method="xml"/>
    <!--..............................................................-->
    <!-- Templates de vues = templates définissant la vue des données -->
    <!--..............................................................-->

    <xsl:key name="metadata" match="metadata" use="@xpath"/>

    <!-- Root element -->
    <xsl:template match="/data">
        <root>
            <tableOfContent/>
            <endOfSection/>
            <xsl:apply-templates/>
        </root>
    </xsl:template>

    <!-- Dashboard -->
    <xsl:template match="/data/dashboard">
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
    <xsl:template match="/data/dashboard">
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
    <xsl:template match="/data/principles"><header level="1">PRINCIPES DE REDACTION</header>
       <xsl:copy-of select="goal"/><newLine/>
       <xsl:for-each select="group">
            <newLine/><header level="2"><xsl:copy-of select="name"/></header>
            <xsl:for-each select="item">
                <header level="3"><xsl:copy-of select="name"/></header>
                <code><codeLine><xsl:value-of select="description"/></codeLine></code>
                <xsl:if test="comments"><newLine/></xsl:if>
                <xsl:apply-templates select="comments"/>
            </xsl:for-each>
        </xsl:for-each>
        <endOfSection/>
    </xsl:template>

    <!-- Sandbox -->
    <xsl:template match="/data/sandbox">
        <header level="1">SANDBOX</header>
        <xsl:for-each select="example">
            <xsl:copy-of select="."/>
            <newLine/>
        </xsl:for-each>
        <endOfSection/>
    </xsl:template>


    <!-- Powershell -->
    <xsl:template match="/data/powershell">
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
                <xsl:for-each select="solutions/solution/pipe">
                    <technic>
                        <xsl:copy-of select="source"/>
                        <xsl:text> > </xsl:text>
                        <colored><xsl:copy-of select="command"/></colored>
                        <xsl:text> > </xsl:text>
                        <xsl:copy-of select="target"/>
                    </technic>
                    <newLine/>
                </xsl:for-each>
                <xsl:if test="solutions/solution/alias">
                    <xsl:text>Alias :</xsl:text><newLine/>
                </xsl:if>
                <xsl:for-each select="solutions/solution/alias">
                    <list level="2"><technic><xsl:copy-of select="."/></technic></list>
                </xsl:for-each>
                <xsl:if test="solutions/solution/option">
                    <xsl:text>Options :</xsl:text><newLine/>
                </xsl:if>
                <xsl:for-each select="solutions/solution/option">
                    <list level="2">
                        <technic><xsl:copy-of select="name"/></technic>
                        <note><technic><xsl:copy-of select="shortcut"/></technic></note>
                    </list>
                </xsl:for-each>
                <xsl:apply-templates select="comments"/>
            </xsl:for-each>
        </xsl:for-each>
        <header level="2">Exemples</header>
        <xsl:for-each select="example/method">
            <list level="1"><xsl:copy-of select="goal"/></list>
            <xsl:for-each select="solutions/solution">
                <list level="2"><xsl:copy-of select="."/></list>
            </xsl:for-each>
        </xsl:for-each>
        <endOfSection/>
    </xsl:template>

    <!-- File modifications -->
    <xsl:template match="/data/fileModification">
        <header level="1">FILE MODIFICATIONS</header>
        <xsl:copy-of select="goal"/><newLine/>
        <header level="2">Fin de lignes dans les fichiers</header>
        <list level="1"><xsl:copy-of select="key('metadata', '/data/fileModification/endOfLine/character')"/></list>
        <xsl:for-each select="endOfLine/character">
            <list level="2"><xsl:copy-of select="."/></list>
        </xsl:for-each>
        <list level="1"><xsl:copy-of select="key('metadata', '/data/fileModification/endOfLine/sequence')"/></list>
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
            <xsl:for-each select="solutions/solution">
                <list level="2"><xsl:copy-of select="tool"/></list>
                <xsl:for-each select="step">
                    <list level="3"><xsl:copy-of select="."/></list>
                </xsl:for-each>
            </xsl:for-each>
        </xsl:for-each>
        <header level="2">Définitions</header>
        <xsl:apply-templates select="item"/>
        <endOfSection/>
    </xsl:template>

    <!-- Subversion -->
    <xsl:template match="/data/subversion">
        <header level="1">SUBVERSION</header>
        <xsl:apply-templates/>
        <endOfSection/>
    </xsl:template>

    <!-- Git -->
    <xsl:template match="/data/git">
        <header level="1">GIT</header>
        <header level="2">Objets</header>
        <xsl:apply-templates select="item"/>
        <header level="2">Méthodes</header>
        <xsl:apply-templates select="method"/>
        <xsl:apply-templates select="references"/>
        <endOfSection/>
    </xsl:template>

    <!-- Sql -->
    <xsl:template match="/data/sql">
        <header level="1">SQL</header>
        <xsl:apply-templates/>
        <endOfSection/>
    </xsl:template>

    <!-- Sybase -->
    <xsl:template match="/data/sybase">
        <header level="1">SYBASE</header>
        <xsl:apply-templates select="method"/>
        <xsl:apply-templates select="item"/>
        <endOfSection/>
    </xsl:template>

    <!-- Dos -->
    <xsl:template match="/data/dos">
        <header level="1">DOS</header>
        <xsl:for-each select="item">
            <list level="1"><xsl:copy-of select="name"/><xsl:text> : </xsl:text><xsl:copy-of select="description"/></list>
            <xsl:apply-templates select="comments/comment"/>
            <xsl:for-each select="references/reference">
                <list level="2"><xsl:copy-of select="."/></list>
            </xsl:for-each>
        </xsl:for-each>
        <xsl:for-each select="method">
            <list level="1"><xsl:copy-of select="goal"/><newLine/><xsl:copy-of select="solutions/solution"/></list>
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
    <xsl:template match="/data/keyboard">
        <header level="1">KEYBOARD</header>
        <xsl:apply-templates/>
        <endOfSection/>
    </xsl:template>

    <!-- Regular expressions -->
    <xsl:template match="/data/regexp">
        <header level="1">REGULAR EXPRESSIONS</header>
        <xsl:for-each select="tool">
            <header level="2"><xsl:copy-of select="name"/></header>
             <xsl:for-each select="matching">
                <list level="1"><technic><xsl:copy-of select="expression"/></technic> : <xsl:copy-of select="behaviour"/></list>
            </xsl:for-each>
            <xsl:for-each select="replacement">
                <list level="1"><xsl:copy-of select="key('metadata', '/data/regexp/tool/remplacement')"/> : <technic><xsl:copy-of select="."/></technic></list>
            </xsl:for-each>
        </xsl:for-each>
        <header level="2">Méthodes indépendantes des outils</header>
        <xsl:apply-templates select="method"/>
        <endOfSection/>
    </xsl:template>


    <!-- Language -->
    <xsl:template match="/data/language">
        <xsl:variable name="hasPhonetic" select="count(word/phonetic)>0"/>
        <xsl:variable name="hasPhonetic2" select="count(word/phonetic)>1"/>
        <xsl:variable name="hasDescription" select="word/description!=''"/>
        <header level="1"><xsl:copy-of select="name"/></header>
        <xsl:apply-templates select="references"/>
        <header level="2">Liste</header>
        <headerRow>
            <cell>Mot</cell>
            <xsl:if test="$hasPhonetic"><cell>Phonétique</cell></xsl:if>
            <xsl:if test="$hasPhonetic2"><cell>Phonétique (US)</cell></xsl:if>
            <xsl:if test="$hasDescription"><cell>Description</cell></xsl:if>
        </headerRow>
        <xsl:for-each select="word">
            <xsl:if test="name!=''">
                <row>
                    <cell><xsl:copy-of select="name"/></cell>
                    <xsl:if test="$hasPhonetic"><cell><xsl:if test="phonetic[1]"><xsl:text>[</xsl:text><xsl:copy-of select="phonetic[1]"/><xsl:text>]</xsl:text></xsl:if></cell></xsl:if>
                    <xsl:if test="$hasPhonetic"><cell><xsl:if test="phonetic[2]"><xsl:text>[</xsl:text><xsl:copy-of select="phonetic[2]"/><xsl:text>]</xsl:text></xsl:if></cell></xsl:if>
                    <xsl:if test="$hasDescription"><cell><xsl:if test="description"><xsl:copy-of select="description"/></xsl:if></cell></xsl:if>
                </row>
            </xsl:if>
        </xsl:for-each>
        <endOfSection/>
    </xsl:template>

    <!-- WebAndWiki -->
    <xsl:template match="/data/webAndWiki">
        <header level="1">WEB AND WIKI</header>
        <xsl:apply-templates/>
        <endOfSection/>
    </xsl:template>

    <!-- Xml -->
    <xsl:template match="/data/xml">
        <header level="1">XML</header>
        <xsl:apply-templates/>
        <endOfSection/>
    </xsl:template>

    <!-- Java -->
    <xsl:template match="/data/java">
        <header level="1">JAVA</header>
        <header level="2">Objets</header>
        <xsl:for-each select="item">
            <list level="1"><xsl:copy-of select="name"/></list>
            <xsl:copy-of select="description"/><newLine/>
            <xsl:apply-templates select="comments"/>
        </xsl:for-each>
        <header level="2">Méthodes</header>
        <xsl:for-each select="method">
            <list level="1"><xsl:copy-of select="goal"/></list>
            <xsl:if test="solutions/solution/description">
                <xsl:copy-of select="solutions/solution/description"/><newLine/>
            </xsl:if>
            <xsl:if test="solutions/solution/package">
                <table>
                    <headerRow>
                        <cell>Package</cell><cell>Classes</cell>
                    </headerRow>
                    <xsl:for-each select="solutions/solution/package">
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
            <xsl:if test="solutions/solution/code">
                <xsl:copy-of select="solutions/solution/code"/><newLine/>
            </xsl:if>
            <xsl:for-each select="solutions/solution/step">
                <xsl:copy-of select="."/><newLine/>
            </xsl:for-each>
            <xsl:apply-templates select="comments"/>
        </xsl:for-each>
        <xsl:apply-templates select="references"/>
        <endOfSection/>
    </xsl:template>

    <!-- Unix -->
    <xsl:template match="/data/unix">
        <header level="1">UNIX</header>
        <xsl:for-each select="item">
            <list level="1"><xsl:copy-of select="name"/></list>
            <list level="2"><xsl:text>Description : </xsl:text><xsl:copy-of select="description"/></list>
            <xsl:for-each select="comments/comment">
                <xsl:copy-of select="."/>
            </xsl:for-each>
        </xsl:for-each>
        <endOfSection/>
    </xsl:template>

    <!-- Hudson -->
    <xsl:template match="/data/hudson">
        <header level="1">HUDSON</header>
        <header level="2">Définitions</header>
        <xsl:apply-templates select="item"/>
        <header level="2">Méthodes</header>
        <xsl:apply-templates select="method"/>
        <endOfSection/>
    </xsl:template>

    <!-- Security -->
    <xsl:template match="/data/security">
        <header level="1">SECURITEE</header>
        <header level="2">Définitions</header>
        <xsl:apply-templates/>
        <endOfSection/>
    </xsl:template>

    <!-- Divers -->
    <xsl:template match="/data/miscellaneous">
        <header level="1">DIVERS</header>
        <xsl:apply-templates/>
        <endOfSection/>
    </xsl:template>

    <!-- Maven -->
    <xsl:template match="/data/maven">
        <header level="1">MAVEN</header>
        <xsl:apply-templates/>
        <endOfSection/>
    </xsl:template>

     <!-- Typing -->
    <xsl:template match="/data/typing">
        <header level="1">Dactylographie</header>
        <xsl:apply-templates/>
        <endOfSection/>
    </xsl:template>

    <!-- Confluence -->
    <xsl:template match="/data/confluence">
        <header level="1">CONFLUENCE</header>
        <xsl:apply-templates/>
        <endOfSection/>
    </xsl:template>
    
    <!-- COMMON -->
    <xsl:template match="references">
        <header level="2"><xsl:text>Références</xsl:text>
            <xsl:if test="@name">
                <xsl:text> (</xsl:text><xsl:value-of select="@name"/><xsl:text>)</xsl:text>
            </xsl:if>
        </header>
        <xsl:apply-templates select="reference"/>
    </xsl:template>

    <xsl:template match="reference">
        <list level="1"><xsl:copy-of select="."/></list>
    </xsl:template>

    <xsl:template match="method">
        <list level="1">
            <xsl:copy-of select="goal"/><newLine/>
            <xsl:if test="useCase"><list level="2">exemple de contexte : <newLine/><xsl:copy-of select="useCase"/></list></xsl:if>
            <xsl:apply-templates select="solutions/solution"/>
            <xsl:if test="example"><newLine/><list level="2">example : <newLine/><xsl:copy-of select="example"/></list></xsl:if>
        </list>
        <xsl:apply-templates select ="comments/comment"/>
    </xsl:template>

    <xsl:template match="solution">
        <xsl:variable name="isNotLastSolution" select="count(./following-sibling::solution)>0"/>
        <xsl:if test="description"><xsl:copy-of select="description"/><xsl:if test="count(description/following-sibling::code)>0"><newLine/></xsl:if></xsl:if>
        <xsl:if test="code"><xsl:copy-of select="code"/></xsl:if>
        <xsl:if test ="$isNotLastSolution"><newLine/></xsl:if>
    </xsl:template>

    <xsl:template match="item">
        <list level="1"><xsl:copy-of select="name"/><xsl:text> : </xsl:text><xsl:copy-of select="description"/></list>
        <xsl:if test="example"><xsl:copy-of select="example"/><newLine/></xsl:if>
        <xsl:apply-templates select="comments"/>
    </xsl:template>

    <xsl:template match="comments">
        <xsl:text>Remarques :</xsl:text><newLine/>
        <xsl:apply-templates select="comment"/>
    </xsl:template>

    <xsl:template match="comment">
        <list level="2"><xsl:copy-of select="."/></list>
    </xsl:template>

</xsl:stylesheet>