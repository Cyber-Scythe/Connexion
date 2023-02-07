package com.nashss.se.connexionservice.metrics;

/**
 * Constant values for use with metrics.
 */
public class MetricsConstants {
    public static final String GETUSER_USERNOTFOUND_COUNT = "GetUser.PlaylistNotFoundException.Count";
    public static final String UPDATEPLAYLIST_INVALIDATTRIBUTEVALUE_COUNT =
        "UpdatePlaylist.InvalidAttributeValueException.Count";
    public static final String UPDATEPLAYLIST_INVALIDATTRIBUTECHANGE_COUNT =
        "UpdatePlaylist.InvalidAttributeChangeException.Count";
    public static final String SERVICE = "Service";
    public static final String SERVICE_NAME = "ConnexionService";
    public static final String NAMESPACE_NAME = "U3/ConnexionService";
}
