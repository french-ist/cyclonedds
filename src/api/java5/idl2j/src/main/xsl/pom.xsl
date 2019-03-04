<?xml version="1.0" encoding="UTF-8"?>
<!--

                                Vortex Cafe

       This software and documentation are Copyright 2010 to 2019 ADLINK
       Technology Limited, its affiliated companies and licensors. All rights
       reserved.

       Licensed under the ADLINK Software License Agreement Rev 2.7 2nd October
       2014 (the "License"); you may not use this file except in compliance with
       the License.
       You may obtain a copy of the License at:
                           docs/LICENSE.html

       See the License for the specific language governing permissions and
       limitations under the License.

-->
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:p="http://maven.apache.org/POM/4.0.0">

    <!-- copy everything -->
    <xsl:template match="node()|@*">
      <xsl:copy>
         <xsl:apply-templates select="node()|@*"/>
      </xsl:copy>
    </xsl:template>

    <!-- except those nodes: -->
    <xsl:template match="p:properties"/>
    <xsl:template match="p:dependencies"/>
    <xsl:template match="p:build"/>
    <xsl:template match="p:profiles"/>
    <xsl:template match="p:distributionManagement"/>

</xsl:stylesheet>
