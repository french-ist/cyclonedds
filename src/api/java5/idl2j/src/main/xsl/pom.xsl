<?xml version="1.0" encoding="UTF-8"?>
<!--

 * Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
 * v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
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
