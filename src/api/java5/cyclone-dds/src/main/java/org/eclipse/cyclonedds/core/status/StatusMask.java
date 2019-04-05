/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.core.status;

public enum StatusMask {
    NONE                                ((int)(0x0L)),
    ANY                                 ((int)(0xFFFFFFFFL)),
    ANY_V1_2                            ((int)(0x7FFFL)),
    INCONSISTENT_TOPIC_STATUS           ((int)(0x0001L << 0L)),
    OFFERED_DEADLINE_MISSED_STATUS      ((int)(0x0001L << 1L)),
    REQUESTED_DEADLINE_MISSED_STATUS    ((int)(0x0001L << 2L)),
    OFFERED_INCOMPATIBLE_QOS_STATUS     ((int)(0x0001L << 5L)),
    REQUESTED_INCOMPATIBLE_QOS_STATUS   ((int)(0x0001L << 6L)),
    SAMPLE_LOST_STATUS                  ((int)(0x0001L << 7L)),
    SAMPLE_REJECTED_STATUS              ((int)(0x0001L << 8L)),
    DATA_ON_READERS_STATUS              ((int)(0x0001L << 9L)),
    DATA_AVAILABLE_STATUS               ((int)(0x0001L << 10L)),
    LIVELINESS_LOST_STATUS              ((int)(0x0001L << 11L)),
    LIVELINESS_CHANGED_STATUS           ((int)(0x0001L << 12L)),
    PUBLICATION_MATCHED_STATUS          ((int)(0x0001L << 13L)),
    SUBSCRIPTION_MATCHED_STATUS         ((int)(0x0001L << 14L)),
    ALL_DATA_DISPOSED_TOPIC_STATUS      ((int)(0x0001L << 31L));

    private final int mask;

    StatusMask(int mask){
        this.mask = mask;
    }

    public int getMask(){
        return this.mask;
    }
}
