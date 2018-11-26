/** TODO: Move to appropriate location */
typedef ;
int32_t dds_return_t;
typedef ;
int32_t dds_entity_t;
/**
 * @file<br>
 * * @brief DDS C Implementation API<br>
 * * This header file defines the public API for all kinds of things in the<br>
 * CycloneDDS C language binding.
 */
typedef struct dds_sequence {
	uint32_t _maximum;
	uint32_t _length;
	uint8_t *_buffer;
	bool _release;
} dds_sequence_t;
typedef struct dds_key_descriptor {
	const char *m_name;
	uint32_t m_index;
} dds_key_descriptor_t;
/**
 * Topic definitions are output by a preprocessor and have an<br>
 * implementation-private definition. The only thing exposed on the<br>
 * API is a pointer to the "topic_descriptor_t" struct type.
 */
typedef struct dds_topic_descriptor {
	const size_t m_size; /* Size of topic type */
	const uint32_t m_align; /* Alignment of topic type */
	const uint32_t m_flagset; /* Flags */
	const uint32_t m_nkeys; /* Number of keys (can be 0) */
	const char *m_typename; /* Type name */
	const dds_key_descriptor_t *m_keys; /* Key descriptors (NULL iff m_nkeys 0) */
	const uint32_t m_nops; /* Number of ops in m_ops */
	const uint32_t *m_ops; /* Marshalling meta data */
	const char *m_meta; /* XML topic description meta data */
} dds_topic_descriptor_t;
/**
 * Masks for read condition, read, take: there is only one mask here,<br>
 * which combines the sample, view and instance states.
 */
typedef enum dds_entity_kind {
	DDS_KIND_DONTCARE = 0x00000000,
	DDS_KIND_TOPIC = 0x01000000,
	DDS_KIND_PARTICIPANT = 0x02000000,
	DDS_KIND_READER = 0x03000000,
	DDS_KIND_WRITER = 0x04000000,
	DDS_KIND_SUBSCRIBER = 0x05000000,
	DDS_KIND_PUBLISHER = 0x06000000,
	DDS_KIND_COND_READ = 0x07000000,
	DDS_KIND_COND_QUERY = 0x08000000,
	DDS_KIND_COND_GUARD = 0x09000000,
	DDS_KIND_WAITSET = 0x0A000000,
	DDS_KIND_INTERNAL = 0x0B000000
} dds_entity_kind_t;
/** Handles are opaque pointers to implementation types */
typedef uint64_t dds_instance_handle_t;
typedef int32_t dds_domainid_t;
/**
 * Description : Enable or disable write batching. Overrides default configuration<br>
 * setting for write batching (DDSI2E/Internal/WriteBatch).<br>
 * * Arguments :<br>
 *   -# enable Enables or disables write batching for all writers.
 */
void dds_write_set_batch(bool enable);
/**
 * Description : Install tcp/ssl and encryption support. Depends on openssl.<br>
 * * Arguments :<br>
 *   -# None
 */
void dds_ssl_plugin();
/**
 * Description : Install client durability support. Depends on OSPL server.<br>
 * * Arguments :<br>
 *   -# None
 */
void dds_durability_plugin();
/**
 * Description : Returns the default DDS domain id. This can be configured<br>
 * in xml or set as an evironment variable ({DDSC_PROJECT_NAME_NOSPACE_CAPS}_DOMAIN).<br>
 * * Arguments :<br>
 *   -# None<br>
 *   -# Returns the default domain id
 */
dds_domainid_t dds_get_default_domainid();
/**
 * dds_sample_state_t<br>
 * \brief defines the state for a data value<br>
 * -# DDS_SST_READ - DataReader has already accessed the sample by read<br>
 * -# DDS_SST_NOT_READ - DataReader has not accessed that sample before
 */
typedef enum dds_sample_state {
	DDS_SST_READ = 1u,
	DDS_SST_NOT_READ = 2u
} dds_sample_state_t;
/**
 * dds_view_state_t<br>
 * \brief defines the view state of an instance relative to the samples<br>
 * -# DDS_VST_NEW - DataReader is accessing the sample for the first time when the<br>
 *                  instance is alive<br>
 * -# DDS_VST_OLD - DataReader has accessed the sample before
 */
typedef enum dds_view_state {
	DDS_VST_NEW = 4u,
	DDS_VST_OLD = 8u
} dds_view_state_t;
/**
 * dds_instance_state_t<br>
 * \brief defines the state of the instance<br>
 * -# DDS_IST_ALIVE - Samples received for the instance from the live data writers<br>
 * -# DDS_IST_NOT_ALIVE_DISPOSED - Instance was explicitly disposed by the data writer<br>
 * -# DDS_IST_NOT_ALIVE_NO_WRITERS - Instance has been declared as not alive by data reader<br>
 *                                   as there are no live data writers writing that instance
 */
typedef enum dds_instance_state {
	DDS_IST_ALIVE = 16u,
	DDS_IST_NOT_ALIVE_DISPOSED = 32u,
	DDS_IST_NOT_ALIVE_NO_WRITERS = 64u
} dds_instance_state_t;
/**
 * Structure dds_sample_info_t - contains information about the associated data value<br>
 * -# sample_state - \ref dds_sample_state_t<br>
 * -# view_state - \ref dds_view_state_t<br>
 * -# instance_state - \ref dds_instance_state_t<br>
 * -# valid_data - indicates whether there is a data associated with a sample<br>
 *    - true, indicates the data is valid<br>
 *    - false, indicates the data is invalid, no data to read<br>
 * -# source_timestamp - timestamp of a data instance when it is written<br>
 * -# instance_handle - handle to the data instance<br>
 * -# publication_handle - handle to the publisher<br>
 * -# disposed_generation_count - count of instance state change from<br>
 *    NOT_ALIVE_DISPOSED to ALIVE<br>
 * -# no_writers_generation_count - count of instance state change from<br>
 *    NOT_ALIVE_NO_WRITERS to ALIVE<br>
 * -# sample_rank - indicates the number of samples of the same instance<br>
 *    that follow the current one in the collection<br>
 * -# generation_rank - difference in generations between the sample and most recent sample<br>
 *    of the same instance that appears in the returned collection<br>
 * -# absolute_generation_rank - difference in generations between the sample and most recent sample<br>
 *    of the same instance when read/take was called<br>
 * -# reception_timestamp - timestamp of a data instance when it is added to a read queue
 */
typedef struct dds_sample_info {
	dds_sample_state_t sample_state;
	dds_view_state_t view_state;
	dds_instance_state_t instance_state;
	bool valid_data;
	dds_time_t source_timestamp;
	dds_instance_handle_t instance_handle;
	dds_instance_handle_t publication_handle;
	uint32_t disposed_generation_count;
	uint32_t no_writers_generation_count;
	uint32_t sample_rank;
	uint32_t generation_rank;
	uint32_t absolute_generation_rank;
	dds_time_t reception_timestamp; /* NOTE: VLite extension */
} dds_sample_info_t;
/**
 * @brief Enable entity.<br>
 * * @note Delayed entity enabling is not supported yet (CHAM-96).<br>
 * * This operation enables the dds_entity_t. Created dds_entity_t objects can start in<br>
 * either an enabled or disabled state. This is controlled by the value of the<br>
 * entityfactory policy on the corresponding parent entity for the given<br>
 * entity. Enabled entities are immediately activated at creation time meaning<br>
 * all their immutable QoS settings can no longer be changed. Disabled Entities are not<br>
 * yet activated, so it is still possible to change their immutable QoS settings. However,<br>
 * once activated the immutable QoS settings can no longer be changed.<br>
 * Creating disabled entities can make sense when the creator of the DDS_Entity<br>
 * does not yet know which QoS settings to apply, thus allowing another piece of code<br>
 * to set the QoS later on.<br>
 * * The default setting of DDS_EntityFactoryQosPolicy is such that, by default,<br>
 * entities are created in an enabled state so that it is not necessary to explicitly call<br>
 * dds_enable on newly-created entities.<br>
 * * The dds_enable operation produces the same results no matter how<br>
 * many times it is performed. Calling dds_enable on an already<br>
 * enabled DDS_Entity returns DDS_RETCODE_OK and has no effect.<br>
 * * If an Entity has not yet been enabled, the only operations that can be invoked<br>
 * on it are: the ones to set, get or copy the QosPolicy settings, the ones that set<br>
 * (or get) the Listener, the ones that get the Status and the dds_get_status_changes<br>
 * operation (although the status of a disabled entity never changes). Other operations<br>
 * will return the error DDS_RETCODE_NOT_ENABLED.<br>
 * * Entities created with a parent that is disabled, are created disabled regardless of<br>
 * the setting of the entityfactory policy.<br>
 * * Calling dds_enable on an Entity whose parent is not enabled<br>
 * will fail and return DDS_RETCODE_PRECONDITION_NOT_MET.<br>
 * * If the entityfactory policy has autoenable_created_entities<br>
 * set to TRUE, the dds_enable operation on the parent will<br>
 * automatically enable all child entities created with the parent.<br>
 * * The Listeners associated with an Entity are not called until the<br>
 * Entity is enabled. Conditions associated with an Entity that<br>
 * is not enabled are "inactive", that is, have a trigger_value which is FALSE.<br>
 * * @param[in]  e        The entity to enable.<br>
 * * @returns  0 - Success (DDS_RETCODE_OK).<br>
 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_OK<br>
 *                  The listeners of to the entity have been successfully been<br>
 *                  copied into the specified listener parameter.<br>
 * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The entity has already been deleted.<br>
 * @retval DDS_RETCODE_PRECONDITION_NOT_MET<br>
 *                  The parent of the given Entity is not enabled.
 */
