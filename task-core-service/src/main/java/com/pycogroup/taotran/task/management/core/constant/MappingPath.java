package com.pycogroup.taotran.task.management.core.constant;


/**
 * @author tao.tran
 *
 * Define general mapping path for each entity resource
 */

public class MappingPath {

    //@formatter:off
    private static final String API_VERSION = "/v1.0/api"               ;

    public static final String USER         = API_VERSION + "/users"    ;
    public static final String ROLE         = API_VERSION + "/roles"    ;
    public static final String USER_ROLE    = API_VERSION + "/userroles";
    public static final String TASK         = API_VERSION + "/tasks"    ;
    //@formatter:on 

    private MappingPath(){
    }
}
