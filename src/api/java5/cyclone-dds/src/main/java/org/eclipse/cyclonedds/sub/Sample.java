/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.sub;

/**
 * OpenSplice-specific extension of {@link org.omg.dds.sub.Sample} with support
 * for obtaining the key value in case the sample is marked as invalid. As
 * {@link org.omg.dds.sub.Sample#getData()} returns null in case the sample is
 * invalid, there is no reliable way to get the key value associated with it.
 * <p>
 * Typically one would be able to get the key value by means of
 * {@link org.omg.dds.sub.DataReader#getKeyValue(org.omg.dds.core.InstanceHandle)}
 * , but this may fail in case the corresponding instance has already been
 * removed from the DataReader.
 * <p>
 * Therefore OpenSplice ensures the key value is always maintained with the
 * Sample, even if it is invalid.
 * 
 * @param <TYPE>
 *            The concrete type of the data encapsulated by this Sample.
 */
public interface Sample<TYPE> extends org.omg.dds.sub.Sample<TYPE> {
    /**
     * @return The key value associated with the Sample. Only key attributes of
     *         the data can be accessed.
     */
    public TYPE getKeyValue();
}