dds_return_t dds_enable(dds_entity_t entity);
/**
 * @brief Delete given entity.<br>
 * * This operation will delete the given entity. It will also automatically<br>
 * delete all its children, childrens' children, etc entities.<br>
 * * TODO: Link to generic dds entity relations documentation.<br>
 * * @param[in]  entity  Entity from which to get its parent.<br>
 * * @returns  0 - Success (DDS_RETCODE_OK).<br>
 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  The entity and its children (recursive are deleted).<br>
 * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The entity has already been deleted.
 */
dds_return_t dds_delete(dds_entity_t entity);
/**
 * @brief Get entity publisher.<br>
 * * This operation returns the publisher to which the given entity belongs.<br>
 * For instance, it will return the Publisher that was used when<br>
 * creating a DataWriter (when that DataWriter was provided here).<br>
 * * TODO: Link to generic dds entity relations documentation.<br>
 * * @param[in]  entity  Entity from which to get its publisher.<br>
 * * @returns >0 - Success (valid entity handle).<br>
 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The entity has already been deleted.
 */
dds_entity_t dds_get_publisher(dds_entity_t writer);
/**
 * @brief Get entity subscriber.<br>
 * * This operation returns the subscriber to which the given entity belongs.<br>
 * For instance, it will return the Subscriber that was used when<br>
 * creating a DataReader (when that DataReader was provided here).<br>
 * * TODO: Link to generic dds entity relations documentation.<br>
 * * @param[in]  entity  Entity from which to get its subscriber.<br>
 * * @returns >0 - Success (valid entity handle).<br>
 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The entity has already been deleted.
 */
dds_entity_t dds_get_subscriber(dds_entity_t entity);
/**
 * @brief Get entity datareader.<br>
 * * This operation returns the datareader to which the given entity belongs.<br>
 * For instance, it will return the DataReader that was used when<br>
 * creating a ReadCondition (when that ReadCondition was provided here).<br>
 * * TODO: Link to generic dds entity relations documentation.<br>
 * * @param[in]  entity  Entity from which to get its datareader.<br>
 * * @returns >0 - Success (valid entity handle).<br>
 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The entity has already been deleted.
 */
dds_entity_t dds_get_datareader(dds_entity_t condition);
/**
 * @brief Get the mask of a condition.<br>
 * * This operation returns the mask that was used to create the given<br>
 * condition.<br>
 * * @param[in]  condition  Read or Query condition that has a mask.<br>
 * * @returns  0 - Success (given mask is set).<br>
 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                  The mask arg is NULL.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The entity has already been deleted.
 */
dds_return_t dds_get_mask(dds_entity_t condition, uint32_t* mask);
/** TODO: document. */
dds_return_t dds_instancehandle_get(dds_entity_t entity, dds_instance_handle_t* ihdl);
/**
 * Description : Read the status(es) set for the entity based on the enabled<br>
 * status and mask set. This operation does not clear the read status(es).<br>
 * * Arguments :<br>
 *   -# e Entity on which the status has to be read<br>
 *   -# status Returns the status set on the entity, based on the enabled status<br>
 *   -# mask Filter the status condition to be read (can be NULL)<br>
 *   -# Returns 0 on success, or a non-zero error value if the mask does not<br>
 *      correspond to the entity
 */
dds_return_t dds_read_status(dds_entity_t entity, uint32_t* status, uint32_t mask);
/**
 * Description : Read the status(es) set for the entity based on the enabled<br>
 * status and mask set. This operation clears the status set after reading.<br>
 * * Arguments :<br>
 *   -# e Entity on which the status has to be read<br>
 *   -# status Returns the status set on the entity, based on the enabled status<br>
 *   -# mask Filter the status condition to be read (can be NULL)<br>
 *   -# Returns 0 on success, or a non-zero error value if the mask does not<br>
 *      correspond to the entity
 */
dds_return_t dds_take_status(dds_entity_t entity, uint32_t* status, uint32_t mask);
/**
 * Description : Returns the status changes since they were last read.<br>
 * * Arguments :<br>
 *   -# e Entity on which the statuses are read<br>
 *   -# Returns the curent set of triggered statuses.
 */
dds_return_t dds_get_status_changes(dds_entity_t entity, uint32_t* status);
/**
 * Description : This operation returns the status enabled on the entity<br>
 * * Arguments :<br>
 *   -# e Entity to get the status<br>
 *   -# Returns the status that are enabled for the entity
 */
dds_return_t dds_get_enabled_status(dds_entity_t entity, uint32_t* status);
/**
 * Description : This operation enables the status(es) based on the mask set<br>
 * * Arguments :<br>
 *   -# e Entity to enable the status<br>
 *   -# mask Status value that indicates the status to be enabled<br>
 *   -# Returns 0 on success, or a non-zero error value indicating failure if the mask<br>
 *      does not correspond to the entity.
 */
dds_return_t dds_set_enabled_status(dds_entity_t entity, uint32_t mask);
/**
 * @brief Get entity QoS policies.<br>
 * * This operation allows access to the existing set of QoS policies<br>
 * for the entity.<br>
 * * TODO: Link to generic QoS information documentation.<br>
 * * @param[in]  e    Entity on which to get qos<br>
 * @param[out] qos  Pointer to the qos structure that returns the set policies<br>
 * * @returns  0 - Success (DDS_RETCODE_OK).<br>
 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_OK<br>
 *                  The existing set of QoS policy values applied to the<br>
 *                  entity has successfully been copied into the specified<br>
 *                  qos parameter.<br>
 * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                  The qos parameter is NULL.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The entity has already been deleted.
 */
dds_return_t dds_get_qos(dds_entity_t entity, dds_qos_t* qos);
/**
 * @brief Set entity QoS policies.<br>
 * * This operation replaces the existing set of Qos Policy settings for an<br>
 * entity. The parameter qos must contain the struct with the QosPolicy<br>
 * settings which is checked for self-consistency.<br>
 * * The set of QosPolicy settings specified by the qos parameter are applied on<br>
 * top of the existing QoS, replacing the values of any policies previously set<br>
 * (provided, the operation returned DDS_RETCODE_OK).<br>
 * * Not all policies are changeable when the entity is enabled.<br>
 * * TODO: Link to generic QoS information documentation.<br>
 * * @note Currently only Latency Budget and Ownership Strength are changeable QoS<br>
 *       that can be set.<br>
 * * @param[in]  e    Entity from which to get qos<br>
 * @param[in]  qos  Pointer to the qos structure that provides the policies<br>
 * * @returns  0 - Success (DDS_RETCODE_OK).<br>
 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_OK<br>
 *                  The new QoS policies are set.<br>
 * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                  The qos parameter is NULL.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The entity has already been deleted.<br>
 * @retval DDS_RETCODE_IMMUTABLE_POLICY<br>
 *                  The entity is enabled and one or more of the policies of<br>
 *                  the QoS are immutable.<br>
 * @retval DDS_RETCODE_INCONSISTENT_POLICY<br>
 *                  A few policies within the QoS are not consistent with<br>
 *                  each other.
 */
dds_return_t dds_set_qos(dds_entity_t entity, const dds_qos_t* qos);
/**
 * @brief Get entity listeners.<br>
 * * This operation allows access to the existing listeners attached to<br>
 * the entity.<br>
 * * TODO: Link to (generic) Listener and status information.<br>
 * * @param[in]  e        Entity on which to get the listeners<br>
 * @param[out] listener Pointer to the listener structure that returns the<br>
 *                      set of listener callbacks.<br>
 * * @returns  0 - Success (DDS_RETCODE_OK).<br>
 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_OK<br>
 *                  The listeners of to the entity have been successfully been<br>
 *                  copied into the specified listener parameter.<br>
 * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                  The listener parameter is NULL.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The entity has already been deleted.
 */
dds_return_t dds_get_listener(dds_entity_t entity, dds_listener_t* listener);
/**
 * @brief Set entity listeners.<br>
 * * This operation attaches a dds_listener_t to the dds_entity_t. Only one<br>
 * Listener can be attached to each Entity. If a Listener was already<br>
 * attached, this operation will replace it with the new one. In other<br>
 * words, all related callbacks are replaced (possibly with NULL).<br>
 * * When listener parameter is NULL, all listener callbacks that were possibly<br>
 * set on the Entity will be removed.<br>
 * * @note Not all listener callbacks are related to all entities.<br>
 * * TODO: Link to (generic) Listener and status information.<br>
 * * <b><i>Communication Status</i></b><br>
 * For each communication status, the StatusChangedFlag flag is initially set to<br>
 * FALSE. It becomes TRUE whenever that plain communication status changes. For<br>
 * each plain communication status activated in the mask, the associated<br>
 * Listener callback is invoked and the communication status is reset<br>
 * to FALSE, as the listener implicitly accesses the status which is passed as a<br>
 * parameter to that operation.<br>
 * The status is reset prior to calling the listener, so if the application calls<br>
 * the get_<status_name> from inside the listener it will see the<br>
 * status already reset.<br>
 * * <b><i>Status Propagation</i></b><br>
 * In case a related callback within the Listener is not set, the Listener of<br>
 * the Parent entity is called recursively, until a Listener with the appropriate<br>
 * callback set has been found and called. This allows the application to set<br>
 * (for instance) a default behaviour in the Listener of the containing Publisher<br>
 * and a DataWriter specific behaviour when needed. In case the callback is not<br>
 * set in the Publishers' Listener either, the communication status will be<br>
 * propagated to the Listener of the DomainParticipant of the containing<br>
 * DomainParticipant. In case the callback is not set in the DomainParticipants'<br>
 * Listener either, the Communication Status flag will be set, resulting in a<br>
 * possible WaitSet trigger.<br>
 * * @param[in]  e        Entity on which to get the listeners<br>
 * @param[in] listener  Pointer to the listener structure that contains the<br>
 *                      set of listener callbacks (maybe NULL).<br>
 * * @returns  0 - Success (DDS_RETCODE_OK).<br>
 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_OK<br>
 *                  The listeners of to the entity have been successfully been<br>
 *                  copied into the specified listener parameter.<br>
 * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The entity has already been deleted.
 */
