/*
package com.example.demo.config;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.Config;

public class EKSClient {
    public static void main(String[] args) throws Exception {
        try {
            // Load Kubernetes configuration from default kubeconfig file
            ApiClient client = Config.defaultClient();
            Configuration.setDefaultApiClient(client);

            // Create a Kubernetes API client object
            CoreV1Api api = new CoreV1Api(client);

            // List all pods in the default namespace
            String namespace = "default";
            String pretty = "true";
            Boolean allowWatchBookmarks = null;
            String labelSelector = null;
            String fieldSelector = null;
            Integer limit = null;
            String resourceVersion = null;
            String resourceVersionMatch = null;
            Boolean watch = null;
            Integer timeoutSeconds = null;
            Boolean _continue = null; // Continuation token for pagination
            V1PodList podList = api.listNamespacedPod(namespace, pretty, allowWatchBookmarks, fieldSelector, labelSelector, limit, resourceVersion, resourceVersionMatch, timeoutSeconds, watch, _continue);

            for (V1Pod pod : podList.getItems()) {
                System.out.println("Pod Name: " + pod.getMetadata().getName());
            }
        } catch (ApiException e) {
            System.err.println("Exception when calling CoreV1Api#listNamespacedPod");
            e.printStackTrace();
        }
    }
}
*/