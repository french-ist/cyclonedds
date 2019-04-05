/**
  Copyright(c) 2006 to 2019 ADLINK Technology Limited and others
 
  This program and the accompanying materials are made available under the
  terms of the Eclipse Public License v. 2.0 which is available at
  http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
  v. 1.0 which is available at
  http://www.eclipse.org/org/documents/edl-v10.php.
 
  SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.cyclonedds.ddsc.dds__types;
import org.eclipse.cyclonedds.helper.*;
import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.os_mutex;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.rhc;
import org.eclipse.cyclonedds.ddsc.dds__types.DdscLibrary.ut_avlTree_t;
/**
 */
public class dds_globals extends Structure {
	/** C type : dds_domainid_t */
	public int m_default_domain;
	public int getM_default_domain() {
		return m_default_domain;
	}
	public void setM_default_domain(int m_default_domain) {
		this.m_default_domain = m_default_domain;
	}
	public int m_init_count;
	public int getM_init_count() {
		return m_init_count;
	}
	public void setM_init_count(int m_init_count) {
		this.m_init_count = m_init_count;
	}
	/** C type : m_dur_reader_callback* */
	public dds_globals.m_dur_reader_callback m_dur_reader;
	public dds_globals.m_dur_reader_callback getM_dur_reader() {
		return m_dur_reader;
	}
	public void setM_dur_reader(dds_globals.m_dur_reader_callback m_dur_reader) {
		this.m_dur_reader = m_dur_reader;
	}
	/** C type : m_dur_wait_callback* */
	public dds_globals.m_dur_wait_callback m_dur_wait;
	public dds_globals.m_dur_wait_callback getM_dur_wait() {
		return m_dur_wait;
	}
	public void setM_dur_wait(dds_globals.m_dur_wait_callback m_dur_wait) {
		this.m_dur_wait = m_dur_wait;
	}
	/** C type : m_dur_init_callback* */
	public dds_globals.m_dur_init_callback m_dur_init;
	public dds_globals.m_dur_init_callback getM_dur_init() {
		return m_dur_init;
	}
	public void setM_dur_init(dds_globals.m_dur_init_callback m_dur_init) {
		this.m_dur_init = m_dur_init;
	}
	/** C type : m_dur_fini_callback* */
	public dds_globals.m_dur_fini_callback m_dur_fini;
	public dds_globals.m_dur_fini_callback getM_dur_fini() {
		return m_dur_fini;
	}
	public void setM_dur_fini(dds_globals.m_dur_fini_callback m_dur_fini) {
		this.m_dur_fini = m_dur_fini;
	}
	/** C type : ut_avlTree_t */
	public ut_avlTree_t m_domains;
	public ut_avlTree_t getM_domains() {
		return m_domains;
	}
	public void setM_domains(ut_avlTree_t m_domains) {
		this.m_domains = m_domains;
	}
	/** C type : os_mutex */
	public os_mutex m_mutex;
	public os_mutex getM_mutex() {
		return m_mutex;
	}
	public void setM_mutex(os_mutex m_mutex) {
		this.m_mutex = m_mutex;
	}
	public interface m_dur_reader_callback extends Callback {
		void apply(dds_reader reader, rhc rhc);
	};
	public interface m_dur_wait_callback extends Callback {
		int apply(dds_reader reader, long timeout);
	};
	public interface m_dur_init_callback extends Callback {
		void apply();
	};
	public interface m_dur_fini_callback extends Callback {
		void apply();
	};
	public dds_globals() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("m_default_domain", "m_init_count", "m_dur_reader", "m_dur_wait", "m_dur_init", "m_dur_fini", "m_domains", "m_mutex");
	}
	/**
	 * @param m_default_domain C type : dds_domainid_t<br>
	 * @param m_dur_reader C type : m_dur_reader_callback*<br>
	 * @param m_dur_wait C type : m_dur_wait_callback*<br>
	 * @param m_dur_init C type : m_dur_init_callback*<br>
	 * @param m_dur_fini C type : m_dur_fini_callback*<br>
	 * @param m_domains C type : ut_avlTree_t<br>
	 * @param m_mutex C type : os_mutex
	 */
	public dds_globals(int m_default_domain, int m_init_count, dds_globals.m_dur_reader_callback m_dur_reader, dds_globals.m_dur_wait_callback m_dur_wait, dds_globals.m_dur_init_callback m_dur_init, dds_globals.m_dur_fini_callback m_dur_fini, ut_avlTree_t m_domains, os_mutex m_mutex) {
		super();
		this.m_default_domain = m_default_domain;
		this.m_init_count = m_init_count;
		this.m_dur_reader = m_dur_reader;
		this.m_dur_wait = m_dur_wait;
		this.m_dur_init = m_dur_init;
		this.m_dur_fini = m_dur_fini;
		this.m_domains = m_domains;
		this.m_mutex = m_mutex;
	}
	public dds_globals(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends dds_globals implements Structure.ByReference {
		
	};
	public static class ByValue extends dds_globals implements Structure.ByValue {
		
	};
}