dds_return_t dds_set_listener(dds_entity_t entity, const dds_listener_t* listener);
/**
 * @brief Creates a new instance of a DDS participant in a domain<br>
 * * If domain is set (not DDS_DOMAIN_DEFAULT) then it must match if the domain has also<br>
 * been configured or an error status will be returned. Currently only a single domain<br>
 * can be configured by setting the environment variable {DDSC_PROJECT_NAME_NOSPACE_CAPS}_DOMAIN,<br>
 * if this is not set the the default domain is 0. Valid values for domain id are between 0 and 230.<br>
 * *<br>
 * @param[in]  domain - The domain in which to create the participant (can be DDS_DOMAIN_DEFAULT)<br>
 * @param[in]  qos - The QoS to set on the new participant (can be NULL)<br>
 * @param[in]  listener - Any listener functions associated with the new participant (can be NULL)<br>
 * * @returns >0 - Success (valid handle of a participant entity).<br>
 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.
 */
dds_entity_t dds_create_participant(const dds_domainid_t domain, const dds_qos_t* qos, const dds_listener_t* listener);
/**
 * @brief Get entity parent.<br>
 * * This operation returns the parent to which the given entity belongs.<br>
 * For instance, it will return the Participant that was used when<br>
 * creating a Publisher (when that Publisher was provided here).<br>
 * * TODO: Link to generic dds entity relations documentation.<br>
 * * @param[in]  entity  Entity from which to get its parent.<br>
 * * @returns >0 - Success (valid entity handle).<br>
 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The entity has already been deleted.
 */
dds_entity_t dds_get_parent(dds_entity_t entity);
/**
 * @brief Get entity participant.<br>
 * * This operation returns the participant to which the given entity belongs.<br>
 * For instance, it will return the Participant that was used when<br>
 * creating a Publisher that was used to create a DataWriter (when that<br>
 * DataWriter was provided here).<br>
 * * TODO: Link to generic dds entity relations documentation.<br>
 * * @param[in]  entity  Entity from which to get its participant.<br>
 * * @returns >0 - Success (valid entity handle).<br>
 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The entity has already been deleted.
 */
dds_entity_t dds_get_participant(dds_entity_t entity);
/**
 * @brief Get entity children.<br>
 * * This operation returns the children that the entity contains.<br>
 * For instance, it will return all the Topics, Publishers and Subscribers<br>
 * of the Participant that was used to create those entities (when that<br>
 * Participant is provided here).<br>
 * * This functions takes a pre-allocated list to put the children in and<br>
 * will return the number of found children. It is possible that the given<br>
 * size of the list is not the same as the number of found children. If<br>
 * less children are found, then the last few entries in the list are<br>
 * untouched. When more children are found, then only 'size' number of<br>
 * entries are inserted into the list, but still complete count of the<br>
 * found children is returned. Which children are returned in the latter<br>
 * case is undefined.<br>
 * * When supplying NULL as list and 0 as size, you can use this to acquire<br>
 * the number of children without having to pre-allocate a list.<br>
 * * TODO: Link to generic dds entity relations documentation.<br>
 * * @param[in]  entity   Entity from which to get its children.<br>
 * @param[out] children Pre-allocated array to contain the found children.<br>
 * @param[in]  size     Size of the pre-allocated children's list.<br>
 * * @returns >=0 - Success (number of found children, can be larger than 'size').<br>
 * @returns  <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                  The children parameter is NULL, while a size is provided.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The entity has already been deleted.
 */
dds_return_t dds_get_children(dds_entity_t entity, dds_entity_t* children, size_t size);
/**
 * @brief Get the domain id to which this entity is attached.<br>
 * * When creating a participant entity, it is attached to a certain domain.<br>
 * All the children (like Publishers) and childrens' children (like<br>
 * DataReaders), etc are also attached to that domain.<br>
 * * This function will return the original domain ID when called on<br>
 * any of the entities within that hierarchy.<br>
 * * @param[in]  entity   Entity from which to get its children.<br>
 * @param[out] id       Pointer to put the domain ID in.<br>
 * * @returns  0 - Success (DDS_RETCODE_OK).<br>
 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_OK<br>
 *                  Domain ID was returned.<br>
 * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                  The id parameter is NULL.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The entity has already been deleted.
 */
dds_return_t dds_get_domainid(dds_entity_t entity, dds_domainid_t* id);
/**
 * @brief Get participants of a domain.<br>
 * * This operation acquires the participants created on a domain and returns<br>
 * the number of found participants.<br>
 * * This function takes a domain id with the size of pre-allocated participant's<br>
 * list in and will return the number of found participants. It is possible that<br>
 * the given size of the list is not the same as the number of found participants.<br>
 * If less participants are found, then the last few entries in an array stay<br>
 * untouched. If more participants are found and the array is too small, then the<br>
 * participants returned are undefined.<br>
 * * @param[in]  domain_id    The domain id<br>
 * @param[out] participants The participant for domain<br>
 * @param[in]  size         Size of the pre-allocated participant's list.<br>
 * * @returns >=0 - Success (number of found participants).<br>
 * @returns  <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                  The participant parameter is NULL, while a size is provided.
 */
dds_return_t dds_lookup_participant(dds_domainid_t domain_id, dds_entity_t* participants, size_t size);
/**
 * Description : Creates a new DDS topic. The type name for the topic<br>
 * is taken from the generated descriptor. Topic matching is done on a<br>
 * combination of topic name and type name.<br>
 * * Arguments :<br>
 *   -# pp The participant on which the topic is being created<br>
 *   -# descriptor The IDL generated topic descriptor<br>
 *   -# name The name of the created topic<br>
 *   -# qos The QoS to set on the new topic (can be NULL)<br>
 *   -# listener Any listener functions associated with the new topic (can be NULL)<br>
 *   -# Returns a status, 0 on success or non-zero value to indicate an error
 */
dds_entity_t dds_create_topic(dds_entity_t participant, const dds_topic_descriptor_t* descriptor, const char* name, const dds_qos_t* qos, const dds_listener_t* listener);
/**
 * Description : Finds a named topic. Returns NULL if does not exist.<br>
 * The returned topic should be released with dds_delete.<br>
 * * Arguments :<br>
 *   -# pp The participant on which to find the topic<br>
 *   -# name The name of the topic to find<br>
 *   -# Returns a topic, NULL if could not be found or error
 */
dds_entity_t dds_find_topic(dds_entity_t participant, const char* name);
/** TODO: Check annotation. Could be writes_to_(size, return + 1) as well. */
dds_return_t dds_get_name(dds_entity_t topic, char* name, size_t size);
/**
 * Description : Returns a topic type name.<br>
 * * Arguments :<br>
 *   -# topic The topic<br>
 *   -# Returns The topic type name or NULL to indicate an error
 */
dds_return_t dds_get_type_name(dds_entity_t topic, char* name, size_t size);
/**
 * @brief Creates a new instance of a DDS subscriber<br>
 * * @param[in]  participant The participant on which the subscriber is being created<br>
 * @param[in]  qos         The QoS to set on the new subscriber (can be NULL)<br>
 * @param[in]  listener    Any listener functions associated with the new subscriber (can be NULL)<br>
 * @returns >0 - Success (valid handle of a subscriber entity).<br>
 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 *         DDS_RETCODE_BAD_PARAMETER<br>
 *                  One of the parameters is invalid
 */
dds_entity_t dds_create_subscriber(dds_entity_t participant, const dds_qos_t* qos, const dds_listener_t* listener);
/**
 * @brief Creates a new instance of a DDS publisher<br>
 * * @param[in]  participant The participant to create a publisher for<br>
 * @param[in]  qos         The QoS to set on the new publisher (can be NULL)<br>
 * @param[in]  listener    Any listener functions associated with the new publisher (can be NULL)<br>
 * * @returns >0 - Success (valid handle of a publisher entity).<br>
 * @returns <0 - Failure (use dds_err_nr() to get error value).
 */
dds_entity_t dds_create_publisher(dds_entity_t participant, const dds_qos_t* qos, const dds_listener_t* listener);
/**
 * @brief Suspends the publications of the Publisher<br>
 * * This operation is a hint to the Service so it can optimize its performance by e.g., collecting<br>
 * modifications to DDS writers and then batching them. The Service is not required to use the hint.<br>
 * * Every invocation of this operation must be matched by a corresponding call to @see dds_resume<br>
 * indicating that the set of modifications has completed.<br>
 * * @param[in]  publisher The publisher for which all publications will be suspended<br>
 * * @returns >0 - Success.<br>
 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_OK<br>
 *                Publications suspended successfully.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                The pub parameter is not a valid publisher.<br>
 * @retval DDS_RETCODE_UNSUPPORTED<br>
 *                Operation is not supported
 */
dds_return_t dds_suspend(dds_entity_t publisher);
/**
 * @brief Resumes the publications of the Publisher<br>
 * * This operation is a hint to the Service to indicate that the application has completed changes<br>
 * initiated by a previous @see suspend. The Service is not required to use the hint.<br>
 * * The call to resume_publications must match a previous call to @see suspend_publications.<br>
 * * @param[in]  publisher The publisher for which all publications will be resumed<br>
 * * @returns >0 - Success.<br>
 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_OK<br>
 *                Publications resumed successfully.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                The pub parameter is not a valid publisher.<br>
 * @retval DDS_RETCODE_PRECONDITION_NOT_MET<br>
 *                No previous matching @see dds_suspend.<br>
 * @retval DDS_RETCODE_UNSUPPORTED<br>
 *                Operation is not supported.
 */
