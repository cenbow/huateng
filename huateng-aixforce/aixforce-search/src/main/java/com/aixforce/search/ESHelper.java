package com.aixforce.search;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.ClusterState;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-06-14
 */
public class ESHelper {
    private final static Logger logger = LoggerFactory.getLogger(ESHelper.class);

    /**
     * Define a type for a given index and if exists with its mapping definition (loaded in classloader)
     *
     * @param client Elasticsearch client
     * @param index  Index name
     * @param type   Type name
     * @throws Exception
     */
    public static void pushMapping(Client client, String index, String type) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("pushMapping(" + index + "," + type + ")");
        }

        // If type does not exist, we create it
        boolean mappingExist = isMappingExist(client, index, type);
        if (!mappingExist) {
            if (logger.isDebugEnabled()) {
                logger.debug("Mapping [" + index + "]/[" + type + "] doesn't exist. Creating it.");
            }

            // Read the mapping json file if exists and use it
            String source = readJsonDefinition(type);

            if (source != null) {
                PutMappingRequestBuilder pmrb = client.admin().indices()
                        .preparePutMapping(index)
                        .setType(type);

                if (logger.isDebugEnabled()) {
                    logger.debug("Mapping for [" + index + "]/[" + type + "]=" + source);
                }
                pmrb.setSource(source);

                // Create type and mapping
                PutMappingResponse response = pmrb.execute().actionGet();
                if (!response.isAcknowledged()) {
                    throw new Exception("Could not define mapping for type [" + index + "]/[" + type + "].");
                } else {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Mapping definition for [" + index + "]/[" + type + "] succesfully created.");
                    }
                }
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("No mapping definition for [" + index + "]/[" + type + "]. Ignoring.");
                }
            }
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("Mapping [" + index + "]/[" + type + "] already exists.");
            }

        }
        if (logger.isDebugEnabled()) {
            logger.debug("succeed pushMapping(" + index + "," + type + ")");
        }
    }

    /**
     * Check if a mapping (aka a type) already exists in an index
     *
     * @param client Elasticsearch client
     * @param index  Index name
     * @param type   Mapping name
     * @return true if mapping exists
     */
    public static boolean isMappingExist(Client client, String index, String type) {
        ClusterState cs = client.admin().cluster().prepareState().setFilterIndices(index).execute().actionGet().getState();
        IndexMetaData imd = cs.getMetaData().index(index);

        if (imd == null) return false;

        MappingMetaData mdd = imd.mapping(type);

        if (mdd != null) return true;
        return false;
    }

    /**
     * Create a default index with our default settings (shortcut to {@link #createIndexIfNeeded(Client, String, String)})
     *
     * @param client Elasticsearch client
     */
    public static void createIndexIfNeeded(Client client) {
        createIndexIfNeeded(client, null, null);
    }

    /**
     * Create an index with our default settings
     *
     * @param client    Elasticsearch client
     * @param indexName Index name
     * @param type      Type name
     */
    public static void createIndexIfNeeded(Client client, String indexName, String type) {
        if (logger.isDebugEnabled()) {
            logger.debug("createIndexIfNeeded({}, {})", indexName, type);
        }

        try {
            // We check first if index already exists
            if (!isIndexExist(client, indexName)) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Index {} doesn't exist. Creating it.", indexName);
                }

                CreateIndexRequestBuilder cirb = client.admin().indices().prepareCreate(indexName);

                /*String source = readJsonDefinition("_settings");
                if (source != null) {
                    if (logger.isTraceEnabled()) logger.trace("Mapping for [{}]={}", indexName, source);
                    cirb.setSettings(source);
                }*/

                CreateIndexResponse createIndexResponse = cirb.execute().actionGet();
                if (!createIndexResponse.isAcknowledged())
                    throw new Exception("Could not create index [" + indexName + "].");
            }

            // We create the mapping for the doc type
            pushMapping(client, indexName, type);
        } catch (Exception e) {
            logger.warn("createIndexIfNeeded() : Exception raised : {}", e.getClass());
            if (logger.isDebugEnabled()) {
                logger.debug("- Exception stacktrace :", e);
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug("/createIndexIfNeeded()");
        }
    }

    /**
     * Check if an index already exists
     *
     * @param client Elasticsearch client
     * @param index  Index name
     * @return true if index already exists
     * @throws Exception
     */
    public static boolean isIndexExist(Client client, String index) throws Exception {
        return client.admin().indices().prepareExists(index).execute().actionGet().isExists();
    }

    /**
     * Check if a type already exists
     *
     * @param client Elasticsearch client
     * @param index  Index name
     * @param type   Type name
     * @return true if index already exists
     * @throws Exception
     */
    public static boolean isTypeExist(Client client, String index, String type) throws Exception {
        return client.admin().indices().prepareExists(index, type).execute().actionGet().isExists();
    }

    /**
     * Read the mapping for a type.<br>
     * Shortcut to readFileInClasspath("/esMapping/" + type + ".json");
     *
     * @param type Type name
     * @return Mapping if exists. Null otherwise.
     * @throws Exception
     */
    private static String readJsonDefinition(String type) throws Exception {
        return Resources.toString(Resources.getResource("/esMapping/" + type + ".json"), Charsets.UTF_8);
    }

}
