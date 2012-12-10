@echo off
java -classpath ".\target\classes" fr.pragmaticmemory.xslt.XsltEngine "knowledge\data\input\Data.xml" "knowledge\data\input\View.xsl" "knowledge\data\output\View.xml"
java -classpath ".\target\classes" fr.pragmaticmemory.xslt.XsltEngine "knowledge\data\output\View.xml" "knowledge\data\input\Wikispaces.xsl" "knowledge\data\output\Wikispaces.txt"
java -classpath ".\target\classes" fr.pragmaticmemory.xslt.XsltEngine "knowledge\data\output\View.xml" "knowledge\data\input\Text.xsl" "knowledge\data\output\Text.txt"