dds_return_t dds_resume(dds_entity_t publisher);
/**
 * @brief Waits at most for the duration timeout for acks for data in the publisher or writer.<br>
 * * This operation blocks the calling thread until either all data written by the publisher<br>
 * or writer is acknowledged by all matched reliable reader entities, or else the duration<br>
 * specified by the timeout parameter elapses, whichever happens first.<br>
 * * @param[in]  pub_or_w   The publisher or writer whose acknowledgements must be waited for.<br>
 * * @returns >0 - Success.<br>
 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_OK<br>
 *                All acknowledgements successfully received with the timeout.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                The pub_or_w parameter is not a valid publisher or writer.<br>
 * @retval DDS_RETCODE_TIMEOUT<br>
 *                Timeout expired before all acknowledgements from reliable reader entities were received.<br>
 * @retval DDS_RETCODE_UNSUPPORTED<br>
 *                Operation is not supported.
 */
dds_return_t dds_wait_for_acks(dds_entity_t publisher_or_writer, dds_duration_t timeout);
/**
 * @brief Creates a new instance of a DDS reader<br>
 * * @param[in]  participant_or_subscriber The participant or subscriber on which the reader is being created<br>
 * * @param[in]  topic The topic to read<br>
 * * @param[in]  qos The QoS to set on the new reader (can be NULL)<br>
 * * @param[in]  listener Any listener functions associated with the new reader (can be NULL)<br>
 * * @returns >0 - Success (valid handle of a reader entity)<br>
 * @returns <0 - Failure (use dds_err_nr() to get error value)
 */
dds_entity_t dds_create_reader(dds_entity_t participant_or_subscriber, dds_entity_t topic, const dds_qos_t* qos, const dds_listener_t* listener);
/** TODO: SAL-annotate TIMEOUT as a succesfull return as well? */
dds_return_t dds_wait_for_historical_data(dds_entity_t reader, dds_duration_t max_wait);
/**
 * @brief Creates a new instance of a DDS writer<br>
 * * @param[in]  participant_or_publisher The participant or publisher on which the writer is being created<br>
 * @param[in]  topic The topic to write<br>
 * @param[in]  qos The QoS to set on the new writer (can be NULL)<br>
 * @param[in]  listener Any listener functions associated with the new writer (can be NULL)<br>
 * * @returns >0 - Success (valid handle of a writer entity)<br>
 * @returns <0 - Failure (use dds_err_nr() to get error value)
 */
dds_entity_t dds_create_writer(dds_entity_t participant_or_publisher, dds_entity_t topic, const dds_qos_t* qos, const dds_listener_t* listener);
/**
 * Description : Registers an instance with a key value to the data writer<br>
 * * Arguments :<br>
 *   -# wr The writer to which instance has be associated<br>
 *   -# data Instance with the key value<br>
 *   -# Returns an instance handle that could be used for successive write & dispose operations or<br>
 *      NULL, if handle is not allocated
 */
dds_return_t dds_register_instance(dds_entity_t writer, dds_instance_handle_t* handle, const void* data);
/**
 * Description : Unregisters an instance with a key value from the data writer. Instance can be identified<br>
 *               either from data sample or from instance handle (at least one must be provided).<br>
 * * Arguments :<br>
 *   -# wr The writer to which instance is associated<br>
 *   -# data Instance with the key value (can be NULL if handle set)<br>
 *   -# handle Instance handle (can be DDS_HANDLE_NIL if data set)<br>
 *   -# Returns 0 on success, or non-zero value to indicate an error<br>
 * * Note : If an unregistered key ID is passed as instance data, an error is logged and not flagged as return value
 */
dds_return_t dds_unregister_instance(dds_entity_t writer, const void* data);
dds_return_t dds_unregister_instance_ih(dds_entity_t writer, dds_instance_handle_t handle);
dds_return_t dds_unregister_instance_ts(dds_entity_t writer, const void* data, dds_time_t timestamp);
dds_return_t dds_unregister_instance_ih_ts(dds_entity_t writer, dds_instance_handle_t handle, dds_time_t timestamp);
/**
 * @brief This operation modifies and disposes a data instance.<br>
 * * This operation requests the Data Distribution Service to modify the instance and<br>
 * mark it for deletion. Copies of the instance and its corresponding samples, which are<br>
 * stored in every connected reader and, dependent on the QoS policy settings (also in<br>
 * the Transient and Persistent stores) will be modified and marked for deletion by<br>
 * setting their dds_instance_state_t to DDS_IST_NOT_ALIVE_DISPOSED.<br>
 * * <b><i>Blocking</i></b><br>
 * If the history QoS policy is set to DDS_HISTORY_KEEP_ALL, the<br>
 * dds_writedispose operation on the writer may block if the modification<br>
 * would cause data to be lost because one of the limits, specified in the<br>
 * resource_limits QoS policy, to be exceeded. In case the synchronous<br>
 * attribute value of the reliability Qos policy is set to true for<br>
 * communicating writers and readers then the writer will wait until<br>
 * all synchronous readers have acknowledged the data. Under these<br>
 * circumstances, the max_blocking_time attribute of the reliability<br>
 * QoS policy configures the maximum time the dds_writedispose operation<br>
 * may block.<br>
 * If max_blocking_time elapses before the writer is able to store the<br>
 * modification without exceeding the limits and all expected acknowledgements<br>
 * are received, the dds_writedispose operation will fail and returns<br>
 * DDS_RETCODE_TIMEOUT.<br>
 * * @param[in]  writer The writer to dispose the data instance from.<br>
 * @param[in]  data   The data to be written and disposed.<br>
 * * @returns  0 - Success.<br>
 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_OK<br>
 *                The sample is written and the instance is marked for deletion.<br>
 * @retval DDS_RETCODE_ERROR<br>
 *                An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                At least one of the arguments is invalid.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                The entity has already been deleted.<br>
 * @retval DDS_RETCODE_TIMEOUT<br>
 *                Either the current action overflowed the available resources<br>
 *                as specified by the combination of the reliability QoS policy,<br>
 *                history QoS policy and resource_limits QoS policy, or the<br>
 *                current action was waiting for data delivery acknowledgement<br>
 *                by synchronous readers. This caused blocking of this operation,<br>
 *                which could not be resolved before max_blocking_time of the<br>
 *                reliability QoS policy elapsed.
 */
dds_return_t dds_writedispose(dds_entity_t writer, const void* data);
/**
 * Description : This operation modifies and disposes a data instance with<br>
 *               a specific timestamp.<br>
 * * This operation performs the same functions as dds_writedispose except that<br>
 * the application provides the value for the source_timestamp that is made<br>
 * available to connected reader objects. This timestamp is important for the<br>
 * interpretation of the destination_order QoS policy.<br>
 * * @param[in]  writer    The writer to dispose the data instance from.<br>
 * @param[in]  data      The data to be written and disposed.<br>
 * @param[in]  timestamp The timestamp used as source timestamp.<br>
 * * @returns  0 - Success.<br>
 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_OK<br>
 *                The sample is written and the instance is marked for deletion.<br>
 * @retval DDS_RETCODE_ERROR<br>
 *                An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                At least one of the arguments is invalid.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                The entity has already been deleted.<br>
 * @retval DDS_RETCODE_TIMEOUT<br>
 *                Either the current action overflowed the available resources<br>
 *                as specified by the combination of the reliability QoS policy,<br>
 *                history QoS policy and resource_limits QoS policy, or the<br>
 *                current action was waiting for data delivery acknowledgement<br>
 *                by synchronous readers. This caused blocking of this operation,<br>
 *                which could not be resolved before max_blocking_time of the<br>
 *                reliability QoS policy elapsed.
 */
dds_return_t dds_writedispose_ts(dds_entity_t writer, const void* data, dds_time_t timestamp);
/**
 * @brief This operation disposes an instance, identified by the data sample.<br>
 * * This operation requests the Data Distribution Service to modify the instance and<br>
 * mark it for deletion. Copies of the instance and its corresponding samples, which are<br>
 * stored in every connected reader and, dependent on the QoS policy settings (also in<br>
 * the Transient and Persistent stores) will be modified and marked for deletion by<br>
 * setting their dds_instance_state_t to DDS_IST_NOT_ALIVE_DISPOSED.<br>
 * * <b><i>Blocking</i></b><br>
 * If the history QoS policy is set to DDS_HISTORY_KEEP_ALL, the<br>
 * dds_writedispose operation on the writer may block if the modification<br>
 * would cause data to be lost because one of the limits, specified in the<br>
 * resource_limits QoS policy, to be exceeded. In case the synchronous<br>
 * attribute value of the reliability Qos policy is set to true for<br>
 * communicating writers and readers then the writer will wait until<br>
 * all synchronous readers have acknowledged the data. Under these<br>
 * circumstances, the max_blocking_time attribute of the reliability<br>
 * QoS policy configures the maximum time the dds_writedispose operation<br>
 * may block.<br>
 * If max_blocking_time elapses before the writer is able to store the<br>
 * modification without exceeding the limits and all expected acknowledgements<br>
 * are received, the dds_writedispose operation will fail and returns<br>
 * DDS_RETCODE_TIMEOUT.<br>
 * * @param[in]  writer The writer to dispose the data instance from.<br>
 * @param[in]  data   The data sample that identifies the instance<br>
 *                    to be disposed.<br>
 * * @returns  0 - Success.<br>
 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_OK<br>
 *                The sample is written and the instance is marked for deletion.<br>
 * @retval DDS_RETCODE_ERROR<br>
 *                An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                At least one of the arguments is invalid.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                The entity has already been deleted.<br>
 * @retval DDS_RETCODE_TIMEOUT<br>
 *                Either the current action overflowed the available resources<br>
 *                as specified by the combination of the reliability QoS policy,<br>
 *                history QoS policy and resource_limits QoS policy, or the<br>
 *                current action was waiting for data delivery acknowledgement<br>
 *                by synchronous readers. This caused blocking of this operation,<br>
 *                which could not be resolved before max_blocking_time of the<br>
 *                reliability QoS policy elapsed.
 */
