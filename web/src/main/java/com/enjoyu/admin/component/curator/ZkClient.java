package com.enjoyu.admin.component.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * @author enjoyu
 */
public class ZkClient {
    CuratorFramework client;

    public void start() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.builder()
                .connectString("192.168.128.129:2181")
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .namespace("base")
                .build();
        client.start();
    }

    public void creatNode(String path, byte[] data) throws Exception {
        client.create()
                .creatingParentContainersIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .forPath(path, data);
    }

    public void deleteNode(String path) throws Exception {
        client.delete()
                .guaranteed()
                .deletingChildrenIfNeeded()
                .withVersion(1)
                .forPath(path);
    }

    public byte[] getData(String path) throws Exception {
        return client.getData().forPath(path);
    }

    public boolean checkExists(String path) throws Exception {
        Stat stat = client.checkExists().forPath(path);
        return stat != null;
    }

    public List<String> getChildren(String path) throws Exception {
        return client.getChildren().forPath(path);
    }

    public void watchData(String path) throws Exception {
        NodeCache nodeCache = new NodeCache(client, path, false);
        nodeCache.getListenable().addListener(() -> {
            System.out.println("path : " + nodeCache.getPath());
            System.out.println("listener : " + nodeCache.getCurrentData());
        });
        nodeCache.start(true);
    }

    public void watchChild(String path) throws Exception {
        PathChildrenCache cache = new PathChildrenCache(client, path, false);
        PathChildrenCacheListener plis = (client, event) -> {
            switch (event.getType()) {
                case CHILD_ADDED: {
                    System.out.println("Node added: " + ZKPaths.getNodeFromPath(event.getData().getPath()));
                    break;
                }
                case CHILD_UPDATED:
                    System.out.println("正在更新子节点：" + event.getData().getPath());
                    break;
                case CHILD_REMOVED:
                    System.out.println("子节点被删除");
                    break;
                case CONNECTION_LOST:
                    System.out.println("连接丢失");
                    break;
                case CONNECTION_SUSPENDED:
                    System.out.println("连接被挂起");
                    break;
                case CONNECTION_RECONNECTED:
                    System.out.println("恢复连接");
                    break;
                default:
                    break;
            }
        };
        cache.getListenable().addListener(plis);
        cache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
    }

    public void watchTree(String path, int depth) throws Exception {
        TreeCache treeCache = TreeCache.newBuilder(client, path)
                .setCacheData(true)
                .setMaxDepth(depth)
                .build();

        treeCache.getListenable().addListener((client, event) -> {
            ChildData childData = event.getData();

            if (childData == null) {
                return;
            }
            System.out.println("Path: " + childData.getPath());
            System.out.println("Stat:" + childData.getStat());
            System.out.println("Data: " + new String(childData.getData()));

            switch (event.getType()) {
                case NODE_ADDED:
                    System.out.println("正在新增子节点：" + childData.getPath());
                    break;
                case NODE_UPDATED:
                    System.out.println("正在更新节点：" + childData.getPath());
                    break;
                case NODE_REMOVED:
                    System.out.println("节点被删除：" + childData.getPath());
                    break;
                case CONNECTION_LOST:
                    System.out.println("连接丢失");
                    break;
                case CONNECTION_SUSPENDED:
                    System.out.println("连接被挂起");
                    break;
                case CONNECTION_RECONNECTED:
                    System.out.println("恢复连接");
                    break;
                default:
                    break;
            }
        });

        treeCache.start();
    }
}
