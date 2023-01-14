<?xml version="1.0" encoding="ISO-8859-1"?>
<xs:stylesheet version="1.0"
               xmlns:xs="http://www.w3.org/1999/XSL/Transform"
               xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <!-- Attribut-Sets fuer Tabellenzellen -->
    <xs:attribute-set name="cell-style">
        <xs:attribute name="border-width">0.5pt</xs:attribute>
        <xs:attribute name="border-style">solid</xs:attribute>
        <xs:attribute name="border-color">black</xs:attribute>
    </xs:attribute-set>
    <xs:attribute-set name="block-style">
        <xs:attribute name="font-size">  10pt</xs:attribute>
        <xs:attribute name="line-height">15pt</xs:attribute>
        <xs:attribute name="start-indent">1mm</xs:attribute>
        <xs:attribute name="end-indent">  1mm</xs:attribute>
    </xs:attribute-set>

    <!-- Seitenaufteilung -->
    <xs:template match="/">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="DIN-A4"
                                       page-height="29.7cm" page-width="21cm"
                                       margin-top="2cm"     margin-bottom="2cm"
                                       margin-left="1cm"  margin-right="1cm">
                    <fo:region-body
                            margin-top="1.5cm" margin-bottom="1.8cm"
                            margin-left="1cm"  margin-right="1cm"/>
                    <fo:region-before region-name="header" extent="1.3cm"/>
                    <fo:region-after  region-name="footer" extent="1.5cm"/>
                    <fo:region-start  region-name="left"   extent="1cm"/>
                    <fo:region-end    region-name="right"  extent="2cm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="DIN-A4">
                <fo:static-content flow-name="header">
                    <fo:block font-size="14pt" text-align="center">
                        moduleManual
                    </fo:block>
                </fo:static-content>
                <fo:static-content flow-name="footer">
                    <fo:block text-align="center">
                        Seite <fo:page-number/> von <fo:page-number-citation ref-id="LastPage"/>
                    </fo:block>
                </fo:static-content>
                <fo:flow flow-name="xsl-region-body">
                    <xs:apply-templates/>
                    <fo:block id="LastPage"/>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xs:template>

    <!--xs:template match="moduleManual/titelseite">

    </xs:template>

    <xs:template match="moduleManual/inhaltsverzeichnis">

    </xs:template-->

    <xs:template match="moduleManual/modules">
        <xs:for-each select="section">
            <fo:block font-weight="bold" font-size="14pt">
                <xs:value-of select="@section"/>
            </fo:block>
            <xs:for-each select="moduleType">
                <fo:block font-weight="bold" font-size="14pt">
                    <xs:value-of select="@type"/>
                </fo:block>
                <xs:for-each select="module">
                    <xs:value-of select="@module"/>
                    <fo:table border-style="solid" table-layout="fixed" width="100%">
                        <fo:table-column column-width="30%"/>
                        <fo:table-column column-width="70%"/>
                        <fo:table-body>
                            <xs:for-each select="attribute">
                                <fo:table-row>
                                    <fo:table-cell xs:use-attribute-sets="cell-style">
                                        <fo:block xs:use-attribute-sets="block-style">
                                            <xs:value-of select="@title"/>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell xs:use-attribute-sets="cell-style">
                                        <fo:block xs:use-attribute-sets="block-style">
                                            <xs:value-of select="@value" disable-output-escaping="yes"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                            </xs:for-each>
                        </fo:table-body>
                    </fo:table>
                    <fo:block page-break-after="always"/>
                </xs:for-each>
            </xs:for-each>
        </xs:for-each>
    </xs:template>
</xs:stylesheet>