dds_return_t dds_dispose(dds_entity_t writer, const void* data);
/**
 * Description : This operation disposes an instance with a specific timestamp,<br>
 *               identified by the data sample.<br>
 * * This operation performs the same functions as dds_dispose except that<br>
 * the application provides the value for the source_timestamp that is made<br>
 * available to connected reader objects. This timestamp is important for the<br>
 * interpretation of the destination_order QoS policy.<br>
 * * @param[in]  writer    The writer to dispose the data instance from.<br>
 * @param[in]  data      The data sample that identifies the instance<br>
 *                       to be disposed.<br>
 * @param[in]  timestamp The timestamp used as source timestamp.<br>
 * * @returns  0 - Success.<br>
 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_OK<br>
 *                The sample is written and the instance is marked for deletion.<br>
 * @retval DDS_RETCODE_ERROR<br>
 *                An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                At least one of the arguments is invalid.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                The entity has already been deleted.<br>
 * @retval DDS_RETCODE_TIMEOUT<br>
 *                Either the current action overflowed the available resources<br>
 *                as specified by the combination of the reliability QoS policy,<br>
 *                history QoS policy and resource_limits QoS policy, or the<br>
 *                current action was waiting for data delivery acknowledgement<br>
 *                by synchronous readers. This caused blocking of this operation,<br>
 *                which could not be resolved before max_blocking_time of the<br>
 *                reliability QoS policy elapsed.
 */
dds_return_t dds_dispose_ts(dds_entity_t writer, const void* data, dds_time_t timestamp);
/**
 * @brief This operation disposes an instance, identified by the instance handle.<br>
 * * This operation requests the Data Distribution Service to modify the instance and<br>
 * mark it for deletion. Copies of the instance and its corresponding samples, which are<br>
 * stored in every connected reader and, dependent on the QoS policy settings (also in<br>
 * the Transient and Persistent stores) will be modified and marked for deletion by<br>
 * setting their dds_instance_state_t to DDS_IST_NOT_ALIVE_DISPOSED.<br>
 * * <b><i>Instance Handle</i></b><br>
 * The given instance handle must correspond to the value that was returned by either<br>
 * the dds_register_instance operation, dds_register_instance_ts or dds_instance_lookup.<br>
 * If there is no correspondence, then the result of the operation is unspecified.<br>
 * * @param[in]  writer The writer to dispose the data instance from.<br>
 * @param[in]  handle The handle to identify an instance.<br>
 * * @returns  0 - Success.<br>
 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_OK<br>
 *                The sample is written and the instance is marked for deletion.<br>
 * @retval DDS_RETCODE_ERROR<br>
 *                An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                At least one of the arguments is invalid.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                The entity has already been deleted.<br>
 * @retval DDS_RETCODE_PRECONDITION_NOT_MET<br>
 *                The instance handle has not been registered with this writer.
 */
dds_return_t dds_dispose_ih(dds_entity_t writer, dds_instance_handle_t handle);
/**
 * Description : This operation disposes an instance with a specific timestamp,<br>
 *               identified by the instance handle.<br>
 * * This operation performs the same functions as dds_dispose_ih except that<br>
 * the application provides the value for the source_timestamp that is made<br>
 * available to connected reader objects. This timestamp is important for the<br>
 * interpretation of the destination_order QoS policy.<br>
 * * @param[in]  writer    The writer to dispose the data instance from.<br>
 * @param[in]  handle    The handle to identify an instance.<br>
 * @param[in]  timestamp The timestamp used as source timestamp.<br>
 * * @returns  0 - Success.<br>
 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_OK<br>
 *                The sample is written and the instance is marked for deletion.<br>
 * @retval DDS_RETCODE_ERROR<br>
 *                An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                At least one of the arguments is invalid.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                The entity has already been deleted.<br>
 * @retval DDS_RETCODE_PRECONDITION_NOT_MET<br>
 *                The instance handle has not been registered with this writer.
 */
dds_return_t dds_dispose_ih_ts(dds_entity_t writer, dds_instance_handle_t handle, dds_time_t timestamp);
/**
 * @brief Write the value of a data instance<br>
 * * With this API, the value of the source timestamp is automatically made<br>
 * available to the data reader by the service.<br>
 * * @param[in]  writer The writer entity<br>
 * @param[in]  data Value to be written<br>
 * * @returns - dds_return_t indicating success or failure
 */
dds_return_t dds_write(dds_entity_t writer, const void* data);
/**
 * @brief Write a CDR serialized value of a data instance<br>
 * * Untyped API, which take serialized blobs now.<br>
 * Whether they remain exposed like this with X-types isn't entirely clear yet.<br>
 * TODO: make a decide about dds_takecdr<br>
 * * @param[in]  writer The writer entity<br>
 * @param[in]  cdr CDR serialized value to be written<br>
 * @param[in]  size Size (in bytes) of CDR encoded data to be written<br>
 * * @returns - A dds_return_t indicating success or failure
 */
dds_return_t dds_writecdr(dds_entity_t writer, const void* cdr, size_t size);
/**
 * @brief Write the value of a data instance along with the source timestamp passed.<br>
 * * @param[in]  writer The writer entity<br>
 * @param[in]  data Value to be written<br>
 * @param[in]  timestamp Source timestamp<br>
 * * @returns - A dds_return_t indicating success or failure
 */
dds_return_t dds_write_ts(dds_entity_t writer, const void* data, dds_time_t timestamp);
/**
 * @brief Creates a readcondition associated to the given reader.<br>
 * * The readcondition allows specifying which samples are of interest in<br>
 * a data reader's history, by means of a mask. The mask is or'd with<br>
 * the flags that are dds_sample_state_t, dds_view_state_t and<br>
 * dds_instance_state_t.<br>
 * * Based on the mask value set, the readcondition gets triggered when<br>
 * data is available on the reader.<br>
 * * Waitsets allow waiting for an event on some of any set of entities.<br>
 * This means that the readcondition can be used to wake up a waitset when<br>
 * data is in the reader history with states that matches the given mask.<br>
 * * @note The parent reader and every of its associated conditions (whether<br>
 *       they are readconditions or queryconditions) share the same resources.<br>
 *       This means that one of these entities reads or takes data, the states<br>
 *       of the data will change for other entities automatically. For instance,<br>
 *       if one reads a sample, then the sample state will become 'read' for all<br>
 *       associated reader/conditions. Or if one takes a sample, then it's not<br>
 *       available to any other associated reader/condition.<br>
 * * @param[in]  reader  Reader to associate the condition to.<br>
 * @param[in]  mask    Interest (dds_sample_state_t|dds_view_state_t|dds_instance_state_t).<br>
 * * @returns >0 - Success (valid condition).<br>
 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The entity has already been deleted.
 */
dds_entity_t dds_create_readcondition(dds_entity_t reader, uint32_t mask);
/**
 * @brief Creates a queryondition associated to the given reader.<br>
 * * The queryondition allows specifying which samples are of interest in<br>
 * a data reader's history, by means of a mask and a filter. The mask is<br>
 * or'd with the flags that are dds_sample_state_t, dds_view_state_t and<br>
 * dds_instance_state_t.<br>
 * * TODO: Explain the filter (aka expression & parameters) of the (to be<br>
 *       implemented) new querycondition implementation.<br>
 * * Based on the mask value set and data that matches the filter, the<br>
 * querycondition gets triggered when data is available on the reader.<br>
 * * Waitsets allow waiting for an event on some of any set of entities.<br>
 * This means that the querycondition can be used to wake up a waitset when<br>
 * data is in the reader history with states that matches the given mask<br>
 * and filter.<br>
 * * @note The parent reader and every of its associated conditions (whether<br>
 *       they are readconditions or queryconditions) share the same resources.<br>
 *       This means that one of these entities reads or takes data, the states<br>
 *       of the data will change for other entities automatically. For instance,<br>
 *       if one reads a sample, then the sample state will become 'read' for all<br>
 *       associated reader/conditions. Or if one takes a sample, then it's not<br>
 *       available to any other associated reader/condition.<br>
 * * TODO: Update parameters when new querycondition is introduced.<br>
 * * @param[in]  reader  Reader to associate the condition to.<br>
 * @param[in]  mask    Interest (dds_sample_state_t|dds_view_state_t|dds_instance_state_t).<br>
 * @param[in]  filter  Callback that the application can use to filter specific samples.<br>
 * * @returns >0 - Success (valid condition).<br>
 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The entity has already been deleted.
 */
typedef bool (*dds_querycondition_filter_fn)(const void* sample);
dds_entity_t dds_create_querycondition(dds_entity_t reader, uint32_t mask, dds_querycondition_filter_fn filter);
/**
 * @brief Waitset attachment argument.<br>
 * * Every entity that is attached to the waitset can be accompanied by such<br>
 * an attachment argument. When the waitset wait is unblocked because of an<br>
 * entity that triggered, then the returning array will be populated with<br>
 * these attachment arguments that are related to the triggered entity.
 */
typedef void *dds_attach_t;
/**
 * @brief Create a waitset and allocate the resources required<br>
 * * A WaitSet object allows an application to wait until one or more of the<br>
 * conditions of the attached entities evaluates to TRUE or until the timeout<br>
 * expires.<br>
 * * @param[in]  participant  Domain participant which the WaitSet contains.<br>
 * * @returns >0 - Success (valid waitset).<br>
 * @returns <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The entity has already been deleted.
 */
dds_entity_t dds_create_waitset(dds_entity_t participant);
/**
 * @brief Acquire previously attached entities.<br>
 * * This functions takes a pre-allocated list to put the entities in and<br>
 * will return the number of found entities. It is possible that the given<br>
 * size of the list is not the same as the number of found entities. If<br>
 * less entities are found, then the last few entries in the list are<br>
 * untouched. When more entities are found, then only 'size' number of<br>
 * entries are inserted into the list, but still the complete count of the<br>
 * found entities is returned. Which entities are returned in the latter<br>
 * case is undefined.<br>
 * * @param[in]  waitset  Waitset from which to get its attached entities.<br>
 * @param[out] entities Pre-allocated array to contain the found entities.<br>
 * @param[in]  size     Size of the pre-allocated entities' list.<br>
 * * @returns >=0 - Success (number of found children, can be larger than 'size').<br>
 * @returns  <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                  The entities parameter is NULL, while a size is provided.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The waitset has already been deleted.
 */
dds_return_t dds_waitset_get_entities(dds_entity_t waitset, dds_entity_t* entities, size_t size);
/**
 * @brief This operation attaches an Entity to the WaitSet.<br>
 * * This operation attaches an Entity to the WaitSet. The dds_waitset_wait()<br>
 * will block when none of the attached entities are triggered. 'Triggered'<br>
 * (dds_triggered()) doesn't mean the same for every entity:<br>
 *  - Reader/Writer/Publisher/Subscriber/Topic/Participant<br>
 *      - These are triggered when their status changed.<br>
 *  - WaitSet<br>
 *      - Triggered when trigger value was set to true by the application.<br>
 *        It stays triggered until application sets the trigger value to<br>
 *        false (dds_waitset_set_trigger()). This can be used to wake up an<br>
 *        waitset for different reasons (f.i. termination) than the 'normal'<br>
 *        status change (like new data).<br>
 *  - ReadCondition/QueryCondition<br>
 *      - Triggered when data is available on the related Reader that matches<br>
 *        the Condition.<br>
 * * Multiple entities can be attached to a single waitset. A particular entity<br>
 * can be attached to multiple waitsets. However, a particular entity can not<br>
 * be attached to a particular waitset multiple times.<br>
 * * @param[in]  waitset  The waitset to attach the given entity to.<br>
 * @param[in]  entity   The entity to attach.<br>
 * @param[in]  x        Blob that will be supplied when the waitset wait is<br>
 *                      triggerd by the given entity.<br>
 * * @returns   0 - Success (entity attached).<br>
 * @returns  <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                  The given waitset or entity are not valid.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The waitset has already been deleted.<br>
 * @retval DDS_RETCODE_PRECONDITION_NOT_MET<br>
 *                  The entity was already attached.
 */
dds_return_t dds_waitset_attach(dds_entity_t waitset, dds_entity_t entity, dds_attach_t x);
/**
 * @brief This operation detaches an Entity to the WaitSet.<br>
 * * @param[in]  waitset  The waitset to detach the given entity from.<br>
 * @param[in]  entity   The entity to detach.<br>
 * * @returns   0 - Success (entity attached).<br>
 * @returns  <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                  The given waitset or entity are not valid.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The waitset has already been deleted.<br>
 * @retval DDS_RETCODE_PRECONDITION_NOT_MET<br>
 *                  The entity is not attached.
 */
dds_return_t dds_waitset_detach(dds_entity_t waitset, dds_entity_t entity);
/**
 * @brief Sets the trigger_value associated with a waitset.<br>
 * * When the waitset is attached to itself and the trigger value is<br>
 * set to 'true', then the waitset will wake up just like with an<br>
 * other status change of the attached entities.<br>
 * * This can be used to forcefully wake up a waitset, for instance<br>
 * when the application wants to shut down. So, when the trigger<br>
 * value is true, the waitset will wake up or not wait at all.<br>
 * * The trigger value will remain true until the application sets it<br>
 * false again deliberately.<br>
 * * @param[in]  waitset  The waitset to set the trigger value on.<br>
 * @param[in]  trigger  The trigger value to set.<br>
 * * @returns   0 - Success (entity attached).<br>
 * @returns  <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                  The given waitset is not valid.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The waitset has already been deleted.
 */
dds_return_t dds_waitset_set_trigger(dds_entity_t waitset, bool trigger);
/**
 * @brief This operation allows an application thread to wait for the a status<br>
 *        change or other trigger on (one of) the entities that are attached to<br>
 *        the WaitSet.<br>
 * * The "dds_waitset_wait" operation blocks until the some of the attached<br>
 * entities have triggered or "reltimeout" has elapsed.<br>
 * 'Triggered' (dds_triggered()) doesn't mean the same for every entity:<br>
 *  - Reader/Writer/Publisher/Subscriber/Topic/Participant<br>
 *      - These are triggered when their status changed.<br>
 *  - WaitSet<br>
 *      - Triggered when trigger value was set to true by the application.<br>
 *        It stays triggered until application sets the trigger value to<br>
 *        false (dds_waitset_set_trigger()). This can be used to wake up an<br>
 *        waitset for different reasons (f.i. termination) than the 'normal'<br>
 *        status change (like new data).<br>
 *  - ReadCondition/QueryCondition<br>
 *      - Triggered when data is available on the related Reader that matches<br>
 *        the Condition.<br>
 * * This functions takes a pre-allocated list to put the "xs" blobs in (that<br>
 * were provided during the attach of the related entities) and will return<br>
 * the number of triggered entities. It is possible that the given size<br>
 * of the list is not the same as the number of triggered entities. If less<br>
 * entities were triggered, then the last few entries in the list are<br>
 * untouched. When more entities are triggered, then only 'size' number of<br>
 * entries are inserted into the list, but still the complete count of the<br>
 * triggered entities is returned. Which "xs" blobs are returned in the<br>
 * latter case is undefined.<br>
 * * In case of a time out, the return value is 0.<br>
 * * Deleting the waitset while the application is blocked results in an<br>
 * error code (i.e. < 0) returned by "wait".<br>
 * * Multiple threads may block on a single waitset at the same time;<br>
 * the calls are entirely independent.<br>
 * * An empty waitset never triggers (i.e., dds_waitset_wait on an empty<br>
 * waitset is essentially equivalent to a sleep).<br>
 * * The "dds_waitset_wait_until" operation is the same as the<br>
 * "dds_waitset_wait" except that it takes an absolute timeout.<br>
 * * @param[in]  waitset    The waitset to set the trigger value on.<br>
 * @param[out] xs         Pre-allocated list to store the 'blobs' that were<br>
 *                        provided during the attach of the triggered entities.<br>
 * @param[in]  nxs        The size of the pre-allocated blobs list.<br>
 * @param[in]  reltimeout Relative timeout<br>
 * * @returns  >0 - Success (number of entities triggered).<br>
 * @returns   0 - Time out (no entities were triggered).<br>
 * @returns  <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                  The given waitset is not valid.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The waitset has already been deleted.
 */
dds_return_t dds_waitset_wait(dds_entity_t waitset, dds_attach_t* xs, size_t nxs, dds_duration_t reltimeout);
/**
 * @brief This operation allows an application thread to wait for the a status<br>
 *        change or other trigger on (one of) the entities that are attached to<br>
 *        the WaitSet.<br>
 * * The "dds_waitset_wait" operation blocks until the some of the attached<br>
 * entities have triggered or "abstimeout" has been reached.<br>
 * 'Triggered' (dds_triggered()) doesn't mean the same for every entity:<br>
 *  - Reader/Writer/Publisher/Subscriber/Topic/Participant<br>
 *      - These are triggered when their status changed.<br>
 *  - WaitSet<br>
 *      - Triggered when trigger value was set to true by the application.<br>
 *        It stays triggered until application sets the trigger value to<br>
 *        false (dds_waitset_set_trigger()). This can be used to wake up an<br>
 *        waitset for different reasons (f.i. termination) than the 'normal'<br>
 *        status change (like new data).<br>
 *  - ReadCondition/QueryCondition<br>
 *      - Triggered when data is available on the related Reader that matches<br>
 *        the Condition.<br>
 * * This functions takes a pre-allocated list to put the "xs" blobs in (that<br>
 * were provided during the attach of the related entities) and will return<br>
 * the number of triggered entities. It is possible that the given size<br>
 * of the list is not the same as the number of triggered entities. If less<br>
 * entities were triggered, then the last few entries in the list are<br>
 * untouched. When more entities are triggered, then only 'size' number of<br>
 * entries are inserted into the list, but still the complete count of the<br>
 * triggered entities is returned. Which "xs" blobs are returned in the<br>
 * latter case is undefined.<br>
 * * In case of a time out, the return value is 0.<br>
 * * Deleting the waitset while the application is blocked results in an<br>
 * error code (i.e. < 0) returned by "wait".<br>
 * * Multiple threads may block on a single waitset at the same time;<br>
 * the calls are entirely independent.<br>
 * * An empty waitset never triggers (i.e., dds_waitset_wait on an empty<br>
 * waitset is essentially equivalent to a sleep).<br>
 * * The "dds_waitset_wait" operation is the same as the<br>
 * "dds_waitset_wait_until" except that it takes an relative timeout.<br>
 * * The "dds_waitset_wait" operation is the same as the "dds_wait"<br>
 * except that it takes an absolute timeout.<br>
 * * @param[in]  waitset    The waitset to set the trigger value on.<br>
 * @param[out] xs         Pre-allocated list to store the 'blobs' that were<br>
 *                        provided during the attach of the triggered entities.<br>
 * @param[in]  nxs        The size of the pre-allocated blobs list.<br>
 * @param[in]  abstimeout Absolute timeout<br>
 * * @returns  >0 - Success (number of entities triggered).<br>
 * @returns   0 - Time out (no entities were triggered).<br>
 * @returns  <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                  The given waitset is not valid.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The waitset has already been deleted.
 */
dds_return_t dds_waitset_wait_until(dds_entity_t waitset, dds_attach_t* xs, size_t nxs, dds_time_t abstimeout);
/**
 * @brief Access and read the collection of data values (of same type) and sample info from the<br>
 *        data reader, readcondition or querycondition.<br>
 * * Return value provides information about number of samples read, which will<br>
 * be <= maxs. Based on the count, the buffer will contain data to be read only<br>
 * when valid_data bit in sample info structure is set.<br>
 * The buffer required for data values, could be allocated explicitly or can<br>
 * use the memory from data reader to prevent copy. In the latter case, buffer and<br>
 * sample_info should be returned back, once it is no longer using the Data.<br>
 * Data values once read will remain in the buffer with the sample_state set to READ<br>
 * and view_state set to NOT_NEW.<br>
 * * @param[in]  reader_or_condition Reader, readcondition or querycondition entity<br>
 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL)<br>
 * @param[out] si Pointer to an array of \ref dds_sample_info_t returned for each data value<br>
 * @param[in]  bufsz The size of buffer provided<br>
 * @param[in]  maxs Maximum number of samples to read<br>
 * * @returns >=0 - Success (number of samples read).<br>
 * @returns  <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                  One of the given arguments is not valid.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The entity has already been deleted.
 */
dds_return_t dds_read(dds_entity_t reader_or_condition, void** buf, dds_sample_info_t* si, size_t bufsz, uint32_t maxs);
/**
 * @brief Access and read loaned samples of data reader, readcondition or querycondition.<br>
 * * After dds_read_wl function is being called and the data has been handled, dds_return_loan function must be called to possibly free memory<br>
 * * @param[in]  reader_or_condition Reader, readcondition or querycondition entity<br>
 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL)<br>
 * @param[out] si Pointer to an array of \ref dds_sample_info_t returned for each data value<br>
 * @param[in]  maxs Maximum number of samples to read<br>
 * * @returns >=0 - Success (number of samples read).<br>
 * @returns  <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                  One of the given arguments is not valid.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The entity has already been deleted.
 */
dds_return_t dds_read_wl(dds_entity_t reader_or_condition, void** buf, dds_sample_info_t* si, uint32_t maxs);
/**
 * @brief Read the collection of data values and sample info from the data reader, readcondition<br>
 *        or querycondition based on mask.<br>
 * * When using a readcondition or querycondition, their masks are or'd with the given mask.<br>
 * *<br>
 * @param[in]  reader_or_condition Reader, readcondition or querycondition entity<br>
 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL)<br>
 * @param[out] si Pointer to an array of \ref dds_sample_info_t returned for each data value<br>
 * @param[in]  bufsz The size of buffer provided<br>
 * @param[in]  maxs Maximum number of samples to read<br>
 * @param[in]  mask Filter the data based on dds_sample_state_t|dds_view_state_t|dds_instance_state_t.<br>
 * * @returns >=0 - Success (number of samples read).<br>
 * @returns  <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                  One of the given arguments is not valid.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The entity has already been deleted.
 */
dds_return_t dds_read_mask(dds_entity_t reader_or_condition, void** buf, dds_sample_info_t* si, size_t bufsz, uint32_t maxs, uint32_t mask);
/**
 * @brief Access and read loaned samples of data reader, readcondition<br>
 *        or querycondition based on mask<br>
 * * When using a readcondition or querycondition, their masks are or'd with the given mask.<br>
 * * After dds_read_mask_wl function is being called and the data has been handled, dds_return_loan function must be called to possibly free memory<br>
 * * @param[in]  reader_or_condition Reader, readcondition or querycondition entity<br>
 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL)<br>
 * @param[out] si Pointer to an array of \ref dds_sample_info_t returned for each data value<br>
 * @param[in]  maxs Maximum number of samples to read<br>
 * @param[in]  mask Filter the data based on dds_sample_state_t|dds_view_state_t|dds_instance_state_t.<br>
 * * @returns >=0 - Success (number of samples read).<br>
 * @returns  <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                  One of the given arguments is not valid.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The entity has already been deleted.
 */
dds_return_t dds_read_mask_wl(dds_entity_t reader_or_condition, void** buf, dds_sample_info_t* si, uint32_t maxs, uint32_t mask);
/**
 * @brief Access and read the collection of data values (of same type) and sample info from the<br>
 *        data reader, readcondition or querycondition, coped by the provided instance handle.<br>
 * * This operation implements the same functionality as dds_read, except that only data scoped to<br>
 * the provided instance handle is read.<br>
 * * @param[in]  reader_or_condition Reader, readcondition or querycondition entity<br>
 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL)<br>
 * @param[out] si Pointer to an array of \ref dds_sample_info_t returned for each data value<br>
 * @param[in]  bufsz The size of buffer provided<br>
 * @param[in]  maxs Maximum number of samples to read<br>
 * @param[in]  handle Instance handle related to the samples to read<br>
 * * @returns >=0 - Success (number of samples read).<br>
 * @returns  <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                  One of the given arguments is not valid.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The entity has already been deleted.<br>
 * @retval DDS_RETCODE_PRECONDITION_NOT_MET<br>
 *                  The instance handle has not been registered with this reader.
 */
dds_return_t dds_read_instance(dds_entity_t reader_or_condition, void** buf, dds_sample_info_t* si, size_t bufsz, uint32_t maxs, dds_instance_handle_t handle);
/**
 * @brief Access and read loaned samples of data reader, readcondition or querycondition,<br>
 *        scoped by the provided instance handle.<br>
 * * This operation implements the same functionality as dds_read_wl, except that only data<br>
 *        scoped to the provided instance handle is read.<br>
 * * @param[in]  reader_or_condition Reader, readcondition or querycondition entity<br>
 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL)<br>
 * @param[out] si Pointer to an array of \ref dds_sample_info_t returned for each data value<br>
 * @param[in]  maxs Maximum number of samples to read<br>
 * @param[in]  handle Instance handle related to the samples to read<br>
 * * @returns >=0 - Success (number of samples read).<br>
 * @returns  <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                  One of the given arguments is not valid.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The entity has already been deleted.<br>
 * @retval DDS_RETCODE_PRECONDITION_NOT_MET<br>
 *                  The instance handle has not been registered with this reader.
 */
dds_return_t dds_read_instance_wl(dds_entity_t reader_or_condition, void** buf, dds_sample_info_t* si, uint32_t maxs, dds_instance_handle_t handle);
/**
 * @brief Read the collection of data values and sample info from the data reader, readcondition<br>
 *        or querycondition based on mask and scoped by the provided instance handle.<br>
 * * This operation implements the same functionality as dds_read_mask, except that only data<br>
 *        scoped to the provided instance handle is read.<br>
 * * @param[in]  reader_or_condition Reader, readcondition or querycondition entity<br>
 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL)<br>
 * @param[out] si Pointer to an array of \ref dds_sample_info_t returned for each data value<br>
 * @param[in]  bufsz The size of buffer provided<br>
 * @param[in]  maxs Maximum number of samples to read<br>
 * @param[in]  handle Instance handle related to the samples to read<br>
 * @param[in]  mask Filter the data based on dds_sample_state_t|dds_view_state_t|dds_instance_state_t.<br>
 * * @returns >=0 - Success (number of samples read).<br>
 * @returns  <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                  One of the given arguments is not valid.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The entity has already been deleted.<br>
 * @retval DDS_RETCODE_PRECONDITION_NOT_MET<br>
 *                  The instance handle has not been registered with this reader.
 */
dds_return_t dds_read_instance_mask(dds_entity_t reader_or_condition, void** buf, dds_sample_info_t* si, size_t bufsz, uint32_t maxs, dds_instance_handle_t handle, uint32_t mask);
/**
 * @brief Access and read loaned samples of data reader, readcondition or<br>
 *        querycondition based on mask, scoped by the provided instance handle.<br>
 * * This operation implements the same functionality as dds_read_mask_wl, except that<br>
 * only data scoped to the provided instance handle is read.<br>
 * * @param[in]  reader_or_condition Reader, readcondition or querycondition entity<br>
 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL)<br>
 * @param[out] si Pointer to an array of \ref dds_sample_info_t returned for each data value<br>
 * @param[in]  maxs Maximum number of samples to read<br>
 * @param[in]  handle Instance handle related to the samples to read<br>
 * @param[in]  mask Filter the data based on dds_sample_state_t|dds_view_state_t|dds_instance_state_t.<br>
 * * @returns >=0 - Success (number of samples read).<br>
 * @returns  <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                  One of the given arguments is not valid.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The entity has already been deleted.<br>
 * @retval DDS_RETCODE_PRECONDITION_NOT_MET<br>
 *                  The instance handle has not been registered with this reader.
 */
dds_return_t dds_read_instance_mask_wl(dds_entity_t reader_or_condition, void** buf, dds_sample_info_t* si, uint32_t maxs, dds_instance_handle_t handle, uint32_t mask);
/**
 * @brief Access the collection of data values (of same type) and sample info from the<br>
 *        data reader, readcondition or querycondition.<br>
 * * Data value once read is removed from the Data Reader cannot to<br>
 * 'read' or 'taken' again.<br>
 * Return value provides information about number of samples read, which will<br>
 * be <= maxs. Based on the count, the buffer will contain data to be read only<br>
 * when valid_data bit in sample info structure is set.<br>
 * The buffer required for data values, could be allocated explicitly or can<br>
 * use the memory from data reader to prevent copy. In the latter case, buffer and<br>
 * sample_info should be returned back, once it is no longer using the Data.<br>
 * * @param[in]  reader_or_condition Reader, readcondition or querycondition entity<br>
 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL)<br>
 * @param[out] si Pointer to an array of \ref dds_sample_info_t returned for each data value<br>
 * @param[in]  bufsz The size of buffer provided<br>
 * @param[in]  maxs Maximum number of samples to read<br>
 * * @returns >=0 - Success (number of samples read).<br>
 * @returns  <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                  One of the given arguments is not valid.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The entity has already been deleted.
 */
dds_return_t dds_take(dds_entity_t reader_or_condition, void** buf, dds_sample_info_t* si, size_t bufsz, uint32_t maxs);
/**
 * @brief Access loaned samples of data reader, readcondition or querycondition.<br>
 * * After dds_take_wl function is being called and the data has been handled, dds_return_loan function must be called to possibly free memory<br>
 * * @param[in]  reader_or_condition Reader, readcondition or querycondition entity<br>
 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL)<br>
 * @param[out] si Pointer to an array of \ref dds_sample_info_t returned for each data value<br>
 * @param[in]  maxs Maximum number of samples to read<br>
 * * @returns >=0 - Success (number of samples read).<br>
 * @returns  <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                  One of the given arguments is not valid.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The entity has already been deleted.
 */
dds_return_t dds_take_wl(dds_entity_t reader_or_condition, void** buf, dds_sample_info_t* si, uint32_t maxs);
/**
 * @brief Take the collection of data values (of same type) and sample info from the<br>
 *        data reader, readcondition or querycondition based on mask<br>
 * * When using a readcondition or querycondition, their masks are or'd with the given mask.<br>
 * * @param[in]  reader_or_condition Reader, readcondition or querycondition entity<br>
 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL)<br>
 * @param[out] si Pointer to an array of \ref dds_sample_info_t returned for each data value<br>
 * @param[in]  bufsz The size of buffer provided<br>
 * @param[in]  maxs Maximum number of samples to read<br>
 * @param[in]  mask Filter the data based on dds_sample_state_t|dds_view_state_t|dds_instance_state_t.<br>
 * * @returns >=0 - Success (number of samples read).<br>
 * @returns  <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                  One of the given arguments is not valid.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The entity has already been deleted.
 */
dds_return_t dds_take_mask(dds_entity_t reader_or_condition, void** buf, dds_sample_info_t* si, size_t bufsz, uint32_t maxs, uint32_t mask);
/**
 * @brief  Access loaned samples of data reader, readcondition or querycondition based on mask.<br>
 * * When using a readcondition or querycondition, their masks are or'd with the given mask.<br>
 * * After dds_take_mask_wl function is being called and the data has been handled, dds_return_loan function must be called to possibly free memory<br>
 * * @param[in]  reader_or_condition Reader, readcondition or querycondition entity<br>
 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL)<br>
 * @param[out] si Pointer to an array of \ref dds_sample_info_t returned for each data value<br>
 * @param[in]  maxs Maximum number of samples to read<br>
 * @param[in]  mask Filter the data based on dds_sample_state_t|dds_view_state_t|dds_instance_state_t.<br>
 * * @returns >=0 - Success (number of samples read).<br>
 * @returns  <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                  One of the given arguments is not valid.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The entity has already been deleted.
 */
dds_return_t dds_take_mask_wl(dds_entity_t reader_or_condition, void** buf, dds_sample_info_t* si, uint32_t maxs, uint32_t mask);
/**
 * Untyped API, which take serialized blobs now.<br>
 * Whether they remain exposed like this with X-types isn't entirely clear yet.<br>
 * TODO: make a decide about dds_takecdr<br>
 * If we want dds_takecdr(), shouldn't there be a dds_readcdr()?
 */
struct serdata;
int dds_takecdr(dds_entity_t reader_or_condition, struct serdata** buf, uint32_t maxs, dds_sample_info_t* si, uint32_t mask);
/**
 * @brief Access the collection of data values (of same type) and sample info from the<br>
 *        data reader, readcondition or querycondition but scoped by the given<br>
 *        instance handle.<br>
 * * This operation mplements the same functionality as dds_take, except that only data<br>
 *        scoped to the provided instance handle is taken.<br>
 * * @param[in]  reader_or_condition Reader, readcondition or querycondition entity<br>
 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL)<br>
 * @param[out] si Pointer to an array of \ref dds_sample_info_t returned for each data value<br>
 * @param[in]  bufsz The size of buffer provided<br>
 * @param[in]  maxs Maximum number of samples to read<br>
 * @param[in]  handle Instance handle related to the samples to read<br>
 * * @returns >=0 - Success (number of samples read).<br>
 * @returns  <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                  One of the given arguments is not valid.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The entity has already been deleted.<br>
 * @retval DDS_RETCODE_PRECONDITION_NOT_MET<br>
 *                  The instance handle has not been registered with this reader.
 */
dds_return_t dds_take_instance(dds_entity_t reader_or_condition, void** buf, dds_sample_info_t* si, size_t bufsz, uint32_t maxs, dds_instance_handle_t handle);
/**
 * @brief Access loaned samples of data reader, readcondition or querycondition,<br>
 *        scoped by the given instance handle.<br>
 * * This operation implements the same functionality as dds_take_wl, except that<br>
 * only data scoped to the provided instance handle is read.<br>
 * * @param[in]  reader_or_condition Reader, readcondition or querycondition entity<br>
 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL)<br>
 * @param[out] si Pointer to an array of \ref dds_sample_info_t returned for each data value<br>
 * @param[in]  maxs Maximum number of samples to read<br>
 * @param[in]  handle Instance handle related to the samples to read<br>
 * * @returns >=0 - Success (number of samples read).<br>
 * @returns  <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                  One of the given arguments is not valid.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The entity has already been deleted.<br>
 * @retval DDS_RETCODE_PRECONDITION_NOT_MET<br>
 *                  The instance handle has not been registered with this reader.
 */
dds_return_t dds_take_instance_wl(dds_entity_t reader_or_condition, void** buf, dds_sample_info_t* si, uint32_t maxs, dds_instance_handle_t handle);
/**
 * @brief Take the collection of data values (of same type) and sample info from the<br>
 *        data reader, readcondition or querycondition based on mask and scoped<br>
 *        by the given instance handle.<br>
 * * This operation implements the same functionality as dds_take_mask, except that only<br>
 * data scoped to the provided instance handle is read.<br>
 * * @param[in]  reader_or_condition Reader, readcondition or querycondition entity<br>
 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL)<br>
 * @param[out] si Pointer to an array of \ref dds_sample_info_t returned for each data value<br>
 * @param[in]  bufsz The size of buffer provided<br>
 * @param[in]  maxs Maximum number of samples to read<br>
 * @param[in]  handle Instance handle related to the samples to read<br>
 * @param[in]  mask Filter the data based on dds_sample_state_t|dds_view_state_t|dds_instance_state_t.<br>
 * * @returns >=0 - Success (number of samples read).<br>
 * @returns  <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                  One of the given arguments is not valid.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The entity has already been deleted.<br>
 * @retval DDS_RETCODE_PRECONDITION_NOT_MET<br>
 *                  The instance handle has not been registered with this reader.
 */
dds_return_t dds_take_instance_mask(dds_entity_t reader_or_condition, void** buf, dds_sample_info_t* si, size_t bufsz, uint32_t maxs, dds_instance_handle_t handle, uint32_t mask);
/**
 * @brief  Access loaned samples of data reader, readcondition or querycondition based<br>
 *         on mask and scoped by the given intance handle.<br>
 * * This operation implements the same functionality as dds_take_mask_wl, except that<br>
 * only data scoped to the provided instance handle is read.<br>
 * * @param[in]  reader_or_condition Reader, readcondition or querycondition entity<br>
 * @param[out] buf An array of pointers to samples into which data is read (pointers can be NULL)<br>
 * @param[out] si Pointer to an array of \ref dds_sample_info_t returned for each data value<br>
 * @param[in]  maxs Maximum number of samples to read<br>
 * @param[in]  handle Instance handle related to the samples to read<br>
 * @param[in]  mask Filter the data based on dds_sample_state_t|dds_view_state_t|dds_instance_state_t.<br>
 * * @returns >=0 - Success (number of samples read).<br>
 * @returns  <0 - Failure (use dds_err_nr() to get error value).<br>
 * * @retval DDS_RETCODE_ERROR<br>
 *                  An internal error has occurred.<br>
 * @retval DDS_RETCODE_BAD_PARAMETER<br>
 *                  One of the given arguments is not valid.<br>
 * @retval DDS_RETCODE_ILLEGAL_OPERATION<br>
 *                  The operation is invoked on an inappropriate object.<br>
 * @retval DDS_RETCODE_ALREADY_DELETED<br>
 *                  The entity has already been deleted.<br>
 * @retval DDS_RETCODE_PRECONDITION_NOT_MET<br>
 *                  The instance handle has not been registered with this reader.
 */
dds_return_t dds_take_instance_mask_wl(dds_entity_t reader_or_condition, void** buf, dds_sample_info_t* si, uint32_t maxs, dds_instance_handle_t handle, uint32_t mask);
/**
 * Description : This operation copies the next, non-previously accessed data value and corresponding<br>
 *               sample info and removes from the data reader.<br>
 * * Arguments :<br>
 * -# rd Reader entity<br>
 * -# buf an array of pointers to samples into which data is read (pointers can be NULL)<br>
 * -# si pointer to \ref dds_sample_info_t returned for a data value<br>
 * -# Returns 1 on successful operation, else 0 if there is no data to be read.
 */
dds_return_t dds_take_next(dds_entity_t reader_or_condition, void** buf, dds_sample_info_t* si);
dds_return_t dds_take_next_wl(dds_entity_t reader_or_condition, void** buf, dds_sample_info_t* si);
/**
 * Description : This operation copies the next, non-previously accessed data value and corresponding<br>
 *               sample info.<br>
 * * Arguments :<br>
 * -# rd Reader entity<br>
 * -# buf an array of pointers to samples into which data is read (pointers can be NULL)<br>
 * -# si pointer to \ref dds_sample_info_t returned for a data value<br>
 * -# Returns 1 on successful operation, else 0 if there is no data to be read.
 */
dds_return_t dds_read_next(dds_entity_t reader_or_condition, void** buf, dds_sample_info_t* si);
dds_return_t dds_read_next_wl(dds_entity_t reader_or_condition, void** buf, dds_sample_info_t* si);
